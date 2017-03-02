package application;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MediaBar extends HBox{
	
	Slider time = new Slider();
	Slider vol = new Slider();
	
	Label timeLabel = new Label("00:00/00:00");
	Button rewindButtton = new Button("R");
	Button playButton = new Button("||");
	Button ffButton = new Button("F");
	Label volume = new Label("Volume: ");
	
	MediaPlayer player;
	
	
	public MediaBar(MediaPlayer play){
		player = play;
		
		setAlignment(Pos.CENTER);
		setPadding(new Insets(5,10,5,10));
		
		timeLabel.setPrefWidth(100);
		timeLabel.setMinWidth(50);
		
		
		vol.setPrefWidth(50);
		vol.setMinWidth(30);
		vol.setValue(100);
		
		HBox.setHgrow(time, Priority.ALWAYS);
		
		
		rewindButtton.setPrefWidth(30);
		playButton.setPrefWidth(30);
		ffButton.setPrefWidth(30);
		
		
		getChildren().add(rewindButtton);
		getChildren().add(playButton);
		getChildren().add(ffButton);
		getChildren().add(time);
		getChildren().add(timeLabel);
		getChildren().add(volume);
		getChildren().add(vol);
		
		rewindButtton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Duration newTime = new Duration(player.getCurrentTime().toMillis()-1500);
				if(newTime.lessThanOrEqualTo(player.getStartTime())){
					player.seek(player.getStartTime());
				}else{
					player.seek(newTime);
				}
			}
			
		});
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Status status = player.getStatus();
				
				if(status == Status.PLAYING){
					if(player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())){
						player.seek(player.getStartTime());
						player.play();
					} else {
						player.pause();
						playButton.setText(">");
					}
				}
				
				if(status == Status.PAUSED || status == Status.HALTED || status == Status.STOPPED){
					player.play();
					playButton.setText("||");
				}
			}
			
		});
		
		ffButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Duration newTime = new Duration(player.getCurrentTime().toMillis()+1500);
				if(newTime.greaterThanOrEqualTo(player.getTotalDuration())){
					player.seek(player.getTotalDuration());
				}else{
					player.seek(newTime);
				}	
			}
			
		});
		
		
		
		player.currentTimeProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				// TODO Auto-generated method stub
				updateValue();
				updateTimeLabel();
			}
			
		});
		
		
		time.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				// TODO Auto-generated method stub
				if(time.isPressed()){
					player.seek(player.getMedia().getDuration().multiply(time.getValue()/100));
				}
			}
			
		});
		
		vol.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				// TODO Auto-generated method stub
				if(vol.isPressed()){
					player.setVolume(vol.getValue()/100);
				}
			}
		});
		
	}
	
	protected void updateValue(){
		Platform.runLater(new Runnable() {
			public void run() {
				time.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
			}
		});
	}
	
	public void updateTimeLabel(){
		Platform.runLater(new Runnable(){
			public void run(){
				StringBuilder totalTime = new StringBuilder();
				int totalMinute = (int) Math.round(player.getTotalDuration().toSeconds()/60);
				int totalSecond = (int) Math.round(player.getTotalDuration().toSeconds()%60);
				if(totalMinute<10){
					totalTime.append(0).append(totalMinute);
				}else{
					totalTime.append(totalMinute);
				}
				totalTime.append(":");
				if(totalSecond<10){
					totalTime.append(0).append(totalSecond);
				}else{
					totalTime.append(totalSecond);
				}				
				StringBuilder currentTime = new StringBuilder();
				int currentMinute = (int) Math.round(player.getCurrentTime().toSeconds()/60);
				int currentSeconds = (int) Math.round(player.getCurrentTime().toSeconds()%60);
				if(currentMinute<10){
					currentTime.append(0).append(currentMinute);
				}else{
					currentTime.append(currentMinute);
				}
				currentTime.append(":");
				if(currentSeconds<10){
					currentTime.append(0).append(currentSeconds);
				}else{
					currentTime.append(currentSeconds);
				}					
				currentTime.append("/").append(totalTime);
				timeLabel.setText(currentTime.toString());
			}
		});
	}
	
}
