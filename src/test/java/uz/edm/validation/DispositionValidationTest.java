package uz.edm.validation;

import uz.edm.model.dto.DispositionDto;
import jakarta.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class DispositionValidationTest {

    @Autowired
    DispositionValidation dispositionValidation;

    @Test
    void assertThatWhenDispositionPassValidationThenTheSameObjectIsReturned() {
        //given
        DispositionDto expectedDisposition = new DispositionDto(UUID.randomUUID(), LocalDate.now(), "10:00", "14:00", "NFE00001");

        //when
        DispositionDto givenDisposition = dispositionValidation.validateDisposition(expectedDisposition);

        //then
        Assertions.assertThat(givenDisposition).isEqualTo(expectedDisposition);

    }

    @Test
    void assertThatWhenDispositionIsNullThenExceptionIsThrown() {
        //given
        DispositionDto givenDisposition = null;

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> dispositionValidation.validateDisposition(givenDisposition);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("DispositionDto is null.");
    }

    @Test
    void assertThatWhenDispositionDayIsEarlierThanTodayThenExceptionIsThrown() {
        //given
        DispositionDto givenDisposition = new DispositionDto(UUID.randomUUID(), LocalDate.now().minusDays(1), "10:00", "14:00", "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> dispositionValidation.validateDisposition(givenDisposition);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("DispositionDto. Day is earlier than today date.");
    }

    @Test
    void assertThatWhenDispositionStopIsEarlierThanStartThenExceptionIsThrown() {
        //given
        DispositionDto givenDisposition = new DispositionDto(UUID.randomUUID(), LocalDate.now(), "18:00", "14:00", "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> dispositionValidation.validateDisposition(givenDisposition);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("DispositionDto. Stop is before start.");
    }

    @Test
    void assertThatWhenDispositionStartIsInIncorrectFormatThenExceptionIsThrown() {
        String start = "25:00";
        //given
        DispositionDto givenDisposition = new DispositionDto(UUID.randomUUID(), LocalDate.now(), start, "26:00", "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> dispositionValidation.validateDisposition(givenDisposition);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("Start: ["+start+"] is in incorrect format.");
    }

    @Test
    void assertThatWhenDispositionStopIsInIncorrectFormatThenExceptionIsThrown() {
        String stop = "26:00";
        //given
        DispositionDto givenDisposition = new DispositionDto(UUID.randomUUID(), LocalDate.now(), "10:00", stop, "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> dispositionValidation.validateDisposition(givenDisposition);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("Stop: ["+stop+"] is in incorrect format.");
    }

    @Test
    void assertThatWhenDispositionDayIsNullThenExceptionIsThrown() {
        //given
        DispositionDto givenDisposition = new DispositionDto(UUID.randomUUID(), null, "10:00", "16:00", "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> dispositionValidation.validateDisposition(givenDisposition);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("DispositionDto. Day is null.");
    }

    @Test
    void assertThatWhenDispositionStartOrStopIsNullThenExceptionIsThrown() {
        //given
        DispositionDto givenDisposition = new DispositionDto(UUID.randomUUID(), LocalDate.now(), "10:00", null, "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> dispositionValidation.validateDisposition(givenDisposition);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("DispositionDto. Start or stop time is null.");
    }

}