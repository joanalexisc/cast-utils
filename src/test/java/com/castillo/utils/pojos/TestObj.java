package com.castillo.utils.pojos;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public class TestObj {
    private String strValue;
    private List<String> list;
    private Set<String> set;
    private Queue<String> queue;
    public TestObj() {
        super();
    }
    public TestObj(String strValue, List<String> list) {
        this.strValue = strValue;
        this.list = list;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Queue<String> getQueue() {
        return queue;
    }

    public void setQueue(Queue<String> queue) {
        this.queue = queue;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }
}
