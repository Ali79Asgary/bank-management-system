package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{
	serverOperation server;

	private static Stage stage;
	
	private static String authenticatedUser = null;
	
    @Override
    public void start(Stage primaryStage) throws Exception
    {

        server = new serverOperation();
        Thread thread = new Thread(server);
        thread.start();

    	stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent root = fxmlLoader.load();
        
        primaryStage.setTitle("Payment Management System");
        primaryStage.setScene(new Scene(root, 1209, 761));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }
    
    public static Stage getStage()
    {
    	return Main.stage;
    }
    
    public static void logOut()
    {
    	authenticatedUser=null;
    }

    public static void setAuthenticatedUser(String user)
    {
    	authenticatedUser = user;
    }
    
    public static String getAuthenticatedUser()
    {
    	return Main.authenticatedUser;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
