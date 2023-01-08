package com.example.simplechat.utils;

import java.io.BufferedWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ControlsUtil {

  public static void appendToMessageArea(String message, TextArea area) {
    Platform.runLater(() -> {
      area.appendText(message + "\n");
    });
  }
  @SneakyThrows
  public static void writeMessageToSocket(String messageToServer, BufferedWriter bufferedWriter){
    try{
      bufferedWriter.write(messageToServer);
      bufferedWriter.newLine();
      bufferedWriter.flush();
    }catch(IOException e){
      e.printStackTrace();
      System.out.println("Error sending message to the Client!");
      bufferedWriter.close();
    }
  }
}
