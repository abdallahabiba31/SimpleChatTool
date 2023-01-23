package com.example.simplechat;

import java.io.IOException;
import java.net.Socket;
import com.example.simplechat.utils.ControlsUtil;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatController {
  @FXML
  private TextArea output;
  @FXML
  private TextField input;
  @FXML
  private TextField portInput;
  @FXML
  private TextField hostInput;
  @FXML
  private BorderPane mainPanel;
  @FXML
  private TextField username;
  private String clientName;

  private ChatServer server;
  private ChatClient client;

  //@FXML-Annotation bedeutet, dass  Methoden in einer JavaFX-Anwendung als Reaktion auf bestimmte Benutzerinteraktionen aufgerufen werden
  //z.B. auf das Klicken einer Schaltfläche
  @FXML
  //wird aufgerufen, wenn der Benutzer auf  Schaltfläche "Senden" klickt. 
  protected void onSendButtonClick() {
    //Text, der vom Benutzer in das Textfeld "input" eingegeben wird, wird abgerufen
    var userInput = input.getText();
    //Textfeld geleert
    input.clear();
    //Text an Methode "appendToMessageArea" übergeben, um in Textfeld "output anzuzeigen"
    ControlsUtil.appendToMessageArea(clientName, userInput, output);
    //Wenn "client" Objekt vorhanden ist
    if (client != null) {
      //wird  Text auch an  Methode "writeMessageToSocket" übergeben, um ihn an den Server zu senden
      ControlsUtil.writeMessageToSocket(userInput, client.getBufferedWriter());
    }
  }

  
  //Methode wird aufgerufen, wenn  Benutzer auf die Schaltfläche "Server starten" klickt
  @FXML
  protected void onStartServerButtonClick() {
    //Port aus dem Textfeld "portInput" abgerufen und in einen Integer umgewandelt
    var port = Integer.parseInt(portInput.getText());
   // Wenn  "server" Objekt null ist
    if (server == null) {  //||server.getPort() != port
      //wird  neues ChatServer-Objekt erstellt
      // über ein separaten Thread erstellt und gestartet damit die gui nicht blockiert wird
      server = new ChatServer(port, output, username);
      Thread newThread = new Thread(server);
      newThread.start();
    }
  }

  //Methode wird aufgerufen, wenn  Benutzer auf die Schaltfläche "Abbrechen" klickt
  @FXML
  protected void onCancelButtonClick() {
    //das Fenster, in dem die Anwendung ausgeführt wird, wird geschlossen
    var stage = (Stage) mainPanel.getScene().getWindow();
    stage.close();
  }

  //Methode aufgerufen, wenn  Benutzer auf  Schaltfläche "Verbinden" klickt
  @FXML
  protected void onConnectClick() throws IOException {
    //Port abgerufen und in INT umgewandelt
    var port = Integer.parseInt(portInput.getText());
    //Host wird abgerufen
    var host = hostInput.getText();
   //username wird abgerufen 
    var name = username.getText();
   //Wenn Benutzername nicht leer
    if (name.isEmpty()) {
      output.setText("Please enter a valid user name");
    } else {
      //und client objekt null ist
      if (client == null) {
        //wird Methode "initConnection" mit host und port als Parameter aufgerufen
        initConnection(host,port);
        client = server.getLastClient();
      }
    }
    clientName=name;

  }
  private void initConnection(String host,int port) throws IOException {
    new Socket(host, port);
  }
  
  //Methode wird aufgerufen, wenn  Benutzer auf  Schaltfläche "disconnect" klickt
  @FXML
  public void onDisconnectClick() {
    //Client wird aus  Liste der Benutzer entfernt
    server.getUsers().remove(client);

    var ka = server.getUsers().toString();
    //Meldung ausgegeben 
    ControlsUtil.appendToMessageArea(ka, " : hat sich disconnected", output);

  }
}
