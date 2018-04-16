package com.android.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

public class HttpUtil {

	 
	public static final String BASE_URL = "http://10.12.224.232:8080/gswServer/";
	public static final String UPLOAD_URL = "http://10.12.224.232:8080/gswSrver/FileUploadService";
	public static final String FILEPATH_URL = "http://10.12.224.232:8080/gswServer/upload/";

	public static String getJsonFromServlet(String jsonPara, String servletName) {
		// 保证数据不是乱码
		String returnResult = "";
		try {

			StringEntity se = new StringEntity(jsonPara, "UTF-8");

			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));

			HttpPost post = HttpUtil.getHttpPost(HttpUtil.BASE_URL
					+ servletName);
			if (jsonPara != "" && jsonPara != null) {
				post.setEntity(se);
			}
			HttpClient client = HttpUtil.getHttpClient();

			HttpResponse httpResponse = client.execute(post);

			int httpCode = httpResponse.getStatusLine().getStatusCode();

			if (httpCode == HttpURLConnection.HTTP_OK && httpResponse != null) {

				org.apache.http.Header[] headers = httpResponse.getAllHeaders();

				HttpEntity entity = httpResponse.getEntity();

				org.apache.http.Header header = httpResponse
						.getFirstHeader("content-type");

				// 读取服务器返回的json
				InputStream inputStream = entity.getContent();

				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);

				BufferedReader reader = new BufferedReader(inputStreamReader);

				String s;

				StringBuffer result = new StringBuffer();

				while ((s = reader.readLine()) != null) {
					result.append(s);
				}
				reader.close();

				returnResult = result.toString();

			}
		} catch (Exception ex) {
			returnResult = "2";
		}

		return returnResult;
	}

	// 获得Get请求对象request
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}

	// 获得Post请求对象request
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	public static HttpClient getHttpClient() {

		BasicHttpParams httpParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);

		HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);

		HttpClient client = new DefaultHttpClient(httpParams);

		return client;
	}

	// 获得post
	public static HttpPost post(String url) {
		HttpPost post = new HttpPost(url);

		return post;
	}

	// 发送Post请求，获得响应查询结果
	public static String queryStringForPost(String url) {
		// 根据url获得HttpPost对象
		HttpPost request = HttpUtil.getHttpPost(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	// 获得响应查询结果
	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	// 发送Get请求，获得响应查询结果
	public static String queryStringForGet(String url) {
		// 获得HttpGet对象
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	// 获取网络图片
	public static Bitmap getBitmapFromUrl(String imgUrl) {

		URL url;

		Bitmap bitmap = null;

		try {
			url = new URL(imgUrl);

			InputStream is = url.openConnection().getInputStream();

			BufferedInputStream bis = new BufferedInputStream(is);

			bitmap = BitmapFactory.decodeStream(bis);

			bis.close();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return bitmap;
	}
}
