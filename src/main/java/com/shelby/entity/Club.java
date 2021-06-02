package com.shelby.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @Author Shelby Li
 * @Date 2021/6/2 18:47
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Club {
    @Id
    protected Integer clubId;
    @Column
    protected String clubName;
    @Column
    protected String info;
}
