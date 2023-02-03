package gft.edm.validation;

import gft.edm.model.dto.TimeEntryDto;
import jakarta.validation.ValidationException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TimeEntryValidationTest {

    @Autowired
    TimeEntryValidation timeEntryValidation;

    @Test
    void assertThatWhenTimeEntryPassValidationThenTheSameObjectIsReturned() {
        //given
        TimeEntryDto givenTimeEntry = new TimeEntryDto(UUID.randomUUID(), LocalDate.now(), "10:00", "14:00", "NFE00001");

        //when
        TimeEntryDto expectedTimeEntry = timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        assertThat(expectedTimeEntry).isEqualTo(givenTimeEntry);
    }

    @Test
    void assertThatWhenTimeEntryIsNullThenExceptionIsThrown() {
        //given
        TimeEntryDto givenTimeEntry = null;

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("TimeEntryDto is null.");
    }

    @Test
    void assertThatWhenTimeEntryDayIsEarlierThanTodayThenExceptionIsThrown() {
        //given
        TimeEntryDto givenTimeEntry = new TimeEntryDto(UUID.randomUUID(), LocalDate.now().minusDays(1), "10:00", "14:00", "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("TimeEntryDto. Day is earlier than today date.");
    }

    @Test
    void assertThatWhenTimeEntryStopIsEarlierThanStartThenExceptionIsThrown() {
        //given
        TimeEntryDto givenTimeEntry = new TimeEntryDto(UUID.randomUUID(), LocalDate.now(), "18:00", "14:00", "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("TimeEntryDto. Stop is before start.");
    }

    @Test
    void assertThatWhenTimeEntryStartIsInIncorrectFormatThenExceptionIsThrown() {
        String start = "25:00";
        //given
        TimeEntryDto givenTimeEntry = new TimeEntryDto(UUID.randomUUID(), LocalDate.now(), start, "26:00", "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("Start: [" + start + "] is in incorrect format.");
    }

    @Test
    void assertThatWhenTimeEntryStopIsInIncorrectFormatThenExceptionIsThrown() {
        String stop = "26:00";
        //given
        TimeEntryDto givenTimeEntry = new TimeEntryDto(UUID.randomUUID(), LocalDate.now(), "10:00", stop, "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("Stop: [" + stop + "] is in incorrect format.");
    }

    @Test
    void assertThatWhenTimeEntryDayIsNullThenExceptionIsThrown() {
        //given
        TimeEntryDto givenTimeEntry = new TimeEntryDto(UUID.randomUUID(), null, "10:00", "16:00", "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("TimeEntryDto. Day is null.");
    }

    @Test
    void assertThatWhenTimeEntryPassValidationWithNullStopThenTheSameObjectIsReturned() {
        //given
        TimeEntryDto givenTimeEntry = new TimeEntryDto(UUID.randomUUID(), LocalDate.now(), "10:00", null, "NFE00001");

        //when
        TimeEntryDto expectedTimeEntry = timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        assertThat(expectedTimeEntry).isEqualTo(givenTimeEntry);
    }

    @Test
    void assertThatWhenTimeEntryStartIsNullThenExceptionIsThrown() {
        //given
        TimeEntryDto givenTimeEntry = new TimeEntryDto(UUID.randomUUID(), LocalDate.now(), null, null, "NFE00001");

        //when
        ThrowableAssert.ThrowingCallable invoked = () -> timeEntryValidation.validateTimeEntry(givenTimeEntry);

        //then
        Assertions.assertThatThrownBy(invoked)
                .isExactlyInstanceOf(ValidationException.class)
                .hasMessage("TimeEntryDto. Start time is null.");
    }

}