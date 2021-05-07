package com.shelby.entity;

import lombok.Data;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:46
 * @Version 1.0
 */

@Data
public class Experience {
    private int id;
    private String content;
    private String time;
    private int resumeId;
}
