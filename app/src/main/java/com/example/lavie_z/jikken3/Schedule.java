package com.example.lavie_z.jikken3;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by LaVie_Z on 19/01/2017.
 */

public class Schedule implements Serializable {

    Schedule(){ }

    public boolean isEvent; //TrueならEvent、FalseならTask

    public Calendar dayB;   //なにこれ
    public Calendar dayA;   //なにこれ

    public Calendar startTimeB = Calendar.getInstance(); //開始時刻(ユーザーが設定した時間)
    public Calendar endTimeB = Calendar.getInstance();   //終了時刻（ユーザーが設定した時間）

    public Calendar startTimeA = Calendar.getInstance(); //開始時刻(システムが設定した時間)
    public Calendar endTimeA = Calendar.getInstance();   //終了時刻（システムが設定した時間）

    public int requiredTime ;    //所要時間(minutes/分)
    public Calendar deadline = Calendar.getInstance();   //締め切り時刻
    public String title;        //スケジュールの名前
    public String explanation;  //スケジュールの説明

    public boolean notification;    //通知の有無
    public int scheduleID;      //スケジュールID（ユニークなID）

    public int priority; //スケジュールの優先順位


}
