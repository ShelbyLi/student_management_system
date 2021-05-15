package com.shelby.mapper;

import com.shelby.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Shelby Li
 * @Date 2021/5/13 21:24
 * @Version 1.0
 */

@Mapper
public interface AdminMapper {
    public Admin queryByName(String name);

    public void insertData(String name, String password);
}
