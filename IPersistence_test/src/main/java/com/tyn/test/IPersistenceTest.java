package com.tyn.test;

import com.tyn.dao.IUserDao;
import com.tyn.io.Resources;
import com.tyn.pojo.User;
import com.tyn.sqlSession.SqlSession;
import com.tyn.sqlSession.SqlSessionFactory;
import com.tyn.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() throws Exception{
       InputStream inputStream = Resources.getResourceAsSteam("sqlMapConfig.xml");
       SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
       SqlSession sqlSession = sqlSessionFactory.openSession();

       //调用
        User user = new User();
        user.setId(1);
        User user11 = sqlSession.selectOne("user.selectOne",user);
        System.out.println(user11);

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> list = userDao.findAll();
        for (User user1 : list) {
            System.out.println(user1);
        }
    }
    }


