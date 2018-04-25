package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.adapters.FoodAdapter;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.FoodChooseFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.FoodFragment;
import com.gpaschos_aikmpel.hotelbeaconapplication.fragments.OnClickAddToBasket;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.POST;
import com.gpaschos_aikmpel.hotelbeaconapplication.globalVars.URL;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.VolleyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomServiceActivity extends AppCompatActivity implements JsonListener, OnClickAddToBasket, FoodChooseFragment.FragmentCallBack {

    private List<Categories> categoriesList;

    private RoomServiceAdapter roomServiceAdapter;


    //Viewpager
    private ViewPager viewpager;
    private TabLayout tabLayout;

    //BottomSheet
    private BottomSheetBehavior bottomSheetBehavior;


    //Views
    private TextView orderTotalCount, orderTotalPrice;
    private EditText etComments;

    //RecyclerView
    private RecyclerView recyclerView;
    private FoodAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_service);

        viewpager = findViewById(R.id.vpRoomService);
        tabLayout = findViewById(R.id.tlRoomService);
        tabLayout.setupWithViewPager(viewpager);
        View bottomSheet = findViewById(R.id.roomServiceBottomSheet);
        orderTotalCount = bottomSheet.findViewById(R.id.tvBottomSheetCount);
        orderTotalPrice = bottomSheet.findViewById(R.id.tvBottomSheetTotalPrice);
        etComments = bottomSheet.findViewById(R.id.etBottomSheetComments);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        recyclerView = bottomSheet.findViewById(R.id.rvFoodFragmentRecycler);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerAdapter = new FoodAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        getCategories();
    }

    private void getCategories() {
        VolleyQueue.getInstance(this).jsonRequest(this, URL.roomServiceTimeCategoriesUrl, null);
    }

    private void getFood(String timeType) {
        Map<String, String> params = new HashMap<>();
        params.put(POST.roomServiceTimeType, timeType);
        VolleyQueue.getInstance(this).jsonRequest(this, URL.roomServiceFoodUrl, params);
    }

    public void makeOrder(View view) {
        if (recyclerAdapter.getItemCount() > 0) {
            List<FoodAdapter.FoodOrderView> foodList = recyclerAdapter.getFoodList();
            JSONObject json = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (FoodAdapter.FoodOrderView foodOrderView : foodList) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put(POST.roomServiceOrderID, String.valueOf(foodOrderView.id));
                    obj.put(POST.roomServiceOrderPrice, String.valueOf(foodOrderView.price));
                    obj.put(POST.roomServiceOrderQuantity, String.valueOf(foodOrderView.quantity));
                    jsonArray.put(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            try {
                json.put(POST.roomServiceOrderArray,jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Map<String,String> params = new HashMap<>();
            //TODO reservationID how to get it
            int resID = 90;
            params.put(POST.roomServiceOrderReservationID,String.valueOf(resID));
            params.put(POST.roomServiceOrderJson,json.toString());
            params.put(POST.roomServiceOrderComments,etComments.getText().toString());
            VolleyQueue.getInstance(this).jsonRequest(this,URL.orderUrl,params);
        } else {
            Toast.makeText(this, "Please select some items first!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {
        switch (url) {
            case URL.orderUrl:
                Intent intent = new Intent(this,RoomServiceConfirmationActivity.class);
                startActivity(intent);
                finish();
                break;
            case URL.roomServiceTimeCategoriesUrl:
                JSONArray jsonArray = json.getJSONArray(POST.roomServiceTimeCategory);
                categoriesList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt(POST.roomServiceCategoriesID);
                    String name = jsonObject.getString(POST.roomServiceCategoriesName);
                    String from = jsonObject.getString(POST.roomServiceCategoriesFrom);
                    String to = jsonObject.getString(POST.roomServiceCategoriesTo);
                    categoriesList.add(new Categories(id, name, from, to));
                }
                ////TODO Change this to pick optimal meal for now
                //getFood(categoriesList.get(1).getName());
                Calendar now = Calendar.getInstance();

                now.set(Calendar.HOUR_OF_DAY,Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                now.set(Calendar.MINUTE,Calendar.getInstance().get(Calendar.MINUTE));
                now.set(Calendar.SECOND,Calendar.getInstance().get(Calendar.SECOND));
                boolean flag = true;
                for (Categories cat:categoriesList) {
                    String[] timefrom = cat.from.split(":");
                    Calendar from = Calendar.getInstance();
                    from.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timefrom[0]));
                    from.set(Calendar.MINUTE,Integer.parseInt(timefrom[1]));
                    from.set(Calendar.SECOND,Integer.parseInt(timefrom[2]));

                    String[] timeto = cat.to.split(":");
                    Calendar to = Calendar.getInstance();
                    to.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeto[0]));
                    to.set(Calendar.MINUTE,Integer.parseInt(timeto[1]));
                    to.set(Calendar.SECOND,Integer.parseInt(timeto[2]));
                    if(now.after(from) && now.before(to)){
                        getFood(cat.getName());
                        flag = false;
                    }
                }
                if(flag){
                    Toast.makeText(this, "Sorry! Nothing is served this hour!", Toast.LENGTH_SHORT).show();
                }
                break;
            case URL.roomServiceFoodUrl:
                JSONArray jsonArrayTypes = json.getJSONArray(POST.roomServiceTypeCategory);

                List<RoomServiceModel> roomServiceModelList = new ArrayList<>();

                for (int i = 0; i < jsonArrayTypes.length(); i++) {

                    String category = jsonArrayTypes.getJSONObject(i).names().getString(0);
                    JSONArray jsonArrayFood = jsonArrayTypes.getJSONObject(i).getJSONArray(category);

                    List<RoomServiceModel.FoodModel> foodList = new ArrayList<>();

                    for (int j = 0; j < jsonArrayFood.length(); j++) {

                        JSONObject jsonObject = jsonArrayFood.getJSONObject(j);

                        int id = jsonObject.getInt(POST.roomServiceFoodID);
                        String name = jsonObject.getString(POST.roomServiceFoodName);
                        String description = jsonObject.getString(POST.roomServiceFoodDesc);
                        if (description.equalsIgnoreCase("null")) {
                            description = "";
                        }
                        double price = jsonObject.getDouble(POST.roomServiceFoodPrice);

                        foodList.add(new RoomServiceModel.FoodModel(id, name, description, price));
                    }

                    roomServiceModelList.add(new RoomServiceModel(category, foodList));
                }
                roomServiceAdapter = new RoomServiceAdapter(getSupportFragmentManager(), roomServiceModelList);
                viewpager.setAdapter(roomServiceAdapter);
                break;
        }

    }

    @Override
    public void getErrorResult(String url, String error) {
        Toast.makeText(this, url + ": " + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void addBasket(RoomServiceModel.FoodModel foodModel) {
        DialogFragment dialogFragment = FoodChooseFragment.newInstance(foodModel);
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void receiveFoodQuantity(RoomServiceModel.FoodModel foodModel, int quantity) {

        recyclerAdapter.addFood(new FoodAdapter.FoodOrderView(foodModel.getId(), foodModel.getName(), quantity, foodModel.getPrice()));
        int i = Integer.parseInt(orderTotalCount.getText().toString()) + 1;
        orderTotalCount.setText(String.valueOf(i));
        Log.d("aaa", String.valueOf(recyclerAdapter.totalCost()));
        orderTotalPrice.setText(String.valueOf(recyclerAdapter.totalCost()));

    }


    private class RoomServiceAdapter extends FragmentStatePagerAdapter {

        private List<RoomServiceModel> modelList;

        public RoomServiceAdapter(FragmentManager fm, List<RoomServiceModel> modelList) {
            super(fm);
            this.modelList = modelList;
        }

        @Override
        public Fragment getItem(int position) {
            return FoodFragment.newInstance(modelList.get(position));
        }

        @Override
        public int getCount() {
            return modelList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return modelList.get(position).type;
        }
    }

    public static class RoomServiceModel implements Parcelable {
        String type;
        List<FoodModel> foodList = new ArrayList<>();


        public RoomServiceModel(String type, List<FoodModel> foodList) {
            this.type = type;
            this.foodList = foodList;
        }

        private RoomServiceModel(Parcel parcel) {
            parcel.readList(foodList, null);
            type = parcel.readString();
        }

        public List<FoodModel> getFoodList() {
            return foodList;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(foodList);
            dest.writeString(type);
        }

        public static final Parcelable.Creator<RoomServiceModel> CREATOR =
                new Parcelable.Creator<RoomServiceModel>() {

                    @Override
                    public RoomServiceModel createFromParcel(Parcel source) {
                        return new RoomServiceModel(source);
                    }

                    @Override
                    public RoomServiceModel[] newArray(int size) {
                        return new RoomServiceModel[size];
                    }
                };


        public static class FoodModel implements Parcelable {
            int id;
            String name;
            String description;
            double price;

            public FoodModel(int id, String name, String description, double price) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.price = price;
            }

            protected FoodModel(Parcel in) {
                id = in.readInt();
                name = in.readString();
                description = in.readString();
                price = in.readDouble();
            }

            public static final Creator<FoodModel> CREATOR = new Creator<FoodModel>() {
                @Override
                public FoodModel createFromParcel(Parcel in) {
                    return new FoodModel(in);
                }

                @Override
                public FoodModel[] newArray(int size) {
                    return new FoodModel[size];
                }
            };

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getDescription() {
                return description;
            }

            public double getPrice() {
                return price;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeString(name);
                dest.writeString(description);
                dest.writeDouble(price);
            }
        }
    }

    public static class Categories {
        private int id;
        private String name, from, to;

        Categories(int id, String name, String from, String to) {
            this.id = id;
            this.name = name;
            this.from = from;
            this.to = to;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }
    }

}
