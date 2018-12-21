import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameController {
    public static GameController instance = null;
    public static boolean Gaming = false;
    public static int numOfRound = 8;
    private static int curRound = -1;
    public static ArrayList<Boolean> roundPassed = new ArrayList<>();

    private static ExecutorService exec;

    public static void setRoundPassed() {
        roundPassed.set(curRound, true);
    }

    public static void setRoundFailed() {
        roundPassed.set(curRound, false);
    }

    public static void handleGameEnd() {
        GraphicsContext g = UserInterface.getMyGraphicContext();
        Gaming = false;
        Monster.stopAllThreads();
        //TODO: if all dead
        GameController.setRoundPassed();
        g.clearRect(0, 0, Block.size * BattleField.getWidth(), Block.size * BattleField.getHeight());
        Heros.getInstance().snake();
        BattleField.display(g);
    }

    public static void handleGameStart() {
        Gaming = true;
        //
        //Execute Threads
        //
        exec.execute(Grandpa.getInstance());
        for (int i = 0; i < 7; i++) {
            exec.execute(Heros.gourdBrothers.get(i));
        }

        for (int i = 0; i < Monster.numOfMinion; i++) {
            exec.execute(Monster.minions.get(i));
        }
        //exec.shutdown();
    }

    public static void setCurRound(int round) {
        curRound = round;
    }

    private GameController() {
        for (int i = 0; i < numOfRound; i++)
            roundPassed.add(false);
        exec = Executors.newCachedThreadPool();
    }

    public static ExecutorService getExecutor() {
        return exec;
    }

    public synchronized static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }

}
