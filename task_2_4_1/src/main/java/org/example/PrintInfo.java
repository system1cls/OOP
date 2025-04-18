package org.example;

public class PrintInfo {

    static public String getHtmlString(Student[] students) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < students.length; i++) {
            builder.append(students[i].name).append("\n");
            builder.append(print(students[i].tasks));
        }

        return builder.toString();
    }

    static private String print(Task tasks[]) {
        StringBuilder builder = new StringBuilder();
        builder.append("<table style=\"width:100%\">\n");
        printStart(builder);
        int sum = 0;

        for (int i = 0; i < tasks.length; i++) {
            printTask(builder, tasks[i]);
            sum += tasks[i].points;
        }

        printSumPoints(builder, sum);

        builder.append("</table>\n");

        return builder.toString();
    }

    static private void printStart(StringBuilder builder) {
        builder.append("\t<tr>\n");
        builder.append("\t\t<td>Name</td>\n");
        builder.append("\t\t<td>").append("Compiled").append("</td>\n");
        builder.append("\t\t<td>").append("javadoc").append("</td>\n");
        builder.append("\t\t<td>").append("Google style").append("</td>\n");
        builder.append("\t\t<td>").append("tests").append("</td>\n");
        builder.append("\t\t<td>").append("points").append("</td>\n");
        builder.append("\t</tr>\n");
    }

    static private  void printTask(StringBuilder builder, Task task) {
        builder.append("\t<tr>\n");

        printOpt(task.compiled, builder);
        printOpt(task.documented, builder);
        printOpt(task.styled, builder);

        builder.append("\t\t<td>");
        builder.append(task.cntSucTests).append("/").append(task.cntFailTests);
        builder.append("</td>\n");

        builder.append("\t\t<td>").append(task.points).append("</td>\n");

        builder.append("\t</tr>\n");
    }

    static private void printOpt(boolean opt, StringBuilder builder) {
        if (opt) builder.append("\t\t<td>").append("+").append("</td>\n");
        else builder.append("\t\t<td>").append("-").append("</td>\n");
    }

    static private void printSumPoints(StringBuilder builder, int points) {
        builder.append("\t<tr>\n");
        builder.append("\t\t<td>Sum</td>\n");

        for (int i = 0; i < 4; i++) builder.append("\t\t<td></td>\n");

        builder.append("\t\t<td>").append(points).append("</td>\n");

        builder.append("\t</tr>\n");

    }
}
