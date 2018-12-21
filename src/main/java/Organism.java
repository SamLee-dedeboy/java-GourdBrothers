
import javafx.scene.image.Image;

public abstract class Organism implements Runnable {
    public enum enumGroup { HERO, MONSTER };

    public enumGroup group;
    public Block position;
    public Image image;
    public Skill skill = null;
    public int healthPoint = Constants.initialHealthPoint;
    private boolean dead = false;

    public void  moveTo(Block b) {
        if(this.position != null)
            this.position.setNull();
        BattleField.setBeing(this, b.getX(),b.getY());
        position = b;
    }
    void fallBack() {
        if(position != null)
            position.setNull();
        position = null;
    }
    public void setDead(boolean flag) {
        if(dead && flag)    //no need to set a dead being dead again
            return;

        dead = flag;
        //set using skill false
        if(dead) {
            if(position != null) {
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
            if(Heros.AllDead())
                GameController.setRoundFailed();
        }
    }
    public boolean isDead() { return dead; }
    public abstract String tellName();
    public abstract Image getImage();


}
