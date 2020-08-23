package com.example.lgq.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public HttpUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 
	 * @param path 获取指定路径的json信息
	 * @param  指定编码格式
	 * @return 
	 */
	
	public static String sendPostMethod(String path,String encoding){
		String result="";
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost(path);
		try {
			HttpResponse response=client.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				result=EntityUtils.toString(response.getEntity(),encoding);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			client.getConnectionManager().shutdown();//关闭连接
		}
		return result;
		
	}
}
