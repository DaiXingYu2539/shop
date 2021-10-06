package com.qf.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.qf.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressMapper  extends BaseMapper<Address> {
    void addAddress(Address address);
}
