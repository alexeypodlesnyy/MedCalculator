package com.example.araragi.medcalculator;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private EditText editSolution;
    private EditText editDoseInVolume;
    private EditText editWeight;
    private EditText editDoseOrdered;

    private TextView textInfusionRate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        editSolution = (EditText)findViewById(R.id.edit_solution);
        editDoseInVolume=(EditText) findViewById(R.id.edit_dose_in_volume);
        editWeight = (EditText) findViewById(R.id.edit_weight);
        editDoseOrdered = (EditText) findViewById(R.id.edit_dose_ordered);

        textInfusionRate = (TextView)findViewById(R.id.text_infusion_rate);




    }




    private double[] getNumbers(){

        boolean interrupt = false;
        String emptyFields = "";

        try {

            double volume = Double.parseDouble(editSolution.getText().toString());
            double doseInVolume = Double.parseDouble(editDoseInVolume.getText().toString());
            double weight = Double.parseDouble(editWeight.getText().toString());
            double doseOrdered = Double.parseDouble(editDoseOrdered.getText().toString());

            if(volume <= 0 || doseInVolume<=0 || weight <=0 || doseOrdered <=0){
                Toast.makeText(this, "Use correct numbers", Toast.LENGTH_LONG).show();
                return new double[]{0,0,0,0};
            }


            double[] result = {volume, doseInVolume, weight, doseOrdered};

            return result;

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Something go wrong. Try again.", Toast.LENGTH_LONG).show();
            return new double[]{0,0,0,0,};
        }
    }

    private double getInfusionRate(double[] numbers){

        if(numbers[0]==0 || numbers[1]==0 || numbers[2]==0 || numbers [3]==0){
            return 0;
        }

        double volume = numbers[0];
        double doseInVol = numbers[1];
        double weight = numbers[2];
        double doseOrdered = numbers[3];

        double result = (doseOrdered * weight * 60 * volume)/ doseInVol;

        return result/1000;

    }
    public void setTextInfusionRate(View view){

        double res = getInfusionRate(getNumbers());


        if(res <= 0){
            textInfusionRate.setText("Can't calculate");
        }

        else {
            String s = String.format("%.2f", res);
            textInfusionRate.setText("Infusion rate " + s + " mL/hr");
        }

    }

}
