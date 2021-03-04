package com.wxw.dao;

import com.wxw.domain.Person;
import com.wxw.domain.PersonExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonMapper {

    int deleteByExample(PersonExample example);

    int insert(Person record);

    int insertSelective(Person record);

    List<Person> selectByExample(PersonExample example);

    int updateByExampleSelective(@Param("record") Person record, @Param("example") PersonExample example);

    int updateByExample(@Param("record") Person record, @Param("example") PersonExample example);

    Person selectById(@Param("personId") Integer personId);
}