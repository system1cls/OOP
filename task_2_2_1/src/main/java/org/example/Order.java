package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

/**
 * Method for orders.
 */
public class Order {
    @JsonIgnore int timeCook;
    @JsonProperty("cntPizza")
    int cntPizza;
    @JsonProperty("timeToDel")
    int timeToDel;
    @JsonProperty("timeStart")
    int timeOfOrder;
    @JsonIgnore int number;

    /**
     * Simple constructor.
     * Needed for Jackson.
     */
    Order() {}

    /**
     * Create random order.
     *
     * @param number number of order.
     */
    Order(int number) {
        this.number = number;
        Random random = new Random();

        this.cntPizza = 1 + random.nextInt(6);
        this.timeCook = cntPizza * 15;
        this.timeToDel = 20 + random.nextInt(60);
        this.timeOfOrder = 480 + random.nextInt(600);
    }

    /**
     * @param cntPizza count of pizza
     * @param timeStart time of adding order ti list of orders to complete
     * @param timeToDel time for delivering order
     * @param number number of order
     */
    Order(int cntPizza, int timeStart, int timeToDel, int number) {
        this.cntPizza = cntPizza;
        this.timeCook = cntPizza * 15;
        this.timeToDel = timeToDel;
        this.timeOfOrder = timeStart;
        this.number = number;
    }

    /**
     * Update data with new number.
     *
     * @param number number
     */
    public void update(int number) {
        this.timeCook = this.cntPizza * 15;
        this.number = number;
    }

    /**
     * Method for making string representation.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("num = ").append(number).append("\ncnt = ").append(cntPizza).append("\ntimeToDel = ").append(timeToDel)
                .append("\ntimeOfOrder = ").append(Timer.timeToString(timeOfOrder)).append("\n\n");
        return builder.toString();
    }
}
