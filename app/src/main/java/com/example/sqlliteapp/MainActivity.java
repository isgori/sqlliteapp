package com.example.sqlliteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sqlliteapp.api.RandomAPI;
import com.example.sqlliteapp.models.Info;
import com.example.sqlliteapp.models.Location;
import com.example.sqlliteapp.models.Name;
import com.example.sqlliteapp.models.Picture;
import com.example.sqlliteapp.utils.NetworkHelper;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private OkHttpClient okHttpClient;
    private RandomAPI randomAPI;
    private TextView nameTextView,lastTextView,addressTextview;
    private Name nameO = new Name();
    private Picture pictureO = new Picture();
    private Location locationO = new Location();
    private Button buttonNext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageUser);
        nameTextView = findViewById(R.id.nameTextView);
        lastTextView = findViewById(R.id.lastTextView);
        addressTextview = findViewById(R.id.addressTextView);
        buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performNetworkCall(view);
            }
        });
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);

        GetUser();
    }


    private void GetUser() {
        okHttpClient = new OkHttpClient();
        randomAPI = NetworkHelper.createRandomAPI();
    }

    public void performNetworkCall(View view) {
        Request request = new Request.Builder().url(NetworkHelper.BASE_URL).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.body() != null) {
                    final String json = response.body().string();
                    try {
                        JSONObject apiResponse = new JSONObject(json);
                        JSONArray nameArray = apiResponse.getJSONArray("results");

                        JSONObject nameObject = nameArray.getJSONObject(0).getJSONObject("name");
                        JSONObject pictureObject = nameArray.getJSONObject(0).getJSONObject("picture");
                        JSONObject locationObject = nameArray.getJSONObject(0).getJSONObject("location");


                        Gson gson = new Gson();
                        nameO = gson.fromJson(nameObject.toString(), Name.class);
                        pictureO = gson.fromJson(pictureObject.toString(), Picture.class);
                        locationO = gson.fromJson(locationObject.toString(), Location.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    final Picture objectPicture = pictureO;
                    final Name objectName = nameO;
                    final Location objectLocation = locationO;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           try {
                               Picasso.get().load(objectPicture.getLarge()).into(imageView);
                               nameTextView.setText( objectName.getFirst());
                               lastTextView.setText(objectName.getLast());
                               addressTextview.setText(objectLocation.getStreet() + " " +
                                       objectLocation.getCity() + " " +
                                       objectLocation.getState() + " " +
                                       objectLocation.getPostcode() + " " );
                           }catch (Exception ex){

                           }

                        }
                    });
                }
            }
        });
    }
}
