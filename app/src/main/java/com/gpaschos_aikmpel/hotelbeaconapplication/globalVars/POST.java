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
    public static final String registerModified = "modified";

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
    public static final String loginTitle = "title";
    public static final String loginFirstName = "firstName";
    public static final String loginLastName = "lastName";
    public static final String loginBirthDate = "birthDate";
    public static final String loginCountry = "country";
    public static final String loginPhone = "phone";
    public static final String loginAddress1 = "address1";
    public static final String loginAddress2 = "address2";
    public static final String loginCity = "city";
    public static final String loginPostalCode = "postalCode";
    public static final String loginOldCustomer = "oldCustomer";
    public static final String loginModified = "modified";


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
    public static final String checkInReservationID = "reservationID";
    public static final String reservationCheck = "check";
    //RCV
    public static final String checkInRoomNumber = "roomNumber";
    public static final String checkInDate = "checkInDate";
    public static final String checkInRoomFloor = "roomFloor";
    public static final String checkInRoomPassword = "roomPassword";
    public static final String checkInModified = "modified";
    //beaconRegions
    public static final String checkInRoomBeaconRegionID = "id";
    public static final String checkInRoomBeaconRegionUUID = "uuid";
    public static final String checkInRoomBeaconRegionUniqueID = "uniqueID";
    public static final String checkInRoomBeaconRegionMajor = "major";
    public static final String checkInRoomBeaconRegionMinor = "minor";
    public static final String checkInRoomBeaconRegionExclusive = "exclusive";
    public static final String checkInRoomBeaconRegionBackground = "background";
    public static final String checkInRoomBeaconRegionRegionType = "regionType";
    public static final String checkInRoomBeaconRegionModified = "modified";
    public static final String checkInRoomBeaconRegionArray = "roomBeaconRegionArray";


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
    public static final String checkoutConfirmModified = "modified";

    //~~~~~~~~~~~~~~~~~~availability.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String availabilityArrivalDate = "arrivalDate";
    public static final String availabilityDepartureDate = "departureDate";
    public static final String availabilityAdults = "adults";
    public static final String availabilityChildren = "children";
    //RCV
    public static final String availabilityRoomTypeArray = "roomTypeArray";
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
    public static final String bookRoomAdults = "adults";
    public static final String bookRoomChildren = "children";
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
    public static final String bookRoomModified = "modified";


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

    //~~~~~~~~~~~~~~~~~~reservation.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String reservationCustomerID = "customerID";
    //RCV
    public static final String reservationArray = "upcomingReservationsArray";
    public static final String reservationReservationID = "reservationID";
    public static final String reservationRoomTypeID = "roomTypeID";
    public static final String reservationAdults = "adults";
    public static final String reservationChildren = "children";
    public static final String reservationBookedDate = "bookedDate";
    public static final String reservationArrival = "startDate";
    public static final String reservationDeparture = "endDate";
    public static final String reservationCheckIn = "checkIn";
    public static final String reservationCheckOut = "checkOut";
    public static final String reservationRoomBeaconID = "roomBeaconRegionID";
    public static final String reservationRoomNumber = "roomNumber";
    public static final String reservationRoomFloor = "roomFloor";
    public static final String reservationReviewed = "reviewed";
    public static final String reservationModified = "modified";

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
    //SND&RCV
    public static final String loyaltyProgramCustomerID = "customerID";
    //RCV
    public static final String loyaltyProgramPoints = "points";
    public static final String loyaltyProgramTierName = "tierName";
    public static final String loyaltyProgramTierPoints = "tierPoints";
    public static final String loyaltyProgramNextTierName = "nextTierName";
    public static final String loyaltyProgramNextTierPoints = "nextTierPoints";
    public static final String loyaltyProgramModified = "modified";

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


    //Sync anything
    public static final String syncId = "id";
    public static final String syncModified = "modified";

    //~~~~~~~~~~~~~~~~~beaconRegion.php~~~~~~~~~~~~~~~~~
    //SND
    public static final String beaconRegionCheck = "check";

    //RCV
    public static final String beaconRegionID = "id";
    public static final String beaconRegionUUID = "uuid";
    public static final String beaconRegionUniqueID = "uniqueID";
    public static final String beaconRegionMajor = "major";
    public static final String beaconRegionMinor = "minor";
    public static final String beaconRegionExclusive = "exclusive";
    public static final String beaconRegionBackground = "background";
    public static final String beaconRegionRegionType = "regionType";
    public static final String beaconRegionModified = "modified";
    public static final String beaconRegionArray = "beaconRegionArray";


    //~~~~~~~~~~~~~~~~~roombeaconregion.php~~~~~~~~~~~~~~~~~
    //SND
    public static final String roomBeaconRegionReservationID = "reservationID";


    //~~~~~~~~~~~~~~~~~beaconregionfeature.php~~~~~~~~~~~~~~~~~
    //SND
    public static final String beaconRegionIDFeatureArray = "beaconRegionIDJsonArray";
    public static final String beaconRegionFeatureCheck = "check";

    //RCV
    public static final String beaconRegionFeatureArray = "beaconRegionFeatureArray";
    public static final String beaconRegionFeatureID = "id";
    public static final String beaconRegionFeatureRegionID = "regionID";
    public static final String beaconRegionFeatureFeature = "feature";
    public static final String beaconRegionFeatureModified = "modified";


    //~~~~~~~~~~~~~~~~~~generaloffers.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String generalOfferCheck = "check";

    //RCV
    public static final String generalOfferArray = "generalOfferArray";
    public static final String generalOfferID = "id";
    public static final String generalOfferPriceDiscount = "priceDiscount";
    public static final String generalOfferTitle = "title";
    public static final String generalOfferDescription = "description";
    public static final String generalOfferDetails = "details";
    public static final String generalOfferStartDate = "startDate";
    public static final String generalOfferEndDate = "endDate";
    public static final String generalOfferModified = "modified";


    //~~~~~~~~~~~~~~~~~~exclusiveoffers.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String exclusiveOfferCustomerID = "customerID";
    public static final String exclusiveOfferCheck = "check";

    //RCV
    public static final String exclusiveOfferArray = "exclusiveOfferArray";
    public static final String exclusiveOfferID = "id";
    public static final String exclusiveOfferServiceID = "serviceID";
    public static final String exclusiveOfferPrice = "price";
    public static final String exclusiveOfferDiscount = "discount";
    public static final String exclusiveOfferPriceDiscount = "priceDiscount";
    public static final String exclusiveOfferTitle = "title";
    public static final String exclusiveOfferDescription = "description";
    public static final String exclusiveOfferDetails = "details";
    public static final String exclusiveOfferSpecial = "special";
    public static final String exclusiveOfferStartDate = "startDate";
    public static final String exclusiveOfferEndDate = "endDate";
    public static final String exclusiveOfferModified = "modified";
    public static final String exclusiveOfferCode = "code";
    public static final String exclusiveOfferCodeUsed = "codeUsed";
    public static final String exclusiveOfferCodeCreated = "codeCreated";


    //~~~~~~~~~~~~~~~~~~~offercoupons.php~~~~~~~~~~~~~~~~~~~~~~~
    //SND
    public static final String offerCouponsOfferID = "offerID";
    public static final String offerCouponsCustomerID = "customerID";
    //RCV
    public static final String offerCouponsCode = "code";
    public static final String offerCouponsCodeCreated = "codeCreated";
    public static final String offerCouponsCodeUsed = "codeUsed";

    //~~~~~~~~~~~~~~~~~~~offerbeaconregion.php~~~~~~~~~~~~~~~~~~~~~~~
    //SND
    public static final String offerBeaconRegionOfferIDArray = "offerIDJsonArray";
    public static final String offerBeaconRegionCheck = "check";
    //RCV
    public static final String offerBeaconRegionArray = "offerBeaconRegionArray";
    public static final String offerBeaconRegionID = "id";
    public static final String offerBeaconRegionRegionID = "regionID";
    public static final String offerBeaconRegionOfferID = "offerID";
    public static final String offerBeaconRegionModified = "modified";

    public static final String bookRoomPhone = "phone";
    public static final String bookRoomAddress1 = "address1";
    public static final String bookRoomAddress2 = "address2";
    public static final String bookRoomCity = "city";
    public static final String bookRoomPostalCode = "postalCode";


}