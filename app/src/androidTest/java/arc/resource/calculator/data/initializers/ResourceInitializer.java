package arc.resource.calculator.data.initializers;

import android.util.SparseArray;

import arc.resource.calculator.R;

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
public class ResourceInitializer {
    public static final String VERSION = "245.1";

    public static final int WOOD = R.drawable.wood;
    public static final int STONE = R.drawable.stone;
    public static final int METAL_ORE = R.drawable.metal_ore;
    public static final int HIDE = R.drawable.hide;
    public static final int CHITIN = R.drawable.chitin;
    public static final int CHITIN_OR_KERATIN = R.drawable.chitin_or_keratin;
    public static final int FLINT = R.drawable.flint;
    public static final int THATCH = R.drawable.thatch;
    public static final int FIBER = R.drawable.fiber;
    public static final int CRYSTAL = R.drawable.crystal;
    public static final int OBSIDIAN = R.drawable.obsidian;
    public static final int OIL = R.drawable.oil;
    public static final int SILICA_PEARLS = R.drawable.silica_pearls;
    public static final int KERATIN = R.drawable.keratin;
    public static final int RARE_FLOWER = R.drawable.rare_flower;
    public static final int RARE_MUSHROOM = R.drawable.rare_mushroom;
    public static final int PELT = R.drawable.pelt;
    public static final int ORGANIC_POLYMER = R.drawable.organic_polymer;
    public static final int ANGLER_GEL = R.drawable.angler_gel;
    public static final int BLACK_PEARL = R.drawable.black_pearl;
    public static final int WOOLLY_RHINO_HORN = R.drawable.woolly_rhino_horn;
    public static final int LEECH_BLOOD = R.drawable.leech_blood;
    public static final int SAP = R.drawable.sap;
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
    public static final int FECES = R.drawable.feces;
    public static final int BERRIES = R.drawable.berries;
    public static final int PRIME_MEAT_JERKY = R.drawable.prime_meat_jerky;
    public static final int COOKED_MEAT_JERKY = R.drawable.cooked_meat_jerky;
    public static final int COOKED_PRIME_MEAT_OR_PRIME_JERKY = R.drawable.cooked_prime_meat_or_prime_jerky;
    public static final int COOKED_MEAT_OR_JERKY = R.drawable.cooked_meat_or_jerky;

    // -- COMPLEX
    public static final int ABSORBENT_SUBSTRATE = RefinedInitializer.ABSORBENT_SUBSTRATE;
    public static final int BEER_LIQUID = RefinedInitializer.BEER_LIQUID;
    public static final int CEMENTING_PASTE = RefinedInitializer.CEMENTING_PASTE;
    public static final int CHARCOAL = RefinedInitializer.CHARCOAL;
    public static final int FERTILIZER = RefinedInitializer.FERTILIZER;
    public static final int GASOLINE = RefinedInitializer.GASOLINE;
    public static final int GUNPOWDER = RefinedInitializer.GUNPOWDER;
    public static final int METAL_INGOT = RefinedInitializer.METAL_INGOT;
    public static final int SPARKPOWDER = RefinedInitializer.SPARKPOWDER;

    public static final int ELECTRONICS = EngramInitializer.ELECTRONICS;
    public static final int POLYMER = EngramInitializer.POLYMER;
    public static final int STONE_ARROW = EngramInitializer.STONE_ARROW;
    public static final int NARCOTIC = EngramInitializer.NARCOTIC;
    public static final int STIMULANT = EngramInitializer.STIMULANT;
    public static final int SIMPLE_RIFLE_AMMO = EngramInitializer.SIMPLE_RIFLE_AMMO;
    public static final int WATER_JAR = EngramInitializer.WATER_JAR;

    // -- DINO EGGS
    public static final int ANKYLO_EGG = R.drawable.ankylo_egg;
    public static final int ARANEO_EGG = R.drawable.araneo_egg;
    public static final int ARGENTAVIS_EGG = R.drawable.argentavis_egg;
    public static final int BRONTO_EGG = R.drawable.bronto_egg;
    public static final int CARNO_EGG = R.drawable.carno_egg;
    public static final int COMPY_EGG = R.drawable.compy_egg;
    public static final int DILO_EGG = R.drawable.dilo_egg;
    public static final int DIMETRODON_EGG = R.drawable.dimetrodon_egg;
    public static final int DIMORPH_EGG = R.drawable.dimorph_egg;
    public static final int DIPLO_EGG = R.drawable.diplo_egg;
    public static final int DODO_EGG = R.drawable.dodo_egg;
    public static final int GALLIMIMUS_EGG = R.drawable.gallimimus_egg;
    public static final int KAIRUKU_EGG = R.drawable.kairuku_egg;
    public static final int LYSTRO_EGG = R.drawable.lystro_egg;
    public static final int PACHY_EGG = R.drawable.pachycephalosaurus_egg;
    public static final int PARASAUR_EGG = R.drawable.parasaur_egg;
    public static final int PTERANODON_EGG = R.drawable.pteranodon_egg;
    public static final int PULMONOSCORPIUS_EGG = R.drawable.pulmonoscorpius_egg;
    public static final int QUETZAL_EGG = R.drawable.quetzal_egg;
    public static final int RAPTOR_EGG = R.drawable.raptor_egg;
    public static final int REX_EGG = R.drawable.rex_egg;
    public static final int SARCO_EGG = R.drawable.sarco_egg;
    public static final int SPINO_EGG = R.drawable.spino_egg;
    public static final int STEGO_EGG = R.drawable.stego_egg;
    public static final int TERROR_BIRD_EGG = R.drawable.terror_bird_egg;
    public static final int TITANOBOA_EGG = R.drawable.titanoboa_egg;
    public static final int TRIKE_EGG = R.drawable.trike_egg;
    public static final int CARBONEMYS_EGG = R.drawable.carbonemys_egg;

    private static SparseArray<String> resources = new SparseArray<String>() {{
        append( ANGLER_GEL, "Angler Gel" );
        append( BLACK_PEARL, "Black Pearl" );
        append( CEMENTING_PASTE, "Cementing Paste" );
        append( CHARCOAL, "Charcoal" );
        append( CHITIN, "Chitin" );
        append( CHITIN_OR_KERATIN, "Chitin or Keratin" );
        append( CRYSTAL, "Crystal" );
        append( ELECTRONICS, "Electronics" );
        append( FIBER, "Fiber" );
        append( FLINT, "Flint" );
        append( GASOLINE, "Gasoline" );
        append( GUNPOWDER, "Gunpowder" );
        append( HIDE, "Hide" );
        append( KERATIN, "Keratin" );
        append( METAL_INGOT, "Metal Ingot" );
        append( METAL_ORE, "Metal Ore" );
        append( OBSIDIAN, "Obsidian" );
        append( OIL, "Oil" );
        append( ORGANIC_POLYMER, "Organic Polymer" );
        append( PELT, "Pelt" );
        append( POLYMER, "Polymer" );
        append( RARE_FLOWER, "Rare Flower" );
        append( RARE_MUSHROOM, "Rare Mushroom" );
        append( SILICA_PEARLS, "Silica Pearls" );
        append( SPARKPOWDER, "Sparkpowder" );
        append( STONE, "Stone" );
        append( THATCH, "Thatch" );
        append( WOOD, "Wood" );
        append( WOOLLY_RHINO_HORN, "Woolly Rhino Horn" );
        append( ABSORBENT_SUBSTRATE, "Absorbent Substrate" );
        append( LEECH_BLOOD, "Leech Blood" );
        append( SAP, "Sap" );
        append( FERTILIZER, "Fertilizer" );
        append( STONE_ARROW, "Stone Arrow" );
        append( NARCOTIC, "Narcotic" );
        append( STIMULANT, "Stimulant" );
        append( SIMPLE_RIFLE_AMMO, "Simple Rifle Ammo" );
        append( WATER_JAR, "Water Jar" );
        append( AMARBERRY, "Amarberry" );
        append( AZULBERRY, "Azulberry" );
        append( MEJOBERRY, "Mejoberry" );
        append( NARCOBERRY, "Narcoberry" );
        append( STIMBERRY, "Stimberry" );
        append( TINTOBERRY, "Tintoberry" );
        append( CITRONAL, "Citronal" );
        append( LONGRASS, "Longrass" );
        append( ROCKARROT, "Rockarrot" );
        append( SAVOROOT, "Savoroot" );
        append( SPOILED_MEAT, "Spoiled Meat" );
        append( RAW_PRIME_MEAT, "Raw Prime Meat" );
        append( RAW_MEAT, "Raw Meat" );
        append( COOKED_FISH_MEAT, "Cooked Fish Meat" );
        append( COOKED_MEAT, "Cooked Meat" );
        append( COOKED_MEAT_OR_FISH_MEAT, "Cooked Meat or Fish Meat" );
        append( COOKED_PRIME_MEAT, "Cooked Prime Meat" );
        append( BEER_LIQUID, "Beer Liquid" );
        append( BERRIES, "Berries (Any Kind)" );
        append( FECES, "Feces (Any Size)" );
        append( PRIME_MEAT_JERKY, "Prime Meat Jerky" );
        append( COOKED_MEAT_JERKY, "Cooked Meat Jerky" );
        append( COOKED_PRIME_MEAT_OR_PRIME_JERKY, "Cooked Prime Meat or Prime Meat Jerky" );
        append( COOKED_MEAT_OR_JERKY, "Cooked Meat or Cooked Meat Jerky" );
        append( ANKYLO_EGG, "Ankylo Egg" );
        append( ARANEO_EGG, "Araneo Egg" );
        append( ARGENTAVIS_EGG, "Argentavis Egg" );
        append( BRONTO_EGG, "Bronto Egg" );
        append( CARNO_EGG, "Carno Egg" );
        append( COMPY_EGG, "Compy Egg" );
        append( DILO_EGG, "Dilo Egg" );
        append( DIMETRODON_EGG, "Dimetrodon Egg" );
        append( DIMORPH_EGG, "Dimorphodon Egg" );
        append( DIPLO_EGG, "Diplodocus Egg" );
        append( DODO_EGG, "Dodo Egg" );
        append( GALLIMIMUS_EGG, "Gallimimus Egg" );
        append( KAIRUKU_EGG, "Kairuku Egg" );
        append( LYSTRO_EGG, "Lystrosaur Egg" );
        append( PACHY_EGG, "Pachy Egg" );
        append( PARASAUR_EGG, "Parasaur Egg" );
        append( PTERANODON_EGG, "Pteranodon Egg" );
        append( PULMONOSCORPIUS_EGG, "Pulmonoscorpius Egg" );
        append( QUETZAL_EGG, "Quetzal Egg" );
        append( RAPTOR_EGG, "Raptor Egg" );
        append( REX_EGG, "Rex Egg" );
        append( SARCO_EGG, "Sarco Egg" );
        append( SPINO_EGG, "Spino Egg" );
        append( STEGO_EGG, "Stego Egg" );
        append( TERROR_BIRD_EGG, "Terror Bird Egg" );
        append( TITANOBOA_EGG, "Titanoboa Egg" );
        append( TRIKE_EGG, "Trike Egg" );
        append( CARBONEMYS_EGG, "Carbonemys Egg" );
    }};

    public static SparseArray<String> getResources() {
        return resources;
    }

    public static int getCount() {
        return resources.size();
    }
}
