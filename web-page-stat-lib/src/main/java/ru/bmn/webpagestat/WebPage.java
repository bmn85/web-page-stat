package ru.bmn.webpagestat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class WebPage {
	private final URL url;
	private UrlPack internal;
	private UrlPack exteranl;
	private List<String> badUrls = null;
	private boolean isFetched = false;

	public WebPage(String url) {
		try {
			this.url = new URL(url);
		}
		catch (MalformedURLException e) {
			throw new IllegalArgumentException("Malformed url value");
		}
	}


	public int urlsCount() {
		this.fetch();
		return this.internal.count() + this.exteranl.count();
	}

	public UrlPack exteranlUrls() {
		this.fetch();
		return this.exteranl;
	}

	public UrlPack internalUrls() {
		this.fetch();
		return this.internal;
	}

	private void fetch() {
		if (!this.isFetched) {
			try {
				URLConnection con = url.openConnection();
				BufferedReader urlContent = new BufferedReader(
					new InputStreamReader(con.getInputStream())
				);
				String line;
				while ((line = urlContent.readLine()) != null) {
					this.processHtmlLine(line);
				}
				urlContent.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				this.isFetched = true;
			}
		}
	}

	private void processHtmlLine(String line) {
		Pattern p = Pattern.compile("<a\\s[^>]*?href=['\"]?(.*?)['\"]?.*?>");
		Matcher m = p.matcher(line);

		while (m.find()) {
			String url = m.group(1);
			try {
				URL urlInfo = new URL(url);
				String host = urlInfo.getHost();
				if (urlInfo.getHost() == this.url.getHost()) {
					this.internal.add(urlInfo);
				}
			}
			catch (MalformedURLException e) {
				if (this.badUrls == null) {
					this.badUrls = new LinkedList<String>();
				}
				this.badUrls.add(url);
			}
		}
	}
}
