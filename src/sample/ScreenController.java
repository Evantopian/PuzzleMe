package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;


public class ScreenController {

    public void addScene(ActionEvent event, String fxmlName)throws IOException{
        Parent view2 = FXMLLoader.load(getClass().getResource(fxmlName));

        Scene scene2 = new Scene(view2);

        Stage window = new Stage();
        window.setScene(scene2);
        window.show();
    }

    private void switchScenes(ActionEvent event, String fxmlName) throws IOException{
        Parent view2 = FXMLLoader.load(getClass().getResource(fxmlName));

        Scene scene2 = new Scene(view2);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }
}