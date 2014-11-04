package com.smf.platform.hiber.service.api;

import com.smf.platform.hiber.domain.Address;
import com.smf.platform.service.api.SmfBaseService;

public interface AddressService extends SmfBaseService<Address> {
	
	public void save(Address address);
	
}
