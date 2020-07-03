package fr.strow.core.utils;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class Utils {

    public static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException ignored){}
        return false;
    }
}
