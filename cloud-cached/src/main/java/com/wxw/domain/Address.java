package com.wxw.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author ：wxw.
 * @ Date ： 17:34 2020/5/28
 * @ Description：地址
 * @ Version:   v_0.0.1$
 */
@Data
@NoArgsConstructor
public class Address {

    private Long addressId;
    private String province;
    private String city;
    private String country;
    private String address;

    public Address(Long addressId, String province, String city, String country, String address) {
        this.addressId = addressId;
        this.province = province;
        this.city = city;
        this.country = country;
        this.address = address;
    }
}
