package es.cice.moviedbrestservicetest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL="https://api.themoviedb.org/3";
    private static final String API_KEY="60e615c9841ee9ab8defdaeb1a4183bc";
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getMovies(View v){
        TheMovieDBAsyncTask at=new TheMovieDBAsyncTask();
        at.execute("https://api.themoviedb.org/3/movie/popular?api_key=60e615c9841ee9ab8defdaeb1a4183bc");
    }

    public class TheMovieDBAsyncTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... urls) {
            //Retrofit evita tener que gestionar la conexion http
            try {
                URL url=new URL(urls[0]);
                HttpURLConnection con=
                        (HttpURLConnection) url.openConnection();
                BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer data=new StringBuffer();
                //Insertar los datos obtenidos con in en el StringBuffer
                String line=null;
                while((line=in.readLine())!=null){
                    data.append(line);
                }
                JSONObject jsonObj=new JSONObject(data.toString());
                JSONArray results=jsonObj.getJSONArray("results");
                for(int i=0;i<results.length();i++){
                    JSONObject jsonMovie=results.getJSONObject(i);
                    String title=jsonMovie.getString("original_title");
                    Log.d(TAG,title);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
