package com.peeko32213.unusualprehistory.client.screen.util;

public class ItemRenderData {
   public String item;
   public String item_tag = "";
   public int x;
   public int y;
   public double scale;
   public int page;

    public ItemRenderData(String item, int x, int y, double scale, int page) {
        this.item = item;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.page = page;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemTag() {
        return item_tag;
    }

    public void setItemTag(String item) {
        this.item_tag = item;
    }

    public int getPage() {
        return page;
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

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }
}
