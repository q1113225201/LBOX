package com.sjl.lbox.app.DesignPattern.Builder;

/**
 * MyData
 *
 * @author SJL
 * @date 2017/1/4
 */

public class MyData {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //建造者
    public static class MyBuilder {
        private int id;
        private String name;

        public MyData build() {
            MyData myData = new MyData();
            myData.setId(id);
            myData.setName(name);
            return myData;
        }

        public MyBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public MyBuilder setName(String name) {
            this.name = name;
            return this;
        }
    }
}
