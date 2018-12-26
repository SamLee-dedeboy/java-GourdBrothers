import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Skill {
    private enum SKILL_NAME {PUNCH, LASER, WALL, FIRE, WATER, ILLUSION, ABSORB}
    private Organism user;
    public int skillRange;
    public SKILL_NAME skillName;
    private Image skillImage;
    public boolean usingSkill;
    private int skillWidth;
    private int skillHeight;
    private int attackPoint;
    public int frequency;
    private int anchorFrequency;
    public static ExecutorService skillExec = Executors.newCachedThreadPool();

    //
    //skill
    //
    public Skill(Organism user) {
        String imagePath = "file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\";
        anchorFrequency = Constants.initialFrequency;
        frequency = (int) (anchorFrequency+ Math.random() * (anchorFrequency + 1));
        this.user = user;
        if (user instanceof Gourd) {
            skillName = SKILL_NAME.values()[((Gourd)user).tellRank()];
            if (((Gourd) user).tellRank() == 1 || ((Gourd) user).tellRank() == 5) {
                this.skillRange = 1;
                attackPoint = 4;
            } else {
                this.skillRange = 3;
                attackPoint = 6;
            }
            skillImage = new Image(imagePath + (((Gourd) user).tellRank() + 1) + "_skill.png");
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
                    break;
                case PURPLE:
                    absorb(x, y);
                    display(GUIController.getMyGraphicContext(), x, y + 1);
                    break;
            }
        }
    }


    private void punch(int x, int y) {
        useNormalSkill(x, y + 1);

    }

    private void laser(int x, int y) {
        skillExec.execute(new Laser(x, y + 1, this.user));
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
        ArrayList<Integer> hitBeingIndex = new ArrayList<>();
        for (int i = y; i < y + skillRange && i < BattleField.getWidth(); i++) {
            if (BattleField.at(x, i).getBeing() != null) {
                hit = true;
                if (BattleField.at(x, i).getBeing().group != Organism.enumGroup.HERO) {

                    hitBeingIndex.add(i);
                }
            }
        }

        if(!hitBeingIndex.isEmpty()) {
            for(Integer i: hitBeingIndex) {
                BattleField.at(x, i).getBeing().healthPoint -= attackPoint;
                if (BattleField.at(x, i).getBeing().healthPoint < 0) {
                    BattleField.at(x, i).getBeing().setDead(true);
                    if(!GameController.Gaming)
                        break;
                }
            }
        }

        return hit;

    }
    public void setFrequency(int frequency) {
        this.anchorFrequency = frequency;
        this.frequency = (int) (anchorFrequency + Math.random() * (anchorFrequency + 1));
    }
    public int getFrequency() {
        return frequency;
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
           // g.clearRect((y-1) * Block.size, x * Block.size, Block.size, Block.size);
           // g.drawImage(user.getUsingSkillImage(), (y-1)*Block.size, x * Block.size, Block.size, Block.size);
            g.drawImage(skillImage, y * Block.size, x * Block.size, skillWidth, skillHeight);
            setBlockUsingSkill(x, y, true);
        });

          TimeUnit.MILLISECONDS.sleep(1000);
        Platform.runLater(() -> {
            g.clearRect(y * Block.size, x * Block.size, skillWidth, skillHeight);
            setBlockUsingSkill(x, y, false);
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
    @Deprecated
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
    private abstract class FlyingSkill extends Skill implements Runnable {
        protected int cur_X, cur_Y;
        protected GraphicsContext g;

        private FlyingSkill(Organism user) {
            super(user);
        }

        protected void displayFly() {
            try {
                boolean hit = false;
                g = GUIController.getMyGraphicContext();
                while (GameController.Gaming && cur_Y < BattleField.getWidth()) {
                    final int final_cur_X = cur_X;
                    final int final_cur_Y = cur_Y;
                    Platform.runLater(() -> {
                        g.drawImage(skillImage, final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);
                    });
                    //setBlockUsingSkill(cur_X, cur_Y, true);


                    TimeUnit.MILLISECONDS.sleep(123);
                    hit = useNormalSkill(final_cur_X, final_cur_Y);
                    final boolean finalHit = hit;
                    Platform.runLater(() -> {
                        g.clearRect(final_cur_Y * Block.size, final_cur_X * Block.size, skillWidth, skillHeight);

                        //restore get hit but not dead being
                        if (finalHit && BattleField.at(final_cur_X, final_cur_Y).getBeing() != null) {
                            //System.out.println(BattleField.at(final_cur_X, final_cur_Y).getBeing().tellName());
                            g.drawImage(BattleField.at(final_cur_X, final_cur_Y).getBeing().getImage(),
                                    final_cur_Y * Block.size, final_cur_X * Block.size,
                                    Block.size, Block.size);


                        }
                    });
                    //setBlockUsingSkill(cur_X, cur_Y, false);
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

}
