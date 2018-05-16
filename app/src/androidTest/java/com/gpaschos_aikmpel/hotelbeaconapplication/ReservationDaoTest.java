package com.gpaschos_aikmpel.hotelbeaconapplication;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.gpaschos_aikmpel.hotelbeaconapplication.database.RoomDB;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.dao.ReservationDao;
import com.gpaschos_aikmpel.hotelbeaconapplication.database.entity.Reservation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ReservationDaoTest {
    private ReservationDao reservationDao;
    private RoomDB roomDB;
    private Reservation inReservation, outReservation;

    @Before
    public void createDB() {
        Context context = InstrumentationRegistry.getTargetContext();
        roomDB = Room.inMemoryDatabaseBuilder(context, RoomDB.class).build();
        reservationDao = roomDB.reservationDao();
            /*Calendar c =Calendar.getInstance();
            Calendar c1 =Calendar.getInstance();

            c.set(2019,10,10);
            c1.set(2019,10,15);
*/
        String c0 = "2018-08-03";
        String c = "2018-05-14";
        String c1 = "2018-05-15";
        inReservation = new Reservation(1, 2, 3, c0, c, c1);
        reservationDao.insert(inReservation);
        inReservation.checkIn(c,100,1,"ASDFASDF",2);
        reservationDao.insert(inReservation);
        inReservation.setCheckOut(c1);
        reservationDao.insert(inReservation);
        //outReservation = reservationDao.getCurrentReservation();
        //outReservation = reservationDao.getActiveReservation();
    }

    @After
    public void closeDB() {
        roomDB.close();
    }

    @Test
    public void id() throws Exception {
        assertThat(inReservation.getId(), equalTo(outReservation.getId()));
    }

    @Test
    public void roomTypeID() throws Exception {
        assertThat(inReservation.getRoomTypeID(), equalTo(outReservation.getRoomTypeID()));
    }

    @Test
    public void adults() throws Exception {

        assertThat(inReservation.getAdults(), equalTo(outReservation.getAdults()));
    }

    @Test
    public void startDate() throws Exception {
        assertThat(inReservation.getStartDate(), equalTo(outReservation.getStartDate()));
    }

    @Test
    public void endDate() throws Exception {
        assertThat(inReservation.getEndDate(), equalTo(outReservation.getEndDate()));
    }

    @Test
    public void bookDate() throws Exception {
        assertThat(inReservation.getBookDate(), equalTo(outReservation.getBookDate()));
    }


}
