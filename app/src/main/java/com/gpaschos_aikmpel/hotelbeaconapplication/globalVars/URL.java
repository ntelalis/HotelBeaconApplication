package com.gpaschos_aikmpel.hotelbeaconapplication.globalVars;

public class URL {

    private static final String serverProtocol = "http";

    //private static final String serverHost = "ntelalis.dyndns-home.com";
    private static final String serverHost = "192.168.1.101";

    //private static final String serverPort = "8642";
    private static final String serverPort = "80";

    private static final String serverAddress = serverProtocol + "://" + serverHost + ":" + serverPort + "/";

    private static final String loginPage = "login.php";
    private static final String forgotPage = "forgot.php";
    private static final String registerPage = "register.php";
    private static final String titlesPage = "titles.php";
    private static final String forgotVerifyPage = "forgotverify.php";
    private static final String forgotNewPassPage = "forgotnewpass.php";
    private static final String personsPage = "persons.php";
    private static final String availabilityPage = "availability.php";
    private static final String bookPage = "book.php";
    private static final String roomServiceCategoriesPage = "roomservicecategories.php";
    private static final String roomServiceFoodPage = "roomservicefood.php";
    private static final String upcomingreservationsPage = "upcomingreservations.php";
    private static final String orderPage = "orderroomservice.php";
    private static final String reviewPage = "rating.php";
    private static final String checkReviewPage = "ratingcheck.php";
    private static final String loyaltyPointsPage = "loyaltypoints.php";
    private static final String checkinPage = "checkin.php";
    private static final String checkoutPage = "checkout.php";
    private static final String checkoutConfirmationPage = "checkoutConfirm.php";
    private static final String welcomeNotificationPage = "welcomenotification.php";
    private static final String checkinNotificationPage = "checkinnotification.php";
    private static final String roomTypesPage = "roomtypes.php";
    private static final String deletePage = "delete.php";
    private static final String doorUnlockPage = "doorunlock.php";
    private static final String countriesPage = "countries.php";
    private static final String totalPointsPage = "totalpoints.php";
    private static final String currenciesPage = "currency.php";

    public static final String loginUrl = serverAddress + loginPage;
    public static final String forgotUrl = serverAddress + forgotPage;
    public static final String registerUrl = serverAddress + registerPage;
    public static final String titlesUrl = serverAddress + titlesPage;
    public static final String forgotVerifyUrl = serverAddress + forgotVerifyPage;
    public static final String forgotNewPassUrl = serverAddress + forgotNewPassPage;
    public static final String personsUrl = serverAddress + personsPage;
    public static final String availabilityUrl = serverAddress + availabilityPage;
    public static final String bookUrl = serverAddress + bookPage;
    public static final String roomServiceTimeCategoriesUrl = serverAddress + roomServiceCategoriesPage;
    public static final String roomServiceFoodUrl = serverAddress + roomServiceFoodPage;
    public static final String upcomingreservationsUrl = serverAddress + upcomingreservationsPage;
    public static final String orderUrl = serverAddress + orderPage;
    public static final String reviewURL = serverAddress + reviewPage;
    public static final String checkReviewURL = serverAddress + checkReviewPage;
    public static final String loyaltyPointsURL = serverAddress + loyaltyPointsPage;
    public static final String checkinUrl = serverAddress + checkinPage;
    public static final String checkoutUrl = serverAddress + checkoutPage;
    public static final String checkoutConfirmationUrl = serverAddress + checkoutConfirmationPage;
    public static final String welcomeNotificationUrl = serverAddress + welcomeNotificationPage;
    public static final String checkinNotificationUrl = serverAddress + checkinNotificationPage;
    public static final String roomTypesUrl = serverAddress + roomTypesPage;
    public static final String deleteUrl = serverAddress + deletePage;
    public static final String doorUnlockUrl = serverAddress + doorUnlockPage;
    public static final String countriesUrl = serverAddress + countriesPage;
    public static final String totalPointsUrl = serverAddress + totalPointsPage;
    public static final String currenciesUrl = serverAddress + currenciesPage;
}