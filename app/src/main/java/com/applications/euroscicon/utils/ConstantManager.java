package com.applications.euroscicon.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class ConstantManager {

    public static final String CHECK_BOX_CHECKED_TRUE = "YES";
    public static final String CHECK_BOX_CHECKED_FALSE = "NO";

    public static   ArrayList<ArrayList<HashMap<String, String>>> childItems = new ArrayList<>();
    public static   ArrayList<ArrayList<HashMap<String, String>>> childItems1 = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> parentItems = new ArrayList<>();


    public static class Parameter {
        public static final String IS_CHECKED = "is_checked";
        public static final String CITY_NAME = "city_name";
        public static final String COUNTRY_NAME = "country_name";
        public static final String COUNTRY_DESCRIPTION = "country_description";
        public static final String COUNTRY_ID = "country_id";
        public static final String CITY_ID = "city_id";
    }
}
