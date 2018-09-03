package com.gpaschos_aikmpel.hotelbeaconapplication.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.ExclusiveOffer;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.alert.ViewOfferDetailsFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.functions.SyncServerData;

import java.util.List;

public class OfferExclusiveFragment extends Fragment implements SyncServerData.SyncCallbacksSwipeRefresh{

    private FragmentCallbacks fragmentCallbacks;
    public static final String TAG = OfferExclusiveFragment.class.getSimpleName();
    private List<ExclusiveOffer> offersList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public OfferExclusiveFragment() {
        // Required empty public constructor
    }

    public static OfferExclusiveFragment newInstance() {

        Bundle args = new Bundle();

        OfferExclusiveFragment fragment = new OfferExclusiveFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallbacks) {
            fragmentCallbacks = (FragmentCallbacks) context;
        } else {
            throw new ClassCastException("interface FragmentCallbacks must be implemented");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offersList = RoomDB.getInstance(getContext()).exclusiveOfferDao().getExclusiveOffersForRecyclerView();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offer_exclusive, container, false);

        recyclerView = view.findViewById(R.id.rvOfferExclusive);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter myAdapter = new ExclusiveOfferAdapter(offersList);
        recyclerView.setAdapter(myAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                Log.i(TAG, "onRefresh called from SwipeRefreshLayout");
                myUpdateOperation();
            }
        });
        return view;
    }

    private void myUpdateOperation(){
        SyncServerData.getInstance(this, getContext()).getExclusiveOffers(RoomDB.getInstance(getContext()).customerDao().getCustomer());
    }

    public void refreshData() {
        offersList = RoomDB.getInstance(getContext()).exclusiveOfferDao().getExclusiveOffersForRecyclerView();
        recyclerView.setAdapter(new ExclusiveOfferAdapter(offersList));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void syncingFinished() {
        Log.d(TAG,"syncing done");
        swipeRefreshLayout.setRefreshing(false);
        refreshData();
    }


    private class ExclusiveOfferAdapter extends RecyclerView.Adapter<ExclusiveOfferAdapter.ExclusiveOfferViewHolder> {

        private List<ExclusiveOffer> offerList;

        ExclusiveOfferAdapter(List<ExclusiveOffer> offerList) {
            this.offerList = offerList;
        }

        @NonNull
        @Override
        public ExclusiveOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = null;
            int offerType = 0;
            switch (viewType) {
                case 0:
                    view = layoutInflater.inflate(R.layout.viewholder_offer_exclusive, parent, false);
                    offerType = 0;
                    break;
                case 1:
                    view = layoutInflater.inflate(R.layout.viewholder_offer_special, parent, false);
                    offerType = 1;
                    break;
            }
            return new ExclusiveOfferViewHolder(view, offerType);
        }

        @Override
        public void onBindViewHolder(@NonNull ExclusiveOfferViewHolder holder, int position) {
            ExclusiveOffer offer = offerList.get(position);
            holder.setData(offer.getTitle(), offer.getDescription(), offer.getDetails(), offer.getPriceDiscount(), offer.getCode(), offer.getId());
        }

        @Override
        public int getItemViewType(int position) {
            return offerList.get(position).isSpecial() ? 1 : 0;
        }

        @Override
        public int getItemCount() {
            return offerList.size();
        }

        class ExclusiveOfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView tvTitle;
           // private TextView tvDescription;
            private TextView tvDetails;
            private TextView tvDiscountPrice;
            private TextView tvCode;
            private TextView tvCodeLabel;
            private int offerID;
            private String description;
            private Button btnCode;

            public ExclusiveOfferViewHolder(View itemView, int offerType) {
                super(itemView);
                switch (offerType) {
                    case 0:
                        tvTitle = itemView.findViewById(R.id.tvOfferExclusiveTitle);
                        tvDetails = itemView.findViewById(R.id.tvOfferExclusiveDetails);
                        tvDiscountPrice = itemView.findViewById(R.id.tvOfferExclusiveDiscountPrice);
                        tvCode = itemView.findViewById(R.id.tvOfferExclusiveCode);
                        tvCodeLabel = itemView.findViewById(R.id.tvOfferExclusiveCodeLabel);
                        btnCode = itemView.findViewById(R.id.btnOfferExclusiveCode);
                        btnCode.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // TODO
                                fragmentCallbacks.getCoupon(offerID);
                            }
                        });
                        break;
                    case 1:
                        tvTitle = itemView.findViewById(R.id.tvOfferSpecialTitle);
                        tvDetails = itemView.findViewById(R.id.tvOfferSpecialDetails);
                        tvDiscountPrice = itemView.findViewById(R.id.tvOfferSpecialDiscountPrice);
                        tvCode = itemView.findViewById(R.id.tvOfferSpecialCode);
                        tvCodeLabel = itemView.findViewById(R.id.tvOfferSpecialCodeLabel);
                        break;
                }
                //MEGALES ALLAGES!!!!!!!!!!!
                itemView.setOnClickListener(this);
            }

            void setTitle1(String title) {
                tvTitle.setText(title);
            }

            public void setDescription1(String description) {
                this.description = description;
            }


            public void setDetails(String details) {
                tvDetails.setText(details);
            }

            void setDiscountPrice(String discountPrice) {
                tvDiscountPrice.setText(discountPrice);
            }

            void setOfferID(int offerID) {
                this.offerID = offerID;
            }

            public void setCode(String code) {
                tvCode.setText(code);
                if (code != null) {
                    setCodeVisibility(View.VISIBLE);
                    if (btnCode != null) {
                        btnCode.setEnabled(false);
                    }

                } else {
                    setCodeVisibility(View.GONE);
                    if (btnCode != null) {
                        btnCode.setEnabled(true);
                    }
                }
            }

            private void setCodeVisibility(int visibility) {
                tvCode.setVisibility(visibility);
                tvCodeLabel.setVisibility(visibility);
            }


            public String getTitle1() {
                return tvTitle.getText().toString();
            }


            public String getDescription1() {
                return description;
            }

            public String getDetails() {
                return tvDetails.getText().toString();
            }

            public String getDiscountPrice() {
                return tvDiscountPrice.getText().toString();
            }

            public int getOfferID() {
                return this.offerID;
            }

            public String getCode() {
                return tvCode.getText().toString();
            }

            void setData(String title, String description, String details, String discountPrice, String code, int offerID) {
                setTitle1(title);
                setDescription1(description);
                setDetails(details);
                setDiscountPrice(discountPrice);
                setCode(code);
                setOfferID(offerID);
            }

            @Override
            public void onClick(View v) {
                Log.d("offer tittle: ", getTitle1());
                DialogFragment descriptionFragment = ViewOfferDetailsFragment.newInstance(description, getTitle1());
                if(getFragmentManager() !=null) {
                    descriptionFragment.show(getFragmentManager(), ViewOfferDetailsFragment.TAG);
                }
                else{
                    Log.e(TAG,"SupportFragmentManager is null");
                }
            }
        }

    }


    public interface FragmentCallbacks {
        void getCoupon(int offerID);
    }
}
