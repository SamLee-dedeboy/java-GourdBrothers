import lydGourdBrother.Controller.LogController;
import org.junit.Test;

public class LogTest {
    @Test
    public void writeLogTest() {
        LogController.openNewRoundLog(10);
        LogController.writeLog("Testing");
        LogController.saveLog();
    }
}
