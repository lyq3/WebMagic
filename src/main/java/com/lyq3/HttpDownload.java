package com.lyq3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 利用HttpClient下载文件
 * @author Administrator
 *
 */
public class HttpDownload {  
	 /** 
     * 下载文件 
     *  
     * @param url 
     *            http://www.xxx.com/img/333.jpg 
     * @param destFileName 
     *            xxx.jpg/xxx.png/xxx.txt 
     * @throws ClientProtocolException 
     * @throws IOException 
     */  
    public static void getFile(String url, String destFileName)  
            throws ClientProtocolException, IOException {  
        // 生成一个httpclient对象  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        HttpGet httpget = new HttpGet(url);  
        HttpResponse response = httpclient.execute(httpget);  
        HttpEntity entity = response.getEntity();  
        InputStream in = entity.getContent();  
        File file = new File("C:/Users/Administrator/Desktop/test/",destFileName + ".crx");  
        try {  
            FileOutputStream fout = new FileOutputStream(file);  
            int l = -1;  
            byte[] tmp = new byte[1024];  
            while ((l = in.read(tmp)) != -1) {  
                fout.write(tmp, 0, l);  
                // 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试  
            }  
            fout.flush();  
            fout.close();  
        } finally {  
            // 关闭低层流。  
            in.close();  
        }  
        httpclient.close();  
    }  
	  
}  
