package org.kernels.schedulr;

import java.io.IOException;
import javafx.fxml.*;
import javafx.scene.Node;

public class Navigator {

	private final String addPanel;

	public Navigator() throws IOException {
	    this.addPanel = "AddPanel.fxml";
	}

	public String getAddPanel() {
	    return addPanel;
	}

	private static AddPanelController addPanelController;

	public static void loadPane(String fxml) {
	    try {
	        addPanelController.setPane(
	                (Node) FXMLLoader.load(Navigator.class.getResource(fxml)));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}