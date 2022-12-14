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

  private ChatServer server;
  private ChatClient client;

  @FXML
  protected void onSendButtonClick() {
    var userInput = input.getText();
    input.clear();
    ControlsUtil.appendToMessageArea(userInput, output);
    if (client != null) {
      ControlsUtil.writeMessageToSocket(userInput, client.getBufferedWriter());
    }
  }

  @FXML
  protected void onStartServerButtonClick() {
    var port = Integer.parseInt(portInput.getText());
    if (server == null) {  //||server.getPort() != port
      // über ein separaten Thread erstellt damit die gui nicht blockiert wird
      server = new ChatServer(port, output, username);
      Thread newThread = new Thread(server);
      newThread.start();
    }
  }

  @FXML
  protected void onCancelButtonClick() {
    var stage = (Stage) mainPanel.getScene().getWindow();
    stage.close();
  }

  @FXML
  protected void onConnectClick() throws IOException {
    var port = Integer.parseInt(portInput.getText());
    var host = hostInput.getText();
    var name = username.getText();
    if (name.isEmpty()) {
      output.setText("Please enter a valid user name");
    } else {
      if (client == null) {
        initConnection(host,port);
        client = server.getLastClient();
      }
    }

  }
  private void initConnection(String host,int port) throws IOException {
    new Socket(host, port);
  }
  @FXML
  public void onDisconnectClick() {
    //
    //ich weiß nicht, wie ich beim userThread erstmal einen Socket angebe
    //daher habe ich daweil port drinnen stehen
    //UserThread user = new UserThread(port, server);
    //server.removeUser(name, user);

  }
}