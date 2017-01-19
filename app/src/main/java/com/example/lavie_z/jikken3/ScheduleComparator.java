package com.example.lavie_z.jikken3;

import java.util.Comparator;

/**
 * Created by root on 2017/01/20.
 */

public class ScheduleComparator implements Comparator<Schedule> {

    @Override
    public int compare(Schedule a, Schedule b) {
        long no1 = a.startTimeA.getTimeInMillis();
        long no2 = b.startTimeA.getTimeInMillis();

        if (no1 > no2) {
            return 1;

        } else if (no1 == no2) {
            return 0;

        } else {
            return -1;
        }

    }
}