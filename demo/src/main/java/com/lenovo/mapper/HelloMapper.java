package com.lenovo.mapper;

import com.lenovo.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
@Component(value = "HelloMapper")
public interface HelloMapper {

    public void  addHelloInfo(User user);
}
