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
    private static final String checkinPage = "checkin.php";



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
    public static final String checkinUrl = serverAddress + checkinPage;
}