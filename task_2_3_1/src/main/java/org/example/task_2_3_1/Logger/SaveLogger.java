package org.example.test2.Logger;

import java.util.ArrayList;
import java.util.List;

public class SaveLogger implements ILogger{
    public List<String> list = new ArrayList<>();

    @Override
    public void print(String str) {
        list.add(str);
    }
}
