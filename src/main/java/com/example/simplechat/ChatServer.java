package com.example.simplechat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import com.example.simplechat.utils.ControlsUtil;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
public class ChatServer implements Runnable {
  private int port;
  private Set<ChatClient> users = new HashSet<>();
  private static ServerSocket serverSocket;
  private TextArea output;
  private TextField userField;
  private ChatClient lastClient;

  public ChatServer(int port, TextArea output,TextField userField) {
    this.output = output;
    this.port = port;
    this.userField = userField;
    try {
      if (serverSocket == null) { // todo behandlung von anderem port
        serverSocket = new ServerSocket(port);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void broadcast(String message) {
    for (ChatClient client : users) {
        ControlsUtil.writeMessageToSocket(message,client.getBufferedWriter());
      }
    }
  
   //Methode erzeugt, um zu schauen, welcher Client sich gerade verbunden hat bzw. sich 
   //in der Liste befindet (wieder nur für uns)
    public ChatClient setText;
    public ChatClient rueckgabeUser() {
      // Iterator wird angefordert
      Iterator it = users.iterator();
      // HashSet wird mit dem Iterator durchlaufen
      while (it.hasNext()) {
        // Next gibt das aktuelle HashSet-Objekt zurück
        // und geht zum nächsten über
        setText = (ChatClient) it.next();

        // Ausgabe des jeweiligen HashSet-Elementes
        System.out.println(setText);
        return setText;
      }
      return setText;
    }
  
  //@SneakyThrows Annotation wird verwendet, um eine mögliche Exception auszulösen
  @SneakyThrows
  @Override
  public void run() {
    Platform.runLater(() -> {
      //Nachricht an textfeld gesendet, um anzuzeigen, dass Server bestimmten Port lauscht
      output.appendText("\"Chat Server is listening on port: " + port + "\n");
    });
    //endlos Schleife 
    while (true) {
      //Server wartet auf neue Verbindung vom Client
      var clientSocket = serverSocket.accept();
      //Sobald eine hergestellt wird, wird neues Client-Objekt erstellt
      lastClient = new ChatClient(clientSocket,output,userField.getText());
      //Client wird Liste users hinzugefügt
      users.add(lastClient);
      //neuer Thread erstellt, der Client startet
      Thread newThread = new Thread(lastClient);
      newThread.start();
      ControlsUtil.appendToMessageArea("[Server]", " new client connection recieved and client started",output);
      //methode wird aufgerufen
      //Methode ist nur zur Überprüfung für uns
      rueckgabeUser();
    }
  }
}
