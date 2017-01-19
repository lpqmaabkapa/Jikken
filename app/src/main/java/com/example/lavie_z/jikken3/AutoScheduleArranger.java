package com.example.lavie_z.jikken3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceConfigurationError;

/**
 * Created by LaVie_Z on 19/01/2017.
 */

public class AutoScheduleArranger implements Serializable {
    // シリアライズバージョンID
    private static final long serialVersionUID = 212993500286484661L;

	public Schedule schedule1;
	public int progress;
	public int scheduleID;

	private Calendar cal;
	private Schedule dbschedule = null;

	public ArrayList<Schedule> list = new ArrayList<>();


	AutoScheduleArranger(){

	}


	//現在の時間を取得する関数
	void getnowTime(){
		cal = Calendar.getInstance(); //現在時間の取得
	}

	//現在の時刻にすでに設定されているスケジュールをデータベースから引っ張ってくる関数
	boolean pullSchedule(){
		//一旦リストから適切であろうリストを持ってくる必要があるため、これから考える
		Schedule tmpschedule = null;
		getnowTime(); //現在の時刻を取得する
		if(cal.compareTo(tmpschedule.startTimeA) >= 0){
			if(cal.compareTo(tmpschedule.endTimeA) <= 0)
				dbschedule = tmpschedule;
			return true;
		}
		return false;
	}

    /*
    //時間を進める関数
    int manageTime(){
        int dec = 0; //次のスケジュールを発見できたかどうかの判定値
        while(dec == 1){
            s2 = new Schedule(); //進めた時間のスケジュールを参照
            cal.add(Calendar.MINUTE, 1); //時間を1分ずつ進める
            if(s2.startTimeB.equals(cal))
                dec = 1;
        }
        return 0;
    }
    */

	//優先順位比較関数
	boolean comparePriority(Schedule s1, Schedule s2){
		//2つのスケジュールを比較して、優先順位の高いものを採用する。
		return s1.priority >= s2.priority;
	}

    /*
    //スケジュール自動設定処理関数　これはあとから手を付ける
    void autoSchedule(Schedule s1, Schedule s2){
        int dec = 0;
        while(dec == 1){
            boolean cmp = comparePriority(s1,s2); //比較
            if(cmp){
            //スケジュールを当てはめる
                dec = 1; //全てのスケジューリングが終了すれば終わり
            }
            else{
            //参照先を変える
                manageTime();
            }
        }
    }
    */

	//スケジュール追加関数
	public void addSchedule(Schedule schedule){
		Iterator<Schedule> iterator = list.iterator();

        if (!iterator.hasNext()) {
            list.add(schedule);
            return;
        }

		while (iterator.hasNext()) {
			Schedule scheduleTmp = iterator.next();
			if(!isOverlap(schedule, scheduleTmp)) {
                schedule.startTimeA = schedule.startTimeB;
				list.add(schedule);
			}
			else {
                System.out.println("sch "+String.valueOf(schedule.priority));
                System.out.println("temp " + String.valueOf(scheduleTmp.priority));
                if (schedule.priority > scheduleTmp.priority){
                    scheduleTmp.startTimeA.setTimeInMillis(schedule.endTimeB.getTimeInMillis());
                    scheduleTmp.endTimeA.setTimeInMillis(scheduleTmp.startTimeA.getTimeInMillis());
                    scheduleTmp.endTimeA.add(Calendar.MINUTE, scheduleTmp.requiredTime);
                    System.out.println(String.valueOf(scheduleTmp.requiredTime));
                    schedule.startTimeA.setTimeInMillis(schedule.startTimeB.getTimeInMillis());
                    schedule.endTimeA.setTimeInMillis(schedule.endTimeB.getTimeInMillis());
                    list.add(schedule);
                }
                else {
                    schedule.startTimeA.setTimeInMillis(scheduleTmp.endTimeB.getTimeInMillis());
                    schedule.endTimeA.setTimeInMillis(schedule.startTimeA.getTimeInMillis());
                    schedule.endTimeA.add(Calendar.MINUTE, schedule.requiredTime);
                    list.add(schedule);
                }
			}

            Collections.sort(list, new ScheduleComparator());

        }

        System.out.println(Integer.toString(list.size()));

	}

	//進捗更新関数
	public boolean updateProgress(){ //今回は使わない
		//ScheduleDatebaseHandlerのupdate関数を呼ぶ
		return false;
	}

	//スケジュール削除関数
	public boolean deteteSchedule(){ //今回は使わない
		//スケジュールの削除ができるかどうかの判定を行う
		if(pullSchedule()){ //データベースにアクセスし、現時刻に保存されているスケジュールを持ってくる
			//スケジュールを入れ替える作業を行う（削除予定のスケジュールを優先順位０のスケジュールに置き換える）
		}//ScheduleDatebaseHandlerのadd関数を呼ぶ

		//ScheduleDatebaseHandlerのdelete関数を呼ぶ
		return false;
	}

	//追加したいsch1がsch2に被ってるか？
	public boolean isOverlap(Schedule sch1, Schedule sch2) {
		if((sch2.startTimeA.getTimeInMillis() < sch1.endTimeB.getTimeInMillis()
				&& sch1.endTimeB.getTimeInMillis() < sch2.endTimeA.getTimeInMillis()
			)||(
				sch2.startTimeA.getTimeInMillis() < sch1.startTimeB.getTimeInMillis()
						&& sch1.startTimeB.getTimeInMillis() < sch2.endTimeA.getTimeInMillis()
				) ){

			return true;

		}
		else{
			return false;
		}
	}

    public int getScheduleSize() {
        return list.size();
    }



}
