package com.example.lavie_z.jikken3;

import android.app.Activity;
import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by LaVie_Z on 19/01/2017.
 */

public class AddTask extends Activity implements View.OnClickListener{

    Schedule task = new Schedule();

    EditText title;
    EditText explain;
    EditText priority;
    EditText requiredTime;

    Button returnBtn;

    AutoScheduleArranger arranger;

//	AutoScheduleArranger arranger = new AutoScheduleArranger();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        title = (EditText) findViewById(R.id.editText1);
        explain = (EditText) findViewById(R.id.editText2);
        priority = (EditText) findViewById(R.id.editText3);
        requiredTime =(EditText) findViewById(R.id.editText4);
        returnBtn = (Button) findViewById(R.id.button);

        returnBtn.setOnClickListener(this);

        //インテントの受け取り

//        arranger = (AutoScheduleArranger) getIntent().getSerializableExtra("arranger");

        Intent intent = getIntent();
        if(intent != null){
            Toast.makeText(this, "項目をすべて入力してください", Toast.LENGTH_SHORT).show();
        }
    }

    Intent intent = getIntent();


    @Override
    public void onClick(View v) {

        System.out.println("RETURN");

        String strTitle = title.getText().toString();
        String strExplain = explain.getText().toString();
        String strRequiredTime = requiredTime.getText().toString();

        if(strTitle.length() == 0
                || strExplain.length() == 0
                || strRequiredTime.length() == 0
                || !isPriority(priority.getText().toString())
                ){
            Toast.makeText(this, "正しく入力してください", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            task.isEvent = false;
            task.title = strTitle;
            task.explanation = strExplain;
            task.requiredTime = Integer.parseInt(strRequiredTime);
            task.startTimeB.set(2024, 1, 1, 12, 0);
            task.endTimeB.set(2024, 1, 1, 12, 0);
            task.endTimeB.add(Calendar.MINUTE , task.requiredTime);
            task.priority = Integer.parseInt(priority.getText().toString());
        }

        Intent data = new Intent();
        data.putExtra("schedule", task);

        setResult(RESULT_OK, data);

        finish();

    }



	//数字であるかを判定する
    public boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	//優先順位に合っているかを判定する
    public boolean isPriority(String num) {
        if(!isNumber(num)) {
            return false;
        }
        else{
            int intNum = Integer.parseInt(num);
            if(1 <= intNum && intNum <= 7) {
                return true;
            }
            else {
                return false;
            }
        }
    }


}
