package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Utils {

  private Utils() {}

  public static Boolean isNotEmpty(String inputString) {
    return inputString != null && !inputString.isEmpty();
  }

  public static String readLine(InputStream inputStream) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    int read;
    while ((read = inputStream.read()) >= 0) {
      if (read == '\r') {
        if (inputStream.read() != '\n')
          throw new IllegalStateException("invalid EOL");
        return buffer.toString(StandardCharsets.UTF_8);
      }
      buffer.write(read);
    }
    throw new IllegalStateException("missing EOL");
  }

  public static byte[] readFile(String directory, String fileName) {
    Path dirPath = Path.of(directory).toAbsolutePath().normalize();
    try {
      Path filePath = dirPath.resolve(fileName);
      if (filePath.startsWith(dirPath) && Files.isRegularFile(filePath)) return Files.readAllBytes(filePath);
      else return null;
    } catch (Exception e) {
      System.out.println("Exception in reading file: " + e.getMessage());
      return null;
    }
  }

  public static Boolean writeFile(String directory, String fileName, byte[] fileContent) {
    Path dirPath = Path.of(directory).toAbsolutePath().normalize();
    try {
      Path filePath = dirPath.resolve(fileName);
      if (filePath.startsWith(dirPath) && !Files.exists(filePath)) {
        Files.write(filePath, fileContent);
        return true;
      }
      else return false;
    } catch (Exception e) {
      System.out.println("Exception in reading file: " + e.getMessage());
      return false;
    }
  }
}
