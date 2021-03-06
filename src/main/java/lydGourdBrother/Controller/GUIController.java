package lydGourdBrother.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import lydGourdBrother.GamingCollections.BattleField;
import lydGourdBrother.GamingCollections.Monster;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {
    private static GUIController instance;
    //constructor only called by fxml loader
    public GUIController() {
        //a special Singleton, see the comment below
        instance = this;
    }
    public synchronized static GUIController getInstance() {
        // instance should have been initialized by fxml loader.
        // if not, and even if you add instance = new lydGourdBrother.Controller.GUIController
        // here, the program still won't work out right. Because by
        // add that line you get a different copy of lydGourdBrother.Controller.GUIController,
        // which is not loaded by fxml loader, therefore components
        // in this copy are not initialized as well, and the program
        // will not work properly
        return instance;
    }
    @FXML Canvas gameView;
    private GraphicsContext g;
    //
    //declare buttons
    //
    @FXML Button buttonSnake;
    @FXML Button buttonCrane;
    @FXML Button buttonWildGoose;
    @FXML Button buttonYoke;
    @FXML Button buttonScale;
    @FXML Button buttonDiamond;
    @FXML Button buttonCrescent;
    @FXML Button buttonArrow;
    @FXML Button buttonStart;
    @FXML Button buttonEnd;

    public void initialize(URL url, ResourceBundle rb) {
        g = gameView.getGraphicsContext2D();
        GameController.initGame();
    }
    static void setAllButtonDisable() {
        instance.buttonSnake.setDisable(true);
        instance.buttonCrane.setDisable(true);
        instance.buttonWildGoose.setDisable(true);
        instance. buttonYoke.setDisable(true);
        instance.buttonScale.setDisable(true);
        instance.buttonDiamond.setDisable(true);
        instance.buttonCrescent.setDisable(true);
        instance.buttonArrow.setDisable(true);
        instance.buttonStart.setDisable(true);
        instance.buttonEnd.setDisable(true);
    }
    private static void setRoundButtonDisable(boolean start) {
        if(start) {
            instance.buttonSnake.setDisable(true);
            instance.buttonCrane.setDisable(true);
            instance.buttonWildGoose.setDisable(true);
            instance. buttonYoke.setDisable(true);
            instance.buttonScale.setDisable(true);
            instance.buttonDiamond.setDisable(true);
            instance.buttonCrescent.setDisable(true);
            instance.buttonArrow.setDisable(true);
        }else {
            instance.buttonSnake.setDisable(GameController.roundPassed.get(0));
            instance.buttonCrane.setDisable(GameController.roundPassed.get(1));
            instance.buttonWildGoose.setDisable(GameController.roundPassed.get(2));
            instance.buttonYoke.setDisable(GameController.roundPassed.get(3));
            instance.buttonScale.setDisable(GameController.roundPassed.get(4));
            instance.buttonDiamond.setDisable(GameController.roundPassed.get(5));
            instance.buttonCrescent.setDisable(GameController.roundPassed.get(6));
            instance.buttonArrow.setDisable(GameController.roundPassed.get(7));
        }
    }

    public static GraphicsContext getMyGraphicContext() {
        if (instance.g == null)
            instance.g = instance.gameView.getGraphicsContext2D();
        return instance.g;
    }

    @FXML
    private void handleButtonSnake(ActionEvent event) {
        Monster.getInstance().snake();

        g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
        BattleField.display(gameView.getGraphicsContext2D());
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("Round1.jpg").toString()),
                400, 0, 450,150);
        instance.buttonStart.setDisable(false);

        GameController.setCurRound(0);
    }

    @FXML
    private void handleButtonCrane(ActionEvent event) {
        Monster.getInstance().crane();
        g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
        BattleField.display(gameView.getGraphicsContext2D());
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("Round2.jpg").toString()),
                400, 0, 450,150);
        instance.buttonStart.setDisable(false);

        GameController.setCurRound(1);
    }

    @FXML
    private void handleButtonWildGoose(ActionEvent event) {
        Monster.getInstance().wildGoose();
        g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
        BattleField.display(gameView.getGraphicsContext2D());
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("Round3.jpg").toString()),
                400, 0, 450,150);
        instance.buttonStart.setDisable(false);

        GameController.setCurRound(2);
    }

    @FXML
    private void handleButtonYoke(ActionEvent event) {
        Monster.getInstance().yoke();
        g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
        BattleField.display(gameView.getGraphicsContext2D());
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("Round4.jpg").toString()),
                400, 0, 450,150);
        instance.buttonStart.setDisable(false);

        GameController.setCurRound(3);
    }

    @FXML
    private void handleButtonScale(ActionEvent event) {
        Monster.getInstance().scale();
        g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
        BattleField.display(gameView.getGraphicsContext2D());
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("Round5.jpg").toString()),
                400, 0, 450,150);
        instance.buttonStart.setDisable(false);

        GameController.setCurRound(4);
    }

    @FXML
    private void handleButtonDiamond(ActionEvent event) {
        Monster.getInstance().diamond();
        g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
        BattleField.display(gameView.getGraphicsContext2D());
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("Round6.jpg").toString()),
                400, 0, 450,150);
        instance.buttonStart.setDisable(false);

        GameController.setCurRound(5);
    }

    @FXML
    private void handleButtonCrescent(ActionEvent event) {
        Monster.getInstance().crescent();
        g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
        BattleField.display(gameView.getGraphicsContext2D());
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("Round7.jpg").toString()),
                400, 0, 450,150);
        instance.buttonStart.setDisable(false);

        GameController.setCurRound(6);
    }

    @FXML
    private void handleButtonArrow(ActionEvent event) {
        Monster.getInstance().arrow();
        g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);

        BattleField.display(gameView.getGraphicsContext2D());
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("Round8.jpg").toString()),
                400, 0, 450,150);
        instance.buttonStart.setDisable(false);

        GameController.setCurRound(7);
    }

    @FXML
    private void handleButtonStart(ActionEvent event) {
        g.clearRect(400,0,450,150);
        instance.buttonStart.setDisable(true);
        instance.buttonEnd.setDisable(false);

        GameController.setRoundStart();
    }

    @FXML
    private void handleButtonEnd(ActionEvent event) {
        g.drawImage(new Image(instance.getClass().getClassLoader().getResource("EndRound.jpg").toString()),
                300, 0, 597,134);
        instance.buttonStart.setDisable(true);
        instance.buttonEnd.setDisable(true);

        GameController.setRoundEnd();
    }
    static void setButtonEndDisable(boolean flag) {
        instance.buttonEnd.setDisable(flag);
    }
    static void resetRoundButton(boolean start) {
        setRoundButtonDisable(start);

    }
}
