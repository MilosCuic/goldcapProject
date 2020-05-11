package com.goldcap.util;

public class Constants {

    public static final String SIGN_UP_URLS = "/register/**";
    public static final String SIGN_IN_URL = "/login/**";

    //token
    public static final String SECRET = "19MozesDaGaDuckasPederu92";
    //this has to be Bearer this with space on the end
    public static final String TOKEN_PREFIX = "Bearer ";
    //also standard value
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION_TIME = 600_000_000; // 60k = 1min



    //roles
    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER";
    public static final String ROLE_SELLER = "ROLE_SELLER";
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";


    public static final String PVP = "PvP";
    public static final String PVE = "PvE";
    public static final String RP = "PvE";

    public static final String STATUS_WAITING = "WAITING";
    public static final String STATUS_SUBMITTED = "SUBMITTED";
    public static final String STATUS_DONE = "DONE";



}
