package handler;

import dto.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable {
  private final Socket clientSocket;
  private final String directory;

  public ClientHandler(Socket clientSocket, String directory) {
    this.clientSocket = clientSocket;
    this.directory = directory;
  }

  @Override
  public void run() {
    try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()))) {
      Request request = new Request(inputReader);
      String response = ResponseHandler.getResponse(request, this.directory).getResponseString();
      this.clientSocket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      System.out.print("Exception in reading input: " + e.getMessage());
    }
  }
}
