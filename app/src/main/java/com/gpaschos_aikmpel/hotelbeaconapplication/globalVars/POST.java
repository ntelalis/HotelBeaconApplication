package com.gpaschos_aikmpel.hotelbeaconapplication.globalVars;

public class POST {

    //~~~~~~~~~~~~~~~~~~register.php~~~~~~~~~~~~~~~~~~~~~
    public static final String registerTitle = "title";
    public static final String registerFirstName = "firstName";
    public static final String registerLastName = "lastName";
    public static final String registerEmail = "email";
    public static final String registerPassword = "pass";

    //titles.php
    public static final String titlesTitleList = "titleList";

    //~~~~~~~~~~~~~~~~~~login.php~~~~~~~~~~~~~~~~~~~~~~~
    public static final String loginEmail = "email";
    public static final String loginPassword = "pass";
    public static final String loginCustomerID = "customerID";

    //~~~~~~~~~~~~~~~~~forgot.php~~~~~~~~~~~~~~~~
    public static final String forgotEmail = "email";

    //forgotverify.php
    public static final String forgotVerifyEmail = "email";
    public static final String forgotVerifyVerification = "verification";

    //forgotnewpass.php
    public static String forgotNewPassEmail = "email";
    public static String forgotNewPassPassword = "pass";
    public static String forgotNewPassVerification = "code";


    //~~~~~~~~~~~~~~~checkin.php~~~~~~~~~~~~~~~~~~~~~~~~
    //SND
    public static final String checkinReservationID = "reservationID";
    //RCV
    public static final String checkinRoom = "room";


    //~~~~~~~~~~~~~~~checkin.php~~~~~~~~~~~~~~~~~~~~~~~~
    //SND
    public static final String checkoutReservationID = "reservationID";



    //~~~~~~~~~~~~~~~~~~availability.php~~~~~~~~~~~~~~~~~~~~
    //SND
    public static String availabilityArrivalDate = "arrivalDate";
    public static String availabilityDepartureDate = "departureDate";
    public static String availabilityPeople = "persons";

    //RCV
    public static String availabilityResultsArray = "results";
    public static String availabilityRoomTypeID = "roomTypeID";
    public static String availabilityRoomTitle = "title";
    public static String availabilityRoomDescription = "description";
    public static String availabilityRoomPrice = "price";
    public static String availabilityRoomImage = "img";

    //persons.php
    public static String personsMaxCapacity = "capacity";

    //~~~~~~~~~~~~~~~~~~~book.php~~~~~~~~~~~~~~~~~~~~~~
    //SND
    public static String bookRoomTypeID = "roomTypeID";
    public static String bookRoomArrival = "arrival";
    public static String bookRoomDeparture = "departure";
    public static String bookRoomPeople = "persons";
    public static String bookRoomCustomerID = "customerID";

    //RCV
    public static String bookRoomReservationID = "reservationID";


    //~~~~~~~~~~~~~roomservicecategories.php~~~~~~~~~~~~~~~~~~~

    //SND
    public static String roomServiceTimeType = "timeType";

    //RCV
    public static String roomServiceCategoriesID = "id";
    public static String roomServiceCategoriesName = "name";
    public static String roomServiceCategoriesFrom = "from";
    public static String roomServiceCategoriesTo = "to";
    public static String roomServiceTimeCategory = "timeCategory";
    public static String roomServiceTypeCategory = "typeCategory";
    public static String roomServiceFoodID = "id";
    public static String roomServiceFoodName = "name";
    public static String roomServiceFoodDesc = "desc";
    public static String roomServiceFoodPrice = "price";

    //~~~~~~~~~~~~~upcomingreservations.php~~~~~~~~~~~~~
    //SND
    public static final String upcomingreservationsCustomerID = "customerID";
    //RCV
    public static final String upcomingreservationsResponseArray = "reservations";
    public static final String upcomingreservationsReservationID = "reservationID";
    public static final String upcomingreservationsAdults = "adults";
    public static final String upcomingreservationsArrival = "arrivalDate";
    public static final String upcomingreservationsDeparture = "departureDate";
    public static final String upcomingreservationsRoomTitle = "roomType";
    public static final String upcomingreservationsEligibleForCheckinCheckout= "statusCode";
    public static final String upcomingreservationsCheckedinRoom= "roomNumber";

    //~~~~~~~~~~~orderroomservice.php~~~~~~~~~~~~~
    //SND
    public static final String roomServiceOrderReservationID = "reservationID";
    public static final String roomServiceOrderArray = "order";
    public static final String roomServiceOrderID = "id";
    public static final String roomServiceOrderPrice = "price";
    public static final String roomServiceOrderQuantity = "quantity";
    public static final String roomServiceOrderJson = "order";
    public static final String roomServiceOrderComments = "comment";

    //RCV

    //~~~~~~~~~rating.php~~~~~~~~~~~~`
    //SND
    public static final String reviewRating = "rating";
    public static final String reviewRatingComments = "comments";
    public static final String reviewReservationID = "reservationID";


    //~~~~~~checkrating.php~~~~~~~~~~
    //SND
    public static final String reviewCheckReservationID = "reservationID";
    //RCV
    public static final String reviewCheckExists = "exists";
    public static final String reviewCheckRating = "rating";
    public static final String reviewCheckComments = "comments";


    //~~~~~~loyaltypoints.php~~~~~~~`
    //SND
    public static final String loyaltyPointsCustomerID = "customerID";
}