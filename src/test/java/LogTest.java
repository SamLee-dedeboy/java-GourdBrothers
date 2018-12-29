import lydGourdBrother.Controller.LogController;
import org.junit.Test;

public class LogTest {
    @Test
    public void getInstanceTest() {
        LogController.getInstance();
    }
    @Test
    public void writeLogTest() {
        LogController.writeLog("Testing");
    }
}
