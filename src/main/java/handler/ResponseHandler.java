package handler;

import constants.Constants;
import dto.Request;
import dto.Response;
import util.ResponseUtils;
import util.Utils;

public class ResponseHandler {

  public static Response getResponse(Request request, String directory) {
    String path = request.getPath();
    String version = request.getHttpVersion();

    Response response;
    if (path.equals(Constants.ROOT_PATH)) {
      response = ResponseUtils.getOKResponse(version);
    } else if (path.startsWith(Constants.ECHO_PATH)) {
      response = processEchoPath(request);
    } else if (path.startsWith(Constants.USER_AGENT_PATH)) {
      response = processUserAgentPath(request);
    } else if (path.startsWith(Constants.FILES_PATH)) {
      response = processFilesPath(request, directory);
    } else {
      response = ResponseUtils.getNotFoundResponse(version);
    }
    return response;
  }

  private static Response processEchoPath(Request request) {
    String content = request.getPath().substring(Constants.ECHO_PATH.length());
    return ResponseUtils.getPlainTextResponse(request.getHttpVersion(), content);
  }

  private static Response processUserAgentPath(Request request) {
    String content = request.getHeaders().getOrDefault(Constants.USER_AGENT_HEADER, Constants.EMPTY_STRING);
    return ResponseUtils.getPlainTextResponse(request.getHttpVersion(), content);
  }

  private static Response processFilesPath(Request request, String directory) {
    String fileName = request.getPath().substring(Constants.FILES_PATH.length());

    if (request.getMethod().equals(Constants.METHOD_GET)) {
      return processGetFile(request.getHttpVersion(), directory, fileName);
    } else if (request.getMethod().equals(Constants.METHOD_POST)) {
      return processPostFile(request.getHttpVersion(), directory, fileName, request.getBody());
    } else {
      return ResponseUtils.getBadRequestResponse(request.getHttpVersion());
    }
  }

  private static Response processGetFile(String version, String directory, String fileName) {
    byte[] fileData = Utils.readFile(directory, fileName);
    if (fileData == null) return ResponseUtils.getNotFoundResponse(version);
    else return ResponseUtils.getOctetStreamResponse(version, fileData);
  }

  private static Response processPostFile(String version, String directory, String fileName, byte[] fileContent) {
    Boolean isWritten = Utils.writeFile(directory, fileName, fileContent);
    if (isWritten) return ResponseUtils.getCreatedResponse(version);
    else return ResponseUtils.getBadRequestResponse(version);
  }
}
