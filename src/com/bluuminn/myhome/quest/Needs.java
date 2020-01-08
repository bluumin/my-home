package com.bluuminn.myhome.quest;

public class Needs {
    public String needItem;        // 퀘스트에 필요한 아이템 이름
    public int needItemCnt;        // 퀘스트에 필요한 아이템 개수

    public Needs(String name, int count) {
        this.needItem = name;
        this.needItemCnt = count;
    }
}