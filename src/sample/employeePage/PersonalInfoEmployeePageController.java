package sample.employeePage;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import sample.Main;
import sample.Payslip;
import sample.PayslipServer;
import sample.serverOperation;

public class PersonalInfoEmployeePageController implements Initializable {
	
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
    private TextField totalDebt;
	@FXML
	private TextField installmentCount;
	@FXML
	private TextField remainDebt;
	@FXML
	private TextField monthInstallment;
	@FXML
	private Label debt_info;


    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
    	serverOperation sp;
    	try 
    	{
			sp = new serverOperation();
			sp.createConnection();
			ResultSet data = sp.getTeacherData(Main.getAuthenticatedUser());
			txtFirstName.setText(data.getString(3));
			txtLastName.setText(data.getString(4));
			txtFatherName.setText(data.getString(5));
			txtNationalId.setText(data.getString(2));
			txtPersonnelId.setText(data.getString(1));
			txtPassword.setText(data.getString(14));
			txtMaritalStatus.setText(data.getString(9));
			txtChildrenNumber.setText(data.getString(10));
			txtDegreeOfEducation.setText(data.getString(12));
			txtCity.setText(sp.getTeacherCity(Main.getAuthenticatedUser()));
			sp.closeConnection();

		} 
    	catch (Exception e)
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
		serverOperation sp;
		try
		{
			sp = new serverOperation();
			sp.createConnection();
			sp.updateMinorInformation(
					Main.getAuthenticatedUser(),
					txtFirstName.getText(),
					txtLastName.getText(),
					txtFatherName.getText(),
					txtNationalId.getText(),
					txtPassword.getText()
			);
			sp.closeConnection();
			String s = "Information Updated Successfully!";
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
