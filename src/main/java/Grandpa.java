import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Grandpa extends Organism implements Runnable {
    private static Grandpa instance = new Grandpa();
    private static String name = "爷爷";
    private static Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\grandpa.jpg");
    public static enumGroup group = enumGroup.HERO;
    private boolean cheering = false;
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
    }



    public String tellName() {
        return name;
    }

    public Image getImage() {
        return image;
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
