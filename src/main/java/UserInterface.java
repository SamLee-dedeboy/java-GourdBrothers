
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.org.apache.xalan.internal.utils.SecuritySupport.getResourceAsStream;

public class UserInterface extends Application {
    private int Block_length = 80;
    private int m = 10, n = 14;
    private int rightWidth = 300;
    private int width = n * Block_length + rightWidth;  //1480
    private int height = m * Block_length;      //800
    @Override
    public void init() {
        GameLauncher.initialize();
    }
    @Override
    public void start(Stage primaryStage) {
        //
        //create battlefield
        //

        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Grandpa());
        exec.shutdown();

        //
        //create scene
        //

        //border
        BorderPane border = new BorderPane();
        border.setRight(addVBox());
        border.setCenter(addGridPane());
        //background
        BackgroundImage background = new BackgroundImage(new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\background.jpg"),
                                                         BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                                         BackgroundPosition.DEFAULT,
                                                         new BackgroundSize(width - rightWidth, height,
                                                                 false,false,true,false));
        border.setBackground(new Background(background));

        //scene
        Scene scene = new Scene(border, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.show();
           /* try {
                wait(1000);
                border.setCenter(addGridPane(battleField));

            } catch (Exception e) {
                e.printStackTrace();
            }
            */

    }


    public GridPane addGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);

        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(0, 0, 0, 0));
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

        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);
        buttonCurrent.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                System.out.println("current");
            }
        });

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);

        //vbox.setMargin(buttonProjected, new Insets(0,200,0,0));
        vbox.getChildren().addAll(buttonCurrent,buttonProjected);

        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
}