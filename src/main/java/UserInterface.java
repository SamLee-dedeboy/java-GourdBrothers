
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
        exec.execute(heros.gourdBrothers.get(1));
        exec.execute(heros.gourdBrothers.get(3));
        exec.execute(heros.gourdBrothers.get(4));
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

            /*
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ImageView blockImage = new ImageView();
                    if(battleField.at(j, i).get() != null) {
                        blockImage.setImage(battleField.at(j, i).get().getImage());
                        blockImage.setFitWidth(Block_length);
                        blockImage.setFitHeight(Block_length);
                        grid.add(blockImage, i, j);
                    }
             */

//                    ImageView huluwa = new ImageView();
//                    huluwa.setImage(new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\grandpa.jpg"));
//                    huluwa.setFitHeight(Block_length);
//                    huluwa.setFitWidth(Block_length);
//                    grid.add(huluwa, 5, 5);




        return grid;
    }

    //right bar
    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0,100,0,100));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #336699;");

        //
        //button snake
        //

        Button buttonSnake = new Button("Snake");
        buttonSnake.setPrefSize(100, 100);
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
        buttonCrane.setPrefSize(100, 100);
        buttonCrane.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                monster.crane();
                g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
                BattleField.display(gameView.getGraphicsContext2D());
            }
        });
        vbox.getChildren().addAll(buttonSnake, buttonCrane);

        return vbox;
    }


    public static void main(String[] args) {
        launch(args);
    }
}