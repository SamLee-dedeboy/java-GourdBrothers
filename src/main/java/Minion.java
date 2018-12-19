import javafx.scene.image.Image;

public class Minion extends Organism {
    private String name = "喽啰";
    private Image image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\paidaishi.jpg");
    public enumGroup group = enumGroup.MONSTER;
    public String tellName() { return this.name; }
    public Image getImage() { return this.image; }
}
