package fr.strow.core.module.punishment.command.parameter;

import fr.strow.core.utils.Utils;
import me.choukas.commands.api.Condition;
import me.choukas.commands.api.Parameter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class TimeParameter extends Parameter<Timestamp> {

    public TimeParameter() {
        super("time");
    }

    @Override
    public List<Condition<String>> getConditions() {
        return Collections.singletonList(new Condition<String>() {
            @Override
            public boolean check(String o) {

                if (o.contains("y")) {
                    String y = o.substring(0, o.indexOf("y") + 1);
                    if (!Utils.isInteger(y)) {
                        return false;
                    }
                }
                if (o.contains("mo")) {
                    String mo = o.substring(o.indexOf("mo") - 2, o.indexOf("mo") + 1);
                    if (!Utils.isInteger(mo)) {
                        return false;
                    }
                }
                if (o.contains("d")) {
                    String d = o.substring(o.indexOf("d") - 2, o.indexOf("d") + 1);
                    if (!Utils.isInteger(d)) {
                        return false;
                    }
                }
                if (o.contains("h")) {
                    String h = o.substring(o.indexOf("h") - 2, o.indexOf("h") + 1);
                    if (!Utils.isInteger(h)) {
                        return false;
                    }
                }
                if (o.contains("m")) {
                    String m = o.substring(o.indexOf("m") - 2, o.indexOf("m") + 1);
                    if (!Utils.isInteger(m)) {
                        return false;
                    }
                }
                if (o.contains("s")) {
                    String sec = o.substring(o.indexOf("s") - 2, o.indexOf("s") + 1);
                    return Utils.isInteger(sec);
                }
                return true;
            }

            @Override
            public String getMessage(String s) {
                return "§cFormat de temps incorrect. Veuillez spécifier un temps positif en utilisant les abréviations suivantes : " +
                        "années: §ay§c, mois: §amo§c, jours: §ad§c, heures: §ah§c, minutes: §am§c, secondes: §as";
            }
        });
    }

    @Override
    public Timestamp get(String s) {
        int years = 0;
        int months = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        if (s.contains("y")) {
            String y = s.substring(0, s.indexOf("y") + 1);
            if (Utils.isInteger(y)) {
                years = Integer.parseInt(y);
                s = s.replace(y, "");
            }else{
                return null;
            }
        }
        if(s.contains("mo")){
            String mo = s.substring(s.indexOf("mo") - 2 , s.indexOf("mo") + 1);
            if(Utils.isInteger(mo)){
                months = Integer.parseInt(mo);
                s = s.replace(mo, "");
            }else{
                return null;
            }
        }
        if(s.contains("d")){
            String d = s.substring(s.indexOf("d") - 2 , s.indexOf("d") + 1);
            if(Utils.isInteger(d)){
                days = Integer.parseInt(d);
                s = s.replace(d, "");
            }else{
                return null;
            }
        }
        if(s.contains("h")){
            String h = s.substring(s.indexOf("h") - 2 , s.indexOf("h") + 1);
            if(Utils.isInteger(h)){
                hours = Integer.parseInt(h);
                s = s.replace(h, "");
            }else{
                return null;
            }
        }
        if(s.contains("m")){
            String m = s.substring(s.indexOf("m") - 2 , s.indexOf("m") + 1);
            if(Utils.isInteger(m)){
                minutes = Integer.parseInt(m);
                s = s.replace(m, "");
            }else{
                return null;
            }
        }
        if(s.contains("s")){
            String sec = s.substring(s.indexOf("s") - 2 , s.indexOf("s") + 1);
            if(Utils.isInteger(sec)){
                seconds = Integer.parseInt(sec);
            }else{
                return null;
            }
        }
        return Timestamp.valueOf(LocalDateTime.now().
                withYear(years == 0 ? LocalDateTime.now().getYear() : months)
                .withMonth(months == 0 ? LocalDateTime.now().getMonth().getValue() : months)
                .withDayOfMonth(days == 0 ? LocalDateTime.now().getDayOfMonth() : days)
                .withHour(hours == 0 ? LocalDateTime.now().getHour() : hours)
                .withMinute(minutes == 0 ? LocalDateTime.now().getMinute() : minutes)
                .withSecond(seconds == 0 ? LocalDateTime.now().getSecond() : seconds)
        );
    }
}
