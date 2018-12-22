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
    public int frequency;
    public boolean flying = false;

    private abstract class FlyingSkill extends Skill implements Runnable {
        protected int cur_X, cur_Y;
        protected GraphicsContext g;

        private FlyingSkill(Organism user) {
            super(user);
            flying = true;
        }

        protected void displayFly() {
            try {
                boolean hit = false;
                g = GUIController.getMyGraphicContext();
                while (cur_Y < BattleField.getWidth()) {
                    final int final_cur_X = cur_X;
                    final int final_cur_Y = cur_Y;
                    Platform.runLater(() -> {
                        g.drawImage(skillImage, final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                    });
                    setBlockUsingSkill(cur_X, cur_Y, true);


                    TimeUnit.MILLISECONDS.sleep(123);
                    hit = useNormalSkill(final_cur_X, final_cur_Y);
                    Platform.runLater(() -> {
                        g.clearRect(final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);

                    });
                    //restore get hit but not dead being
                    if (hit && BattleField.at(final_cur_X, final_cur_Y).getBeing() != null) {
                        Platform.runLater(() -> {
                            //System.out.println(BattleField.at(final_cur_X, final_cur_Y).getBeing().tellName());
                            g.drawImage(BattleField.at(final_cur_X, final_cur_Y).getBeing().getImage(),
                                    final_cur_Y * Block.size, final_cur_X * Block.size,
                                    Block.size, Block.size);
                        });

                    }
                    setBlockUsingSkill(cur_X, cur_Y, false);
                    if (hit)
                        break;
                    cur_Y++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class Laser extends FlyingSkill {

        private Laser(int x, int y, Organism user) {
            super(user);
            cur_X = x;
            cur_Y = y;
        }

        @Override
        public void run() {
            displayFly();
        }
    }

    private class Illusion extends FlyingSkill {
        private Illusion(int x, int y, Organism user) {
            super(user);
            cur_X = x;
            cur_Y = y;
        }

        @Override
        public void run() {
            displayFly();
        }
    }

    public Skill(Organism user) {
        String imagePath = "file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\";
        frequency = (int) (2000 + Math.random() * (2000 + 1));
        this.user = user;
        if (user instanceof Gourd) {
            if (((Gourd) user).tellRank() == 1 || ((Gourd) user).tellRank() == 5) {
                this.skillRange = 1;
                attackPoint = 4;
            } else {
                this.skillRange = 4;
                attackPoint = 6;
            }
            skillImage = new Image(imagePath + (((Gourd) user).tellRank() + 1) + "_skill.jpg");
        } else
            skillImage = null;
        //attackPoint = 11;
        skillWidth = Block.size * skillRange;
        skillHeight = Block.size;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public Image getSkillImage() {
        return skillImage;
    }

    public void useSkill(int x, int y) throws Exception {
        if (user instanceof Gourd) {
            switch (((Gourd) user).gourd) {
                case RED:
                    punch(x, y);
                    display(GUIController.getMyGraphicContext(), x, y + 1);
                    break;
                case ORANGE:
                    laser(x, y);
                    //displayMovement(UserInterface.getMyGraphicContext(), x, y);
                    break;
                case YELLOW:
                    wall(x, y);
                    display(GUIController.getMyGraphicContext(), x, y + 1);
                    break;
                case GREEN:
                    fire(x, y);
                    display(GUIController.getMyGraphicContext(), x, y + 1);
                    break;
                case CYAN:
                    water(x, y);
                    display(GUIController.getMyGraphicContext(), x, y + 1);
                    break;
                case BLUE:
                    illusion(x, y);
                    //displayMovement(UserInterface.getMyGraphicContext(), x, y + 1);
                    break;
                case PURPLE:
                    absorb(x, y);
                    display(GUIController.getMyGraphicContext(), x, y + 1);
            }
        }
    }

    private void punch(int x, int y) {
        useNormalSkill(x, y + 1);

    }

    private void laser(int x, int y) {
        GameController.getExecutor().execute(new Laser(x, y + 1, this.user));
    }

    private void wall(int x, int y) {
        useNormalSkill(x, y + 1);
    }

    private void fire(int x, int y) {
        useNormalSkill(x, y + 1);

    }

    private void water(int x, int y) {
        useNormalSkill(x, y + 1);

    }

    private void illusion(int x, int y) {
        GameController.getExecutor().execute(new Illusion(x, y + 1, this.user));

    }

    private void absorb(int x, int y) {
        useNormalSkill(x, y + 1);

    }

    private boolean useNormalSkill(int x, int y) {
        boolean hit = false;
        for (int i = y; i < y + skillRange && i < BattleField.getWidth(); i++) {
            if (BattleField.at(x, i).getBeing() != null) {
                hit = true;
                if (BattleField.at(x, i).getBeing().group != Organism.enumGroup.HERO) {
                    BattleField.at(x, i).getBeing().healthPoint -= attackPoint;

                    if (BattleField.at(x, i).getBeing().healthPoint < 0) {
                        BattleField.at(x, i).getBeing().setDead(true);
                    }

                }
            }
        }
        return hit;
    }

    private void setBlockUsingSkill(int x, int y, boolean flag) {
        synchronized (BattleField.getInstance()) {
            for (int i = y; i <= y + skillRange && i < BattleField.getWidth(); i++) {
                BattleField.at(x, i).setUsingSkill(flag, user);
            }
        }
    }

    private void display(GraphicsContext g, int x, int y) throws Exception {
        Platform.runLater(() -> {
            g.drawImage(skillImage, y * Block.size, x * Block.size, skillWidth, skillHeight);
            setBlockUsingSkill(x, y - 1, true);
        });

        TimeUnit.MILLISECONDS.sleep(1000);

        Platform.runLater(() -> {
            g.clearRect(y * Block.size, x * Block.size, skillWidth, skillHeight);
            setBlockUsingSkill(x, y - 1, false);
            synchronized (BattleField.getInstance()) {
                for (int i = y; i < y + skillRange && i < BattleField.getWidth(); i++) {
                    if (BattleField.at(x, i).getBeing() != null) {
                        BattleField.display(g);
                        break;
                    }
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
                //g.save();
                g.drawImage(skillImage, final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                setBlockUsingSkill(x, y, true);
            });

            TimeUnit.MILLISECONDS.sleep(500);

            Platform.runLater(() -> {
                g.clearRect(final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                setBlockUsingSkill(x, y, false);
                if (BattleField.at(final_cur_X, final_cur_Y).getBeing() != null) ;
                BattleField.display(g);
            });
            cur_Y++;
        }
    }
}
