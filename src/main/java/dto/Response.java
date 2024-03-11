package dto;

import constants.Constants;

import java.util.Map;

public class Response {

  private final String statusString;
  private final Map<String, String> headers;
  private final String content;

  public Response(String statusString, Map<String, String> headers, String content) {
    this.statusString = statusString;
    this.content = content;
    this.headers = headers;
  }

  public String getResponseString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.statusString).append(Constants.CRLF);

    for (Map.Entry<String, String> header : this.headers.entrySet()) {
      stringBuilder.append(header.getKey()).append(Constants.COLON).append(header.getValue())
          .append(Constants.CRLF);
    }
    stringBuilder.append(Constants.CRLF).append(this.content);
    return stringBuilder.toString();
  }
}
