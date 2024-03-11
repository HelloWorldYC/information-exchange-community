package com.community;

import com.community.dao.LoginTicketMapper;
import com.community.dao.UserMapper;
import com.community.entity.LoginTicket;
import com.community.entity.User;
import com.community.util.CommunityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class TestMappers {
    @Autowired
    UserMapper userMapper;

    @Autowired
    LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelect(){
        User user = userMapper.selectById(120);
        System.out.println(user);

        user = userMapper.selectByName("jjj");
        System.out.println(user);

        user = userMapper.selectByEmail("nowcoder120@sina.com");
        System.out.println(user);
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("abc");
        user.setEmail("hello");
        user.setType(0);
        user.setStatus(1);
        user.setHeaderUrl("heello.com");
        user.setCreateTime(new Date());
        int count = userMapper.insertUser(user);
        System.out.println(count);
        System.out.println(user.getId());
    }

    @Test
    public void testUpdate(){
        userMapper.updateHeaderUrl(150, "helloYC.com");
        userMapper.updatePassword(150, "qwert");
        userMapper.updateStatus(150,0);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(2);
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 3600 * 24));
        loginTicketMapper.insertLoginTicket(loginTicket);

    }

    @Test
    public void testUpdateLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("50e52fcd1a69449f8655c2bb0c4a2cfa");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("50e52fcd1a69449f8655c2bb0c4a2cfa",1);

        loginTicket = loginTicketMapper.selectByTicket("50e52fcd1a69449f8655c2bb0c4a2cfa");
        System.out.println(loginTicket);
    }
}
