package com.trustedoffer.firestore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;*/

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private Context context;
    private ArrayList<TestModelClass> datalist;
    /*private FirebaseStorage storageReference;
    private DatabaseReference databaseReference;*/

    public TestAdapter(Context context, ArrayList<TestModelClass> datalist) {
        this.context = context;
        this.datalist = datalist;
    }
    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.product_layout,parent,false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, final int position) {
        final TestModelClass data=datalist.get(position);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        /*storageReference=  FirebaseStorage.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("test");*/

        holder.tvTitle.setText(data.getTitle());
        holder.tvPrice.setText(String.valueOf(data.getPrice()));
        //Storing Item List In StringBuilder
        List<String> itemValue=data.getItems();
        StringBuilder stringBuilder=new StringBuilder("");
        try{
            for (String item:itemValue){
                stringBuilder.append(item+"\n");
            }
        }
        catch (Exception e){

        }

        holder.tvItems.setText(stringBuilder);

       holder.btDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String key=data.getKey();
               db.document("productList/"+""+key).delete();
               //For Remove From ArrayList
               datalist.remove(position);
               notifyDataSetChanged();
           }
       });
        holder.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=data.getKey();
                db.document("productList/"+""+key).update("price",10000);
                //For Update ArrayLisy
                datalist.set(position,new TestModelClass("Updated",100000,data.getItems()));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvPrice,tvItems;
        private ImageView ivProductImage;
        private Button btDelete,btUpdate;
        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvProductLayTitle);
            tvPrice=itemView.findViewById(R.id.tvProductLayPrice);
            tvItems=itemView.findViewById(R.id.tvProductLayItems);
            ivProductImage=itemView.findViewById(R.id.ivProductLayProductImage);
            btDelete=itemView.findViewById(R.id.btProductLayDeleteButtonId);
            btUpdate=itemView.findViewById(R.id.btProductLayUpdateButtonId);
        }
    }
}
