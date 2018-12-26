import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameController implements  Runnable{
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

    private static ExecutorService gameExec;
    private static ExecutorService creatureExec;
    private static boolean GameEnd = false;
    private static boolean roundStart = false;
    private static boolean roundEnd = false;
    private static int passedNum = 0;
    public static void setRoundStart() {
        roundStart = true;
    }
    public static void setRoundEnd() {
        roundEnd = true;
    }
    @Deprecated
    public static void setRoundPassed() {
        roundPassed.set(curRound, true);
        handleRoundEnd();
    }
    @Deprecated
    public static void setRoundFailed() {
        System.out.println("round failed");
        roundPassed.set(curRound, false);
        handleRoundEnd();
    }
    @Deprecated
    public static void stopAllThreads() {
//        exec.shutdown();
//        exec = Executors.newCachedThreadPool();
    }
    private static void handleGameOver() {
        //TODO
    }
    private static void handleRoundEnd() {
        try {
            System.out.println("Round end!");
            boolean passed = Monster.AllDead();
            roundPassed.set(curRound, passed);
            if(passed)
                passedNum++;
            Gaming = false;

            //System.out.println(gameExec.awaitTermination(3, TimeUnit.SECONDS));
            // stopAllThreads();
            //TimeUnit.MILLISECONDS.sleep(1000);
            GUIController.resetRoundButton(false);


            GraphicsContext g = GUIController.getMyGraphicContext();

            g.clearRect(0, 0, Block.size * BattleField.getWidth(), Block.size * BattleField.getHeight());


            Monster.resetRound();


            Heros.resetRound();

            BattleField.display(g);


            //System.out.println("waiting for threads end");
            LogController.saveLog();
            if (!creatureExec.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("oh no");
                creatureExec.shutdownNow();
            } else
                System.out.println("good");
            if(passedNum == 7)
                handleGameOver();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        try {
//            exec.awaitTermination(100, TimeUnit.DAYS);
//            Skill.skillExec.awaitTermination(100, TimeUnit.DAYS);
//            System.out.println("all shut down");
//            BattleField.print();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Skill.skillExec = Executors.newCachedThreadPool();
//        exec = Executors.newCachedThreadPool();
    }
    public static int getCurRound() {
        return curRound;
    }

    public static void initGame() {
        for (int i = 0; i < numOfRound; i++)
            roundPassed.add(false);

        gameExec = Executors.newCachedThreadPool();
        Heros.getInstance().snake();
        BattleField.display(GUIController.getMyGraphicContext());
        gameExec.execute(GameController.getInstance());
        gameExec.shutdown();

    }
    private static void handleRoundStart() {
        System.out.println("round start!");
        creatureExec = Executors.newCachedThreadPool();
        LogController.openNewRoundLog(curRound);
        Gaming = true;
        GUIController.resetRoundButton(true);
        //
        //Execute Threads
        //

        //Heros
        creatureExec.execute(Grandpa.getInstance());
        for (int i = 0; i < 7; i++) {
           creatureExec.execute(Heros.gourdBrothers.get(i));
        }

        //Monsters
        for (int i = 0; i < Monster.numOfMinion; i++) {
            creatureExec.execute(Monster.minions.get(i));
        }
        creatureExec.execute(Monster.SCORPION);
        //cdLatch = new CountDownLatch(7 + Monster.numOfMinion + 2);
        creatureExec.shutdown();
    }

    public static void setCurRound(int round) {
        curRound = round;
    }

    @Deprecated
    public static ExecutorService getExecutor() {
        //return exec;
        return null;
    }

    @Override
    public void run() {
        synchronized (GameController.getInstance()) {
            while (!GameEnd) {
                //Listen for Round start and end
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    if (roundStart) {
                        handleRoundStart();
                        roundStart = false;
                    }
                    if (roundEnd) {
                        handleRoundEnd();
                        roundEnd = false;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
