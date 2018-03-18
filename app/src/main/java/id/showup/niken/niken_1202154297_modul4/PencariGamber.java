package id.showup.niken.niken_1202154297_modul4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PencariGamber extends AppCompatActivity {
    EditText etlink;
    Button bgambar;
    ImageView ivGambar;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencari_gamber);

        //reference
        etlink = findViewById(R.id.etlink);
        bgambar = findViewById(R.id.bgambar);
        ivGambar = findViewById(R.id.ivGambar);

        bgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etlink.getText().toString();
                new AsyncTList(PencariGamber.this).execute(url);
            }
        });
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
        switch(item.getItemId()){
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

    public class AsyncTList extends AsyncTask<String, String, Bitmap>{
        ProgressDialog dialog;

        public AsyncTList(PencariGamber activity) {
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
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try{
                URL url = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            }catch (IOException e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            int process = count++;
            dialog.setProgress(process);
            dialog.setMessage(process+"%");
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivGambar.setImageBitmap(bitmap);
            dialog.dismiss();
        }
    }
}
