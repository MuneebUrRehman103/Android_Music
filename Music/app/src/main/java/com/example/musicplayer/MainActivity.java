package com.example.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public Button btn,btn1,btn2;
    List<ModelClass> mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);

        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        //retrostart
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(Retro.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retro api2 = retrofit2.create(Retro.class);
        Log.i("Print My data ", "1");

        Call<List<ModelClass>> call2 = api2.getdata();

        call2.enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call2, Response<List<ModelClass>> response) {

                Log.i("Print My data ", "2");
                mylist = response.body();
                for(int i=0; i <mylist.size() ;i++)
                {
                    Log.i("Print My data ", " Row " + mylist.get(i).getId() + " Title " + mylist.get(i).getTitle() + " link" + mylist.get(i).getCoverUrl());
                }
            }
            @Override
            public void onFailure(Call<List<ModelClass>> call2, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Print My data ", "3");

            }
        });

        //retroend
}

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.button:
                intent = new Intent(this, PlayerActivity.class);
                intent.putExtra("TITLE", mylist.get(0).getTitle());
                intent.putExtra("COVER", mylist.get(0).getCoverUrl());
                intent.putExtra("AUDIO", mylist.get(0).getAudioUrl());

                 startActivity(intent);
                break;

            case R.id.button1:
                intent = new Intent(this, PlayerActivity.class);
                intent.putExtra("TITLE", mylist.get(1).getTitle());
                intent.putExtra("COVER", mylist.get(1).getCoverUrl());
                intent.putExtra("AUDIO", mylist.get(1).getAudioUrl());

                 startActivity(intent);
                break;

            case R.id.button2:
                intent = new Intent(this, PlayerActivity.class);
                intent.putExtra("TITLE", mylist.get(2).getTitle());
                intent.putExtra("COVER", mylist.get(2).getCoverUrl());
                intent.putExtra("AUDIO", mylist.get(2).getAudioUrl());

                 startActivity(intent);
                break;


            default:
                break;
        }
    }

}
