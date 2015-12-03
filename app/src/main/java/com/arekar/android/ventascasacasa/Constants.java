package com.arekar.android.ventascasacasa;

/**
 * Created by yknx4 on 16/10/2015.
 */
public abstract class Constants {
    public static String APP_PREFERENCES = "APP_PREFERENES";
    public abstract class Preferences{
        public static final String USER_ID = "CURRENT_USER_ID";
        public static final String TOKEN = "CURRENT_TOKEN";
    }

    public static String STATIC_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1NjFhZTk5MTRjZmRjYzA0MjBlODI1MmEiLCJuYW1lIjoiSm9yZ2UgQWxlamFuZHJvIEZpZ3Vlcm9hIFDDqXJleiIsInVzZXIiOiJ5a254NCIsImltYWdlIjoiaHR0cDovL2dyYXBoLmZhY2Vib2suY29tL3YyLjUvMTI1ODAwMzA3NC9waWN0dXJlP3R5cGU9bGFyZ2UiLCJlbWFpbCI6Inlrbng0QGxpdmUuY29tLm14IiwiY3JlYXRlZCI6MTQ0Mjc3ODk0MzYzNywic2VlZCI6NDU5OTMzNTc4LCJpYXQiOjE0NDU4MzM3ODh9.5VQO99fxXCPmlGN7bYv4B8WPyJgkFbYPDbkrKpgoWvQ";

    public static Boolean LOGGING = true;
    /*
          Your imgur client id. You need this to upload to imgur.

          More here: https://api.imgur.com/
         */
    public static final String MY_IMGUR_CLIENT_ID = "d8c1ea518431d6a";
    public static final String MY_IMGUR_CLIENT_SECRET = "a62cc7e7e3e69e256479603dbc7ba76ad161945e";

    /*
      Redirect URL for android.
     */
    public static final String MY_IMGUR_REDIRECT_URL = "http://android";

    public static final String GOOGLE_HTTP_ID = "AIzaSyC-e3Dhgj_33CpGV0p5kIUjORCrd93F21g";

    /*
      Client Auth
     */
    public static String getClientAuth() {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }


    public abstract static class Connections{
        public static String API_URL = "http://sales-yknx4.rhcloud.com";
        public static String PATH_SALES = "/sale";
        public static String PATH_PRODUCTS = "/product";
        public static String PATH_CLIENTS = "/client";
        public static String PATH_PAYMENTS = "/payment";
        public static String PATH_USER = "/user";

        public static String DETAILS_URL="http://yknx4.github.io/sales/?client_id=";

    }
}
