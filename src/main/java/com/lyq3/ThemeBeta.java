package com.lyq3;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * ��ȡ https://www.themebeta.com/������վ����
 * @author Administrator
 *
 */
public class ThemeBeta implements PageProcessor{
	private static Logger log = Logger.getLogger(ThemeBeta.class); 
	 // ����һ��ץȡ��վ��������ã��������롢ץȡ��������Դ�����
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);
	
	public void process(Page page) {
//		Selectable fla = page.getHtml().links().regex("(https://www\\.themebeta\\.com/files/chrome/theme/.*/.*crx)");
//		System.err.println(fla.match());
		boolean flg = page.getUrl().toString().matches("(https://www\\.themebeta\\.com/files/chrome/theme/.*/.*crx)");
		System.err.println(flg);
		if(flg) {
			System.err.println("==========");
			try {
				String url=page.getUrl().toString();
				System.err.println(url);
				System.err.println(url.lastIndexOf("/"));
				System.err.println(url.lastIndexOf("."));
				System.err.println(url.substring(url.lastIndexOf("/")+1, url.lastIndexOf(".")));
				HttpDownload.getFile(url, url.substring(url.lastIndexOf("/")+1, url.lastIndexOf(".")));
			} catch (ClientProtocolException e) {
				System.err.println("�쳣");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("�쳣");
				e.printStackTrace();
			}
		}
		page.addTargetRequests(page.getHtml().links().regex("(https://www\\.themebeta\\.com/.*)").all());
		 // ���ֶ���������γ�ȡҳ����Ϣ������������
        page.putField("author", page.getUrl()
        		.regex("https://www\\.themebeta\\.com/chrome/.*").toString());
        page.putField("name", page.getHtml()
        		.xpath("//div[@class='panel-body']/h1/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        } else {
        	
        	log.error("��ַ��"+page.getUrl().toString() + "\n");
        	log.error("���ƣ�" + page.getResultItems().get("name"));
        }
       // page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // ����������ҳ�淢�ֺ�����url��ַ��ץȡ
        
	}

	public Site getSite() {
		return site;
	}
	
	
	 /**
	  * ��������
	 * @param args
	 */
	public static void main(String[] args) {

	        Spider.create(new ThemeBeta())
	                //��"https://github.com/code4craft"��ʼץ
	                .addUrl("https://www.themebeta.com/chrome/")
	                //����5���߳�ץȡ
	                .thread(10)
	                //��������
	                .run();
	    }

}
