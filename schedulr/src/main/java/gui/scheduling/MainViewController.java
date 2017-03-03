package gui.scheduling;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import de.ks.fxcontrols.weekview.WeekView;
import de.ks.fxcontrols.weekview.WeekViewAppointment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainViewController extends VBox {

    @FXML //  fx:id="addPanelButton"
    private Button addPanelButton; // Value injected by FXMLLoader
    @FXML
    private Button editPanelButton;
    @FXML
    private Button rmPanelButton;
    @FXML
    private Button closeAddPanelButton = new Button();
    @FXML
    private VBox addPane;
    @FXML
    private AnchorPane calendarPane;
    @FXML
    private SplitPane splitPane;

    private boolean open;

    private WeekView<Object> weekView;
    private LocalDate begin, end;
    private LinkedList<WeekViewAppointment<Object>> retval;
    private String titleString;

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
        		titleString = "Add a Class";
        		handleClassButtonPress(event);
        	}
        });
        editPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Edit a Class";
        		handleClassButtonPress(event);
        	}
        });
        rmPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Remove a Class";
        		handleClassButtonPress(event);
        	}
        });

        closeAddPanelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
	           	if (open) {
		    		//addPanelButton.setText("Add Q Class");
		    		addPane.getChildren().remove(0);
		    		addPane.getChildren().remove(0);
		    		splitPane.setDividerPositions(0.1005567928730512, 0.9905567928730512);
		    		open = false;
	           	}
            }
        });

    }

	public void handleClassButtonPress(ActionEvent event) {
        try {

        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPanel.fxml"));
        	Pane addClassPanel = (Pane) loader.load();
    	    AddPanelController addPanelCtrl = loader.<AddPanelController>getController();
    	    addPanelCtrl.initData(titleString, weekView, begin, end, retval);

    	    addPane.getChildren().add(closeAddPanelButton);
    		closeAddPanelButton.setText("Close");
            addPane.getChildren().add(addClassPanel);
            open = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCalendar(WeekView<Object> calendar) {
        GridPane calendarView = (GridPane) calendar;
        calendarPane.setPrefSize(800, 600);
        calendarPane.getChildren().add(calendarView);
    }


}