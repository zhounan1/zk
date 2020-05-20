package com.aust.listener;/*
 * Copyright © 2016 睿泰集团 版权所有
 */

import com.aust.zk.ServiceRegister;

import javax.imageio.spi.ServiceRegistry;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;
import java.util.Properties;

/**
 * @Autor zhouNan
 * @Date 2019/9/19 11:22
 * @Description InitListener
 **/
public class InitListener  implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            Properties properties = new Properties();
            properties.load(InitListener.class.getClassLoader().getResourceAsStream("application.yml"));
            String  hostAddress = InetAddress.getLocalHost().getHostAddress();
            int port  = Integer.valueOf(properties.getProperty("port"));
            ServiceRegister.register(hostAddress, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
