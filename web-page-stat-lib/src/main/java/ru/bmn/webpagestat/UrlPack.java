package ru.bmn.webpagestat;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class UrlPack {
	private Map<String, Set<String>> urlByHost = null;

	public void add(URL url) {
		if (this.urlByHost == null) {
			this.urlByHost = new HashMap<>();
		}
	}

	public int count() {
		return 0;
	}

	public Set<String> hostNames() {
		return null;

	}
}
