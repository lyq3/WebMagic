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
 * 爬取 https://www.themebeta.com/所有网站内容
 * @author Administrator
 *
 */
public class ThemeBeta implements PageProcessor{
	private static Logger log = Logger.getLogger(ThemeBeta.class); 
	 // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
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
				System.err.println("异常");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("异常");
				e.printStackTrace();
			}
		}
		page.addTargetRequests(page.getHtml().links().regex("(https://www\\.themebeta\\.com/.*)").all());
		 // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl()
        		.regex("https://www\\.themebeta\\.com/chrome/.*").toString());
        page.putField("name", page.getHtml()
        		.xpath("//div[@class='panel-body']/h1/text()").toString());
        if (page.getResultItems().get("name") == null) {
            //skip this page
            page.setSkip(true);
        } else {
        	
        	log.error("地址："+page.getUrl().toString() + "\n");
        	log.error("名称：" + page.getResultItems().get("name"));
        }
       // page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        
	}

	public Site getSite() {
		return site;
	}
	
	
	 /**
	  * 启动测试
	 * @param args
	 */
	public static void main(String[] args) {

	        Spider.create(new ThemeBeta())
	                //从"https://github.com/code4craft"开始抓
	                .addUrl("https://www.themebeta.com/chrome/")
	                //开启5个线程抓取
	                .thread(10)
	                //启动爬虫
	                .run();
	    }

}
