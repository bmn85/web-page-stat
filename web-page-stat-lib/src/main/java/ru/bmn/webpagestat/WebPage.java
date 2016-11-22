package ru.bmn.webpagestat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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
	private static final Pattern REGEX_LINK_PATTERN = Pattern.compile("<a\\s[^>]*?href=['\"]?(.*?)['\"]?.*?>");

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
				URLConnection con = this.url.openConnection();
				BufferedReader urlContent = new BufferedReader(
					new InputStreamReader(con.getInputStream())
				);
				String line;

				this.internal = new UrlPack();
				this.exteranl = new UrlPack();
				this.badUrls  = new LinkedList<>();

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
		Matcher m = REGEX_LINK_PATTERN.matcher(line);

		while (m.find()) {
			String url = m.group(1);
			try {
				URL urlInfo = new URL(url);
				if (urlInfo.getHost() == this.url.getHost()) {
					this.internal.add(urlInfo);
				}
			}
			catch (MalformedURLException e) {
				this.badUrls.add(url);
			}
		}
	}
}
