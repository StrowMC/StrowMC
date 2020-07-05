package fr.strow.core.module.punishment.command.parameter;

import fr.strow.core.utils.Utils;
import me.choukas.commands.api.Parameter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Hokkaydo on 27-06-2020.
 */
public class TimeParameter extends Parameter<Timestamp> {

    public TimeParameter() {
        super("time");
    }

    @Override
    public Optional<Timestamp> check(String s) {
        int years = 0;
        int months = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        if(s.contains("y")){
            String y = s.substring(0, s.indexOf("y") + 1);
            if(Utils.isInteger(y)){
                years = Integer.parseInt(y);
                s = s.replace(y, "");
            }else{
                return Optional.empty();
            }
        }
        if(s.contains("mo")){
            String mo = s.substring(s.indexOf("mo") - 2 , s.indexOf("mo") + 1);
            if(Utils.isInteger(mo)){
                months = Integer.parseInt(mo);
                s = s.replace(mo, "");
            }else{
                return Optional.empty();
            }
        }
        if(s.contains("d")){
            String d = s.substring(s.indexOf("d") - 2 , s.indexOf("d") + 1);
            if(Utils.isInteger(d)){
                days = Integer.parseInt(d);
                s = s.replace(d, "");
            }else{
                return Optional.empty();
            }
        }
        if(s.contains("h")){
            String h = s.substring(s.indexOf("h") - 2 , s.indexOf("h") + 1);
            if(Utils.isInteger(h)){
                hours = Integer.parseInt(h);
                s = s.replace(h, "");
            }else{
                return Optional.empty();
            }
        }
        if(s.contains("m")){
            String m = s.substring(s.indexOf("m") - 2 , s.indexOf("m") + 1);
            if(Utils.isInteger(m)){
                minutes = Integer.parseInt(m);
                s = s.replace(m, "");
            }else{
                return Optional.empty();
            }
        }
        if(s.contains("s")){
            String sec = s.substring(s.indexOf("s") - 2 , s.indexOf("s") + 1);
            if(Utils.isInteger(sec)){
                seconds = Integer.parseInt(sec);
            }else{
                return Optional.empty();
            }
        }
        return Optional.of(Timestamp.valueOf(LocalDateTime.now().
                withYear(years == 0 ? LocalDateTime.now().getYear() : months)
                .withMonth(months == 0 ? LocalDateTime.now().getMonth().getValue() : months)
                .withDayOfMonth(days == 0 ? LocalDateTime.now().getDayOfMonth() : days)
                .withHour(hours == 0 ? LocalDateTime.now().getHour() : hours)
                .withMinute(minutes == 0 ? LocalDateTime.now().getMinute() : minutes)
                .withSecond(seconds == 0 ? LocalDateTime.now().getSecond() : seconds)
                ));
    }

    @Override
    public String getMessage(String s) {
        return "§cFormat de temps incorrect. Veuillez spécifier un temps positif en utilisant les abréviations suivantes : " +
                "années: §ay§c, mois: §amo§c, jours: §ad§c, heures: §ah§c, minutes: §am§c, secondes: §as";
    }
}
