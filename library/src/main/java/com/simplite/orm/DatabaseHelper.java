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
//            db.execSQL(new MaterialItem(context).getCreateTableCommand());
//            db.execSQL(new Cycle(context).getCreateTableCommand());
//            db.execSQL(new Course(context).getCreateTableCommand());
//            db.execSQL(new Rating(context).getCreateTableCommand());
//            db.execSQL(new MalshabItem(context).getCreateTableCommand());
//            db.execSQL(new FaqItem(context).getCreateTableCommand());
            ArrayList<String> createTableCommands = ManifestProvider.getCreateTableCommands(context);
            for (String command : createTableCommands) {
                db.execSQL(command);
            }
//            initializePreferences();
            Log.i("SimpLite", "onCreate: All tables created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (config != null) config.afterOnCreate(context);
    }

//    private void initializePreferences() {
//        try {
//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//            sharedPreferences.edit().putBoolean(Constants.PREF_IS_FIRST_RUN, true).apply();
//            sharedPreferences.edit().putBoolean(Constants.PREF_IS_UPDATE_SERVICE_SUCCESS_ONCE, false).apply();
//            sharedPreferences.edit().putLong(Constants.PREF_LAST_UPDATE_TIMESTAMP, 0).apply();
//            sharedPreferences.edit().putInt(Constants.PREF_DATABASE_VERSION, ManifestProvider.getDatabaseVersion(context)).apply();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (config != null) config.beforeOnUpgrade(context);
//        db.execSQL(MaterialItem.getDropTableIfExistCommand(MaterialItem.class));
//        db.execSQL(Course.getDropTableIfExistCommand(Course.class));
//        db.execSQL(Cycle.getDropTableIfExistCommand(Cycle.class));
//        db.execSQL(Rating.getDropTableIfExistCommand(Rating.class));
//        db.execSQL(MalshabItem.getDropTableIfExistCommand(MalshabItem.class));
//        db.execSQL(FaqItem.getDropTableIfExistCommand(FaqItem.class));
        ArrayList<String> dropTableCommands = ManifestProvider.getDropTableCommands(context);
        for (String command : dropTableCommands) {
            db.execSQL(command);
        }
        Log.i("SimpLite", "onUpgrade: Upgrade from version " + oldVersion + " to version " +
                newVersion + ".\nAll tables dropped successfully");
        onCreate(db);
        if (config != null) config.afterOnUpgrade(context);
    }
}
