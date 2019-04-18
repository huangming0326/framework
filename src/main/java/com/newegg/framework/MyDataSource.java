package com.newegg.framework;

import com.newegg.framework.common.data.access.common.SqlDatabase;

public class MyDataSource {
    private String dataStr;

    public MyDataSource(String data) {
        this.dataStr = data;
        System.out.println("构造函数：" + this.dataStr);
    }

    static {

        System.out.println("语句块：");
    }

    public static void function(){
        SqlDatabase bs = new SqlDatabase("d");
    }

    public static class MySub {

        public static void Finction() {
            SqlDatabase bs = new SqlDatabase("d");

        }
    }
}


