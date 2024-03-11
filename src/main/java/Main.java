import constants.Constants;
import dto.Request;
import service.ResponseHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
      serverSocket.setReuseAddress(true);
      Socket clientSocket = serverSocket.accept(); // Wait for connection from client.

      try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
        Request request = new Request(inputReader);
        String response = ResponseHandler.getResponse(request).getResponseString();
        clientSocket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
      } catch (Exception e) {
        System.out.print("Exception in reading input: " + e.getMessage());
      }
      System.out.println("accepted new connection");
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
