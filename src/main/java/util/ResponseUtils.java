package util;

import constants.Constants;
import constants.HttpStatus;
import dto.Response;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class ResponseUtils {
  private ResponseUtils() {}

  public static Response getOKResponse(String version) {
    return
        new Response(
            getStatusString(version, HttpStatus.OK_200.getStatusString()),
            new HashMap<>(),
            Constants.EMPTY_STRING);
  }

  public static Response getCreatedResponse(String version) {
    return
        new Response(
            getStatusString(version, HttpStatus.CREATED_201.getStatusString()),
            new HashMap<>(),
            Constants.EMPTY_STRING);
  }

  public static Response getBadRequestResponse(String version) {
    return
        new Response(
            getStatusString(version, HttpStatus.BAD_REQUEST_400.getStatusString()),
            new HashMap<>(),
            Constants.EMPTY_STRING);
  }

  public static Response getNotFoundResponse(String version) {
    return
        new Response(
            getStatusString(version, HttpStatus.NOT_FOUND_404.getStatusString()),
            new HashMap<>(),
            Constants.EMPTY_STRING);
  }

  public static Response getPlainTextResponse(String version, String content) {
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put("Content-Type", "text/plain");
    headerMap.put("Content-Length", String.valueOf(content.getBytes(StandardCharsets.UTF_8).length));

    return
        new Response(
            getStatusString(version, HttpStatus.OK_200.getStatusString()),
            headerMap,
            content);
  }

  public static Response getOctetStreamResponse(String version, byte[] content) {
    Map<String, String> headerMap = new HashMap<>();
    headerMap.put("Content-Type", "application/octet-stream");
    headerMap.put("Content-Length", String.valueOf(content.length));

    return
        new Response(
            getStatusString(version, HttpStatus.OK_200.getStatusString()),
            headerMap,
            new String(content));
  }

  private static String getStatusString(String version, String status) {
    return version + " " + status;
  }
}
