package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.gpaschos_aikmpel.hotelbeaconapplication.utility.SyncModel;


//@Entity(foreignKeys = {@ForeignKey(entity = RoomType.class, parentColumns = "id", childColumns = "roomTypeID"),
//        @ForeignKey(entity = Beacon.class, parentColumns = "id", childColumns = "roomBeaconId")})
@Entity
public class Reservation implements SyncModel {
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
    private String roomPassword;
    private int roomNumber;
    private int roomFloor;
    private String modified;
    private double rating;
    private String ratingComments;

    @Ignore
    public Reservation(int id, int roomTypeID, int adults, int children, String bookDate, String startDate, String endDate, String checkIn, String checkOut, int roomNumber, int roomFloor, double rating, String ratingComments, String modified) {
        this.id = id;
        this.roomTypeID = roomTypeID;
        this.adults = adults;
        this.children = children;
        this.bookDate = bookDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomNumber = roomNumber;
        this.roomFloor = roomFloor;
        this.rating = rating;
        this.ratingComments = ratingComments;
        this.modified = modified;
    }

    public Reservation(int id, int roomTypeID, int adults, int children, String bookDate, String startDate, String endDate, String modified) {
        this.id = id;
        this.roomTypeID = roomTypeID;
        this.adults = adults;
        this.children = children;
        this.bookDate = bookDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.modified = modified;
    }

    public void checkIn(String checkIn, int roomNumber, int roomFloor, String roomPassword, String modified) {
        this.checkIn = checkIn;
        this.roomNumber = roomNumber;
        this.roomFloor = roomFloor;
        this.roomPassword = roomPassword;
        this.modified = modified;
    }

    public void checkOut(String checkOut, String modified) {
        this.checkOut = checkOut;
        this.modified = modified;
    }

    public void update(int id, int roomTypeID, int adults, int children, String bookDate, String startDate, String endDate, String checkIn, String checkOut, int roomNumber, int roomFloor, double rating, String ratingComments, String modified) {
        this.id = id;
        this.roomTypeID = roomTypeID;
        this.adults = adults;
        this.children = children;
        this.bookDate = bookDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomNumber = roomNumber;
        this.roomFloor = roomFloor;
        this.rating = rating;
        this.ratingComments = ratingComments;
        this.modified = modified;
    }

    public boolean isReviewed() {
        return rating!=0.0f;
    }

    public boolean isCheckedIn() {
        return checkIn != null;
    }

    public boolean isCheckedOut() {
        return checkOut != null;
    }

    public boolean isCheckedInNotCheckedOut() {
        return isCheckedIn() && !isCheckedOut();
    }

    public boolean isCheckedInCheckedOut() {
        return isCheckedIn() && isCheckedOut();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
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

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getRatingComments() {
        return ratingComments;
    }

    public void setRatingComments(String ratingComments) {
        this.ratingComments = ratingComments;
    }
}