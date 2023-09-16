package com.example.assignment_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tipAmount;
    TextView totalAmount;
    TextView customTipProgress;
    SeekBar customTipSeek;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Even listeners for reset and calculate buttons
        findViewById(R.id.resetBtn).setOnClickListener(this);
        findViewById(R.id.calcBtn).setOnClickListener(this);

        customTipSeek = findViewById(R.id.customTipSeek);
        customTipProgress = findViewById(R.id.customTipProgress);

        customTipSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                customTipProgress.setText(String.valueOf(i) + "%");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        // Handles button inputs
        if(view.getId() == R.id.resetBtn){
            reset();
        } else if (view.getId() == R.id.calcBtn) {
            calcTipTotal();
        }
    }

    public void reset(){
        tipAmount = findViewById(R.id.tipAmount);
        totalAmount = findViewById(R.id.totalAmount);
        radioGroup = findViewById(R.id.radioGroup);
        customTipSeek = findViewById(R.id.customTipSeek);
        customTipProgress = findViewById(R.id.customTipProgress);
        EditText editText = findViewById(R.id.checkValueInput);
        editText.setText("");
        customTipProgress.setText("25%");
        customTipSeek.setProgress(25);
        radioGroup.check(R.id.tenPerTip);
        tipAmount.setText("");
        totalAmount.setText("");
    }

    public void calcTipTotal(){
        EditText editText = findViewById(R.id.checkValueInput);
        String inputString = editText.getText().toString();
        radioGroup = findViewById(R.id.radioGroup);
        int checkedId = radioGroup.getCheckedRadioButtonId();

        double tip = 0.0;
        double tipAmt = 0.0;
        double total = 0.0;

        if (inputString.isEmpty()) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
            return;
        }

        double input = Double.parseDouble(inputString);
        if (input < 0) {
            Toast.makeText(this, "Input must be greater than or equal to 0", Toast.LENGTH_SHORT).show();
            return;
        }

        if(checkedId == R.id.tenPerTip){
            tip = 0.1;
        } else if (checkedId == R.id.fifteenPerTIp) {
            tip = 0.15;
        } else if (checkedId == R.id.eighteenPerTip) {
            tip = 0.18;
        } else if (checkedId == R.id.customTipRadio) {
            tip = customTip(input);
        }


        tipAmt = tip * input;
        total = tipAmt + input;

        tipAmount = findViewById(R.id.tipAmount);
        tipAmount.setText("$" + String.format("%,.2f", tipAmt));

        totalAmount = findViewById(R.id.totalAmount);
        totalAmount.setText("$" + String.format("%,.2f", total));
    }

    public double customTip(double input){
       double tip = customTipSeek.getProgress();
        return (tip/ 100.0);
    }
}