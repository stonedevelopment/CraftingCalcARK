package com.gmail.jaredstone1982.craftingcalcark.model.initializers;

import android.util.SparseArray;

import com.gmail.jaredstone1982.craftingcalcark.R;

/**
 * Copyright (C) 2016, Jared Stone
 * -
 * Author: Jared Stone, Stone Development
 * Title: ARK:Crafting Calculator
 * -
 * Web: https://github.com/jaredstone1982/CraftingCalcARK
 * Email: jaredstone1982@gmail.com
 * Twitter: @MasterxOfxNone
 * -
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 */
public class ResourceInitializer {
    public static final String VERSION = "0.1.5";

    // List of Constant values that contain Image Resource IDs
    public static final int WOOD = R.drawable.wood;
    public static final int STONE = R.drawable.stone;
    public static final int METAL_ORE = R.drawable.metal_ore;
    public static final int HIDE = R.drawable.hide;
    public static final int CHITIN = R.drawable.chitin;
    public static final int CHITIN_OR_KERATIN = R.drawable.chitin_or_keratin;
    public static final int FLINT = R.drawable.flint;
    public static final int METAL_INGOT = R.drawable.refined_metal_ingot;
    public static final int THATCH = R.drawable.thatch;
    public static final int FIBER = R.drawable.fiber;
    public static final int CHARCOAL = R.drawable.refined_charcoal;
    public static final int CRYSTAL = R.drawable.crystal;
    public static final int SPARKPOWDER = R.drawable.refined_sparkpowder;
    public static final int GUNPOWDER = R.drawable.refined_gunpowder;
    public static final int OBSIDIAN = R.drawable.obsidian;
    public static final int CEMENTING_PASTE = R.drawable.refined_cementing_paste;
    public static final int OIL = R.drawable.oil;
    public static final int SILICA_PEARLS = R.drawable.silica_pearls;
    public static final int GASOLINE = R.drawable.refined_gasoline;
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
    public static final int ABSORBENT_SUBSTRATE = R.drawable.refined_absorbent_substrate;
    public static final int LEECH_BLOOD = R.drawable.leech_blood;
    public static final int SAP = R.drawable.sap;
    public static final int FERTILIZER = R.drawable.refined_fertilizer;
    public static final int STONE_ARROW = R.drawable.weapons_ammo_stone_arrow;
    public static final int NARCOTIC = R.drawable.consumables_drugs_narcotic;
    public static final int STIMULANT = R.drawable.consumables_drugs_stimulant;
    public static final int SIMPLE_RIFLE_AMMO = R.drawable.weapons_ammo_simple_rifle_ammo;
    public static final int AMARBERRY = R.drawable.amarberry;
    public static final int AZULBERRY = R.drawable.azulberry;
    public static final int MEJOBERRY = R.drawable.mejoberry;
    public static final int NARCOBERRY = R.drawable.narcoberry;
    public static final int STIMBERRY = R.drawable.stimberry;
    public static final int TINTOBERRY = R.drawable.tintoberry;
    public static final int CITRONAL = R.drawable.citronal;
    public static final int LONGRASS = R.drawable.longrass;
    public static final int ROCKARROT = R.drawable.rockarrot;
    public static final int SAVOROOT = R.drawable.savoroot;
    public static final int SPOILED_MEAT = R.drawable.spoiled_meat;
    public static final int RAW_PRIME_MEAT = R.drawable.raw_prime_meat;
    public static final int RAW_MEAT = R.drawable.raw_meat;
    public static final int COOKED_MEAT_OR_FISH_MEAT = R.drawable.cooked_meat_or_fish_meat;
    public static final int COOKED_MEAT = R.drawable.cooked_meat;
    public static final int COOKED_FISH_MEAT = R.drawable.cooked_fish_meat;
    public static final int COOKED_PRIME_MEAT = R.drawable.cooked_prime_meat;
    public static final int BEER_LIQUID = R.drawable.refined_beer_liquid;
    public static final int FECES = R.drawable.feces;
    public static final int BERRIES = R.drawable.berries;

    private static SparseArray<String> resources = new SparseArray<String>() {{
        append(ANGLER_GEL, "Angler Gel");
        append(BLACK_PEARL, "Black Pearl");
        append(CEMENTING_PASTE, "Cementing Paste");
        append(CHARCOAL, "Charcoal");
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
        append(ABSORBENT_SUBSTRATE, "Absorbent Substrate");
        append(LEECH_BLOOD, "Leech Blood");
        append(SAP, "Sap");
        append(FERTILIZER, "Fertilizer");
        append(STONE_ARROW, "Stone Arrow");
        append(NARCOTIC, "Narcotic");
        append(STIMULANT, "Stimulant");
        append(SIMPLE_RIFLE_AMMO, "Simple Rifle Ammo");
        append(AMARBERRY, "Amarberry");
        append(AZULBERRY, "Azulberry");
        append(MEJOBERRY, "Mejoberry");
        append(NARCOBERRY, "Narcoberry");
        append(STIMBERRY, "Stimberry");
        append(TINTOBERRY, "Tintoberry");
        append(CITRONAL, "Citronal");
        append(LONGRASS, "Longrass");
        append(ROCKARROT, "Rockarrot");
        append(SAVOROOT, "Savoroot");
        append(SPOILED_MEAT, "Spoiled Meat");
        append(RAW_PRIME_MEAT, "Raw Prime Meat");
        append(RAW_MEAT, "Raw Meat");
        append(COOKED_FISH_MEAT, "Cooked Fish Meat");
        append(COOKED_MEAT, "Cooked Meat");
        append(COOKED_MEAT_OR_FISH_MEAT, "Cooked Meat or Fish Meat");
        append(COOKED_PRIME_MEAT, "Cooked Prime Meat");
        append(BEER_LIQUID, "Beer Liquid");
        append(BERRIES, "Berries (Any Kind)");
        append(FECES, "Feces (Any Size)");
    }};

    public static SparseArray<String> getResources() {
        return resources;
    }

    public static int getCount() {
        return resources.size();
    }
}
