package com.shelby.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:31
 * @Version 1.0
 */

@Data
public class Student {
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private int age;

    private Resume resume;
}
