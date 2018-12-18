import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import java.awt.*;

public class GameCanvas extends Canvas {
    private GraphicsContext g;
    public GameCanvas(double width, double height) {
        super(width, height);

        g = getGraphicsContext2D();
    }
}
