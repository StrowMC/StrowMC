package fr.strow.core.utils;

import java.util.Optional;
import java.util.function.Consumer;

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

    public static <T> void ifPresentOrElse(Optional<T> optional, Consumer<T> consumer, Runnable runnable){
        if(optional.isPresent()){
            consumer.accept(optional.get());
        }else{
            runnable.run();
        }
    }
}
