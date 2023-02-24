package com.bluuminn.myhome.quest;

public class Title {
    private String name;
    private String condition;
    private boolean achieved;

    public Title(String name) {
        this.name = name;
        this.achieved = false;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void achieved() {
        this.achieved = true;
    }
}