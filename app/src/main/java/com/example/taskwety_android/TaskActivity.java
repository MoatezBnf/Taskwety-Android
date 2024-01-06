package com.example.taskwety_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText title, description;
    Button addTaskButton,deleteTaskButton;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    ListView listView;
    public ArrayList<Task> Blist= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mAuth = FirebaseAuth.getInstance();
        title = findViewById(R.id.titleEditText);
        description = findViewById(R.id.descriptionEditText);
        addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(this);
        deleteTaskButton = findViewById(R.id.deleteTaskButton);
        deleteTaskButton.setOnClickListener(this);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        String uid = currentUser.getUid();
        List<Task> Tasks = databaseHandler.getTasksByUid(uid);
        for(Task tsk:Tasks){
            Blist.add(tsk);
        }
        TasksAdapter adapter = new TasksAdapter(this, Blist);
        listView = findViewById(R.id.taskListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedItem = (Task) parent.getItemAtPosition(position);
                title.setText(String.valueOf(selectedItem.getID()));
                description.setText(selectedItem.getDescription());
            }
        });
        refresh();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addTaskButton) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            assert currentUser != null;
            String uid = currentUser.getUid();
            Task t = new Task(uid, title.getText().toString().trim(), description.getText().toString().trim());
            databaseHandler.addTask(t);
            title.setText("");
            description.setText("");
            refresh();
        } else if (v.getId() == R.id.deleteTaskButton) {
            databaseHandler.deleteTask(Integer.valueOf(title.getText().toString()));
            title.setText("");
            description.setText("");
            refresh();
            }
        }

    public void refresh(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        String uid = currentUser.getUid();
        List<Task> Tasks  = databaseHandler.getTasksByUid(uid);
        Blist.removeAll(Blist);
        for (Task Tsk : Tasks) {
            Blist.add(Tsk);
        }
        TasksAdapter adapter = new TasksAdapter(this, Blist);
        listView.setAdapter(adapter);
    }
}