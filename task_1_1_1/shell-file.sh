cd "C:\Program Files\Java\jdk-21\bin"
./javadoc "D:\Documents\java\OOP-main\task_1_1_2\src\main\java\org\example\Main.java" "D:\Documents\java\OOP-main\task_1_1_2\src\main\java\org\example\HeapSort.java" -d "D:\Documents\java\OOP-main\task_1_1_2\jdk"
cd "D:\Documents\Java\OOP-main\task_1_1_2\src\main\java\org\example"
javac HeapSort.java Main.java
java Main.java HeapSort.java
