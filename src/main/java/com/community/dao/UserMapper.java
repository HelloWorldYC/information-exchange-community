package com.community.dao;

import com.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    //按id查询
    User selectById(int id);

    //按名称查询
    User selectByName(String username);
    //按邮箱查询
    User selectByEmail(String email);

    //增加用户
    int insertUser(User user);

    //修改用户状态
    int updateStatus(int id, int status);

    //更新头像，即更新头像路径
    int updateHeaderUrl(int id, String headerUrl);

    //更新密码
    int updatePassword(int id, String password);
}
