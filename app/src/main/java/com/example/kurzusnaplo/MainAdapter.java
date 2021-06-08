package com.example.kurzusnaplo;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Course> dataList;
    private Activity context;
    private AppDatabase database;

    public MainAdapter(Activity context, List<Course> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course data = dataList.get(position);

        database = AppDatabase.getInstance(context);
        //set texts on text view
        holder.nameView.setText(data.getCourseName());
        holder.creditView.setText("Course credit: " + Integer.toString(data.getCourseCredit()));
        if(data.getCourseGrade() > 1) {
            holder.gradeView.setText("Course grade: " + Integer.toString(data.getCourseGrade()));
        }else{
            holder.gradeView.setText("Course grade: - ");
        }
        holder.typeView.setText("Course type: " + data.getCourseType());
        if (data.getCompleted() == 1){
            holder.completedView.setText("Completed");
        }else{
            holder.completedView.setText("Not completed");
        }


        //edit dialog on edit button click
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course c = dataList.get(holder.getAdapterPosition());
                //get id
                int sId = c.getId();
                //get name
                String sCourseName = c.getCourseName();
                //get credit
                int sCourseCredit = c.getCourseCredit();
                //get grade
                int sCourseGrade = c.getCourseGrade();
                //get type
                String sCourseType = c.getCourseType();
                //get completed
                int sCompleted = c.getCompleted();

                //initialize dialog
                Dialog dialog = new Dialog(context);
                //set content view
                dialog.setContentView(R.layout.dialog_update);
                //initialize width and height
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //set layout with theese params
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                //initialize and assign variables
                EditText editName = dialog.findViewById(R.id.edit_name);
                EditText editCredit = dialog.findViewById(R.id.edit_credit);
                EditText editGrade = dialog.findViewById(R.id.edit_grade);
                EditText editType = dialog.findViewById(R.id.edit_type);
                EditText editCompleted = dialog.findViewById(R.id.edit_completed);
                ImageButton btUpdate = dialog.findViewById(R.id.bt_update);

                //set text on edit texts
                editName.setText(sCourseName);
                editCredit.setText(Integer.toString(sCourseCredit));
                editGrade.setText(Integer.toString(sCourseGrade));
                editType.setText(sCourseType);
                editCompleted.setText(Integer.toString(sCompleted));

                //update datas on update button click
                btUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    dialog.dismiss();
                    //get updated texts from edit texts
                    String uName = editName.getText().toString().trim();
                    int uCredit = Integer.parseInt(editCredit.getText().toString().trim());
                    int uGrade = Integer.parseInt(editGrade.getText().toString().trim());
                    String uType = editType.getText().toString().trim();
                    int uCompleted= Integer.parseInt(editCompleted.getText().toString().trim());
                    //update in database
                    database.dao().update(sId, uName, uCredit, uGrade, uType, uCompleted);

                    dataList.clear();
                    dataList.addAll(database.dao().getAllCourse());
                    notifyDataSetChanged();
                    }
                });
            }
        });

        //delete data on delete button click
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course c = dataList.get(holder.getAdapterPosition());
                database.dao().delete(c);
                //notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView, creditView, gradeView, typeView, completedView;
        ImageView btEdit,btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //assign variables
            nameView = itemView.findViewById(R.id.name_view);
            creditView = itemView.findViewById(R.id.credit_view);
            gradeView = itemView.findViewById(R.id.grade_view);
            typeView = itemView.findViewById(R.id.type_view);
            completedView = itemView.findViewById(R.id.completed_view);

            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
