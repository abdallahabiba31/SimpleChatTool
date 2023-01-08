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

  public ChatClient(Socket socket, TextArea output, String userName) {
    this.socket = socket;
    this.userName = userName;
    this.port = socket.getLocalPort();
    this.output = output;
  }

  public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
    try {
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
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      ControlsUtil.appendToMessageArea("[Client - "+userName+" ] Connected to the chat server", output);
      this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      while (!socket.isClosed()) {
        while (bufferedReader.ready()) {
          var input = bufferedReader.readLine();
          ControlsUtil.writeMessageToSocket(input,bufferedWriter);
          ControlsUtil.appendToMessageArea("\n" + input, output);
        }
      }
    } catch (IOException e) {
      System.out.println("Error creating Client!");
      e.printStackTrace();
      closeEverything(socket, bufferedReader, bufferedWriter);
    }
  }
}



