package app.config;

import app.model.DbSnapshot;

/**
 * Created by Ionut on 21-May-17.
 */
public class DbSnapshotHolder {

    private static final ThreadLocal<DbSnapshot> snapshotHolder = new ThreadLocal<>();

    public final static DbSnapshot[] SNAPSHOTS = {
            new DbSnapshot(2016, 2017, 1, "extra"),
            new DbSnapshot(2016, 2017, 2, "original")
    };

    public static void setDbSnapshot(DbSnapshot dbSnapshot) {
        if(dbSnapshot == null){
            throw new NullPointerException();
        }
        snapshotHolder.set(dbSnapshot);
    }

    public static DbSnapshot getDbSnapshot() {
        return snapshotHolder.get();
    }

    public static DbSnapshot getDefaultSnapshot(){
        return SNAPSHOTS[SNAPSHOTS.length - 1];
    }

    public static void clearDbSnapshot() {
        snapshotHolder.remove();
    }
}