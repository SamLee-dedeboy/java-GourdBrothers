import javafx.scene.image.Image;

public abstract class Organism {
    Block position;
    Image image;
    void  moveTo(Block b){
        b.set(this);
        position = b;
    }
    void fallBack(){
        position = null;
    }
    public abstract String tellName();
    public abstract Image getImage();
}
