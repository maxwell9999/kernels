package org.kernels.schedulr;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.de.ks.fxcontrols.*;
import main.java.de.ks.fxcontrols.weekview.WeekView;
import main.java.de.ks.fxcontrols.weekview.WeekViewAppointment;
import org.kernels.schedulr.AddPanelController;

public class MainWindow extends Application{
	private static final Logger log = LoggerFactory.getLogger(MainWindow.class);

    LocalDate begin, end;
    LinkedList<WeekViewAppointment<Object>> retval;

    public void start(Stage primaryStage) throws IOException {
        BasicConfigurator.configure();

        primaryStage.setTitle("Schedulr");

        WeekView<Object> weekView = new WeekView<>("Today");
        weekView.setAppointmentResolver(this::getNextEntries);
        weekView.setOnAppointmentCreation((date, time) -> log.info("Creating new appointment beginning at {} {}", date, time));

        MainViewController mainViewCtrl = new MainViewController(weekView, begin, end, retval);

    	FXMLLoader loader = new FXMLLoader(
    	        getClass().getResource("MainView.fxml")
    	);
    	loader.setController(mainViewCtrl);
    	Pane root = (Pane) loader.load();

        mainViewCtrl.addCalendar(weekView);

        primaryStage.setScene(createScene(root));

        primaryStage.show();
    }

    private Pane loadMainView(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainViewPane = (Pane) loader.load(
                getClass().getResourceAsStream(path));

        return mainViewPane;
    }

    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
      return scene;
    }

    private void getNextEntries(LocalDate begin, LocalDate end, Consumer<List<WeekViewAppointment<Object>>> consumer) {
        //ThreadLocalRandom random = ThreadLocalRandom.current();

    	this.begin = begin;
    	this.end = end;

        LinkedList<WeekViewAppointment<Object>> retval = new LinkedList<>();
        //LocalDate firstDayOfWeek = begin;

//        for (int i = 0; i < 7; i++) {
//          LocalDate current = firstDayOfWeek.plusDays(i);
//          LocalTime time = LocalTime.of(random.nextInt(6, 18), random.nextInt(3) * 15);
//
//          int minutes = Math.max(15, random.nextInt(12) * 15);
//          Duration duration = Duration.ofMinutes(minutes);
//          LocalDateTime localDateTime = LocalDateTime.of(current, time);
//
//          BiPredicate<LocalDate, LocalTime> newTimePossiblePredicate = (newDate, newTime) -> {
//            if (newTime == null) {
//              return true;
//            }
//            if (newTime.getHour() > 6 && newTime.getHour() < 22) {
//              return true;
//            } else {
//              log.info("Wrong time {}", newTime);
//              return false;
//            }
//          };
//
//          WeekViewAppointment<Object> timedAppointment = new WeekViewAppointment<>("test entry" + i + " " + minutes + "m", localDateTime, duration);
//          timedAppointment.setChangeStartCallback((newDate, newTime) -> {
//            log.info("{} now starts on {} {}", timedAppointment.getTitle(), newDate, newTime);
//          });
//          timedAppointment.setNewTimePossiblePredicate(newTimePossiblePredicate);
//          retval.add(timedAppointment);
//          for (int j = 0; j < random.nextInt(1, 4); j++) {
//            WeekViewAppointment<Object> dayAppointment = new WeekViewAppointment<>(j + " test day spanning entry" + i + " " + minutes + "m", localDateTime.toLocalDate(), duration);
//            dayAppointment.setChangeStartCallback((newDate, newTime) -> {
//              log.info("{} now starts on {} {}", dayAppointment.getTitle(), newDate, newTime);
//            });
//            dayAppointment.setNewTimePossiblePredicate(newTimePossiblePredicate);
//            retval.add(dayAppointment);
//          }
//        }
        consumer.accept(retval);
        this.retval = retval;
      }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}

