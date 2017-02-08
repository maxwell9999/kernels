package org.kernels.schedulr;

import javafx.scene.layout.VBox;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AddPanelController extends VBox {

    public AddPanelController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddPanel.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
