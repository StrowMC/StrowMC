/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 03/07/2020 14:48
 */

package fr.strow.persistence.beans.sanctions;

import java.sql.Timestamp;
import java.util.UUID;

public class MuteBean extends SanctionBean {

    public MuteBean(UUID uuid, String reason, UUID sanctionerUuid, Timestamp startingTimestamp, Timestamp endingTimestamp) {
        super(uuid, reason, sanctionerUuid, startingTimestamp, endingTimestamp);
    }
}
