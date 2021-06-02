package com.shelby.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:46
 * @Version 1.0
 */

@Data
public class Experience implements Serializable {
    @Id
    private int id;
    @Column
    private String content;
    @Column
    private String time;
    @Column
    private int resumeId;
}
