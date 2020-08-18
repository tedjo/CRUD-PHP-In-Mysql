package com.e.menumakanki;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.menumakanki.API.ApiService;
import com.e.menumakanki.Model.Value;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditData extends AppCompatActivity {

    private String mExtraData_ID = new String();
    EditText uData_Name, uData_Price, uData_Stock, uData_Description;
    Button Btn_UpdateData, Btn_DeleteData;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        uData_Name = findViewById(R.id.Update_name);
        uData_Price = findViewById(R.id.Update_price);
        uData_Stock = findViewById(R.id.Update_stock);
        uData_Description = findViewById(R.id.Update_decs);

        Btn_UpdateData = findViewById(R.id.Btn_updateData);
        Btn_DeleteData = findViewById(R.id.Btn_deleteData);
        Btn_UpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupUpdateData();
            }
        });
        Btn_DeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForm();
            }
        });

        Bundle bundle=getIntent().getExtras();
        final String _Id =bundle.getString("Id");
        final String _Name =bundle.getString("Name");
        final String _Price =bundle.getString("Price");
        final String _Stock =bundle.getString("Stock");
        final String _Description =bundle.getString("Description");

        mExtraData_ID = _Id;
        uData_Name.setText(_Name);
        uData_Price.setText(_Price);
        uData_Stock.setText(_Stock);
        uData_Description.setText(_Description);

    }

    private void DialogForm() {
        dialog = new AlertDialog.Builder(EditData.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_delete, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.ic_baseline_delete_forever_24);
        dialog.setTitle("Delete Data");


        dialog.setNeutralButton("Yes Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                stupDelete();
            }
        });

        dialog.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    void stupDelete(){
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        Log.d("data","dataku"+" :"+ mExtraData_ID);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Value> call = apiService.getDelete(mExtraData_ID);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                progress.dismiss();
                if(value.equals("1")){
                    Intent ed = new Intent(EditData.this, MainActivity.class);
                    startActivity(ed);
                    finish();
                    Toast.makeText(EditData.this, value, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditData.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(EditData.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setupUpdateData(){
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        String _name            =uData_Name.getText().toString();
        String _price           =uData_Price.getText().toString();
        String _stock           =uData_Stock.getText().toString();
        String _description     =uData_Description.getText().toString();

        Log.d("data","dataku"+" :"+ mExtraData_ID);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Value> call = apiService.getUpdate(mExtraData_ID, _name, _price, _stock, _description);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                progress.dismiss();
                if(value.equals("1")){
                    Intent ed = new Intent(EditData.this, MainActivity.class);
                    startActivity(ed);
                    finish();
                    Toast.makeText(EditData.this, value, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditData.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(EditData.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}