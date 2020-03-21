package xyz.coolestme.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.coolestme.community.mapper.UserMapper;
import xyz.coolestme.community.model.User;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAcountId(user.getAccountId());
        if (dbUser == null){
            //插入一条新的用户数据

            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println("insert token:" + user.getToken());
            userMapper.insert(user);
        }else {
            //更新老数据
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            System.out.println("update token:" + user.getToken());
            userMapper.update(dbUser);

            //System.out.println(userMapper.findByAcountId(dbUser.getAccountId()).toString());
        }
    }
}
