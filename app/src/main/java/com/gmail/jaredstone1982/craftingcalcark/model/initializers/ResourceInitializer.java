package com.gmail.jaredstone1982.craftingcalcark.model.initializers;

import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.R;

/**
 * Initializes a list of data to be used to fill database table RESOURCE_TABLE
 * <p>
 * FIXME: This does not currently allow for complex resource objects (resources that are composed of resources), be advised.
 */
public class ResourceInitializer {

    // List of Constant values that contain Image Resource IDs
    public static final int WOOD = R.drawable.wood;
    public static final int STONE = R.drawable.stone;
    public static final int METAL_ORE = R.drawable.metal_ore;
    public static final int HIDE = R.drawable.hide;
    public static final int CHITIN = R.drawable.chitin;
    public static final int FLINT = R.drawable.flint;
    public static final int METAL_INGOT = R.drawable.metal_ingot;
    public static final int THATCH = R.drawable.thatch;
    public static final int FIBER = R.drawable.fiber;
    public static final int CHARCOAL = R.drawable.charcoal;
    public static final int CRYSTAL = R.drawable.crystal;
    public static final int SPARKPOWDER = R.drawable.sparkpowder;
    public static final int GUNPOWDER = R.drawable.gunpowder;
    public static final int OBSIDIAN = R.drawable.obsidian;
    public static final int CEMENTING_PASTE = R.drawable.cementing_paste;
    public static final int OIL = R.drawable.oil;
    public static final int SILICA_PEARLS = R.drawable.silica_pearls;
    public static final int GASOLINE = R.drawable.gasoline;
    public static final int ELECTRONICS = R.drawable.electronics;
    public static final int POLYMER = R.drawable.polymer;
    public static final int KERATIN = R.drawable.keratin;
    public static final int RARE_FLOWER = R.drawable.rare_flower;
    public static final int RARE_MUSHROOM = R.drawable.rare_mushroom;
    public static final int PELT = R.drawable.pelt;
    public static final int ORGANIC_POLYMER = R.drawable.organic_polymer;
    public static final int ANGLER_GEL = R.drawable.angler_gel;
    public static final int BLACK_PEARL = R.drawable.black_pearl;
    public static final int WOOLLY_RHINO_HORN = R.drawable.woolly_rhino_horn;

    private static SparseArray<String> resources = new SparseArray<String>() {
        {
            append(ANGLER_GEL, "Angler Gel");
            append(BLACK_PEARL, "Black Pearl");
            append(CEMENTING_PASTE, "Cementing Paste");
            append(CHARCOAL, "Charoal");
            append(CHITIN, "Chitin");
            append(CRYSTAL, "Crystal");
            append(ELECTRONICS, "Electronics");
            append(FIBER, "Fiber");
            append(FLINT, "Flint");
            append(GASOLINE, "Gasoline");
            append(GUNPOWDER, "Gunpowder");
            append(HIDE, "Hide");
            append(KERATIN, "Keratin");
            append(METAL_INGOT, "Metal Ingot");
            append(METAL_ORE, "Metal Ore");
            append(OBSIDIAN, "Obsidian");
            append(OIL, "Oil");
            append(ORGANIC_POLYMER, "Organic Polymer");
            append(PELT, "Pelt");
            append(POLYMER, "Polymer");
            append(RARE_FLOWER, "Rare Flower");
            append(RARE_MUSHROOM, "Rare Mushroom");
            append(SILICA_PEARLS, "Silica Pearls");
            append(SPARKPOWDER, "Sparkpowder");
            append(STONE, "Stone");
            append(THATCH, "Thatch");
            append(WOOD, "Wood");
            append(WOOLLY_RHINO_HORN, "Woolly Rhino Horn");
        }
    };

    /**
     * Retrieve a list of Resource Objects as generated for DB
     *
     * @return SpareArray of Resource Objects
     */
    public static SparseArray<String> getResources() {
        return resources;
    }
}
