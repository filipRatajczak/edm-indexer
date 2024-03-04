package uz.edm.validation;

import uz.edm.model.dto.TimeEntryDto;
import io.vavr.control.Try;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TimeEntryValidation {

    private TimeEntryDto timeEntryDto;

    public TimeEntryDto validateTimeEntry(TimeEntryDto timeEntryDto) {
        this.timeEntryDto = timeEntryDto;
        return checkTimeEntryNonNull()
//                .checkTimeEntryDayNonNullAndNotEarlierThanNow()
                .checkTimeEntryStartIsBeforeStop()
                .getTimeEntryDto();
    }

    private TimeEntryValidation checkTimeEntryNonNull() {
        if (Objects.nonNull(timeEntryDto)) {
            return this;
        } else {
            throw new ValidationException("TimeEntryDto is null.");
        }
    }

    private TimeEntryValidation checkTimeEntryDayNonNullAndNotEarlierThanNow() {
        if (Objects.nonNull(timeEntryDto.getDay())) {
            boolean isNotEarlierThanNow = LocalDate.now().isEqual(timeEntryDto.getDay()) || LocalDate.now().isBefore(timeEntryDto.getDay());
            if (isNotEarlierThanNow) {
                return this;
            } else {
                throw new ValidationException("TimeEntryDto. Day is earlier than today date.");
            }
        } else {
            throw new ValidationException("TimeEntryDto. Day is null.");
        }
    }

    private TimeEntryValidation checkTimeEntryStartIsBeforeStop() {
        if (Objects.nonNull(timeEntryDto.getStart())) {
            if (Objects.nonNull(timeEntryDto.getStop())) {
                boolean isNotEarlierThanNow = checkAndGetStart().toInstant().isBefore(checkAndGetStop().toInstant());
                if (isNotEarlierThanNow) {
                    return this;
                } else {
                    throw new ValidationException("TimeEntryDto. Stop is before start.");
                }
            } else {
                return this;
            }
        } else {
            throw new ValidationException("TimeEntryDto. Start time is null.");
        }
    }

    private Date checkAndGetStart() {
        String errorMsgFormat = "Start: [%s] is in incorrect format.";
        return Try.of(() -> getDate(timeEntryDto.getStart(), errorMsgFormat))
                .getOrElseThrow(() -> {
                    String errorMsg = String.format(errorMsgFormat, timeEntryDto.getStart());
                    return new ValidationException(errorMsg);
                });
    }

    private Date checkAndGetStop() {
        String errorMsgFormat = "Stop: [%s] is in incorrect format.";
        return Try.of(() -> getDate(timeEntryDto.getStop(), errorMsgFormat))
                .getOrElseThrow(() -> {
                    String errorMsg = String.format(errorMsgFormat, timeEntryDto.getStop());
                    return new ValidationException(errorMsg);
                });
    }

    private Date getDate(String time, String errorMsg) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String pattern = "^([0-1][\\d]|2[0-3]):[0-5][\\d]$";
        Pattern timePattern = Pattern.compile(pattern);
        Matcher timeMatcher = timePattern.matcher(time);
        if (timeMatcher.matches()) {
            return dateFormat.parse(time);
        } else {
            String msgFormat = String.format(errorMsg, time);
            throw new ValidationException(msgFormat);
        }
    }

    private TimeEntryDto getTimeEntryDto() {
        return timeEntryDto;
    }

}
