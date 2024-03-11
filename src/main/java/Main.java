import constants.Constants;

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
        String startLine = inputReader.readLine();
        String[] parts = startLine.split("\\s+");

        if (parts.length >= 2) {
          String response = getResponse(parts[1]);
          clientSocket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
        }
      } catch (Exception e) {
        System.out.print("Exception in reading input: " + e.getMessage());
      }
      System.out.println("accepted new connection");
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }

  private static String getResponse(String path) {
    if (path.equals("/")) return getOKResponse();
    else if (path.startsWith(Constants.ECHO)) return getContentResponse(path);
    else return getNotFoundResponse();
  }

  private static String getOKResponse() {
    return "HTTP/1.1 200 OK" + Constants.CRLF + Constants.CRLF;
  }

  private static String getNotFoundResponse() {
    return "HTTP/1.1 404 Not Found" + Constants.CRLF + Constants.CRLF;
  }

  private static String getContentResponse(String path) {
    String content = path.substring(Constants.ECHO.length());
    return "HTTP/1.1 200 OK"
        + Constants.CRLF
        + "Content-Type: text/plain"
        + Constants.CRLF
        + String.format("Content-Length: %d", content.getBytes(StandardCharsets.UTF_8).length)
        + Constants.CRLF
        + Constants.CRLF
        + content;
  }
}
