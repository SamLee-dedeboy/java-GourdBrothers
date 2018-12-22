import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Scorpion extends Organism {
    private static Scorpion instance = new Scorpion();
    private static String name = "Scorpion";
    private static Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\scorpion.jpg");
    public enumGroup group = enumGroup.MONSTER;

    public static synchronized Scorpion getInstance() {
        if (instance == null) {
            instance = new Scorpion();
        }
        return instance;
    }

    private Scorpion() {
        ;
    }

    @Override
    public void run() {
        while (GameController.Gaming && position.getY() > 1) {
            try {
                if (!isDead()) {
                    moveForward(-1);
                    TimeUnit.MILLISECONDS.sleep(2500);
                }
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

}
