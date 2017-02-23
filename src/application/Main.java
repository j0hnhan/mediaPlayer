package application;
	
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	Player player;
	FileChooser fileChooser;
	
	@Override
	public void start(Stage primaryStage) {
		
		MenuItem open = new MenuItem("Open");
		Menu file = new Menu("File");
		MenuBar menu = new MenuBar(); 
		
		file.getItems().add(open);
		menu.getMenus().add(file); 
		
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
