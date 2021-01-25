package com.example.compile1.Team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.compile1.HelperClasses.TaskItem;
import com.example.compile1.R;
import com.example.compile1.HelperClasses.SwipeController;
import com.example.compile1.HelperClasses.SwipeControllerActions;
import com.example.compile1.HelperClasses.TaskItem;
import com.example.compile1.HelperClasses.TaskItemAdapter;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    //Add database recyclerview taskItemRecycler
    RecyclerView taskItemRecycler;
    SwipeController swipeController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        TextView taskName = findViewById(R.id.task_name);
        TextView taskDesc = findViewById(R.id.task_desc);
        TextView taskDue = findViewById(R.id.taskDue);
        taskName.setText(intent.getStringExtra("taskName"));
        taskDesc.setText(intent.getStringExtra("taskDesc"));
        taskDue.setText(intent.getStringExtra("taskDue"));


//        Bundle args = intent.getBundleExtra("BUNDLE");
//        ArrayList<TaskItem> taskItems = (ArrayList<TaskItem>) args.getSerializable("taskItems");
        taskItemRecycler = findViewById(R.id.taskitem_recycler);

        taskItemRecycler();

    }

    private void taskItemRecycler() {
        //add recycle task item
        taskItemRecycler.setHasFixedSize(true);
        taskItemRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ArrayList<TaskItem>  taskItem = new ArrayList<TaskItem>();
        taskItem.add(new TaskItem("Meow",null,false));
        taskItem.add(new TaskItem("Bark",null,false));
        taskItem.add(new TaskItem("Chrip",null,false));
        TaskItemAdapter adapter = new TaskItemAdapter(taskItem,getApplicationContext());
        taskItemRecycler.setAdapter(adapter);

        //item goes swoosh

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //edit item
                editItemDialog();
            }
            //Enable left side to delete works
            //to use this please uncomment below and in swipe controller.java on Draw child
            @Override
            public void onLeftClicked(int position) {
                //delete item
                taskItem.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(taskItemRecycler);

        taskItemRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void editItemDialog() {
        EditTaskItemDialog taskItemDialog = new EditTaskItemDialog();
        taskItemDialog.show(getSupportFragmentManager(),"taskItemEdit");
    }


    public void addTaskOnCliked(View view){
        createItemDialog();
    }
    private void createItemDialog() {
        CreateTaskItemDialog taskItemDialog = new CreateTaskItemDialog();
        taskItemDialog.show(getSupportFragmentManager(),"taskItemCreate");
    }

}