import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Minion extends Organism {
    private String name = "喽啰";
    private Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\minion.jpg");
    public enumGroup group = enumGroup.MONSTER;

    public String tellName() {
        return this.name;
    }

    public Image getImage() {
        return this.image;
    }

    public void run() {
        while (GameController.Gaming && position.getY() > 0) {
            try {
                if (!isDead()) {
                    moveForward(-1);
                    //int waitTime = (int) (1500 + Math.random() * (2000 + 1));
                    int waitTime = 2500;
                    TimeUnit.MILLISECONDS.sleep(waitTime);
                } else
                    break;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
