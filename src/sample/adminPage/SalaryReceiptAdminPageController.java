package sample.adminPage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import sample.serverOperation;

public class SalaryReceiptAdminPageController implements Initializable
{
	
	private serverOperation sp;
	
	@FXML 
	private TextField txtIssueDate;
	@FXML 
	private TextField yearConstant;
	@FXML 
	private TextField familyAllowance;
	@FXML 
	private TextField childrenHelp;
	@FXML 
	private TextField jobHardnessScore;
	@FXML 
	private TextField jobSpecialScore;
	@FXML
	private TextField txtMinimumSalary;
	
	@FXML
	private Button btnGenerateActions;
	
	@FXML
	private Button btnChangeConstants;
	
	@FXML
	void changeConstants(ActionEvent event)
	{
		try
		{
			sp = new serverOperation();
			sp.createConnection();
			sp.setSystemConstants(yearConstant.getText(), familyAllowance.getText(),
			childrenHelp.getText(), jobHardnessScore.getText(), jobSpecialScore.getText());
			sp.closeConnection();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(" با موفقیت انجام شد");
			alert.setTitle("done!");
			alert.showAndWait();
		}
		catch(SQLException | ClassNotFoundException e)
    	{
    		String s = "Database Connection failed!\n"
    				+ "Make sure you are connected to the Internet!";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(s);
			alert.setTitle("Error");
			alert.showAndWait();
    	}
	}
	
	@FXML
	void generateAction(ActionEvent event)
	{
		try
		{
			sp = new serverOperation();
			
			String date = txtIssueDate.getText();
			if(sp.dateValidate(date)==true) 
			{
				sp.createConnection();
				sp.createActionAll(date);
				sp.closeConnection();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.setContentText("صدور حکم با موفقیت انجام شد");
				alert.setTitle("done!");
				alert.showAndWait();
			}
			else
			{
				String message = "فرمت تاریخ بصورت زیر باشد"
	        			+ "yyyy-mm-dd";
				Alert alert = new Alert(AlertType.ERROR);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.setContentText(message);
				alert.setTitle("Error");
				alert.showAndWait();
			}
		}
		catch(SQLException | ClassNotFoundException e)
    	{
    		String s = "Database Connection failed!\n"
    				+ "Make sure you are connected to the Internet!";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(s);
			alert.setTitle("Error");
			alert.showAndWait();
    	}
	}
	    
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
    	try 
    	{
			sp = new serverOperation();
			sp.createConnection();
			ResultSet data = sp.getConstants();
			yearConstant.setText(data.getString(1));
			familyAllowance.setText(data.getString(2));
			childrenHelp.setText(data.getString(3));
			jobHardnessScore.setText(data.getString(4));
			jobSpecialScore.setText(data.getString(5));
			txtMinimumSalary.setText(String.valueOf(data.getInt("minimumSalary")));
			
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
}
