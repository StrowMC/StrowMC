package fr.strow.core.api.services;

import fr.strow.api.services.GameUtilsService;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hokkaydo on 09-07-2020.
 */
public class GameUtilsServiceImpl implements GameUtilsService {
    @Override
    public String formatTime(Instant instant) {
        List<String> nodes = new ArrayList<>();
        if (instant.get(ChronoField.YEAR) > 0) {
            nodes.add(instant.get(ChronoField.YEAR) + " année" + (instant.get(ChronoField.YEAR) > 1 ? "s" : "s"));
        }
        if (instant.get(ChronoField.MONTH_OF_YEAR) > 0) {
            nodes.add(instant.get(ChronoField.MONTH_OF_YEAR) + " mois");
        }
        if (instant.get(ChronoField.DAY_OF_WEEK) > 0) {
            nodes.add(instant.get(ChronoField.DAY_OF_WEEK) + " jour" + (instant.get(ChronoField.DAY_OF_WEEK) > 1 ? "s" : "s"));
        }
        if (instant.get(ChronoField.HOUR_OF_DAY) > 0) {
            nodes.add(instant.get(ChronoField.HOUR_OF_DAY) + " heure" + (instant.get(ChronoField.HOUR_OF_DAY) > 1 ? "s" : "s"));
        }
        if (instant.get(ChronoField.MINUTE_OF_HOUR) > 0) {
            nodes.add(instant.get(ChronoField.MINUTE_OF_HOUR) + " minute" + (instant.get(ChronoField.MINUTE_OF_HOUR) > 1 ? "s" : "s"));
        }
        if (instant.get(ChronoField.SECOND_OF_MINUTE) > 0) {
            nodes.add(instant.get(ChronoField.SECOND_OF_MINUTE) + " seconde" + (instant.get(ChronoField.SECOND_OF_MINUTE) > 1 ? "s" : "s"));
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nodes.size() - 1; i++) {
            stringBuilder.append(nodes.get(i)).append(" ");
        }
        stringBuilder.append("et ");
        stringBuilder.append(nodes.get(nodes.size() - 1));
        return stringBuilder.toString();
    }
}
