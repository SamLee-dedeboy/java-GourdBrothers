import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Skill {
    private Organism user;
    private int skillRange;
    private Image skillImage;
    private int skillWidth;
    private int skillHeight;

    private Skill(Organism user) {
        String imagePath = "file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\";
        this.user = user;
        if(user.getClass() == Gourd.class)
            this.skillRange = 1;
        else
            this.skillRange = 3;
       // skillImage = new Image(imagePath + (gourd.rank + 1) + "_skill.jpg");
        skillImage = new Image(imagePath +  "1_skill.jpg");
        skillWidth = Block.size * skillRange;
        skillHeight = Block.size;
    }

    private void useSkill(int x, int y) throws Exception {

//        switch(user) {
//
//            case GREEN: fire(x,y);  display(UserInterface.getMyGraphicContext(), x, y); break;
//            case CYAN: water(x,y);  display(UserInterface.getMyGraphicContext(), x, y); break;
//            case ORANGE: laser(x,y); displayMovement(UserInterface.getMyGraphicContext(), x, y); break;
//
//        }



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

        Platform.runLater(() -> {
            g.clearRect(y * Block.size, x * Block.size, skillWidth, skillHeight);
            for(int i = y; i < y + skillRange; i++){
                if(BattleField.at(x,i).getBeing()!= null) {
                    BattleField.display(g);
                    break;
                }
            }
            //BattleField.display(g);
        });

    }
    private void displayMovement(GraphicsContext g, int x, int y) throws Exception {
        int cur_X = x;
        int cur_Y = y;
        while (cur_Y < BattleField.getWidth()) {
            final int final_cur_X = cur_X;
            final int final_cur_Y = cur_Y;
            Platform.runLater(() -> {
                //g.save();
                g.drawImage(skillImage, final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
            });

            TimeUnit.MILLISECONDS.sleep(500);

            Platform.runLater(() -> {
                g.clearRect(final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                if(BattleField.at(final_cur_X, final_cur_Y).getBeing()!= null)
                    BattleField.display(g);
            });
            cur_Y++;
        }
    }
}
}