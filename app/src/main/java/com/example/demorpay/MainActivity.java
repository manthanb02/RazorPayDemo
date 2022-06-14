package com.example.demorpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.ExternalWalletListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements PaymentResultListener
{

    Button paybtn;
    TextView paytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To ensure faster loading of the Checkout form, call this method as early as possible in your checkout flow.
        Checkout.preload(getApplicationContext());

        paytext=(TextView)findViewById(R.id.paytext);
        paybtn=(Button)findViewById(R.id.paybtn);

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makepayment();
            }
        });

    }

    private void makepayment()
    {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_gTM9m9XQSFWHPc");//key which is generated on razorpay account

//        checkout.setImage(R.drawable.logo);
        // You need to pass current activity in order to let Razorpay create CheckoutActivity
        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "MarkTech");
            options.put("description", "Reference No. #123456");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//             options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "30000");//300 X 100
            options.put("prefill.email", "manthanbendkhale@gmail.com");
            options.put("prefill.contact","9130205500");
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s)
    {
        paytext.setText("Successful payment ID :"+s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        paytext.setText("Failed and cause is :"+s);
    }


}