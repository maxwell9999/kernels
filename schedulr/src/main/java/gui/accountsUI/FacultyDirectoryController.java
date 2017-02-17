package gui.accountsUI;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import core.accounts.AccountManager;
import core.accounts.User;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * UI for viewing faculty directory.
 * @author sarahpadlipsky
 * @version February 14, 2017
 */
public class FacultyDirectoryController extends Application {

	// TableView for the list of Faculty Members.
    private final TableView<FacultyMember> table = new TableView<FacultyMember>();
    // List to populate the listview.
    final ObservableList<FacultyMember> data = FXCollections.observableArrayList();
    // Used to pass itself along.
    final private FacultyDirectoryController controller;
    
    public FacultyDirectoryController() {
    	updateList();
    	controller = this;
    }
    
    //TODO(Sarah) : Delete because temporary - just for quick building purposes.
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {		
		Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(500);
        stage.setHeight(500);
 
        final Label label = new Label("Faculty Directory");
        label.setFont(new Font("Optima", 20));
 
        table.setEditable(false);
        table.setMinWidth(400);
        
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        TableColumn loginCol = new TableColumn("Login");
        loginCol.setMinWidth(200);

       
        lastNameCol.setCellValueFactory(
        	    new PropertyValueFactory<String, String>("lastName")
        	);
        
        firstNameCol.setCellValueFactory(
        	    new PropertyValueFactory<String, String>("firstName")
        	);
        	
        loginCol.setCellValueFactory(
        	new PropertyValueFactory<String, String>("login")
         );
        
        table.getColumns().addAll(lastNameCol, firstNameCol, loginCol);
        table.setItems((ObservableList<FacultyMember>) this.data);
        
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    Node node = ((Node) event.getTarget()).getParent();
                    TableRow<FacultyMember> row;
                    if (node instanceof TableRow) {
                        row = (TableRow<FacultyMember>) node;
                    } else {
                        row = (TableRow<FacultyMember>) node.getParent();
                    }
                    
                    // Gets the current user that has been clicked.
                    int num = table.getSelectionModel().getSelectedIndex();
                    table.getSelectionModel().select(num);
                    TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
                    int numRow = pos.getRow();
                    FacultyMember item = table.getItems().get(numRow);
                   
                    User clickedUser = AccountManager.getUser(item.getLogin());                    
                    
                    // Transition to loading edit account view.
                    Stage stage = new Stage();
                    System.out.println(getClass().getResource("EditAccountView.fxml"));
                	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditAccountView.fxml"));     

                	Parent root = null;
    				try {
    					root = (Parent)fxmlLoader.load();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}          
                	EditAccountController editAccountController = fxmlLoader.<EditAccountController>getController();
                	editAccountController.setFacultyController(controller);
                	editAccountController.setCurrentUser(clickedUser);
                	editAccountController.setList("ready");
                	Scene scene = new Scene(root); 

                	stage.setScene(scene);    
                	stage.show(); 
                }
            }
        });
        
        Button button = new Button("Add faculty");
        button.setOnAction(new EventHandler<ActionEvent>() {

        	// Transitions to add account view window.
            public void handle(ActionEvent event) {
            	Stage stage = new Stage();
            	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAccountView.fxml"));     

            	Parent root = null;
				try {
					root = (Parent)fxmlLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}          
            	AddAccountController accountController = fxmlLoader.<AddAccountController>getController();
            	accountController.setFacultyController(controller);
            	Scene scene = new Scene(root); 

            	stage.setScene(scene);    

            	stage.show();   
              }
            });
        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, button);
 
        ((Group) scene.getRoot()).getChildren().addAll(vbox);  
 
        stage.setScene(scene);
        stage.show();
    }
	
	/**
     * Updates the list that has the current list of faculty. 
     */
	public void updateList() {
		List<HashMap<String, Object>> map = AccountManager.getUserList();
		data.clear();
    	for (HashMap<String, Object> faculty : map) {
    		
    		String login = (String) faculty.get("login");
    		String lastName = (String) faculty.get("last_name");
    		String firstName = (String) faculty.get("first_name");
    		data.add(new FacultyMember(login, firstName,lastName));
    	}
    	
	}
	
	/**
     * FacultyMember is a class to correctly populate the ListView.
     */
	public static class FacultyMember {
	    private final SimpleStringProperty login;
	    private final SimpleStringProperty firstName;
	    private final SimpleStringProperty lastName;
	 
	    private FacultyMember(String login, String fName, String lName) {
	        this.firstName = new SimpleStringProperty(fName);
	        this.lastName = new SimpleStringProperty(lName);
	        this.login = new SimpleStringProperty(login);
	    }
	
	    public String getFirstName() {
	        return firstName.get();
	    }
	    public void setFirstName(String fName) {
	        firstName.set(fName);
	    }
	        
	    public String getLastName() {
	        return lastName.get();
	    }
	    public void setLastName(String fName) {
	        lastName.set(fName);
	    }
	    
	    public String getLogin() {
	        return login.get();
	    }
	    public void setLogin(String login) {
	    	this.login.set(login);
	    }      
	}
		
}