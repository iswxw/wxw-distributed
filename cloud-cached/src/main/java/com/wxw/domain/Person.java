package com.wxw.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @ Author ：wxw.
 * @ Date ： 17:28 2020/5/28
 * @ Description：Redis测试的bean
 * @ Version:   v_0.0.1$
 */
@Data
@NoArgsConstructor
public class Person implements Serializable {
    private Long personId;
    private String personName;
    private Integer personAge;
    private Date date;
    private LocalDate localDate;
    private Address address;

    public Person(Long personId, String personName, Integer personAge, Date date, LocalDate localDate, Address address) {
        this.personId = personId;
        this.personName = personName;
        this.personAge = personAge;
        this.date = date;
        this.localDate = localDate;
        this.address = address;
    }
    public Person(Long personId, String personName, Integer personAge, Date date, Address address) {
        this.personId = personId;
        this.personName = personName;
        this.personAge = personAge;
        this.date = date;
        this.address = address;
    }

}
