package com.example.simplechat.utils;

import java.io.BufferedWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;


//"ControlsUtil" ist eine Klasse die allgemeine oder nützliche Funktionalitäten bereitstellt
//die in verschiedenen Teilen der Anwendung verwendet werden können
//z.B eben Methoden zum Schreiben von Nachrichten an einen Socket, zum Anhängen von Nachrichten an ein Textfeld, usw.
@UtilityClass
public class ControlsUtil {

  //Mit der Methode wird die Message im textfeld/Output angezeigt
  public static void appendToMessageArea(String name, String message, TextArea area) {
    Platform.runLater(() -> {
      //Format, wie Message in Area ausgegebn werden soll
      area.appendText(name + ": ");
      //message wird angehängt und dann kommt Zeilenumbruch
      area.appendText(message + "\n");
    });
  }
  
  //Methode ist mit @SneakyThrows Annotation markiert, was bedeutet, dass  Methode mögliche Exceptions auslösen kann
  @SneakyThrows
  public static void writeMessageToSocket(String messageToServer, BufferedWriter bufferedWriter){
    try{
      
      //Der "bufferedWriter" ist Java-Klasse die Daten in  Puffer schreibt, bevor sie in Ausgabestream geschrieben werden
      //Dadurch werden Schreibvorgänge beschleunigt, da  Daten nicht jedes Mal direkt an  Ausgabestream geschrieben werden müssen, 
      //sondern erst im Puffer zwischengespeichert werdenn
      //hierwird Nachricht in den Puffer geschrieben, und dann wird die Nachricht an den Server geschickt 
      //--> indem  Puffer mit  Methode "flush()" geleert wird
      bufferedWriter.write(messageToServer);
      //neue Zeile hizufügen
      bufferedWriter.newLine();
      //Nachricht wird geflusht
      bufferedWriter.flush();
    }catch(IOException e){
      //wenn Exception auftritt, wird sie mit printStackTrace protokolliert
      e.printStackTrace();
      //folgende Fehlermeldung wird angezeigt
      System.out.println("Error sending message to the Client!");
      //BufferedReader wird geschlossen
      bufferedWriter.close();
    }
  }
}
