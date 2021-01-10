package com.pirkovitaliysoft.testbankapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pirkovitaliysoft.testbankapplication.retrofit.NbuEntity;
import com.pirkovitaliysoft.testbankapplication.retrofit.NbuService;
import com.pirkovitaliysoft.testbankapplication.retrofit.PbEntity;
import com.pirkovitaliysoft.testbankapplication.retrofit.PbEntityContainer;
import com.pirkovitaliysoft.testbankapplication.retrofit.PbService;
import com.pirkovitaliysoft.testbankapplication.rvHandlers.RVnbuAdapter;
import com.pirkovitaliysoft.testbankapplication.rvHandlers.RVpbAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView datePbTextView, dateNbuTextView;

    private final String PRIVAT_URL = "https://api.privatbank.ua";
    private final String NBU_URL = "https://bank.gov.ua";

    private RecyclerView pbRecyclerView;
    private RVpbAdapter pbAdapter;

    private RecyclerView nbuRecyclerView;
    private RVnbuAdapter nbuAdapter;

    //Retrofit services
    private PbService pbService;
    private NbuService nbuService;

    private List<PbEntity> pbEntities;
    private List<NbuEntity> nbuEntities;

    private String pbDate = null, nbuDate = null;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date currentDate = Calendar.getInstance().getTime();
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        datePbTextView = (TextView) findViewById(R.id.date_pb_textview);
        dateNbuTextView = (TextView) findViewById(R.id.date_nbu_textview);

        String formattedDate = dateFormatter.format(currentDate);
        if(savedInstanceState != null){
            pbDate = savedInstanceState.getString("pbDate", formattedDate);
            nbuDate = savedInstanceState.getString("nbuDate", formattedDate);
        }else{
            pbDate = formattedDate;
            nbuDate = formattedDate;
        }

        datePbTextView.setText(pbDate);
        dateNbuTextView.setText(nbuDate);

        //Recyclers
        pbRecyclerView = (RecyclerView) findViewById(R.id.pb_recycler);
        pbRecyclerView.setHasFixedSize(true);

        nbuRecyclerView = (RecyclerView) findViewById(R.id.nbu_recycler);
        nbuRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager pbLayoutManager = new LinearLayoutManager(this);
        pbRecyclerView.setLayoutManager(pbLayoutManager);
        nbuRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        pbAdapter = new RVpbAdapter(getResources().getColor(R.color.row_highlight, getTheme()));
        pbRecyclerView.setAdapter(pbAdapter);
        nbuAdapter = new RVnbuAdapter(getResources().getColor(R.color.row_highlight, getTheme()));
        nbuRecyclerView.setAdapter(nbuAdapter);

        pbAdapter.setOnItemClickListener(new RVpbAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PbEntity currentPbEntity = pbEntities.get(position);
                String currency = currentPbEntity.getCurrency() == null ? currentPbEntity.getBaseCurrency() : currentPbEntity.getCurrency();
                int entityPos = getPositionOfVaultInNbu(currency, nbuEntities);
                if(entityPos == -1) return;
                nbuRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        nbuRecyclerView.scrollToPosition(entityPos);
                    }
                });
            }
        });

        Retrofit pbRetrofit = new Retrofit.Builder()
                .baseUrl(PRIVAT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pbService = pbRetrofit.create(PbService.class);
        pbService.listEntities(pbDate).enqueue(new Callback<PbEntityContainer>() {
            @Override
            public void onResponse(Call<PbEntityContainer> call, Response<PbEntityContainer> response) {
                if(response.isSuccessful()){
                    pbEntities = response.body().getExchangeRate();
                    pbAdapter.setEntities(pbEntities);
                }
            }

            @Override
            public void onFailure(Call<PbEntityContainer> call, Throwable t) {
                Log.e("Cannot fetch data", t.getMessage());
            }
        });

        Retrofit nbuRetrofit = new Retrofit.Builder()
                .baseUrl(NBU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        nbuService = nbuRetrofit.create(NbuService.class);
        nbuService.listEntities(nbuDate).enqueue(new Callback<List<NbuEntity>>() {
            @Override
            public void onResponse(Call<List<NbuEntity>> call, Response<List<NbuEntity>> response) {
                if(response.isSuccessful()){
                    nbuEntities = response.body();
                    nbuAdapter.setEntities(nbuEntities);
                }
            }

            @Override
            public void onFailure(Call<List<NbuEntity>> call, Throwable t) {
                Log.e("Cannot fetch data", t.getMessage());
            }
        });
    }

    public void onPickerClick(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String date = String.format(Locale.getDefault(), "%d.%d.%d", dayOfMonth, month + 1, year);
                try {
                    date = dateFormatter.format(dateFormatter.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                setDate(date, view);
            }
        });

        datePickerDialog.show();
    }

    private void setDate(String date, View view){
        switch (view.getId()){
            case R.id.date_picker_pb_button:
                datePbTextView.setText(date);
                pbDate = date;

                pbService.listEntities(date).enqueue(new Callback<PbEntityContainer>() {
                    @Override
                    public void onResponse(Call<PbEntityContainer> call, Response<PbEntityContainer> response) {
                        if(response.isSuccessful()){
                            pbEntities = response.body().getExchangeRate();
                            pbAdapter.setEntities(pbEntities);
                        }
                    }

                    @Override
                    public void onFailure(Call<PbEntityContainer> call, Throwable t) {
                        Log.e("Failed to refetch data", t.getMessage());
                    }
                });
                break;
            case R.id.date_picker_nbu_button:
                dateNbuTextView.setText(date);
                nbuDate = date;

                nbuService.listEntities(date).enqueue(new Callback<List<NbuEntity>>() {
                    @Override
                    public void onResponse(Call<List<NbuEntity>> call, Response<List<NbuEntity>> response) {
                        if(response.isSuccessful()){
                            nbuEntities = response.body();
                            nbuAdapter.setEntities(nbuEntities);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NbuEntity>> call, Throwable t) {
                        Log.e("Failed to refetch data", t.getMessage());
                    }
                });
                break;
        }
    }

    private int getPositionOfVaultInNbu(String vault, List<NbuEntity> entities){
        for (int i = 0; i < entities.size(); i++) {
            NbuEntity entity = entities.get(i);
            if(entity.getCurrencyCodeL() == null)
                continue;
            if (entity.getCurrencyCodeL().toLowerCase().equals(vault.toLowerCase()))
                return i;
        }
        return -1;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("pbDate", pbDate);
        outState.putString("nbuDate", nbuDate);
    }
}