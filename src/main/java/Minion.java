import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.concurrent.TimeUnit;

public class Minion extends Organism {
    private String name = "喽啰";
    private Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\minion.jpg");
    public enumGroup group = enumGroup.MONSTER;
    public String tellName() { return this.name; }
    public Image getImage() { return this.image; }
    public void run() {

        while (GameController.Gaming) {
            while (position.getY() > 0) {
                try {
                    if (!isDead()) {
                        if(!GameController.Gaming)
                            break;
                        moveForward();
                        int waitTime = (int) (1500 + Math.random() * (2000 + 1));
                        TimeUnit.MILLISECONDS.sleep(waitTime);
                    }
                    else
                        break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void moveForward() {
        int oldPosition_X = position.getX();
        int oldPosition_Y = position.getY();
        int nextPosition_X = oldPosition_X;
        int nextPosition_Y = oldPosition_Y - 1;
        synchronized (BattleField.getInstance()) {
            if (BattleField.collide(nextPosition_X, nextPosition_Y)) {
                if (BattleField.at(nextPosition_X, nextPosition_Y).getBeing().group == enumGroup.HERO) {
                    killBeing(nextPosition_X, nextPosition_Y);
                } else
                    nextPosition_Y--;
            }
            this.moveTo(BattleField.at(nextPosition_X, nextPosition_Y));
            //
            //display movement
            //
            int final_nextPosition_Y = nextPosition_Y;
            Platform.runLater(() -> {
                GraphicsContext g = UserInterface.getMyGraphicContext();
                g.clearRect(oldPosition_Y * Block.size, oldPosition_X * Block.size, Block.size, Block.size);

                //repaint covered unflying skill
                if (BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing() != null)
                    g.drawImage(BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing().skill.getSkillImage(),
                            (BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing().position.getY() + 1) * Block.size,
                            BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing().position.getX() * Block.size,
                            BattleField.at(oldPosition_X, oldPosition_Y).getUsingSkillBeing().skill.skillRange * Block.size,
                            Block.size);

                g.drawImage(getImage(),
                        final_nextPosition_Y * Block.size, nextPosition_X * Block.size,
                        Block.size, Block.size);

            });
        }
    }
    private void killBeing(int x, int y) {
        BattleField.at(x,y).getBeing().setDead(true);
    }
}
