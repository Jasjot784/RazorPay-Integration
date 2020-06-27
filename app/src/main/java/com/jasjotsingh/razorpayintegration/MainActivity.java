package com.jasjotsingh.razorpayintegration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText etAmount;
   // EditText etPhone,etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.etMoney);
//        etPhone = findViewById(R.id.etPhone);
//        etEmail = findViewById(R.id.etEmail);


        Checkout.preload(getApplicationContext());

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

      //  TextView privacyPolicy = (TextView) findViewById(R.id.txt_privacy_policy);

//        privacyPolicy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
//                httpIntent.setData(Uri.parse("https://razorpay.com/sample-application/"));
//                startActivity(httpIntent);
//            }
//        });

    }
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String s) {
        try {
            Toast.makeText(this, "Payment Successful: " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int i, String s) {
        try {
            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startPayment(){
        final Activity activity = this;
        String payment = etAmount.getText().toString();
        double amount = Double.parseDouble(payment);
        amount = amount*100;
        final Checkout co = new Checkout();
        co.setKeyID("rzp_test_L76EmCRbcuEcev");
        int image = R.drawable.ic_launcher_background; // Can be any drawable
        co.setImage(image);


        try {
            JSONObject options = new JSONObject();
            options.put("name", "At Doc");
            options.put("description", "Charges");
            //You can omit the image option to fetch the image from dashboard
            //options.put("image", getDrawable(R.drawable.ic_launcher_foreground));
            options.put("currency", "INR");
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "nowonderwhoiam123@gmail.com");
            preFill.put("contact", "9463809630");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }
}