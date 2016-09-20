package arc.resource.calculator.data.initializers;

import android.util.SparseArray;

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
public class ComplexResourceInitializer {
    public static final String VERSION = "245.0";

    private static SparseArray<String> resources = new SparseArray<String>() {{
        append( ResourceInitializer.ABSORBENT_SUBSTRATE, "Absorbent Substrate" );
        append( ResourceInitializer.BEER_LIQUID, "Beer Liquid" );
        append( ResourceInitializer.CEMENTING_PASTE, "Cementing Paste" );
        append( ResourceInitializer.CHARCOAL, "Charcoal" );
        append( ResourceInitializer.FERTILIZER, "Fertilizer" );
        append( ResourceInitializer.GASOLINE, "Gasoline" );
        append( ResourceInitializer.GUNPOWDER, "Gunpowder" );
        append( ResourceInitializer.METAL_INGOT, "Metal Ingot" );
        append( ResourceInitializer.SPARKPOWDER, "Sparkpowder" );
        append( ResourceInitializer.ELECTRONICS, "Electronics" );
        append( ResourceInitializer.POLYMER, "Polymer" );
        append( ResourceInitializer.STONE_ARROW, "Stone Arrow" );
        append( ResourceInitializer.NARCOTIC, "Narcotic" );
        append( ResourceInitializer.STIMULANT, "Stimulant" );
        append( ResourceInitializer.SIMPLE_RIFLE_AMMO, "Simple Rifle Ammo" );
    }};

    public static SparseArray<String> getResources() {
        return resources;
    }

    public static int getCount() {
        return resources.size();
    }
}
