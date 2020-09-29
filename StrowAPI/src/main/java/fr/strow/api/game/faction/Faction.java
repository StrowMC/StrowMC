/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 15:56
 */

package fr.strow.api.game.faction;

import fr.strow.api.game.Messenger;
import fr.strow.api.property.PropertiesOwner;
import fr.strow.api.property.Registerer;

public interface Faction extends PropertiesOwner<Faction>, Registerer<Faction>, Messenger {

}
