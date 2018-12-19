
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.org.apache.xalan.internal.utils.SecuritySupport.getResourceAsStream;

public class UserInterface extends Application {
    private int Block_length = 80;
    private int m = 10, n = 14;
    private int rightWidth = 300;
    private int width = n * Block_length + rightWidth;  //1480
    private int height = m * Block_length;      //800
    //private Image background = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\background.jpg");
    private BattleField battleField;
    private Heros heros;
    private Monster monster;
    private static Canvas gameView;
    private static GraphicsContext g;
    @Override
    public void start(Stage primaryStage) {

        //
        //create Canvas
        //
        gameView = new Canvas(width - rightWidth,height);

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
                                                                false,false, false,false)));

        border.setRight(addVBox());
        border.setBackground(background);
        //border.setCenter(addGridPane());
        border.getChildren().addAll(gameView);
        //
        //GameController
        GameController.getInstance();
        GameController.Gaming = true;
        //
        //
        //battlefield
        //
        BattleField.getInstance();



        //
        //Heros and Monsters
        //
        heros = new Heros();
        monster = new Monster();
        heros.snake();
        BattleField.display(g);

        //
        //Execute Threads
        //
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(Grandpa.getInstance());
        for(int i = 0; i < 7; i++) {
            exec.execute(heros.gourdBrothers.get(i));
        }
        exec.shutdown();

        //scene
        Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();

           /* try {
                wait(1000);
                border.setCenter(addGridPane(battleField));

            } catch (Exception e) {
                e.printStackTrace();
            }
            */

    }
    public static GraphicsContext getMyGraphicContext() {
        if(g == null)
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
        int buttonHeight = 90;
        int buttonWidth = 120;
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0,(rightWidth-buttonWidth)/2,0,(rightWidth-buttonWidth)/2));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #336699;");
        //
        //button snake
        //

        Button buttonSnake = new Button("Snake");
        buttonSnake.setPrefSize(buttonWidth, buttonHeight);
        buttonSnake.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.snake();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
                BattleField.display(gameView.getGraphicsContext2D());
            }
        });

        //
        //button crane
        //
        Button buttonCrane = new Button("Crane");
        buttonCrane.setPrefSize(buttonWidth, buttonHeight);
        buttonCrane.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.crane();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
                BattleField.display(gameView.getGraphicsContext2D());
            }
        });

        //
        //button wildGoose
        //
        Button buttonWildGoose = new Button("WildGoose");
        buttonWildGoose.setPrefSize(buttonWidth, buttonHeight);
        buttonWildGoose.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.wildGoose();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
                BattleField.display(gameView.getGraphicsContext2D());
            }
        });
        //
        //button yoke
        //
        Button buttonYoke = new Button("Yoke");
        buttonYoke.setPrefSize(buttonWidth, buttonHeight);
        buttonYoke.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.yoke();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
                BattleField.display(gameView.getGraphicsContext2D());
            }
        });
        //
        //button scale
        //
        Button buttonScale = new Button("Scale");
        buttonScale.setPrefSize(buttonWidth, buttonHeight);
        buttonScale.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.scale();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
                BattleField.display(gameView.getGraphicsContext2D());
            }
        });
        //
        //button diamond
        //
        Button buttonDiamond = new Button("Diamond");
        buttonDiamond.setPrefSize(buttonWidth, buttonHeight);
        buttonDiamond.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.diamond();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
                BattleField.display(gameView.getGraphicsContext2D());
            }
        });
        //
        //button crescent
        //
        Button buttonCrescent = new Button("Crescent");
        buttonCrescent.setPrefSize(buttonWidth, buttonHeight);
        buttonCrescent.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.crescent();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
                BattleField.display(gameView.getGraphicsContext2D());
            }
        });
        //
        //button arrow
        //
        Button buttonArrow = new Button("Arrow");
        buttonArrow.setPrefSize(buttonWidth, buttonHeight);
        buttonArrow.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.arrow();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);

                BattleField.display(gameView.getGraphicsContext2D());
            }
        });

        vbox.getChildren().addAll(buttonSnake,
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