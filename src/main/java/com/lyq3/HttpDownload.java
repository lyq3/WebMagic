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
 * ����HttpClient�����ļ�
 * @author Administrator
 *
 */
public class HttpDownload {  
	 /** 
     * �����ļ� 
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
        // ����һ��httpclient����  
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
                // ע�����������OutputStream.write(buff)�Ļ���ͼƬ��ʧ�棬��ҿ�������  
            }  
            fout.flush();  
            fout.close();  
        } finally {  
            // �رյͲ�����  
            in.close();  
        }  
        httpclient.close();  
    }  
	  
}  
