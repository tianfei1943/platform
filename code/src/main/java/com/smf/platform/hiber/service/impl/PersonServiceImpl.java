package com.smf.platform.hiber.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.hiber.domain.Person;
import com.smf.platform.hiber.service.api.PersonService;
import com.smf.platform.service.impl.SmfBaseServiceImpl;

@SuppressWarnings("unchecked")
@Component("personService")
public class PersonServiceImpl extends SmfBaseServiceImpl<Person> implements PersonService {

	@Override
	public void update(Person obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public Person get(Serializable id) {
		Person person = (Person)super.dao.get(Person.class, id);
		return person;
	}

	@Override
	public void removeById(Serializable id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Person obj) {
		// TODO Auto-generated method stub

	}


	@Override
	public void saveAll(List<Person> datas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAll(List<Person> datas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAll(List<Person> datas) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object mergeOrUpdate(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Person person) {
		super.dao.save(person);
	}

}
