cd "C:\Program Files\Java\jdk-21\bin"
./javadoc "D:\Documents\java\OOP-main\task_1_1_2_real_2.0\src\main\java\org\example\Main.java" "D:\Documents\java\OOP-main\task_1_1_2_real_2.0\src\main\java\org\example\BlackJack.java" "D:\Documents\Java\OOP-main\task_1_1_2_real_2.0\src\main\java\org\example\Card.java" "D:\Documents\Java\OOP-main\task_1_1_2_real_2.0\src\main\java\org\example\Deck.java" "D:\Documents\Java\OOP-main\task_1_1_2_real_2.0\src\main\java\org\example\Hand.java"   -d "D:\Documents\java\OOP-main\task_1_1_2\jdk"
cd "D:\Documents\Java\OOP-main\task_1_1_2_real_2.0\src\main\java\org\example"
javac BlackJack.java Main.java Hand.java Deck.java Card.java
java Main.java