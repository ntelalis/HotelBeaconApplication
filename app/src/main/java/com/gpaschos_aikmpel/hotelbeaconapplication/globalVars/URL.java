package com.gpaschos_aikmpel.hotelbeaconapplication.globalVars;

public class URL {

    private static final String serverProtocol = "http";

    //HOME
    private static final String serverHost = "192.168.10.1";
//    private static final String serverPath = "";
//    private static final String serverPort = "80";
//    private static final String serverHost = "ntelalis.dedyn.io";
    private static final String serverPath = "";
    private static final String serverPort = "80";

    //WORK-Kate
    //private static final String serverHost = "192.168.102.96";
    //private static final String serverPath = "phpserver";
    //private static final String serverPort = "80";

    //WORK-Lenovo
    //private static final String serverHost = "192.168.102.97";
    //private static final String serverPath = "phpserver";
    //private static final String serverPort = "8079";

    //WORK-Dimitris
    //private static final String serverHost = "83.235.23.44";
    //private static final String serverPath = "phpserver";
    //private static final String serverPort = "18080";


    private static final String serverAddress = serverProtocol + "://" + serverHost + ":" + serverPort + "/" + serverPath + "/";

    private static final String registerPage = "register.php";
    private static final String loginPage = "login.php";
    private static final String forgotPage = "forgot.php";
    private static final String forgotVerifyPage = "forgotverify.php";
    private static final String forgotNewPassPage = "forgotnewpass.php";

    private static final String titlesPage = "titles.php";

    private static final String availabilityPage = "availability.php";
    private static final String bookPage = "book.php";
    private static final String roomServiceCategoriesPage = "roomservicecategories.php";
    private static final String roomServiceFoodPage = "roomservicefood.php";
    private static final String orderPage = "orderroomservice.php";
    private static final String reviewPage = "rating.php";
    private static final String loyaltyPointsPage = "loyaltypoints.php";
    private static final String checkInPage = "checkin.php";
    private static final String checkoutPage = "checkout.php";
    private static final String checkoutConfirmationPage = "checkoutConfirm.php";
    private static final String roomTypesPage = "roomtypes.php";
    private static final String deletePage = "deletereservations.php";
    private static final String doorUnlockPage = "doorunlock.php";
    private static final String countriesPage = "countries.php";
    private static final String reservationsPage = "reservations.php";
    private static final String beaconRegionsPage = "beaconregions.php";
    private static final String roomBeaconRegionsPage = "roombeaconregion.php";
    private static final String beaconRegionFeaturePage = "beaconregionfeature.php";
    private static final String generalOffersPage = "generaloffers.php";
    private static final String exclusiveOffersPage = "exclusiveoffers.php";
    private static final String offerCouponsPage = "offercoupons.php";
    private static final String offerBeaconRegionPage = "offerbeaconregion.php";

    public static final String loginUrl = serverAddress + loginPage;
    public static final String forgotUrl = serverAddress + forgotPage;
    public static final String registerUrl = serverAddress + registerPage;
    public static final String titlesUrl = serverAddress + titlesPage;
    public static final String forgotVerifyUrl = serverAddress + forgotVerifyPage;
    public static final String forgotNewPassUrl = serverAddress + forgotNewPassPage;
    public static final String availabilityUrl = serverAddress + availabilityPage;
    public static final String bookUrl = serverAddress + bookPage;
    public static final String roomServiceTimeCategoriesUrl = serverAddress + roomServiceCategoriesPage;
    public static final String roomServiceFoodUrl = serverAddress + roomServiceFoodPage;
    public static final String orderUrl = serverAddress + orderPage;
    public static final String reviewURL = serverAddress + reviewPage;
    public static final String loyaltyPointsURL = serverAddress + loyaltyPointsPage;
    public static final String checkInUrl = serverAddress + checkInPage;
    public static final String checkoutUrl = serverAddress + checkoutPage;
    public static final String checkoutConfirmationUrl = serverAddress + checkoutConfirmationPage;
    public static final String roomTypesUrl = serverAddress + roomTypesPage;
    public static final String deleteUrl = serverAddress + deletePage;
    public static final String doorUnlockUrl = serverAddress + doorUnlockPage;
    public static final String countriesUrl = serverAddress + countriesPage;
    public static final String reservationsUrl = serverAddress + reservationsPage;
    public static final String beaconRegionsUrl = serverAddress + beaconRegionsPage;
    public static final String roomBeaconRegionsUrl = serverAddress + roomBeaconRegionsPage;
    public static final String beaconRegionFeatureUrl = serverAddress + beaconRegionFeaturePage;
    public static final String generalOffersUrl = serverAddress + generalOffersPage;
    public static final String exclusiveOffersUrl = serverAddress + exclusiveOffersPage;
    public static final String offerCouponsUrl = serverAddress + offerCouponsPage;
    public static final String offerBeaconRegionUrl = serverAddress + offerBeaconRegionPage;
}