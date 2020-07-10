/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 03/07/2020 14:53
 */

package fr.strow.persistence.beans.moderation;

import java.sql.Timestamp;
import java.util.UUID;

public class BanBean extends SanctionBean {

    public BanBean(UUID uuid, String reason, UUID sanctionerUuid, Timestamp startingTimestamp, Timestamp endingTimestamp) {
        super(uuid, reason, sanctionerUuid, startingTimestamp, endingTimestamp);
    }
}
