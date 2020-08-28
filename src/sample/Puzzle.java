package sample;

import com.sun.javafx.geom.Rectangle;
import javafx.animation.TranslateTransition;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Puzzle {

    private ArrayList<String> path;
    private Scene scene;
    private Pane root;

    private int negXBounds = (int) Screen.getPrimary().getBounds().getMaxX() * 3/7;
    private int yBounds = (int) Screen.getPrimary().getBounds().getMaxY() * 1/5;

    private final int absX = negXBounds;
    private final int absY = yBounds;

    private HashMap<String, ImageView> imgPos = new HashMap<>();
    final Duration DURATION = Duration.millis(100);

    private int moves = 0;

    Puzzle(ArrayList<String> imgPaths, Scene s, Pane r){
        this.path = imgPaths;
        this.scene = s;
        this.root = r;
    }

    public Puzzle() {
    }

    void game() {
        randomize();

        int scale = ((int) Math.sqrt((path.size())));
        System.out.println(negXBounds + ":" + yBounds);
        /*
        Creating the border:
        Since x screen bounds is -300, 1920/2 + |-300| < 1870
        Since y screen bounds is +480, 1080/2 + |480| < 1020
        - Not accurate since, but for reference (0,0) = (1920/2, 1080/2)
        */
        //Path:
        //Checking resolution of screen.
        //int screenSizeX = (int) Screen.getPrimary().getBounds().getMaxX();
        //int screenSizeY = (int) Screen.getPrimary().getBounds().getMaxY();

        int subX = (((int) Screen.getPrimary().getBounds().getMaxX() - absX) / scale);
        int subY = (((int) Screen.getPrimary().getBounds().getMaxY() * 4 / 7) / scale);

        for (int i = 0; i < path.size(); i++) { //Cannot use enhanced for-loop
            System.out.println(path.get(i));

            File file = new File(path.get(i));

            Image newImage = new Image(file.toURI().toString());

            ImageView newPiece = new ImageView(newImage);

            newPiece.setFitWidth(subX);
            newPiece.setFitHeight(subY);

            newPiece.setTranslateX(negXBounds);
            newPiece.setTranslateY(yBounds);
            negXBounds += subX;

            imgPos.put(path.get(i), newPiece);

            if ((i + 1) % scale == 0) {
                negXBounds = absX;
                yBounds += subY;
            }
            root.getChildren().add(newPiece);
        }

        int rightBoundX = (int) Screen.getPrimary().getBounds().getMaxX();
        //absX is equal to leftBoundX .
        int bottomBoundY = (int) Screen.getPrimary().getBounds().getMaxY();
        //absY is equal to topBoundY.

        //[Selected img]: subX + getX, getX - subX, getY - subY, getY + subY.

        /*
        for (int i = 0; i < imgPos.size(); i++){
            int collisions = 0;
            String directory = path.get(i);

            int[] temp = getBounds(imgPos.get(directory)); //0: x, 1: y, 2: w, 3: h
            ImageView tempImage = imgPos.get(path.get(i+1));

        }
        */

        for (int i = 0; i < imgPos.size()-1; i++) {

            boolean valid = false;
            String directory = path.get(i);

            if (getBounds(imgPos.get(directory)).intersects(getBounds(imgPos.get(path.get(i+1))))){
                valid = true;
            }


            boolean finalValid = valid;
            imgPos.get(directory).addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                moveOnKeyPressed(scene, imgPos.get(directory), subX, subY, finalValid);
            });

        }
    }

        //moveOnKeyPressed(scene, imgPos.get(directory), subX, subY);




    //Change the signature of the method for Rectangle2D and return to use Rectangle2D.
    private Rectangle2D getBounds(ImageView checkImg){
        int posX = (int) checkImg.getX();
        int posY = (int) checkImg.getY();
        int width = (int) checkImg.getFitWidth();
        int height = (int) checkImg.getFitHeight();

        return new Rectangle2D(posX, posY, width, height);
    }

    private boolean collides(Rectangle2D rec, Rectangle2D rec2){
        if (rec.intersects(rec2)){
            return false;
        }
        return true;
    }

    private void moveOnKeyPressed(Scene scene, ImageView img, int moveX, int moveY, boolean valid)
    {

        final TranslateTransition transition = new TranslateTransition(DURATION, img);
        scene.setOnKeyPressed(e -> {
            switch(e.getCode())
            {

                case UP:
                {
                    if (valid) {
                        transition.setFromX(img.getTranslateX());
                        transition.setFromY(img.getTranslateY());
                        transition.setToX(img.getTranslateX());
                        transition.setToY(img.getTranslateY() - moveY);
                        transition.playFromStart();
                        moves++;
                    }

                } break;
                case DOWN:
                {
                    if (valid) {
                        transition.setFromX(img.getTranslateX());
                        transition.setFromY(img.getTranslateY());
                        transition.setToX(img.getTranslateX());
                        transition.setToY(img.getTranslateY() + moveY);
                        transition.playFromStart();
                        moves++;
                    }

                } break;
                case LEFT:
                {
                    if(valid) {
                        transition.setFromX(img.getTranslateX());
                        transition.setFromY(img.getTranslateY());
                        transition.setToX(img.getTranslateX() - moveX);
                        transition.setToY(img.getTranslateY());
                        transition.playFromStart();
                        moves++;
                    }

                } break;
                case RIGHT:
                {
                    if (valid) {
                        transition.setFromX(img.getTranslateX());
                        transition.setFromY(img.getTranslateY());
                        transition.setToX(img.getTranslateX() + moveX);
                        transition.setToY(img.getTranslateY());
                        transition.playFromStart();
                        moves++;
                    }

                } break;

                default:break;

            }
        });
    }


    protected void randomize(){
        //This can also be done with a 2D array with nested for loops.
        Collections.shuffle(path);
    }

    //Getters
    private ArrayList<String> getImgs(){
        return path;
    }

    Pane getRoot(){
        return root;
    }

    int getAbsX(){
        return absX;
    }

    int getAbsY(){
        return absY;
    }

    int getMoves(){return moves;}

}
