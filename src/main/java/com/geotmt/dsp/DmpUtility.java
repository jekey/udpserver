package com.geotmt.dsp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Created by Administrator on 2014/4/23.
 */
public class DmpUtility {
    private static final String TAG = "http://10.111.31.14:7878/geo-dsp-request.php?act=keyword&ip='";
    private static final String KEY = "http://10.111.31.14:7878/geo-dsp-request.php?act=keyword&ip='";
    private static final String URL = "http://10.111.31.14:7878/geo-dsp-request.php?act=hostname&ip='";
    public static String getTagData(String ip){
        return null;
    }
    public static String getKeyData(String ip){
        String url = KEY+ip+"'";
        String result = getResult(url);
        return result;
    }
    public static String getUrlData(String ip){
        String url = URL+ip+"'";
        String result = getResult(url);
        return result;
    }

    public static String getResult(String url) {
        HttpClient client = new DefaultHttpClient();
        // 请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,10);
        String strResult="";
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse res = client.execute(get);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                strResult = EntityUtils.toString(entity,HTTP.UTF_8);
            }
        } catch (Exception e) {
            System.err.println("查询超时");
        } finally{
            client.getConnectionManager().shutdown();
        }
        return strResult;
    }
}
