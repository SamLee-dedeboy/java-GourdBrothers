import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.management.relation.RoleUnresolved;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Gourd extends Organism implements Runnable {

    public enum enum_Gourd {
        RED("老大", "赤", 0), ORANGE("老二", "橙", 1), YELLOW("老三", "黄", 2),
        GREEN("老四", "绿", 3), CYAN("老五", "青", 4), BLUE("老六", "蓝", 5), PURPLE("老七", "紫", 6);
        private String name, color;
        private int rank;
        Skill skill;
        enum_Gourd(String name, String color, int rank) {
            this.rank = rank;
            this.name = name;
            this.color = color;
            this.skill = new Skill(this);
        }
        private class Skill {
            private enum_Gourd user;
            private int skillRange;
            private Image skillImage;
            private int skillWidth;
            private int skillHeight;

            private Skill(enum_Gourd gourd) {
                String imagePath = "file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\";
                this.user = gourd;
                if(gourd.rank == 1)
                    this.skillRange = 1;
                else
                    this.skillRange = 3;
                skillImage = new Image(imagePath + (gourd.rank + 1) + "_skill.jpg");
                skillWidth = Block.size * skillRange;
                skillHeight = Block.size;
            }

            private void useSkill(int x, int y) throws Exception {

                switch(user) {
                    case GREEN: fire(x,y);  display(UserInterface.getMyGraphicContext(), x, y); break;
                    case CYAN: water(x,y);  display(UserInterface.getMyGraphicContext(), x, y); break;
                    case ORANGE: laser(x,y); displayMovement(UserInterface.getMyGraphicContext(), x, y); break;
                }



            }

            private void fire(int x, int y) {
                //TODO

            }
            private void water(int x, int y) {
                //TODO
            }
            private void laser(int x, int y) {
                //TODO
            }
            private void display(GraphicsContext g, int x, int y) throws Exception {
                Platform.runLater(() -> {
                    g.drawImage(skillImage,y*Block.size,x*Block.size,skillWidth,skillHeight);
                });

                TimeUnit.MILLISECONDS.sleep(1000);
                //store covered Being
                ArrayList<Block> coveredBlock = new ArrayList<>();
                for (int i = y; i < y + skillRange; i++) {
                    if (BattleField.at(x, i).getBeing() != null) {
                        coveredBlock.add(BattleField.at(x, i));
                    }
                }
                Platform.runLater(() -> {
                    g.clearRect(y * Block.size, x * Block.size, skillWidth, skillHeight);

                    //restore covered Being
                    for (Block b : coveredBlock) {
                        if (b.getBeing() != null) {

                            g.drawImage(b.getBeing().getImage(),
                                    b.getY() * Block.size, b.getX() * Block.size,
                                    Block.size, Block.size);
                        }
                    }
                });
            }
            private void displayMovement(GraphicsContext g, int x, int y) throws Exception {
                int cur_X = x;
                int cur_Y = y;
                while (cur_Y < BattleField.getWidth()) {
                    final int final_cur_X = cur_X;
                    final int final_cur_Y = cur_Y;
                    Platform.runLater(() -> {
                        g.drawImage(skillImage, final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                    });

                    TimeUnit.MILLISECONDS.sleep(500);
                    //store covered Being
                    ArrayList<Block> coveredBlock = new ArrayList<>();
                    for (int i = cur_Y; i < cur_Y + skillRange && i < BattleField.getWidth(); i++) {
                        if (BattleField.at(cur_X, i).getBeing() != null) {
                            coveredBlock.add(BattleField.at(cur_X, i));
                        }
                    }
                    Platform.runLater(() -> {
                        g.clearRect(final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                    });
                    cur_Y++;
                    //restore covered Being
                    for (Block b : coveredBlock) {
                        if (b.getBeing() != null) {
                            Platform.runLater(() -> {
                                g.drawImage(b.getBeing().getImage(),
                                        b.getY() * Block.size, b.getX() * Block.size,
                                        Block.size, Block.size);
                            });
                        }
                    }
                }
            }
        }
    }
    private enum_Gourd gourd;

    @Override
    public void run() {
        try {
            while(GameController.Gaming) {
                TimeUnit.MILLISECONDS.sleep(1000);
                gourd.skill.useSkill(position.getX(), position.getY() + 1);


                //attack();
                // BattleField.display(UserInterface.getMyGraphicContext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void attack()throws Exception {
        if(BattleField.hasEnemy(group, position.getX(), position.getY(), gourd.skill.skillRange)) {
            gourd.skill.useSkill(position.getX(), position.getY());
        }
}
    public String tellName() {
        return this.gourd.name;
    }

    public String tellColor() {
        return this.gourd.color;
    }
    public int tellRank() {
        return this.gourd.rank;
    }

    public void report(int src, int dst) {
        System.out.println(this.gourd.name + ": " + src + "->" + dst);
    }

    Gourd(int rank){
        group = enumGroup.HERO;
        image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\" + (rank + 1) +".jpg");
        gourd = enum_Gourd.values()[rank];
    }
    public Image getImage() { return this.image; }

    public static Gourd[] values(){
        Gourd[] gourdBrothers = new Gourd[7];
        for(int i = 0; i < 7; i++){
            gourdBrothers[i] = new Gourd(i);
        }
        return gourdBrothers;
    }
}