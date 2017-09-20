package application;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class FXML_Controller {
	
	private String path;
	private MediaPlayer mediaPlayer;
	//true - play media, false - pause media/stop media
	private boolean changeMode;
	private boolean FileTaken = false;
	private boolean NoVolume = false;
	
	private Timeline slideIn = new Timeline();
	private Timeline slideOut = new Timeline();
	
	@FXML
	private MediaView mediaView;
	@FXML
	private Slider sliderVolume;
	@FXML
	private Slider sliderTime;
	@FXML
	private VBox vbox;
	@FXML
	private Button withSound;

	@FXML
	private void buttonClicked(){
		if(FileTaken == true){
			mediaPlayer.pause();
			changeMode = false;
		}
		
		FileChooser file = new FileChooser();
		FileChooser.ExtensionFilter filter = 
				new FileChooser.ExtensionFilter("Select a file (*.mp4)", "*.mp4");
		file.getExtensionFilters().add(filter);
		
		File File = file.showOpenDialog(null);
		path = File.toURI().toString();
		
		if(path!=null){
			
			Media media = new Media(path);
			mediaPlayer = new MediaPlayer(media);
			mediaView.setMediaPlayer(mediaPlayer);
			FileTaken = true;
			changeMode = true;
			
			DoubleProperty width = mediaView.fitWidthProperty();
			DoubleProperty hight = mediaView.fitHeightProperty();
			width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
			hight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
			
			mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {

				@Override
				public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
						Duration newValue) {
					sliderTime.setValue(newValue.toSeconds());
				}
			});
			
			mediaPlayer.play();
			
			mediaPlayer.setOnReady(new Runnable() {
				
				@Override
				public void run() {
					
					sliderVolume.setValue(mediaPlayer.getVolume()*100);
					sliderTime.setMin(0.0);
					sliderTime.setValue(0.0);
					sliderTime.setMax(mediaPlayer.getTotalDuration().toSeconds());
					
					slideOut.getKeyFrames().addAll(
							new KeyFrame(new Duration(300.0),
									 new KeyValue(vbox.translateYProperty(), 0),
									 new KeyValue(vbox.opacityProperty(), 0.0)
									 )
					);
					
					slideIn.getKeyFrames().addAll(
							new KeyFrame(new Duration(300.0),
									 new KeyValue(vbox.translateYProperty(), 0),
									 new KeyValue(vbox.opacityProperty(), 1.0)
									 )
					);
					
				}
			});
		}	
	}
	
	@FXML
	private void setOnMouseClicked(){
		if(changeMode == true){
			changeMode = false;
			mediaPlayer.pause();
		}else{
			changeMode = true;
			mediaPlayer.setRate(1.0);
			mediaPlayer.play();
		}
	}
	
	@FXML
	private void play(){
		changeMode=true;
		mediaPlayer.setRate(1.0);
		mediaPlayer.play();
	}
	
	@FXML
	private void pause(){
		changeMode=false;
		mediaPlayer.pause();
	}
	
	@FXML
	private void stop(){
		changeMode=false;
		mediaPlayer.stop();
	}
	
	@FXML
	private void slow(){
		mediaPlayer.setRate(0.75);
	}
	
	@FXML
	private void slower(){
		mediaPlayer.setRate(0.5);
	}
	
	@FXML
	private void fast(){
		mediaPlayer.setRate(1.25);
	}
	@FXML
	private void faster(){
		mediaPlayer.setRate(1.5);
	}
	
	@FXML
	private void changeVolume(){
		mediaPlayer.setVolume(sliderVolume.getValue()/100);	
		if(sliderVolume.getValue()<5.0){
			withSound.setId("withoutSound");
		}else{
			withSound.setId("withSound");
		}
		
	}
	@FXML
	private void changeSliderTime(){
		mediaPlayer.seek(Duration.seconds(sliderTime.getValue()));	
	}
	
	@FXML
	private void NoVolume(){
		if(NoVolume == false){
			NoVolume = true;
			mediaPlayer.setVolume(0.0);
			sliderVolume.setValue(0.0);
			withSound.setId("withoutSound");
		}else{
			NoVolume = false;
			mediaPlayer.setVolume(1.0);
			sliderVolume.setValue(100.0);
			withSound.setId("withSound");
		}
			
	}
	
	@FXML
	private void SlideIn(){
		slideIn.play();
	}
	
	@FXML
	private void SlideOut(){
		slideOut.play();
	}

}
		
		
	
