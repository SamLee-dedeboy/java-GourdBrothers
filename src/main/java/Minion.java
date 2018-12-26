import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Minion extends Organism {
    private int index;
    public Minion(int index) {
        this.index = index;
        name = "minion " + index;
        group = enumGroup.MONSTER;
        image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\minion.jpg");

    }


    public void run() {
        int x, y;
        while (GameController.Gaming && position.getY() > 0 && !stop) {
            try {
                if (!isDead()) {
                        moveForward(-1);
                        x = position.getX();
                        y = position.getY();
                        LogController.writeLog("minion " + index + " moveTo ("
                                + x + ", " + y + ")");

                    //int waitTime = (int) (1500 + Math.random() * (2000 + 1));
                    int waitTime = 1500;
                    TimeUnit.MILLISECONDS.sleep(waitTime);
                }
                else
                    break;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println(tellName() + " end");
    }


}
