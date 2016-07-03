package net.bangbao.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * @author mosl
 *  提交post的Request
 */
public class PostFormRequest extends Request<String> {

	public static final String TAG = "PostFormRequest";
	private int user_id;
	private String user_token;
	private Bitmap bp;
	private String filename;
	
	private String BOUNDARY = "------WebKitFormBoundarycLaDD5A1D95uHhxR"; //数据分隔线
	private String MULTIPART_FORM_DATA = "multipart/form-data";

	private Success success;
	
	public interface Success{
		public void success(String success);
	}
	public PostFormRequest(int method,String url,Success success,ErrorListener listener,
			int user_id,String user_token,Bitmap bp,String filename) {
		this(method,url,listener);
		this.success = success;
		this.user_id = user_id;
		this.user_token = user_token;
		this.bp = bp;
		this.filename = filename;
	}
	
	public PostFormRequest(int method, String url, ErrorListener listener) {
		super(method, url, listener);
		setShouldCache(false);
		setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
		 StringBuffer sb= new StringBuffer() ;
		 // 第一个参数
         /*第一行*/
         sb.append("--"+BOUNDARY);
         sb.append("\r\n") ;
         /*第二行*/
         sb.append("Content-Disposition: form-data;");
         sb.append(" name=\"");
         sb.append("user_id") ;
         sb.append("\"") ;
         sb.append("\r\n") ;
         /*第三行*/
         sb.append("\r\n") ;
         /*第四行*/
         sb.append(this.user_id) ;
         sb.append("\r\n") ;
         
      // 第二个参数
         /*第一行*/
         sb.append("--"+BOUNDARY);
         sb.append("\r\n") ;
         /*第二行*/
         sb.append("Content-Disposition: form-data;");
         sb.append(" name=\"");
         sb.append("token") ;
         sb.append("\"") ;
         sb.append("\r\n") ;
         /*第三行*/
         sb.append("\r\n") ;
         /*第四行*/
         sb.append(this.user_token) ;
         sb.append("\r\n") ;
         //图片
         sb.append("--"+BOUNDARY);
         sb.append("\r\n") ;
         /*第二行*/
         sb.append("Content-Disposition: form-data;");
         sb.append(" name=\"");
         sb.append("pic") ;
         sb.append("\"") ;
         sb.append("; filename=\"") ;
         sb.append(filename) ;
         sb.append("\"");
         sb.append("\r\n") ;
         /*第三行*/
         sb.append("Content-Type: ");
         sb.append("image/png") ;
         sb.append("\r\n") ;
         /*第四行*/
         sb.append("\r\n") ;
         try {
             bos.write(sb.toString().getBytes("utf-8"));
             //图片
             ByteArrayOutputStream bosimage = new ByteArrayOutputStream() ;
             bp.compress(Bitmap.CompressFormat.JPEG,80,bosimage) ;
             bos.write(bosimage.toByteArray());
             bos.write("\r\n".getBytes("utf-8"));
         } catch (IOException e) {
             e.printStackTrace();
         }
         /*结尾行*/
         String endLine = "--" + BOUNDARY + "--" + "\r\n" ;
         try {
             bos.write(endLine.toString().getBytes("utf-8"));
         } catch (IOException e) {
             e.printStackTrace();
         }
         Log.v(TAG ,"=====formImage====\n"+bos.toString()) ;
         return bos.toByteArray();
	}
	
	/**
     * 这里开始解析数据
     * @param response Response from the network
     * @return
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String mString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.v("zgy", "====mString===" + mString);

            return Response.success(mString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
    
	 @Override
	public String getBodyContentType() {
	        return MULTIPART_FORM_DATA+"; boundary="+BOUNDARY;
	    }

	@Override
	protected void deliverResponse(String response) {
		
		if(success != null)
			success.success(response);
	}
}
