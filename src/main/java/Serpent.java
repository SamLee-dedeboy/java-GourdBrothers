import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Serpent extends Organism {
    private static Serpent instance;
    public static synchronized Serpent getInstance() {
        if (instance == null) {
            instance = new Serpent();
        }
        return instance;
    }

    private Serpent() {
        name = "Serpant";
        group = enumGroup.MONSTER;
        image = new Image(this.getClass().getClassLoader().getResource(("serpent.jpg")).toString());

    }

    public void run() {

        while (GameController.Gaming) {
            try {
                //moveForward();
                System.out.println(tellName() + " moving");
                TimeUnit.MILLISECONDS.sleep(5000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       // GameController.cdLatch.countDown();

    }



    public void cheers() {
        //TODO
    }
}
