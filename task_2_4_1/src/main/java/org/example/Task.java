package org.example;

import java.io.File;

public class Task {
    public boolean compiled = false;
    public boolean documented = false;
    public boolean styled = false;
    public int cntSucTests = 0;
    public int cntFailTests = 0;
    public int points;


    private final String pathOfStart;
    private final String pathForCompiledDirs;
    public final String name;
    private final String pathForClasses;

    private Tester tester;

    Task(String path, String pathForCompiled, String name, Tester tester) {
        this.pathOfStart = path;
        this.pathForCompiledDirs = pathForCompiled;
        this.name = name;
        this.tester = tester;
        this.pathForClasses = pathForCompiledDirs + name;
    }

    public void test() {
        if (!tester.mkdir(pathForCompiledDirs + File.separator + name)) return;

        if (tester.compileFiles(name, pathForClasses, pathOfStart + File.separator + name) != 0) return;
        else compiled = true;

        if (tester.checkJavadoc(pathOfStart + File.separator + name) == 0) documented = true;

        if (tester.checkStyle(pathOfStart + File.separator + name) == 0) styled = true;

        Tester.TestResult result = tester.test(pathForClasses, pathOfStart + File.separator + name);
        cntFailTests = result.cntFail;
        cntSucTests = result.cntSuc;
    }

    public void setPoints() {
        if (compiled && (documented || styled) && cntSucTests > 4 && cntSucTests > cntFailTests * 2) points = 1;
        else if (compiled && (documented || styled) && cntSucTests > 4 && cntFailTests == 0) points = 2;
        else points = 0;
    }
}
