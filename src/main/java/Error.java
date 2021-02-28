import java.util.Map;

public class Error {
    private final int code1;
    private final int code2;
    ErrorType errorType;

    public Error(ErrorType errorType) {
        this.errorType = errorType;
        this.code1 = 0;
        this.code2 = 0;
    }

    public Error(ErrorType errorType, int code1) {
        this.errorType = errorType;
        this.code1 = code1;
        this.code2 = 0;
    }

    public Error(ErrorType errorType, int code1, int code2) {
        this.errorType = errorType;
        this.code1 = code1;
        this.code2 = code2;
    }

    public String errorMessage() {
        switch (errorType) {
            case OfferingNotFound -> {
                return "OfferingNotFound";
            }
            case StudentNotFound -> {
                return "StudentNotFound";
            }
            case MinimumUnitsError -> {
                return "MinimumUnitsError";
            }
            case MaximumUnitsError -> {
                return "MaximumUnitsError";
            }
            case ClassTimeCollisionError -> {
                return "ClassTimeCollisionError " + code1 + " " + code2;
            }
            case ExamTimeCollisionError -> {
                return "ExamTimeCollisionError " + code1 + " " + code2;
            }
            case CapacityError -> {
                return "CapacityError " + code1;
            }
            default -> {
                return null;
            }
        }
    }
}
