package com.gmail.jaredstone1982.craftingcalcark.model.initializers;

import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.model.CraftableResource;
import com.gmail.jaredstone1982.craftingcalcark.model.Resource;

/**
 * FIXME: This class may not be needed anymore.
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
    
    private static SparseArray<Resource> resources = new SparseArray<Resource>() {
        {
            append(ANGLER_GEL, new Resource(ANGLER_GEL, "Angler Gel"));
            append(BLACK_PEARL, new Resource(BLACK_PEARL, "Black Pearl"));
            append(CEMENTING_PASTE, new Resource(CEMENTING_PASTE, "Cementing Paste"));
            append(CHARCOAL, new Resource(CHARCOAL, "Charoal"));
            append(CHITIN, new Resource(CHITIN, "Chitin"));
            append(CRYSTAL, new Resource(CRYSTAL, "Crystal"));
            append(ELECTRONICS, new Resource(ELECTRONICS, "Electronics"));
            append(FIBER, new Resource(FIBER, "Fiber"));
            append(FLINT, new Resource(FLINT, "Flint"));
            append(GASOLINE, new Resource(GASOLINE, "Gasoline"));
            append(GUNPOWDER, new Resource(GUNPOWDER, "Gunpowder"));
            append(HIDE, new Resource(HIDE, "Hide"));
            append(KERATIN, new Resource(KERATIN, "Keratin"));
            append(METAL_INGOT, new Resource(METAL_INGOT, "Metal Ingot"));
            append(METAL_ORE, new Resource(METAL_ORE, "Metal Ore"));
            append(OBSIDIAN, new Resource(OBSIDIAN, "Obsidian"));
            append(OIL, new Resource(OIL, "Oil"));
            append(ORGANIC_POLYMER, new Resource(ORGANIC_POLYMER, "Organic Polymer"));
            append(PELT, new Resource(PELT, "Pelt"));
            append(POLYMER, new Resource(POLYMER, "Polymer"));
            append(RARE_FLOWER, new Resource(RARE_FLOWER, "Rare Flower"));
            append(RARE_MUSHROOM, new Resource(RARE_MUSHROOM, "Rare Mushroom"));
            append(SILICA_PEARLS, new Resource(SILICA_PEARLS, "Silica Pearls"));
            append(SPARKPOWDER, new Resource(SPARKPOWDER, "Sparkpowder"));
            append(STONE, new Resource(STONE, "Stone"));
            append(THATCH, new Resource(THATCH, "Thatch"));
            append(WOOD, new Resource(WOOD, "Wood"));
            append(WOOLLY_RHINO_HORN, new Resource(WOOLLY_RHINO_HORN, "Woolly Rhino Horn"));
        }
    };

    /**
     * Retrieve a list of Resource Objects as generated for DB
     *
     * @return SpareArray of Resource Objects
     */
    public static SparseArray<Resource> getResources() {
        return resources;
    }

    /**
     * Retrieve the relative Resource Object of its ID
     *
     * @param imageId ID of requested Resource Object to retrieve
     * @return Resource Object as found by its ID in the list
     */
    public static Resource getResource(int imageId) {
        return resources.get(imageId);
    }

    public static CraftableResource getCraftableResource(int imageId) {
        Resource resource = resources.get(imageId);
        return new CraftableResource(resource.getName(), resource.getImageId());
    }
}
