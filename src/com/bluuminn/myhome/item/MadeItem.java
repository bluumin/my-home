package com.bluuminn.myhome.item;

import java.util.ArrayList;

public class MadeItem extends Item {

    public ArrayList<RequiredItem> requiredItem = new ArrayList<RequiredItem>();

    public MadeItem(String name, String areaOfProduction, int price, int proCost) {
        super(name, areaOfProduction, price);
        this.type = "제작";
        this.cost = proCost;
    }
}