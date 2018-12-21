
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;


public class UserInterface extends Application {
    private int Block_length = 80;
    private int m = 10, n = 14;
    private int rightWidth = 300;
    private int width = n * Block_length + rightWidth;  //1480
    private int height = m * Block_length;      //800

    private Heros heros = Heros.getInstance();
    private Monster monster = Monster.getInstance();
    private static Canvas gameView;
    private static GraphicsContext g;
    //
    //declare buttons
    //
    private static Button buttonSnake;
    private static Button buttonCrane;
    private static Button buttonWildGoose;
    private static Button buttonYoke;
    private static Button buttonScale;
    private static Button buttonDiamond;
    private static Button buttonCrescent;
    private static Button buttonArrow;
    private static Button buttonStart;
    private static Button buttonEnd = new Button("EndRound");


    @Override
    public void start(Stage primaryStage) {
        //
        //create Canvas
        //
        gameView = new Canvas(width - rightWidth, height);

        //
        //GraphicsContext
        //
        g = gameView.getGraphicsContext2D();

        //
        //border and background
        //
        BorderPane border = new BorderPane();
        Background background = new Background(new BackgroundImage(
                new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\background.jpg"),
                null, null, BackgroundPosition.DEFAULT,
                new BackgroundSize(width - rightWidth, height,
                        false, false, false, false)));
        border.setRight(addVBox());
        border.setBackground(background);
        //border.setCenter(addGridPane());
        border.getChildren().addAll(gameView);

        //
        //GameController
        //
        GameController.getInstance();

        //
        //
        //battlefield
        //
        BattleField.getInstance();


        //
        //Heros and Monsters
        //
        Heros.getInstance().snake();
        BattleField.display(g);


        //scene
        Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void setRoundButtonDisable() {
        buttonSnake.setDisable(GameController.roundPassed.get(0));
        buttonCrane.setDisable(GameController.roundPassed.get(1));
        buttonWildGoose.setDisable(GameController.roundPassed.get(2));
        buttonYoke.setDisable(GameController.roundPassed.get(3));
        buttonScale.setDisable(GameController.roundPassed.get(4));
        buttonDiamond.setDisable(GameController.roundPassed.get(5));
        buttonCrescent.setDisable(GameController.roundPassed.get(6));
        buttonArrow.setDisable(GameController.roundPassed.get(7));
    }

    public static GraphicsContext getMyGraphicContext() {
        if (g == null)
            g = gameView.getGraphicsContext2D();
        return g;
    }

    public GridPane addGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setGridLinesVisible(true);
        grid.setPadding(new javafx.geometry.Insets(0, 100, 0, 100));
        return grid;
    }

    //right bar
    public VBox addVBox() {
        int buttonHeight = 70;
        int buttonWidth = 120;
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, (rightWidth - buttonWidth) / 2, 0, (rightWidth - buttonWidth) / 2));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #336699;");


        //
        //
        //button snake
        //

        buttonSnake = new Button("Snake");
        buttonSnake.setPrefSize(buttonWidth, buttonHeight);
        buttonSnake.setOnAction((ActionEvent event) -> {
            monster.snake();
            g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
            BattleField.display(gameView.getGraphicsContext2D());
            GameController.setCurRound(0);
        });

        //
        //button crane
        //
        buttonCrane = new Button("Crane");
        buttonCrane.setPrefSize(buttonWidth, buttonHeight);
        buttonCrane.setOnAction((ActionEvent event) -> {
            monster.crane();
            g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
            BattleField.display(gameView.getGraphicsContext2D());
            GameController.setCurRound(1);
        });

        //
        //button wildGoose
        //
        buttonWildGoose = new Button("WildGoose");
        buttonWildGoose.setPrefSize(buttonWidth, buttonHeight);
        buttonWildGoose.setOnAction((ActionEvent event) -> {
            monster.wildGoose();
            g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
            BattleField.display(gameView.getGraphicsContext2D());
            GameController.setCurRound(2);
        });
        //
        //button yoke
        //
        buttonYoke = new Button("Yoke");
        buttonYoke.setPrefSize(buttonWidth, buttonHeight);
        buttonYoke.setOnAction((ActionEvent event) -> {
            monster.yoke();
            g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
            BattleField.display(gameView.getGraphicsContext2D());
            GameController.setCurRound(3);
        });
        //
        //button scale
        //
        buttonScale = new Button("Scale");
        buttonScale.setPrefSize(buttonWidth, buttonHeight);
        buttonScale.setOnAction((ActionEvent event) -> {
            monster.scale();
            g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
            BattleField.display(gameView.getGraphicsContext2D());
            GameController.setCurRound(4);
        });
        //
        //button diamond
        //
        buttonDiamond = new Button("Diamond");
        buttonDiamond.setPrefSize(buttonWidth, buttonHeight);
        buttonDiamond.setOnAction((ActionEvent event) -> {
            monster.diamond();
            g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
            BattleField.display(gameView.getGraphicsContext2D());
            GameController.setCurRound(5);
        });
        //
        //button crescent
        //
        buttonCrescent = new Button("Crescent");
        buttonCrescent.setPrefSize(buttonWidth, buttonHeight);
        buttonCrescent.setOnAction((ActionEvent event) -> {
            monster.crescent();
            g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
            BattleField.display(gameView.getGraphicsContext2D());
            GameController.setCurRound(6);
        });
        //
        //button arrow
        //
        buttonArrow = new Button("Arrow");
        buttonArrow.setPrefSize(buttonWidth, buttonHeight);
        buttonArrow.setOnAction((ActionEvent event) -> {
            monster.arrow();
            g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);

            BattleField.display(gameView.getGraphicsContext2D());
            GameController.setCurRound(7);
        });
        //
        //button start
        //
        buttonStart = new Button("StartRound");
        buttonStart.setPrefSize(buttonWidth, buttonHeight);
        buttonStart.setOnAction((ActionEvent event) -> {
            GameController.handleGameStart();

            setRoundButtonDisable();
            buttonEnd.setDisable(false);
            buttonStart.setDisable(true);

        });

        //
        //button end
        //
        buttonEnd.setPrefSize(buttonWidth, buttonHeight);
        buttonEnd.setDisable(true);
        buttonEnd.setOnAction((ActionEvent event) -> {
            GameController.handleGameEnd();
            setRoundButtonDisable();
            buttonStart.setDisable(false);
            buttonEnd.setDisable(true);
        });

        vbox.getChildren().addAll(buttonStart,
                buttonEnd,
                buttonSnake,
                buttonCrane,
                buttonWildGoose,
                buttonYoke,
                buttonScale,
                buttonDiamond,
                buttonCrescent,
                buttonArrow);

        return vbox;
    }


    public static void main(String[] args) {
        launch(args);
    }
}