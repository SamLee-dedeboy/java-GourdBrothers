import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Organism implements Runnable {
    public enum enumGroup { HERO, MONSTER };

    public enumGroup group;
    public Block position;
    public Image image;
    public Skill skill = null;
    public int healthPoint = 10;
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
        dead = flag;
        //set using skill false
        Organism being = BattleField.at(position.getX(), position.getY()).getBeing();
        if(being != null) {
           // if(being.skill != null) {
            if(being.group == enumGroup.HERO) {
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
        }
        BattleField.at(position.getX(), position.getY()).setNull();
    }
    public boolean isDead() { return dead; }
    public abstract String tellName();
    public abstract Image getImage();


}
