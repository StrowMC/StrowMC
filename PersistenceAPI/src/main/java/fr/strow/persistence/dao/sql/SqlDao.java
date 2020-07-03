/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:48
 */

package fr.strow.persistence.dao.sql;

import fr.strow.persistence.data.sql.SQLAccess;

public abstract class SqlDao {

    protected final SQLAccess access;

    public SqlDao(SQLAccess access) {
        this.access = access;
    }
}
