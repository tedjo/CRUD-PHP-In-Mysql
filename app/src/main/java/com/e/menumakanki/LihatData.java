package com.e.menumakanki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.e.menumakanki.API.ApiService;
import com.e.menumakanki.Adapter.MenuAdapter;
import com.e.menumakanki.Model.DataModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LihatData extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<DataModel> mDataModel = new ArrayList<>();
    private ListView listView;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        listView = findViewById(R.id.listItem);
        listView.setOnItemClickListener(this);
        listView.setDividerHeight(0);
        Setup();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent dS = new Intent(LihatData.this, EditData.class);
        dS.putExtra("Id", ""+mDataModel.get(i).getId());
        dS.putExtra("Name", ""+mDataModel.get(i).getName());
        dS.putExtra("Price", ""+mDataModel.get(i).getPrice());
        dS.putExtra("Stock", ""+mDataModel.get(i).getStock());
        dS.putExtra("Description", ""+mDataModel.get(i).getDescription());
        startActivity(dS);
    }

    void Setup(){
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<DataModel>> call = apiService.getAll();
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                progress.dismiss();
                if (response.isSuccessful()) {
                    int dataSize = response.body().size();
                    for (int i = 0; i < dataSize; i++) {
                        DataModel iData = new DataModel(
                                response.body().get(i).getId(),
                                response.body().get(i).getName(),
                                response.body().get(i).getPrice(),
                                response.body().get(i).getStock(),
                                response.body().get(i).getDescription());
                        mDataModel.add(iData);

                        Log.d("data","dataku"+" :"+
                                response.body().get(i).getName());
                    }
                    listView.setVisibility(View.VISIBLE);
                    ListAdapter adapter = new MenuAdapter(LihatData.this, mDataModel);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(LihatData.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}