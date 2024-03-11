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

    try (ServerSocket serverSocket = new ServerSocket(4221)) {
      serverSocket.setReuseAddress(true);
      Socket clientSocket = serverSocket.accept(); // Wait for connection from client.

      try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
        String startLine = inputReader.readLine();
        String[] parts = startLine.split("\\s+");

        if (parts.length >= 2) {
         Boolean isPathMatched = "/".equals(parts[1]);
          clientSocket.getOutputStream().write(getResponse(isPathMatched).getBytes(StandardCharsets.UTF_8));
        }
      } catch (Exception e) {
        System.out.print("Exception in reading input: " + e.getMessage());
      }
      System.out.println("accepted new connection");
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }

  private static String getResponse(Boolean isPathMatched) {
    return isPathMatched ? "HTTP/1.1 200 OK\r\n\r\n" : "HTTP/1.1 404 Not Found\r\n\r\n";
  }
}
