package com.preesoft.mortgagevip.Mortgage_Rate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.preesoft.mortgagevip.ModelClass.ViewRates;
import com.preesoft.mortgagevip.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class ViewRatesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private FirebaseRecyclerAdapter<ViewRates, ViewRatesHolder> adapter;
    private DatabaseReference mReference;
    private ArrayList<ViewRates> arrayList  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rates);

        avLoadingIndicatorView = findViewById(R.id.avi);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        mReference = FirebaseDatabase.getInstance().getReference("Rates");


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<ViewRates>()
                .setQuery(mReference, ViewRates.class)
                .build();

        avLoadingIndicatorView.setVisibility(android.view.View.VISIBLE);


        adapter = new FirebaseRecyclerAdapter<ViewRates, ViewRatesActivity.ViewRatesHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewRatesActivity.ViewRatesHolder holder, final int position, @NonNull ViewRates model) {
                final String key = getRef(position).getKey();

                if (key != null) {
                    mReference.child(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                avLoadingIndicatorView.setVisibility(android.view.View.GONE);

                                ViewRates viewRates = dataSnapshot.getValue(ViewRates.class);

                                arrayList.add(viewRates);

                                holder.lender.setText(viewRates.getLender());
                                holder.rate.setText(viewRates.getRate()+"%");
                                holder.apr.setText("$"+viewRates.getApr());
                                holder.moPayment.setText("$"+viewRates.getMoPayment());
                                holder.upfrontCost.setText("$"+viewRates.getUpfrontCost());

//                                Glide.with(HomeActivity.this).load(forms.getImage()).into(holder.userImage);


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            avLoadingIndicatorView.hide();
                        }
                    });
                }


            }

            @NonNull
            @Override
            public ViewRatesActivity.ViewRatesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                android.view.View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_view_rates_layout, viewGroup, false);
                ViewRatesActivity.ViewRatesHolder viewHolder = new ViewRatesActivity.ViewRatesHolder(view);


//                ViewHolder.setOnClickListener(new formViewHolder.ClickListener() {
//                    @Override
//                    public void onItemClick(android.view.View view, int position) {
//
//                        Forms values = arrayList.get(position);
//
//                        Intent intent = new Intent(HomeActivity.this, FormDetailActivity.class);
//                        intent.putExtra("firstName", values.getFirstName());
//                        intent.putExtra("lastName", values.getLastName());
//                        intent.putExtra("employeeId", values.getEmployeeId());
//                        intent.putExtra("email", values.getEmail());
//                        intent.putExtra("image", values.getImage());
//                        intent.putExtra("title", values.getTitle());
//                        intent.putExtra("moreInfo", values.getBoxSix());
//                        intent.putExtra("extraInfo", values.getBoxSeven());
//                        startActivity(intent);
//
//                    }
//
//
//                });

                return viewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public static class ViewRatesHolder extends RecyclerView.ViewHolder {
        private TextView lender, rate, apr, moPayment, upfrontCost;


        public ViewRatesHolder(@NonNull android.view.View itemView) {
            super(itemView);

            lender = itemView.findViewById(R.id.lenderName);
            rate = itemView.findViewById(R.id.rate);
            apr = itemView.findViewById(R.id.apr);
            moPayment = itemView.findViewById(R.id.moPayment);
            upfrontCost = itemView.findViewById(R.id.upFrontCost);


//            cardView.setOnClickListener(new android.view.View.OnClickListener() {
//                @Override
//                public void onClick(android.view.View v) {
//                    mClickListener.onItemClick(v, getAdapterPosition());
//
//                }
//            });


        }

//        private formViewHolder.ClickListener mClickListener;

        //Interface to send callbacks...
//        public interface ClickListener {
//            void onItemClick(View view, int position);
//        }

//        public void setOnClickListener(formViewHolder.ClickListener clickListener) {
//            mClickListener = clickListener;
//        }
    }
}
