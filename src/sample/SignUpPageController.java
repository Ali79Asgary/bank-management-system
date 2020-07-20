package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SignUpPageController implements Initializable {

	private serverOperation sp;
	
	private double xOffset = 0;
	private double yOffset = 0;
	
    @FXML
    private Button btnSignUp;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtFatherName;
    @FXML
    private TextField txtNationalId;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField dateBirthday;
    @FXML
    private ComboBox<String> comboMaritalStatus;
    @FXML
    private TextField txtChildrenNumber;
    @FXML
    private ComboBox<String> comboGender;
    @FXML
    private ComboBox<String> comboDegreeOfEducation;
    @FXML
    private ComboBox<String> comboStateOfWorkplace;
    @FXML
    private ComboBox<String> comboCityOfWorkplace;
    @FXML
    private ToggleButton ToggleButtenAdminEmployee;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private Button btnSignIn;
    @FXML
    private Label lblWrongInfo;
    @FXML
    private Circle close;
    @FXML
    private Circle mini;

    
    @FXML
    public void signUp(ActionEvent actionEvent)
    {
    	String FirstName = txtFirstName.getText();
    	String LastName = txtLastName.getText();
    	String FatherName = txtFatherName.getText();
    	String ChildrenNumber = txtChildrenNumber.getText();
    	String NationalId = txtNationalId.getText();
    	String PhoneNumber = txtPhoneNumber.getText();
   		String dateBirthdayString = dateBirthday.getText();
   		String Password = txtPassword.getText();
    	String ConfirmPassword = txtConfirmPassword.getText();
    

    	
    	try 
    	{
			sp = new serverOperation();
		} 
    	catch (ClassNotFoundException | SQLException e) 
    	{
    		String message = "Error while loading files.";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
		}
    	
        if( FirstName.length() > 50  || FirstName.length() < 3 ||
        	LastName.length() > 50   || LastName.length() < 3  ||
        	FatherName.length() > 50 || FatherName.length() < 3 )
        {
        	String message = "نام شما باید کوچکتر از ۵۰ حرف یا بزرگتر از ۲ حرف باشد";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else if(sp.isPersian(FirstName) ==false || 
        		sp.isPersian(LastName)  ==false ||
        		sp.isPersian(FatherName)==false)
        {
        	String message = "لطفا اسامی  را فارسی بنویسید";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else if(sp.isNumber(ChildrenNumber)==false)
        {
        	String message = "تعداد فرزندان باید عدد باشد";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else if(NationalId.length()!=10 || sp.isNumber(NationalId)==false)
        {
        	String message = "فرمت ده رقمی شماره ملی به درستی رعایت نشده";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else if(PhoneNumber.length()!=11 || sp.isNumber(PhoneNumber)==false)
        {
        	String message = "فرمت شماره همراه بصورت زیر باشد"
        			+ "09xxxxxxxxx";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else if( sp.dateValidate(dateBirthdayString)==false )
        {
        	String message = "فرمت تاریخ تولد بصورت زیر باشد"
        			+ "yyyy-mm-dd";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else if(comboCityOfWorkplace.getValue()==null ||
        		comboDegreeOfEducation.getValue()==null ||
        		comboGender.getValue()== null ||
        		comboMaritalStatus.getValue() == null || 
        		comboStateOfWorkplace.getValue() == null )
        {
        	String message = "لطفا همه ی کادر ها را پر کنید";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else if(Password.length() > 100  || Password.length() < 7)
        {
        	String message = "رمز عبور از صد کاراکتر کمتر و حداقل هشت کاراکتر باشد";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else if(Password.contentEquals(ConfirmPassword)==false)
        {
        	String message = "رمز های عبور وارد شده متفاوت هستند";
			Alert alert = new Alert(AlertType.ERROR);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setContentText(message);
			alert.setTitle("Error");
			alert.showAndWait();
        }
        else
        {
        	try
        	{
        		sp.createConnection();
        		String personId = sp.createUser(FirstName,LastName,FatherName,ChildrenNumber,
        		NationalId,PhoneNumber,dateBirthdayString,comboMaritalStatus.getValue().toString(),comboGender.getValue().toString(),
        		comboDegreeOfEducation.getValue().toString(),comboCityOfWorkplace.getValue().toString(),Password);
        		sp.closeConnection();
        		
        		String message = "حساب کاربری شما در سامانه با موفقیت ثبت گردید"+"\n"
        				+ "کد پرسنی شما :" + personId + "\n"
        				+ "رمز عبور شما :" + Password ;
    			Alert alert = new Alert(AlertType.INFORMATION);
    			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    			alert.setContentText(message);
    			alert.setTitle("ثبت اطلاعات");
    			alert.showAndWait();
    			
    			Stage stage =  Main.getStage();
   	         	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample/LoginPage.fxml"));
   	         	Parent root = null;
				try 
				{
					root = fxmlLoader.load();
				} 
				catch (IOException ex)
				{
					ex.printStackTrace();
					String message2 = "Error while opening login page!";
	    			Alert alert2 = new Alert(AlertType.ERROR);
	    			alert2.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
	    			alert2.setContentText(message2);
	    			alert2.setTitle("Error");
	    			alert2.showAndWait();
				}    
	   	         stage.setScene(new Scene(root));	
	        }
        	catch(SQLException e)
        	{
        		e.printStackTrace();
        		String message = "Error While Connecting to database";
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    			alert.setContentText(message);
    			alert.setTitle("Error");
    			alert.showAndWait();
        	}
        	
        	
        }
    }
    @FXML
    void loadCities()
    {
    	String province = comboStateOfWorkplace.getValue().toString();
    	ObservableList<String> cities = null;
  
		try 
		{
			sp = new serverOperation();
			sp.createConnection();
			cities = sp.getCities(province);
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
	
		comboCityOfWorkplace.setItems(cities);
    }
    
    @FXML
    public void gotoLoginPage(ActionEvent actionEvent)
    {
        try
        {
        	 Stage stage =  Main.getStage();
	         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample/LoginPage.fxml"));
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
    	ObservableList<String>  gender = FXCollections.observableArrayList(); 
    	gender.add("مرد");
    	gender.add("زن");
    	
    	ObservableList<String> maritalstatus = FXCollections.observableArrayList();
    	maritalstatus.add("مجرد");
    	maritalstatus.add("متاهل");
    	
    	ObservableList<String> degree = FXCollections.observableArrayList();
    	degree.add("دیپلم");
    	degree.add("فوق دیپلم");
    	degree.add("لیسانس");
    	degree.add("فوق لیسانس");
    	degree.add("دکتری");

    	comboMaritalStatus.setItems(maritalstatus);
    	comboDegreeOfEducation.setItems(degree);
    	comboGender.setItems(gender);
    	
    	ObservableList<String> provinces = null;
    		try 
    		{
				sp = new serverOperation();
				sp.createConnection();
				provinces = sp.getProvinces();
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
    	
    	comboStateOfWorkplace.setItems(provinces);

    }



}
