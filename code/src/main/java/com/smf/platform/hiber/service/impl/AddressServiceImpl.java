package com.smf.platform.hiber.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.smf.platform.hiber.domain.Address;
import com.smf.platform.hiber.service.api.AddressService;
import com.smf.platform.service.impl.SmfBaseServiceImpl;

@SuppressWarnings("unchecked")
@Component("addressService")
public class AddressServiceImpl extends SmfBaseServiceImpl<Address> implements AddressService {

	@Override
	public void update(Address obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public Address get(Serializable id) {
		Address address = (Address)super.dao.get(Address.class, id);
		return address;
	}

	@Override
	public void removeById(Serializable id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Address obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAll(List<Address> datas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAll(List<Address> datas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAll(List<Address> datas) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object mergeOrUpdate(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Address address) {
		super.dao.save(address);

	}

	@Override
	public List query(String queryStr) {
//		String sql = "select h from Address h where h."
		return super.query(queryStr);
	}
	
	

}
