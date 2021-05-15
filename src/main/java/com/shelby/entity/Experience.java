package com.shelby.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:46
 * @Version 1.0
 */

@Data
public class Experience {
    @Id
    private int id;
    @Column
    private String content;
    @Column
    private String time;
    @Column
    private int resumeId;
}
