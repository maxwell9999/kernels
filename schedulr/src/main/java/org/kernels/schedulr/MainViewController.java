package org.kernels.schedulr;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import main.java.de.ks.fxcontrols.weekview.WeekView;
import main.java.de.ks.fxcontrols.weekview.WeekViewAppointment;
import javafx.scene.Scene;
import javafx.scene.control.*;
import main.java.de.ks.fxcontrols.*;

public class MainViewController extends VBox {

    @FXML //  fx:id="addPanelButton"
    private Button addPanelButton; // Value injected by FXMLLoader
    @FXML
    private VBox addPane;
    @FXML
    private AnchorPane calendarPane;

    private boolean open;

    private WeekView<Object> weekView;
    private LocalDate begin, end;
    LinkedList<WeekViewAppointment<Object>> retval;

	public MainViewController(WeekView<Object> weekView, LocalDate begin, LocalDate end, LinkedList<WeekViewAppointment<Object>> retval) {
		this.weekView = weekView;
		this.begin = begin;
		this.end = end;
		this.retval = retval;
	}

    @FXML
    public void initialize() {
        assert addPanelButton != null : "fx:id=\"addPanelButton\" was not injected: check your FXML file 'simple.fxml'.";
        assert addPane != null : "fx:id=\"addPane\" was not injected: check your FXML file 'simple.fxml'.";
        assert calendarPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'simple.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected

        open = false;

        addPanelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {

                	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPanel.fxml"));
                	Pane addClassPanel = (Pane) loader.load();
            	    AddPanelController addPanelCtrl = loader.<AddPanelController>getController();
            	    addPanelCtrl.initData(weekView, begin, end, retval);

                	if (open) {
                		addPanelButton.setText("Add A Class");
                		addPane.getChildren().remove(1);
                		open = false;
                	} else {
                		addPanelButton.setText("Close");
                        addPane.getChildren().add(addClassPanel);
                        open = true;
                	}

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addCalendar(WeekView<Object> calendar) {
        GridPane calendarView = (GridPane) calendar;
        calendarPane.setPrefSize(800, 600);
        calendarPane.getChildren().add(calendarView);
    }


}