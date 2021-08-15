package com.example.yourlocation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.yourlocation.databinding.ActivityPaymentBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
ActivityPaymentBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        binding.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

//        String getAmount = getIntent().getStringExtra("amount");
//        float  amountEntered = Math.round(Float.parseFloat(getAmount)*100);


        String money = "10";
        float amount = Math.round(Float.parseFloat(money) *100);

        binding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_f7ANdhwAMfvreF");
                checkout.setImage(R.drawable.logo);
                JSONObject object = new JSONObject();

                try {
                    object.put("name","Nabeel Mirza");
                    object.put("description", "Test Payment");
                    object.put("amount",amount);
                    object.put("theme.color","#0093DD");
                    object.put("currency","INR");
                    checkout.open(PaymentActivity.this,object);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Payment Id");
        alertDialog.setMessage(s);
        alertDialog.show();
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}