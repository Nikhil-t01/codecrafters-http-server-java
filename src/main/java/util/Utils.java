package util;

public final class Utils {

  private Utils() {}

  public static Boolean isNotEmpty(String inputString) {
    return inputString != null && !inputString.isEmpty();
  }
}
