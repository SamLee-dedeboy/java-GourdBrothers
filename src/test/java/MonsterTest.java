import javafx.scene.layout.BackgroundImage;
import lydGourdBrother.Controller.GameController;
import lydGourdBrother.Controller.Main;
import lydGourdBrother.GamingCollections.BattleField;
import lydGourdBrother.GamingCollections.Monster;
import lydGourdBrother.GamingCollections.Heros;
import org.junit.Ignore;
import org.junit.Test;

public class MonsterTest {
    @Test
    public void GourdBrotherTest() throws Exception {
        //
        //can not call Monster and Hero methods, because the
        //initialization of them needs to load image
        //Error message:
        //java.lang.RuntimeException: Internal graphics not initialized yet
        //
        //Monster.getInstance().snake();
        BattleField.print();
    }
}
