package com.hdsx.hdyyglyh.greendb.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hdsx.hdyyglyh.greendb.bean.PathBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PATH_BEAN".
*/
public class PathBeanDao extends AbstractDao<PathBean, String> {

    public static final String TABLENAME = "PATH_BEAN";

    /**
     * Properties of entity PathBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Userid = new Property(0, String.class, "userid", true, "USERID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Tracktime = new Property(2, String.class, "tracktime", false, "TRACKTIME");
        public final static Property Polygon = new Property(3, String.class, "polygon", false, "POLYGON");
        public final static Property Polygonbd = new Property(4, String.class, "polygonbd", false, "POLYGONBD");
    }


    public PathBeanDao(DaoConfig config) {
        super(config);
    }
    
    public PathBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PATH_BEAN\" (" + //
                "\"USERID\" TEXT PRIMARY KEY NOT NULL ," + // 0: userid
                "\"NAME\" TEXT," + // 1: name
                "\"TRACKTIME\" TEXT," + // 2: tracktime
                "\"POLYGON\" TEXT," + // 3: polygon
                "\"POLYGONBD\" TEXT);"); // 4: polygonbd
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PATH_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PathBean entity) {
        stmt.clearBindings();
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(1, userid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String tracktime = entity.getTracktime();
        if (tracktime != null) {
            stmt.bindString(3, tracktime);
        }
 
        String polygon = entity.getPolygon();
        if (polygon != null) {
            stmt.bindString(4, polygon);
        }
 
        String polygonbd = entity.getPolygonbd();
        if (polygonbd != null) {
            stmt.bindString(5, polygonbd);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PathBean entity) {
        stmt.clearBindings();
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(1, userid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String tracktime = entity.getTracktime();
        if (tracktime != null) {
            stmt.bindString(3, tracktime);
        }
 
        String polygon = entity.getPolygon();
        if (polygon != null) {
            stmt.bindString(4, polygon);
        }
 
        String polygonbd = entity.getPolygonbd();
        if (polygonbd != null) {
            stmt.bindString(5, polygonbd);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public PathBean readEntity(Cursor cursor, int offset) {
        PathBean entity = new PathBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // tracktime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // polygon
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // polygonbd
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PathBean entity, int offset) {
        entity.setUserid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTracktime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPolygon(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPolygonbd(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final String updateKeyAfterInsert(PathBean entity, long rowId) {
        return entity.getUserid();
    }
    
    @Override
    public String getKey(PathBean entity) {
        if(entity != null) {
            return entity.getUserid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PathBean entity) {
        return entity.getUserid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}