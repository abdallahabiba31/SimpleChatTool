package com.example.simplechat;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
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

  @SneakyThrows
  @Override
  public void run() {
    Platform.runLater(() -> {
      output.appendText("\"Chat Server is listening on port: " + port + "\n");
    });
    while (true) {
      var clientSocket = serverSocket.accept();
      lastClient = new ChatClient(clientSocket,output,userField.getText());
      Thread newThread = new Thread(lastClient);
      newThread.start();
      ControlsUtil.appendToMessageArea("[Server]", " new client connection recieved and client started",output);
    }
  }
}
