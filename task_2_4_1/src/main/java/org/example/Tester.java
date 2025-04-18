package org.example;
import java.io.*;

public class Tester {
    private final String pathToJunit;
    private final String pathToCheck;
    private final String pathToXml;

    Tester(String pathToJunit, String pathToCheck, String pathToXml) {
        this.pathToJunit = pathToJunit;
        this.pathToCheck = pathToCheck;
        this.pathToXml = pathToXml;
    }

    public int cloneRep(String url, String name, String pathStart) {


        if (!mkdir(name)) return 1;

        if (!clone(url, name, pathStart)) return 2;

        return 0;
    }

    public boolean mkdir(String name) {
        try {
            Process p = Runtime.getRuntime().exec(new String[] {"mkdir", name});
            p.waitFor();

            if (p.exitValue() != 0) {
                return false;
            }

        } catch (IOException e) {
            System.out.println("mkdir error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("mkdir waitFor error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return true;
    }

    private boolean clone(String url, String name, String pathStart) {
        try {
            Process p = Runtime.getRuntime().exec(new String[] {"git", "clone", url, pathStart + File.separator + name});
            p.waitFor();

            if (p.exitValue() != 0) {
                return false;
            }
        } catch(IOException e) {
            System.out.println("clone error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("clone waitFor error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return true;
    }

    public int compileFiles(String branchName, String folderForClasses, String pathStart) {

        if (!changeBranch(branchName)) return 1;

        if (!compile(folderForClasses, pathStart)) return 2;

        return 0;
    }

    private boolean changeBranch(String branchName) {
        try {

            Process p = Runtime.getRuntime().exec(new String[]{"git", "checkout", branchName});
            p.waitFor();


            if (p.exitValue() != 0) {
                return false;
            }
        }catch(IOException e) {
            System.out.println("javac error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("javac waitFor error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return true;
    }

    private boolean compile(String folderForClasses, String pathStart) {
        try {

            String foldOfJavaFiles = getJavaFilesFolder(pathStart);

            Process p = Runtime.getRuntime().exec(new String[]{"javac", "-d",
                   folderForClasses, pathStart + File.separator + foldOfJavaFiles + "*.java"});
            p.waitFor();


            if (p.exitValue() != 0) {
                return false;
            }
        }catch(IOException e) {
            System.out.println("javac error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("javac waitFor error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return true;
    }

    private String getJavaFilesFolder(String pathStart) {
        return pathStart + File.separator + "src" + File.separator + "main" +
                File.separator + "java" + File.separator + "org" + File.separator + "example" + File.separator;
    }

    public int checkJavadoc(String pathStart) {
        try {
            String javaFolder = getJavaFilesFolder(pathStart);

            Process p = Runtime.getRuntime().exec(new String[]{"javadoc", javaFolder + "*.java"});
            p.waitFor();


            if (p.exitValue() != 0) {
                return 1;
            }
        }catch(IOException e) {
            System.out.println("javac error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("javac waitFor error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return 0;
    }

    public int checkStyle(String pathStart) {
        try {
            String javaFolder = getJavaFilesFolder(pathStart);

            Process p = Runtime.getRuntime().exec(new String[]{"java", "-jar", pathToCheck, "-c",
            pathToXml, javaFolder + "*.java"});
            p.waitFor();

            if (p.exitValue() != 0) {
                return -1;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str;
            while((str = reader.readLine()) != null) {
                if (str.contains("Checkstyle ends with")) {
                    return getNumInStr(str);
                }
            }

            return -2;
        }catch(IOException e) {
            System.out.println("checkstyle error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("checkstyle waitFor error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public class TestResult {
        int cntSuc;
        int cntFail;

        TestResult(int cntSuc, int cntFail) {
            this.cntSuc = cntSuc;
            this.cntFail = cntFail;
        }
    }

    public TestResult test(String folderForClasses, String pathStart) {
        if (!compileTests(folderForClasses, pathStart)) return new TestResult(0, 0);

        try {
            Process p = Runtime.getRuntime().exec(new String[] {"java", "-jar", pathToJunit, "--class-path",
                    pathStart + File.separator + folderForClasses, "--scan-class-path"});
            p.waitFor();

            if (p.exitValue() != 0) return new TestResult(0, 0);

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int suc = 0;
            int fail = 0;
            String str;
            while((str = reader.readLine()) != null) {
                if (str.contains("tests successful")) {
                    suc = getNumInStr(str);
                }
                if (str.contains("tests failed")) {
                    fail = getNumInStr(str);
                }
            }


            return new TestResult(suc, fail);
        }  catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        } catch (InterruptedException e) {
            System.out.println("tests interrupted");
            throw new RuntimeException();
        }

    }


    private boolean compileTests(String folderForClasses, String pathStart) {
        try {
            String javaFolder = getJavaTestFilesFolder(pathStart);

            Process p = Runtime.getRuntime().exec(new String[]{"javac", "-d", folderForClasses,
                    "-cp", pathToJunit,  pathStart + File.separator + javaFolder + "*.java"});
            p.waitFor();


            if (p.exitValue() != 0) {
                return false;
            }

        }catch(IOException e) {
            System.out.println("compile test error: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("compile test waitFor error: " + e.getMessage());
            throw new RuntimeException(e);
        }



        return true;
    }

    private String getJavaTestFilesFolder(String pathStart) {
        return pathStart + File.separator + "src" + File.separator + "test" +
                File.separator + "java" + File.separator + "org" + File.separator + "example" + File.separator;
    }

    private int getNumInStr(String str) {
        char[] arr = str.toCharArray();
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(arr[i])) {
                while(Character.isDigit(arr[i])) builder.append(arr[i]);

                return Integer.parseInt(builder.toString());
            }
        }

        return -1;
    }
}
