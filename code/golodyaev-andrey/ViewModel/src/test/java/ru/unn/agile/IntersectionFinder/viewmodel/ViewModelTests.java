package ru.unn.agile.IntersectionFinder.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        FakeFinderLogger fakeLogger = new FakeFinderLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultPointLine() {
        assertEquals("", viewModel.getPointLine());
    }

    @Test
    public void canSetDefaultVectorLine() {
        assertEquals("", viewModel.getVectorLine());
    }

    @Test
    public void canSetDefaultPointPlane() {
        assertEquals("", viewModel.getPointPlane());
    }

    @Test
    public void canSetDefaultNormalPlane() {
        assertEquals("", viewModel.getNormalPlane());
    }

    @Test
    public void canSetDefaultResult() {
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void canSetDefaultError() {
        assertEquals(ViewModel.ErrorStatus.EMPTY_FIELDS.toString(), viewModel.getError());
    }

    @Test
    public void isFinderButtonDisabledInTheBeginning() {
        assertFalse(viewModel.isFinderButtonEnabled());
    }

    @Test
    public void errorTextIsEmptyWhenFieldsContainCorrectData() {
        fillCorrectInputFields();

        viewModel.parseInput();

        assertEquals("", viewModel.getError());
    }

    @Test
    public void finderButtonIsEnabledWhenFieldsContainCorrectData() {
        fillCorrectInputFields();

        viewModel.parseInput();

        assertTrue(viewModel.isFinderButtonEnabled());
    }

    @Test
    public void checkErrorTextWhenFieldsAreEmpty() {
        fillEmptyInputFields();

        viewModel.parseInput();

        assertEquals(ViewModel.ErrorStatus.EMPTY_FIELDS.toString(), viewModel.getError());
    }

    @Test
    public void finderButtonIsDisabledWhenFieldsContainCorrectData() {
        fillEmptyInputFields();

        viewModel.parseInput();

        assertFalse(viewModel.isFinderButtonEnabled());
    }

    @Test
    public void checkErrorTextWhenFieldsContainIncorrectData() {
        fillCorrectInputFields();
        viewModel.setPointPlane("1; 2; a");

        viewModel.parseInput();

        assertEquals(ViewModel.ErrorStatus.INCORRECT_DATA.toString(), viewModel.getError());
    }

    @Test
    public void finderButtonIsDisabledWhenFieldsContainIncorrectData() {
        fillCorrectInputFields();
        viewModel.setPointPlane("1; 2; a");

        viewModel.parseInput();

        assertFalse(viewModel.isFinderButtonEnabled());
    }

    @Test
    public void checkErrorTextWhenOneIntersection() {
        fillCorrectInputFields();

        viewModel.findIntersection();

        assertEquals(ViewModel.ErrorStatus.NO_ERROR.toString(), viewModel.getError());
    }

    @Test
    public void checkErrorTextWhenNoIntersection() {
        fillInputFieldsNoIntersection();

        viewModel.findIntersection();

        assertEquals(ViewModel.ErrorStatus.NO_INTERSECTION.toString(), viewModel.getError());
    }

    @Test
    public void checkErrorTextWhenPlaneContainsLine() {
        fillInputFieldsLineOnThePlane();

        viewModel.findIntersection();

        assertEquals(ViewModel.ErrorStatus.PLANE_CONTAINS_LINE.toString(), viewModel.getError());
    }

    @Test
    public void resultIsCorrectWhenOneIntersection() {
        fillCorrectInputFields();

        viewModel.findIntersection();

        assertEquals("0.0; 0.0; 0.0", viewModel.getResult());
    }

    @Test
    public void resultIsEmptyWhenNoIntersection() {
        fillCorrectInputFields();
        viewModel.findIntersection();
        fillInputFieldsNoIntersection();

        viewModel.findIntersection();

        assertEquals("", viewModel.getResult());
    }

    @Test
    public void resultIsEmptyWhenPlaneContainsLine() {
        fillCorrectInputFields();
        viewModel.findIntersection();
        fillInputFieldsLineOnThePlane();

        viewModel.findIntersection();

        assertEquals("", viewModel.getResult());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        assertNotNull(viewModel);
    }

    @Test(expected = Exception.class)
    public void canConstructorThrowExceptionWithNullLoggerParameter() {
        viewModel = new ViewModel(null);
    }

    @Test
    public void isDefaultLogEmpty() {
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isLogNotEmptyWhenPressedButton() {
        fillCorrectInputFields();
        viewModel.findIntersection();
        List<String> log = viewModel.getLog();

        assertNotEquals(0, log.size());
    }

    @Test
    public void isLogCorrectWhenFillPointLineField() {
        viewModel.setPointLine("1; 2; 3");
        viewModel.lostFocus();
        String message = viewModel.getLog().get(0);

        int index1, index2;
        index1 = message.indexOf(ViewModel.FocusedField.POINT_LINE.toString());
        index2 = message.indexOf(viewModel.getPointLine());

        assertTrue(index1 >= 0 && index2 >= 0);
    }

    @Test
    public void isLogCorrectWhenFillVectorLineField() {
        viewModel.setVectorLine("1; 2; 3");
        viewModel.lostFocus();
        String message = viewModel.getLog().get(0);

        int index1, index2;
        index1 = message.indexOf(ViewModel.FocusedField.VECTOR_LINE.toString());
        index2 = message.indexOf(viewModel.getVectorLine());

        assertTrue(index1 >= 0 && index2 >= 0);
    }

    @Test
    public void isLogCorrectWhenFillPointPlaneField() {
        viewModel.setPointPlane("1; 2; 3");
        viewModel.lostFocus();
        String message = viewModel.getLog().get(0);

        int index1, index2;
        index1 = message.indexOf(ViewModel.FocusedField.POINT_PLANE.toString());
        index2 = message.indexOf(viewModel.getPointPlane());

        assertTrue(index1 >= 0 && index2 >= 0);
    }

    @Test
    public void isLogCorrectWhenFillNormalPlaneField() {
        viewModel.setNormalPlane("1; 2; 3");
        viewModel.lostFocus();
        String message = viewModel.getLog().get(0);

        int index1, index2;
        index1 = message.indexOf(ViewModel.FocusedField.NORMAL_PLANE.toString());
        index2 = message.indexOf(viewModel.getNormalPlane());

        assertTrue(index1 >= 0 && index2 >= 0);
    }

    @Test
    public void canLogSeveralEvents() {
        viewModel.setNormalPlane("1; 2; 3");
        viewModel.lostFocus();
        fillCorrectInputFields();
        viewModel.findIntersection();
        List<String> log = viewModel.getLog();

        assertEquals(3, log.size());
    }

    @Test
    public void isOperationNotLoggedWhenFocusDidntLost() {
        viewModel.setPointLine("1; 2; 3");
        viewModel.setVectorLine("1; 2; 3");
        viewModel.setPointPlane("1; 2; 3");
        viewModel.setNormalPlane("1; 2; 3");
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isOperationNotLoggedWhenInputSameData() {
        viewModel.setPointLine("1; 2; 3");
        viewModel.lostFocus();
        viewModel.setPointLine("1; 2; 3");
        viewModel.lostFocus();
        List<String> log = viewModel.getLog();

        assertEquals(1, log.size());
    }

    @Test
    public void canLogNoIntersectionResult() {
        fillInputFieldsNoIntersection();

        viewModel.findIntersection();
        String message = viewModel.getLog().get(1);

        assertTrue(message.indexOf("No Intersection!") >= 0);
    }

    @Test
    public void canLogOneIntersectionResult() {
        fillCorrectInputFields();

        viewModel.findIntersection();
        String message = viewModel.getLog().get(1);

        assertTrue(message.indexOf("One Intersection!") >= 0);
    }

    @Test
    public void canLogLineOnThePlaneResult() {
        fillInputFieldsLineOnThePlane();

        viewModel.findIntersection();
        String message = viewModel.getLog().get(1);

        assertTrue(message.indexOf("Plane Contains Line!") >= 0);
    }

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }
    private void fillEmptyInputFields() {
        viewModel.setPointLine("");
        viewModel.setVectorLine("");
        viewModel.setPointPlane("");
        viewModel.setNormalPlane("");
    }

    private void fillCorrectInputFields() {
        viewModel.setPointLine("0; 0; 0");
        viewModel.setVectorLine("0; 0; 1");
        viewModel.setPointPlane("0; 0; 0");
        viewModel.setNormalPlane("0; 0; 1");
    }

    private void fillInputFieldsNoIntersection() {
        viewModel.setPointLine("0; 0; 1");
        viewModel.setVectorLine("0; 1; 0");
        viewModel.setPointPlane("0; 0; 0");
        viewModel.setNormalPlane("0; 0; 1");
    }

    private void fillInputFieldsLineOnThePlane() {
        viewModel.setPointLine("0; 0; 0");
        viewModel.setVectorLine("0; 1; 0");
        viewModel.setPointPlane("0; 0; 0");
        viewModel.setNormalPlane("0; 0; 1");
    }
}
