
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public abstract class Organism implements Runnable {
    public enum enumGroup {HERO, MONSTER}

    protected String name;
    public enumGroup group;
    protected Image image;
    protected Block position = null;
    protected Image usingSkillImage = null;
    protected Skill skill = null;
    protected int healthPoint = Constants.initialHealthPoint;
    protected boolean dead = false;
    protected boolean stop = false;

    public void stopMoving() { this.stop = true;}
    public void moveTo(Block b) {
        if (this.position != null)
            this.position.setNull();
        BattleField.setBeing(this, b.getX(), b.getY());
        position = b;
    }

    void fallBack() {
        if (position != null)
            position.setNull();
        position = null;
    }

    public void setDead(boolean flag) {
        if (dead && flag)    //no need to set a dead being dead again
            return;

        dead = flag;
        //set using skill false
        if (dead) {
            if (position != null) {
                Organism being = BattleField.at(position.getX(), position.getY()).getBeing();
                if (being != null) {
                    // if(being.skill != null) {
                    if (being.group == enumGroup.HERO) {
                        if(being.tellName().equals("Grandpa")) {
                            GameController.setRoundFailed();
                            return;
                        }
                        if(being.skill != null) {
                            if (being.skill.usingSkill) {
                                being.skill.usingSkill = false;
                                //set Block using skill false
                                for (int i = position.getY() + 1;
                                     i < position.getY() + 1 + being.skill.skillRange;
                                     i++) {
                                    BattleField.at(position.getX(), i).setUsingSkill(false);
                                }
                            }
                        }
                    } else {
                        if (being.getClass() == Minion.class) {
                            Monster.SetMinionDead();
                        }
                    }
                }
                BattleField.at(position.getX(), position.getY()).setNull();
            }
            LogController.writeLog(this.tellName() + " dead");
            try {
                if (Monster.AllDead()) {
                    //Skill.skillExec.awaitTermination(3000, TimeUnit.MILLISECONDS);
                    GameController.setRoundPassed();
                }
                if (Heros.AllDead()) {
                    //Skill.skillExec.awaitTermination(3000, TimeUnit.MILLISECONDS);
                    GameController.setRoundFailed();
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDead() {
        return dead;
    }

    public String tellName() { return name; }

    public Image getImage() { return image; }
    public Image getUsingSkillImage() { return usingSkillImage; }
    public void moveForward(int direction) {
        int oldPosition_X = position.getX();
        int oldPosition_Y = position.getY();
        int nextPosition_X = oldPosition_X;
        int nextPosition_Y = oldPosition_Y + direction;
        synchronized (BattleField.getInstance()) {
            if (BattleField.collide(nextPosition_X, nextPosition_Y)) {
                if (BattleField.at(nextPosition_X, nextPosition_Y).getBeing().group == enumGroup.HERO) {
                    killBeing(nextPosition_X, nextPosition_Y);
                    System.out.println("kill huluwa!");
                } else {
                    System.out.println("met friend");
                    return;
                }
            }
            if(nextPosition_Y < 0)
                return;
            this.moveTo(BattleField.at(nextPosition_X, nextPosition_Y));
            //
            //display movement
            //
            int final_nextPosition_Y = nextPosition_Y;
            Platform.runLater(() -> {
                GraphicsContext g = GUIController.getMyGraphicContext();
                g.clearRect(oldPosition_Y * Block.size, oldPosition_X * Block.size, Block.size, Block.size);

                //repaint covered unflying skill
                if (BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing() != null)
                    g.drawImage(BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing().skill.getSkillImage(),
                            (BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing().position.getY() + 1) * Block.size,
                            BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing().position.getX() * Block.size,
                            BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing().skill.skillRange * Block.size,
                            Block.size);

                g.drawImage(getImage(),
                        final_nextPosition_Y * Block.size, nextPosition_X * Block.size,
                        Block.size, Block.size);

            });
        }
    }
    private void killBeing(int x, int y) {
        BattleField.at(x, y).getBeing().setDead(true);
    }
}
