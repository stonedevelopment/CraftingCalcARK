package arc.resource.calculator.model;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone
 * Title: A:RC, a resource calculator for ARK:Survival Evolved
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class Engram {
    // Holds id held in database
    private long id;

    // Holds string literal of Engram's in-game name
    private String name;

    // Holds drawable resource id
    private Integer imageId;

    public Engram(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Engram(long id, String name, int imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", imageId=" + imageId;
    }
}
