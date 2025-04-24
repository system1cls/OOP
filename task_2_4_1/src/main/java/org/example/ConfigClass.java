package org.example;

import groovy.lang.GroovyObjectSupport;
import lombok.Data;



@Data
public class ConfigClass extends GroovyObjectSupport {
    @Data
    public class StudentInfo {
        String name;
        String url;
        StudentInfo(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    @Data
    public class TaskInfo {
        String name;

        TaskInfo(String name) {
            this.name = name;
        }
    }

    String group;
    StudentInfo[] studentsIn;
    TaskInfo[] tasksIn;
}
