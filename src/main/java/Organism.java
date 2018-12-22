
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Organism implements Runnable {
    public enum enumGroup {HERO, MONSTER}


    public enumGroup group;
    public Block position;
    public Image image;
    public Skill skill = null;
    public int healthPoint = Constants.initialHealthPoint;
    private boolean dead = false;

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
                        if (being.skill.usingSkill) {
                            being.skill.usingSkill = false;
                            //set Block using skill false
                            for (int i = position.getY() + 1;
                                 i < position.getY() + 1 + being.skill.skillRange;
                                 i++) {
                                BattleField.at(position.getX(), i).setUsingSkill(false);
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
            if (Monster.AllDead())
                GameController.setRoundPassed();
            if (Heros.AllDead())
                GameController.setRoundFailed();
        }
    }

    public boolean isDead() {
        return dead;
    }

    public abstract String tellName();

    public abstract Image getImage();

    public void moveForward(int direction) {
        int oldPosition_X = position.getX();
        int oldPosition_Y = position.getY();
        int nextPosition_X = oldPosition_X;
        int nextPosition_Y = oldPosition_Y + direction;
        synchronized (BattleField.getInstance()) {
            if (BattleField.collide(nextPosition_X, nextPosition_Y)) {
                if (BattleField.at(nextPosition_X, nextPosition_Y).getBeing().group == enumGroup.HERO) {
                    killBeing(nextPosition_X, nextPosition_Y);
                } else
                    nextPosition_Y--;
            }
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
