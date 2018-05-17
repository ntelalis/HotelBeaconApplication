package com.gpaschos_aikmpel.hotelbeaconapplication.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gpaschos_aikmpel.hotelbeaconapplication.R;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;
import com.gpaschos_aikmpel.hotelbeaconapplication.requestVolley.JsonListener;

import org.altbeacon.beacon.BeaconConsumer;
import org.json.JSONException;
import org.json.JSONObject;

public class DoorUnlockActivity extends AppCompatActivity implements JsonListener,BeaconConsumer {

    private static final String TAG = DoorUnlockActivity.class.getSimpleName();

    private Button btnDoorUnlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_unlock);

        //btnDoorUnlock = findViewById(R.id.btnDoorUnlock);

    }

    public void unlockDoor(View view){
        Reservation r =RoomDB.getInstance(this).reservationDao().getCurrentReservation();
        if(r!=null && r.isCheckedInNotCheckedOut()){
            int reservationId = r.getId();
            String roomPass = r.getRoomPassword();

        }
        else{
            Toast.makeText(this, "No active reservation found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getSuccessResult(String url, JSONObject json) throws JSONException {

    }

    @Override
    public void getErrorResult(String url, String error) {

    }

    @Override
    public void onBeaconServiceConnect() {

    }

}
