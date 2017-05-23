package bookingSystem;


import java.time.DayOfWeek;
import java.time.LocalDate;

import db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	public static void main(String[] args)
	{
		test();
		SystemDriver system = SystemDriver.getSystemDriver();
		SeptFacade.getFacade().loadSystem();
	    launch(args);
	}
	
	private static void test() {
		
	}

	@Override
	public void start(Stage primaryStage) 
	{
		try
		{
			// if there is a business in system, load main
			// else load make business scene
			String sceneToLoad = SystemDriver.getBusiness() != null ? 
										 "/bookingSystem/Main.fxml" : 
										 "/bookingSystem/MakeBusiness.fxml";
			Parent root = FXMLLoader.load(getClass().getResource(sceneToLoad));
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


