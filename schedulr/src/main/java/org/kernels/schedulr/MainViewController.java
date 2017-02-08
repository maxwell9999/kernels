package org.kernels.schedulr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class MainViewController extends VBox implements Initializable {

    @FXML //  fx:id="addPanelButton"
    private Button addPanelButton; // Value injected by FXMLLoader
    @FXML
    private AnchorPane addPane;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert addPanelButton != null : "fx:id=\"addPanelButton\" was not injected: check your FXML file 'simple.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

        addPanelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    Pane newPane = (Pane) FXMLLoader.load(getClass().getResource("AddPanel.fxml"));

                    addPane.getChildren().add(newPane);

                    System.out.println("test");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}