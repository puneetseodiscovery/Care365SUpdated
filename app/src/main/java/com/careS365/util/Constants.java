package com.careS365.util;

import com.careS365.responseModel.GetCirclesResponseModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Constants {
    public static String IMAGE_URL="http://api.amrdev.site/care365s/apis/uploads/";
    public static String SELECTED_CIRCLE = "";
    public static HashMap<String,String> circlesHashmap = new HashMap<>();
    public static HashMap<String,String> selectedcircleMembersHashmap = new HashMap<>();
    //public static ArrayList<String> circlesArrayList = new ArrayList<>();
    public static ArrayList<String> circlesArrayList = new ArrayList<>();
    public static GetCirclesResponseModel getCirclesResponseModel = new GetCirclesResponseModel();
    public static String inviteCode,invitedBy,invitedCircle;
    public static String movedThroughLink="0";
    public static HashMap<String, HashMap<String,String>> circlesMap=new HashMap<String, HashMap<String, String>>();
    public static Double currentUserLat;
    public static Double currentUserLong;
}
