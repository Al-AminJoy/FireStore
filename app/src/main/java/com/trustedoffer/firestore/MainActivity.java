package com.trustedoffer.firestore;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String title, prc;
    private EditText etTitle, etPrice, etItems;
    private Button btSave, btDelete, btNextPage;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<TestModelClass> list = new ArrayList<>();
    private TestAdapter adapter;
    private DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findId();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        db = FirebaseFirestore.getInstance();
        //ref Is For Defining Collection+Document which is DocumentPath
        //ref used in Update and Delete,but You Should Not Use ref While Using Random Generated ID of Document
        //usually Delete and Update is Used In Adapter...Here in Main Activity Used For Testing
        ref = db.document("sample/data");
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOp();
            }
        });
        btNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NextActivity.class);
                startActivity(intent);
            }
        });
    }

    private void deleteOp() {
        ref.delete();
    }

    private void savedata() {
        title = etTitle.getText().toString().trim();
        prc = etPrice.getText().toString().trim();
        int price = Integer.parseInt(prc);

        //This Is For Many Item in Item Field
        //Item Will Store In Database Like Array
        String item = etItems.getText().toString();
        //Split , From Item Field
        String[] inputs = item.split("\\s*,\\s*");
        //Converting Array Into Arraylist
        List<String> items = Arrays.asList(inputs);
        TestModelClass user = new TestModelClass(title, price, items);
        // Add a New Document/DataSet With a Random Generated ID
        db.collection("productList").add(user);
        loadData();


        //This Is Used For Store Data By Defining Collection and Document
        /*db.collection("sample").document("data")
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/
    }

    private void findId() {
        etTitle = findViewById(R.id.etProductTitle);
        etPrice = findViewById(R.id.etProductPrice);
        btSave = findViewById(R.id.btProductSave);
        recyclerView = findViewById(R.id.rvProductListFireStore);
        btDelete = findViewById(R.id.btProductDelete);
        etItems = findViewById(R.id.etProductItems);
        btNextPage = findViewById(R.id.btProductNextPage);


    }

    private void loadData() {
        list.clear();
        db.collection("productList").whereGreaterThanOrEqualTo("price", 50)
                .limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TestModelClass data = document.toObject(TestModelClass.class);
                                data.setKey(document.getId());
                                list.add(data);
                            }
                            Collections.reverse(list);
                            adapter = new TestAdapter(getApplicationContext(), list);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } else {
                        }
                    }
                });

        //This Proccedure Is For Read Data Using Defined Collection and Document
             /* ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                 @Override
                 public void onSuccess(DocumentSnapshot documentSnapshot) {
                     if (documentSnapshot.exists()){
                         TestModelClass data=documentSnapshot.toObject(TestModelClass.class);

                       *//*  String title=data.getTitle();
                       //  int price=Integer.parseInt(documentSnapshot.get(PRICE_KEY).toString());
                         int productPrice=data.getPrice();
                         TestModelClass data=new TestModelClass(title,productPrice);*//*
                         list.add(data);
                     }
                     Collections.reverse(list);
                     adapter=new TestAdapter(getApplicationContext(),list);
                     recyclerView.setAdapter(adapter);
                     adapter.notifyDataSetChanged();
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                 }
             });*/

    }

    @Override
    protected void onStart() {
        loadData();
        super.onStart();
    }
}
