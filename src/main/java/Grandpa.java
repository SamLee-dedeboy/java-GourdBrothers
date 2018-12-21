import javafx.scene.image.Image;

public class Grandpa extends Organism implements Runnable {
    private static Grandpa instance = null;
    private static String name = "爷爷";
    private static Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\grandpa.jpg");
    public static enumGroup group = enumGroup.HERO;

    private Grandpa() {
    }

    public synchronized static Grandpa getInstance() {
        if (instance == null) {
            instance = new Grandpa();
        }
        return instance;
    }

    @Override
    public void run() {
        while (GameController.Gaming) {
            try {
                //moveForward();
                //TimeUnit.MILLISECONDS.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String tellName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public void cheers() {
        //TODO
    }
}
