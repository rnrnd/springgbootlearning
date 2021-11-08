package com.example.demo.serialize;

import com.example.demo.bean.Student;

import java.io.*;

public class SerializeUtil {

    public static void serialize(Student student) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("student.txt"));
            objectOutputStream.writeObject(student);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==============序列化完成==============");
    }

    public static Student deserialize(File file) {
        Student student = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            student = (Student)objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==============反序列化完成==============");
        return student;
    }

}
