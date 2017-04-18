package bookingSystem;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application
{
	Stage window;
	Scene startMenu, ownerMenu, customerMenu, login, registration;
	
	public static void main(String[] args)
	{
	    launch(args);
		SystemDriver system = new SystemDriver();
		system.loadSystem();
	}
	
	public void start(Stage primaryStage) 
	{
		window = primaryStage;
		
		GridPane baseLayout = new GridPane();
		baseLayout.setAlignment(Pos.CENTER);
		baseLayout.setHgap(10);
		baseLayout.setVgap(10);
		baseLayout.setPadding(new Insets(25, 25, 25, 25));
		
		
		// start window elements
		Text welcomeLabel = new Text("Welcome to <>\n"
				                   + "Appointment booking system.");
		welcomeLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        window.setTitle("Book Things!");
		Button loginButton = new Button("Login");
		loginButton.setMaxWidth(500);
		loginButton.setOnAction(e -> window.setScene(login));
		Button regButton = new Button("Register");
		regButton.setOnAction(e -> window.setScene(registration));
		regButton.setMaxWidth(500);
		baseLayout.add(welcomeLabel, 0, 0);
		baseLayout.add(loginButton, 0, 1);
		baseLayout.add(regButton, 0, 2);
		startMenu = new Scene(baseLayout, 720, 480);
		
		// login layout
		GridPane loginWindow = new GridPane();
		loginWindow.setAlignment(Pos.CENTER);
		loginWindow.setHgap(10);
		loginWindow.setVgap(10);
		loginWindow.setPadding(new Insets(25, 25, 25, 25));
		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		loginWindow.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		loginWindow.add(userName, 0, 1);

		TextField userTextField = new TextField();
		loginWindow.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		loginWindow.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		loginWindow.add(pwBox, 1, 2);
		
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		loginWindow.add(hbBtn, 1, 4);
		login = new Scene(loginWindow, 720, 480);

		// reg layout
		GridPane regWindow = new GridPane();
		regWindow.setAlignment(Pos.CENTER);
		regWindow.setHgap(10);
		regWindow.setVgap(10);
		regWindow.setPadding(new Insets(25, 25, 25, 25));
		Text regtitle = new Text("Registraion");
		regtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		regWindow.add(regtitle, 0, 0, 2, 1);

		Label regUserName = new Label("User Name:");
		final Text invUsername = new Text();
        regWindow.add(invUsername, 2, 1);
		regWindow.add(regUserName, 0, 1);
		TextField usernameTextField = new TextField();
		usernameTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> 
		{
			if (!newValue) 
			{
				if (!RegistrationValidation.validateUserName(usernameTextField.getText()))
				{
			        invUsername.setFill(Color.FIREBRICK);
			        invUsername.setText("Invalid Username");				
			    }
				else
				{
			        invUsername.setText("");				
				}
			}
		});
		regWindow.add(usernameTextField, 1, 1);

		Label pass = new Label("Password:");
		final Text invPass = new Text();
        regWindow.add(invPass, 2, 3);
		regWindow.add(pass, 0, 2);
		PasswordField regPass = new PasswordField();
		regWindow.add(regPass, 1, 2);

		Label confirmPass = new Label("Confirm Password:");
		regWindow.add(confirmPass, 0, 3);
		PasswordField regConfirmPass = new PasswordField();
		regConfirmPass.focusedProperty().addListener((arg0, oldValue, newValue) -> 
		{
			if (!newValue) 
			{
				if (!RegistrationValidation.validatePassword(regPass.getText(), regConfirmPass.getText()))
				{
			        invPass.setFill(Color.FIREBRICK);
					invPass.setText("Passwords Do not Match");
				}
				else
				{
					invPass.setText("");
				}
			}
		});
		regWindow.add(regConfirmPass, 1, 3);
		
		Label regName = new Label("Full Name:");
		final Text invTrueName = new Text();
        regWindow.add(invTrueName, 2, 4);
		regWindow.add(regName, 0, 4);
		TextField nameTextField = new TextField();
		nameTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> 
		{
			if (!newValue) 
			{
				if (!RegistrationValidation.validateName(nameTextField.getText()))
				{
			        invTrueName.setFill(Color.FIREBRICK);
					invTrueName.setText("Is that really how you spell it?");
				}
				else
				{
					invTrueName.setText("");
				}
			}
		});
		regWindow.add(nameTextField, 1, 4);

		Label regDOB = new Label("DOB:");
		final Text invDOB = new Text();
        regWindow.add(invDOB, 2, 5);
		regWindow.add(regDOB, 0, 5);
		TextField dobTextField = new TextField();
		dobTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> 
		{
			if (!newValue) 
			{
				LocalDate dob = SystemDriver.parseDate(dobTextField.getText());
				if (dob != null)
				{
			        invDOB.setText("");				
			    }
				else
				{
			        invDOB.setFill(Color.FIREBRICK);
			        invDOB.setText("Invalid Date");				
				}
			}
		});
		regWindow.add(dobTextField, 1, 5);

		Label regPhone = new Label("Phone Number:");
		final Text invPhone = new Text();
        regWindow.add(invPhone, 2, 6);
		regWindow.add(regPhone, 0, 6);
		TextField phoneTextField = new TextField();
		phoneTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> 
		{
			if (!newValue) 
			{
				if (!RegistrationValidation.validatePhone(phoneTextField.getText()))
				{
			        invPhone.setFill(Color.FIREBRICK);
			        invPhone.setText("Invalid Phone Number");				
			    }
				else
				{
			        invPhone.setText("");				
				}
			}
		});
		regWindow.add(phoneTextField, 1, 6);

		Label regEmail = new Label("Email:");
		final Text invEmail = new Text();
        regWindow.add(invEmail, 2, 7);
		regWindow.add(regEmail, 0, 7);
		TextField emailTextField = new TextField();
		emailTextField.focusedProperty().addListener((arg0, oldValue, newValue) -> 
		{
			if (!newValue) 
			{
				if (!RegistrationValidation.validateEmail(emailTextField.getText()))
				{
			        invEmail.setFill(Color.FIREBRICK);
			        invEmail.setText("Invalid Email");				
			    }
				else
				{
			        invEmail.setText("");				
				}
			}
		});
		regWindow.add(emailTextField, 1, 7);

		Button regBut = new Button("Register");
		HBox regButBox = new HBox(10);
		regButBox.setAlignment(Pos.BOTTOM_RIGHT);
		regButBox.getChildren().add(regBut);
		regWindow.add(regButBox, 1, 8);
		registration = new Scene(regWindow, 720, 480);

        primaryStage.setScene(startMenu);
        primaryStage.show();
    }
}


