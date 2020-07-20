package sample.employeePage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import sample.Action;
import sample.Main;
import sample.serverOperation;

public class SalaryReceiptEmployeePageController implements Initializable
{
	
	serverOperation sp;

	@FXML
	private TableView<Action> table;
	@FXML
	private TableColumn<Action, String> col_actionId;
	@FXML
	private TableColumn<Action, String> col_jobAllowance;
	@FXML
	private TableColumn<Action, String> col_employeeAllowance;
	@FXML
	private TableColumn<Action, String> col_familyHelp;
	@FXML
	private TableColumn<Action, String> col_childrenHelp;
	@FXML
	private TableColumn<Action, String> col_notDevelopedRegionAllowance;
	@FXML
	private TableColumn<Action, String> col_badWeatherRegionAllowance;
	@FXML
	private TableColumn<Action, String> col_jobDifficultnessAllowance;
	@FXML
	private TableColumn<Action, String> col_jobSpecial;
	@FXML
	private TableColumn<Action,String> col_salary;
	@FXML
	private TableColumn<Action, String> col_issueDate;
	@FXML
	private TableColumn<Action, String> col_jobClass;
	@FXML
	private TableColumn<Action, String> col_jobRank;
	    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		col_actionId.setCellValueFactory(new PropertyValueFactory<>("actionId"));
		col_jobAllowance.setCellValueFactory(new PropertyValueFactory<>("jobAllowance"));
		col_employeeAllowance.setCellValueFactory(new PropertyValueFactory<>("employeeAllowance"));
		col_familyHelp.setCellValueFactory(new PropertyValueFactory<>("familyHelp"));
		col_childrenHelp.setCellValueFactory(new PropertyValueFactory<>("childrenHelp"));
		col_notDevelopedRegionAllowance.setCellValueFactory(new PropertyValueFactory<>("notDevelopedRegionAllowance"));
		col_badWeatherRegionAllowance.setCellValueFactory(new PropertyValueFactory<>("badWeatherRegionAllowance"));
		col_jobDifficultnessAllowance.setCellValueFactory(new PropertyValueFactory<>("jobDifficultnessAllowance"));
		col_jobSpecial.setCellValueFactory(new PropertyValueFactory<>("jobSpecial"));
		col_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
		col_issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
		col_jobClass.setCellValueFactory(new PropertyValueFactory<>("jobClass"));
		col_jobRank.setCellValueFactory(new PropertyValueFactory<>("jobrank"));
		
		ObservableList<Action> actionList = null;
		
   		try 
		{
			sp = new serverOperation();
			sp.createConnection();
			actionList = sp.getActions( Main.getAuthenticatedUser() );
			sp.closeConnection();
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			String s = "Database Connection failed!\n"
    				+ "Make sure you are connected to the Internet!";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(s);
			alert.setTitle("Error");
			alert.showAndWait();
		}
   		
   		table.setItems(actionList);
	}
}
