package com.flower.mitayclient.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Locations
{
    public static boolean isAtOverWorldRegion(double x1, double x2, double z1, double z2, double playerX, double playerZ,String worldName)
    {
        if(playerX >= x1 && playerX <= x2 && playerZ >= z1 && playerZ <= z2 && worldName == "主世界")
        {
            return true;
        }else return false;
    }
    public static boolean isAtNetherRegion(double x1, double x2, double z1, double z2, double playerX, double playerZ,String worldName)
    {
        if(playerX >= x1 && playerX <= x2 && playerZ >= z1 && playerZ <= z2 && worldName == "地狱")
        {
            return true;
        }else return false;
    }
    public static boolean isAtEndRegion(double x1, double x2, double z1, double z2, double playerX, double playerZ,String worldName)
    {
        if(playerX >= x1 && playerX <= x2 && playerZ >= z1 && playerZ <= z2 && worldName == "末地")
        {
            return true;
        }else return false;
    }
    public static String getPlace(double x, double z, String worldName)
    {
        if(worldName == "主世界")
        {
            if(isAtOverWorldRegion(114,171,69,159,x,z, worldName))
            {
                return "村民交易所";
            }else return null;
        }else if(worldName == "地狱")
        {
            if(isAtNetherRegion(0,0,0,0,x,z,worldName))
            {
                return "地狱";
            }else return null;
        }else if(worldName == "末地")
        {
            if(isAtEndRegion(0,0,0,0,x,z,worldName))
            {
                return "小黑塔";
            }else return null;
        }else return null;
    }
}
