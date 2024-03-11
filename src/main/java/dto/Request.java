package dto;

import constants.Constants;
import util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
  private final String method;
  private final String path;
  private final String httpVersion;
  private final Map<String, String> headers;

  public Request(BufferedReader inputReader) throws Exception {
    String[] parts = readStartLine(inputReader);
    this.method = parts[0];
    this.path = parts[1];
    this.httpVersion = parts[2];
    this.headers = readHeaders(inputReader);
  }

  public String getMethod() {
    return this.method;
  }

  public String getPath() {
    return this.path;
  }

  public String getHttpVersion() {
    return this.httpVersion;
  }

  public Map<String, String> getHeaders() {
    return this.headers;
  }

  private static Map<String, String> readHeaders(BufferedReader inputReader) throws IOException {
    String line;
    Map<String, String> headerMap = new HashMap<>();

    while (Utils.isNotEmpty(line = inputReader.readLine())) {
      String[] headerParts = line.split(Constants.COLON);
      if (headerParts.length > 1)
        headerMap.put(headerParts[0].strip().toLowerCase(), headerParts[1].strip());
    }

    return headerMap;
  }

  private static String[] readStartLine(BufferedReader inputReader) throws IOException {
    return inputReader.readLine().split("\\s+");
  }
}
