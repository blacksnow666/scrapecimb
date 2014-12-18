package com.twistlet.scrapecimb.model.service;

import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.stereotype.Service;

@Service
public class DateFormatServiceImpl implements DateFormatService {

	@Override
	public String format(final Date date) {
		final FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd");
		return df.format(date);
	}

}
