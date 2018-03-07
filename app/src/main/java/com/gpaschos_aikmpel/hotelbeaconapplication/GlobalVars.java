package com.gpaschos_aikmpel.hotelbeaconapplication;

/**
 * Created by gpaschos on 28/02/18.
 */

public class GlobalVars {

    private static final String serverProtocol = "http";
    private static final String serverHost = "192.168.1.101";
    private static final String serverPort = "80";

    private static final String serverAddress = serverProtocol +"://"+ serverHost +":"+ serverPort+"/";

    private static final String loginPage = "login.php";
    private static final String forgotPage = "forgot.php";
    private static final String registerPage = "register.php";
    private static final String titlesPage= "titles.php";
    private static final String forgotVerifyPage = "forgotverify.php";
    private static final String forgotNewPassPage = "forgotnewpass.php";

    public static final String loginUrl = serverAddress + loginPage;
    public static final String forgotUrl = serverAddress + forgotPage;
    public static final String registerUrl = serverAddress + registerPage;
    public static final String titlesUrl = serverAddress + titlesPage;
    public static final String forgotVerifyUrl = serverAddress + forgotVerifyPage;
    public static final String forgotNewPassUrl = serverAddress + forgotNewPassPage;
}