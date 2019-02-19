package com.newport.app.data.models.ApplicationSQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.newport.app.util.Constant;

public class NewportAppBD extends SQLiteOpenHelper {

    private static String nameDB = Constant.NEWPORTAPPBD;
    private static int versionDB = 1;
    private static SQLiteDatabase.CursorFactory factory = null;
    private String queryCreateTableMessages = "CREATE TABLE Message (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "message_sended TEXT," +
            "message_sended_time TEXT," +
            "chat_id TEXT" +
            ")";

    public NewportAppBD(Context context) {
        super(context, nameDB, factory, versionDB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryCreateTableMessages);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
