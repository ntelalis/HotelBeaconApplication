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
    //SND
    public static final String loginEmail = "email";
    public static final String loginPassword = "pass";
    //RCV
    public static final String loginCustomerID = "customerID";
    public static final String loginFirstName= "firstName";
    public static final String loginLastName= "lastName";
    public static final String loginTitle= "title";
    public static final String loginIsOldCustomer= "isOldCustomer";
    public static final String loginIsCheckedIn= "isCheckedIn";
    public static final String loginIsCheckedOut= "isCheckedOut";



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
    public static final String checkinDate = "date";
    public static final String checkinRoomFloor = "floor";
    public static final String checkinRoomPassword = "roomPassword";
    public static final String checkinBeaconID = "beaconID";
    public static final String checkinBeaconUUID = "beaconUUID";
    public static final String checkinBeaconMajor = "beaconMajor";
    public static final String checkinBeaconMinor = "beaconMinor";



    //~~~~~~~~~~~~~~~checkout.php~~~~~~~~~~~~~~~~~~~~~~~~
    //SND
    public static final String checkoutReservationID = "reservationID";
    //RCV
    public static final String checkoutTotalPrice= "totalPrice";
    public static final String checkoutChargeDetails= "chargeDetails";
    public static final String checkoutService= "service";
    public static final String checkoutServicePrice= "price";



    //~~~~~~~~~~~~~~~checkoutConfirm.php~~~~~~~~~~~~~~~~
    //SND
    public static final String checkoutConfirmReservationID = "reservationID";
    //RCV
    public static final String checkoutConfirmDate= "date";



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
    public static String bookRoomBookedDate = "bookedDate";


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


    //~~~~~~loyaltypoints.php~~~~~~~
    //SND
    public static final String loyaltyPointsCustomerID = "customerID";
    //RCV
    public static final String loyaltyProgramFirstName = "firstName";
    public static final String loyaltyProgramLastName = "lastName";
    public static final String loyaltyProgramPoints = "points";
    public static final String loyaltyProgramTierName = "tierName";
    public static final String loyaltyProgramTierPoints = "tierPoints";
    public static final String loyaltyProgramNextTierName = "nextTierName";
    public static final String loyaltyProgramNextTierPoints = "nextTierPoints";
    public static final String loyaltyProgramBenefits = "tierBenefits";


    //~~~~~~~~~Welcome.php~~~~~~~~~~~
    //SND
    public static final String welcomeNotificationCustomerID = "customerID";
    //RCV
    public static final String welcomeNotificationHasStayed= "hasStayed";

    //~~~~~~~~~.php~~~~~~~~~~~
    //SND
    public static final String temp  = "customerID";
    //RCV
    public static final String temp2 = "hasCheckedOut";

    //~~~~~~~~~~~roomtype.php~~~~~~~~
    //RCV
    public static final String roomTypeArray = "roomTypeArray";
    public static final String roomTypeID = "id";
    public static final String roomTypeName = "name";
    public static final String roomTypeCapacity = "capacity";
    public static final String roomTypePrice = "price";
    public static final String roomTypeImage = "image";
    public static final String roomTypeDescription = "description";
    //SND
    public static final String roomTypeCheck = "check";
    //BOTH
    public static final String roomTypeModified = "modified";
}