package sample.adminPage;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import sample.serverOperation;

public class PersonalInfoAdminPageController implements Initializable {

	private serverOperation sp;
	
	private String authenticated;

	@FXML
	private ComboBox<String> comboLoadEmployees;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtFatherName;

    @FXML
    private TextField txtNationalId;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtPersonnelId;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtMaritalStatus;

    @FXML
    private TextField txtChildrenNumber;

    @FXML
    private TextField txtDegreeOfEducation;

    @FXML
	private TextField txt_debtValue;

	@FXML
	private TextField txt_installmentCount;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<String> employees = null;
		try
		{
			sp = new serverOperation();
			sp.createConnection();
			employees = sp.getEmployees();
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
		comboLoadEmployees.setItems(employees);

	}

    @FXML
    void getInfo()
    {
    	authenticated = comboLoadEmployees.getValue().toString();
    	try 
    	{
			sp = new serverOperation();
			sp.createConnection();
			ResultSet data = sp.getTeacherData(authenticated);
			txtFirstName.setText(data.getString(3));
			txtLastName.setText(data.getString(4));
			txtFatherName.setText(data.getString(5));
			txtNationalId.setText(data.getString(2));
			txtPersonnelId.setText(data.getString(1));
			txtPassword.setText(data.getString(14));
			txtMaritalStatus.setText(data.getString(9));
			txtChildrenNumber.setText(data.getString(10));
			txtDegreeOfEducation.setText(data.getString(12));
			txtCity.setText(sp.getTeacherCity(authenticated));
			sp.closeConnection();
		} 
    	catch (ClassNotFoundException | SQLException e) 
    	{
    		String s = "Error while connecting to database !";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(s);
			alert.setTitle("Error");
			alert.showAndWait();	
		}
    }

    @FXML
    void updateInfo(ActionEvent event) 
    {
    	try 
    	{
			sp = new serverOperation();
			sp.createConnection();
			sp.updateMinorInformation(
				authenticated,
				txtFirstName.getText(),
				txtLastName.getText(),
				txtFatherName.getText(),
				txtNationalId.getText(),	
				txtPassword.getText()
			);
			sp.closeConnection();
			String s = "اطلاعات کارمند با موفقیت به روز شد";
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(s);
			alert.setTitle("Error");
			alert.showAndWait();		
		} 
    	catch (ClassNotFoundException | SQLException e) 
    	{
    		String s = "Error while connecting to database !";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(s);
			alert.setTitle("Error");
			alert.showAndWait();	
		}
    }
}
