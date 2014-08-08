package com.twistlet.scrapecimb.model.service;

import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MultiplePopulateServiceTest {

	private MultiplePopulateService sut;

	@Mock
	private PopulateService service1;
	@Mock
	private PopulateService service2;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		sut = new MultiplePopulateService(Arrays.asList(service1, service2));
	}

	@Test
	public void testPopulate() {
		sut.populate();
		verify(service1).populate();
		verify(service2).populate();
		verifyNoMoreInteractions(service1, service2);
	}

}
