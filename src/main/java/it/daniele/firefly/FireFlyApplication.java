package it.univaq.disim.oop.firefly;

import it.univaq.disim.oop.firefly.view.ViewDispatcher;
import javafx.application.Application;
import javafx.stage.Stage;

public class FireFlyApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		ViewDispatcher dispatcher = ViewDispatcher.getInstance();
		dispatcher.loginView(stage);
	}

}
