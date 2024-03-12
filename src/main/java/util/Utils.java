package util;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Utils {

  private Utils() {}

  public static Boolean isNotEmpty(String inputString) {
    return inputString != null && !inputString.isEmpty();
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
}
