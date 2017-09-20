package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private Stage primaryStage;
	private static StackPane Pane;
	private boolean fullScreen=false;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("ValadorPlayer");
		this.primaryStage.getIcons().add(new Image("image/play.png"));
		showMainView();
		
		Scene scene = new Scene(Pane);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() == 2){
					if(fullScreen == false){
						fullScreen = true;
						primaryStage.setFullScreen(true);
					}else{
						fullScreen = false;
						primaryStage.setFullScreen(false);
					}
				}
			}
		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void showMainView() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("FXML_Document.fxml"));
		Pane = loader.load();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
