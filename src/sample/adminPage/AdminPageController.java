package sample.adminPage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;

public class AdminPageController implements Initializable {
   
    private FXMLLoader fxmlLoader;
    
    private double xOffset = 0;
	private double yOffset = 0;
	
    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private Circle close;

    @FXML
    private Circle mini;

    @FXML
    private Circle circlePlayMusic;

    @FXML
    private Circle circleStopMusic;

    @FXML
    private AnchorPane anchorAdminMain;

    @FXML
    private AnchorPane anchorAdmin;
    @FXML
    private AnchorPane slidepane;


    @FXML
    void mini(MouseEvent event) {
        ((Stage) ((Circle) event.getSource()).getScene().getWindow()).setIconified(true);
    }

    @FXML
    void onClose(MouseEvent event) {
        System.exit(0);
    }
    
    @FXML
    void fade(MouseEvent event)
    {
    	
    	if(slidepane.getTranslateX() < 0 )
    	{
	    	TranslateTransition slide = new TranslateTransition();
	    	slide.setDuration(Duration.seconds(0.4));
	    	slide.setNode(slidepane);
	    	slide.setToX(0);
	    	slide.play();
	    	slidepane.setTranslateX(210);
    	}
    	else
    	{
    		TranslateTransition slide = new TranslateTransition();
        	slide.setDuration(Duration.seconds(0.4));
        	slide.setNode(slidepane);
        	slide.setToX(-210);
        	slide.play();
        	slidepane.setTranslateX(210);
    	}
    }
    @FXML
    void goToHomePage()
    {
    	 try 
    	 {
             fxmlLoader = new FXMLLoader(getClass().getResource("HomePageAdminPage.fxml"));
             mainAnchor.getChildren().removeAll();
             mainAnchor.getChildren().add(fxmlLoader.load());
         } 
    	 catch(Exception e) 
    	 {
             System.out.println(e.getMessage());
             e.printStackTrace();
         }
    }
    @FXML
    void goToYourInformation()
    {
    	try 
   	 {
            fxmlLoader = new FXMLLoader(getClass().getResource("PersonalInfoAdminPage.fxml"));
            mainAnchor.getChildren().removeAll();
            mainAnchor.getChildren().add(fxmlLoader.load());
        } 
   	 catch(Exception e) 
   	 {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    void goToEmploymentAction()
    {
    	try 
   	 {
            fxmlLoader = new FXMLLoader(getClass().getResource("SalaryReceiptAdminPage.fxml"));
            mainAnchor.getChildren().removeAll();
            mainAnchor.getChildren().add(fxmlLoader.load());
        } 
   	 catch(Exception e) 
   	 {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    public void goToReceiptAction(MouseEvent mouseEvent) {
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("ReceiptAdminPage.fxml"));
            mainAnchor.getChildren().removeAll();
            mainAnchor.getChildren().add(fxmlLoader.load());
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void logOut()
    {
    	Main.logOut();
    	Stage stage =  Main.getStage();
        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getClassLoader().getResource("sample/LoginPage.fxml"));
        Parent root=null;
		try 
		{
			root = fxmlLoader2.load();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}    
        stage.setScene(new Scene(root));
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
    	 slidepane.setTranslateX(-210);
    	 
    	 try 
    	 {
             fxmlLoader = new FXMLLoader(getClass().getResource("HomePageAdminPage.fxml"));
             mainAnchor.getChildren().add(fxmlLoader.load());
         } 
    	 catch (Exception e) 
    	 {
             System.out.println(e.getMessage());
             e.printStackTrace();
         }
    }


}
