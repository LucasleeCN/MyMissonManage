package com.example.missionmanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.missionmanage.Adapter.MissionAdapter;
import com.example.missionmanage.Mission.Mission;
import com.example.missionmanage.Mission.MissionAction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity  {
   private MissionAction missionAction ;
    private List<Mission> mission_list = new ArrayList<Mission>();
    private String start_date;//任务开始时间
    private String end_date;//任务结束时间

    private int hour;
    private int minute;
    String date_day;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar calendar = Calendar.getInstance();
        final int Year = calendar.get(Calendar.YEAR);//获取当前年
        final int month = calendar.get(Calendar.MONTH)+1;//获取月份，加1是因为月份是从0开始计算的
        final int day = calendar.get(Calendar.DATE);//获取日
        date_day = Year+"-"+month+"-"+day;
        hour = calendar.get(Calendar.HOUR);//获取小时
        minute = calendar.get(Calendar.MINUTE);//获取分钟
        final int seconds = calendar.get(Calendar.SECOND);//获取秒钟
        //更新界面，listView为所有任务
        missionAction = new MissionAction(MainActivity.this);
        if(missionAction.returnCount("0")>0) {
            new Thread(new Runnable() {
                @Override
                public void run(){
                    Message message = new Message();
                    message.what=updataUI_4;
                    handler.sendMessage(message);
                }
            }).start();
        }
        else{
            Log.d("测试","run here");
        }
        Typeface typeface = Typeface.createFromAsset(getAssets(),"iconfont.ttf");
        TextView add_mission_button = findViewById(R.id.add_mission_button);
        add_mission_button.setTypeface(typeface);

        //获得系统默认时间



        //点击对勾完成任务
//        LayoutInflater button_inflater = getLayoutInflater();
//        final View view_button = button_inflater.inflate(R.layout.mission_item,null);
//        ImageView mission_finished_button = view_button.findViewById(R.id.mission_finished_pic);
//        mission_finished_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//
//            }
//        });
//        //点击“X”删除任务
//        ImageView mission_unfinished_button = view_button.findViewById(R.id.mission_unfinished_pic);
//        mission_unfinished_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v){
//
//            }
//        });

        //添加任务
        add_mission_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.d("测试","here ");
                AlertDialog.Builder dialog_submit = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater mission_inflater = getLayoutInflater();
                final View view = mission_inflater.inflate(R.layout.submit_mission,null,false);
                dialog_submit.setView(view);
                dialog_submit.setTitle("请输入任务信息:");
                dialog_submit.setCancelable(false);

                //设置默认时间
                EditText st = view.findViewById(R.id.mission_start_time_editText);
                String default_time=""+Year+"-"+month+"-"+day+" "+hour+":"+minute+":"+seconds;
                st.setText(default_time);
                EditText et =view.findViewById(R.id.mission_end_time_editText);
                et.setText(default_time);

                dataChose(view);//调用选择日期方法

                dialog_submit.setPositiveButton("添加任务", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        EditText editText_details = view.findViewById(R.id.mission_details_editText);
                        EditText editText_priority = view.findViewById(R.id.mission_priority_editText);
                        EditText editText_tag = view.findViewById(R.id.mission_tag_editText);
                        EditText editText_end_time = view.findViewById(R.id.mission_end_time_editText);
                        EditText editText_start_time = view.findViewById(R.id.mission_start_time_editText);

                        Mission mission = new Mission();
                        mission.setDetails(editText_details.getText().toString());
                        if (!mission.getDetails() .equals("")) {
                            mission.setPriority((Integer.parseInt((editText_priority.getText().toString()))));
                            mission.setTag(editText_tag.getText().toString());
                            mission.setStart_time(editText_start_time.getText().toString());
                            mission.setEnd_time(editText_end_time.getText().toString());
                            mission.setMark("0");
                            Log.d("TAG", "" + mission.getDetails() + " " + mission.getPriority() + " " + mission.getTag() + " " + mission.getStart_time() + " " + mission.getEnd_time() + " " + mission.getMark());
                            missionAction = new MissionAction(MainActivity.this);
                            boolean flag = missionAction.saveMissionToMySql(mission);
//                        if(Integer.parseInt(mission.getStart_time())<Integer.parseInt(mission.getEnd_time())) {
//                            Log.d("TAG", mission.getStart_time());
//                        }
//                        else{
//                            Log.d("TAG",""+Integer.parseInt(mission.getEnd_time()));
//                        }
                            if (flag == true) {
                                Log.d("测试", "保存成功");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run(){
                                        Message message = new Message();
                                        message.what = updataUI_4;
                                        handler.sendMessage(message);
                                    }
                                }).start();

                            } else
                                Log.d("测试", "保存失败");


                        }
                        else {
                            Toast.makeText(MainActivity.this,"详情不能为空",Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                dialog_submit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                dialog_submit.show();
//                ViewParent parent = view.getParent();
//                Log.d("测试",parent.getClass().toString());

            }
        });

        //listView 点击事件
        ListView listView = findViewById(R.id.mission_listView);
        //单击删除
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                missionAction = new MissionAction(MainActivity.this);
               if( missionAction.deleteFromMySql(mission_list.get(position))){
                   new Thread(new Runnable() {
                       @Override
                       public void run(){
                           Message message = new Message();
                           message.what = updataUI_4;
                           handler.sendMessage(message);
                       }
                   }).start();
               }
               else{
                   Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

               }

            }
        });
        //长按编辑
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id){
               AlertDialog.Builder dialog_edit = new AlertDialog.Builder(MainActivity.this);
               LayoutInflater layoutInflater = getLayoutInflater();
               final View view1 = layoutInflater.inflate(R.layout.edit_mission,null,false);
               dialog_edit.setView(view1);
               dialog_edit.setTitle("编辑");

               EditText editText_details = view1.findViewById(R.id.mission_details_editText);
               EditText editText_priority = view1.findViewById(R.id.mission_priority_editText);
               EditText editText_tag = view1.findViewById(R.id.mission_tag_editText);
               EditText editText_start_time =view1.findViewById(R.id.mission_start_time_editText);
               EditText editText_end_time = view1.findViewById(R.id.mission_end_time_editText);
               Mission mission = mission_list.get(position);

               editText_details.setText(mission.getDetails());
               editText_priority.setText(String.valueOf(mission.getPriority()));
               editText_tag.setText(mission.getTag());
               editText_start_time.setText(mission.getStart_time());
               editText_end_time.setText(mission.getEnd_time());

               dataChose(view1);
               dialog_edit.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which){
                       EditText editText_details =view1. findViewById(R.id.mission_details_editText);
                       EditText editText_priority = view1.findViewById(R.id.mission_priority_editText);
                       EditText editText_tag = view1.findViewById(R.id.mission_tag_editText);
                       EditText editText_start_time =view1.findViewById(R.id.mission_start_time_editText);
                       EditText editText_end_time = view1.findViewById(R.id.mission_end_time_editText);
                       Mission mission = mission_list.get(position);
                           mission.setDetails(editText_details.getText().toString());
                           mission.setPriority((Integer.parseInt((editText_priority.getText().toString()))));
                           mission.setTag(editText_tag.getText().toString());
                           mission.setStart_time(editText_start_time.getText().toString());
                           mission.setEnd_time(editText_end_time.getText().toString());
                           mission.setMark("0");
                           missionAction = new MissionAction(MainActivity.this);
                       Log.d("TAG",mission.getId()+" "+""+mission.getDetails()+" "+mission.getPriority()+" "+mission.getTag()+" " +mission.getStart_time()+" "+mission.getEnd_time()+" "+mission.getMark());

                       boolean flag =missionAction.updateTheMission(mission);
                           if(flag){
                               Toast.makeText(MainActivity.this,"编辑成功",Toast.LENGTH_SHORT).show();
                               new Thread(new Runnable() {
                                   @Override
                                   public void run(){
                                       Message message = new Message();
                                       message.what=updataUI_4;
                                       handler.sendMessage(message);
                                   }
                               }).start();
                           }
                           else
                           {
                               Toast.makeText(MainActivity.this,"编辑失败",Toast.LENGTH_SHORT).show();
                           }
                   }
               });

            dialog_edit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which){

                }
            });
            dialog_edit.show();
               return true;
           }
       });

    }
    public static final int updataUI_1=1;//今日未做任务
    public static final int updataUI_2=2;//已完成
    public static final int updataUI_3=3;//未完成
    public static final int updataUI_4=4;//全部任务未做
    public static  final  int updataUI_5_1= 5;//按照优先级排序

    private Handler handler =new Handler(){
        public void handleMessage(Message msg){
        switch (msg.what){
            case updataUI_1:
                initMission(0,date_day,false);
                break;
            case updataUI_2:
                initMission(1);
                break;
            case updataUI_3:
                initMission(-1);
                break;
            case  updataUI_4:
                initMission(0);
                break;
            case updataUI_5_1:
                initMission(0,date_day,true);
                default:
                    break;
        }
        }
    };
//    public static  final  int sort_by_priority= 5_1;//按照优先级排序
//
//    Handler handler_prepare = new Handler(){
//          public void handleMessage(final Message message){
//              switch (message.what){
//                  case sort_by_priority:
//                      new Thread(new Runnable() {
//                          @Override
//                          public void run(){
//                              Message message1 = new Message();
//                              message1.what=updataUI_5_1;
//                              handler.sendMessage(message1);
//                          }
//                      }).start();
//              }
//          }
//
//        };

   public void initMission( int mark){
        mission_list.clear();
        missionAction = new MissionAction(MainActivity.this);
        Mission[] missions_arr = missionAction.findMissionFromMySql(String.valueOf(mark));
        for (int i =0;i<missions_arr.length;i++){
            mission_list.add(missions_arr[i]);
        }

            MissionAdapter missionAdapter = new MissionAdapter(MainActivity.this, R.layout.mission_item, mission_list);
            ListView listView = findViewById(R.id.mission_listView);
            listView.setAdapter(missionAdapter);

    }
    public void initMission( int mark,String start_date,boolean sort){
        mission_list.clear();
        missionAction = new MissionAction(MainActivity.this);
        Mission[] missions_arr = missionAction.findMissionFromMySql(String.valueOf(mark),date_day,sort);
        for (int i =0;i<missions_arr.length;i++){
            mission_list.add(missions_arr[i]);
        }

            MissionAdapter missionAdapter = new MissionAdapter(MainActivity.this, R.layout.mission_item, mission_list);
            ListView listView = findViewById(R.id.mission_listView);
            listView.setAdapter(missionAdapter);

    }


    //选择日期
public void dataChose(View view){

    final EditText editText_start_time =view.findViewById(R.id.mission_start_time_editText);
    editText_start_time.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v){

            new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                //实现监听方法
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    //设置文本显示内容
                    editText_start_time.setText(editText_start_time.getText().toString()+" "+hour+":"+minute);

                }
            },hour,minute,true).show();

            new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                    String start_date = (String.format("%d-%d-%d",year,month+1,dayOfMonth));
                    editText_start_time.setText(start_date);

                }
            },2018,12,31).show();




//            TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute){
//
//                }
//            });

//                                CalendarView start_time_calendarView = findViewById(R.id.start_time_calendarView);
//                                start_time_calendarView.setVisibility(0);

        }
    });
    final EditText editText_end_time = view.findViewById(R.id.mission_end_time_editText);
    editText_end_time.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v){

            new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                //实现监听方法
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    //设置文本显示内容
                    editText_end_time.setText(editText_end_time.getText().toString()+" "+hour+":"+minute);

                }
            },hour,minute,true).show();

            new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                    String end_date = (String.format("%d-%d-%d",year,month+1,dayOfMonth));
                    editText_end_time.setText(end_date);

                }
            },2019,12,31).show();


//                                CalendarView start_time_calendarView = findViewById(R.id.start_time_calendarView);
//                                start_time_calendarView.setVisibility(0);

        }
    });
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
         getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.share:
                //分享
                AlertDialog.Builder dia = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater mission_share = getLayoutInflater();
                View share_view = mission_share.inflate(R.layout.mission_share,null);
                dia.setView(share_view);
                dia.setCancelable(false);
                dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                dia.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                dia.show();
                break;
            case R.id.sort:
                //排序
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater mission_inflater = getLayoutInflater();
                final View view = mission_inflater.inflate(R.layout.sort_mission,null);
                builder.setView(view);
                builder.setTitle("排序");
                builder.setCancelable(false);
                RadioGroup radioGroup = view.findViewById(R.id.sort_radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId){
                        switch (checkedId){
                            case R.id.sort_by_start_time:
                                break;
                            case R.id.sort_by_priority:
                                Log.d("测试","执行到这里了1");
                                new Thread(new Runnable() {
                                    @Override
                                    public void run(){
                                        Message message = new Message();
                                        message.what=updataUI_5_1;
                                        handler.sendMessage(message);
                                    }
                                }).start();
                                break;
                            case R.id.sort_by_end_time:
                                break;
                            case R.id.sort_by_rest_time:
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                });
                builder.show();
                break;
            case R.id.finished_missions:
                //查询已完成任务
                missionAction = new MissionAction(MainActivity.this);
                if (missionAction.returnCount("1")>0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run(){
                            Message message = new Message();
                            message.what = updataUI_2;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                else{
                    Toast.makeText(MainActivity.this,"暂时没有已完成任务",Toast.LENGTH_SHORT).show();
                }
                break;
            case  R.id.unfinished_mission:
                //查询未完成任务
                missionAction = new MissionAction(MainActivity.this);
                if (missionAction.returnCount("-1")>0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run(){
                            Message message = new Message();
                            message.what = updataUI_3;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                else{
                    Toast.makeText(MainActivity.this,"暂时没有未完成任务",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mission_days:
                //查询今日任务
                missionAction = new MissionAction(MainActivity.this);
                if (missionAction.returnCount("0")>0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run(){
                            Message message = new Message();
                            message.what = updataUI_1;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                else{
                    Toast.makeText(MainActivity.this,"暂时没有任务",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clear_all_finished_missions:
                //清空已完成任务
                missionAction = new MissionAction(MainActivity.this);
                missionAction.clearMissions("1");
                new Thread(new Runnable() {
                    @Override
                    public void run(){
                        Message message = new Message();
                        message.what=updataUI_2;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.clear_all_unfinished_missions:
                    //清空未完成任务
                missionAction = new MissionAction(MainActivity.this);
                missionAction.clearMissions("-1");
                new Thread(new Runnable() {
                    @Override
                    public void run(){
                        Message message = new Message();
                        message.what=updataUI_3;
                        handler.sendMessage(message);
                    }
                }).start();
                    break;
            case R.id.back:
                //返回
                if (missionAction.returnCount("0")>0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run(){
                            Message message = new Message();
                            message.what = updataUI_4;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                break;
        }
        return true;
    }



}
