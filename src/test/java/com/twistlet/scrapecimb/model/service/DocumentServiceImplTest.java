package com.twistlet.scrapecimb.model.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.io.ClassPathResource;

import com.twistlet.scrapecimb.model.service.DocumentServiceImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class DocumentServiceImplTest {

	@Test
	public void testGet() throws IOException, URISyntaxException {
		Document document1 = Jsoup.parse(IOUtils
				.toString(new ClassPathResource("html/simple.html")
						.getInputStream()));
		PowerMockito.mockStatic(Jsoup.class);
		Connection connection1 = mock(Connection.class);
		when(Jsoup.connect("http://www.google.com.my/"))
				.thenReturn(connection1);
		when(connection1.get()).thenReturn(document1);
		DocumentServiceImpl sut = new DocumentServiceImpl();
		Document document = sut.get(new URI("http://www.google.com.my/")
				.toString());
		assertEquals("Simple HTML5", document.title());
	}

	@Test
	public void testGetIOException() throws IOException, URISyntaxException {
		PowerMockito.mockStatic(Jsoup.class);
		Connection connection1 = mock(Connection.class);
		when(Jsoup.connect("http://www.google.com.my/"))
				.thenReturn(connection1);
		when(connection1.get()).thenThrow(new IOException());
		DocumentServiceImpl sut = new DocumentServiceImpl();
		Document document = sut.get(new URI("http://www.google.com.my/").toString());
		assertNull(document);
	}

}
