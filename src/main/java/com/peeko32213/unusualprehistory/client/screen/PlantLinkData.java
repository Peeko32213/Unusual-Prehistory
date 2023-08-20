package com.peeko32213.unusualprehistory.client.screen;

import net.minecraft.world.item.Item;

public class PlantLinkData {
    public String plant;
    public int x;
    public int y;
    public float offset_x;
    public float offset_y;
    public double plant_scale;
    public double scale;
    public int page;
    public String linked_page;
    public String hover_text;

    public PlantLinkData(String plant, int x, int y, double scale, double plant_scale, int page, String linked_page, String hover_text, float offset_x, float offset_y) {
        this.plant = plant;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.plant_scale = plant_scale;
        this.page = page;
        this.linked_page = linked_page;
        this.hover_text = hover_text;
        this.offset_x = offset_x;
        this.offset_y = offset_y;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String model) {
        this.plant = model;
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

    public double getPlantScale() {
        return plant_scale;
    }

    public void setPlantScale(double scale) {
        this.plant_scale = scale;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getLinkedPage() {
        return linked_page;
    }

    public void setLinkedPage(String linkedPage) {
        this.linked_page = linkedPage;
    }

    public String getHoverText() {
        return hover_text;
    }

    public void setHoverText(String titleText) {
        this.hover_text = titleText;
    }

    public float getOffset_y() {
        return offset_y;
    }

    public void setOffset_y(float offset_y) {
        this.offset_y = offset_y;
    }

    public float getOffset_x() {
        return offset_x;
    }

    public void setOffset_x(float offset_x) {
        this.offset_x = offset_x;
    }
}
