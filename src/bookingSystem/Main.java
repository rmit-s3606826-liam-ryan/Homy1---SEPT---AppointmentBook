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
		SeptFacade sf = SeptFacade.getFacade();
		sf.loadSystem();
	    launch(args);
	}
	
	private static void test() {
//TODO
		
	}

	@Override
	public void start(Stage primaryStage) 
	{
		try
		{
			if (SystemDriver.getBusiness() == null)
			{
				Parent root = FXMLLoader.load(getClass().getResource("/bookingSystem/Main.fxml"));
				Scene scene = new Scene(root, 720, 480);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
			else
			{
				Parent root = FXMLLoader.load(getClass().getResource("/bookingSystem/MakeBusiness.fxml"));
				Scene scene = new Scene(root, 720, 480);
				primaryStage.setScene(scene);
				primaryStage.show();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }
}


