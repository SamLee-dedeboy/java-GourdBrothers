import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameController {
    //
    //Singleton
    //
    public static GameController instance = new GameController();
    public static synchronized GameController getInstance() {
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
        System.out.println("round failed");
        roundPassed.set(curRound, false);
        handleRoundEnd();
    }
    public static void stopAllThreads() {
        exec.shutdown();
        exec = Executors.newCachedThreadPool();
    }
    public static void handleRoundEnd() {
        System.out.println("Round end!");

        Gaming = false;
        stopAllThreads();
        //TimeUnit.MILLISECONDS.sleep(1000);
        GUIController.resetRoundButton(false);


        GraphicsContext g = GUIController.getMyGraphicContext();

        g.clearRect(0, 0, Block.size * BattleField.getWidth(), Block.size * BattleField.getHeight());


        Monster.resetRound();


        Heros.resetRound();

        BattleField.display(g);


        //Skill.skillExec.awaitTermination(3000, TimeUnit.MILLISECONDS);


        try {
            LogController.saveLog();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        exec.shutdownNow();
        Skill.skillExec.shutdownNow();
        try {
            exec.awaitTermination(100, TimeUnit.DAYS);
            Skill.skillExec.awaitTermination(100, TimeUnit.DAYS);
            System.out.println("all shut down");
            BattleField.print();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        Skill.skillExec = Executors.newCachedThreadPool();
        exec = Executors.newCachedThreadPool();
    }
    public static int getCurRound() {
        return curRound;
    }

    public static void initGame() {
        for (int i = 0; i < numOfRound; i++)
            roundPassed.add(false);

        exec = Executors.newCachedThreadPool();
        Heros.getInstance().snake();
        BattleField.display(GUIController.getMyGraphicContext());

    }
    public static void handleRoundStart() {
        LogController.openNewRoundLog(curRound);
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

        //Monsters
        for (int i = 0; i < Monster.numOfMinion; i++) {
            exec.execute(Monster.minions.get(i));
        }
        exec.execute(Monster.SCORPION);
        //cdLatch = new CountDownLatch(7 + Monster.numOfMinion + 2);
        //xec.shutdown();
    }

    public static void setCurRound(int round) {
        curRound = round;
    }


    public static ExecutorService getExecutor() {
        return exec;
    }


}
