package com.example.todolist;

import java.util.ArrayList;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.TextView;

public class ListArrange extends ArrayAdapter<Event>{
	private ArrayList<Event> listarr=new ArrayList<Event>();
	private Context ctx;

	public ListArrange(Context context,ArrayList<Event> objects) {
		super(context, R.layout.eventlist, objects);
		this.ctx=context;
		this.listarr=objects;
	}
	public View getView(int index, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
		convertView = inflater.inflate(R.layout.eventlist, parent, false);
		TextView name = (TextView) convertView.findViewById(R.id.testView);
		CheckBox checkbox= (CheckBox) convertView.findViewById(R.id.checkBox);
		name.setText(listarr.get(index).getBody());
		checkbox.setOnCheckedChangeListener(null);
		checkbox.setChecked(listarr.get(index).getClick());
		checkbox.setOnCheckedChangeListener(new TodoCheckboxListerner(index));
		return convertView;
	}
	public class TodoCheckboxListerner implements OnCheckedChangeListener{
		int i_index;
		public TodoCheckboxListerner(int index){
			i_index = index;
		}
		public void onCheckedChanged(CompoundButton buttonView, boolean clicked){
			listarr.get(i_index).setClick(clicked);
		}
	}
}