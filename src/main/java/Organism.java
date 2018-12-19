import javafx.scene.image.Image;

public abstract class Organism {
    public enum enumGroup { HERO, MONSTER };

    public enumGroup group;
    public Block position;
    public Image image;
    public Skill skill;
    void  moveTo(Block b) {
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
    public abstract String tellName();
    public abstract Image getImage();
}
