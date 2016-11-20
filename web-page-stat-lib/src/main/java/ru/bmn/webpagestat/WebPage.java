package ru.bmn.webpagestat;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
public class WebPage {
	private final String url;
	private UrlPack internal;
	private UrlPack exteranl;
	private boolean isFetched = false;

	public WebPage(String url) {
		if (url == null) {
			throw new IllegalArgumentException("URL expected!");
		}
		this.url = url;
	}


	public int urlsCount() {

		return 0;
	}

	public UrlPack exteranlUrls() {
		return null;
	}

	public UrlPack interanlUrls() {
		return null;
	}

	private void fetch() {
		if (!this.isFetched) {
			try {
				URL url = new URL(this.url);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			finally {
				this.isFetched = true;
			}
		}
	}
}
