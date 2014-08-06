package com.twistlet.scrapecimb.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class OpenPojoTest {

	private List<PojoClass> pojoList;
	private PojoValidator pojoValidator;

	@Before
	public void init() {
		pojoList = new ArrayList<PojoClass>();
		pojoList.add(PojoClassFactory.getPojoClass(AuctionDate.class));
		pojoList.add(PojoClassFactory.getPojoClass(AuctionDatePrice.class));
		pojoList.add(PojoClassFactory.getPojoClass(AuctionHouse.class));
		pojoValidator = new PojoValidator();
		pojoValidator.addTester(new GetterTester());
		pojoValidator.addTester(new SetterTester());
	}

	@Test
	public void testPojo() {
		for (PojoClass pojoClass : pojoList) {
			pojoValidator.runValidation(pojoClass);
		}
	}
}
