package dto;

import constants.Constants;
import util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Request {
  private final String method;
  private final String path;
  private final String httpVersion;
  private final Map<String, String> headers;
  private final byte[] body;

  public Request(InputStream inputStream) throws Exception {
    String[] parts = readStartLine(inputStream);
    this.method = parts[0];
    this.path = parts[1];
    this.httpVersion = parts[2];
    this.headers = readHeaders(inputStream);
    this.body = readBody(inputStream, this.headers.getOrDefault(Constants.CONTENT_LENGTH_HEADER, "0"));
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

  public byte[] getBody() {
    return this.body;
  }

  private static byte[] readBody(InputStream inputStream, String lengthString) throws IOException {
    int length;
    try {
      length = Integer.parseInt(lengthString);
    } catch (Exception e) {
      length = 0;
    }
    return inputStream.readNBytes(length);
  }

  private static Map<String, String> readHeaders(InputStream inputStream) throws IOException {
    String line;
    Map<String, String> headerMap = new HashMap<>();

    while (Utils.isNotEmpty(line = Utils.readLine(inputStream))) {
      String[] headerParts = line.split(Constants.COLON);
      if (headerParts.length > 1)
        headerMap.put(headerParts[0].strip().toLowerCase(), headerParts[1].strip());
    }

    return headerMap;
  }

  private static String[] readStartLine(InputStream inputStream) throws IOException {
    return Utils.readLine(inputStream).split("\\s+");
  }
}
