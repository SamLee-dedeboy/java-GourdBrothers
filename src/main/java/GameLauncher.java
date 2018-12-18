import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.awt.*;

public class GameLauncher {
    Canvas gameView;
    Parent root;
    BorderPane border;
    GraphicsContext g;
        private int Block_length = 80;
        private int m = 10, n = 14;
        private int rightWidth = 300;
        private int width = n * Block_length + rightWidth;  //1480
        private int height = m * Block_length;      //800
    public void initialize() {
        //
        //root
        //

        //
        //Canvas
        //
        gameView = new Canvas();
        //
        //border Pane
        //
        border = new BorderPane();
        border.setRight(addVBox());
        border.setCenter(addGridPane());
        //
        //background
        //
        BackgroundImage background = new BackgroundImage(new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\background.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(width - rightWidth, height,
                        false,false,true,false));
        border.setBackground(new Background(background));
        //
        //Graphics
        //
        return ;
    }
    public GridPane addGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(0);

        grid.setGridLinesVisible(true);
        grid.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));
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

        javafx.scene.control.Button buttonCurrent = new javafx.scene.control.Button("Current");
        buttonCurrent.setPrefSize(100, 20);
        buttonCurrent.setOnAction(new EventHandler<ActionEvent>() {
            //@Override
            public void handle(ActionEvent event) {
                System.out.println("current");
            }
        });

        javafx.scene.control.Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);

        //vbox.setMargin(buttonProjected, new Insets(0,200,0,0));
        vbox.getChildren().addAll(buttonCurrent,buttonProjected);

        return vbox;
    }

}
