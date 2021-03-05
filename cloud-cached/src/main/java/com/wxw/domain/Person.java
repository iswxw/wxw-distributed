package com.wxw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    private Integer id;

    private String name;

    private Date birthday;

    private String address;

    private Date updateTime;
}