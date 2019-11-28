package com.example.missionmanage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.missionmanage.Mission.Mission;
import com.example.missionmanage.R;


import java.util.ArrayList;
import java.util.List;

public class MissionAdapter extends ArrayAdapter<Mission> {

    private List<Mission> missions_List = new ArrayList<Mission>();

    public MissionAdapter(Context context,int textViewResourceId,List<Mission> mission_list) {
        super(context,textViewResourceId,mission_list);
        this.missions_List=mission_list;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.mission_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.details=view.findViewById(R.id.mission_details_textView);
            viewHolder.priority=view.findViewById(R.id.mission_priority_textView);
            viewHolder.tag = view.findViewById(R.id.mission_tag_textView);
            viewHolder.start_time = view.findViewById(R.id.mission_start_time);
            viewHolder.end_time = view.findViewById(R.id.mission_end_time);
            view.setTag(viewHolder);

        }
        else{
            view = convertView;
            viewHolder=(ViewHolder) view.getTag();
        }
        Mission mission=missions_List.get(position);

        viewHolder.details.setText(mission.getDetails());
        viewHolder.tag.setText(mission.getTag());
        viewHolder.priority.setText(String.valueOf(mission.getPriority()));
        viewHolder.start_time.setText(mission.getStart_time());
        viewHolder.end_time.setText(mission.getEnd_time());

        return view;
    }
}
