package com.example.kurzusnaplo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    EditText editName;
    EditText editCredit;
    EditText editGrade;
    EditText editType;
    EditText editCompleted;
    ImageButton btAdd,btReset,btCalc;
    RecyclerView recyclerView;
    Dialog dialog;
    TextView totalNumOfCredits,obCredits,recOpCredits,opCredits,complCourses,failedCourses, weightedAvarage;


    List<Course> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AppDatabase database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.edit_name);
        editCredit = findViewById(R.id.edit_credit);
        editGrade = findViewById(R.id.edit_grade);
        editType = findViewById(R.id.edit_type);
        editCompleted = findViewById(R.id.edit_completed);
        btAdd = findViewById(R.id.btn_add);
        btReset = findViewById(R.id.btn_reset);
        recyclerView = findViewById(R.id.recycler_view);
        btCalc = findViewById(R.id.bt_calc);



        //Initialize database and store database values in data list
        database = AppDatabase.getInstance(this);
        dataList = database.dao().getAllCourse();

        //initialize and set layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //initialize and set adapter
        adapter = new MainAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);


        //add info dialog on calc button click
        btCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.calc_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                totalNumOfCredits = dialog.findViewById(R.id.tcc_value);
                obCredits = dialog.findViewById(R.id.oc_value);
                recOpCredits = dialog.findViewById(R.id.roc_value);
                opCredits = dialog.findViewById(R.id.cor_value);
                complCourses = dialog.findViewById(R.id.cc_value);
                failedCourses = dialog.findViewById(R.id.fc_value);

                if(!dataList.isEmpty()){

                    if(database.dao().allCompletedCredits() != null){
                        totalNumOfCredits.setText(Integer.toString(database.dao().allCompletedCredits()));
                    }else{
                        totalNumOfCredits.setText("-");
                    }
                    if(database.dao().obligatoryCompletedCredits() != null){
                        obCredits.setText(Integer.toString(database.dao().obligatoryCompletedCredits()));
                    }else{
                        obCredits.setText("-");
                    }
                    if(database.dao().requiredOptionalCompletedCredits() != null){
                        recOpCredits.setText(Integer.toString(database.dao().requiredOptionalCompletedCredits()));
                    }else{
                        recOpCredits.setText("-");
                    }
                    if(database.dao().optionalCompletedCredits() != null){
                        opCredits.setText(Integer.toString(database.dao().optionalCompletedCredits()));
                    }else{
                        opCredits.setText("-");
                    }
                    if(database.dao().NumberOfCompletedCourses() != null){
                        complCourses.setText(Integer.toString(database.dao().NumberOfCompletedCourses()));
                    }else{
                        complCourses.setText("-");
                    }
                    if(database.dao().NumberOfFailedCourses() != null){
                        failedCourses.setText(Integer.toString(database.dao().NumberOfFailedCourses()));
                    }else{
                        failedCourses.setText("-");
                    }

                }else{
                    totalNumOfCredits.setText("-");
                    obCredits.setText("-");
                    recOpCredits.setText("-");
                    opCredits.setText("-");
                    complCourses.setText("-");
                    failedCourses.setText("-");
                }

                dialog.show();

            }
        });

        //add dialog on add button click
        btAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String sCourseName = editName.getText().toString().trim();
                 int sCourseCredit = Integer.parseInt(editCredit.getText().toString().trim());
                 int sCourseGrade = Integer.parseInt(editGrade.getText().toString().trim());
                 String sCourseType = editType.getText().toString().trim();
                 int sCompleted = Integer.parseInt(editCompleted.getText().toString().trim());

                 //check if fields are not empty
                 if (!sCourseName.equals("")) {
                         if (!sCourseType.equals("")) {
                             Course data = new Course();
                             data.setCourseName(sCourseName);
                             data.setCourseCredit(sCourseCredit);
                             data.setCourseGrade(sCourseGrade);
                             data.setCourseType(sCourseType);
                             data.setCompleted(sCompleted);
                             //save in database
                             database.dao().insert(data);
                             //clear edit texts
                             editName.setText("");
                             editCredit.setText(null);
                             editGrade.setText(null);
                             editType.setText("");
                             editCompleted.setText(null);

                             //notify when new data is inserted
                             dataList.clear();
                             dataList.addAll(database.dao().getAllCourse());
                             adapter.notifyDataSetChanged();
                         } else {
                             dialog = new Dialog(MainActivity.this);
                             dialog.setContentView(R.layout.custom_dialog);
                             dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
                             dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                             dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                             Button okey = dialog.findViewById(R.id.bt_ok);
                             dialog.show();

                             okey.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                     dialog.dismiss();

                                 }
                             });
                         }
                 } else {
                     dialog = new Dialog(MainActivity.this);
                     dialog.setContentView(R.layout.custom_dialog);
                     dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background));
                     dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                     dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
                     Button okey = dialog.findViewById(R.id.bt_ok);
                     dialog.show();

                     okey.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             dialog.dismiss();

                         }
                     });
                 }
             }
         });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete all data from database
                database.dao().reset(dataList);
                //notify reset
                dataList.clear();
                dataList.addAll(database.dao().getAllCourse());
                adapter.notifyDataSetChanged();
            }
        });
    }
}