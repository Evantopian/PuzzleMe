package sample;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Pieces extends Puzzle{

    private ArrayList<String> pathOfPieces = new ArrayList<String>();
    private int row, col;
    private int eWidth, eHeight;

    Pieces(String pathOfImg, int xPx, int yPx){
        super();
        this.row = xPx;
        this.col = yPx;

        try {

            BufferedImage originalImage = ImageIO.read(new File(pathOfImg));
            //total width and total height of the given image
            int tWidth = originalImage.getWidth();
            int tHeight = originalImage.getHeight();

            System.out.println("Image Dimension: " + tWidth + "x" + tHeight);

            //width and height of each piece
            eWidth = tWidth/col;
            eHeight = tHeight/row;

            int x = 0;
            int y = 0;

            String userName = System.getProperty("user.name");

            int count = 0;
            for (int i = 0; i < row; i++) {
                x = 0;
                for (int j = 0; j < col; j++) {
                    try {
                        BufferedImage subImage = originalImage.getSubimage(x, y, eWidth, eHeight);

                        //Add the path of the files to the array.
                        File outputFile = new File("C:/Users/" + userName + "/AppData/Local/Temp/"
                                + i+j + ".jpg");
                        ImageIO.write(subImage, "jpg", outputFile);

                        x += eWidth;
                        pathOfPieces.add("C:/Users/" + userName + "/AppData/Local/Temp/" + i+j + ".jpg");

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Image incompatible...");
                    }

                    count++;
                }

                y += eHeight;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected ArrayList<String> getPiecePaths(){
        return pathOfPieces;
    }

    int getHeight(){
        return eHeight;
    }

    int getWidth(){
        return eWidth;
    }

    private void deleteImgs(){
        //This is not necessary because the program already overwrites any new files, however its a icing on the cake.
        //Delete the files when the puzzle game meets a end or another case scenario.
        for (int i = 0; i < row*col; i++){

        }
    }




}