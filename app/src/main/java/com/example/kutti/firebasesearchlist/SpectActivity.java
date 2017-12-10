package com.example.kutti.firebasesearchlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by KUTTI on 11/30/2017.
 */

public class SpectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec);

        SearchAdapter products = getIntent().getParcelableExtra("products");

        // Product Spec
        TextView specTextView = (TextView) findViewById(R.id.spec_name);
        specTextView.setText((CharSequence) products.spec_name);//specification

        getSupportActionBar().setTitle((CharSequence) products.speclist); //model_name
    }
}
