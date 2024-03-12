package handler;

import constants.Constants;
import dto.Request;
import dto.Response;
import util.Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

  public static Response getResponse(Request request, String directory) {
    String path = request.getPath();
    String version = request.getHttpVersion();

    Response response;
    if (path.equals(Constants.ROOT_PATH)) {
      response = getOKResponse(version);
    } else if (path.startsWith(Constants.ECHO_PATH)) {
      response = processEchoPath(request);
    } else if (path.startsWith(Constants.USER_AGENT_PATH)) {
      response = processUserAgentPath(request);
    } else if (path.startsWith(Constants.FILES_PATH)) {
      response = processFilesPath(request, directory);
    } else {
      response = getNotFoundResponse(version);
    }
    return response;
  }

  private static Response processEchoPath(Request request) {
    String content = request.getPath().substring(Constants.ECHO_PATH.length());
    return getPlainTextResponse(request.getHttpVersion(), content);
  }

  private static Response processUserAgentPath(Request request) {
    String content = request.getHeaders().getOrDefault(Constants.USER_AGENT, Constants.EMPTY_STRING);
    return getPlainTextResponse(request.getHttpVersion(), content);
  }

  private static Response processFilesPath(Request request, String directory) {
    String fileName = request.getPath().substring(Constants.FILES_PATH.length());

    byte[] fileData = Utils.readFile(directory, fileName);
    if (fileData == null) return getNotFoundResponse(request.getHttpVersion());
    else return getOctetStreamResponse(request.getHttpVersion(), fileData);
  }

  private static Response getOKResponse(String version) {
    return new Response(getStatusString(version, Constants.OK_200), new HashMap<>(), Constants.EMPTY_STRING);
  }

  private static Response getNotFoundResponse(String version) {
    return new Response(getStatusString(version, Constants.NOT_FOUND_404), new HashMap<>(), Constants.EMPTY_STRING);
  }

  private static Response getPlainTextResponse(String version, String content) {
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put("Content-Type", "text/plain");
    headerMap.put("Content-Length", String.valueOf(content.getBytes(StandardCharsets.UTF_8).length));

    return new Response(getStatusString(version, Constants.OK_200), headerMap, content);
  }

  private static Response getOctetStreamResponse(String version, byte[] content) {
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put("Content-Type", "application/octet-stream");
    headerMap.put("Content-Length", String.valueOf(content.length));

    return new Response(getStatusString(version, Constants.OK_200), headerMap, new String(content));
  }

  private static String getStatusString(String version, String status) {
    return version + " " + status;
  }
}
