package lydGourdBrother.Beings;

import lydGourdBrother.Controller.Constants;
import lydGourdBrother.Controller.GUIController;
import lydGourdBrother.Controller.GameController;
import lydGourdBrother.Controller.LogController;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import lydGourdBrother.GamingCollections.BattleField;
import lydGourdBrother.GamingCollections.Block;
import lydGourdBrother.GamingCollections.Heros;
import lydGourdBrother.GamingCollections.Monster;
import lydGourdBrother.Skills.Skill;

public abstract class Organism implements Runnable {
    public enum enumGroup {HERO, MONSTER};
    public Skill skill = null;
    public int healthPoint = Constants.initialHealthPoint;
    public enumGroup group;
    //package private
    String name;
    Image image;
    Image deadImage;
    Block position = null;
    boolean stop = false;
    private Image usingSkillImage = null;
    private boolean dead = false;

    public void stopMoving() { this.stop = true;}
    public void moveTo(Block b) {
        if (this.position != null)
            this.position.setNull();
        BattleField.setBeing(this, b.getX(), b.getY());
        position = b;
    }

    public void fallBack() {
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
                        if(being.tellName().equals("lydGourdBrother.Beings.Grandpa")) {
                            GameController.setRoundEnd();
                            return;
                        }
                        if(being.skill != null) {
                            if (being.skill.usingSkill) {
                                being.skill.usingSkill = false;
                                //set lydGourdBrother.GamingCollections.Block using skill false
                                for (int i = position.getY() + 1;
                                     i < position.getY() + 1 + being.skill.skillRange;
                                     i++) {
                                    BattleField.at(position.getX(), i).setUsingSkill(false);
                                }
                            }
                        }
                        BattleField.at(position.getX(), position.getY()).setNull();
                    } else {
                        if (being.getClass() == Minion.class) {
                            Monster.SetMinionDead();
                        }
                    }
                }

            }
            LogController.writeLog(this.tellName() + " dead");
            try {
                if (Monster.AllDead() || Heros.AllDead()) {
                    GameController.setRoundEnd();
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

    public Image getImage() {
        if(dead && this.group == enumGroup.MONSTER)
            return deadImage;
        else
            return image;
    }
    public Image getUsingSkillImage() { return usingSkillImage; }
    void moveForward(int direction) {
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
                    if(BattleField.at(nextPosition_X,nextPosition_Y).getBeing().dead) {
                        BattleField.at(nextPosition_X, nextPosition_Y).setNull();
                    } else {
                        System.out.println("met friend");
                        return;
                    }
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
