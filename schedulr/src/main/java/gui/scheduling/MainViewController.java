package gui.scheduling;

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
import de.ks.fxcontrols.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import de.ks.fxcontrols.weekview.*;
import de.ks.fxcontrols.cell.*;

public class MainViewController extends VBox {

    @FXML
    private Button addPanelButton;
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
    @FXML
    private ScrollPane rightScrollPane;

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
        editRmButtonsEnabled(false);

        addPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Add a Class";
        		editRmButtonsEnabled(false);
        		handleClassButtonPress(event);
        	}
        });
        editPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		titleString = "Edit a Class";

        		WeekViewAppointment<Object> selected = getFocusedNode();

        		if (selected != null) {
	        		addPanelButton.setDisable(true);
	        		rmPanelButton.setDisable(true);
	        		handleClassButtonPress(event);
        		} else {
        			System.out.println("Please select a class to edit.");
        		}
        	}
        });
        rmPanelButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		WeekViewAppointment<Object> selected = getFocusedNode();
        		if (selected != null) {
        			retval.remove(selected);
        			weekView.recreateEntries(retval);
        		}
        		editRmButtonsEnabled(false);
        	}
        });

        closeAddPanelButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
	           	if (open) {
		    		addPane.getChildren().remove(0);
		    		addPane.getChildren().remove(0);
		    		splitPane.setDividerPositions(0.1505567928730512, 0.9905567928730512);
		    		open = false;
	        		addPanelButton.setDisable(open);
	           	}
            }
        });

//        MouseEvent e;
//        Object content = e.getDragboard().getContent(WeekView.getDataFormat());
//        System.out.println(content);
    }

    public WeekViewAppointment<Object> getFocusedNode() {
    	for (WeekViewAppointment<Object> appointment : retval) {
    		if (appointment.getFocused()) {
    			System.out.println("focused " + appointment.toString());
    			return appointment;
    		}
    	}
    	return null;
    }

	public void handleClassButtonPress(ActionEvent event) {
        if (open == false) {
        	try {

	        	FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPanel.fxml"));
	        	Pane addClassPanel = (Pane) loader.load();
	    	    AddPanelController addPanelCtrl = loader.<AddPanelController>getController();
	    	    addPanelCtrl.initData(titleString, weekView, begin, end, retval, closeAddPanelButton);

	    	    addPane.getChildren().add(closeAddPanelButton);
	    		closeAddPanelButton.setText("Close");
	            addPane.getChildren().add(addClassPanel);
	            splitPane.setDividerPositions(0.1505567928730512, 0.7305567928730512);
	            open = true;

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }
    }

	public void editRmButtonsEnabled(Boolean status){
        editPanelButton.setDisable(!status);
        rmPanelButton.setDisable(!status);
	}

    public void addCalendar(WeekView<Object> calendar) {
        GridPane calendarView = (GridPane) calendar;
        calendarPane.setPrefSize(800, 600);
        calendarPane.getChildren().add(calendarView);
    }


}