package com.example.lavie_z.jikken3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button addScheduleBtn;
    Button deleteScheduleBtn;
    Button updateProgressBtn;

    ListView listview;

	AutoScheduleArranger arranger;

    Schedule schedule1 = new Schedule();     //デモ用の初期スケジュール

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addScheduleBtn = (Button) findViewById(R.id.addScheduleBtn);
        deleteScheduleBtn = (Button) findViewById(R.id.deleteScheduleBtn);
        updateProgressBtn = (Button) findViewById(R.id.changeProgressBtn);

        addScheduleBtn.setOnClickListener(this);
        deleteScheduleBtn.setOnClickListener(this);
        updateProgressBtn.setOnClickListener(this);

		arranger = new AutoScheduleArranger();

        Random rand = new Random();
        schedule1.startTimeB.set(2024, 1, 1, 12, 0);
        schedule1.endTimeB.set(2024, 1, 1, 13, 0);
        schedule1.startTimeA.set(2024, 1, 1, 12, 0);
        schedule1.endTimeA.set(2024, 1, 1, 13, 0);
        schedule1.requiredTime = 60;
        schedule1.isEvent = true;
        schedule1.title = "LUNCH";
        schedule1.explanation = "LUNCH IN CAFE";
        schedule1.priority = 5;
        schedule1.notification = false;
        schedule1.scheduleID = rand.nextInt(10000);  //スケジュールIDはランダムでつける（ほんとはダメよ）

        arranger.addSchedule(schedule1);

        updateListView();

    }


    @Override
    public void onClick(View v) {

        /* スケジュール追加 */
        if(v == addScheduleBtn) {

            Intent intentAddTask = new Intent();
            intentAddTask.setClass(this, com.example.lavie_z.jikken3.AddTask.class);
            //Serializeに失敗???
//            intentAddTask.putExtra("arranger", arranger);

            int requestCode = 111;
            startActivityForResult(intentAddTask, requestCode);



        }

        /* スケジュール削除 */
        if(v == deleteScheduleBtn) {

            Intent intentAddTask = new Intent(this, AddTask.class);
            startActivity(intentAddTask);

        }

        /* 進捗更新 */
        if(v == updateProgressBtn) {

            Intent intentAddTask = new Intent(this, AddTask.class);
            startActivity(intentAddTask);

        }

    }

    public void updateListView(){
        listview = (ListView) findViewById(R.id.listview);

        String[] members = new String[arranger.getScheduleSize()];

        for (int i = 0; i < arranger.getScheduleSize(); i++) {
            Schedule scheduleI = arranger.list.get(i);
            members[i] = getCalToStr(scheduleI.startTimeA) + "~" +
                    getCalToStr(scheduleI.endTimeA) + ":\n" +
                    scheduleI.title;
        }

        System.out.println(Integer.toString(arranger.getScheduleSize()));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, members);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intentAddTask = new Intent();
        intentAddTask.setClass(this, com.example.lavie_z.jikken3.ScheduleInformation.class);
        //Serializeに失敗???
        intentAddTask.putExtra("schedule", arranger.list.get(position));

        startActivity(intentAddTask);

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

    // startActivityForResult で起動させたアクティビティが
    // finish() により破棄されたときにコールされる
    // requestCode : startActivityForResult の第二引数で指定した値が渡される
    // resultCode : 起動先のActivity.setResult の第一引数が渡される
    // Intent data : 起動先Activityから送られてくる Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Schedule sch = (Schedule) data.getSerializableExtra("schedule");

        switch (requestCode) {
            case 111:
                if (resultCode == RESULT_OK) {
                    arranger.addSchedule(sch);
                } else if (resultCode == RESULT_CANCELED) {

                }
                break;

            default:
                break;
        }

        updateListView();

    }



}
