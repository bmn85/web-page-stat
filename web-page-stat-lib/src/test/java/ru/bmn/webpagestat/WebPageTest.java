package ru.bmn.webpagestat;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;

import static org.junit.Assert.*;

/**
 *
 */
public class WebPageTest {
	private final static String HTML =
		"<html><body>"
			+ "<a href='http://yandex.ru/somepage.html>somepage</a>"
			+ "<a href='http://yandex.ru/somepage.html>somepage</a>"
			+ "<a href='http://yandex.ru/somepage.html>somepage</a>"
			+ "<a href='http://yandex.ru/somepage.html>somepage</a>"
			+ "<a href='http://yandex.ru/somepage.html>somepage</a>"
			+ "</body></html>";

	@Test
	public void content() throws IOException {
		WebPage page = new WebPage("http://yandex.ru/");

		final URLConnection mockURLConnection = Mockito.mock(URLConnection.class);
		ByteArrayInputStream is = new ByteArrayInputStream(HTML.getBytes("UTF-8"));
		Mockito.doReturn(is).when(mockURLConnection).getInputStream();

		assertTrue(page.urlsCount() == 5);

		assertTrue(page.exteranlUrls().count() == 3);
		assertTrue(page.interanlUrls().count() == 2);

		assertTrue(page.exteranlUrls().hostNames().size() == 2);
		assertTrue(page.interanlUrls().hostNames().size() == 1);
	}

}