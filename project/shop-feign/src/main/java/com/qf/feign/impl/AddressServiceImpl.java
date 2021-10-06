package com.qf.feign.impl;

import com.qf.entity.Address;
import com.qf.feign.api.IAddressService;

import java.util.List;

public class AddressServiceImpl implements IAddressService {
    @Override
    public List<Address> getAddressListByUid(Integer uid) {
        return null;
    }

    @Override
    public Address getAddressById(Integer addressId) {
        return null;
    }
}
