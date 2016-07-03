
package net.bangbao.database;

import java.util.ArrayList;
import java.util.List;

import net.bangbao.AppInit;
import net.bangbao.dao.Msg;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseUtils {
	
	public static void insertMsg(SQLiteDatabase sqliteDatabase,String user_id,String to_id,String msg,int flag){
		long currentTimeMillis = System.currentTimeMillis();
		String sql = "insert into user_chat(myself_id,to_id,msg,flag,timetmtp) values("+user_id+","+to_id+",'"+msg+"',"+flag+","+currentTimeMillis+")";
		try{
			sqliteDatabase.execSQL(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static List<Msg> queryMsg(SQLiteDatabase sqliteDatabase,int user_id,int dest_id){
		
		String sql = "select * from user_chat where to_id = "+ dest_id +" order by timetmtp";
		
		Cursor cur = null;
		List<Msg> list = new ArrayList<Msg>();
		try{
			cur = sqliteDatabase.rawQuery(sql, null);
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(cur != null)
		while(cur.moveToNext()){
			Msg msg = new Msg(cur.getString(3),cur.getInt(4),cur.getLong(5),null,null);
		    list.add(msg);
		}
		return list;
	}
}
