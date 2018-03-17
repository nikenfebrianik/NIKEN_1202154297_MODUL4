package id.showup.niken.niken_1202154297_modul4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView namaMahasiswa;
    private Button bmulai;

    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference
        namaMahasiswa = findViewById(R.id.listNama);
        bmulai = findViewById(R.id.bmulai);

        //onClick bmulai
        bmulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buat class async task
                new AsyncTList(MainActivity.this).execute();
            }
        });

        // get resource String arraylist
        items = getResources().getStringArray(R.array.listNama);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,
                new ArrayList<String>()
        );

        //set adapter
        namaMahasiswa.setAdapter(adapter);

    }

    //menu titik 3

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        //inflate menu
        inflater.inflate(R.menu.daftar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.listNama:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.cariGambar:
                Intent intent2 = new Intent(this, PencariGamber.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // buat param, progress, result
    public class AsyncTList extends AsyncTask<Void, String, Void> {
        ArrayAdapter<String> adapter;
        ProgressDialog dialog;

        public AsyncTList(MainActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Loading Data");
            dialog.setCancelable(false);
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Progress", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AsyncTList.this.cancel(true);
                    dialog.dismiss();
                }
            });
            dialog.show();
            adapter = (ArrayAdapter<String>) namaMahasiswa.getAdapter();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String name : items) {
                publishProgress(name);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }
}