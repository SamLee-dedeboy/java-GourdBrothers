import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Scorpion extends Organism {
    private static Scorpion instance = new Scorpion();

    public static synchronized Scorpion getInstance() {
        return instance;
    }

    private Scorpion() {
        name = "Scorpion";
        group = enumGroup.MONSTER;
        image = new Image(this.getClass().getClassLoader().getResource(("scorpion.jpg")).toString());
        deadImage = new Image(this.getClass().getClassLoader().getResource(("DeadScorpion.jpg")).toString());

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
                else
                    break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       // GameController.cdLatch.countDown();
        //System.out.println(tellName() + " end");
    }



}
