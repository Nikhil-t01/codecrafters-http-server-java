package constants;

public enum HttpStatus {
  OK_200("OK", 200),
  CREATED_201("Created", 201),
  BAD_REQUEST_400("Bad Request", 400),
  NOT_FOUND_404("Not Found", 404);

  private final String status;
  private final int code;

  HttpStatus(String status, int code) {
    this.status = status;
    this.code = code;
  }

  public String getStatusString() {
    return this.code + Constants.SPACE_STRING + this.status;
  }
}
