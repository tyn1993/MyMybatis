package com.tyn.io;

import java.io.InputStream;

public class Resources {

    //根据配置文件的路径，将配置文件加载成字节输入流，存储再内存中
    public static InputStream getResourceAsSteam(String path){
        InputStream resourceAsStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }

}
