package com.example.missionmanage.Mission;

public class Mission {

    private String details;
    private int  priority ;
    private  String start_time;
    private String end_time ;
    private  String tag;
    private String mark;
    private int id;

    public Mission(){
        super();
    }

    public Mission(int id, String details, int priority, String start_time, String end_time, String tag,String mark){
        this.id=id;
        this.details = details;
        this.priority = priority;
        this.start_time = start_time;
        this.end_time = end_time;
        this.tag = tag;
        this.mark=mark;

    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDetails(){
        return details;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public String getStart_time(){
        return start_time;
    }

    public void setStart_time(String start_time){
        this.start_time = start_time;
    }

    public String getEnd_time(){
        return end_time;
    }

    public void setEnd_time(String end_time){
        this.end_time = end_time;
    }

    public String getTag(){
        return tag;
    }

    public void setTag(String tag){
        this.tag = tag;
    }

    public String getMark(){
        return mark;
    }

    public void setMark(String mark){
        this.mark = mark;
    }
}
