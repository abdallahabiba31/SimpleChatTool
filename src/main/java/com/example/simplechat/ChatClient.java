package com.example.simplechat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import com.example.simplechat.utils.ControlsUtil;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatClient implements Runnable {
  private int port;
  private String userName;
  private TextArea output;
  private TextField input;
  private BufferedReader bufferedReader;
  private BufferedWriter bufferedWriter;
  private Socket socket;

  //Kunstruktor zum erstellen eines ChatClients
  public ChatClient(Socket socket, TextArea output, String userName) {
    this.socket = socket;
    this.userName = userName;
    this.port = socket.getLocalPort();
    this.output = output;
  }
  //Aufgabe der Methode: alle drei übergebenen Objekte zu schließen
  public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
    try {
     //überprüft, ob jeder der übergebenen Parameter nicht null ist, 
     //bevor dessen close()-Methode aufgerufen wird
     //so wird sichergestellt, dass nur gültige Objekte geschlossen werden
      if (bufferedReader != null) {
        bufferedReader.close();
      }
      if (bufferedWriter != null) {
        bufferedWriter.close();
      }
      if (socket != null) {
        socket.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  
  
  @Override
  public void run() {
    try {
      //neuer BufferedWriter erstellt, der mit dem OutputStream des übergebenen Sockets verbunden ist
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      //Methode namens "appendToMessageArea" aufgerufen, die  Nachricht an  Textfeld mit dem Namen "output" sendet
      //um anzuzeigen, das Benutzer erfolgreich mit Server verbunden ist
      ControlsUtil.appendToMessageArea(userName," Connected to the chat server", output);
      //neuer BufferedReader erstellt, der mit  InputStream des Sockets verbunden ist
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      //while-Schleife, die so lange läuft, wie der Socket nicht geschlossen ist
      while (!socket.isClosed()) {
        //Innerhalb  Schleife wird überprüft, ob  BufferedReader bereit ist, Daten zu lesen
        while (bufferedReader.ready()) {
          //nächste Zeile aus dem Eingabestream wird gelesen 
          var input = bufferedReader.readLine();
          //Eingabstream an Methode namens "writeMessageToSocket" und "appendToMessageArea" übergeben
          ControlsUtil.writeMessageToSocket(input,bufferedWriter);
          ControlsUtil.appendToMessageArea(userName, "\n" + input, output);
        }
      }
    } catch (IOException e) {
      System.out.println("Error creating Client!");
      e.printStackTrace();
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }
}



