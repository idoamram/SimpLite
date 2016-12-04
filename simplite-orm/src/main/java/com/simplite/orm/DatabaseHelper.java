package com.simplite.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.simplite.orm.interfaces.SimpLiteConfiguration;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

//    private static final String DATABASE_NAME = "marshal_local_db";
//    public static final int DATABASE_VERSION = 5;
    private static DatabaseHelper helperInstance;
    private static SQLiteDatabase databaseInstance;

    private Context context;
    private SimpLiteConfiguration config;

    private DatabaseHelper(Context context) {
        super(context, ManifestProvider.getDatabaseName(context), null, ManifestProvider.getDatabaseVersion(context));
        this.config = ManifestProvider.getSimpLiteConfiguration(context);
        this.context = context;
    }

    static DatabaseHelper getHelperInstance(Context context) {
        if (helperInstance == null)
            helperInstance = new DatabaseHelper(context.getApplicationContext());
        return helperInstance;
    }

    public static SQLiteDatabase getWritableDatabaseInstance(Context context) {
        if (databaseInstance == null || (!databaseInstance.isOpen()))
            databaseInstance = getHelperInstance(context).getWritableDatabase();
        return databaseInstance;
    }

    public static void closeIfExist() {
        if (helperInstance != null)
            helperInstance.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (config != null) config.beforeOnCreate(context);
        try {
            ArrayList<String> createTableCommands = ManifestProvider.getCreateTableCommands(context);
            for (String command : createTableCommands) {
                db.execSQL(command);
            }
            Log.i("SimpLite", "onCreate: All tables created successfully (" +
                    createTableCommands.size() + " tables)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (config != null) config.afterOnCreate(context);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (config != null) config.beforeOnUpgrade(context);
        ArrayList<String> dropTableCommands = ManifestProvider.getDropTableCommands(context);
        for (String command : dropTableCommands) {
            db.execSQL(command);
        }
        Log.i("SimpLite", "onUpgrade: Upgrade from version " + oldVersion + " to version " +
                newVersion + ".\nAll tables dropped successfully (" + dropTableCommands.size() + " tables)");
        onCreate(db);
        if (config != null) config.afterOnUpgrade(context);
    }
}
