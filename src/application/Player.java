package application;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane{
	
	Media media;
	MediaPlayer player;
	MediaView view;
	Pane mpane;
	MediaBar bar;
	
	public Player(String file){
		
		mpane = new Pane();
		
		if(file.equals("")){
			setupBackground();
		}else if(file.endsWith(".mp3")){
			setupBackground();
			setupPlayer(file);
		}else{
			setupPlayer(file);
		}
		
	}
	
	
	public void setupPlayer(String file) {
		media = new Media(file);
		player = new MediaPlayer(media);
		view = new MediaView(player);
		mpane.getChildren().add(view);

		setCenter(mpane);
		
		bar = new MediaBar(player);
		setBottom(bar);
		
		bar.setStyle("-fx-background-color: #bfc2c7");
		player.play();
	}
	
	public void setupBackground() {
		BackgroundImage myBI= new BackgroundImage(new Image("file:///Users/johnhan/Documents/Code/workspace/Player/src/image/background.jpg",360,240,false,true),
		        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
		          BackgroundSize.DEFAULT);
		//then you set to your node
		mpane.setBackground(new Background(myBI));		
	}
	
}
