package bookingSystem;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	Stage window;
	Scene startMenu, ownerMenu, customerMenu, login, registration;
	
	public static void main(String[] args)
	{
		SystemDriver system = new SystemDriver();
		system.loadSystem();
	    launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) 
	{
		try
		{
			Parent root = FXMLLoader.load(getClass().getResource("/bookingSystem/Main.fxml"));
			Scene scene = new Scene(root, 720, 480);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }
}


