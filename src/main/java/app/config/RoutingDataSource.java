package app.config;

import app.model.DbSnapshot;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by Ionut on 21-May-17.
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        DbSnapshot currentSnapshot = DbSnapshotHolder.getDbSnapshot();
        return currentSnapshot != null ? currentSnapshot.getKey() : DbSnapshotHolder.getDefaultSnapshot().getKey();
    }

    /*
    * */

}
