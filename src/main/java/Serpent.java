import javafx.scene.image.Image;

public class Serpent extends Organism {
    private static Serpent instance = null;
    private static String name = "蛇精";
    private static Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\serpent.jpg");
    public enumGroup group = enumGroup.MONSTER;

    public static synchronized Serpent getInstance() {
        if(instance == null) {
            instance = new Serpent();
        }
        return instance;
    }
    public void run () {

        while (GameController.Gaming) {
            try {
                //TimeUnit.MILLISECONDS.sleep(200);
                //moveForward();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public String tellName(){ return name; }
    public Image getImage(){ return image; }
    public void cheers() {
        //TODO
    }
}
