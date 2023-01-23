package com.example.simplechat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
  //Hoehe des Fensters wird in der Variable angegeben
  private static final double window_height = 600;
  //Breite des Fensters wird in der Variable angegeben
  private static final double window_width = 400;
  //Fenstername kann heir angegeben werden
  private static final String application_title = "Simple TCP Chat";

  public static void main(String[] args) {
    launch();
  }
  //Die Methode bzw. Klasse wird automatisch erzeugt
  @Override
  public void start(Stage stage) throws Exception {
    //hier kann man die Datei angeben bzw. ändern, die der FMXLLoader beim "Laden" verwenden soll
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), window_height, window_width);
    stage.setTitle(application_title);
    stage.setScene(scene);
    stage.show();
  }
}
