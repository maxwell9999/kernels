package gui.scheduling;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.ks.fxcontrols.weekview.WeekView;
import de.ks.fxcontrols.weekview.WeekViewAppointment;

public class MainWindow extends Application{
	private static final Logger log = LoggerFactory.getLogger(MainWindow.class);

    private LocalDate begin, end;
    private LinkedList<WeekViewAppointment<Object>> retval;
    private WeekView<Object> weekView;
    private static MainViewController mainViewCtrl;

    public void start(Stage primaryStage) throws IOException {
        BasicConfigurator.configure();

        primaryStage.setTitle("Schedulr");

        weekView = new WeekView<>("Today");
        weekView.setAppointmentResolver(this::getNextEntries);
        //weekView.setOnAppointmentCreation((date, time) -> log.info("Creating new ddappointment beginning at {} {}", date, time));

        mainViewCtrl = new MainViewController(weekView, begin, end, retval);

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
    	loader.setController(mainViewCtrl);
    	Pane root = (Pane) loader.load();

        mainViewCtrl.addCalendar(weekView);

        primaryStage.setScene(createScene(root));

        primaryStage.show();
        selectSchedule(); 
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
      return scene;
    }
    
    private void selectSchedule() throws IOException {
    	Stage stage = new Stage();
		Pane myPane = null;
		FXMLLoader loader = null;
		ScheduleSelectionController controller = new ScheduleSelectionController();
		loader = new FXMLLoader(controller.getClass().getResource("ScheduleSelectionView.fxml"));
		myPane = (Pane) loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		stage.show();
    }

    private void getNextEntries(LocalDate begin, LocalDate end, Consumer<List<WeekViewAppointment<Object>>> consumer) {

    	this.begin = begin;
    	this.end = end;

        LinkedList<WeekViewAppointment<Object>> retval = new LinkedList<>();

        consumer.accept(retval);
        this.retval = retval;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}
	
	public static MainViewController getController() {
		return mainViewCtrl;
	}

}

