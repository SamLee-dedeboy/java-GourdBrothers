import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Scorpion extends Organism {
    private static Scorpion instance;

    public static synchronized Scorpion getInstance() {
        if (instance == null) {
            instance = new Scorpion();
        }
        return instance;
    }

    private Scorpion() {
        name = "Scorpion";
        group = enumGroup.MONSTER;
        image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\scorpion.jpg");

    }

    @Override
    public void run() {
        while (GameController.Gaming && position.getY() > 1 && !stop) {
            try {
                if (!isDead()) {
                    moveForward(-1);
                    LogController.writeLog("Scorpion moveTo ("
                                + position.getX() + ", " + position.getY() + ")");

                    TimeUnit.MILLISECONDS.sleep(2500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       // GameController.cdLatch.countDown();

    }



}
