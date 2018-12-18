import javafx.scene.image.Image;

public class Scorpion extends Organism {
    private static Scorpion instance = null;
    private static String name = "蝎子精";
    private static Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\scorpion.jpg");

    public static synchronized Scorpion getInstance() {
        if(instance == null) {
            instance = new Scorpion();
        }
        return instance;
    }
    public String tellName(){ return name; }
    public Image getImage(){ return image; }
    public void cheers() {
        //TODO
    }
}
