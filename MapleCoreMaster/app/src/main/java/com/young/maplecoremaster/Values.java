package com.young.maplecoremaster;

import java.util.Arrays;

public class Values {
    int id;
    String charactor;
    int x, y;
    boolean skillImageOn;
    int[] levelSum={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    int level;
    boolean loadOn;

    public void Init(){
        this.id=0;
        this.charactor="에반";
        this.x=1;
        this.y=0;
        this.skillImageOn=false;
        this.level=1;
        this.loadOn=false;
        levelSumInit();
    }

    public void levelSumInit(){
        Arrays.fill(this.levelSum, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharactor() {
        return charactor;
    }

    public void setCharactor(String charactor) {
        this.charactor = charactor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSkillImageOn() {
        return skillImageOn;
    }

    public void setSkillImageOn(boolean skillImageOn) {
        this.skillImageOn = skillImageOn;
    }

    public int[] getLevelSum() {
        return levelSum;
    }

    public void setLevelSum(int[] levelSum) {
        this.levelSum = levelSum;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isLoadOn() {
        return loadOn;
    }

    public void setLoadOn(boolean loadOn) {
        this.loadOn = loadOn;
    }
    /*
    ArrayList<ArrayList<Integer>> listss = new ArrayList<ArrayList<Integer>>();
    public void listInit(){
        listss.clear();
        listss.add(null);
    }
    public void autoClicked(int x, int y, int level){
        this.x = x;
        this.y = y;
        this.level = level;
    }
    public void loadClicked(String charactor, int x, int y, int level, int[] levelSum, boolean skillImageOn, boolean loadOn, ArrayList<ArrayList<Integer>> listss){
        this.charactor = charactor;
        this.x=x;
        this.y=y;
        this.level = level;
        this.levelSum = levelSum;
        this.skillImageOn = skillImageOn;
        this.loadOn = loadOn;
        this.listss = listss;
    }
    */
}
