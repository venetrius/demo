package arguewise.demo.exception;

public record ApiError(
        String message,
        int statusCode
) {}
