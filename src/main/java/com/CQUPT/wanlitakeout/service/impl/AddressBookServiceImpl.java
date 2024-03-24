package com.CQUPT.wanlitakeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.CQUPT.wanlitakeout.entity.AddressBook;
import com.CQUPT.wanlitakeout.mapper.AddressBookMapper;
import com.CQUPT.wanlitakeout.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
