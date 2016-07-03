package net.bangbao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author mosl
 * sqlite 数据库帮助类
 */
public class BangbaoDataBaseHelper extends SQLiteOpenHelper {

	private static final String TAG = BangbaoDataBaseHelper.class.getSimpleName();
	
	private static String sqlChatDATABASE = "RONGYUN_CHAT_DATABASE";
	private static int version = 1;
	private String create_table_chat = "create table if not exists user_chat(id integer primary key autoincrement,myself_id integer not null" +
			",to_id integer not null,msg text not null,flag integer not null,timetmtp integer not null)";
	
	public BangbaoDataBaseHelper(Context context){
		this(context,sqlChatDATABASE,null,version);
	}
	
	public BangbaoDataBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG,"onCreate BangbaoDataBaseHelper");
		db.execSQL(create_table_chat);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
