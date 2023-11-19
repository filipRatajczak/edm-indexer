package uz.edm.util;

import java.time.LocalTime;


public class DateUtils {

    public static LocalTime parseStringToLocalTime(String time) {
        int timeO1Hours = Integer.parseInt(time.split(":")[0]);
        int timeO1Minutes = Integer.parseInt(time.split(":")[1]);
        return LocalTime.of(timeO1Hours, timeO1Minutes);
    }

    public static int getDifferenceInHours(String start, String stop) {
        LocalTime startDate = parseStringToLocalTime(start);
        LocalTime stopDate = parseStringToLocalTime(stop);
        return startDate.getHour() - stopDate.getHour();
    }

}
