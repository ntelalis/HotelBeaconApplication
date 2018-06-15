package com.gpaschos_aikmpel.hotelbeaconapplication.globalVars;

public class POST {

    //~~~~~~~~~~~~~~~~~~register.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String registerTitleID = "titleID";
    public static final String registerFirstName = "firstName";
    public static final String registerLastName = "lastName";
    public static final String registerEmail = "email";
    public static final String registerPassword = "pass";
    public static final String registerBirthDate = "birthDate";
    public static final String registerCountryID = "countryID";
    public static final String registerPhone = "phone";
    //RCV
    public static final String registerCustomerID = "customerID";

    //~~~~~~~~~~~~~~~~~~titles.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String titlesTitleList = "titleList";
    public static final String titlesID = "id";
    public static final String titlesTitle = "title";


    //~~~~~~~~~~~~~~~~~~login.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String loginEmail = "email";
    public static final String loginPassword = "pass";
    //RCV
    public static final String loginCustomerID = "customerID";
    public static final String loginFirstName = "firstName";
    public static final String loginLastName = "lastName";
    public static final String loginTitleID = "titleID";
    public static final String loginCountryID = "countryID";
    public static final String loginBirthDate = "birthDate";
    public static final String loginIsOldCustomer = "isOldCustomer";

    public static final String loginIsCheckedIn = "isCheckedIn";
    public static final String loginIsCheckedOut = "isCheckedOut";


    //~~~~~~~~~~~~~~~~~~forgot.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String forgotEmail = "email";

    //~~~~~~~~~~~~~~~~~~forgotverify.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String forgotVerifyEmail = "email";
    public static final String forgotVerifyVerification = "verification";

    //~~~~~~~~~~~~~~~~~~forgotnewpass.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String forgotNewPassEmail = "email";
    public static final String forgotNewPassPassword = "pass";
    public static final String forgotNewPassVerification = "code";


    //~~~~~~~~~~~~~~~~~~checkin.php~~~~~~~~~~~~~~~~~~
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


    //~~~~~~~~~~~~~~~~~~checkout.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String checkoutReservationID = "reservationID";
    //RCV
    public static final String checkoutTotalPrice = "totalPrice";
    public static final String checkoutChargeDetails = "chargeDetails";
    public static final String checkoutService = "service";
    public static final String checkoutServicePrice = "price";


    //~~~~~~~~~~~~~~~~~~checkoutConfirm.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String checkoutConfirmReservationID = "reservationID";
    //RCV
    public static final String checkoutConfirmDate = "date";


    //~~~~~~~~~~~~~~~~~~availability.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String availabilityArrivalDate = "arrivalDate";
    public static final String availabilityDepartureDate = "departureDate";
    public static final String availabilityPeople = "persons";
    //RCV
    public static final String availabilityResultsArray = "results";
    public static final String availabilityRoomTypeID = "roomTypeID";
    public static final String availabilityRoomTitle = "title";
    public static final String availabilityRoomDescription = "description";
    public static final String availabilityRoomPrice = "price";
    public static final String availabilityRoomImage = "img";

    //~~~~~~~~~~~~~~~~~~persons.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String personsMaxCapacity = "capacity";

    //~~~~~~~~~~~~~~~~~~book.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String bookRoomTypeID = "roomTypeID";
    public static final String bookRoomArrival = "arrival";
    public static final String bookRoomDeparture = "departure";
    public static final String bookRoomPeople = "persons";
    public static final String bookRoomFreeNights = "freeNights";
    public static final String bookRoomCashNights = "cashNights";
    public static final String bookRoomCustomerID = "customerID";
    public static final String bookRoomCreditCard = "ccNumber";
    public static final String bookRoomHoldersName = "ccName";
    public static final String bookRoomExpMonth = "ccMonth";
    public static final String bookRoomExpYear = "ccYear";
    public static final String bookRoomCVV = "ccCVV";
    //RCV
    public static final String bookRoomReservationID = "reservationID";
    public static final String bookRoomBookedDate = "bookedDate";


    //~~~~~~~~~~~~~~~~~~roomservicecategories.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String roomServiceTimeType = "timeType";
    //RCV
    public static final String roomServiceCategoriesID = "id";
    public static final String roomServiceCategoriesName = "name";
    public static final String roomServiceCategoriesFrom = "from";
    public static final String roomServiceCategoriesTo = "to";
    public static final String roomServiceTimeCategory = "timeCategory";
    public static final String roomServiceTypeCategory = "typeCategory";
    public static final String roomServiceFoodID = "id";
    public static final String roomServiceFoodName = "name";
    public static final String roomServiceFoodDesc = "desc";
    public static final String roomServiceFoodPrice = "price";

    //~~~~~~~~~~~~~~~~~~upcomingreservations.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String upcomingreservationsCustomerID = "customerID";
    //RCV
    public static final String upcomingreservationsResponseArray = "reservations";
    public static final String upcomingreservationsReservationID = "reservationID";
    public static final String upcomingreservationsAdults = "adults";
    public static final String upcomingreservationsArrival = "arrivalDate";
    public static final String upcomingreservationsDeparture = "departureDate";
    public static final String upcomingreservationsRoomTitle = "roomType";
    public static final String upcomingreservationsEligibleForCheckinCheckout = "statusCode";
    public static final String upcomingreservationsCheckedinRoom = "roomNumber";

    //~~~~~~~~~~~~~~~~~~orderroomservice.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String roomServiceOrderReservationID = "reservationID";
    public static final String roomServiceOrderArray = "order";
    public static final String roomServiceOrderID = "id";
    public static final String roomServiceOrderPrice = "price";
    public static final String roomServiceOrderQuantity = "quantity";
    public static final String roomServiceOrderJson = "order";
    public static final String roomServiceOrderComments = "comment";

    //~~~~~~~~~~~~~~~~~~rating.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String reviewRating = "rating";
    public static final String reviewRatingComments = "comments";
    public static final String reviewReservationID = "reservationID";


    //~~~~~~~~~~~~~~~~~~checkrating.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String reviewCheckReservationID = "reservationID";
    //RCV
    public static final String reviewCheckExists = "exists";
    public static final String reviewCheckRating = "rating";
    public static final String reviewCheckComments = "comments";


    //~~~~~~~~~~~~~~~~~~loyaltypoints.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String loyaltyPointsCustomerID = "customerID";
    //RCV
    public static final String loyaltyProgramPoints = "points";
    public static final String loyaltyProgramTierName = "tierName";
    public static final String loyaltyProgramTierPoints = "tierPoints";
    public static final String loyaltyProgramNextTierName = "nextTierName";
    public static final String loyaltyProgramNextTierPoints = "nextTierPoints";
    public static final String loyaltyProgramBenefits = "tierBenefits";

    //~~~~~~~~~~~~~~~~~~roomtype.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String roomTypeCheck = "check";
    //RCV
    public static final String roomTypeArray = "roomTypeArray";
    public static final String roomTypeID = "id";
    public static final String roomTypeName = "name";
    public static final String roomTypeCapacity = "capacity";
    public static final String roomTypeAdults = "adults";
    public static final String roomTypeChildrenSupported = "childrenSupported";
    public static final String roomTypeImage = "image";
    public static final String roomTypeDescription = "description";
    public static final String roomTypeModified = "modified";

    //~~~~~~~~~~~~~~~~~~doorunlock.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String doorUnlockReservationID = "reservationID";
    public static final String doorUnlockRoomPassword = "roomPassword";

    //~~~~~~~~~~~~~~~~~~countries.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String countryArray = "countryArray";
    public static final String countryID = "id";
    public static final String countryName = "name";

    //~~~~~~~~~~~~~~~~~~roomtypecash.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String roomTypeCashArray = "roomTypeCashArray";
    public static final String roomTypeCashID = "roomTypeID";
    public static final String roomTypeCashAdults = "adults";
    public static final String roomTypeCashChildren = "children";
    public static final String roomTypeCashCurrencyID = "currencyID";
    public static final String roomTypeCashCash = "cash";

    //~~~~~~~~~~~~~~~~~~roomtypepoints.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String roomTypePointsArray = "roomTypePointsArray";
    public static final String roomTypePointsRoomTypeID = "roomTypeID";
    public static final String roomTypePointsAdults = "adults";
    public static final String roomTypePointsChildren = "children";
    public static final String roomTypePointsSpendingPoints = "spendingPoints";
    public static final String roomTypePointsGainingPoints = "gainingPoints";

    //~~~~~~~~~~~~~~~~~~roomtypecashpoints.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String roomTypeCashPointsArray = "roomTypeCashPointsArray";
    public static final String roomTypeCashPointsRoomTypeID = "roomTypeID";
    public static final String roomTypeCashPointsAdults = "adults";
    public static final String roomTypeCashPointsChildren = "children";
    public static final String roomTypeCashPointsCurrencyID = "currencyID";
    public static final String roomTypeCashPointsCash = "cash";
    public static final String roomTypeCashPointsPoints = "points";

    //~~~~~~~~~~~~~~~~~~currency.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String currencyArray = "currencyArray";
    public static final String currencyID = "id";
    public static final String currencyName = "name";
    public static final String currencyCode = "code";
    public static final String currencySymbol = "symbol";


    //~~~~~~~~~~~~~~~~~~totalpoints.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String totalPoints = "points";
}