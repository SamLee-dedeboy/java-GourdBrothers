package lydGourdBrother.Beings;

import javafx.scene.image.Image;
import lydGourdBrother.Controller.GameController;
import lydGourdBrother.GamingCollections.Heros;

import java.util.concurrent.TimeUnit;

public class Grandpa extends Organism implements Runnable {
    private static Grandpa instance = new Grandpa();
    public boolean cheering = false;
    private Grandpa() {
        name = "Grandpa";
        group = enumGroup.HERO;
        image = new Image(this.getClass().getClassLoader().getResource(("grandpa.jpg")).toString());
    }

    public static Grandpa getInstance() {
        return instance;
    }

    @Override
    public void run() {
        try {
            while (GameController.Gaming) {
                if (!isDead()) {
                    //System.out.println("not dead");
                    if (!cheering) {
                        //System.out.println("try cheers");
                        cheers();
                        if(GameController.Gaming)
                            TimeUnit.MILLISECONDS.sleep(3000);
                        else
                            break;
                    } else
                        break;
                }
                else
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //lydGourdBrother.Controller.GameController.cdLatch.countDown();
        //System.out.println(tellName() + " end");
    }


    public void cheers() {
        int doCheer = (int) (2000 + Math.random() * (2000 + 1));
        if(doCheer%20 == 0) {   // cheer by probability = 1/20
           // System.out.println("cheer success!");
            synchronized (Heros.getInstance()) {
                    Heros.setFrequency(Heros.getFrequency() / 3);
            }
            cheering = true;
        }
    }
}
