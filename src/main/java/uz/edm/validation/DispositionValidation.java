package uz.edm.validation;

import uz.edm.model.dto.DispositionDto;
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
public class DispositionValidation {

    private DispositionDto dispositionDto;

    public DispositionDto validateDisposition(DispositionDto dispositionDto) {
        this.dispositionDto = dispositionDto;
        return checkDispositionNonNull()
                .checkDispositionDayNonNullAndNotEarlierThanNow()
                .checkDispositionStartIsBeforeStop()
                .getDispositionDto();
    }

    private DispositionValidation checkDispositionNonNull() {
        if (Objects.nonNull(dispositionDto)) {
            return this;
        } else {
            throw new ValidationException("DispositionDto is null.");
        }
    }

    private DispositionValidation checkDispositionDayNonNullAndNotEarlierThanNow() {
        if (Objects.nonNull(dispositionDto.getDay())) {
            boolean isNotEarlierThanNow = LocalDate.now().isEqual(dispositionDto.getDay()) || LocalDate.now().isBefore(dispositionDto.getDay());
            if (isNotEarlierThanNow) {
                return this;
            } else {
                throw new ValidationException("DispositionDto. Day is earlier than today date.");
            }
        } else {
            throw new ValidationException("DispositionDto. Day is null.");
        }
    }

    private DispositionValidation checkDispositionStartIsBeforeStop() {
        if (Objects.nonNull(dispositionDto.getStart()) && Objects.nonNull(dispositionDto.getStop())) {
            boolean isNotEarlierThanNow = checkAndGetStart().toInstant().isBefore(checkAndGetStop().toInstant());
            if (isNotEarlierThanNow) {
                return this;
            } else {
                throw new ValidationException("DispositionDto. Stop is before start.");
            }
        } else {
            throw new ValidationException("DispositionDto. Start or stop time is null.");
        }
    }

    private Date checkAndGetStart() {
        String errorMsgFormat = "Start: [%s] is in incorrect format.";
        return Try.of(() -> getDate(dispositionDto.getStart(), errorMsgFormat))
                .getOrElseThrow(() -> {
                    String errorMsg = String.format(errorMsgFormat, dispositionDto.getStart());
                    return new ValidationException(errorMsg);
                });
    }

    private Date checkAndGetStop() {
        String errorMsgFormat = "Stop: [%s] is in incorrect format.";
        return Try.of(() -> getDate(dispositionDto.getStop(), errorMsgFormat))
                .getOrElseThrow(() -> {
                    String errorMsg = String.format(errorMsgFormat, dispositionDto.getStop());
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

    private DispositionDto getDispositionDto() {
        return dispositionDto;
    }
}
