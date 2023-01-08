package com.example.simplechat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
  private static final double window_height = 600;
  private static final double window_width = 400;
  private static final String application_title = "Simple TCP Chat";

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), window_height, window_width);
    stage.setTitle(application_title);
    stage.setScene(scene);
    stage.show();
  }
}
