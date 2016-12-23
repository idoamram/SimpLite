package com.simplite.orm;

import android.content.Context;
import android.content.pm.PackageManager;
import com.simplite.orm.interfaces.SimpLiteConfiguration;
import java.util.ArrayList;

public final class ManifestProvider {

    private static final String DEFAULT_DATABASE_NAME = "default_db";
    private static final int DEFAULT_DATABASE_VERSION = 1;

    private static final String META_DATABASE_NAME = "DATABASE_NAME";
    private static final String META_DATABASE_VERSION = "DATABASE_VERSION";
    private static final String META_ENTITIES_CLASSES_NAME = "ENTITIES_CLASSES_NAME";
    private static final String META_CONFIGURATION_CLASS = "CONFIG_CLASS";

    private static int dataBaseVersion;
    private static String dataBaseName;
    private static SimpLiteConfiguration simpLiteConfiguration;

    public static SimpLiteConfiguration getSimpLiteConfiguration(Context context) {
        if (simpLiteConfiguration == null) {
            try {
                simpLiteConfiguration = (SimpLiteConfiguration) Class.forName(context.getPackageManager().getApplicationInfo(
                        context.getPackageName(), PackageManager.GET_META_DATA).metaData
                        .getString(META_CONFIGURATION_CLASS).replaceAll(" ","")).newInstance();
            } catch (InstantiationException | IllegalAccessException | PackageManager.NameNotFoundException | ClassNotFoundException e) {
                e.printStackTrace();
                return simpLiteConfiguration;
            }
        }
        return simpLiteConfiguration;
    }

    public static String getDatabaseName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            if (dataBaseName == null) {
                dataBaseName = packageManager.getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA).metaData.getString(META_DATABASE_NAME);
                return dataBaseName;
            } else return dataBaseName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return DEFAULT_DATABASE_NAME;
        }
    }

    public static int getDatabaseVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            if (dataBaseVersion == 0) {
                dataBaseVersion = packageManager.getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA).metaData.getInt(META_DATABASE_VERSION, 1);
                return dataBaseVersion;
            } else return dataBaseVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return DEFAULT_DATABASE_VERSION;
        }
    }

    public static ArrayList<Class<? extends DBObject>> getEntities(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ArrayList<Class<? extends DBObject>> entities = new ArrayList<>();
        try {
            String[] entitiesClassName = packageManager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA).metaData.getString(META_ENTITIES_CLASSES_NAME, "").split(",");

            for (String entityName : entitiesClassName) {
                entities.add((Class<? extends DBObject>) Class.forName(entityName.replaceAll(" ","")));
            }
        } catch (PackageManager.NameNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public static ArrayList<String> getCreateTableCommands(Context context) {
        ArrayList<Class<? extends DBObject>> classes = getEntities(context);
        ArrayList<String> commands = new ArrayList<>();

        for(Class<? extends DBObject> clas : classes) {
            try {
                Object instance = clas.getConstructor(Context.class).newInstance(context);
                commands.add((clas.cast(instance)).getCreateTableCommand());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return commands;
    }

    public static ArrayList<String> getDropTableCommands(Context context) {
        ArrayList<Class<? extends DBObject>> classes = getEntities(context);
        ArrayList<String> commands = new ArrayList<>();

        for(Class<? extends DBObject> clas : classes) {
            try {
                Object instance = clas.getConstructor(Context.class).newInstance(context);
                commands.add((clas.cast(instance)).getDropTableIfExistCommand());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return commands;
    }
}
