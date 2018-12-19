public class GameController {
    public static GameController instance = null;
    public static boolean Gaming = false;
    private GameController(){}

    public synchronized static GameController getInstance() {
        if(instance == null)
            instance = new GameController();
        return instance;
    }
}
