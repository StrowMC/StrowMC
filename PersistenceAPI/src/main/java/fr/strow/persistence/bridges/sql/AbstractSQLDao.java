package fr.strow.persistence.bridges.sql;

import fr.strow.persistence.data.sql.SQLAccess;

public abstract class AbstractSQLDao {

    protected SQLAccess sqlAccess;

    public AbstractSQLDao(SQLAccess sqlAccess) {
        this.sqlAccess = sqlAccess;
    }
}
