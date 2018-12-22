import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameController {
    //
    //Singleton
    //
    public static GameController instance;

    public synchronized static GameController getInstance() {
        if (instance == null)
            instance = new GameController();
        return instance;
    }
    private GameController() { }


    public static boolean Gaming = false;
    public static int numOfRound = 8;
    private static int curRound = -1;
    public static ArrayList<Boolean> roundPassed = new ArrayList<>();

    private static ExecutorService exec;

    public static void setRoundPassed() {
        roundPassed.set(curRound, true);
        handleRoundEnd();
    }

    public static void setRoundFailed() {
        roundPassed.set(curRound, false);
        handleRoundEnd();
    }

    public static void handleRoundEnd(){
        Gaming = false;
        try {
            //TimeUnit.MILLISECONDS.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GUIController.resetRoundButton(false);
        GraphicsContext g = GUIController.getMyGraphicContext();
        g.clearRect(0, 0, Block.size * BattleField.getWidth(), Block.size * BattleField.getHeight());
        Monster.resetRound();
        Heros.resetRound();
        BattleField.display(g);
    }

    public static void initGame() {
        for (int i = 0; i < numOfRound; i++)
            roundPassed.add(false);
        exec = Executors.newCachedThreadPool();
        //
        //
        //battlefield
        //
        BattleField.getInstance();


        //
        //Heros and Monsters
        //
        Heros.getInstance().snake();
        Monster.getInstance();
        BattleField.display(GUIController.getMyGraphicContext());

    }
    public static void handleGameStart() {
        Gaming = true;
        GUIController.resetRoundButton(true);
        //
        //Execute Threads
        //

        //Heros
        exec.execute(Grandpa.getInstance());
        for (int i = 0; i < 7; i++) {
            exec.execute(Heros.gourdBrothers.get(i));
        }
        exec.execute(Heros.grandpa);

        //Monsters
        for (int i = 0; i < Monster.numOfMinion; i++) {
            exec.execute(Monster.minions.get(i));
        }
        exec.execute(Monster.SCORPION);

        //xec.shutdown();
    }

    public static void setCurRound(int round) {
        curRound = round;
    }


    public static ExecutorService getExecutor() {
        return exec;
    }


}
