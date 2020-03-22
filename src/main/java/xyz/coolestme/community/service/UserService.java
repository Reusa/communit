package xyz.coolestme.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.coolestme.community.mapper.UserMapper;
import xyz.coolestme.community.model.User;
import xyz.coolestme.community.model.UserExample;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
//        User dbUser = userMapper.selectByExample(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            //插入一条新的用户数据

            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModifiled(user.getGmtCreate());
            System.out.println("insert token:" + user.getToken());
            userMapper.insert(user);
        }else {
            //更新老数据
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModifiled(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser,example);

            //System.out.println(userMapper.findByAcountId(dbUser.getAccountId()).toString());
        }
    }
}
