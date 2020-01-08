package com.bluuminn.myhome.quest;

import com.bluuminn.myhome.inventory.ItemEntry;

import java.util.ArrayList;

public class Quest {
    public String questName;       // 퀘스트 이름
    public String npcName;         // 퀘스트 제공 npc 이름
    public char[] questContent;    // 퀘스트 내용
    public char[] questEnding;     // 퀘스트 종료시 멘트

    public ArrayList<Needs> needs = new ArrayList<Needs>();

    public ItemEntry payItem = null;
    //    String payItem = null;     // 보상 아이템 이름
    public int payItemCount = 0;       // 보상 아이템 개수
    public int payForGold = 0;     // 보상 골드
    public int payExp = 0;         // 보상 경험치

    public boolean completeCK;     // 완료 여부 체크(완료되면 리스트 삭제할 용도)
    // 퀘스트가 아이템을 만들어서 납품하는 것인지, 만들기만 하는 것인지 확인
    // true = 납품  false = 제작만
    public boolean deliverCK;


    public Quest(String name, String npc, boolean deliverCK) {
        this.questName = name;
        this.npcName = npc;
        this.completeCK = false;
        this.deliverCK = deliverCK;
    }


}