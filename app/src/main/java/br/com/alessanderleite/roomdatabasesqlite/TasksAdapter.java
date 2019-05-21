package br.com.alessanderleite.roomdatabasesqlite;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private Context mContext;
    private List<Task> taskList;

    public TasksAdapter(Context mContext, List<Task> taskList) {
        this.mContext = mContext;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_tasks, viewGroup, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.textViewTask.setText(task.getTask());
        holder.textViewDesc.setText(task.getDesc());
        holder.textViewFinishBy.setText(task.getFinishBy());

        if (task.isFinished()) {
            holder.textViewStatus.setText("Completed");
        } else {
            holder.textViewStatus.setText("Not Completed");
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewStatus = (TextView) itemView.findViewById(R.id.textViewStatus);
            textViewTask = (TextView) itemView.findViewById(R.id.textViewTask);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = (TextView) itemView.findViewById(R.id.textViewFinishBy);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Task task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mContext, UpdateTaskActivity.class);
            intent.putExtra("task", task);

            mContext.startActivity(intent);
        }
    }
}