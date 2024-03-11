package service;

import constants.Constants;
import dto.Request;
import dto.Response;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

  public static Response getResponse(Request request) {
    String path = request.getPath();
    if (path.equals("/")) {
      return getOKResponse();
    } else if (path.startsWith(Constants.ECHO_PATH)) {
      return getContentResponse(path.substring(Constants.ECHO_PATH.length()));
    } else if (path.startsWith(Constants.USER_AGENT_PATH)) {
      return getContentResponse(request.getHeaders().getOrDefault(Constants.USER_AGENT, Constants.EMPTY_STRING));
    } else return getNotFoundResponse();
  }

  private static Response getOKResponse() {
    return new Response("HTTP/1.1 200 OK", new HashMap<>(), Constants.EMPTY_STRING);
  }

  private static Response getNotFoundResponse() {
    return new Response("HTTP/1.1 404 Not Found", new HashMap<>(), Constants.EMPTY_STRING);
  }

  private static Response getContentResponse(String content) {
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put("Content-Type", "text/plain");
    headerMap.put("Content-Length", String.valueOf(content.getBytes(StandardCharsets.UTF_8).length));

    return new Response("HTTP/1.1 200 OK", headerMap, content);
  }
}
