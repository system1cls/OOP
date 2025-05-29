javadoc ".\src\main\java\org\example\*.java" -d "D:\Documents\java\OOP-main\task_1_1_2\jdk"
cd "D:\Documents\Java\OOP-main\task_1_1_2_real_2.0\src\main\java"
javac -d jar_dir org/example/*.java
jar --create --file jar_dir/BlackJack.jar --main-class=org.example.Main -C ./jar_dir/ .
java -jar jar_dir/BlackJack.jar