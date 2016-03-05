package ru.unn.agile.Huffman.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.ComplexNumber.viewmodel.legacy.ViewModel.Operation;
import ru.unn.agile.ComplexNumber.viewmodel.legacy.ViewModel.Status;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;
    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Before
    public void setUp() {
        FakeLogger logger = new FakeLogger();
        viewModel = new ViewModel(logger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getHuffmanString());
        assertEquals("", viewModel.getResult());
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingInTheBeginning() {
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();

        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAreFill() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(Status.READY, viewModel.getStatus());
    }

    private void fillInputFields() {
        viewModel.setHuffmanString("I'm Yaroslav Karpunin");

    }


    @Test
    public void canCleanStatus() {
        viewModel.setHuffmanString("I'm not Yaroslav Karpunin");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);
        viewModel.setHuffmanString("I'm Yaroslav Karpunin");
        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isCalculateButtonDisabledInitially() {
        assertEquals(false, viewModel.isCalculateButtonEnabled());
    }



    @Test
    public void isCalculateButtonEnabledWithCorrectInput() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(true, viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void canPerformCalcAction() {
        viewModel.setHuffmanString("setHuffmanString");
         viewModel.calculate();

    }

    @Test
    public void canSetSuccessMessage() {
        fillInputFields();

        viewModel.calculate();

        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }



    @Test
    public void isStatusReadyWhenKeyIsNotEnter() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ANY);

        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isStatusSuccessWhenKeyIsEnter() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER);

        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }
    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new ViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void isLogEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertEquals(0, log.size());
    }

    @Test
    public void isCalculatePuttingSomething() {
        viewModel.calculate();

        List<String> log = viewModel.getLog();
        assertNotEquals(0, log.size());
    }

    @Test
    public void isLogContainsProperMessage() {
        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertThat(message,
                matchesPattern(".*" + ViewModel.LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void isLogContainsInputArguments() {
        fillInputFields();

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + viewModel.getHaffmanString()));
    }





    @Test
    public void canPutSeveralLogMessages() {
        fillInputFields();

        viewModel.calculate();
        viewModel.calculate();
        viewModel.calculate();

        assertEquals(3, viewModel.getLog().size());
    }

    @Test
    public void isOperationNotLoggedWhenNotChanged() {
        viewModel.setOperation(ViewModel.Operation.MULTIPLY);
        viewModel.setOperation(ViewModel.Operation.MULTIPLY);

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void isEditingFinishLogged() {
        viewModel.setHuffmanString("Input argument is: ");

        viewModel.focusLost();

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void areArgumentsCorrectlyLoggedOnEditingFinish() {
        fillInputFields();
        viewModel.focusLost();

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED
                + "Input argument is: "
                + viewModel.getHuffmanString()));
    }

    @Test
    public void isLogInputsCalledOnEnter() {
        fillInputFields();

        viewModel.processKeyInTextField(KeyboardKeys.ENTER);

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void isCalculateNotCalledWhenButtonIsDisabled() {
        viewModel.processKeyInTextField(KeyboardKeys.ENTER);

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void doNotLogSameParametersTwice() {
        fillInputFields();
        fillInputFields();

        viewModel.focusLost();
        viewModel.focusLost();

        String message = viewModel.getLog().get(0);
        assertThat(message, matchesPattern(".*" + ViewModel.LogMessages.EDITING_FINISHED + ".*"));
        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void doNotLogSameParametersTwiceWithPartialInput() {
        viewModel.setHuffmanString("test");

        viewModel.focusLost();

        assertEquals(1, viewModel.getLog().size());
    }
}
