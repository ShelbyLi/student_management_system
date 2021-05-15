package com.shelby.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:31
 * @Version 1.0
 */

@Data
public class Resume {
    @Id
    private Integer id;
    @Column
    private String content;
    @Column
    private long stuId;

    private List<Experience> experienceList;
}
