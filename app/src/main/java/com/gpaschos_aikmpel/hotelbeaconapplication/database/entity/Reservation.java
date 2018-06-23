package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


//@Entity(foreignKeys = {@ForeignKey(entity = RoomType.class, parentColumns = "id", childColumns = "roomTypeID"),
//        @ForeignKey(entity = Beacon.class, parentColumns = "id", childColumns = "roomBeaconId")})
@Entity
public class Reservation {
    @PrimaryKey
    private int id;
    private int roomTypeID;
    private int adults;
    private int children;
    private String bookDate;
    private String startDate;
    private String endDate;
    private String checkIn;
    private String checkOut;
    private int roomBeaconId;
    private String roomPassword;
    private int roomNumber;
    private int roomFloor;

    public Reservation(int id, int roomTypeID, int adults, int children ,String bookDate, String startDate, String endDate) {
        this.id = id;
        this.roomTypeID = roomTypeID;
        this.adults = adults;
        this.children = children;
        this.bookDate = bookDate;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public void checkIn(String checkIn, int roomNumber, int roomFloor, String roomPassword, int roomBeaconId) {
        this.checkIn = checkIn;
        this.roomNumber = roomNumber;
        this.roomFloor = roomFloor;
        this.roomPassword = roomPassword;
        this.roomBeaconId = roomBeaconId;
    }

    public void update(int id, int roomTypeID, int adults, int children ,String bookDate, String startDate, String endDate, String checkIn, String checkOut, int roomNumber, int roomFloor, int roomBeaconId){
        this.id = id;
        this.roomTypeID = roomTypeID;
        this.adults = adults;
        this.children = children;
        this.bookDate = bookDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomBeaconId = roomBeaconId;
        this.roomNumber = roomNumber;
        this.roomFloor = roomFloor;
    }


    public boolean isCheckedIn() {
        return checkIn != null;
    }


    public boolean isCheckedOut() {
        return checkOut != null;
    }

    public boolean isCheckedInNotCheckedOut(){
        return isCheckedIn() && !isCheckedOut();
    }

    public boolean isCheckedInCheckedOut(){
        return isCheckedIn() && isCheckedOut();
    }


    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setRoomBeaconId(int roomBeaconId) {
        this.roomBeaconId = roomBeaconId;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public int getId() {
        return id;
    }


    public int getRoomTypeID() {
        return roomTypeID;
    }


    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getBookDate() {
        return bookDate;
    }

    public String getStartDate() {
        return startDate;
    }


    public String getEndDate() {
        return endDate;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public int getRoomBeaconId() {
        return roomBeaconId;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getRoomFloor() {
        return roomFloor;
    }
}
