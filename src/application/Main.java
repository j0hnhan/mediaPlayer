package application;
	
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.SubtitleTrack;
import javafx.scene.web.WebView;


public class Main extends Application {
	
	Player player;
	FileChooser fileChooser;
	
	@Override
	public void start(Stage primaryStage) {
		
		MenuItem open = new MenuItem("Open");
		MenuItem close = new MenuItem("Close");
		Menu file = new Menu("File");
		
		MenuItem play = new MenuItem("Play");
		MenuItem pause = new MenuItem("Pause");
		MenuItem ff = new MenuItem("Fast Forword");
		MenuItem rewind = new MenuItem("Rewind");
		MenuItem fullScreen = new MenuItem("Full Screen");
		Menu control = new Menu("Control");
		
		MenuBar menu = new MenuBar(); 
		
		file.getItems().add(open);
		file.getItems().add(close);
		control.getItems().add(play);
		control.getItems().add(pause);
		control.getItems().add(ff);
		control.getItems().add(rewind);
		control.getItems().add(fullScreen);
		menu.getMenus().add(file); 
		menu.getMenus().add(control);
		
		fileChooser = new FileChooser();

//		player = new Player("file:///Users/johnhan/Downloads/Gakki.mp4");
		player = new Player("");
		
		open.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(player.player != null){
					player.player.pause();
				}
				File file = fileChooser.showOpenDialog(primaryStage);
				if(file != null){
					try {
						player = new Player(file.toURI().toURL().toExternalForm());
						setUpScene(primaryStage);
						player.setTop(menu);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		
		close.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				primaryStage.close();
			}
			
		});
		
		play.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(player.player != null){
					Status status = player.player.getStatus();
					if(status == Status.PAUSED || status == Status.HALTED || status == Status.STOPPED) {
						player.player.play();
					}
				}				
			}
			
		});
		
		pause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(player.player != null){
					Status status = player.player.getStatus();
					if(status == Status.PLAYING) {
						player.player.pause();
					}
				}				
			}
			
		});
		
		ff.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(player.player != null){
					Duration newTime = new Duration(player.player.getCurrentTime().toMillis()+1500);
					if(newTime.greaterThanOrEqualTo(player.player.getTotalDuration())){
						player.player.seek(player.player.getTotalDuration());
					}else{
						player.player.seek(newTime);
					}
						
				}
			}
			
		});
		
		rewind.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(player.player != null){
					Duration newTime = new Duration(player.player.getCurrentTime().toMillis()-1500);
					if(newTime.lessThanOrEqualTo(player.player.getStartTime())){
						player.player.seek(player.player.getStartTime());
					}else{
						player.player.seek(newTime);
					}
				}
			}
			
		});
		
		fullScreen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(!primaryStage.isFullScreen()){
					primaryStage.setFullScreen(true);
				}else{
					primaryStage.setFullScreen(false);
				}
				
			}
			
		});
		
		player.setTop(menu);
		setUpScene(primaryStage);
		primaryStage.show();
		
	}
	
	public void setUpScene(Stage primaryStage) {
		Scene scene = new Scene(player, 720, 480);
		primaryStage.setScene(scene);
		if(player.player != null){
			player.player.setOnReady(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int w = player.player.getMedia().getWidth();
					int h = player.player.getMedia().getHeight();
					primaryStage.setHeight(h==0? 480:h+20);
					primaryStage.setWidth(w==0?720:w);
				}

			});	
			Path p = Paths.get(player.player.getMedia().getSource());
			primaryStage.setTitle(p.getFileName().toString());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
