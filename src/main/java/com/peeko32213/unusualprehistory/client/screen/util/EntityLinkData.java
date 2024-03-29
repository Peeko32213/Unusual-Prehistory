package com.peeko32213.unusualprehistory.client.screen.util;

public class EntityLinkData {
    public String entity;
    public int x;
    public int y;
    public float offset_x;
    public float offset_y;
    public double entity_scale;
    public double scale;
    public int page;
    public String linked_page;
    public String hover_text;
    public String entity_data;
    public EntityLinkData(String entity, int x, int y, double scale, double entity_scale, int page, String linked_page, String hover_text, float offset_x, float offset_y, String entity_data) {
        this.entity = entity;
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.entity_scale = entity_scale;
        this.page = page;
        this.linked_page = linked_page;
        this.hover_text = hover_text;
        this.offset_x = offset_x;
        this.offset_y = offset_y;
        this.entity_data = entity_data;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String model) {
        this.entity = model;
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

    public double getEntityScale() {
        return entity_scale;
    }

    public void setEntityScale(double scale) {
        this.entity_scale = scale;
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
    public String getEntityData() {
        return entity_data;
    }
}
