package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    int port = 5005;
    Tests tests = new Tests();
    ILogger logger = new Logger();

    @Test
    public void test1Worker() {
        for(Tests.Test test : tests.tests) {

            Server server = new Server(port, test.arr, 256, logger);
            Thread serverT = new Thread(server);
            serverT.start();

            Worker worker = new Worker("localhost", port, logger, 1);
            Thread workerT = new Thread(worker);
            workerT.start();


            try {
                serverT.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertEquals(server.answer, test.ans);
        }
    }

    @Test
    public void test2Worker() {
        for(Tests.Test test : tests.tests) {
            Server server = new Server(port, test.arr, 2, logger);
            Thread serverT = new Thread(server);
            serverT.start();

            Worker worker1 = new Worker("localhost", port, logger, 1);
            Thread workerT1 = new Thread(worker1);


            Worker worker2 = new Worker("localhost", port, logger, 2);
            Thread workerT2 = new Thread(worker2);

            workerT1.start();
            workerT2.start();

            try {
                serverT.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertEquals(server.answer, test.ans);
        }
    }


    @Test
    public void test3Worker() {
        for(Tests.Test test : tests.tests) {
            Server server = new Server(port, test.arr, 2, logger);
            Thread serverT = new Thread(server);
            serverT.start();

            Worker worker1 = new Worker("localhost", port, logger, 1);
            Thread workerT1 = new Thread(worker1);


            Worker worker2 = new Worker("localhost", port, logger, 2);
            Thread workerT2 = new Thread(worker2);

            Worker worker3 = new Worker("localhost", port, logger, 3);
            Thread workerT3 = new Thread(worker3);

            workerT1.start();
            workerT2.start();
            workerT3.start();

            try {
                serverT.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertEquals(server.answer, test.ans);
        }
    }

    @Test
    public void test4Worker() {
        for(Tests.Test test : tests.tests) {
            Server server = new Server(port, test.arr, 2, logger);
            Thread serverT = new Thread(server);
            serverT.start();

            Worker worker1 = new Worker("localhost", port, logger, 1);
            Thread workerT1 = new Thread(worker1);


            Worker worker2 = new Worker("localhost", port, logger, 2);
            Thread workerT2 = new Thread(worker2);

            Worker worker3 = new Worker("localhost", port, logger, 3);
            Thread workerT3 = new Thread(worker3);

            Worker worker4 = new Worker("localhost", port, logger, 4);
            Thread workerT4 = new Thread(worker4);

            workerT1.start();
            workerT2.start();
            workerT3.start();
            workerT4.start();

            try {
                serverT.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            assertEquals(server.answer, test.ans);
        }
    }
}