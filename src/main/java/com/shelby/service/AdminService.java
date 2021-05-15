package com.shelby.service;

import com.shelby.entity.Admin;

import java.util.Map;

/**
 * @Author Shelby Li
 * @Date 2021/5/13 21:19
 * @Version 1.0
 */

public interface AdminService {
    public Admin queryByName(String username);

    public void insertData(Map<String, String> dataMap);
}
