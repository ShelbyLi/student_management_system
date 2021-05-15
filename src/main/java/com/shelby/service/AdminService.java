package com.shelby.service;

import com.shelby.entity.Admin;

/**
 * @Author Shelby Li
 * @Date 2021/5/13 21:19
 * @Version 1.0
 */

public interface AdminService {
    public Admin queryByUsername(String username);
}
