package br.com.alessanderleite.roomdatabasesqlite;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    private EditText mEditTextTask;
    private EditText mEditTextDesc;
    private EditText mEditTextFinishBy;
    private Button mSaveTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mEditTextTask = (EditText) findViewById(R.id.editTextTask);
        mEditTextDesc = (EditText) findViewById(R.id.editTextDesc);
        mEditTextFinishBy = (EditText) findViewById(R.id.editTextFinishBy);
        mSaveTask = (Button) findViewById(R.id.button_save);

        mSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        final String sTask = mEditTextTask.getText().toString().trim();
        final String sDesc = mEditTextDesc.getText().toString().trim();
        final String sFinishBy = mEditTextFinishBy.getText().toString().trim();

        if (sTask.isEmpty()) {
            mEditTextTask.setError("Task required");
            mEditTextTask.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            mEditTextDesc.setError("Description required");
            mEditTextDesc.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            mEditTextFinishBy.setError("Finish by required");
            mEditTextFinishBy.requestFocus();
            return;
        }

        // we created an AsyncTask to perform our operation
        // because if we will try to perform the database operation in main
        // thread it will crash our application
        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                //creating a task
                Task task = new Task();
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinishBy(sFinishBy);
                task.setFinished(false);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Saved into SQLite database", Toast.LENGTH_LONG).show();
            }
        }
    }
}
