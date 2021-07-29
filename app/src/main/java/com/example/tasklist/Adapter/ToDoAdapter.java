package com.example.tasklist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasklist.AddNewTask;
import com.example.tasklist.MainActivity;
import com.example.tasklist.Model.TodoMode;
import com.example.tasklist.R;
import com.example.tasklist.utils.DatabaseHandler;

import java.util.List;

// todoAdapter extends the RecyclerViewAdapter

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<TodoMode> todoList; // list of task type is TodoMode
    public MainActivity activity; // activity context
    private  final DatabaseHandler db;


    public ToDoAdapter( DatabaseHandler db,  MainActivity activity)
    {
        this.db = db;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                           .inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        db.openDatabase();

        final TodoMode item= todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                {
                    db.updateStatus(item.getId(),1);
                }
                else    {
                    db.updateStatus(item.getId(),0);
                }
            }
        });

    }



    private boolean toBoolean(int n)
    {
        return n!=0;
    }

    @Override
    public int getItemCount()
    {
        return todoList.size();
    }

    public Context getContext()
    {
        return activity;
    }

    public void setTask(List<TodoMode> todoList)
    {
        this.todoList = todoList;
        notifyDataSetChanged();
    }


   public void editItem(int position)
   {
       TodoMode item = todoList.get(position);
       Bundle bundle = new Bundle();
       bundle.putInt("id",item.getId());
       bundle.putString("task",item.getTask());
       AddNewTask fragment = new AddNewTask();
       fragment.setArguments(bundle);
       fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
   }

   public  void deleteItem(int position)
   {
       TodoMode item =todoList.get(position);
       db.deleteTask(item.getId());
       todoList.remove(position);
       notifyItemRemoved(position);
   }

    // this viewHolder extends RecyclerView.ViewHolder

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view)
        {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }


}
