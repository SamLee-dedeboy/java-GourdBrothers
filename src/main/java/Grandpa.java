import javafx.scene.image.Image;

import static java.lang.Thread.sleep;

public class Grandpa extends Organism {
    private static Grandpa instance = null;
    private static String name = "爷爷";
    private static Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\grandpa.jpg");
    public Grandpa() { }

    public static Grandpa getInstance() {
        if(instance == null) {
            instance = new Grandpa();
        }
        return instance;
    }
    public void run() {

        /*
        while(true) {
            this.moveTo(BattleField.at(position.getX() + 1, position.getY()));
            try {
                wait(8000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        */
    }
    public String tellName(){ return name; }
    public Image getImage(){ return image; }
    public void cheers() {
        //TODO
    }
}
