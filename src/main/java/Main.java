import constants.Constants;
import handler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

  private static final ExecutorService EXECUTORS = Executors.newCachedThreadPool();

  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    try (ServerSocket serverSocket = new ServerSocket(Constants.PORT)) {
      serverSocket.setReuseAddress(true);
      while (true) {
        Socket clientSocket = serverSocket.accept(); // Wait for connection from client.
        System.out.println("accepted new connection");
        EXECUTORS.submit(new ClientHandler(clientSocket));
      }
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
