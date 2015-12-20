package ru.unn.agile.IntersectionFinder.viewmodel;

import ru.unn.agile.IntersectionFinder.model.*;

import java.util.List;

public class ViewModel {
    private String pointLineText;
    private String vectorLineText;
    private String pointPlaneText;
    private String normalPlaneText;
    private String result;
    private String status;
    private boolean isFinderButtonEnabled;
    private ILogger logger;
    private FocusedField focusedField;
    private boolean isCurrentFieldChanged;

    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger is null!");
        }

        isCurrentFieldChanged = false;
        this.logger = logger;
        pointLineText = "";
        vectorLineText = "";
        pointPlaneText = "";
        normalPlaneText = "";
        result = "";
        status = Status.EMPTY_FIELDS.toString();
        isFinderButtonEnabled = false;
    }

    public boolean isFinderButtonEnabled() {
        return isFinderButtonEnabled;
    }

    private boolean isInputEmpty() {
        return pointLineText.isEmpty() || vectorLineText.isEmpty()
                || pointPlaneText.isEmpty() || normalPlaneText.isEmpty();
    }

    private boolean isInputContainsIllegalData() {
        try {
            Vector3D tmpVector = new Vector3D(0.0, 0.0, 0.0);
            if (!pointLineText.isEmpty()) {
                tmpVector.parse(pointLineText);
            }
            if (!vectorLineText.isEmpty()) {
                tmpVector.parse(vectorLineText);
            }
            if (!pointPlaneText.isEmpty()) {
                tmpVector.parse(pointPlaneText);
            }
            if (!normalPlaneText.isEmpty()) {
                tmpVector.parse(normalPlaneText);
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    public boolean parseInput() {
        if (isInputContainsIllegalData()) {
            isFinderButtonEnabled = false;
            status = Status.INCORRECT_DATA.toString();
            return false;
        }
        if (isInputEmpty()) {
            isFinderButtonEnabled = false;
            status = Status.EMPTY_FIELDS.toString();
            return false;
        }
        isFinderButtonEnabled = true;
        status = Status.NO_ERROR.toString();
        return true;
    }

    public void findIntersection() {
        logger.log(findIntersectionMessage());
        Vector3D pointLine = new Vector3D(pointLineText);
        Vector3D vectorLine = new Vector3D(vectorLineText);
        Vector3D pointPlane = new Vector3D(pointPlaneText);
        Vector3D normalPlane = new Vector3D(normalPlaneText);
        Line line = new Line(pointLine, vectorLine);
        Plane plane = new Plane(pointPlane, normalPlane);
        IntersectionFinder intersectionFinder = new IntersectionFinder(line, plane);

        IntersectionFinder.TypeOfIntersection type = intersectionFinder.getTypeOfIntersection();
        Status currentStatus = Status.NO_ERROR;
        switch (type) {
            case NoIntersection:
                currentStatus = Status.NO_INTERSECTION;
                result = "";
                break;
            case LineOnThePlane:
                currentStatus = Status.PLANE_CONTAINS_LINE;
                result = "";
                break;
            case OneIntersection:
                currentStatus = Status.ONE_INTERSECTION;
                result = intersectionFinder.getIntersectionPoint().toString();
                break;
            default:
                break;
        }
        status = currentStatus.toString();
        logger.log(resultMessage(currentStatus) + result);
    }

    private String findIntersectionMessage() {
        String message = "Pressed FindIntersection button. Arguments: "
                + "pointLine(" + pointLineText
                + "); vectorLine(" + vectorLineText
                + "); pointPlane(" + pointPlaneText
                + "); normalPlane(" + normalPlaneText
                + ")";
        return message;
    }

    private String resultMessage(final Status status) {
        String message = "Result: " + status.toString();
        if (status == Status.ONE_INTERSECTION) {
            message += " Point = ";
        }
        return message;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public String getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public String getPointLine() {
        return pointLineText;
    }

    public void setPointLine(final String pointLineText) {
        if (pointLineText.equals(this.pointLineText)) {
            return;
        }

        isCurrentFieldChanged = true;
        focusedField = FocusedField.POINT_LINE;
        this.pointLineText = pointLineText;
    }

    public String getVectorLine() {
        return vectorLineText;
    }

    public void setVectorLine(final String vectorLineText) {
        if (vectorLineText.equals(this.vectorLineText)) {
            return;
        }

        isCurrentFieldChanged = true;
        focusedField = FocusedField.VECTOR_LINE;
        this.vectorLineText = vectorLineText;
    }

    public String getPointPlane() {
        return pointPlaneText;
    }

    public void setPointPlane(final String pointPlaneText) {
        if (pointPlaneText.equals(this.pointPlaneText)) {
            return;
        }

        isCurrentFieldChanged = true;
        focusedField = FocusedField.POINT_PLANE;
        this.pointPlaneText = pointPlaneText;
    }

    public String getNormalPlane() {
        return normalPlaneText;
    }

    public void setNormalPlane(final String normalPlane) {
        if (normalPlane.equals(this.normalPlaneText)) {
            return;
        }

        isCurrentFieldChanged = true;
        focusedField = FocusedField.NORMAL_PLANE;
        this.normalPlaneText = normalPlane;
    }

    public void lostFocus() {
        if (!isCurrentFieldChanged) {
            return;
        }
        String message = "Updated field " + focusedField.toString() + " with value (";
        switch (focusedField) {
            case POINT_LINE:
                message += pointLineText;
                break;
            case VECTOR_LINE:
                message += vectorLineText;
                break;
            case POINT_PLANE:
                message += pointPlaneText;
                break;
            case NORMAL_PLANE:
                message += normalPlaneText;
                break;
            default:
                break;
        }
        message += ")";
        isCurrentFieldChanged = false;
        logger.log(message);
    }

    enum Status {
        NO_ERROR(""),
        ONE_INTERSECTION("One Intersection!"),
        NO_INTERSECTION("No Intersection!"),
        PLANE_CONTAINS_LINE("Plane contains line!"),
        EMPTY_FIELDS("Empty fields! Format a;b;c"),
        INCORRECT_DATA("Incorrect data! Format a;b;c");

        private final String name;

        private Status(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    enum FocusedField {
        POINT_LINE("pointLine"),
        VECTOR_LINE("vectorLine"),
        POINT_PLANE("pointPlane"),
        NORMAL_PLANE("normalPlane");

        private final String name;

        private FocusedField(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    };
}
