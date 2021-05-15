package com.shelby.service.impl;

import com.shelby.entity.Admin;
import com.shelby.mapper.AdminMapper;
import com.shelby.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Shelby Li
 * @Date 2021/5/13 21:20
 * @Version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin queryByName(String name) {
        return adminMapper.queryByName(name);
    }

    @Override
    public void insertData(Map<String, String> dataMap) {
//        Admin admin = new Admin();
//        admin.setName(dataMap.get("name"));
//        admin.setPassword(dataMap.get("password"));
        adminMapper.insertData(dataMap.get("name"), dataMap.get("password"));
    }
}
