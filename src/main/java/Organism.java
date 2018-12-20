import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Organism implements Runnable {
    public enum enumGroup { HERO, MONSTER };

    public enumGroup group;
    public Block position;
    public Image image;
    public Skill skill;
    private boolean dead = false;

    public void  moveTo(Block b) {
        if(this.position != null)
            this.position.setNull();
        b.set(this);
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
        if(BattleField.at(position.getX(), position.getY()).getBeing().skill.usingSkill) {
            BattleField.at(position.getX(),position.getY()).getBeing().skill.usingSkill = false;
            //set Block using skill false
            for(int i = position.getY() + 1;
                i < position.getY() + 1 + BattleField.at(position.getX(),position.getY()).getBeing().skill.skillRange;
                i++) {
                BattleField.at(position.getX(),i).setUsingSkill(false);
            }
        }
    }
    public boolean isDead() { return dead; }
    public abstract String tellName();
    public abstract Image getImage();


}
