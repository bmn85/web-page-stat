package ru.bmn.webpagestat;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

import static org.junit.Assert.*;

/**
 *
 */
public class WebPageTest {
	private final static String HTML =
		"<html><body>"
			+ "<a href='http://yandex.ru/somepage1.html>somepage1</a>"
			+ "<a href='http://yandex.ru/somepage2.html>somepage2</a>"
			+ "<a href='http://rambler.ru/somepage3.html>somepage3</a>"
			+ "<a href='http://google.ru/somepage4.html>somepage4</a>"
			+ "<a href='http://google.ru/somepage5.html>somepage5</a>"
			+ "</body></html>";

	@Test
	public void content() throws IOException {
		WebPage page = new WebPage("http://yandex.ru/");

		URLConnection mockURLConnection = Mockito.mock(URLConnection.class);
		ByteArrayInputStream is = new ByteArrayInputStream(HTML.getBytes("UTF-8"));
		Mockito
			.when(mockURLConnection.getInputStream())
			.thenReturn(is);

		assertEquals("urls count", 5, page.urlsCount());

		assertEquals("external urls count", 3, page.exteranlUrls().count());
		assertEquals("internal urls count", 2, page.internalUrls().count());

		assertEquals("external host names count", 2, page.exteranlUrls().hostNames().size());
		assertEquals("internal host names count", 1, page.internalUrls().hostNames().size());
	}

}