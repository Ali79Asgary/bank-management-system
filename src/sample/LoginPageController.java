package sample;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class LoginPageController implements Initializable
{
	private double xOffset = 0;
	private double yOffset = 0;
	
	serverOperation sp;
    @FXML
    private Circle close;
    @FXML
    private Circle mini;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;

	@FXML
	void admin(ActionEvent event) {
		txtUserName.setText("89001000001");
		txtPassword.setText("87654321");
	}

	@FXML
	void nima(ActionEvent event) {
		txtUserName.setText("99002000011");
		txtPassword.setText("12");
	}
    @FXML
    void doAuthentication(ActionEvent event)
    {
        try
        {
        	sp = new serverOperation();
        	String personId = txtUserName.getText();
        	String Password = txtPassword.getText();
        	boolean loginStatus = false;
        	
        	if(personId.substring(2,5).contentEquals("002"))
        	{
	        	try
	        	{
		        	sp.createConnection();
		        	loginStatus = sp.Authenticate(personId, Password);
		        	sp.closeConnection();
		        	
		        	if(loginStatus==true )
		        	{
		        		Main.setAuthenticatedUser(personId);
			            Stage stage =  Main.getStage();
			            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample/employeePage/EmployeePage.fxml"));
			            Parent root = fxmlLoader.load();    
			            stage.setScene(new Scene(root));
		        	}
		        	else
		        	{
		        		String s = "نام کاربری یا رمز عبور اشتباه است";
		    			Alert alert = new Alert(AlertType.ERROR);
		    			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		    			alert.setContentText(s);
		    			alert.setTitle("Error");
		    			alert.showAndWait();
		        	}
		        	
	        	}
	        	catch(SQLException e)
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
        	else if(personId.substring(2,5).contentEquals("001"))
        	{
	        	try
	        	{
		        	sp.createConnection();
		        	loginStatus = sp.AuthenticateAdmin(personId, Password);
		        	sp.closeConnection();
		        	
		        	if(loginStatus==true )
		        	{
		        		Main.setAuthenticatedUser(personId);
			            Stage stage =  Main.getStage();
			            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample/adminPage/AdminPage.fxml"));
			            Parent root = fxmlLoader.load();    
			            stage.setScene(new Scene(root));
		        	}
		        	else
		        	{
		        		String s = "Incorrect personId/password";
		    			Alert alert = new Alert(AlertType.ERROR);
		    			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		    			alert.setContentText(s);
		    			alert.setTitle("Error");
		    			alert.showAndWait();
		        	}
		        	
	        	}
	        	catch(SQLException e)
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
        	else
        	{
        		String s = "Invalid Username";
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    			alert.setContentText(s);
    			alert.setTitle("Error");
    			alert.showAndWait();
        	}
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void gotoSignUpPage(ActionEvent event)
    {
        try
        {
            Stage stage =  Main.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample/SignUpPage.fxml"));
            Parent root = fxmlLoader.load();    
            stage.setScene(new Scene(root));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    void mini(MouseEvent event)
    {
        ((Stage) ((Circle) event.getSource()).getScene().getWindow()).setIconified(true);
    }
    @FXML
    void onClose(MouseEvent event)
    {
        System.exit(0);
    }

    @FXML
    public void startScreenMovement(MouseEvent event) 
    {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }
    
    @FXML
    public void handleScreenMovement(MouseEvent event) 
    {
        Main.getStage().setX(event.getScreenX() - xOffset);
        Main.getStage().setY(event.getScreenY() - yOffset);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        
    }
}
