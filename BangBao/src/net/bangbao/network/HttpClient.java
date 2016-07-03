package net.bangbao.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;

import android.util.Log;
import android.webkit.URLUtil;

public class HttpClient{
	
	public String httpPost(String strUrl,Map<String,Object> maps){
		
		if(strUrl == null) return null;
		StringBuffer sb = new StringBuffer();
		String json = null;
		if(maps != null)
		{
			json = new Gson().toJson(maps);
		}
		InputStream is = null;
		try{
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(50*1000);
	        conn.setConnectTimeout(50*1000);
	        conn.setRequestMethod("POST");
	        conn.setDoInput(true);
	        conn.getOutputStream().write(json.getBytes());// 输入参数
	        conn.connect();
	        int response = conn.getResponseCode();
	        is = conn.getInputStream();
	        String contentAsString = readIt(is);
	        return contentAsString;
		}catch(Exception e){
			e.printStackTrace();
			
		}finally {
	        if (is != null) {
	            try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
		return null;
	}
	
	
	public String readIt(InputStream is)throws IOException, UnsupportedEncodingException
	{
		Reader reader = null;
		reader = new InputStreamReader(is, "utf-8");
		char[] buffer = new char[1];
		StringBuffer sb = new StringBuffer();
		while(reader.read(buffer) != -1)
		{
			sb.append(buffer);
		}
		return sb.toString();
	}
}
