cd "C:\Program Files\Java\jdk-21\bin"
./javadoc "D:\Documents\java\OOP-main\task_1_1_2\src\main\java\org\example\Main.java" "D:\Documents\java\OOP-main\task_1_1_2\src\main\java\org\example\HeapSort.java" -d "D:\Documents\java\OOP-main\task_1_1_2\jdk"
cd "D:\Documents\Java\OOP-main\task_1_1_2\src\main\java\org\example"
cd "D:\Documents\Java\OOP-main\task_1_1_2\src\main\java"
javac -d jar_dir org/example/Main.java org/example/HeapSort.java
jar --create --file jar_dir/JarSort.jar --main-class=org.example.Main -C ./jar_dir/ .
java -jar jar_dir/JarSort.jar
