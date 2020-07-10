package fr.strow.persistence.bridges.sql.stats;

import com.google.inject.Inject;
import fr.strow.persistence.bridges.sql.AbstractSQLDao;
import fr.strow.persistence.data.sql.SQLAccess;

public class FishingStatsSQLDao extends AbstractSQLDao {

    @Inject
    public FishingStatsSQLDao(SQLAccess sqlAccess) {
        super(sqlAccess);
    }
}
