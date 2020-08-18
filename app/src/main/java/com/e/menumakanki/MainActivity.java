package com.e.menumakanki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.e.menumakanki.API.ApiService;
import com.e.menumakanki.Model.DataModel;
import com.e.menumakanki.Model.Value;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText iData_Name, iData_Price, iData_Stock, iData_Description;
    Button Btn_Tambah, Btn_LihatData;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iData_Name = findViewById(R.id.Add_name);
        iData_Price = findViewById(R.id.Add_price);
        iData_Stock = findViewById(R.id.Add_stock);
        iData_Description = findViewById(R.id.Add_decs);

        Btn_Tambah = findViewById(R.id.Btn_addData);
        Btn_LihatData = findViewById(R.id.Btn_listData);
        Btn_Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setup_add();
            }
        });
        Btn_LihatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listData = new Intent(MainActivity.this, LihatData.class);
                startActivity(listData);
            }
        });

    }

    private void Setup_add(){
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        String _name            =iData_Name.getText().toString();
        String _price           =iData_Price.getText().toString();
        String _stock           =iData_Stock.getText().toString();
        String _description     =iData_Description.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Value> call = apiService.getAdd(_name, _price, _stock, _description);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                Log.d("data","dataku"+" :"+ message);
                progress.dismiss();
                if(value.equals("1")){
                    ClearData();
                    Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void ClearData(){
        iData_Name.setText("");
        iData_Price.setText("");
        iData_Stock.setText("");
        iData_Description.setText("");
    }

}