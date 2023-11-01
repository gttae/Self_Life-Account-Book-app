package com.example.self_life;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class YearMonth_Value {
    private static final long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000; // 1일의 밀리초
    private static final long MILLISECONDS_IN_MONTH = 30 * MILLISECONDS_IN_DAY; // 한 달의 밀리초

    private static final int currentYear;
    private static final int currentMonth;

    static {
        // 현재 시간을 밀리초로 가져옴
        long currentTimeMillis = System.currentTimeMillis();

        // 밀리초를 연도와 월로 변환
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(currentTimeMillis);
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더함
    }

    public static int getCurrentYear() {
        return currentYear;
    }

    public static int getCurrentMonth() {
        return currentMonth;
    }
}
