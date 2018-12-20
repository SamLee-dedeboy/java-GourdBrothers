import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Skill {
    private Organism user;
    public int skillRange;
    private Image skillImage;
    public boolean usingSkill;
    private int skillWidth;
    private int skillHeight;
    private int attackPoint;
    private abstract class FlyingSkill implements Runnable{
        protected int cur_X, cur_Y;
        protected GraphicsContext g;
    }
    private class Laser extends FlyingSkill {

        private Laser(int x, int y) {
            cur_X = x;
            cur_Y = y;
        }
        @Override
        public void run() {
            try {
                g = UserInterface.getMyGraphicContext();
                while (cur_Y < BattleField.getWidth()) {
                    final int final_cur_X = cur_X;
                    final int final_cur_Y = cur_Y;
                    Platform.runLater(() -> {
                        g.drawImage(skillImage, final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                        setBlockUsingSkill(cur_X,cur_Y, true);
                    });

                    TimeUnit.MILLISECONDS.sleep(100);

                    Platform.runLater(() -> {
                        g.clearRect(final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                        setBlockUsingSkill(cur_X, cur_Y, false);
                        if (BattleField.at(final_cur_X, final_cur_Y).getBeing() != null)
                                useNormalSkill(final_cur_X, final_cur_Y);
                    });
                    cur_Y++;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private class Illusion extends FlyingSkill {
        private Illusion(int x, int y) {
            cur_X = x;
            cur_Y = y;
        }
        @Override
        public void run() {
            try {
                g = UserInterface.getMyGraphicContext();
                while (cur_Y < BattleField.getWidth()) {
                    final int final_cur_X = cur_X;
                    final int final_cur_Y = cur_Y;
                    Platform.runLater(() -> {
                        g.drawImage(skillImage, final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                    });
                    setBlockUsingSkill(cur_X, cur_Y, true);


                    TimeUnit.MILLISECONDS.sleep(200);

                    Platform.runLater(() -> {
                        g.clearRect(final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                    });
                    if (BattleField.at(final_cur_X, final_cur_Y).getBeing() != null) {
                        if (useNormalSkill(final_cur_X, final_cur_Y)) {
                            break;
                        }
                    }
                    setBlockUsingSkill(cur_X, cur_Y, false);


                    cur_Y++;
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public Skill(Organism user) {
        String imagePath = "file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\";
        this.user = user;
        if (user instanceof Gourd) {
            if (((Gourd) user).tellRank() == 1 || ((Gourd) user).tellRank() == 5)
                this.skillRange = 1;
            else
                this.skillRange = 7;
            skillImage = new Image(imagePath + (((Gourd) user).tellRank() + 1) + "_skill.jpg");
        } else
            skillImage = null;
        attackPoint = 11;
        skillWidth = Block.size * skillRange;
        skillHeight = Block.size;
    }

    public Image getSkillImage() {
        return skillImage;
    }

    public void useSkill(int x, int y) throws Exception {
        if (user instanceof Gourd) {
            switch (((Gourd) user).gourd) {
                case RED:
                    punch(x, y);
                    display(UserInterface.getMyGraphicContext(), x, y + 1);
                    break;
                case ORANGE:
                    laser(x, y);
                    //displayMovement(UserInterface.getMyGraphicContext(), x, y);
                    break;
                case YELLOW:
                    wall(x,y);
                    display(UserInterface.getMyGraphicContext(),x,y + 1);
                    break;
                case GREEN:
                    fire(x, y);
                    display(UserInterface.getMyGraphicContext(), x, y + 1);
                    break;
                case CYAN:
                    water(x, y);
                    display(UserInterface.getMyGraphicContext(), x, y + 1);
                    break;
                case BLUE:
                    illusion(x, y);
                    //displayMovement(UserInterface.getMyGraphicContext(), x, y + 1);
                    break;
                case PURPLE:
                    absorb(x,y);
                    display(UserInterface.getMyGraphicContext(), x, y + 1);
            }
        }
    }
    private void punch(int x, int y) {
        useNormalSkill(x,y + 1);

    }

    private void laser(int x, int y) {
        UserInterface.exec.execute(new Laser(x,y + 1));
    }
    private void wall(int x, int y) {
        useNormalSkill(x,y + 1);
    }
    private void fire(int x, int y) {
        useNormalSkill(x,y + 1);

    }

    private void water(int x, int y) {
        useNormalSkill(x,y + 1);

    }
    private void illusion(int x, int y) {
        UserInterface.exec.execute(new Illusion(x,y + 1));

    }
    private void absorb(int x, int y) {
        useNormalSkill(x,y + 1);

    }
    private boolean useNormalSkill(int x, int y) {
        boolean flag = false;

        for(int i = y; i < y + skillRange && i < BattleField.getWidth(); i++) {
            if (BattleField.at(x, i).getBeing() != null) {
                //System.out.println(BattleField.at(x,i).getBeing().tellName());
                    if (BattleField.at(x, i).getBeing().group != Organism.enumGroup.HERO) {
                       // System.out.println("collide being");
                        BattleField.at(x, i).getBeing().healthPoint -= attackPoint;


                        if (BattleField.at(x, i).getBeing().healthPoint < 0) {
                            //System.out.println(BattleField.at(x,i).getBeing().tellName());
                            BattleField.at(x, i).getBeing().setDead(true);
                            //BattleField.print();
                            flag = true;
                        }

                    }
                }
            }

        return flag;
    }

    private void setBlockUsingSkill(int x, int y, boolean flag) {
        synchronized (BattleField.getInstance()) {
            for (int i = y; i <= y + skillRange && i < BattleField.getWidth(); i++)
                BattleField.at(x, i).setUsingSkill(flag, user);
        }
    }
    private void display(GraphicsContext g, int x, int y) throws Exception {
        Platform.runLater(() -> {
            g.drawImage(skillImage, y * Block.size, x * Block.size, skillWidth, skillHeight);
            setBlockUsingSkill(x,y - 1,true);
        });

        TimeUnit.MILLISECONDS.sleep(1000);

        Platform.runLater(() -> {
            g.clearRect(y * Block.size, x * Block.size, skillWidth, skillHeight);
            setBlockUsingSkill(x,y - 1,false);
            synchronized (BattleField.getInstance()) {
                for (int i = y; i < y + skillRange && i < BattleField.getWidth(); i++) {
                    if (BattleField.at(x, i).getBeing() != null) {
                        BattleField.display(g);
                        break;
                    }
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
                setBlockUsingSkill(x,y,true);
            });

            TimeUnit.MILLISECONDS.sleep(500);

            Platform.runLater(() -> {
                g.clearRect(final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                setBlockUsingSkill(x,y,false);
                if (BattleField.at(final_cur_X, final_cur_Y).getBeing() != null);
                    BattleField.display(g);
            });
            cur_Y++;
        }
    }
}
