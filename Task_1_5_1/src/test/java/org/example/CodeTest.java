package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.markDown.Code;
import org.example.markDown.Text;
import org.junit.jupiter.api.Test;

class CodeTest {
    @Test
    void print1() {
        Code.CodeBuilder builder = new Code.CodeBuilder();
        builder = builder.addString("#include \"stdlib.h\"\n"
                + "#include \"stdio.h\"\n"
                + "#include \"string.h\"\n"
                + "#include \"stddef.h\"\n"
                + "#include \"unistd.h\"\n"
                + "#include <ctype.h>\n"
                + "\n"
                + "#include <sys/socket.h>\n"
                + "#include <sys/types.h> \n"
                + "#include <sys/un.h>\n"
                + "#include \"sys/syscall.h\"\n"
                + "\n"
                + "\n"
                + "\n"
                + "int main() {\n"
                + "\tint socket_fd = 0;\n"
                + "\tstruct sockaddr_un address;\n"
                + "\tsocklen_t addrlen = sizeof(address);\n"
                + "\tint cntOfBytesGetted = 0;\n"
                + "\tchar str[1024], path[] = \"/tmp/lab31_socket\";\n"
                + "\n"
                + "\n"
                + "\n"
                + "\n"
                + "\tsocket_fd = socket(AF_UNIX, SOCK_STREAM, 0);\n"
                + "\tif (socket_fd == -1) {\n"
                + "\t\tperror(\"socket error\");\n"
                + "\t\texit(1);\n"
                + "\t}\n"
                + "\n"
                + "\taddress.sun_family = AF_UNIX;\n"
                + "\tstrcpy(address.sun_path, path);\n"
                + "\n"
                + "\tif (connect(socket_fd, (struct sockaddr*)&address, addrlen) == -1) {\n"
                + "\t\tperror(\"connect error\");\n"
                + "\t\texit(1);\n"
                + "\t}\n"
                + "\n"
                + "\tfor (;;) {\n"
                + "\t\tcntOfBytesGetted = read(0, str, 1023);\n"
                + "\t\tif (cntOfBytesGetted == -1) {\n"
                + "\t\t\tperror(\"read error\");\n"
                + "\t\t\texit(1);\n"
                + "\t\t}\n"
                + "\n"
                + "\t\tif (cntOfBytesGetted <= 0) {\n"
                + "\t\t\tbreak;\n"
                + "\t\t}\n"
                + "\n"
                + "\t\tif (write(socket_fd, str, cntOfBytesGetted) == -1) {\n"
                + "\t\t\tperror(\"write error\");\n"
                + "\t\t\texit(1);\n"
                + "\t\t}\n"
                + "\t}\n"
                + "\n"
                + "\n"
                + "\tif (close(socket_fd) == -1) {\n"
                + "\t\tperror(\"close error\");\n"
                + "\t\texit(1);\n"
                + "\t}\n"
                + "\texit(0);\n"
                + "}");
        System.out.print(builder.build());
    }

    @Test
    void print2() {
        Code.CodeBuilder builder = new Code.CodeBuilder();
        builder = builder.addString(new Text.TextBuilder()
                .setText("print(\"Hello World\");").build());
        Code.CodeBuilder builder1 = new Code.CodeBuilder().addString("print(\"Hello World\");");
        assertTrue(builder1.build().equals(builder.build()));
    }

    @Test
    void toStringTest() {
        Code.CodeBuilder builder = new Code.CodeBuilder();
        builder = builder.addString(new Text.TextBuilder()
                .setText("print(\"Hello World\");").build());
        String answer = "```\nprint(\"Hello World\");\n```\n";
        assertEquals(builder.build().toString(), answer);
    }

}