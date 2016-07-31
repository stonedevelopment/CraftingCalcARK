package ark.resource.calculator.model.initializers;

import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

import ark.resource.calculator.R;
import ark.resource.calculator.model.InitEngram;

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
public class EngramInitializer {
    public static final String VERSION = "0.1.6";

    // -- ARMOR > CHITIN --
    public static final int CHITIN_BOOTS = R.drawable.armor_chitin_chitin_boots;
    public static final int CHITIN_CHESTPIECE = R.drawable.armor_chitin_chitin_chestpiece;
    public static final int CHITIN_GAUNTLETS = R.drawable.armor_chitin_chitin_gauntlets;
    public static final int CHITIN_HELMET = R.drawable.armor_chitin_chitin_helmet;
    public static final int CHITIN_LEGGINGS = R.drawable.armor_chitin_chitin_leggings;

    // -- ARMOR > CLOTH --
    public static final int CLOTH_BOOTS = R.drawable.armor_cloth_cloth_boots;
    public static final int CLOTH_GLOVES = R.drawable.armor_cloth_cloth_gloves;
    public static final int CLOTH_HAT = R.drawable.armor_cloth_cloth_hat;
    public static final int CLOTH_PANTS = R.drawable.armor_cloth_cloth_pants;
    public static final int CLOTH_SHIRT = R.drawable.armor_cloth_cloth_shirt;

    // -- ARMOR > FUR --
    public static final int FUR_BOOTS = R.drawable.armor_fur_fur_boots;
    public static final int FUR_CAP = R.drawable.armor_fur_fur_cap;
    public static final int FUR_CHESTPIECE = R.drawable.armor_fur_fur_chestpiece;
    public static final int FUR_GAUNTLETS = R.drawable.armor_fur_fur_gauntlets;
    public static final int FUR_LEGGINGS = R.drawable.armor_fur_fur_leggings;

    // -- ARMOR > GHILLIE --
    public static final int GHILLIE_BOOTS = R.drawable.armor_ghillie_ghillie_boots;
    public static final int GHILLIE_CHESTPIECE = R.drawable.armor_ghillie_ghillie_chestpiece;
    public static final int GHILLIE_GAUNTLETS = R.drawable.armor_ghillie_ghillie_gauntlets;
    public static final int GHILLIE_LEGGINGS = R.drawable.armor_ghillie_ghillie_leggings;
    public static final int GHILLIE_MASK = R.drawable.armor_ghillie_ghillie_mask;

    // -- ARMOR > HIDE --
    public static final int HIDE_BOOTS = R.drawable.armor_hide_hide_boots;
    public static final int HIDE_GLOVES = R.drawable.armor_hide_hide_gloves;
    public static final int HIDE_HAT = R.drawable.armor_hide_hide_hat;
    public static final int HIDE_PANTS = R.drawable.armor_hide_hide_pants;
    public static final int HIDE_SHIRT = R.drawable.armor_hide_hide_shirt;

    // -- ARMOR > METAL --
    public static final int FLAK_BOOTS = R.drawable.armor_metal_flak_boots;
    public static final int FLAK_CHESTPIECE = R.drawable.armor_metal_flak_chestpiece;
    public static final int FLAK_GAUNTLETS = R.drawable.armor_metal_flak_gauntlets;
    public static final int FLAK_HELMET = R.drawable.armor_metal_flak_helmet;
    public static final int FLAK_LEGGINGS = R.drawable.armor_metal_flak_leggings;
    public static final int HEAVY_MINERS_HELMET = R.drawable.armor_metal_heavy_miners_helmet;

    // -- ARMOR > MISC --
    public static final int GAS_MASK = R.drawable.armor_misc_gas_mask;

    // -- ARMOR > RIOT --
    public static final int RIOT_BOOTS = R.drawable.armor_riot_riot_boots;
    public static final int RIOT_CHESTPIECE = R.drawable.armor_riot_riot_chestpiece;
    public static final int RIOT_GAUNTLETS = R.drawable.armor_riot_riot_gauntlets;
    public static final int RIOT_HELMET = R.drawable.armor_riot_riot_helmet;
    public static final int RIOT_LEGGINGS = R.drawable.armor_riot_riot_leggings;

    // -- ARMOR > SCUBA --
    public static final int SCUBA_FLIPPERS = R.drawable.armor_scuba_scuba_flippers;
    public static final int SCUBA_LEGGINGS = R.drawable.armor_scuba_scuba_leggings;
    public static final int SCUBA_MASK = R.drawable.armor_scuba_scuba_mask;
    public static final int SCUBA_TANK = R.drawable.armor_scuba_scuba_tank;

    // -- ARMOR > SHIELDS --
    public static final int METAL_SHIELD = R.drawable.armor_shields_metal_shield;
    public static final int RIOT_SHIELD = R.drawable.armor_shields_riot_shield;
    public static final int WOODEN_SHIELD = R.drawable.armor_shields_wooden_shield;

    // -- COMMUNICATION --
    public static final int RADIO = R.drawable.communication_radio;

    // -- COMPOSITES --
    public static final int ELECTRONICS = R.drawable.composites_electronics;
    public static final int POLYMER = R.drawable.composites_polymer;

    // -- CONSUMABLES --
    // -- CONSUMABLES > DRUGS --
    public static final int BUG_REPELLANT = R.drawable.consumables_drugs_bug_repellant;
    public static final int LESSER_ANTIDOTE = R.drawable.consumables_drugs_lesser_antidote;
    public static final int NARCOTIC = R.drawable.consumables_drugs_narcotic;
    public static final int STIMULANT = R.drawable.consumables_drugs_stimulant;

    // -- CONSUMABLES > FOOD --
    public static final int BATTLE_TARTARE = R.drawable.consumables_food_battle_tartare;
    public static final int BEER_JAR = R.drawable.consumables_food_beer_jar;
    public static final int BINGLEBERRY_SOUP = R.drawable.consumables_food_bingleberry_soup;
    public static final int BROTH_OF_ENLIGHTENMENT = R.drawable.consumables_food_broth_of_enlightenment;
    public static final int CALIEN_SOUP = R.drawable.consumables_food_calien_soup;
    public static final int ENDURO_STEW = R.drawable.consumables_food_enduro_stew;
    public static final int ENERGY_BREW = R.drawable.consumables_food_energy_brew;
    public static final int FOCAL_CHILI = R.drawable.consumables_food_focal_chili;
    public static final int FRIA_CURRY = R.drawable.consumables_food_fria_curry;
    public static final int LAZARUS_CHOWDER = R.drawable.consumables_food_lazarus_chowder;
    public static final int MEDICAL_BREW = R.drawable.consumables_food_medical_brew;
    public static final int MINDWIPE_TONIC = R.drawable.consumables_food_mindwipe_tonic;
    public static final int SHADOW_STEAK_SAUCE = R.drawable.consumables_food_shadow_steak_saute;
    public static final int SWEET_VEGGIE_CAKE = R.drawable.consumables_food_sweet_veggie_cake;

    // -- DYES --
    public static final int BLACK_COLORING = R.drawable.dyes_black_coloring;
    public static final int BLUE_COLORING = R.drawable.dyes_blue_coloring;
    public static final int BRICK_COLORING = R.drawable.dyes_brick_coloring;
    public static final int BROWN_COLORING = R.drawable.dyes_brown_coloring;
    public static final int CANTALOUPE_COLORING = R.drawable.dyes_cantaloupe_coloring;
    public static final int CYAN_COLORING = R.drawable.dyes_cyan_coloring;
    public static final int FOREST_COLORING = R.drawable.dyes_forest_coloring;
    public static final int GREEN_COLORING = R.drawable.dyes_green_coloring;
    public static final int MAGENTA_COLORING = R.drawable.dyes_magenta_coloring;
    public static final int MUD_COLORING = R.drawable.dyes_mud_coloring;
    public static final int NAVY_COLORING = R.drawable.dyes_navy_coloring;
    public static final int OLIVE_COLORING = R.drawable.dyes_olive_coloring;
    public static final int ORANGE_COLORING = R.drawable.dyes_orange_coloring;
    public static final int PARCHMENT_COLORING = R.drawable.dyes_parchment_coloring;
    public static final int PINK_COLORING = R.drawable.dyes_pink_coloring;
    public static final int PURPLE_COLORING = R.drawable.dyes_purple_coloring;
    public static final int RED_COLORING = R.drawable.dyes_red_coloring;
    public static final int ROYALTY_COLORING = R.drawable.dyes_royalty_coloring;
    public static final int SILVER_COLORING = R.drawable.dyes_silver_coloring;
    public static final int SKY_COLORING = R.drawable.dyes_sky_coloring;
    public static final int SLATE_COLORING = R.drawable.dyes_slate_coloring;
    public static final int TAN_COLORING = R.drawable.dyes_tan_coloring;
    public static final int TANGERINE_COLORING = R.drawable.dyes_tangerine_coloring;
    public static final int WHITE_COLORING = R.drawable.dyes_white_coloring;
    public static final int YELLOW_COLORING = R.drawable.dyes_yellow_coloring;

    // -- MISC --
    public static final int BLOOD_EXTRACTION_SYRINGE = R.drawable.misc_blood_extraction_syringe;
    public static final int CANTEEN = R.drawable.misc_canteen;
    public static final int FLARE_GUN = R.drawable.misc_flare_gun;
    public static final int MAGNIFYING_GLASS = R.drawable.misc_magnifying_glass;
    public static final int NOTE = R.drawable.misc_note;
    public static final int PAINTBRUSH = R.drawable.misc_paintbrush;
    public static final int PARACHUTE = R.drawable.misc_parachute;
    public static final int REFERTILIZER = R.drawable.misc_refertilizer;
    public static final int SPRAY_PAINTER = R.drawable.misc_spray_painter;
    public static final int SPYGLASS = R.drawable.misc_spyglass;
    public static final int WATER_JAR = R.drawable.misc_water_jar;
    public static final int WATERSKIN = R.drawable.misc_waterskin;
    public static final int WOODEN_RAFT = R.drawable.misc_wooden_raft;

    // -- NAVIGATION --
    public static final int COMPASS = R.drawable.navigation_compass;
    public static final int GPS = R.drawable.navigation_gps;
    public static final int TRANSPONDER_NODE = R.drawable.navigation_transponder_node;
    public static final int TRANSPONDER_TRACKER = R.drawable.navigation_transponder_tracker;

    // -- OFFHAND --
    public static final int HANDCUFFS = R.drawable.offhand_handcuffs;

    // -- REFINED --
    public static final int ABSORBENT_SUBSTRATE = R.drawable.refined_absorbent_substrate;
    public static final int BEER_LIQUID = R.drawable.refined_beer_liquid;
    public static final int CEMENTING_PASTE = R.drawable.refined_cementing_paste;
    public static final int CHARCOAL = R.drawable.refined_charcoal;
    public static final int FERTILIZER = R.drawable.refined_fertilizer;
    public static final int GASOLINE = R.drawable.refined_gasoline;
    public static final int GUNPOWDER = R.drawable.refined_gunpowder;
    public static final int METAL_INGOT = R.drawable.refined_metal_ingot;
    public static final int SPARKPOWDER = R.drawable.refined_sparkpowder;

    // -- SADDLES --
    public static final int ANKYLO = R.drawable.saddles_ankylo;
    public static final int ARANEO = R.drawable.saddles_araneo;
    public static final int ARGENTAVIS = R.drawable.saddles_argentavis;
    public static final int ARTHROPLUERA = R.drawable.saddles_arthropluera;
    public static final int BEELZEBUFO = R.drawable.saddles_beelzebufo;
    public static final int BRONTO = R.drawable.saddles_bronto;
    public static final int BRONTO_PLATFORM = R.drawable.saddles_bronto_platform;
    public static final int CARBONEMYS = R.drawable.saddles_carbonemys;
    public static final int CARNO = R.drawable.saddles_carno;
    public static final int CASTOROIDES = R.drawable.saddles_castoroides;
    public static final int DIPLODOCUS = R.drawable.saddles_diplodocus;
    public static final int DIREBEAR = R.drawable.saddles_direbear;
    public static final int DOEDICURUS = R.drawable.saddles_doedicurus;
    public static final int DUNKLEOSTEUS = R.drawable.saddles_dunkleosteus;
    public static final int GALLIMIMUS = R.drawable.saddles_gallimimus;
    public static final int GIGANOTOSAURUS = R.drawable.saddles_giganotosaurus;
    public static final int ICHTHYOSAURUS = R.drawable.saddles_ichthyosaurus;
    public static final int MAMMOTH = R.drawable.saddles_mammoth;
    public static final int MANTA = R.drawable.saddles_manta;
    public static final int MEGALOCEROS = R.drawable.saddles_megaloceros;
    public static final int MEGALODON = R.drawable.saddles_megalodon;
    public static final int MOSASAURUS = R.drawable.saddles_mosasaurus;
    public static final int MOSASAURUS_PLATFORM = R.drawable.saddles_mosasaurus_platform;
    public static final int PACHY = R.drawable.saddles_pachy;
    public static final int PARACER = R.drawable.saddles_paracer;
    public static final int PARACER_PLATFORM = R.drawable.saddles_paracer_platform;
    public static final int PARASAUR = R.drawable.saddles_parasaur;
    public static final int PHIOMIA = R.drawable.saddles_phiomia;
    public static final int PLESIOSAUR = R.drawable.saddles_plesiosaur;
    public static final int PLESIOSAUR_PLATFORM = R.drawable.saddles_plesiosaur_platform;
    public static final int PROCOPTODON = R.drawable.saddles_procoptodon;
    public static final int PTERANODON = R.drawable.saddles_pteranodon;
    public static final int PULMONOSCORPIUS = R.drawable.saddles_pulmonoscorpius;
    public static final int QUETZAL = R.drawable.saddles_quetzal;
    public static final int QUETZAL_PLATFORM = R.drawable.saddles_quetzal_platform;
    public static final int RAPTOR = R.drawable.saddles_raptor;
    public static final int REX = R.drawable.saddles_rex;
    public static final int SABERTOOTH = R.drawable.saddles_sabertooth;
    public static final int SARCO = R.drawable.saddles_sarco;
    public static final int SPINO = R.drawable.saddles_spino;
    public static final int STEGO = R.drawable.saddles_stego;
    public static final int TERROR_BIRD = R.drawable.saddles_terror_bird;
    public static final int TITANOSAUR_PLATFORM = R.drawable.saddles_titanosaur_platform;
    public static final int TRIKE = R.drawable.saddles_trike;
    public static final int WOOLLY_RHINO = R.drawable.saddles_woolly_rhino;

    // -- WEAPONS > AMMO --
    public static final int ADVANCED_BULLET = R.drawable.weapons_ammo_advanced_bullet;
    public static final int ADVANCED_RIFLE_BULLET = R.drawable.weapons_ammo_advanced_rifle_bullet;
    public static final int ADVANCED_SNIPER_BULLET = R.drawable.weapons_ammo_advanced_sniper_bullet;
    public static final int BALLISTA_BOLT = R.drawable.weapons_ammo_ballista_bolt;
    public static final int BOULDER = R.drawable.weapons_ammo_boulder;
    public static final int C4_CHARGE = R.drawable.weapons_ammo_c4_charge;
    public static final int CANNON_BALL = R.drawable.weapons_ammo_cannon_ball;
    public static final int CHAIN_BOLA = R.drawable.weapons_ammo_chain_bola;
    public static final int GRAPPLING_HOOK = R.drawable.weapons_ammo_grappling_hook;
    public static final int METAL_ARROW = R.drawable.weapons_ammo_metal_arrow;
    public static final int ROCKET_PROPELLED_GRENADE = R.drawable.weapons_ammo_rocket_propelled_grenade;
    public static final int SIMPLE_BULLET = R.drawable.weapons_ammo_simple_bullet;
    public static final int SIMPLE_RIFLE_AMMO = R.drawable.weapons_ammo_simple_rifle_ammo;
    public static final int SIMPLE_SHOTGUN_AMMO = R.drawable.weapons_ammo_simple_shotgun_ammo;
    public static final int STONE_ARROW = R.drawable.weapons_ammo_stone_arrow;
    public static final int TRANQUILIZER_ARROW = R.drawable.weapons_ammo_tranquilizer_arrow;
    public static final int TRANQUILIZER_DART = R.drawable.weapons_ammo_tranquilizer_dart;

    // -- WEAPONS > ATTACHMENTS --
    public static final int FLASHLIGHT_ATTACHMENT = R.drawable.weapons_attachments_flashlight_attachment;
    public static final int HOLOSCOPE_ATTACHMENT = R.drawable.weapons_attachments_holo_scope_attachment;
    public static final int LASER_ATTACHMENT = R.drawable.weapons_attachments_laser_attachment;
    public static final int SCOPE_ATTACHMENT = R.drawable.weapons_attachments_scope_attachment;
    public static final int SILENCER_ATTACHMENT = R.drawable.weapons_attachments_silencer_attachment;

    // -- WEAPONS > EXPLOSIVES --
    public static final int C4_REMOTE_DETONATOR = R.drawable.weapons_explosive_c4_remote_detonator;
    public static final int GRENADE = R.drawable.weapons_explosive_grenade;
    public static final int IMPROVISED_EXPLOSIVE_DEVICE = R.drawable.weapons_explosive_improvised_explosive_device;
    public static final int POISON_GRENADE = R.drawable.weapons_explosive_poison_grenade;
    public static final int SMOKE_GRENADE = R.drawable.weapons_explosive_smoke_grenade;

    // -- WEAPONS > FIREARMS --
    public static final int ASSAULT_RIFLE = R.drawable.weapons_firearms_assault_rifle;
    public static final int FABRICATED_PISTOL = R.drawable.weapons_firearms_fabricated_pistol;
    public static final int FABRICATED_SNIPER_RIFLE = R.drawable.weapons_firearms_fabricated_sniper_rifle;
    public static final int LONGNECK_RIFLE = R.drawable.weapons_firearms_longneck_rifle;
    public static final int PUMP_ACTION_SHOTGUN = R.drawable.weapons_firearms_pump_action_shotgun;
    public static final int ROCKET_LAUNCHER = R.drawable.weapons_firearms_rocket_launcher;
    public static final int SHOTGUN = R.drawable.weapons_firearms_shotgun;
    public static final int SIMPLE_PISTOL = R.drawable.weapons_firearms_simple_pistol;

    // -- WEAPONS > MELEE --
    public static final int ELECTRIC_PROD = R.drawable.weapons_melee_electric_prod;
    public static final int METAL_HATCHET = R.drawable.weapons_melee_metal_hatchet;
    public static final int METAL_PICK = R.drawable.weapons_melee_metal_pick;
    public static final int METAL_SICKLE = R.drawable.weapons_melee_metal_sickle;
    public static final int PIKE = R.drawable.weapons_melee_pike;

    // -- WEAPONS > PRIMITIVE --
    // -- WEAPONS > PRIMITIVE > MELEE --
    public static final int METAL_SWORD = R.drawable.primitive_melee_metal_sword;
    public static final int SPEAR = R.drawable.primitive_melee_spear;
    public static final int STONE_HATCHET = R.drawable.primitive_melee_stone_hatchet;
    public static final int STONE_PICK = R.drawable.primitive_melee_stone_pick;
    public static final int TORCH = R.drawable.primitive_melee_torch;
    public static final int WOODEN_CLUB = R.drawable.primitive_melee_wooden_club;

    // -- WEAPONS > PRIMITIVE > RANGED --
    public static final int BOLA = R.drawable.primitive_ranged_bola;
    public static final int BOW = R.drawable.primitive_ranged_bow;
    public static final int COMPOUND_BOW = R.drawable.primitive_ranged_compound_bow;
    public static final int SLINGSHOT = R.drawable.primitive_ranged_slingshot;

    // -- WEAPONS > RANGED --
    public static final int CROSSBOW = R.drawable.weapons_ranged_crossbow;

    // -- WEAPONS > TRIPWIRE --
    public static final int TRIPWIRE_ALARM_TRAP = R.drawable.weapons_tripwires_tripwire_alarm_trap;
    public static final int TRIPWIRE_NARCOTIC_TRAP = R.drawable.weapons_tripwires_tripwire_narcotic_trap;

//    -- FUTURE ITEMS --
//    public static final int FISHING_ROD = R.drawable.;
//    public static final int TEK_GRENADE = R.drawable.weapons_explosive_tek_grenade;

    private static List<InitEngram> engrams = new ArrayList<InitEngram>() {
        {
            // -- ARMOR > CHITIN --
            add(new InitEngram(
                    CHITIN_HELMET,
                    "Chitin Helmet",
                    "Provides moderate physical protection, but can get a little warm.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.CHITIN, 15);
                        append(ResourceInitializer.HIDE, 7);
                        append(ResourceInitializer.FIBER, 3);
                    }},
                    CategoryInitializer.ARMOR.CHITIN));
            add(new InitEngram(
                    CHITIN_CHESTPIECE,
                    "Chitin Chestpiece",
                    "Provides moderate physical protection, but can get a little warm.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.CHITIN, 20);
                        append(ResourceInitializer.HIDE, 10);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.CHITIN));
            add(new InitEngram(
                    CHITIN_LEGGINGS,
                    "Chitin Leggings",
                    "Provides moderate physical protection, but can get a little warm.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.CHITIN, 25);
                        append(ResourceInitializer.HIDE, 12);
                        append(ResourceInitializer.FIBER, 5);
                    }},
                    CategoryInitializer.ARMOR.CHITIN));
            add(new InitEngram(
                    CHITIN_BOOTS,
                    "Chitin Boots",
                    "Provides moderate physical protection, but can get a little warm.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.CHITIN, 12);
                        append(ResourceInitializer.HIDE, 6);
                        append(ResourceInitializer.FIBER, 6);
                    }},
                    CategoryInitializer.ARMOR.CHITIN));
            add(new InitEngram(
                    CHITIN_GAUNTLETS,
                    "Chitin Gauntlets",
                    "Provides moderate physical protection, but can get a little warm.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.CHITIN, 10);
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.FIBER, 2);
                    }},
                    CategoryInitializer.ARMOR.CHITIN));

            // -- ARMOR > CLOTH --
            add(new InitEngram(
                    CLOTH_HAT,
                    "Cloth Hat",
                    "Provides some protection from the heat and cold, but only minimal protection from injuries.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FIBER, 10);
                    }},
                    CategoryInitializer.ARMOR.CLOTH));
            add(new InitEngram(
                    CLOTH_SHIRT,
                    "Cloth Shirt",
                    "Provides some protection from the heat and cold, but only minimal protection from injuries.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FIBER, 40);
                    }},
                    CategoryInitializer.ARMOR.CLOTH));
            add(new InitEngram(
                    CLOTH_PANTS,
                    "Cloth Pants",
                    "Provides some protection from the heat and cold, but only minimal protection from injuries.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FIBER, 50);
                    }},
                    CategoryInitializer.ARMOR.CLOTH));
            add(new InitEngram(
                    CLOTH_BOOTS,
                    "Cloth Boots",
                    "Hide-soled shoes provide some protection from the heat and cold, but only minimal protection from injuries.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FIBER, 25);
                        append(ResourceInitializer.HIDE, 6);
                    }},
                    CategoryInitializer.ARMOR.CLOTH));
            add(new InitEngram(
                    CLOTH_GLOVES,
                    "Cloth Gloves",
                    "Hide-padded gloves provide some protection from the heat and cold, but only minimal protection from injuries.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FIBER, 20);
                        append(ResourceInitializer.HIDE, 4);
                    }},
                    CategoryInitializer.ARMOR.CLOTH));

            // -- ARMOR > FUR --
            add(new InitEngram(
                    FUR_CAP,
                    "Fur Cap",
                    "Provides significant physical protection. Also keeps your temperature up, so long as it stays dry.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.PELT, 56);
                        append(ResourceInitializer.METAL_INGOT, 10);
                        append(ResourceInitializer.HIDE, 7);
                        append(ResourceInitializer.FIBER, 3);
                    }},
                    CategoryInitializer.ARMOR.FUR));
            add(new InitEngram(
                    FUR_CHESTPIECE,
                    "Fur Chestpiece",
                    "Provides significant physical protection. Also keeps your temperature up, so long as it stays dry.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.PELT, 80);
                        append(ResourceInitializer.METAL_INGOT, 13);
                        append(ResourceInitializer.HIDE, 10);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.FUR));
            add(new InitEngram(
                    FUR_LEGGINGS,
                    "Fur Leggings",
                    "Provides significant physical protection. Also keeps your temperature up, so long as it stays dry.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.PELT, 96);
                        append(ResourceInitializer.METAL_INGOT, 12);
                        append(ResourceInitializer.HIDE, 12);
                        append(ResourceInitializer.FIBER, 5);
                    }},
                    CategoryInitializer.ARMOR.FUR));
            add(new InitEngram(
                    FUR_BOOTS,
                    "Fur Boots",
                    "Provides significant physical protection. Also keeps your temperature up, so long as it stays dry.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.PELT, 48);
                        append(ResourceInitializer.METAL_INGOT, 8);
                        append(ResourceInitializer.HIDE, 6);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.FUR));
            add(new InitEngram(
                    FUR_GAUNTLETS,
                    "Fur Gauntlets",
                    "Provides significant physical protection. Also keeps your temperature up, so long as it stays dry.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.PELT, 40);
                        append(ResourceInitializer.METAL_INGOT, 6);
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.FIBER, 2);
                    }},
                    CategoryInitializer.ARMOR.FUR));

            // -- ARMOR > GHILLIE --
            add(new InitEngram(
                    GHILLIE_MASK,
                    "Ghillie Mask",
                    "Strands of Organic Polymer cause this to disperse heat and keep you cool. Camouflage keeps you more hidden from enemy creatures. Provides moderate physical protection, but less than Chitin.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.ORGANIC_POLYMER, 5);
                        append(ResourceInitializer.HIDE, 7);
                        append(ResourceInitializer.FIBER, 3);
                    }},
                    CategoryInitializer.ARMOR.GHILLIE));
            add(new InitEngram(
                    GHILLIE_CHESTPIECE,
                    "Ghillie Chestpiece",
                    "Strands of Organic Polymer cause this to disperse heat and keep you cool. Camouflage keeps you more hidden from enemy creatures. Provides moderate physical protection, but less than Chitin.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.ORGANIC_POLYMER, 6);
                        append(ResourceInitializer.HIDE, 10);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.GHILLIE));
            add(new InitEngram(
                    GHILLIE_LEGGINGS,
                    "Ghillie Leggings",
                    "Strands of Organic Polymer cause this to disperse heat and keep you cool. Camouflage keeps you more hidden from enemy creatures. Provides moderate physical protection, but less than Chitin.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.ORGANIC_POLYMER, 8);
                        append(ResourceInitializer.HIDE, 12);
                        append(ResourceInitializer.FIBER, 5);
                    }},
                    CategoryInitializer.ARMOR.GHILLIE));
            add(new InitEngram(
                    GHILLIE_BOOTS,
                    "Ghillie Boots",
                    "Strands of Organic Polymer cause this to disperse heat and keep you cool. Camouflage keeps you more hidden from enemy creatures. Provides moderate physical protection, but less than Chitin.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.ORGANIC_POLYMER, 4);
                        append(ResourceInitializer.HIDE, 6);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.GHILLIE));
            add(new InitEngram(
                    GHILLIE_GAUNTLETS,
                    "Ghillie Gauntlets",
                    "Strands of Organic Polymer cause this to disperse heat and keep you cool. Camouflage keeps you more hidden from enemy creatures. Provides moderate physical protection, but less than Chitin.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.ORGANIC_POLYMER, 3);
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.FIBER, 2);
                    }},
                    CategoryInitializer.ARMOR.GHILLIE));

            // -- ARMOR > HIDE --
            add(new InitEngram(
                    HIDE_HAT,
                    "Hide Hat",
                    "Keeps you warm while providing some physical protection.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 15);
                        append(ResourceInitializer.FIBER, 6);
                    }},
                    CategoryInitializer.ARMOR.HIDE));
            add(new InitEngram(
                    HIDE_SHIRT,
                    "Hide Shirt",
                    "Keeps you warm while providing some physical protection.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 20);
                        append(ResourceInitializer.FIBER, 8);
                    }},
                    CategoryInitializer.ARMOR.HIDE));
            add(new InitEngram(
                    HIDE_PANTS,
                    "Hide Shirt",
                    "Keeps you warm while providing some physical protection.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 25);
                        append(ResourceInitializer.FIBER, 10);
                    }},
                    CategoryInitializer.ARMOR.HIDE));
            add(new InitEngram(
                    HIDE_BOOTS,
                    "Hide Boots",
                    "Keeps you warm while providing some physical protection.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 12);
                        append(ResourceInitializer.FIBER, 5);
                    }},
                    CategoryInitializer.ARMOR.HIDE));
            add(new InitEngram(
                    HIDE_GLOVES,
                    "Hide Gloves",
                    "Keeps you warm while providing some physical protection.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 10);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.HIDE));

            // -- ARMOR > METAL --
            add(new InitEngram(
                    FLAK_HELMET,
                    "Flak Helmet",
                    "Provides heavy physical protection, but makes the elements harder to endure.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 10);
                        append(ResourceInitializer.HIDE, 7);
                        append(ResourceInitializer.FIBER, 3);
                    }},
                    CategoryInitializer.ARMOR.METAL));
            add(new InitEngram(
                    FLAK_CHESTPIECE,
                    "Flak Chestpiece",
                    "Provides heavy physical protection, but makes the elements harder to endure.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 13);
                        append(ResourceInitializer.HIDE, 10);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.METAL));
            add(new InitEngram(
                    FLAK_LEGGINGS,
                    "Flak Leggings",
                    "Provides heavy physical protection, but makes the elements harder to endure.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 16);
                        append(ResourceInitializer.HIDE, 12);
                        append(ResourceInitializer.FIBER, 5);
                    }},
                    CategoryInitializer.ARMOR.METAL));
            add(new InitEngram(
                    FLAK_BOOTS,
                    "Flak Boots",
                    "Provides heavy physical protection, but makes the elements harder to endure.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 8);
                        append(ResourceInitializer.HIDE, 6);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.METAL));
            add(new InitEngram(
                    FLAK_GAUNTLETS,
                    "Flak Gauntlets",
                    "Provides heavy physical protection, but makes the elements harder to endure.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 6);
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.FIBER, 2);
                    }},
                    CategoryInitializer.ARMOR.METAL));
            add(new InitEngram(
                    HEAVY_MINERS_HELMET,
                    "Heavy Miner's Helmet",
                    "Provides heavy physical protection, but makes the elements harder to endure.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.ELECTRONICS, 30);
                        append(ResourceInitializer.METAL_INGOT, 20);
                        append(ResourceInitializer.POLYMER, 14);
                        append(ResourceInitializer.CRYSTAL, 30);
                        append(ResourceInitializer.HIDE, 15);
                        append(ResourceInitializer.FIBER, 8);
                    }},
                    CategoryInitializer.ARMOR.METAL));

            // -- ARMOR > MISC --
            add(new InitEngram(
                    GAS_MASK,
                    "Gas Mask",
                    "Protects the wearer against various airborne poisons.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 220);
                        append(ResourceInitializer.CRYSTAL, 85);
                        append(ResourceInitializer.ABSORBENT_SUBSTRATE, 10);
                    }},
                    CategoryInitializer.ARMOR.MISC));

            // -- ARMOR > RIOT --
            add(new InitEngram(
                    RIOT_HELMET,
                    "Riot Helmet",
                    "Provides heavy physical protection and reduced physical torpor, but provides almost no protection from the elements.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 50);
                        append(ResourceInitializer.SILICA_PEARLS, 25);
                        append(ResourceInitializer.HIDE, 7);
                        append(ResourceInitializer.FIBER, 3);
                        append(ResourceInitializer.CRYSTAL, 35);
                    }},
                    CategoryInitializer.ARMOR.RIOT));
            add(new InitEngram(
                    RIOT_CHESTPIECE,
                    "Riot Chestpiece",
                    "Provides heavy physical protection and reduced physical torpor, but provides almost no protection from the elements.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 70);
                        append(ResourceInitializer.SILICA_PEARLS, 40);
                        append(ResourceInitializer.HIDE, 10);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.RIOT));
            add(new InitEngram(
                    RIOT_LEGGINGS,
                    "Riot Leggings",
                    "Provides heavy physical protection and reduced physical torpor, but provides almost no protection from the elements.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 70);
                        append(ResourceInitializer.SILICA_PEARLS, 40);
                        append(ResourceInitializer.HIDE, 12);
                        append(ResourceInitializer.FIBER, 5);
                    }},
                    CategoryInitializer.ARMOR.RIOT));
            add(new InitEngram(
                    RIOT_BOOTS,
                    "Riot Boots",
                    "Provides heavy physical protection and reduced physical torpor, but provides almost no protection from the elements.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 30);
                        append(ResourceInitializer.SILICA_PEARLS, 18);
                        append(ResourceInitializer.HIDE, 6);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.ARMOR.RIOT));
            add(new InitEngram(
                    RIOT_GAUNTLETS,
                    "Riot Gauntlets",
                    "Provides heavy physical protection and reduced physical torpor, but provides almost no protection from the elements.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 25);
                        append(ResourceInitializer.SILICA_PEARLS, 15);
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.FIBER, 2);
                    }},
                    CategoryInitializer.ARMOR.RIOT));

            // -- ARMOR > SCUBA --
            add(new InitEngram(
                    SCUBA_MASK,
                    "SCUBA Mask",
                    "Connects to a SCUBA Tank, allowing the wearer to breathe underwater for as long as the tank has oxygen.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 15);
                        append(ResourceInitializer.FIBER, 6);
                        append(ResourceInitializer.CRYSTAL, 10);
                        append(ResourceInitializer.SILICA_PEARLS, 3);
                    }},
                    CategoryInitializer.ARMOR.SCUBA));
            add(new InitEngram(
                    SCUBA_TANK,
                    "SCUBA Tank",
                    "A tank that's filled with Oxygen. Used to temporarily breathe underwater.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 20);
                        append(ResourceInitializer.FIBER, 8);
                        append(ResourceInitializer.METAL_INGOT, 14);
                        append(ResourceInitializer.POLYMER, 3);
                    }},
                    CategoryInitializer.ARMOR.SCUBA));
            add(new InitEngram(
                    SCUBA_LEGGINGS,
                    "SCUBA Leggings",
                    "Provides little defense, but tremendous hypothermal insulation, specifically when underwater.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 40);
                        append(ResourceInitializer.FIBER, 4);
                        append(ResourceInitializer.METAL_INGOT, 2);
                        append(ResourceInitializer.POLYMER, 40);
                    }},
                    CategoryInitializer.ARMOR.SCUBA));
            add(new InitEngram(
                    SCUBA_FLIPPERS,
                    "SCUBA Flippers",
                    "These flippers are firm, but flexible. Wearing them increases swim speed, but decreases walking speed.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 12);
                        append(ResourceInitializer.FIBER, 5);
                        append(ResourceInitializer.CHITIN_OR_KERATIN, 12);
                        append(ResourceInitializer.CEMENTING_PASTE, 6);
                        append(ResourceInitializer.SILICA_PEARLS, 4);
                    }},
                    CategoryInitializer.ARMOR.SCUBA));

            // -- ARMOR > SHIELDS --
            add(new InitEngram(
                    METAL_SHIELD,
                    "Metal Shield",
                    "A metal shield on a layer of leather. Absorbs damage from the front when activated.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 80);
                        append(ResourceInitializer.CEMENTING_PASTE, 20);
                        append(ResourceInitializer.HIDE, 15);
                    }},
                    CategoryInitializer.ARMOR.SHIELDS));
            add(new InitEngram(
                    WOODEN_SHIELD,
                    "Wooden Shield",
                    "A wooden shield on a layer of leather. Absorbs damage from the front when activated.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 40);
                        append(ResourceInitializer.FIBER, 15);
                        append(ResourceInitializer.HIDE, 12);
                    }},
                    CategoryInitializer.ARMOR.SHIELDS));
            add(new InitEngram(
                    RIOT_SHIELD,
                    "Metal Shield",
                    "A metal-framed shield reinforced with polymer and crystal. Absorbs damage from the front when activated.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 120);
                        append(ResourceInitializer.CRYSTAL, 200);
                        append(ResourceInitializer.SILICA_PEARLS, 120);
                        append(ResourceInitializer.METAL_INGOT, 40);
                    }},
                    CategoryInitializer.ARMOR.SHIELDS));

            // -- COMMUNICATION --
            add(new InitEngram(
                    RADIO,
                    "Radio",
                    "Use this to communicate securely over great distances.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 40);
                        append(ResourceInitializer.CRYSTAL, 40);
                        append(ResourceInitializer.SPARKPOWDER, 40);
                    }},
                    CategoryInitializer.COMMUNICATION.ID));

            // -- COMPOSITES --
            add(new InitEngram(
                    ELECTRONICS,
                    "Electronics",
                    "This multipurpose computer chip can be used to create electronic devices.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.SILICA_PEARLS, 3);
                        append(ResourceInitializer.METAL_INGOT, 1);
                    }},
                    CategoryInitializer.COMPOSITES.ID));
            add(new InitEngram(
                    POLYMER,
                    "Polymer",
                    "These incredibly strong, lightweight plates can be shaped and then heat treated into casings for anything.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.OBSIDIAN, 2);
                        append(ResourceInitializer.CEMENTING_PASTE, 2);
                    }},
                    CategoryInitializer.COMPOSITES.ID));

            // -- CONSUMABLES --
            // -- CONSUMABLES > DRUGS --
            add(new InitEngram(
                    BUG_REPELLANT,
                    "Bug Repellant",
                    "This gnarly paste makes you nearly invisible to bugs. Just don't attack them, and they'll ignore you! Effect lasts 10 minutes.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.PELT, 6);
                        append(ResourceInitializer.NARCOTIC, 2);
                        append(ResourceInitializer.CITRONAL, 4);
                        append(ResourceInitializer.ROCKARROT, 4);
                    }},
                    CategoryInitializer.CONSUMABLES.DRUGS));
            add(new InitEngram(
                    LESSER_ANTIDOTE,
                    "Lesser Antidote",
                    "Cures Lesser Afflictions!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.RARE_FLOWER, 10);
                        append(ResourceInitializer.RARE_MUSHROOM, 10);
                        append(ResourceInitializer.LEECH_BLOOD, 5);
                        append(ResourceInitializer.NARCOTIC, 1);
                    }},
                    CategoryInitializer.CONSUMABLES.DRUGS));
            add(new InitEngram(
                    NARCOTIC,
                    "Narcotic",
                    "Increases your health, but puts you to sleep.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.NARCOBERRY, 5);
                        append(ResourceInitializer.SPOILED_MEAT, 1);
                    }},
                    CategoryInitializer.CONSUMABLES.DRUGS));
            add(new InitEngram(
                    STIMULANT,
                    "Stimulant",
                    "Keeps you awake, but dehydrates you.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STIMBERRY, 5);
                        append(ResourceInitializer.SPARKPOWDER, 2);
                    }},
                    CategoryInitializer.CONSUMABLES.DRUGS));

            // -- CONSUMABLES > FOOD --
            add(new InitEngram(
                    BATTLE_TARTARE,
                    "Battle Tartare",
                    "Only eat this dish when you intend to go into a brawl. It causes pain and stress to your body, but grants you almost supernatural strength, speed, and resilience. Warning: This concoction can be habit forming.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.RAW_PRIME_MEAT, 3);
                        append(ResourceInitializer.MEJOBERRY, 20);
                        append(ResourceInitializer.STIMULANT, 8);
                        append(ResourceInitializer.RARE_FLOWER, 2);
                        append(ResourceInitializer.CITRONAL, 1);
                        append(ResourceInitializer.LONGRASS, 1);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    BEER_JAR,
                    "Beer Jar",
                    "Mmmmm.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.BEER_LIQUID, 1);
                        append(WATER_JAR, 1);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    BINGLEBERRY_SOUP,
                    "Bingleberry Soup",
                    "Eat it to gain mega nourishment, long-term fortification. (NOT YET CRAFTABLE)",
                    new SparseIntArray() {{
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    BROTH_OF_ENLIGHTENMENT,
                    "Broth of Enlightment",
                    "A single sip of this incredible broth will expand your mind and broaden your horizons, granting greatly increased experience gain for a brief time.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.MEJOBERRY, 10);
                        append(ResourceInitializer.ROCKARROT, 2);
                        append(ResourceInitializer.LONGRASS, 2);
                        append(ResourceInitializer.SAVOROOT, 2);
                        append(ResourceInitializer.CITRONAL, 2);
                        append(ResourceInitializer.WOOLLY_RHINO_HORN, 5);
                        append(ResourceInitializer.BLACK_PEARL, 1);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    CALIEN_SOUP,
                    "Calien Soup",
                    "This simple vegetarian dish refreshes you body like an oasis. Helps keep you stay hydrated and feel cool.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.CITRONAL, 5);
                        append(ResourceInitializer.TINTOBERRY, 20);
                        append(ResourceInitializer.AMARBERRY, 20);
                        append(ResourceInitializer.MEJOBERRY, 10);
                        append(ResourceInitializer.STIMULANT, 2);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    ENDURO_STEW,
                    "Enduro Stew",
                    "This hearty dish is like a workout in the form of a meal. You will find yourself hitting harder and running longer after eating this.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.COOKED_MEAT_OR_FISH_MEAT, 9);
                        append(ResourceInitializer.ROCKARROT, 5);
                        append(ResourceInitializer.SAVOROOT, 5);
                        append(ResourceInitializer.MEJOBERRY, 10);
                        append(ResourceInitializer.STIMULANT, 2);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    ENERGY_BREW,
                    "Energy Brew",
                    "his brew doesn't have any nutritional value, but fills your stamina.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AZULBERRY, 20);
                        append(ResourceInitializer.STIMULANT, 2);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    FOCAL_CHILI,
                    "Focal Chili",
                    "This filling dish is full of nutritional energy. Consume it to gain increased crafting speed, and gain increased movement speed. Effect lasts 15 minutes.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.COOKED_MEAT, 9);
                        append(ResourceInitializer.CITRONAL, 5);
                        append(ResourceInitializer.AMARBERRY, 20);
                        append(ResourceInitializer.AZULBERRY, 20);
                        append(ResourceInitializer.TINTOBERRY, 20);
                        append(ResourceInitializer.MEJOBERRY, 10);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    FRIA_CURRY,
                    "Fria Curry",
                    "his spicy vegetarian dish fills the body with a comfortable warmth. It controls your appetite while helping you ignore cold.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.LONGRASS, 5);
                        append(ResourceInitializer.ROCKARROT, 5);
                        append(ResourceInitializer.AZULBERRY, 20);
                        append(ResourceInitializer.MEJOBERRY, 10);
                        append(ResourceInitializer.NARCOTIC, 2);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    LAZARUS_CHOWDER,
                    "Lazarus Chowder",
                    "This creamy dish improves the body's natural constitution. You will recover from injury more quickly after eating this, and your body will need less oxygen.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.COOKED_MEAT, 9);
                        append(ResourceInitializer.SAVOROOT, 5);
                        append(ResourceInitializer.LONGRASS, 5);
                        append(ResourceInitializer.MEJOBERRY, 10);
                        append(ResourceInitializer.NARCOTIC, 2);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    MEDICAL_BREW,
                    "Medical Brew",
                    "This brew doesn't have any nutritional value, but it promotes healing.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.TINTOBERRY, 20);
                        append(ResourceInitializer.NARCOTIC, 2);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    MINDWIPE_TONIC,
                    "Mindwipe Tonic",
                    "When consumed, this tonic causes neural overload. Synapses fire off too quickly resulting in damage to the memory centers of the brain related to construction. Warning: this concoction may cause temporary memory loss, and resetting of Engrams.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.COOKED_PRIME_MEAT, 24);
                        append(ResourceInitializer.MEJOBERRY, 200);
                        append(ResourceInitializer.NARCOTIC, 72);
                        append(ResourceInitializer.STIMULANT, 72);
                        append(ResourceInitializer.RARE_MUSHROOM, 20);
                        append(ResourceInitializer.RARE_FLOWER, 20);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    SHADOW_STEAK_SAUCE,
                    "Shadow Steak Sauce",
                    "Only eat this dish in the dark. It causes the light receptors in your eyes to become hyperactive, improves your hand-eye coordination, and allows your body to ignore extreme temperatures. Warning: this concoction can be habit-forming.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.COOKED_PRIME_MEAT, 3);
                        append(ResourceInitializer.MEJOBERRY, 20);
                        append(ResourceInitializer.NARCOTIC, 8);
                        append(ResourceInitializer.RARE_MUSHROOM, 2);
                        append(ResourceInitializer.SAVOROOT, 1);
                        append(ResourceInitializer.ROCKARROT, 1);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));
            add(new InitEngram(
                    SWEET_VEGGIE_CAKE,
                    "Sweet Veggie Cake",
                    "A sappy-sweet vegetable cake which Herbivores find delicious. Sadly, humans and carnivores tend to find its taste repugnant.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.SAP, 7);
                        append(ResourceInitializer.ROCKARROT, 2);
                        append(ResourceInitializer.LONGRASS, 2);
                        append(ResourceInitializer.SAVOROOT, 2);
                        append(ResourceInitializer.STIMULANT, 4);
                        append(ResourceInitializer.FIBER, 25);
                    }},
                    CategoryInitializer.CONSUMABLES.FOOD));

            // -- DYES --
            add(new InitEngram(
                    BLACK_COLORING,
                    "Black",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.NARCOBERRY, 15);
                        append(ResourceInitializer.CHARCOAL, 2);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    BLUE_COLORING,
                    "Blue",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AZULBERRY, 15);
                        append(ResourceInitializer.CHARCOAL, 2);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    BRICK_COLORING,
                    "Brick",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.TINTOBERRY, 12);
                        append(ResourceInitializer.NARCOBERRY, 6);
                        append(ResourceInitializer.SPARKPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    BROWN_COLORING,
                    "Brown",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 6);
                        append(ResourceInitializer.AZULBERRY, 3);
                        append(ResourceInitializer.TINTOBERRY, 9);
                        append(ResourceInitializer.CHARCOAL, 2);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    CANTALOUPE_COLORING,
                    "Cantaloupe",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 7);
                        append(ResourceInitializer.TINTOBERRY, 7);
                        append(ResourceInitializer.STIMBERRY, 4);
                        append(ResourceInitializer.SPARKPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    CYAN_COLORING,
                    "Cyan",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 6);
                        append(ResourceInitializer.AZULBERRY, 12);
                        append(ResourceInitializer.SPARKPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    FOREST_COLORING,
                    "Forest",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 7);
                        append(ResourceInitializer.AZULBERRY, 7);
                        append(ResourceInitializer.NARCOBERRY, 4);
                        append(ResourceInitializer.GUNPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    GREEN_COLORING,
                    "Green",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 9);
                        append(ResourceInitializer.AZULBERRY, 9);
                        append(ResourceInitializer.CHARCOAL, 2);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    MAGENTA_COLORING,
                    "Magenta",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AZULBERRY, 9);
                        append(ResourceInitializer.TINTOBERRY, 9);
                        append(ResourceInitializer.SPARKPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    MUD_COLORING,
                    "Mud",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 4);
                        append(ResourceInitializer.AZULBERRY, 1);
                        append(ResourceInitializer.TINTOBERRY, 7);
                        append(ResourceInitializer.NARCOBERRY, 6);
                        append(ResourceInitializer.SPARKPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    NAVY_COLORING,
                    "Navy",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AZULBERRY, 12);
                        append(ResourceInitializer.NARCOBERRY, 6);
                        append(ResourceInitializer.SPARKPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    OLIVE_COLORING,
                    "Olive",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 12);
                        append(ResourceInitializer.NARCOBERRY, 6);
                        append(ResourceInitializer.SPARKPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    ORANGE_COLORING,
                    "Orange",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 9);
                        append(ResourceInitializer.TINTOBERRY, 9);
                        append(ResourceInitializer.CHARCOAL, 2);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    PARCHMENT_COLORING,
                    "Parchment",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 12);
                        append(ResourceInitializer.STIMBERRY, 6);
                        append(ResourceInitializer.GUNPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    PINK_COLORING,
                    "Pink",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.TINTOBERRY, 12);
                        append(ResourceInitializer.STIMBERRY, 6);
                        append(ResourceInitializer.GUNPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    PURPLE_COLORING,
                    "Purple",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AZULBERRY, 9);
                        append(ResourceInitializer.TINTOBERRY, 9);
                        append(ResourceInitializer.CHARCOAL, 2);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    RED_COLORING,
                    "Red",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.TINTOBERRY, 15);
                        append(ResourceInitializer.CHARCOAL, 2);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    ROYALTY_COLORING,
                    "Royalty",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AZULBERRY, 7);
                        append(ResourceInitializer.TINTOBERRY, 7);
                        append(ResourceInitializer.NARCOBERRY, 4);
                        append(ResourceInitializer.GUNPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    SILVER_COLORING,
                    "Silver",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.NARCOBERRY, 6);
                        append(ResourceInitializer.STIMBERRY, 12);
                        append(ResourceInitializer.GUNPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    SKY_COLORING,
                    "Sky",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AZULBERRY, 12);
                        append(ResourceInitializer.STIMBERRY, 6);
                        append(ResourceInitializer.GUNPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    SLATE_COLORING,
                    "Slate",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.NARCOBERRY, 12);
                        append(ResourceInitializer.STIMBERRY, 6);
                        append(ResourceInitializer.SPARKPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    TAN_COLORING,
                    "Tan",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 4);
                        append(ResourceInitializer.AZULBERRY, 1);
                        append(ResourceInitializer.TINTOBERRY, 7);
                        append(ResourceInitializer.STIMBERRY, 6);
                        append(ResourceInitializer.GUNPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    TANGERINE_COLORING,
                    "Tangerine",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 7);
                        append(ResourceInitializer.TINTOBERRY, 7);
                        append(ResourceInitializer.NARCOBERRY, 4);
                        append(ResourceInitializer.GUNPOWDER, 1);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    WHITE_COLORING,
                    "White",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STIMBERRY, 15);
                        append(ResourceInitializer.CHARCOAL, 2);
                    }},
                    CategoryInitializer.DYES.ID));
            add(new InitEngram(
                    YELLOW_COLORING,
                    "Yellow",
                    "",
                    new SparseIntArray() {{
                        append(ResourceInitializer.AMARBERRY, 15);
                        append(ResourceInitializer.CHARCOAL, 1);
                    }},
                    CategoryInitializer.DYES.ID));

            // -- MISC --
            add(new InitEngram(
                    BLOOD_EXTRACTION_SYRINGE,
                    "Blood Extraction Syringe",
                    "Use this on a human to extract their blood for transfusion.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 10);
                        append(ResourceInitializer.CHITIN_OR_KERATIN, 1);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    CANTEEN,
                    "Canteen",
                    "Safely carries a sizable amount of water, and is reasonably lightweight.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 10);
                        append(ResourceInitializer.HIDE, 2);
                        append(ResourceInitializer.CEMENTING_PASTE, 4);
                        append(ResourceInitializer.METAL_INGOT, 1);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    FLARE_GUN,
                    "Flare Gun",
                    "A single-use flare launcher. Fires a bright ball of Sparkpowder to temporarily light an area.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 4);
                        append(ResourceInitializer.FIBER, 2);
                        append(ResourceInitializer.SPARKPOWDER, 10);
                        append(ResourceInitializer.GUNPOWDER, 2);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    MAGNIFYING_GLASS,
                    "Magnifying Glass",
                    "This instrument, while primitive, is effective as short-range analysis of both flora and fauna.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 15);
                        append(ResourceInitializer.HIDE, 20);
                        append(ResourceInitializer.FIBER, 20);
                        append(ResourceInitializer.CRYSTAL, 30);
                        append(ResourceInitializer.OBSIDIAN, 15);
                        append(ResourceInitializer.METAL_INGOT, 8);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    NOTE,
                    "Note",
                    "Write your own text on a note! Or put it in a Cooking Pot to make a Custom Recipe!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 3);
                        append(ResourceInitializer.FIBER, 1);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    PAINTBRUSH,
                    "Paintbrush",
                    "Apply a dye to this, then swing it at structures to paint them.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 1);
                        append(ResourceInitializer.HIDE, 3);
                        append(ResourceInitializer.THATCH, 10);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    PARACHUTE,
                    "Parachute",
                    "Use this while falling to slow your fall. Jump while parachuting to deactivate it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 40);
                        append(ResourceInitializer.FIBER, 20);
                        append(ResourceInitializer.HIDE, 10);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    REFERTILIZER,
                    "Re-Fertilizer",
                    "Spread these seeds of concentrated nutrients around and what was once harvested may yet regrow, even nearby a structure!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.RARE_MUSHROOM, 1);
                        append(ResourceInitializer.RARE_FLOWER, 1);
                        append(ResourceInitializer.SPARKPOWDER, 4);
                        append(ResourceInitializer.FERTILIZER, 1);
                        append(ResourceInitializer.OIL, 3);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    SPRAY_PAINTER,
                    "Spray Painter",
                    "Apply a dye to this, then shoot it at structures to paint them. Hold Alt Fire + Hotkey Number to set painting region.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 50);
                        append(ResourceInitializer.POLYMER, 35);
                        append(ResourceInitializer.CRYSTAL, 40);
                        append(ResourceInitializer.CEMENTING_PASTE, 60);
                        append(ResourceInitializer.OIL, 20);
                        append(ResourceInitializer.HIDE, 10);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    SPYGLASS,
                    "Spyglass",
                    "This instrument, while primitive, is quite effective at long-range reconnaissance.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 5);
                        append(ResourceInitializer.HIDE, 10);
                        append(ResourceInitializer.FIBER, 10);
                        append(ResourceInitializer.CRYSTAL, 2);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    WATER_JAR,
                    "Water Jar",
                    "Safely carries a lot of water, but is also a bit heavy.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.CEMENTING_PASTE, 7);
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.CRYSTAL, 2);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    WATERSKIN,
                    "Waterskin",
                    "Good to keep you hydrated, but slowly leaks water.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 4);
                        append(ResourceInitializer.FIBER, 12);
                    }},
                    CategoryInitializer.MISC.ID));
            add(new InitEngram(
                    WOODEN_RAFT,
                    "Wooden Raft",
                    "A floating wooden platform that you can pilot across the water. Can support the weight of structures and be built on.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 250);
                        append(ResourceInitializer.FIBER, 125);
                        append(ResourceInitializer.HIDE, 75);
                    }},
                    CategoryInitializer.MISC.ID));

            // -- NAVIGATION --
            add(new InitEngram(
                    COMPASS,
                    "Compass",
                    "Use this to find which direction you are traveling.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_ORE, 5);
                        append(ResourceInitializer.FLINT, 5);
                        append(ResourceInitializer.FIBER, 30);
                    }},
                    CategoryInitializer.NAVIGATION.ID));
            add(new InitEngram(
                    GPS,
                    "GPS",
                    "Detects strange energy from the three Obelisks to triangulate your exact location on the island.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 5);
                        append(ResourceInitializer.ELECTRONICS, 20);
                    }},
                    CategoryInitializer.NAVIGATION.ID));
            add(new InitEngram(
                    TRANSPONDER_NODE,
                    "Transponder Node",
                    "Can be attached to living Creatures to track their location.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 3);
                        append(ResourceInitializer.CEMENTING_PASTE, 12);
                        append(ResourceInitializer.ELECTRONICS, 14);
                        append(ResourceInitializer.POLYMER, 10);
                        append(ResourceInitializer.CRYSTAL, 12);
                    }},
                    CategoryInitializer.NAVIGATION.ID));
            add(new InitEngram(
                    TRANSPONDER_TRACKER,
                    "Transponder Tracker",
                    "Uses strange energy from the three Obelisks to triangulate all Transponder Nodes on the specified frequency.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 20);
                        append(ResourceInitializer.CEMENTING_PASTE, 30);
                        append(ResourceInitializer.ELECTRONICS, 80);
                        append(ResourceInitializer.POLYMER, 50);
                        append(ResourceInitializer.CRYSTAL, 25);
                    }},
                    CategoryInitializer.NAVIGATION.ID));

            // -- OFFHAND --
            add(new InitEngram(
                    HANDCUFFS,
                    "Handcuffs",
                    "Equip this onto an unconscious player, and they'll be restrained when they wake up!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 50);
                        append(ResourceInitializer.CEMENTING_PASTE, 10);
                        append(ResourceInitializer.OBSIDIAN, 5);
                    }},
                    CategoryInitializer.OFFHAND.ID));

            // -- REFINED --
            add(new InitEngram(
                    ABSORBENT_SUBSTRATE,
                    "Absorbent Substrate",
                    "This sticky compound excels at absorbing other chemicals.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.BLACK_PEARL, 8);
                        append(ResourceInitializer.SAP, 8);
                        append(ResourceInitializer.OIL, 8);
                    }},
                    CategoryInitializer.REFINED.ID));
            add(new InitEngram(
                    BEER_LIQUID,
                    "Beer Liquid",
                    "Put it in a glass jar to drink it!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 40);
                        append(ResourceInitializer.BERRIES, 50);
                    }},
                    CategoryInitializer.REFINED.ID));
            add(new InitEngram(
                    CEMENTING_PASTE,
                    "Cementing Paste",
                    "Paste created at Mortar and Pestle.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.CHITIN_OR_KERATIN, 4);
                        append(ResourceInitializer.STONE, 8);
                    }},
                    CategoryInitializer.REFINED.ID));
            add(new InitEngram(
                    CHARCOAL,
                    "Charcoal",
                    "Created by burning wood.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 1);
                    }},
                    CategoryInitializer.REFINED.ID));
            add(new InitEngram(
                    FERTILIZER,
                    "Fertilizer",
                    "A fertilizer high in nitrogen. Use this to help your crops grow.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 50);
                        append(ResourceInitializer.FECES, 3);
                    }},
                    CategoryInitializer.REFINED.ID));
            add(new InitEngram(
                    GASOLINE,
                    "Gasoline",
                    "An advanced fuel. Can only be used in machines designed to consume it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.OIL, 3);
                    }},
                    CategoryInitializer.REFINED.ID));
            add(new InitEngram(
                    GUNPOWDER,
                    "Gunpowder",
                    "A powerful propellant. Necessary for any firearm or explosive.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.SPARKPOWDER, 1);
                        append(ResourceInitializer.CHARCOAL, 1);
                    }},
                    CategoryInitializer.REFINED.ID));
            add(new InitEngram(
                    METAL_INGOT,
                    "Metal Ingot",
                    "Created by refining metal ore in a forge.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_ORE, 2);
                    }},
                    CategoryInitializer.REFINED.ID));
            add(new InitEngram(
                    SPARKPOWDER,
                    "Sparkpowder",
                    "Created by grinding flint with stone in a Mortar and Pestle.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FLINT, 2);
                        append(ResourceInitializer.STONE, 1);
                    }},
                    CategoryInitializer.REFINED.ID));

            // -- SADDLES --
            add(new InitEngram(
                    ANKYLO,
                    "Ankylo Saddle",
                    "Equip an Ankylo with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 260);
                        append(ResourceInitializer.FIBER, 140);
                        append(ResourceInitializer.METAL_INGOT, 10);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    ARANEO,
                    "Araneo Saddle",
                    "Equip an Araneo with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 260);
                        append(ResourceInitializer.FIBER, 140);
                        append(ResourceInitializer.CHITIN_OR_KERATIN, 100);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    ARGENTAVIS,
                    "Argentavis Saddle",
                    "Equip an Argentavis with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 350);
                        append(ResourceInitializer.FIBER, 185);
                        append(ResourceInitializer.CHITIN_OR_KERATIN, 150);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    ARTHROPLUERA,
                    "Arthropluera Saddle",
                    "Equip an Arthropluera with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 80);
                        append(ResourceInitializer.FIBER, 50);
                        append(ResourceInitializer.WOOD, 20);
                        append(ResourceInitializer.METAL_INGOT, 30);
                        append(ResourceInitializer.FLINT, 8);
                        append(ResourceInitializer.CEMENTING_PASTE, 15);
                        append(ResourceInitializer.OBSIDIAN, 35);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    BEELZEBUFO,
                    "Beelzebufo Saddle",
                    "Equip a Beelzebufo with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 170);
                        append(ResourceInitializer.FIBER, 95);
                        append(ResourceInitializer.WOOD, 30);
                        append(ResourceInitializer.CEMENTING_PASTE, 5);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    BRONTO,
                    "Bronto Saddle",
                    "Equip a Bronto with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 350);
                        append(ResourceInitializer.FIBER, 185);
                        append(ResourceInitializer.METAL_INGOT, 40);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    BRONTO_PLATFORM,
                    "Bronto Platform Saddle",
                    "Equip a Bronto with this to ride it. You can build structures on the large platform to make a mobile base.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 550);
                        append(ResourceInitializer.FIBER, 325);
                        append(ResourceInitializer.METAL_INGOT, 90);
                        append(ResourceInitializer.SILICA_PEARLS, 125);
                        append(ResourceInitializer.CEMENTING_PASTE, 45);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    CARBONEMYS,
                    "Carbonemys Saddle",
                    "Equip a Carboenemys with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 170);
                        append(ResourceInitializer.FIBER, 95);
                        append(ResourceInitializer.CEMENTING_PASTE, 10);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    CARNO,
                    "Carno Saddle",
                    "Equip a Carnotaurus with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 320);
                        append(ResourceInitializer.FIBER, 170);
                        append(ResourceInitializer.METAL_INGOT, 30);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    CASTOROIDES,
                    "Castoroides Saddle",
                    "Equip a Giant Beaver with this to ride it. Can be used as a mobile Smithy! Sounds pretty good eh?",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 290);
                        append(ResourceInitializer.FIBER, 200);
                        append(ResourceInitializer.METAL_INGOT, 100);
                        append(ResourceInitializer.THATCH, 180);
                        append(ResourceInitializer.CEMENTING_PASTE, 140);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    DIPLODOCUS,
                    "Diplodocus Saddle",
                    "A saddle for your Diplodocus. It has ten seats! Bring lots of friends!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 820);
                        append(ResourceInitializer.FIBER, 600);
                        append(ResourceInitializer.WOOD, 250);
                        append(ResourceInitializer.METAL_INGOT, 200);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    DIREBEAR,
                    "Dire Bear Saddle",
                    "Equip a Dire Bear with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 300);
                        append(ResourceInitializer.FIBER, 130);
                        append(ResourceInitializer.CEMENTING_PASTE, 100);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    DOEDICURUS,
                    "Doedicurus Saddle",
                    "Equip a Doedicurus with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 200);
                        append(ResourceInitializer.FIBER, 110);
                        append(ResourceInitializer.STONE, 15);
                        append(ResourceInitializer.METAL_INGOT, 5);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    DUNKLEOSTEUS,
                    "Dunkleosteus",
                    "Equip a Dunkleosteus with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 300);
                        append(ResourceInitializer.FIBER, 180);
                        append(ResourceInitializer.CEMENTING_PASTE, 120);
                        append(ResourceInitializer.METAL_INGOT, 80);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    GALLIMIMUS,
                    "Gallimimus Saddle",
                    "A saddle for your Gallimimus. It has three seats! Bring your friends!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 240);
                        append(ResourceInitializer.FIBER, 160);
                        append(ResourceInitializer.WOOD, 120);
                        append(ResourceInitializer.METAL_INGOT, 25);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    GIGANOTOSAURUS,
                    "Giganotosaurus Saddle",
                    "Equip a Giganotosaurus with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 680);
                        append(ResourceInitializer.FIBER, 350);
                        append(ResourceInitializer.METAL_INGOT, 120);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    ICHTHYOSAURUS,
                    "Ichthyosaurus Saddle",
                    "Equip an Ichthyosaurus with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 55);
                        append(ResourceInitializer.FIBER, 50);
                        append(ResourceInitializer.WOOD, 20);
                        append(ResourceInitializer.METAL_ORE, 10);
                        append(ResourceInitializer.FLINT, 8);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    MAMMOTH,
                    "Mammoth Saddle",
                    "Equip a Mammoth with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 260);
                        append(ResourceInitializer.FIBER, 140);
                        append(ResourceInitializer.METAL_INGOT, 10);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    MANTA,
                    "Manta Saddle",
                    "Equip a Manta with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 100);
                        append(ResourceInitializer.FIBER, 70);
                        append(ResourceInitializer.WOOD, 25);
                        append(ResourceInitializer.METAL_INGOT, 35);
                        append(ResourceInitializer.FLINT, 12);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    MEGALOCEROS,
                    "Megaloceros Saddle",
                    "Equip a Megaloceros with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 200);
                        append(ResourceInitializer.FIBER, 110);
                        append(ResourceInitializer.METAL_INGOT, 5);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    MEGALODON,
                    "Megalodon Saddle",
                    "Equip a Megalodon with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 290);
                        append(ResourceInitializer.FIBER, 155);
                        append(ResourceInitializer.CEMENTING_PASTE, 30);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    MOSASAURUS,
                    "Mosasaurus Saddle",
                    "Equip a Mosasaurus with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 800);
                        append(ResourceInitializer.FIBER, 600);
                        append(ResourceInitializer.CEMENTING_PASTE, 140);
                        append(ResourceInitializer.SILICA_PEARLS, 100);
                        append(ResourceInitializer.METAL_INGOT, 400);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    MOSASAURUS_PLATFORM,
                    "Mosasaurus Platform Saddle",
                    "Equip a Mosasaurus with this to ride it. You can build structures on the large platform to make a mobile base.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 960);
                        append(ResourceInitializer.FIBER, 720);
                        append(ResourceInitializer.METAL_INGOT, 1200);
                        append(ResourceInitializer.SILICA_PEARLS, 320);
                        append(ResourceInitializer.CEMENTING_PASTE, 180);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PACHY,
                    "Pachy Saddle",
                    "Equip a Pachy with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 110);
                        append(ResourceInitializer.FIBER, 65);
                        append(ResourceInitializer.WOOD, 20);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PARACER,
                    "Paracer Saddle",
                    "Equip a Paraceratherium with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 200);
                        append(ResourceInitializer.FIBER, 110);
                        append(ResourceInitializer.METAL_INGOT, 10);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PARACER_PLATFORM,
                    "Paracer Platform Saddle",
                    "Equip a Paraceratherium with this to ride it. You can build structures on the large platform to make a mobile base.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 320);
                        append(ResourceInitializer.FIBER, 200);
                        append(ResourceInitializer.METAL_INGOT, 70);
                        append(ResourceInitializer.SILICA_PEARLS, 45);
                        append(ResourceInitializer.CEMENTING_PASTE, 25);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PARASAUR,
                    "Parasaur Saddle",
                    "Equip a Parasaur with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 80);
                        append(ResourceInitializer.FIBER, 50);
                        append(ResourceInitializer.WOOD, 15);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PHIOMIA,
                    "Phiomia Saddle",
                    "Equip a Phiomia with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 20);
                        append(ResourceInitializer.FIBER, 15);
                        append(ResourceInitializer.WOOD, 5);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PLESIOSAUR,
                    "Plesiosaur Saddle",
                    "Equip a Plesiosaur with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 400);
                        append(ResourceInitializer.FIBER, 250);
                        append(ResourceInitializer.CEMENTING_PASTE, 65);
                        append(ResourceInitializer.SILICA_PEARLS, 40);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PLESIOSAUR_PLATFORM,
                    "Plesiosaur Platform Saddle",
                    "Equip a Plesiosaur with this to ride it. You can build structures on the large platform to make a mobile base.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 680);
                        append(ResourceInitializer.FIBER, 405);
                        append(ResourceInitializer.METAL_INGOT, 112);
                        append(ResourceInitializer.SILICA_PEARLS, 155);
                        append(ResourceInitializer.CEMENTING_PASTE, 55);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PROCOPTODON,
                    "Procoptodon Saddle",
                    "Equip a Procoptodon with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 550);
                        append(ResourceInitializer.PELT, 150);
                        append(ResourceInitializer.FIBER, 200);
                        append(ResourceInitializer.METAL_INGOT, 70);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PTERANODON,
                    "Pteranodon Saddle",
                    "Equip a Pteranodon with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 230);
                        append(ResourceInitializer.FIBER, 125);
                        append(ResourceInitializer.CHITIN_OR_KERATIN, 75);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    PULMONOSCORPIUS,
                    "Pulmonoscorpius Saddle",
                    "Equip a Pulmonoscorpius with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 170);
                        append(ResourceInitializer.FIBER, 95);
                        append(ResourceInitializer.WOOD, 3);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    QUETZAL,
                    "Quetzal Saddle",
                    "Equip a Quetzal with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 750);
                        append(ResourceInitializer.FIBER, 500);
                        append(ResourceInitializer.CEMENTING_PASTE, 100);
                        append(ResourceInitializer.SILICA_PEARLS, 85);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    QUETZAL_PLATFORM,
                    "Quetzal Platform Saddle",
                    "Equip a Quetzal with this to ride it. You can build structures on the large platform to make a mobile base.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 620);
                        append(ResourceInitializer.FIBER, 400);
                        append(ResourceInitializer.METAL_INGOT, 180);
                        append(ResourceInitializer.SILICA_PEARLS, 220);
                        append(ResourceInitializer.CEMENTING_PASTE, 120);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    RAPTOR,
                    "Raptor Saddle",
                    "Equip a Raptor with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 110);
                        append(ResourceInitializer.FIBER, 65);
                        append(ResourceInitializer.WOOD, 20);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    REX,
                    "Rex Saddle",
                    "Equip a T-Rex with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 380);
                        append(ResourceInitializer.FIBER, 200);
                        append(ResourceInitializer.METAL_INGOT, 50);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    SABERTOOTH,
                    "Sabertooth Saddle",
                    "Equip a Sabertooth with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 290);
                        append(ResourceInitializer.FIBER, 155);
                        append(ResourceInitializer.METAL_INGOT, 20);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    SARCO,
                    "Sarco Saddle",
                    "Equip a Sarco with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 230);
                        append(ResourceInitializer.FIBER, 75);
                        append(ResourceInitializer.CEMENTING_PASTE, 20);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    SPINO,
                    "Spino Saddle",
                    "Equip a Spino with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 380);
                        append(ResourceInitializer.FIBER, 200);
                        append(ResourceInitializer.CEMENTING_PASTE, 45);
                        append(ResourceInitializer.SILICA_PEARLS, 25);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    STEGO,
                    "Stego Saddle",
                    "Equip a Stego with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 200);
                        append(ResourceInitializer.FIBER, 110);
                        append(ResourceInitializer.WOOD, 35);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    TERROR_BIRD,
                    "Terror Bird",
                    "Equip a Terror Bird with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 110);
                        append(ResourceInitializer.FIBER, 65);
                        append(ResourceInitializer.WOOD, 20);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    TITANOSAUR_PLATFORM,
                    "Titanosaur Platform Saddle",
                    "Equip a Titanosaur with this to ride it. You can build structures on the large platform to make a mobile base.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 4000);
                        append(ResourceInitializer.FIBER, 2000);
                        append(ResourceInitializer.METAL_INGOT, 4000);
                        append(ResourceInitializer.CEMENTING_PASTE, 1600);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    TRIKE,
                    "Trike Saddle",
                    "Equip a Triceratops with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 140);
                        append(ResourceInitializer.FIBER, 80);
                        append(ResourceInitializer.WOOD, 25);
                    }},
                    CategoryInitializer.SADDLES.ID));
            add(new InitEngram(
                    WOOLLY_RHINO,
                    "Woolly Rhino Saddle",
                    "Equip a Woolly Rhino with this to ride it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 250);
                        append(ResourceInitializer.FIBER, 130);
                        append(ResourceInitializer.CEMENTING_PASTE, 100);
                        append(ResourceInitializer.METAL_INGOT, 60);
                    }},
                    CategoryInitializer.SADDLES.ID));

            // -- WEAPONS > AMMO --
            add(new InitEngram(
                    ADVANCED_BULLET,
                    "Advanced Bullet",
                    "A small-caliber modern bullet, primarily used with fabricated handguns.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 1);
                        append(ResourceInitializer.GUNPOWDER, 3);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    ADVANCED_RIFLE_BULLET,
                    "Advanced Rifle Bullet",
                    "Primarily used with fabricated rifles.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 1);
                        append(ResourceInitializer.GUNPOWDER, 9);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    ADVANCED_SNIPER_BULLET,
                    "Advanced Sniper Bullet",
                    "A high-caliber modern bullet, primarily used with fabricated sniper rifles",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 2);
                        append(ResourceInitializer.GUNPOWDER, 12);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    BALLISTA_BOLT,
                    "Ballista Bolt",
                    "A massive Bolt with a cast iron arrow head. Can only be used in a Ballista Turret. If it doesn't break, you can recover your ammo.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 14);
                        append(ResourceInitializer.WOOD, 14);
                        append(ResourceInitializer.THATCH, 20);
                        append(ResourceInitializer.FLINT, 5);
                        append(ResourceInitializer.FIBER, 20);
                        append(ResourceInitializer.CEMENTING_PASTE, 2);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    BOULDER,
                    "Boulder",
                    "Massive rock with metal spikes. Can only be used in a Catapult Turret.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 30);
                        append(ResourceInitializer.METAL_INGOT, 3);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    C4_CHARGE,
                    "C4 Charge",
                    "This advanced explosive can annihilate structures.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 1);
                        append(ResourceInitializer.GUNPOWDER, 3);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    CANNON_BALL,
                    "Cannon Ball",
                    "A gigantic ball of heavy metal, capable of demolishing very strong structures.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 1);
                        append(ResourceInitializer.GUNPOWDER, 3);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    CHAIN_BOLA,
                    "Chain Bola",
                    "A gigantic bola made of metal chain, capable of ensnaring larger creatures. Usable within a Ballista turret.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 65);
                        append(ResourceInitializer.CEMENTING_PASTE, 10);
                        append(ResourceInitializer.OBSIDIAN, 2);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    GRAPPLING_HOOK,
                    "Grappling Hook",
                    "Apply on the Crossbow to grapple onto the environment & other creatures. Primary Fire to \"Reel-In\", Secondary Fire to \"Reel-Out\", Crouch to Detach, Jump + Reel-In to Vault-Jump!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE_ARROW, 3);
                        append(ResourceInitializer.CEMENTING_PASTE, 1);
                        append(ResourceInitializer.METAL_INGOT, 9);
                        append(ResourceInitializer.THATCH, 2);
                        append(ResourceInitializer.FIBER, 35);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    METAL_ARROW,
                    "Metal Arrow",
                    "An armor-and-hide piercing metal arrow.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 3);
                        append(ResourceInitializer.FIBER, 3);
                        append(ResourceInitializer.METAL_INGOT, 3);
                        append(ResourceInitializer.CEMENTING_PASTE, 1);
                        append(ResourceInitializer.POLYMER, 1);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    ROCKET_PROPELLED_GRENADE,
                    "Rocket Propelled Grenade",
                    "A small but powerful explosive, primarily used with rocket launchers.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.GUNPOWDER, 40);
                        append(ResourceInitializer.CRYSTAL, 10);
                        append(ResourceInitializer.CEMENTING_PASTE, 20);
                        append(ResourceInitializer.POLYMER, 10);
                        append(ResourceInitializer.METAL_INGOT, 12);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    SIMPLE_BULLET,
                    "Simple Bullet",
                    "Primarily used with forged handguns.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 1);
                        append(ResourceInitializer.GUNPOWDER, 6);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    SIMPLE_RIFLE_AMMO,
                    "Simple Rifle Ammo",
                    "An odd bullet and casing, used with forged rifles.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 2);
                        append(ResourceInitializer.GUNPOWDER, 12);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    SIMPLE_SHOTGUN_AMMO,
                    "Simple Shotgun Ammo",
                    "A few simple bullets in a hide casing, used with forged shotguns.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 1);
                        append(ResourceInitializer.GUNPOWDER, 3);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    STONE_ARROW,
                    "Stone Arrow",
                    "An arrow for the bow.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 2);
                        append(ResourceInitializer.FIBER, 2);
                        append(ResourceInitializer.FLINT, 1);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    TRANQUILIZER_ARROW,
                    "Tranquilizer Arrow",
                    "Less deadly than other arrows, but rapidly increases the victim's torpidity.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE_ARROW, 1);
                        append(ResourceInitializer.NARCOTIC, 1);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));
            add(new InitEngram(
                    TRANQUILIZER_DART,
                    "Tranquilizer Dart",
                    "A potent dart full of tranquilizing poison. For use in a Longneck Rifle. Can not be used with attachments.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.NARCOTIC, 3);
                        append(ResourceInitializer.METAL_INGOT, 2);
                        append(ResourceInitializer.SIMPLE_RIFLE_AMMO, 1);
                    }},
                    CategoryInitializer.WEAPONS.AMMO));

            // -- WEAPONS > ATTACHMENTS --
            add(new InitEngram(
                    FLASHLIGHT_ATTACHMENT,
                    "Flashlight Attachment",
                    "This flashlight sheds light out in a wide area, but makes you easier to see too. Attach this to a supporting weapon to shine light from a weapon.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 40);
                        append(ResourceInitializer.CRYSTAL, 40);
                        append(ResourceInitializer.ELECTRONICS, 10);
                    }},
                    CategoryInitializer.WEAPONS.ATTACHMENTS));
            add(new InitEngram(
                    HOLOSCOPE_ATTACHMENT,
                    "Holo-Scope Attachment",
                    "This advanced scope can tell friends from strangers. Attach this to a supporting weapon to gain more accurate aiming.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 40);
                        append(ResourceInitializer.CRYSTAL, 40);
                        append(ResourceInitializer.ELECTRONICS, 30);
                    }},
                    CategoryInitializer.WEAPONS.ATTACHMENTS));
            add(new InitEngram(
                    LASER_ATTACHMENT,
                    "Laser Attachment",
                    "This advanced aiming device places a red dot where the weapon is pointed. Attach this to a supporting weapon to add a laser sight.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 50);
                        append(ResourceInitializer.CRYSTAL, 60);
                        append(ResourceInitializer.ELECTRONICS, 40);
                    }},
                    CategoryInitializer.WEAPONS.ATTACHMENTS));
            add(new InitEngram(
                    SCOPE_ATTACHMENT,
                    "Scope Attachment",
                    "The carefully shaped crystal lenses in this scope grant the user a telescopic aim when firing. Attach this to a supporting weapon to gain more accurate aiming.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 40);
                        append(ResourceInitializer.CRYSTAL, 20);
                        append(ResourceInitializer.STONE, 5);
                    }},
                    CategoryInitializer.WEAPONS.ATTACHMENTS));
            add(new InitEngram(
                    SILENCER_ATTACHMENT,
                    "Silencer Attachment",
                    "The lubricated materials in this silencer slow the gases released from a gunshot, muffling the sounds. Attach this to a supporting weapon for reduced noise when firing.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 50);
                        append(ResourceInitializer.OIL, 5);
                        append(ResourceInitializer.CHITIN_OR_KERATIN, 20);
                        append(ResourceInitializer.HIDE, 10);
                    }},
                    CategoryInitializer.WEAPONS.ATTACHMENTS));

            // -- WEAPONS > EXPLOSIVES --
            add(new InitEngram(
                    C4_REMOTE_DETONATOR,
                    "C4 Remote Detonator",
                    "This device uses radio waves to detonate all primed C4 packages on the same frequency.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 20);
                        append(ResourceInitializer.ELECTRONICS, 50);
                        append(ResourceInitializer.CRYSTAL, 10);
                        append(ResourceInitializer.METAL_INGOT, 10);
                        append(ResourceInitializer.CEMENTING_PASTE, 15);
                    }},
                    CategoryInitializer.WEAPONS.EXPLOSIVES));
            add(new InitEngram(
                    GRENADE,
                    "Grenade",
                    "Pulling the pin starts a 5 second timer to an explosion. Make sure you've thrown it by then.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FIBER, 15);
                        append(ResourceInitializer.STONE, 20);
                        append(ResourceInitializer.GUNPOWDER, 30);
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.METAL_INGOT, 2);
                        append(ResourceInitializer.OIL, 4);
                    }},
                    CategoryInitializer.WEAPONS.EXPLOSIVES));
            add(new InitEngram(
                    IMPROVISED_EXPLOSIVE_DEVICE,
                    "Improvised Explosive Device",
                    "Place two of these near each other to create an explosive trap.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.GUNPOWDER, 50);
                        append(ResourceInitializer.CRYSTAL, 10);
                        append(ResourceInitializer.FIBER, 35);
                        append(ResourceInitializer.HIDE, 5);
                        append(ResourceInitializer.METAL_INGOT, 10);
                        append(ResourceInitializer.WOOD, 1);
                    }},
                    CategoryInitializer.WEAPONS.EXPLOSIVES));
            add(new InitEngram(
                    POISON_GRENADE,
                    "Poison Grenade",
                    "Releases narcotic smoke to knock out anything in the area - only affects humans. Pulling the pin starts a 2.5 second timer to the gas release.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.NARCOTIC, 10);
                        append(ResourceInitializer.CHARCOAL, 14);
                        append(ResourceInitializer.METAL_INGOT, 5);
                        append(ResourceInitializer.SPARKPOWDER, 18);
                        append(ResourceInitializer.GUNPOWDER, 12);
                        append(ResourceInitializer.CRYSTAL, 5);
                        append(ResourceInitializer.FIBER, 20);
                        append(ResourceInitializer.FLINT, 5);
                    }},
                    CategoryInitializer.WEAPONS.EXPLOSIVES));
            add(new InitEngram(
                    SMOKE_GRENADE,
                    "Smoke Grenade",
                    "Releases a lot of smoke to obscure your plans. Pulling the pin starts a 5 second timer to an explosion.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 1);
                        append(ResourceInitializer.CHARCOAL, 10);
                        append(ResourceInitializer.WOOD, 2);
                        append(ResourceInitializer.HIDE, 6);
                        append(ResourceInitializer.FLINT, 3);
                        append(ResourceInitializer.FIBER, 15);
                        append(ResourceInitializer.THATCH, 5);
                        append(ResourceInitializer.SPARKPOWDER, 5);
                    }},
                    CategoryInitializer.WEAPONS.EXPLOSIVES));

            // -- WEAPONS > FIREARMS --
            add(new InitEngram(
                    ASSAULT_RIFLE,
                    "Assault Rifle",
                    "The fastest way to fill a target with holes.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 60);
                        append(ResourceInitializer.METAL_INGOT, 35);
                        append(ResourceInitializer.CEMENTING_PASTE, 50);
                    }},
                    CategoryInitializer.WEAPONS.FIREARMS));
            add(new InitEngram(
                    FABRICATED_PISTOL,
                    "Fabricated Pistol",
                    "This advanced pistol gains a high rate of fire and a large magazine size by sacrificing stopping power.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 35);
                        append(ResourceInitializer.METAL_INGOT, 20);
                        append(ResourceInitializer.CEMENTING_PASTE, 30);
                    }},
                    CategoryInitializer.WEAPONS.FIREARMS));
            add(new InitEngram(
                    FABRICATED_SNIPER_RIFLE,
                    "Fabricated Sniper Rifle",
                    "This semi-automatic rifle has less punch than a Longneck Rifle, but can be fired much more rapidly.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 115);
                        append(ResourceInitializer.METAL_INGOT, 80);
                        append(ResourceInitializer.CEMENTING_PASTE, 110);
                    }},
                    CategoryInitializer.WEAPONS.FIREARMS));
            add(new InitEngram(
                    LONGNECK_RIFLE,
                    "Longneck Rifle",
                    "This simple single-shot rifle is highly accurate, but has a long reload time.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 95);
                        append(ResourceInitializer.WOOD, 20);
                        append(ResourceInitializer.HIDE, 25);
                    }},
                    CategoryInitializer.WEAPONS.FIREARMS));
            add(new InitEngram(
                    PUMP_ACTION_SHOTGUN,
                    "Pump-Action Shotgun",
                    "Powerful up close, but less reliable with range. Can fire six rounds before reloading.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 30);
                        append(ResourceInitializer.POLYMER, 55);
                        append(ResourceInitializer.CEMENTING_PASTE, 45);
                    }},
                    CategoryInitializer.WEAPONS.FIREARMS));
            add(new InitEngram(
                    ROCKET_LAUNCHER,
                    "Rocket Launcher",
                    "Mankind's ultimate portable killing device.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 80);
                        append(ResourceInitializer.METAL_INGOT, 50);
                        append(ResourceInitializer.CEMENTING_PASTE, 60);
                    }},
                    CategoryInitializer.WEAPONS.FIREARMS));
            add(new InitEngram(
                    SHOTGUN,
                    "Shotgun",
                    "Very powerful up close, but less reliable with range.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 80);
                        append(ResourceInitializer.WOOD, 20);
                        append(ResourceInitializer.HIDE, 25);
                    }},
                    CategoryInitializer.WEAPONS.FIREARMS));
            add(new InitEngram(
                    SIMPLE_PISTOL,
                    "Simple Pistol",
                    "This simple revolver trades accuracy for flexibility.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 60);
                        append(ResourceInitializer.WOOD, 5);
                        append(ResourceInitializer.HIDE, 15);
                    }},
                    CategoryInitializer.WEAPONS.FIREARMS));

            // -- WEAPONS > MELEE --
            add(new InitEngram(
                    ELECTRIC_PROD,
                    "Electric Prod",
                    "Powerful stunning weapon, but can only be used for a single strike before recharge is needed.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 120);
                        append(ResourceInitializer.METAL_INGOT, 130);
                        append(ResourceInitializer.ELECTRONICS, 120);
                        append(ResourceInitializer.CEMENTING_PASTE, 70);
                        append(ResourceInitializer.CRYSTAL, 120);
                        append(ResourceInitializer.ANGLER_GEL, 15);
                    }},
                    CategoryInitializer.WEAPONS.MELEE));
            add(new InitEngram(
                    METAL_HATCHET,
                    "Metal Hatchet",
                    "A sharp metal hatchet for harvesting wood from trees, stone from rocks, and skin from bodies.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 8);
                        append(ResourceInitializer.WOOD, 1);
                        append(ResourceInitializer.HIDE, 10);
                    }},
                    CategoryInitializer.WEAPONS.MELEE));
            add(new InitEngram(
                    METAL_PICK,
                    "Metal Pick",
                    "A sharp metal pick for harvesting metal from mountain rocks, flint from rocks, and meat from bodies.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 1);
                        append(ResourceInitializer.WOOD, 1);
                        append(ResourceInitializer.HIDE, 10);
                    }},
                    CategoryInitializer.WEAPONS.MELEE));
            add(new InitEngram(
                    METAL_SICKLE,
                    "Metal Sickle",
                    "A curved tool ideal for harvesting fiber from seed plants. Also useful for delicately shearing off slices of Prime Fish!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 18);
                        append(ResourceInitializer.WOOD, 4);
                        append(ResourceInitializer.HIDE, 16);
                    }},
                    CategoryInitializer.WEAPONS.MELEE));
            add(new InitEngram(
                    PIKE,
                    "Pike",
                    "A powerful weapon tipped with metal. Unlike the spear, it cannot be thrown.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 10);
                        append(ResourceInitializer.WOOD, 10);
                        append(ResourceInitializer.HIDE, 20);
                    }},
                    CategoryInitializer.WEAPONS.MELEE));

            // -- WEAPONS > PRIMITIVE --
            // -- WEAPONS > PRIMITIVE > MELEE --
            add(new InitEngram(
                    METAL_SWORD,
                    "Metal Sword",
                    "The undisputed ruler of short-range combat. Needs a hotter flame to be forged.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 50);
                        append(ResourceInitializer.WOOD, 1);
                        append(ResourceInitializer.HIDE, 15);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.MELEE));
            add(new InitEngram(
                    SPEAR,
                    "Spear",
                    "An easily made melee weapon that can also be thrown. Has a chance to break when used.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FLINT, 2);
                        append(ResourceInitializer.WOOD, 8);
                        append(ResourceInitializer.FIBER, 12);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.MELEE));
            add(new InitEngram(
                    STONE_HATCHET,
                    "Stone Hatchet",
                    "A sharp flint hatchet for harvesting wood from trees, stone from rocks, and skin from bodies.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 10);
                        append(ResourceInitializer.FLINT, 1);
                        append(ResourceInitializer.WOOD, 1);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.MELEE));
            add(new InitEngram(
                    STONE_PICK,
                    "Stone Pick",
                    "A large stone pick for harvesting primarily flint from rocks, thatch from trees, and raw meat from bodies.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 1);
                        append(ResourceInitializer.WOOD, 1);
                        append(ResourceInitializer.THATCH, 10);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.MELEE));
            add(new InitEngram(
                    TORCH,
                    "Torch",
                    "Provides light, and some warmth. Doubles as a melee weapon in a pinch.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FLINT, 1);
                        append(ResourceInitializer.WOOD, 1);
                        append(ResourceInitializer.STONE, 1);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.MELEE));
            add(new InitEngram(
                    WOODEN_CLUB,
                    "Wooden Club",
                    "A easily made melee weapon that is excellent for knocking out targets. Only effective at short range.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 4);
                        append(ResourceInitializer.FIBER, 15);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.MELEE));

            // -- WEAPONS > PRIMITIVE > RANGED --
            add(new InitEngram(
                    BOLA,
                    "Bola",
                    "Wind it up and throw!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 10);
                        append(ResourceInitializer.STONE, 3);
                        append(ResourceInitializer.FIBER, 15);
                        append(ResourceInitializer.HIDE, 3);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.RANGED));
            add(new InitEngram(
                    BOW,
                    "Bow",
                    "Masters of the bow often became great conquerors. Requires arrows to fire.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 15);
                        append(ResourceInitializer.FIBER, 50);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.RANGED));
            add(new InitEngram(
                    COMPOUND_BOW,
                    "Compound Bow",
                    "A high-tech bow made of durable alloy, can launch arrows at high velocity. Requires arrows to fire.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 95);
                        append(ResourceInitializer.METAL_INGOT, 85);
                        append(ResourceInitializer.CEMENTING_PASTE, 75);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.RANGED));
            add(new InitEngram(
                    SLINGSHOT,
                    "Slingshot",
                    "A simple ranged weapon that deals damage from afar. Better for knocking out a target than killing it outright. Requires stone to fire.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 1);
                        append(ResourceInitializer.WOOD, 5);
                        append(ResourceInitializer.FIBER, 20);
                    }},
                    CategoryInitializer.WEAPONS.PRIMITIVE.RANGED));

            // -- WEAPONS > RANGED --
            add(new InitEngram(
                    CROSSBOW,
                    "Crossbow",
                    "Has significantly more power than the Bow, but cannot fire rapidly. Can fire underwater.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 7);
                        append(ResourceInitializer.WOOD, 10);
                        append(ResourceInitializer.FIBER, 35);
                    }},
                    CategoryInitializer.WEAPONS.RANGED));

            // -- WEAPONS > TRIPWIRES --
            add(new InitEngram(
                    TRIPWIRE_ALARM_TRAP,
                    "Tripwire Alarm Trap",
                    "Place two of these near each other to create a trap.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_ORE, 3);
                        append(ResourceInitializer.WOOD, 5);
                        append(ResourceInitializer.FIBER, 30);
                        append(ResourceInitializer.HIDE, 6);
                        append(ResourceInitializer.OIL, 2);
                    }},
                    CategoryInitializer.WEAPONS.TRIPWIRES));
            add(new InitEngram(
                    TRIPWIRE_NARCOTIC_TRAP,
                    "Tripwire Narcotic Trap",
                    "Place two of these near each other to create a poisonous trap.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.NARCOTIC, 15);
                        append(ResourceInitializer.CEMENTING_PASTE, 3);
                        append(ResourceInitializer.WOOD, 4);
                        append(ResourceInitializer.FIBER, 35);
                        append(ResourceInitializer.HIDE, 6);
                        append(ResourceInitializer.CRYSTAL, 1);
                    }},
                    CategoryInitializer.WEAPONS.TRIPWIRES));
        }
    };

    public static List<InitEngram> getEngrams() {
        engrams.addAll(StructureInitializer.getEngrams());
        engrams.addAll(KibbleInitializer.getEngrams());

        return engrams;
    }

    public static int getCount() {
        return engrams.size();
    }
}