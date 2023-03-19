package org.epha.com.labprint.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从网络中获取资源
 * @author pangjiping
 */
@Deprecated
public class HttpClientUtil {

    /**
     * 从指定url中下载资源以供打印
     *
     * @param url 资源url
     * @return 响应体数据
     * @throws Exception
     */
    public static String doGet(String url) throws Exception {
        // 创建httpclient链接
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        // 获取响应数据
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * 判断一个字符串是不是url
     * @param url 待检验的url
     * @return true-是url
     */
    public static boolean isHttpUrl(String url) {
        boolean isUrl = false;

        if (null==url){
            return false;
        }

        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                +
                "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";

        Pattern pattern = Pattern.compile(regex.trim());
        Matcher matcher = pattern.matcher(url.trim());
        isUrl = matcher.matches();
        return isUrl;
    }
}
