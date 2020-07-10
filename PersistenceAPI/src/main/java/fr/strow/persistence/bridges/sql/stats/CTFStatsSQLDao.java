package fr.strow.persistence.bridges.sql.stats;

import com.google.inject.Inject;
import fr.strow.persistence.bridges.sql.AbstractSQLDao;
import fr.strow.persistence.data.sql.SQLAccess;

public class CTFStatsSQLDao extends AbstractSQLDao {

    @Inject
    public CTFStatsSQLDao(SQLAccess sqlAccess) {
        super(sqlAccess);
    }
}
