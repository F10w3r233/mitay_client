package com.flower.mitayclient.util;

public class PlayerDataHandler
{
    public static int ping;
    public static String entitlement;

    public static String leftHasMending;
    public static String rightHasMending;

    public static String leftOnMending;
    public static String rightOnMending;

    public static String[] playerNames;

    public static void parsePlayerStatusInfo(int ping, String entitlement)
    {
        PlayerDataHandler.ping = ping;
        PlayerDataHandler.entitlement = entitlement;
    }


    public static void parseToolInfo(String flag)
    {
        switch (flag)
        {
            case "left_true" -> leftHasMending = "true";
            case "left_false" -> leftHasMending = "false";


            case "right_true" -> rightHasMending = "true";
            case "right_false" -> rightHasMending = "false";
        }
    }

    public static void parseMendingInfo(String flag)
    {
        switch (flag)
        {
            case "left_true" -> leftOnMending = "true";
            case "left_false" -> leftOnMending = "false";


            case "right_true" -> rightOnMending = "true";
            case "right_false" -> rightOnMending = "false";
        }
    }

    public static void parsePlayerNames(String[] names)
    {
        playerNames = names;
    }
}
