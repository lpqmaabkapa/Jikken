package com.example.lavie_z.jikken3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by root on 2017/01/20.
 */

public class ScheduleInformation extends Activity {

    TextView title;
    TextView explanation;
    TextView start;
    TextView end;
    TextView priority;

    Schedule schedule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_information);

        title = (TextView) findViewById(R.id.tvTitle);
        explanation = (TextView) findViewById(R.id.tvExp);
        start = (TextView) findViewById(R.id.tvStart);
        end = (TextView) findViewById(R.id.tvEnd);
        priority = (TextView) findViewById(R.id.tvPri) ;


        //インテントの受け取り

        schedule = (Schedule) getIntent().getSerializableExtra("schedule");

        Intent intent = getIntent();
        if(intent != null){
            Toast.makeText(this, "スケジュール詳細", Toast.LENGTH_SHORT).show();
        }

        title.setText("スケジュール名：" + schedule.title);
        explanation.setText("スケジュール説明：" + schedule.explanation);
        start.setText("開始時間：" + getCalToStr(schedule.startTimeA));
        end.setText("開始時間：" + getCalToStr(schedule.endTimeA));
        priority.setText("優先度：" + String.valueOf(schedule.priority));



    }

    public String getCalToStr(Calendar cal) {
        String str;
        str = String.valueOf(cal.get(cal.YEAR)) + "年";
        str += String.valueOf(cal.get(cal.MONTH)) + "月";
        str += String.valueOf(cal.get(cal.DAY_OF_MONTH) +1) + "日";
        str += String.valueOf(cal.get(cal.HOUR_OF_DAY)) + "時";
        str += String.valueOf(cal.get(cal.MINUTE)) + "分";

        return str;
    }

}
