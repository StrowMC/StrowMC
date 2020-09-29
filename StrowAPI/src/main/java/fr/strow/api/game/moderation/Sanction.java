package fr.strow.api.game.moderation;

import fr.strow.api.property.Property;

import java.util.List;

public interface Sanction<T extends DatedSanction> extends Property {

    void add(T sanction);

    List<T> get();
}
