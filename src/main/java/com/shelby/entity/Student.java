package com.shelby.entity;

import lombok.Data;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:31
 * @Version 1.0
 */

@Data
public class Student {
    private int id;
    private String name;
    private int age;
    private Resume resume;
}
