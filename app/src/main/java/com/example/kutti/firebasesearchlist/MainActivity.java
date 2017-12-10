package com.example.kutti.firebasesearchlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

public class MainActivity  extends AppCompatActivity {
    EditText edittextsearch;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> modelnamelist;
    ArrayList<String> pricelist;
    ArrayList<String> shopnamelist;
    ArrayList<String> speclist;
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edittextsearch = (EditText) findViewById(R.id.edittextsearch);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        /*
        * Create a array list for each node you want to use
        * */
        modelnamelist  = new ArrayList<>();
        pricelist  = new ArrayList<>();
        shopnamelist  = new ArrayList<>();
        speclist = new ArrayList<>();

        edittextsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    /*
                    * Clear the list when editText is empty
                    * */
                    modelnamelist.clear();
                    pricelist.clear();
                    shopnamelist.clear();
                    recyclerView.removeAllViews();




                }
            }
        });
    }

    private void setAdapter(final String searchedstring) {
        databaseReference.child("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*
                * Clear the list for every new search
                * */
                modelnamelist.clear();
                pricelist.clear();
                shopnamelist.clear();
                recyclerView.removeAllViews();
                searchAdapter.notifyDataSetChanged();


                int counter = 0;

                /*
                * Search all users for matching searched string
                * */
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String model_name = snapshot.child("model_name").getValue(String.class);
                    String price_name = snapshot.child("price_name").getValue(String.class);
                    String shop_name = snapshot.child("shop_name").getValue(String.class);
                    String spec_name = snapshot.child("spec_name").getValue(String.class);

                    if (model_name.toLowerCase().contains(searchedstring.toLowerCase())) {
                        modelnamelist.add(model_name);
                        pricelist.add(price_name);
                        shopnamelist.add(shop_name);
                        counter++;
                    }

                    /*
                    * Get maximum of 15 searched results only
                    * */
                    if (counter == 5)
                        break;
                }

                searchAdapter = new SearchAdapter(MainActivity.this, modelnamelist, pricelist, shopnamelist,speclist);
                searchAdapter.setListener(new SearchAdapter.Listener() {
                    @Override
                    public void onclick(int position) {
                        Intent i = new Intent(MainActivity.this, SpectActivity.class);
                        i.putExtra("products",speclist);
                        startActivity(i);
                    }
                });
                recyclerView.setAdapter(searchAdapter);
                

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}