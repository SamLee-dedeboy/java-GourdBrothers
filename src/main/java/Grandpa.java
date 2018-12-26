import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Grandpa extends Organism implements Runnable {
    private static Grandpa instance = new Grandpa();
    private boolean cheering = false;
    private Grandpa() {
        name = "Grandpa";
        group = enumGroup.HERO;
        image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\grandpa.jpg");

    }

    public static Grandpa getInstance() {
        return instance;
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
            while (GameController.Gaming) {
                if (!cheering) {
                    cheers();
                    TimeUnit.MILLISECONDS.sleep(3000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //GameController.cdLatch.countDown();

    }


    public void cheers() {
        int doCheer = (int) (2000 + Math.random() * (2000 + 1));
        if(doCheer%10 == 0) {   // cheer by probability = 1/10
            for(int i = 0; i < 7; i++) {
                Heros.gourdBrothers.get(i).skill.setFrequency
                        (Heros.gourdBrothers.get(i).skill.getFrequency() / 10);

            }
            cheering = true;
        }
    }
}
