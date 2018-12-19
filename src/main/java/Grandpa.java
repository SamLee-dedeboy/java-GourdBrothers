import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.management.relation.RoleUnresolved;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Grandpa extends Organism implements Runnable {
    private static Grandpa instance = null;
    private static String name = "爷爷";
    private static Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\grandpa.jpg");
    public static enumGroup group = enumGroup.HERO;
    private Grandpa() { }

    public synchronized static Grandpa getInstance() {
        if(instance == null) {
            instance = new Grandpa();
        }
        return instance;
    }
    @Override
    public void run() {


        while (GameController.Gaming) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
                moveForward();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void moveForward() {
        int oldPosition_X = position.getX();
        int oldPosition_Y = position.getY();
        int nextPosition_X = oldPosition_X;
        int nextPosition_Y;

        //find next empty Block
        for(nextPosition_Y = oldPosition_Y;
            nextPosition_Y < BattleField.getWidth() && BattleField.collide(nextPosition_X,nextPosition_Y) ;
            nextPosition_Y = (nextPosition_Y+1) %(BattleField.getWidth()));

        this.moveTo(BattleField.at(nextPosition_X, nextPosition_Y));

        int final_nextPosition_Y = nextPosition_Y;
        //
        //display movement
        //
        Platform.runLater(() -> {
            GraphicsContext g = UserInterface.getMyGraphicContext();
            g.clearRect(oldPosition_Y * Block.size, oldPosition_X * Block.size, Block.size, Block.size);
            if(BattleField.at(oldPosition_X,oldPosition_Y).getUsingSkillBeing() != null)
                g.drawImage(BattleField.at(oldPosition_X,oldPosition_Y).getUsingSkillBeing().skill.getSkillImage(),
                        (BattleField.at(oldPosition_X,oldPosition_Y).getUsingSkillBeing().position.getY() + 1) * Block.size,
                        BattleField.at(oldPosition_X,oldPosition_Y).getUsingSkillBeing().position.getX() * Block.size,
                        BattleField.at(oldPosition_X,oldPosition_Y).getUsingSkillBeing().skill.skillRange*Block.size,
                            Block.size);
            g.drawImage(getImage(),
                     final_nextPosition_Y * Block.size,nextPosition_X * Block.size,
                        Block.size,Block.size);
        });
    }
    public String tellName(){ return name; }
    public Image getImage(){ return image; }
    public void cheers() {
        //TODO
    }
}
