package org.example;

import java.io.File;

public class Student implements Runnable{
    private Tester tester;
    public String name;
    private String taskNames[];
    private String url;
    public Task[] tasks;



    Student(Tester tester, String name, String taskNames[], String url) {
        this.tester = tester;
        this.name = name;
        this.taskNames = taskNames;
        this.url = url;

        tasks = new Task[taskNames.length];

        for (int i = 0; i < tasks.length; i++) tasks[i] = new Task(name + File.separator + "OOP",
                name + File.separator + "compiled", taskNames[i], tester);
    }

    @Override
    public void run() {

        if (tester.cloneRep(url, name, name + File.separator + "OOP") != 0) return;

        if (!tester.mkdir(name + File.separator + "compiled")) return;

        for (int i = 0; i < tasks.length; i++) {
            tasks[i].test();
            tasks[i].setPoints();
        }

    }


}
