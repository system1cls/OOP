package org.example;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;

public class Manager {

    public void test(String pathToConfig) {
        ConfigClass conf = getConf(pathToConfig);
        String curDir = System.getProperty("user.dir");
        Tester tester = new Tester(curDir + File.separator + "junit-platform-console-standalone-1.12.2.jar",
                curDir + File.separator + "checkstyle-10.23.0-all.jar",
                curDir + File.separator + "google_checks.xml");

        Student[] students = new Student[conf.studentsIn.length];
        String[] tasksNames = new String[conf.tasksIn.length];
        for (int i = 0; i < tasksNames.length; i++) {
            tasksNames[i] = new String(conf.tasksIn[i].name);
        }


        for (int i = 0; i < conf.studentsIn.length; i++) {
            students[i] = new Student(tester, conf.studentsIn[i].name, tasksNames, conf.studentsIn[i].url);
        }


        Thread threads[] = new Thread[students.length];
        for (int i = 0; i < students.length; i++) {
            threads[i] = new Thread(students[i]);
            threads[i].start();
        }

        for (int i = 0; i < students.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException();
            }
        }

        System.out.println(PrintInfo.getHtmlString(students));
    }


    private ConfigClass getConf(String pathToConfig) {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(Manager.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = (DelegatingScript) new sh.parse(new File(pathToConfig));
        ConfigClass conf = new ConfigClass();
        script.setDelegate(conf);

        script.run();

        return conf;
    }

}
