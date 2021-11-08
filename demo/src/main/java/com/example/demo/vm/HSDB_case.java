package com.example.demo.vm;

public class HSDB_case {

    public static void main(String[] args) throws InterruptedException {
        Teacher t1 = new Teacher();
        t1.setName("Li Lei");
        for (int i = 0; i < 15; i++) {
            System.gc();
        }
        Teacher t2 = new Teacher();
        t2.setName("Han Meimei");
        Thread.sleep(Integer.MAX_VALUE);
    }

    static class Teacher{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
