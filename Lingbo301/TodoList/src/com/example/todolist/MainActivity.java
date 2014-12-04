package com.example.todolist;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.todolist.Email;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends Activity {


	private static final String FTodoList = "todo.sav";
	private static final String FArchive = "arch.sav";
	private static final String FText = "text.sav";
	private static final String FEmail = "mail.sav";
	private EditText MainText;
	private ListView listview;
	private ArrayList<Event> Events;
	private ArrayList<Event> archEvents;
	private int totalEvent;
	private int totalArch;
	private ArrayList<String> Summary;
	private ArrayAdapter<String> summaryViewAdapter;
	private ListArrange arrangetodo;
	private ListArrange arrangearch;
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        MainText = (EditText)findViewById(R.id.body);
		listview = (ListView) findViewById(R.id.todoListView);

    }
    
    private String read(String filename) {
    
    	
        try {  
        	BufferedReader fis = new BufferedReader(new InputStreamReader(this.openFileInput(filename)));
        	String line;
        	StringBuffer fileContent = new StringBuffer();
        	while ((line = fis.readLine())!=null){
        		fileContent.append(line);
        	}
        } catch (Exception e) {  
        	Log.i("TodoList", "Error Casting");
        	e.printStackTrace();
        	
        }  
        return null;  
    } 
    
	protected void onStart(){
		super.onStart();
		Events=new ArrayList<Event>();
		archEvents=new ArrayList<Event>();
		Summary=new ArrayList<String>();
		totalEvent=loadItems(Events, FTodoList,totalEvent);
		totalArch=loadItems(archEvents, FArchive,totalArch);
		arrangetodo = new ListArrange(this, Events);
		arrangearch = new ListArrange(this, archEvents);
		String theText=read(FText);
		MainText.setText(theText);
		listview.setAdapter(arrangetodo);
		summaryViewAdapter = new ArrayAdapter<String>(this, R.layout.summary,Summary);
		
	}	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void todoview(View view){
		listview.setAdapter(arrangetodo);
	}
	public void archived(View view){
		listview.setAdapter(arrangearch);
	}
	
	public void save(View view) {
		String text = MainText.getText().toString();
		Event event = new Event(text);
		Events.add(event);
		totalEvent+=1;
		MainText.setText("");
		arrangetodo.notifyDataSetChanged();
	}
	
	
	public void archive(View view){
    	int i = 0;
    	while (Events.get(i).getClick()) {
    		Events.get(i).setClick(false);
    		archEvents.add(Events.get(i));
    		Events.remove(i);
    		totalArch+=1;
    		totalEvent-=1;
    		i++;
    	}
    	i = 0;
    	while (archEvents.get(i).getClick()){
    		archEvents.get(i).setClick(false);
    		Events.add(Events.get(i));
    		archEvents.remove(i);
    		totalArch-=1;
    		totalEvent+=1;
    		i++;
    	}
    	arrangetodo.notifyDataSetChanged();
    	arrangearch.notifyDataSetChanged();
    }  
	
	public void delete(View view) {
		int i = 0;
		while (Events.get(i).getClick()) {
			Events.remove(i);
			totalEvent-=1;
			i+=1;
		}
		arrangetodo.notifyDataSetChanged();
		i = 0;
		while (archEvents.get(i).getClick()){
			archEvents.remove(i);
			totalArch-=1;
			i+=1;
		}
		arrangearch.notifyDataSetChanged();
	}
	
	public void summary(View view){
		Summary.clear();
		String stat1="";
		String stat2="";
		String stat3="";
		String stat4="";
		String stat5="";
		String stat6="";
		String stat7="";
		
		int index;
		int clicktodo=0;
		int clickarch=0;
		index = 0;
		while (Events.get(index).getClick()) {
			index++;
			clicktodo++;
		}
    	index = 0;
    	while (Events.get(index).getClick()) {
			index++;
			clickarch++;
		}
    	stat1="Number of Total items: "+(totalEvent+totalArch);
    	stat2="Number of items: "+totalEvent;
    	stat3="Number of TODO items checked: "+clicktodo;
    	stat4="Number of items left unchecked: "+(totalEvent-clicktodo);
    	stat5="Number of TODO items: "+totalArch;
    	stat6="Number of Checked archived TODO items: "+clickarch;
    	stat7="Number of archived TODO items: "+(totalArch-clickarch);
    	Summary.add(stat1);
    	Summary.add(stat2);
    	Summary.add(stat3);
    	Summary.add(stat4);
    	Summary.add(stat5);
    	Summary.add(stat6);
    	Summary.add(stat7);
	    
		summaryViewAdapter.notifyDataSetChanged();
		listview.setAdapter(summaryViewAdapter);
	}
    
	public void saveItems(List<Event> lts, String filename) {
        try {  
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);  
            ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(lts);
            fos.close();  
        } catch (Exception e) {  
        	String ex = e.toString();
            MainText.setText(ex);
        }  
	}
    public int loadItems(ArrayList<Event> aim, String filename, int theSize) {
		ArrayList<Event> lts = new ArrayList<Event>();
        try {  
            FileInputStream inputStream = openFileInput(filename);  
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			inputStream.close();  

        } catch (Exception e) {  
        }
        aim.clear();
        int a;
        theSize=lts.size();
        for (a=0;a<theSize;a++){
        	aim.add(lts.get(a));
        }
        return theSize;
	}
    
    
    public void emailsend(View view) {
    	Intent intent = new Intent(this, Email.class);
    	startActivity(intent);
    }
  
    private void write(String content,String filename) {  
        try {  
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);  
            fos.write(content.getBytes());  
            fos.close();  
        } catch (Exception e) {  
        }  
    }  
    
    protected void onStop(){
        super.onStop();  
		saveItems(Events, FTodoList);
		saveItems(archEvents, FArchive);
		String text = MainText.getText().toString();
		write(text,FText);

    }  

}