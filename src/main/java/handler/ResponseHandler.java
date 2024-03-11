package handler;

import constants.Constants;
import dto.Request;
import dto.Response;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

  public static Response getResponse(Request request) {
    String path = request.getPath();
    String version = request.getHttpVersion();

    Response response;
    if (path.equals(Constants.ROOT_PATH)) {
      response = getOKResponse(version);
    } else if (path.startsWith(Constants.ECHO_PATH)) {
      response = getContentResponse(version, path.substring(Constants.ECHO_PATH.length()));
    } else if (path.startsWith(Constants.USER_AGENT_PATH)) {
      response = getContentResponse(version, request.getHeaders().getOrDefault(Constants.USER_AGENT, Constants.EMPTY_STRING));
    } else {
      response = getNotFoundResponse(version);
    }
    return response;
  }

  private static Response getOKResponse(String version) {
    return new Response(getStatusString(version, Constants.OK_200), new HashMap<>(), Constants.EMPTY_STRING);
  }

  private static Response getNotFoundResponse(String version) {
    return new Response(getStatusString(version, Constants.NOT_FOUND_404), new HashMap<>(), Constants.EMPTY_STRING);
  }

  private static Response getContentResponse(String version, String content) {
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put("Content-Type", "text/plain");
    headerMap.put("Content-Length", String.valueOf(content.getBytes(StandardCharsets.UTF_8).length));

    return new Response(getStatusString(version, Constants.OK_200), headerMap, content);
  }

  private static String getStatusString(String version, String status) {
    return version + " " + status;
  }
}
