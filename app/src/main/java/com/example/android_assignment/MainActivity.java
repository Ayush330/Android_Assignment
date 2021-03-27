package com.example.android_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity
{
    protected RecyclerView mRecyclerView;
    private UserDao mUserDao;
    public static List<Data> temp ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AppDatabase db = AppDatabase.getDatabase(this);
        mUserDao = db.userDao();
        //Log.i("RoomLibrary","The length of the room database is: "+String.valueOf(temp.size()));

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);

        Boolean check=false;
        try {
            check = new checkConnection().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(check)
        {
            Log.i("RoomLibrary","internet connection is available.");
            Moshi moshi = new Moshi.Builder()
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://restcountries.eu/")
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build();

            UserService service = retrofit.create(UserService.class);
            Call<List<Data>> call = service.getPosts();
            call.enqueue(new Callback<List<Data>>()
            {
                @Override
                public void onResponse(Call<List<Data>> call, Response<List<Data>> response)
                {
                    List<Data> d ;
                    List<Data> data = response.body();
                    Log.i("RoomLibrary","Inserted Size: "+String.valueOf(data.size()));

                    try {
                        d = getAllDataFncn();
                    }
                    catch (ExecutionException e)
                    {
                        d=null;
                        e.printStackTrace();
                    } catch (InterruptedException e)
                    {
                        d=null;
                        e.printStackTrace();
                    }

                    if(d==null || d.size()==0)
                    {
                        for(Data x : data)
                        {
                            insertt(x);
                            Log.i("RoomLibrary","Inserted");
                        }
                    }
                    //temp = db.userDao().getAll();
                    Log.i("RoomLibrary","The length here of the room database is: "+temp);

                    CustomAdapter adapter = new CustomAdapter(data);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mRecyclerView.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<List<Data>> call, Throwable t)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Error!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context,text+"\t"+"\t"+t.toString(), duration);
                    toast.show();
                }
            });
        }

        else
        {
            Context context = getApplicationContext();
            String text = "No internet and no data in the cache.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(this,text, duration);

            List<Data> d = null;
            try
            {
                d = getAllDataFncn();
            } catch (ExecutionException e)
            {
                d=null;
                e.printStackTrace();
            } catch (InterruptedException e)
            {
                d=null;
                e.printStackTrace();
            }
            if(d==null )
            {
                toast.show();
            }
            else if(d.size()==0)
            {
                toast.show();
            }
            else
            {
                toast = Toast.makeText(this,"Showing offline cached data.", duration);
                toast.show();
                CustomAdapter adapter = new CustomAdapter(d);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mRecyclerView.setAdapter(adapter);
            }
        }

    }

    private static class insertAsyncTask extends AsyncTask<Data, Void, Void>
    {

        private final UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao)
        {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Data... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void insertt(Data data)
    {
        new insertAsyncTask(mUserDao).execute(data);

    }

    private static class getData extends AsyncTask<Void, Void, List<Data>>
    {

        private final UserDao mAsyncTaskDao;

        getData(UserDao dao)
        {
            mAsyncTaskDao = dao;
        }


        @Override
        protected List<Data> doInBackground(Void... voids)
        {
            temp = mAsyncTaskDao.getAll();
            Log.i("RoomLibrary","The length here of the room database in background is: "+temp);
            return temp;
        }
    }

    public List<Data> getAllDataFncn() throws ExecutionException, InterruptedException {
        List<Data> Temp;
        return new getData(mUserDao).execute().get();
    }


    private static class checkConnection extends AsyncTask<Void, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            boolean success = false;
            try {
                URL url = new URL("https://google.com");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.connect();
                success = connection.getResponseCode() == 200;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return success;
        }
    }

}