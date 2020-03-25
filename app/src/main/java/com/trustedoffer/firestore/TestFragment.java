package com.trustedoffer.firestore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;


public class TestFragment extends Fragment {
   //This Method For Pass Value For TextView
    public static TestFragment newInstance(int val) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", val);
        fragment.setArguments(args);
        return fragment;
    }

    private int val;
    private TextView testText;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<TestModelClass> list=new ArrayList<>();
    private TestAdapter adapter;
    private DocumentReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test, container, false);

        val = getArguments().getInt("someInt", 0);
        testText = view.findViewById(R.id.tvFragText);
        testText.setText("" + val);
        findId(view);
        //For Refresh
        ((NextActivity)getActivity()).setFragmentRefreshListener(new NextActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        db = FirebaseFirestore.getInstance();
       // ref=db.document("sample/data");
        loadData();
        return view;
    }

    private void findId(View view) {
        recyclerView=view.findViewById(R.id.rvTestProductListFireStore);
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
                            adapter = new TestAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        } else {
                        }
                    }
                });
    }

}
