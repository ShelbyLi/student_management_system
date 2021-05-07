package com.shelby.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:31
 * @Version 1.0
 */

@Data
public class Resume {
    private Integer id;
    private String content;
    private long stuId;
    private List<Experience> experienceList;
}
