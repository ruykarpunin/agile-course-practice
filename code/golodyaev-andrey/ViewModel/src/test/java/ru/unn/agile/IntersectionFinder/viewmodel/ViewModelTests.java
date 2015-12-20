package ru.unn.agile.IntersectionFinder.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        FakeLogger fakeLogger = new FakeLogger();
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
    public void canSetDefaultStatus() {
        assertEquals(ViewModel.Status.EMPTY_FIELDS.toString(), viewModel.getStatus());
    }

    @Test
    public void isFinderButtonDisabledInTheBeginning() {
        assertFalse(viewModel.isFinderButtonEnabled());
    }

    @Test
    public void errorTextIsEmptyWhenFieldsContainCorrectData() {
        fillCorrectInputFields();

        viewModel.parseInput();

        assertEquals("", viewModel.getStatus());
    }

    @Test
    public void finderButtonIsEnabledWhenFieldsContainCorrectData() {
        fillCorrectInputFields();

        viewModel.parseInput();

        assertTrue(viewModel.isFinderButtonEnabled());
    }

    @Test
    public void checkStatusTextWhenFieldsAreEmpty() {
        fillEmptyInputFields();

        viewModel.parseInput();

        assertEquals(ViewModel.Status.EMPTY_FIELDS.toString(), viewModel.getStatus());
    }

    @Test
    public void finderButtonIsDisabledWhenFieldsContainCorrectData() {
        fillEmptyInputFields();

        viewModel.parseInput();

        assertFalse(viewModel.isFinderButtonEnabled());
    }

    @Test
    public void checkStatusTextWhenFieldsContainIncorrectData() {
        fillCorrectInputFields();
        viewModel.setPointPlane("1; 2; a");

        viewModel.parseInput();

        assertEquals(ViewModel.Status.INCORRECT_DATA.toString(), viewModel.getStatus());
    }

    @Test
    public void finderButtonIsDisabledWhenFieldsContainIncorrectData() {
        fillCorrectInputFields();
        viewModel.setPointPlane("1; 2; a");

        viewModel.parseInput();

        assertFalse(viewModel.isFinderButtonEnabled());
    }

    @Test
    public void checkStatusTextWhenOneIntersection() {
        fillCorrectInputFields();

        viewModel.findIntersection();

        assertEquals(ViewModel.Status.ONE_INTERSECTION.toString(), viewModel.getStatus());
    }

    @Test
    public void checkStatusTextWhenNoIntersection() {
        fillInputFieldsNoIntersection();

        viewModel.findIntersection();

        assertEquals(ViewModel.Status.NO_INTERSECTION.toString(), viewModel.getStatus());
    }

    @Test
    public void checkStatusTextWhenPlaneContainsLine() {
        fillInputFieldsLineOnThePlane();

        viewModel.findIntersection();

        assertEquals(ViewModel.Status.PLANE_CONTAINS_LINE.toString(), viewModel.getStatus());
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

        String focusedField = ViewModel.FocusedField.POINT_LINE.toString();
        String fieldText = viewModel.getPointLine();

        String log = viewModel.getLog().get(0);
        assertThat(log, containsString(focusedField));
        assertThat(log, containsString(fieldText));
    }

    @Test
    public void isLogCorrectWhenFillVectorLineField() {
        viewModel.setVectorLine("1; 2; 3");
        viewModel.lostFocus();
        String message = viewModel.getLog().get(0);

        String focusedField = ViewModel.FocusedField.VECTOR_LINE.toString();
        String fieldText = viewModel.getVectorLine();

        String log = viewModel.getLog().get(0);
        assertThat(log, containsString(focusedField));
        assertThat(log, containsString(fieldText));
    }

    @Test
    public void isLogCorrectWhenFillPointPlaneField() {
        viewModel.setPointPlane("1; 2; 3");
        viewModel.lostFocus();
        String message = viewModel.getLog().get(0);

        String focusedField = ViewModel.FocusedField.POINT_PLANE.toString();
        String fieldText = viewModel.getPointPlane();

        String log = viewModel.getLog().get(0);
        assertThat(log, containsString(focusedField));
        assertThat(log, containsString(fieldText));
    }

    @Test
    public void isLogCorrectWhenFillNormalPlaneField() {
        viewModel.setNormalPlane("1; 2; 3");
        viewModel.lostFocus();
        String message = viewModel.getLog().get(0);

        String focusedField = ViewModel.FocusedField.NORMAL_PLANE.toString();
        String fieldText = viewModel.getNormalPlane();

        String log = viewModel.getLog().get(0);
        assertThat(log, containsString(focusedField));
        assertThat(log, containsString(fieldText));
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

        assertThat(message, containsString(ViewModel.Status.NO_INTERSECTION.toString()));
    }

    @Test
    public void canLogOneIntersectionResult() {
        fillCorrectInputFields();

        viewModel.findIntersection();
        String message = viewModel.getLog().get(1);

        assertThat(message, containsString(ViewModel.Status.ONE_INTERSECTION.toString()));
    }

    @Test
    public void canLogLineOnThePlaneResult() {
        fillInputFieldsLineOnThePlane();

        viewModel.findIntersection();
        String message = viewModel.getLog().get(1);

        assertThat(message, containsString(ViewModel.Status.PLANE_CONTAINS_LINE.toString()));
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
