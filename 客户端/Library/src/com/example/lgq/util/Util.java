package com.example.lgq.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
 
/**
 * 工具类
 * @author LIYAO
 *
 */
public class Util {
	

	  //读取流中的数据
			public static byte[] getByte(InputStream is)throws Exception {
				ByteArrayOutputStream output=new ByteArrayOutputStream();
				byte[] buff=new byte[1024];
				int len=0;
				while((len=is.read(buff))!=-1)
						{
					output.write(buff, 0, len);
						}
				return output.toByteArray();
				
			}
	
	
	       //发送post请求
			public static String sendPostRequest(String path,Map<String,String>params,String encoding) throws IOException {
				String result="";
				List<NameValuePair>pairs=new ArrayList<NameValuePair>();
				if(pairs!=null){
					for(Map.Entry<String,String>entry:params.entrySet()){
						pairs.add(new BasicNameValuePair(entry.getKey(),  entry.getValue()));
					}
				}
				UrlEncodedFormEntity entity=new UrlEncodedFormEntity(pairs,encoding);
				HttpPost post=new HttpPost(path);
				post.setEntity(entity);
				DefaultHttpClient client=new DefaultHttpClient();
				HttpResponse response=client.execute(post);
				if(response.getStatusLine().getStatusCode()==200){
					result=EntityUtils.toString(response.getEntity());      //获得response数据
				}
				System.out.println(result.isEmpty()+"返回信息");
				return result;
			}
		
			public static String TIME_OUT = "操作超时";
				 
				 
		
			private void parseJson(InputStream instream) throws Exception {
				byte[]data=Util.getByte(instream);
				String json=new String(data);
				System.out.print(json+"json");
				if(json.equals("fail")){
					
				}else{
				System.out.println(json+"返回信息");
				JSONArray array=new JSONArray(json);
				
				for(int i=0;i<array.length();i++){
					JSONObject jobject=array.getJSONObject(i);
					
					jobject.getString("uname");
					
				}
				
				}
		    }
			


			
	}

