package com.smf.platform.hiber.service.api;

import com.smf.platform.hiber.domain.Person;
import com.smf.platform.service.api.SmfBaseService;

public interface PersonService extends SmfBaseService<Person> {
	
	public void save(Person person);
	
}
