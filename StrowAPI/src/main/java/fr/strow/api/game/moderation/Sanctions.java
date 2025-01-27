package fr.strow.api.game.moderation;

import fr.strow.api.property.Property;

import java.util.List;

public interface Sanctions extends Property {

    <T extends Sanction<?>> T get(Class<T> sanction);

    List<DatedSanction> getOrderedByDecreasingDate();
}
