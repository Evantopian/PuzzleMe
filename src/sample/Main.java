package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;

public class Main  extends Application implements EventHandler {


    private Scene scene, scene2;
    private File puzzleImage;
    private static StackPane root;

    public void start(Stage primaryStage) {
        //Maybe use a menu bar later.


        //Picture of the JavaFile.
        primaryStage.getIcons().add(new Image("/resources/titleIcon.jpg"));

        root = new StackPane();
        root.setId("pane"); //Sets id to pane to set css background img.


        //Adding images & translating them to fit the screen.
        Image image = new Image("/resources/logo.png");
        ImageView logo = new ImageView(image);
        logo.setTranslateY(-120);

        Image image2 = new Image("/resources/sublogo.png");
        ImageView sublogo = new ImageView(image2);
        sublogo.setTranslateY(-55);

        Image image3 = new Image("/resources/playicon.png");
        ImageView playIcon = new ImageView(image3);
        playIcon.setTranslateY(130);

        //Add to the StackPane so the scene displays the images.
        root.getChildren().addAll(logo, sublogo, playIcon);

        scene = new Scene(root, 800, 450);//creates the new scene
        scene.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());

        //Allows the user to select a file with a file filter.
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.setTitle("Select Your Image");
        fileChooser.getExtensionFilters().add(imageFilter); //Adds existing filter to the fileChooser

        Text text = new Text("Dimensions: 3 = 3x3, 4 = 4x4...");
        text.setStyle("-fx-font-weight: bold");
        text.setFill(Color.rgb(255,255,255));
        text.setFont(new Font(20));
        text.setTranslateX(-150);
        text.setTranslateY(75);

        TextField dimensionsText = new TextField();
        dimensionsText.setMaxWidth(50);
        dimensionsText.setTranslateX(25);
        dimensionsText.setTranslateY(75);

        root.getChildren().addAll(text, dimensionsText);


        //GridPane would be much better for next update.
        Pane layout = new Pane();
        layout.setId("layout");

        playIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            puzzleImage = fileChooser.showOpenDialog(primaryStage);


            int num = 0;
            boolean isValid = false;

            if (!dimensionsText.getText().isEmpty()){
                isValid = true;
                num = Integer.parseInt(dimensionsText.getText());
            }

            //If the user gives an incompatible image or an error occurred, this displays the file name & type.
            String imgPath = puzzleImage.toString();
            imgPath = imgPath.substring(imgPath.lastIndexOf(".") +1);

            System.out.println(imgPath);
            if(isValid) {
                //This is included because for some reason I have yet to figure out, fileChooser accepts Google files.
                if (imgPath.equalsIgnoreCase("jpg") || imgPath.equalsIgnoreCase("png") ||
                        imgPath.equalsIgnoreCase("jpeg")) {

                    primaryStage.setScene(scene2);

                    //In the future, include a image class for better modularity.
                    Image hImage = new Image("/resources/hint.png");
                    ImageView hint = new ImageView(hImage);
                    hint.setTranslateX(30);
                    hint.setTranslateY(600);

                    Image mImage = new Image("/resources/moves.png");
                    ImageView moves = new ImageView(mImage);
                    moves.setTranslateX(30);
                    moves.setTranslateY(400);

                    Image tImage = new Image("/resources/time.png");
                    ImageView time = new ImageView(tImage);
                    time.setTranslateX(30);
                    time.setTranslateY(200);



                    /*
                    Text timeText = new Text(String.valueOf(timeElapsed) + " seconds.");
                    timeText.setStyle("-fx-font-weight: bold");
                    timeText.setFill(Color.rgb(255,255,255));
                    timeText.setFont(new Font(20));
                    timeText.setTranslateX(120);
                    timeText.setTranslateY(200);
                     */

                    Image pImage = new Image("/resources/pathText.png");
                    ImageView pathImg = new ImageView(pImage);
                    pathImg.setTranslateX(640);
                    pathImg.setTranslateY(50);

                    layout.getChildren().addAll(hint, moves, time, pathImg);

                    Pieces newPuzzle = new Pieces(puzzleImage.toString(), num, num);
                    Puzzle run = new Puzzle(newPuzzle.getPiecePaths(), scene2, layout);
                    //game.randomize();

                    run.game();

                    primaryStage.setFullScreen(true);

                } else {
                    Label imgAlert = new Label();
                    imgAlert.setText("Oops! It seems like you selected an incorrect image format! (jpg, png, jpeg)");
                    imgAlert.setTextFill(Color.web("#f60300", 1));

                    root.getChildren().add(imgAlert);
                }
            }
        });


        scene2 = new Scene(layout, 800, 450);
        scene2.getStylesheets().addAll(this.getClass().getResource("styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("PuzzleMe!");
        primaryStage.show();
    }

    public static void main(String args[]){
        launch(args);
    }

    //Just opens the explorer with a given event.
    @Override
    public void handle(Event event) {
        /*
        if (event.getSource() == button){
            try {
                Runtime.getRuntime().exec("explorer C:\\bin");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

         */
    }
}