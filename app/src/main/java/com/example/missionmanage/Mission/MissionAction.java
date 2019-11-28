package com.example.missionmanage.Mission;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.missionmanage.MainActivity;
import com.example.missionmanage.SQLiteOpenHelper.MyDBOpenHelper;

public class MissionAction {
    private MissionAction missionAction;
    private String table = "MyMission";
    private SQLiteDatabase db;


    public  MissionAction(Context context){
        MyDBOpenHelper helper = new MyDBOpenHelper(context,"Mission",null,1);
        db=helper.getReadableDatabase();
    }
    public boolean deleteFromMySql(Mission mission){
        ContentValues values = new ContentValues();
        values.put("mark",-1);
        db.update(table,values,"id=?",new String[]{String.valueOf(mission.getId())});
        values.clear();
        return true;
    }
    public boolean updateTheMission(Mission mission){
        ContentValues values = new ContentValues();
        values.put("details",mission.getDetails());
        values.put("priority",mission.getPriority());
        values.put("tag",mission.getTag());
        values.put("start_time",mission.getStart_time());
        values.put("end_time",mission.getEnd_time());
        db.update(table,values,"id=?",new String[]{String.valueOf(mission.getId())});
        values.clear();
        return true;
    }
    public boolean clearMissions(String mark){
        db.delete(table,"mark=?",new String[]{mark});
        return true;
    }


    public Mission[] findMissionFromMySql(String mark){//精确查询
        Mission mission = new Mission();
        Mission[] mission_arr = new Mission[0];
        Cursor cursor = db.query(table,null,"mark=?",new String[]{mark},null,null,null);
        if(cursor.getCount()>0){
            mission_arr = new Mission[cursor.getCount()];
            while(cursor.moveToNext()){

                mission.setDetails(cursor.getString(cursor.getColumnIndex("details")));
                mission.setPriority(Integer.parseInt(cursor.getString(cursor.getColumnIndex("priority"))));
                mission.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                mission.setStart_time(cursor.getString(cursor.getColumnIndex("start_time")));
                mission.setEnd_time(cursor.getString(cursor.getColumnIndex("end_time")));
                mission.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                mission_arr[cursor.getPosition()]=mission;
                mission = new Mission();

            }
            cursor.close();
        }
        else{
            Log.d("测试","数据库为空");
        }
        return mission_arr;
    }
    public Mission[] findMissionFromMySql(String mark,String start_date,boolean sort){//模糊查询+排序
        Mission mission = new Mission();
        Mission[] mission_arr = new Mission[0];
        if(sort==false) {
            Cursor cursor = db.query(table, null, "mark=? and start_time like ?", new String[]{mark, "%" + start_date + "%"}, null, null, null);
            if (cursor.getCount() > 0) {
                mission_arr = new Mission[cursor.getCount()];
                while (cursor.moveToNext()) {

                    mission.setDetails(cursor.getString(cursor.getColumnIndex("details")));
                    mission.setPriority(Integer.parseInt(cursor.getString(cursor.getColumnIndex("priority"))));
                    mission.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                    mission.setStart_time(cursor.getString(cursor.getColumnIndex("start_time")));
                    mission.setEnd_time(cursor.getString(cursor.getColumnIndex("end_time")));
                    mission.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                    mission_arr[cursor.getPosition()] = mission;
                    mission = new Mission();

                }
                cursor.close();
            } else {
                Log.d("测试", "数据库为空");
            }
        }
        else {
            Cursor cursor = db.query(table, null, "mark=? and start_time like ?", new String[]{mark, "%" + start_date + "%"}, "priority", null, "priority DESC");
            if (cursor.getCount() > 0) {
                mission_arr = new Mission[cursor.getCount()];
                while (cursor.moveToNext()) {

                    mission.setDetails(cursor.getString(cursor.getColumnIndex("details")));
                    mission.setPriority(Integer.parseInt(cursor.getString(cursor.getColumnIndex("priority"))));
                    mission.setTag(cursor.getString(cursor.getColumnIndex("tag")));
                    mission.setStart_time(cursor.getString(cursor.getColumnIndex("start_time")));
                    mission.setEnd_time(cursor.getString(cursor.getColumnIndex("end_time")));
                    mission.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                    mission_arr[cursor.getPosition()] = mission;
                    Log.d("测试",mission_arr.toString());
                    mission = new Mission();

                }
                cursor.close();
            } else {
                Log.d("测试", "数据库为空");
            }
        }
        return mission_arr;
    }

    public int returnCount(String mark){
        Cursor cursor = db.query(table,null,"mark=?",new String[]{mark},null,null,null);
        return cursor.getCount();
    }

    public boolean saveMissionToMySql(Mission mission){
        ContentValues values = new ContentValues();
        if(mission.getDetails()!=""){
            values.put("details",mission.getDetails());
            values.put("priority",mission.getPriority());
            values.put("tag",mission.getTag());
            values.put("start_time",mission.getStart_time());
            values.put("end_time",mission.getEnd_time());
            values.put("mark",mission.getMark());
            db.insert(table,null,values);
            values.clear();
            return true;
        }
        else
        {
            Log.d("Wrong","任务描述不能为空");
            return false;

        }

    }

}

