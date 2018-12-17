package com.gpaschos_aikmpel.hotelbeaconapplication.globalVars;

public class POST {

    //Sync anything
    public static final String syncId = "id";
    public static final String syncModified = "modified";

    //~~~~~~~~~~~~~~~~~~register.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String registerEmail = "email";
    public static final String registerPassword = "pass";
    public static final String registerFirstName = "firstName";
    public static final String registerLastName = "lastName";
    public static final String registerTitleID = "titleID";
    public static final String registerCountryID = "countryID";
    public static final String registerBirthDate = "birthDate";
    public static final String registerPhone = "phone";

    //~~~~~~~~~~~~~~~~~~titles.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String titlesArray = "titleArray";
    public static final String titleId = "id";
    public static final String titleTitle = "title";


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
    //SND&RCV
    public static final String loginModified = "modified";


    //~~~~~~~~~~~~~~~~~~forgot.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String forgotEmail = "email";

    //~~~~~~~~~~~~~~~~~~forgotverify.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String forgotVerifyEmail = "email";
    public static final String forgotVerifyVerification = "verCode";

    //~~~~~~~~~~~~~~~~~~forgotnewpass.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String forgotNewPassEmail = "email";
    public static final String forgotNewPassVerification = "verCode";
    public static final String forgotNewPassPassword = "pass";


    //~~~~~~~~~~~~~~~~~~checkin.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String checkInReservationID = "reservationID";
    //RCV
    public static final String checkInRoomNumber = "roomNumber";
    public static final String checkInRoomFloor = "roomFloor";
    public static final String checkInRoomPassword = "roomPassword";
    public static final String checkInDate = "checkInDate";
    public static final String checkInRoomBeaconRegionID = "id";
    public static final String checkInRoomBeaconRegionUniqueID = "uniqueID";
    public static final String checkInRoomBeaconRegionUUID = "uuid";
    public static final String checkInRoomBeaconRegionMajor = "major";
    public static final String checkInRoomBeaconRegionMinor = "minor";
    public static final String checkInRoomBeaconRegionBackground = "background";
    public static final String checkInRoomBeaconRegionModified = "modified";
    public static final String checkInRoomBeaconRegionArray = "roomBeaconRegionArray";


    //~~~~~~~~~~~~~~~~~~checkout.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String checkoutReservationID = "reservationID";
    //RCV
    public static final String checkoutTotalPrice = "totalPrice";
    public static final String checkoutChargeArray = "chargeArray";
    public static final String checkoutService = "service";
    public static final String checkoutServicePrice = "price";


    //~~~~~~~~~~~~~~~~~~checkoutConfirm.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String checkoutConfirmReservationID = "reservationID";
    //RCV
    public static final String checkoutCheckedOutDate = "checkedOutDate";

    //~~~~~~~~~~~~~~~~~~availability.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String availabilityArrivalDate = "arrivalDate";
    public static final String availabilityDepartureDate = "departureDate";
    public static final String availabilityAdults = "adults";
    public static final String availabilityChildren = "children";
    //RCV
    public static final String availabilityRoomTypeArray = "roomTypeArray";


    //~~~~~~~~~~~~~~~~~~book.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String bookRoomCustomerID = "customerID";
    public static final String bookRoomTypeID = "roomTypeID";
    public static final String bookRoomArrival = "arrival";
    public static final String bookRoomDeparture = "departure";
    public static final String bookRoomAdults = "adults";
    public static final String bookRoomChildren = "children";
    public static final String bookRoomFreeNights = "freeNights";
    public static final String bookRoomCashNights = "cashNights";
    public static final String bookRoomCreditCard = "ccNumber";
    public static final String bookRoomHoldersName = "ccName";
    public static final String bookRoomExpMonth = "ccMonth";
    public static final String bookRoomExpYear = "ccYear";
    public static final String bookRoomCVV = "ccCVV";
    public static final String bookRoomPhone = "phone";
    public static final String bookRoomAddress1 = "address1";
    public static final String bookRoomAddress2 = "address2";
    public static final String bookRoomCity = "city";
    public static final String bookRoomPostalCode = "postalCode";
    //RCV
    public static final String bookRoomReservationID = "reservationID";
    public static final String bookRoomBookedDate = "bookedDate";
    public static final String bookRoomModified = "modified";


    //~~~~~~~~~~~~~~~~~~roomservicecategories.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String roomServiceCategoryArray = "menuTimeArray";
    public static final String roomServiceCategoryID = "id";
    public static final String roomServiceCategoryName = "name";
    public static final String roomServiceCategoryFrom = "from";
    public static final String roomServiceCategoryTo = "to";

    //~~~~~~~~~~~~~~~~~~roomservicefood.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String roomServiceFoodMenuTime = "menuTime";
    //RCV
    public static final String roomServiceFoodCategoryArray = "categoryArray";
    public static final String roomServiceFoodID = "id";
    public static final String roomServiceFoodName = "name";
    public static final String roomServiceFoodDesc = "desc";
    public static final String roomServiceFoodPrice = "price";

    //~~~~~~~~~~~~~~~~~~reservations.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String reservationCustomerID = "customerID";
    public static final String reservationCheck = "check";
    //RCV
    public static final String reservationArray = "reservationArray";
    public static final String reservationReservationID = "reservationId";
    public static final String reservationRoomTypeID = "roomTypeID";
    public static final String reservationAdults = "adults";
    public static final String reservationChildren = "children";
    public static final String reservationBookedDate = "bookedDate";
    public static final String reservationArrival = "startDate";
    public static final String reservationDeparture = "endDate";
    public static final String reservationCheckIn = "checkIn";
    public static final String reservationCheckOut = "checkOut";
    public static final String reservationRoomNumber = "roomNumber";
    public static final String reservationRoomFloor = "roomFloor";
    public static final String reservationRating = "rating";
    public static final String reservationRatingComments = "ratingComments";
    public static final String reservationModified = "modified";

    //~~~~~~~~~~~~~~~~~~orderroomservice.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String roomServiceOrderReservationID = "reservationID";
    public static final String roomServiceOrderJson = "order";
    public static final String roomServiceOrderID = "id";
    public static final String roomServiceOrderQuantity = "quantity";
    public static final String roomServiceOrderComments = "comments";

    //~~~~~~~~~~~~~~~~~~rating.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String ratingReservationID = "reservationID";
    public static final String ratingRating = "rating";
    public static final String ratingRatingComments = "comments";

    //~~~~~~~~~~~~~~~~~~loyaltypoints.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String loyaltyProgramCustomerID = "customerID";
    //RCV
    public static final String loyaltyProgramPoints = "points";
    public static final String loyaltyProgramTierName = "tierName";
    public static final String loyaltyProgramTierPoints = "tierPoints";
    public static final String loyaltyProgramNextTierName = "nextTierName";
    public static final String loyaltyProgramNextTierPoints = "nextTierPoints";
    //SND&RCV
    public static final String loyaltyProgramModified = "modified";

    //~~~~~~~~~~~~~~~~~~roomtypes.php~~~~~~~~~~~~~~~~~~
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
    //Cash only
    public static final String roomTypeCashArray = "roomTypeCashArray";
    public static final String roomTypeCashID = "roomTypeID";
    public static final String roomTypeCashAdults = "adults";
    public static final String roomTypeCashChildren = "children";
    public static final String roomTypeCashCash = "cash";
    //Points only
    public static final String roomTypePointsArray = "roomTypePointsArray";
    public static final String roomTypePointsRoomTypeID = "roomTypeID";
    public static final String roomTypePointsAdults = "adults";
    public static final String roomTypePointsChildren = "children";
    public static final String roomTypePointsSpendingPoints = "spendingPoints";
    public static final String roomTypePointsGainingPoints = "gainingPoints";
    //Cash&Points
    public static final String roomTypeCashPointsArray = "roomTypeCashPointsArray";
    public static final String roomTypeCashPointsRoomTypeID = "roomTypeID";
    public static final String roomTypeCashPointsAdults = "adults";
    public static final String roomTypeCashPointsChildren = "children";
    public static final String roomTypeCashPointsCash = "cash";
    public static final String roomTypeCashPointsPoints = "points";

    //~~~~~~~~~~~~~~~~~~doorunlock.php~~~~~~~~~~~~~~~~~~
    //SND
    public static final String doorUnlockReservationID = "reservationID";
    public static final String doorUnlockRoomPassword = "roomPassword";

    //~~~~~~~~~~~~~~~~~~countries.php~~~~~~~~~~~~~~~~~~
    //RCV
    public static final String countryArray = "countryArray";
    public static final String countryID = "id";
    public static final String countryName = "name";
    //SND&RCV
    public static final String countryModified = "modified";

    //~~~~beaconregions.php~&~roombeaconregion.php~~~~~~~~~~~~~~~~~
    //SND beaconregions.php
    public static final String beaconRegionCheck = "check";
    //SND roombeaconregion.php
    public static final String roomBeaconRegionCheck = "check";
    public static final String roomBeaconRegionReservationID = "reservationID";
    //RCV
    public static final String beaconRegionArray = "beaconRegionArray";
    public static final String beaconRegionID = "id";
    public static final String beaconRegionUniqueID = "uniqueID";
    public static final String beaconRegionUUID = "uuid";
    public static final String beaconRegionMajor = "major";
    public static final String beaconRegionMinor = "minor";
    public static final String beaconRegionBackground = "background";
    public static final String beaconRegionModified = "modified";

    //~~~~~~~~~~~~~~~~~beaconregionfeature.php~~~~~~~~~~~~~~~~~
    //SND
    public static final String beaconRegionFeatureRegionArray = "beaconRegionArray";
    public static final String beaconRegionFeatureCheck = "check";
    //RCV
    public static final String beaconRegionFeatureArray = "beaconRegionFeatureArray";
    public static final String beaconRegionFeatureRegionID = "regionID";
    public static final String beaconRegionFeatureFeature = "feature";
    //SND&RCV
    public static final String beaconRegionFeatureID = "id";
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
    public static final String exclusiveOfferServiceID = "serviceID";
    public static final String exclusiveOfferPriceDiscount = "priceDiscount";
    public static final String exclusiveOfferTitle = "title";
    public static final String exclusiveOfferDescription = "description";
    public static final String exclusiveOfferDetails = "details";
    public static final String exclusiveOfferSpecial = "special";
    public static final String exclusiveOfferStartDate = "startDate";
    public static final String exclusiveOfferEndDate = "endDate";
    public static final String exclusiveOfferCode = "code";
    public static final String exclusiveOfferCodeUsed = "codeUsed";
    public static final String exclusiveOfferCodeCreated = "codeCreated";
    //SND&RCV
    public static final String exclusiveOfferID = "id";
    public static final String exclusiveOfferModified = "modified";


    //~~~~~~~~~~~~~~~~~~~offercoupons.php~~~~~~~~~~~~~~~~~~~~~~~
    //SND
    public static final String offerCouponsCustomerID = "customerID";
    public static final String offerCouponsOfferID = "offerID";
    //RCV
    public static final String offerCouponsCode = "code";
    public static final String offerCouponsCodeCreated = "codeCreated";

    //~~~~~~~~~~~~~~~~~~~offerbeaconregion.php~~~~~~~~~~~~~~~~~~~~~~~
    //SND
    public static final String offerBeaconRegionOfferIDArray = "offerArray";
    public static final String offerBeaconRegionCheck = "check";
    //RCV
    public static final String offerBeaconRegionArray = "beaconRegionOfferArray";
    public static final String offerBeaconRegionRegionID = "regionID";
    public static final String offerBeaconRegionOfferID = "offerID";
    //SND&RCV
    public static final String offerBeaconRegionID = "id";
    public static final String offerBeaconRegionModified = "modified";
}