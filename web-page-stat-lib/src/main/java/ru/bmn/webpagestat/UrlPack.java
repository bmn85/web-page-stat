package ru.bmn.webpagestat;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public final class UrlPack {
	private final Map<String, Set<String>> urlByHost = new HashMap<>();

	public void add(URL url) {
		String host = url.getHost();
		if (!this.urlByHost.containsKey(host)) {
			this.urlByHost.put(host, new HashSet<String>());
		}
		this.urlByHost.get(host).add(url.getPath());
	}

	public int count() {
		return this.urlByHost.size();
	}

	public Set<String> hostNames() {
		return this.urlByHost.keySet();

	}
}
