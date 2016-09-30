package arc.resource.calculator.data.initializers;

import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.model.engram.InitEngram;

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
public class StructureInitializer {

    // -- STRUCTURES > BEDS --
    public static final int BUNK_BED = R.drawable.structures_beds_bunk_bed;
    public static final int HIDE_SLEEPING_BAG = R.drawable.structures_beds_hide_sleeping_bag;
    public static final int SIMPLE_BED = R.drawable.structures_beds_simple_bed;

    // -- STRUCTURES > COOKING --
    public static final int BEER_BARREL = R.drawable.structures_cooking_beer_barrel;
    public static final int CAMPFIRE = R.drawable.structures_cooking_campfire;
    public static final int COOKING_POT = R.drawable.structures_cooking_cooking_pot;
    public static final int INDUSTRIAL_COOKER = R.drawable.structures_cooking_industrial_cooker;
    public static final int INDUSTRIAL_GRILL = R.drawable.structures_cooking_industrial_grill;

    // -- STRUCTURES > CRAFTING --
    public static final int CHEMISTRY_BENCH = R.drawable.structures_crafting_chemistry_bench;
    public static final int FABRICATOR = R.drawable.structures_crafting_fabricator;
    public static final int INDUSTRIAL_FORGE = R.drawable.structures_crafting_industrial_forge;
    public static final int MORTAR_AND_PESTLE = R.drawable.structures_crafting_mortar_and_pestle;
    public static final int REFINING_FORGE = R.drawable.structures_crafting_refining_forge;
    public static final int SMITHY = R.drawable.structures_crafting_smithy;
    public static final int INDUSTRIAL_GRINDER = R.drawable.structures_crafting_industrial_grinder;

    // -- STRUCTURES > DEFENSES --
    public static final int BEAR_TRAP = R.drawable.structures_defenses_bear_trap;
    public static final int HOMING_UNDERWATER_MINE = R.drawable.structures_defenses_homing_underwater_mine;
    public static final int LARGE_BEAR_TRAP = R.drawable.structures_defenses_large_bear_trap;

    // -- STRUCTURES > ELECTRIC --
    public static final int AIR_CONDITIONER = R.drawable.structures_electric_air_conditioner;
    public static final int LAMPPOST = R.drawable.structures_electric_lamppost;
    public static final int OMNIDIRECTIONAL_LAMPPOST = R.drawable.structures_electric_omnidirectional_lamppost;
    public static final int REFRIGERATOR = R.drawable.structures_electric_refrigerator;
    public static final int REMOTE_KEYPAD = R.drawable.structures_electric_remote_keypad;

    // -- STRUCTURES > ELECTRICAL --
    public static final int ELECTRICAL_CABLE_INTERSECTION = R.drawable.structures_electrical_electrical_cable_intersection;
    public static final int ELECTRICAL_GENERATOR = R.drawable.structures_electrical_electrical_generator;
    public static final int ELECTRICAL_OUTLET = R.drawable.structures_electrical_electrical_outlet;
    public static final int INCLINED_ELECTRICAL_CABLE = R.drawable.structures_electrical_inclined_electrical_cable;
    public static final int STRAIGHT_ELECTRICAL_CABLE = R.drawable.structures_electrical_straight_electrical_cable;
    public static final int VERTICAL_ELECTRICAL_CABLE = R.drawable.structures_electrical_vertical_electrical_cable;

    // -- STRUCTURES > ELECTRICAL > DEFENSES --
    public static final int AUTO_TURRET = R.drawable.electrical_defenses_auto_turret;

    //-- STRUCTURES > ELECTRICAL > ELEVATORS --
    public static final int ELEVATOR_TRACK = R.drawable.electrical_elevators_elevator_track;
    public static final int LARGE_ELEVATOR_PLATFORM = R.drawable.electrical_elevators_large_elevator_platform;
    public static final int MEDIUM_ELEVATOR_PLATFORM = R.drawable.electrical_elevators_medium_elevator_platform;
    public static final int SMALL_ELEVATOR_PLATFORM = R.drawable.electrical_elevators_small_elevator_platform;

    // -- STRUCTURES > FARMING --
    public static final int COMPOST_BIN = R.drawable.structures_farming_compost_bin;
    public static final int LARGE_CROP_PLOT = R.drawable.structures_farming_large_crop_plot;
    public static final int MEDIUM_CROP_PLOT = R.drawable.structures_farming_medium_crop_plot;
    public static final int SMALL_CROP_PLOT = R.drawable.structures_farming_small_crop_plot;

    // -- STRUCTURES > FRIDGES --
    public static final int PRESERVING_BIN = R.drawable.structures_fridges_preserving_bin;

    // -- STRUCTURES > FURNITURE --
    // -- STRUCTURES > FURNITURE > STONE --
    public static final int GRAVESTONE = R.drawable.furniture_stone_gravestone;

    // -- STRUCTURES > FURNITURE > WOOD --
    public static final int WARDRUMS = R.drawable.furniture_wood_wardrums;
    public static final int WOODEN_BENCH = R.drawable.furniture_wood_wooden_bench;
    public static final int WOODEN_CHAIR = R.drawable.furniture_wood_wooden_chair;
    public static final int WOODEN_TABLE = R.drawable.furniture_wood_wooden_table;

    // -- STRUCTURES > GREENHOUSE --
    public static final int GREENHOUSE_CEILING = R.drawable.structures_greenhouse_greenhouse_ceiling;
    public static final int GREENHOUSE_DOOR = R.drawable.structures_greenhouse_greenhouse_door;
    public static final int GREENHOUSE_DOORFRAME = R.drawable.structures_greenhouse_greenhouse_doorframe;
    public static final int SLOPED_GREENHOUSE_ROOF = R.drawable.structures_greenhouse_sloped_greenhouse_roof;
    public static final int GREENHOUSE_WALL = R.drawable.structures_greenhouse_greenhouse_wall;
    public static final int GREENHOUSE_WINDOW = R.drawable.structures_greenhouse_greenhouse_window;
    public static final int SLOPED_GREENHOUSE_WALL_LEFT = R.drawable.structures_greenhouse_sloped_greenhouse_wall_left;
    public static final int SLOPED_GREENHOUSE_WALL_RIGHT = R.drawable.structures_greenhouse_sloped_greenhouse_wall_right;

    // -- STRUCTURES > METAL --
    public static final int BALLISTA_TURRET = R.drawable.structures_metal_ballista_turret;
    public static final int CATAPULT_TURRET = R.drawable.structures_metal_catapult_turret;
    public static final int MINIGUN_TURRET = R.drawable.structures_metal_minigun_turret;
    public static final int ROCKET_TURRET = R.drawable.structures_metal_rocket_turret;
    public static final int PRIMITIVE_CANNON = R.drawable.structures_metal_primitive_cannon;
    public static final int BEHEMOTH_GATE = R.drawable.structures_metal_behemoth_gate;
    public static final int BEHEMOTH_GATEWAY = R.drawable.structures_metal_behemoth_gateway;
    public static final int METAL_CATWALK = R.drawable.structures_metal_metal_catwalk;
    public static final int METAL_CEILING = R.drawable.structures_metal_metal_ceiling;
    public static final int METAL_DINOSAUR_GATE = R.drawable.structures_metal_metal_dinosaur_gate;
    public static final int METAL_DINOSAUR_GATEWAY = R.drawable.structures_metal_metal_dinosaur_gateway;
    public static final int METAL_DOOR = R.drawable.structures_metal_metal_door;
    public static final int METAL_DOORFRAME = R.drawable.structures_metal_metal_doorframe;
    public static final int METAL_FENCE_FOUNDATION = R.drawable.structures_metal_metal_fence_foundation;
    public static final int METAL_FOUNDATION = R.drawable.structures_metal_metal_foundation;
    public static final int METAL_HATCHFRAME = R.drawable.structures_metal_metal_hatchframe;
    public static final int METAL_LADDER = R.drawable.structures_metal_metal_ladder;
    public static final int METAL_PILLAR = R.drawable.structures_metal_metal_pillar;
    public static final int METAL_RAILING = R.drawable.structures_metal_metal_railing;
    public static final int METAL_RAMP = R.drawable.structures_metal_metal_ramp;
    public static final int METAL_SPIKE_WALL = R.drawable.structures_metal_metal_spike_wall;
    public static final int METAL_TRAPDOOR = R.drawable.structures_metal_metal_trapdoor;
    public static final int METAL_TREE_PLATFORM = R.drawable.structures_metal_metal_tree_platform;
    public static final int METAL_WALL = R.drawable.structures_metal_metal_wall;
    public static final int METAL_WINDOW = R.drawable.structures_metal_metal_window;
    public static final int METAL_WINDOWFRAME = R.drawable.structures_metal_metal_windowframe;
    public static final int SLOPED_METAL_ROOF = R.drawable.structures_metal_sloped_metal_roof;
    public static final int SLOPED_METAL_WALL_LEFT = R.drawable.structures_metal_sloped_metal_wall_left;
    public static final int SLOPED_METAL_WALL_RIGHT = R.drawable.structures_metal_sloped_metal_wall_right;
    public static final int WALL_TORCH = R.drawable.structures_metal_wall_torch;
    public static final int METAL_SPIRAL_STAIRCASE = R.drawable.blank;

    // -- STRUCTURES > METAL > SIGNS --
    public static final int METAL_BILLBOARD = R.drawable.metal_signs_metal_billboard;
    public static final int METAL_SIGN = R.drawable.metal_signs_metal_sign;
    public static final int METAL_WALL_SIGN = R.drawable.metal_signs_metal_wall_sign;

    // -- STRUCTURES > METAL > STORAGE --
    public static final int VAULT = R.drawable.metal_storage_vault;

    // -- STRUCTURES > MISC --
    public static final int PAINTING_CANVAS = R.drawable.structures_misc_painting_canvas;
    public static final int SINGLE_PANEL_FLAG = R.drawable.structures_misc_single_panel_flag;
    public static final int MULTI_PANEL_FLAG = R.drawable.structures_misc_multi_panel_flag;
    public static final int TRAINING_DUMMY = R.drawable.structures_misc_training_dummy;
    public static final int TREE_SAP_TAP = R.drawable.structures_misc_tree_sap_tap;
    public static final int TROPHY_BASE = R.drawable.structures_misc_trophy_base;
    public static final int TROPHY_WALL_MOUNT = R.drawable.structures_misc_trophy_wall_mount;
    public static final int WAR_MAP = R.drawable.structures_misc_war_map;

    // -- STRUCTURES > PIPES --
    // -- STRUCTURES > PIPES > METAL --
    public static final int METAL_IRRIGATION_PIPE_INCLINED = R.drawable.pipes_metal_metal_irrigation_pipe_inclined;
    public static final int METAL_IRRIGATION_PIPE_INTERSECTION = R.drawable.pipes_metal_metal_irrigation_pipe_intersection;
    public static final int METAL_IRRIGATION_PIPE_STRAIGHT = R.drawable.pipes_metal_metal_irrigation_pipe_straight;
    public static final int METAL_IRRIGATION_PIPE_INTAKE = R.drawable.pipes_metal_metal_irrigation_pipe_intake;
    public static final int METAL_IRRIGATION_PIPE_TAP = R.drawable.pipes_metal_metal_irrigation_pipe_tap;
    public static final int METAL_IRRIGATION_PIPE_VERTICAL = R.drawable.pipes_metal_metal_irrigation_pipe_vertical;
    public static final int METAL_WATER_RESERVOIR = R.drawable.pipes_metal_metal_water_reservoir;

    // -- STRUCTURES > PIPES > STONE --
    public static final int STONE_IRRIGATION_PIPE_INCLINED = R.drawable.pipes_stone_stone_irrigation_pipe_inclined;
    public static final int STONE_IRRIGATION_PIPE_INTAKE = R.drawable.pipes_stone_stone_irrigation_pipe_intake;
    public static final int STONE_IRRIGATION_PIPE_INTERSECTION = R.drawable.pipes_stone_stone_irrigation_pipe_intersection;
    public static final int STONE_IRRIGATION_PIPE_STRAIGHT = R.drawable.pipes_stone_stone_irrigation_pipe_straight;
    public static final int STONE_IRRIGATION_PIPE_TAP = R.drawable.pipes_stone_stone_irrigation_pipe_tap;
    public static final int STONE_IRRIGATION_PIPE_VERTICAL = R.drawable.pipes_stone_stone_irrigation_pipe_vertical;
    public static final int WATER_RESERVOIR = R.drawable.pipes_stone_water_reservoir;

    // -- STRUCTURES > STONE --
    public static final int BEHEMOTH_REINFORCED_DINOSAUR_GATE = R.drawable.structures_stone_behemoth_reinforced_dinosaur_gate;
    public static final int BEHEMOTH_REINFORCED_DINOSAUR_GATEWAY = R.drawable.structures_stone_behemoth_stone_dinosaur_gateway;
    public static final int REINFORCED_DINOSAUR_GATE = R.drawable.structures_stone_reinforced_dinosaur_gate;
    public static final int REINFORCED_TRAPDOOR = R.drawable.structures_stone_reinforced_trapdoor;
    public static final int REINFORCED_WINDOW = R.drawable.structures_stone_reinforced_window;
    public static final int REINFORCED_WOODEN_DOOR = R.drawable.structures_stone_reinforced_wooden_door;
    public static final int SLOPED_STONE_ROOF = R.drawable.structures_stone_sloped_stone_roof;
    public static final int SLOPED_STONE_WALL_LEFT = R.drawable.structures_stone_sloped_stone_wall_left;
    public static final int SLOPED_STONE_WALL_RIGHT = R.drawable.structures_stone_sloped_stone_wall_right;
    public static final int STONE_CEILING = R.drawable.structures_stone_stone_ceiling;
    public static final int STONE_DINOSAUR_GATEWAY = R.drawable.structures_stone_stone_dinosaur_gateway;
    public static final int STONE_DOORFRAME = R.drawable.structures_stone_stone_doorframe;
    public static final int STONE_FENCE_FOUNDATION = R.drawable.structures_stone_stone_fence_foundation;
    public static final int STONE_FOUNDATION = R.drawable.structures_stone_stone_foundation;
    public static final int STONE_HATCHFRAME = R.drawable.structures_stone_stone_hatchframe;
    public static final int STONE_PILLAR = R.drawable.structures_stone_stone_pillar;
    public static final int STONE_RAILING = R.drawable.structures_stone_stone_railing;
    public static final int STONE_WALL = R.drawable.structures_stone_stone_wall;
    public static final int STONE_WINDOWFRAME = R.drawable.structures_stone_stone_windowframe;
    public static final int STONE_SPIRAL_STAIRCASE = R.drawable.blank;

    // -- STRUCTURES > THATCH --
    public static final int SLOPED_THATCH_ROOF = R.drawable.structures_thatch_sloped_thatch_roof;
    public static final int SLOPED_THATCH_WALL_LEFT = R.drawable.structures_thatch_sloped_thatch_wall_left;
    public static final int SLOPED_THATCH_WALL_RIGHT = R.drawable.structures_thatch_sloped_thatch_wall_right;
    public static final int THATCH_DOOR = R.drawable.structures_thatch_thatch_door;
    public static final int THATCH_DOORFRAME = R.drawable.structures_thatch_thatch_doorframe;
    public static final int THATCH_FOUNDATION = R.drawable.structures_thatch_thatch_foundation;
    public static final int THATCH_ROOF = R.drawable.structures_thatch_thatch_roof;
    public static final int THATCH_WALL = R.drawable.structures_thatch_thatch_wall;

    // -- STRUCTURES > WOOD --
    public static final int DINOSAUR_GATE = R.drawable.structures_wood_dinosaur_gate;
    public static final int DINOSAUR_GATEWAY = R.drawable.structures_wood_dinosaur_gateway;
    public static final int ROPE_LADDER = R.drawable.structures_wood_rope_ladder;
    public static final int SLOPED_WOOD_WALL_LEFT = R.drawable.structures_wood_sloped_wood_wall_left;
    public static final int SLOPED_WOOD_WALL_RIGHT = R.drawable.structures_wood_sloped_wood_wall_right;
    public static final int SLOPED_WOODEN_ROOF = R.drawable.structures_wood_sloped_wooden_roof;
    public static final int STANDING_TORCH = R.drawable.structures_wood_standing_torch;
    public static final int WOODEN_CAGE = R.drawable.structures_wood_wooden_cage;
    public static final int WOODEN_CATWALK = R.drawable.structures_wood_wooden_catwalk;
    public static final int WOODEN_CEILING = R.drawable.structures_wood_wooden_ceiling;
    public static final int WOODEN_DOOR = R.drawable.structures_wood_wooden_door;
    public static final int WOODEN_DOORFRAME = R.drawable.structures_wood_wooden_doorframe;
    public static final int WOODEN_FENCE_FOUNDATION = R.drawable.structures_wood_wooden_fence_foundation;
    public static final int WOODEN_FOUNDATION = R.drawable.structures_wood_wooden_foundation;
    public static final int WOODEN_HATCHFRAME = R.drawable.structures_wood_wooden_hatchframe;
    public static final int WOODEN_LADDER = R.drawable.structures_wood_wooden_ladder;
    public static final int WOODEN_PILLAR = R.drawable.structures_wood_wooden_pillar;
    public static final int WOODEN_RAILING = R.drawable.structures_wood_wooden_railing;
    public static final int WOODEN_RAMP = R.drawable.structures_wood_wooden_ramp;
    public static final int WOODEN_SPIKE_WALL = R.drawable.structures_wood_wooden_spike_wall;
    public static final int WOODEN_TRAPDOOR = R.drawable.structures_wood_wooden_trapdoor;
    public static final int WOODEN_TREE_PLATFORM = R.drawable.structures_wood_wooden_tree_platform;
    public static final int WOODEN_WALL = R.drawable.structures_wood_wooden_wall;
    public static final int WOODEN_WINDOW = R.drawable.structures_wood_wooden_window;
    public static final int WOODEN_WINDOWFRAME = R.drawable.structures_wood_wooden_windowframe;
    public static final int WOODEN_SPIRAL_STAIRCASE = R.drawable.blank;

    // -- STRUCTURES > WOOD > SIGNS --
    public static final int WOODEN_BILLBOARD = R.drawable.wood_signs_wooden_billboard;
    public static final int WOODEN_SIGN = R.drawable.wood_signs_wooden_sign;
    public static final int WOODEN_WALL_SIGN = R.drawable.wood_signs_wooden_wall_sign;

    // -- STRUCTURES > WOOD > STORAGE --
    public static final int BOOKSHELF = R.drawable.wood_storage_bookshelf;
    public static final int FEEDING_TROUGH = R.drawable.wood_storage_feeding_trough;
    public static final int LARGE_STORAGE_BOX = R.drawable.wood_storage_large_storage_box;
    public static final int STORAGE_BOX = R.drawable.wood_storage_storage_box;

    private static List<InitEngram> engrams = new ArrayList<InitEngram>() {
        {

            // -- STRUCTURES > BEDS --
            add(new InitEngram(
                    BUNK_BED,
                    "Bunk Bed",
                    "This modern bunk-style bed has two mattresses, and a high thread count. Acts as a respawn point for you and your tribe with half cooldown time.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.POLYMER, 80 );
                        append( ResourceInitializer.PELT, 200 );
                        append( ResourceInitializer.METAL_INGOT, 320 );
                        append( ResourceInitializer.FIBER, 280 );
                        append( ResourceInitializer.HIDE, 120 );
                    }},
                    CategoryInitializer.STRUCTURES.BEDS));
            add(new InitEngram(
                    HIDE_SLEEPING_BAG,
                    "Hide Sleeping Bag",
                    "This hide sleeping bag acts as a single-use respawn point, only usable by you.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.HIDE, 25 );
                        append( ResourceInitializer.FIBER, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.BEDS));
            add(new InitEngram(
                    SIMPLE_BED,
                    "Simple Bed",
                    "Thatch may not make the most comfortable mattress, but this bed acts as a respawn point for you and your Tribe.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 15 );
                        append( ResourceInitializer.THATCH, 80 );
                        append( ResourceInitializer.FIBER, 30 );
                        append( ResourceInitializer.HIDE, 40 );
                    }},
                    CategoryInitializer.STRUCTURES.BEDS));

            // -- STRUCTURES > COOKING --
            add(new InitEngram(
                    BEER_BARREL,
                    "Beer Barrel",
                    "Ferments tasty brew from Thatch, Water, and Berries",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 500 );
                        append( ResourceInitializer.METAL_INGOT, 80 );
                        append( ResourceInitializer.CEMENTING_PASTE, 100 );
                    }},
                    CategoryInitializer.STRUCTURES.COOKING));
            add(new InitEngram(
                    CAMPFIRE,
                    "Campfire",
                    "Perfect for cooking meat, staying warm, and providing light.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 12 );
                        append( ResourceInitializer.FLINT, 1 );
                        append( ResourceInitializer.STONE, 16 );
                        append( ResourceInitializer.WOOD, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.COOKING));
            add(new InitEngram(
                    COOKING_POT,
                    "Cooking Pot",
                    "Must contain basic fuel to light the fire. Put various ingredients with water in this to make soups, stews, and dyes.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 75 );
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.WOOD, 10 );
                        append( ResourceInitializer.FLINT, 5 );
                    }},
                    CategoryInitializer.STRUCTURES.COOKING));
            add(new InitEngram(
                    INDUSTRIAL_COOKER,
                    "Industrial Cooker",
                    "Burns Gasoline to cook large quantities of food quickly. Put various ingredients in this to make soups, stews, and dyes.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.POLYMER, 300 );
                        append( ResourceInitializer.METAL_INGOT, 1800 );
                        append( ResourceInitializer.CEMENTING_PASTE, 450 );
                        append( ResourceInitializer.OIL, 300 );
                    }},
                    CategoryInitializer.STRUCTURES.COOKING));
            add(new InitEngram(
                    INDUSTRIAL_GRILL,
                    "Industrial Grill",
                    "Perfect for cooking meat in bulk, staying warm, and providing light. Powered by Gasoline.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 200 );
                        append( ResourceInitializer.CEMENTING_PASTE, 40 );
                        append( ResourceInitializer.OIL, 40 );
                        append( ResourceInitializer.CRYSTAL, 30 );
                    }},
                    CategoryInitializer.STRUCTURES.COOKING));

            // -- STRUCTURES > CRAFTING --
            add(new InitEngram(
                    CHEMISTRY_BENCH,
                    "Chemistry Bench",
                    "Place materials in this to transmute chemical substances with extreme efficiency!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 250 );
                        append( ResourceInitializer.CEMENTING_PASTE, 250 );
                        append( ResourceInitializer.SPARKPOWDER, 100 );
                        append( ResourceInitializer.CRYSTAL, 250 );
                        append( ResourceInitializer.POLYMER, 250 );
                        append( ResourceInitializer.ELECTRONICS, 250 );
                    }},
                    CategoryInitializer.STRUCTURES.CRAFTING));
            add(new InitEngram(
                    FABRICATOR,
                    "Fabricator",
                    "Place materials along with blueprints in this to create certain high-end machined items.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 20 );
                        append( ResourceInitializer.SPARKPOWDER, 50 );
                        append( ResourceInitializer.CRYSTAL, 15 );
                        append( ResourceInitializer.OIL, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.CRAFTING));
            add(new InitEngram(
                    INDUSTRIAL_FORGE,
                    "Industrial Forge",
                    "Refines resources in bulk. Powered by gasoline.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 2500 );
                        append( ResourceInitializer.CRYSTAL, 250 );
                        append( ResourceInitializer.CEMENTING_PASTE, 600 );
                        append( ResourceInitializer.OIL, 400 );
                        append( ResourceInitializer.POLYMER, 400 );
                    }},
                    CategoryInitializer.STRUCTURES.CRAFTING));
            add(new InitEngram(
                    MORTAR_AND_PESTLE,
                    "Mortar and Pestle",
                    "This simple stone contraption can be used to grind resources into new substances.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 65 );
                        append( ResourceInitializer.HIDE, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.CRAFTING));
            add(new InitEngram(
                    REFINING_FORGE,
                    "Refining Forge",
                    "Requires wood, thatch, or sparkpowder to activate. Put unrefined resources in this to refine them.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 125 );
                        append( ResourceInitializer.FLINT, 5 );
                        append( ResourceInitializer.HIDE, 65 );
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.FIBER, 40 );
                    }},
                    CategoryInitializer.STRUCTURES.CRAFTING));
            add(new InitEngram(
                    SMITHY,
                    "Smithy",
                    "Place materials along with blueprints in this to create certain advanced forged items.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.STONE, 50 );
                        append( ResourceInitializer.WOOD, 30 );
                        append( ResourceInitializer.HIDE, 20 );
                    }},
                    CategoryInitializer.STRUCTURES.CRAFTING));
            add(new InitEngram(
                    INDUSTRIAL_GRINDER,
                    "Industrial Grinder",
                    "Grind up crafted items and certain resources!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 3200 );
                        append( ResourceInitializer.CRYSTAL, 2000 );
                        append( ResourceInitializer.CEMENTING_PASTE, 800 );
                        append( ResourceInitializer.OIL, 600 );
                        append( ResourceInitializer.POLYMER, 600 );
                    }},
                    CategoryInitializer.STRUCTURES.CRAFTING));

            // -- STRUCTURES > DEFENSES --
            add(new InitEngram(
                    BEAR_TRAP,
                    "Bear Trap",
                    "Immobilizes humans and small creatures.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.FIBER, 5 );
                        append( ResourceInitializer.METAL_INGOT, 3 );
                        append( ResourceInitializer.HIDE, 6 );
                    }},
                    CategoryInitializer.STRUCTURES.DEFENSES));
            add(new InitEngram(
                    HOMING_UNDERWATER_MINE,
                    "Homing Underwater Mine",
                    "Place this underwater to create an explosive trap that floats, homes, explodes when touched.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.POLYMER, 45 );
                        append( ResourceInitializer.METAL_INGOT, 30 );
                        append( ResourceInitializer.CEMENTING_PASTE, 20 );
                        append( ResourceInitializer.OIL, 15 );
                        append( ResourceInitializer.GUNPOWDER, 100 );
                        append( ResourceInitializer.CRYSTAL, 30 );
                        append( ResourceInitializer.ELECTRONICS, 150 );
                    }},
                    CategoryInitializer.STRUCTURES.DEFENSES));
            add(new InitEngram(
                    LARGE_BEAR_TRAP,
                    "Large Bear Trap",
                    "Immobilizes large creatures only.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.FIBER, 10 );
                        append( ResourceInitializer.METAL_INGOT, 6 );
                        append( ResourceInitializer.HIDE, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.DEFENSES));

            // -- STRUCTURES > ELECTRIC --
            add(new InitEngram(
                    AIR_CONDITIONER,
                    "Air Conditioner",
                    "Requires electricity to run. Provides insulation from both the heat and the cold to an area.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 80 );
                        append( ResourceInitializer.ELECTRONICS, 15 );
                        append( ResourceInitializer.POLYMER, 5 );
                        append( ResourceInitializer.CRYSTAL, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRIC));
            add(new InitEngram(
                    LAMPPOST,
                    "Lamppost",
                    "Requires electricity to activate. Lights an area without adding much heat.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.CRYSTAL, 10 );
                        append( ResourceInitializer.ELECTRONICS, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRIC));
            add(new InitEngram(
                    OMNIDIRECTIONAL_LAMPPOST,
                    "Omnidirectional Lamppost",
                    "Requires electricity to activate. Lights an area without adding much heat.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.CRYSTAL, 10 );
                        append( ResourceInitializer.ELECTRONICS, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRIC));
            add(new InitEngram(
                    REFRIGERATOR,
                    "Refrigerator",
                    "Requires electricity to run. Keeps perishables from spoiling for a long time.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 120 );
                        append( ResourceInitializer.POLYMER, 15 );
                        append( ResourceInitializer.CRYSTAL, 25 );
                        append( ResourceInitializer.ELECTRONICS, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRIC));
            add(new InitEngram(
                    REMOTE_KEYPAD,
                    "Remote Keypad",
                    "Allows remote access to multiple doors and/or lights.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 2 );
                        append( ResourceInitializer.CEMENTING_PASTE, 1 );
                        append( ResourceInitializer.ELECTRONICS, 25 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRIC));

            // -- STRUCTURES > ELECTRICAL --
            add(new InitEngram(
                    ELECTRICAL_CABLE_INTERSECTION,
                    "Electrical Cable - Intersection",
                    "A plus-shaped intersection for a power grid, used for splitting one power cable into three.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ID));
            add(new InitEngram(
                    ELECTRICAL_GENERATOR,
                    "Electrical Generator",
                    "A large machine that converts gasoline into electricity.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 25 );
                        append( ResourceInitializer.ELECTRONICS, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ID));
            add(new InitEngram(
                    ELECTRICAL_OUTLET,
                    "Electrical Outlet",
                    "An outlet for an electrical grid. When connected to a generator via cables, this provides power to all nearby devices that use electricity.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.WOOD, 15 );
                        append( ResourceInitializer.ELECTRONICS, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ID));
            add(new InitEngram(
                    INCLINED_ELECTRICAL_CABLE,
                    "Electrical Cable - Inclined",
                    "An inclined cable for transmitting electricity up and down hills.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ID));
            add(new InitEngram(
                    STRAIGHT_ELECTRICAL_CABLE,
                    "Electrical Cable - Straight",
                    "A straight cable, used for transmitting electricity across land.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ID));
            add(new InitEngram(
                    VERTICAL_ELECTRICAL_CABLE,
                    "Electrical Cable - Vertical",
                    " vertical cable for transmitting electricity up and down cliffs.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ID));

            // -- STRUCTURES > ELECTRICAL > DEFENSES --
            add(new InitEngram(
                    AUTO_TURRET,
                    "Auto Turret",
                    "Requires electricity to run. Consumes bullets while firing. Can be configured to automatically attack hostiles within range.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.ELECTRONICS, 70 );
                        append( ResourceInitializer.METAL_INGOT, 140 );
                        append( ResourceInitializer.CEMENTING_PASTE, 50 );
                        append( ResourceInitializer.POLYMER, 20 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.DEFENSES));

            // -- STRUCTURES > ELECTRICAL > ELEVATORS --
            add(new InitEngram(
                    ELEVATOR_TRACK,
                    "Elevator Track",
                    "Attach an Elevator Platform to these to complete an elevator!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 140 );
                        append( ResourceInitializer.POLYMER, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 25 );
                        append( ResourceInitializer.ELECTRONICS, 20 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ELEVATORS));
            add(new InitEngram(
                    SMALL_ELEVATOR_PLATFORM,
                    "Small Elevator Platform",
                    "Attach to an Elevator Track to carry a small amount of weight.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 200 );
                        append( ResourceInitializer.POLYMER, 50 );
                        append( ResourceInitializer.CEMENTING_PASTE, 40 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ELEVATORS));
            add(new InitEngram(
                    MEDIUM_ELEVATOR_PLATFORM,
                    "Medium Elevator Platform",
                    "Attach to an Elevator Track to carry a medium amount of weight.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 400 );
                        append( ResourceInitializer.POLYMER, 100 );
                        append( ResourceInitializer.CEMENTING_PASTE, 80 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ELEVATORS));
            add(new InitEngram(
                    LARGE_ELEVATOR_PLATFORM,
                    "Large Elevator Platform",
                    "Attach to an Elevator Track to carry a large amount of weight.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 800 );
                        append( ResourceInitializer.POLYMER, 200 );
                        append( ResourceInitializer.CEMENTING_PASTE, 160 );
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ELEVATORS));

            // -- STRUCTURES > FARMING --
            add(new InitEngram(
                    COMPOST_BIN,
                    "Compost Bin",
                    "A large bin for converting thatch and dung into high-quality fertilizer.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 50 );
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.FIBER, 12 );
                    }},
                    CategoryInitializer.STRUCTURES.FARMING));
            add(new InitEngram(
                    SMALL_CROP_PLOT,
                    "Small Crop Plot",
                    "A small garden plot, with a fence to keep out vermin.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.THATCH, 10 );
                        append( ResourceInitializer.FIBER, 15 );
                        append( ResourceInitializer.STONE, 25 );
                    }},
                    CategoryInitializer.STRUCTURES.FARMING));
            add(new InitEngram(
                    MEDIUM_CROP_PLOT,
                    "Medium Crop Plot",
                    "A medium garden plot, with a fence to keep out vermin.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 40 );
                        append( ResourceInitializer.THATCH, 20 );
                        append( ResourceInitializer.FIBER, 30 );
                        append( ResourceInitializer.STONE, 50 );
                    }},
                    CategoryInitializer.STRUCTURES.FARMING));
            add(new InitEngram(
                    LARGE_CROP_PLOT,
                    "Large Crop Plot",
                    "A large garden plot, with a fence to keep out vermin.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 80 );
                        append( ResourceInitializer.THATCH, 40 );
                        append( ResourceInitializer.FIBER, 60 );
                        append( ResourceInitializer.STONE, 100 );
                    }},
                    CategoryInitializer.STRUCTURES.FARMING));

            // -- STRUCTURES > FURNITURE --
            // -- STRUCTURES > FURNITURE > STONE --
            add(new InitEngram(
                    GRAVESTONE,
                    "Gravestone",
                    "A simple unadorned stone headstone to mark a grave or commemorate a loved one.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 70 );
                        append( ResourceInitializer.CEMENTING_PASTE, 8 );
                    }},
                    CategoryInitializer.STRUCTURES.FURNITURE.STONE));

            // -- STRUCTURES > FURNITURE > WOOD --
            add(new InitEngram(
                    WARDRUMS,
                    "War Drums",
                    "A set of tribal wardrums, to let everyone around hear the power of your tribe.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 380 );
                        append( ResourceInitializer.HIDE, 220 );
                        append( ResourceInitializer.FIBER, 200 );
                        append( ResourceInitializer.THATCH, 200 );
                    }},
                    CategoryInitializer.STRUCTURES.FURNITURE.WOOD));
            add(new InitEngram(
                    WOODEN_BENCH,
                    "Wooden Bench",
                    "A simple wooden bench for solo sitting.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 12 );
                        append( ResourceInitializer.THATCH, 55 );
                        append( ResourceInitializer.FIBER, 45 );
                    }},
                    CategoryInitializer.STRUCTURES.FURNITURE.WOOD));
            add(new InitEngram(
                    WOODEN_CHAIR,
                    "Wooden Chair",
                    "A simple wooden chair for solo sitting.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 30 );
                        append( ResourceInitializer.THATCH, 18 );
                        append( ResourceInitializer.FIBER, 14 );
                    }},
                    CategoryInitializer.STRUCTURES.FURNITURE.WOOD));
            add(new InitEngram(
                    WOODEN_TABLE,
                    "Wooden Table",
                    "A simple wooden table with a variety of uses.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 100 );
                        append( ResourceInitializer.METAL_ORE, 12 );
                    }},
                    CategoryInitializer.STRUCTURES.FURNITURE.WOOD));

            // -- STRUCTURES > FRIDGES --
            add(new InitEngram(
                    PRESERVING_BIN,
                    "Preserving Bin",
                    "Burns simple fuel at low temperatures to dehydrate food and kill bacteria. Keeps perishables from spoiling for a small time.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 30 );
                        append( ResourceInitializer.WOOD, 30 );
                        append( ResourceInitializer.THATCH, 10 );
                        append( ResourceInitializer.FIBER, 20 );
                    }},
                    CategoryInitializer.STRUCTURES.FRIDGES));

            // -- STRUCTURES > GREENHOUSE --
            add(new InitEngram(
                    GREENHOUSE_CEILING,
                    "Greenhouse Ceiling",
                    "A metal-framed, glass ceiling that provides insulation, and doubles as a floor for higher levels. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 10 );
                        append( ResourceInitializer.CRYSTAL, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));
            add(new InitEngram(
                    GREENHOUSE_WALL,
                    "Greenhouse Wall",
                    "A metal-framed, glass wall that insulates the inside from the outside and separates rooms. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 7 );
                        append( ResourceInitializer.CRYSTAL, 25 );
                        append( ResourceInitializer.CEMENTING_PASTE, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));
            add(new InitEngram(
                    SLOPED_GREENHOUSE_WALL_LEFT,
                    "Sloped Greenhouse Wall Left",
                    "A metal-frame, glass sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 4 );
                        append( ResourceInitializer.CRYSTAL, 15 );
                        append( ResourceInitializer.CEMENTING_PASTE, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));
            add(new InitEngram(
                    SLOPED_GREENHOUSE_WALL_RIGHT,
                    "Sloped Greenhouse Wall Right",
                    "A metal-frame, glass sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 4 );
                        append( ResourceInitializer.CRYSTAL, 15 );
                        append( ResourceInitializer.CEMENTING_PASTE, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));
            add(new InitEngram(
                    GREENHOUSE_DOORFRAME,
                    "Greenhouse Doorframe",
                    "A metal-frame, glass wall that provides entrance to a structure. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.CRYSTAL, 20 );
                        append( ResourceInitializer.CEMENTING_PASTE, 5 );
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));
            add(new InitEngram(
                    GREENHOUSE_DOOR,
                    "Greenhouse Door",
                    "A metal-frame, glass door that provides entrance to structures. Can be locked for security. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 3 );
                        append( ResourceInitializer.CRYSTAL, 10 );
                        append( ResourceInitializer.CEMENTING_PASTE, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));
            add(new InitEngram(
                    GREENHOUSE_WINDOW,
                    "Greenhouse Window",
                    "Metal-framed, glass plates on hinges that cover windows to provide protection from projectiles and spying. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 1 );
                        append( ResourceInitializer.CRYSTAL, 3 );
                        append( ResourceInitializer.CEMENTING_PASTE, 1 );
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));
            add(new InitEngram(
                    SLOPED_GREENHOUSE_ROOF,
                    "Sloped Greenhouse Roof",
                    "An inclined metal-frame, glass roof. Slightly different angle than the ramp. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 10 );
                        append( ResourceInitializer.CRYSTAL, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));

            // -- STRUCTURES > METAL --
            add(new InitEngram(
                    BALLISTA_TURRET,
                    "Ballista Turret",
                    "Survivors can mount this and fire devastating ballista arrows, useful for bringing down large dinos and structures!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 500 );
                        append( ResourceInitializer.METAL_INGOT, 300 );
                        append( ResourceInitializer.STONE, 250 );
                        append( ResourceInitializer.FIBER, 200 );
                        append( ResourceInitializer.THATCH, 100 );
                        append( ResourceInitializer.HIDE, 100 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    CATAPULT_TURRET,
                    "Catapult Turret",
                    "Mount this to throw destructive Boulders at your enemies, particularly effective against Structures.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 400 );
                        append( ResourceInitializer.METAL_INGOT, 300 );
                        append( ResourceInitializer.STONE, 390 );
                        append( ResourceInitializer.THATCH, 100 );
                        append( ResourceInitializer.HIDE, 100 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    MINIGUN_TURRET,
                    "Minigun Turret",
                    "Mount this to fire a hail of Advanced Rifle Bullets at your enemies. Powered by the rider.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.POLYMER, 150 );
                        append( ResourceInitializer.METAL_INGOT, 500 );
                        append( ResourceInitializer.CEMENTING_PASTE, 125 );
                        append( ResourceInitializer.HIDE, 100 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    ROCKET_TURRET,
                    "Rocket Turret",
                    "Mount this to fire Rockets at your enemies. Powered by the rider.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.POLYMER, 400 );
                        append( ResourceInitializer.METAL_INGOT, 1100 );
                        append( ResourceInitializer.CEMENTING_PASTE, 300 );
                        append( ResourceInitializer.ELECTRONICS, 500 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    PRIMITIVE_CANNON,
                    "Primitive Cannon",
                    "A powerful, heavy weapon of war, capable of demolishing heavily reinforced structures.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 950 );
                        append( ResourceInitializer.FIBER, 200 );
                        append( ResourceInitializer.HIDE, 150 );
                        append( ResourceInitializer.THATCH, 120 );
                        append( ResourceInitializer.CEMENTING_PASTE, 300 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    BEHEMOTH_GATE,
                    "Behemoth Gate",
                    "A large metal-plated concrete gate that can be used with a Behemoth Gateway to allow even the largest of creatures in or out.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 1500 );
                        append( ResourceInitializer.CEMENTING_PASTE, 350 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    BEHEMOTH_GATEWAY,
                    "Behemoth Gateway",
                    "A large metal-plated concrete gateway, reinforced with obsidian polymer, that can be used with a Behemoth Gate to allow even the largest of creatures in or out.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 1500 );
                        append( ResourceInitializer.CEMENTING_PASTE, 350 );
                        append( ResourceInitializer.POLYMER, 100 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_CATWALK,
                    "Metal Catwalk",
                    "A thin walkway for bridging areas together. Made from metal plates.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_CEILING,
                    "Metal Ceiling",
                    "A stable metal-plated concrete ceiling that provides insulation, and doubles as a floor for higher levels.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_DINOSAUR_GATE,
                    "Metal Dinosaur Gate",
                    "A large metal gate that can be used with a Gateway to keep most dinosaurs in or out.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_DINOSAUR_GATEWAY,
                    "Metal Dinosaur Gateway",
                    "A large metal gateway that can be used with a gate to keep most dinosaurs in or out.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 85 );
                        append( ResourceInitializer.CEMENTING_PASTE, 25 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_DOOR,
                    "Metal Door",
                    "A stable metal-plated concrete door that provides entrance to structures. Can be locked for security.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_DOORFRAME,
                    "Metal Doorframe",
                    "A metal-plated concrete wall that provides entrance to a structure.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 20 );
                        append( ResourceInitializer.CEMENTING_PASTE, 6 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_FENCE_FOUNDATION,
                    "Metal Fence Foundation",
                    "This very strong, narrow foundation is used to build walls around an area.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.CEMENTING_PASTE, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_FOUNDATION,
                    "Metal Foundation",
                    "A foundation is required to build structures in an area. This one is made from sturdy metal-plated concrete.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 50 );
                        append( ResourceInitializer.CEMENTING_PASTE, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_HATCHFRAME,
                    "Metal Hatchframe",
                    "This metal-plated concrete ceiling has a hole in it for trapdoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 30 );
                        append( ResourceInitializer.CEMENTING_PASTE, 8 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_LADDER,
                    "Metal Ladder",
                    "A simple metal ladder used to climb up or down tall structures. Can also be used to extend existing ladders.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_PILLAR,
                    "Metal Pillar",
                    "This metal-plated concrete pillar adds structural integrity to the area it is built under. Can also act as stilts for building on inclines.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 25 );
                        append( ResourceInitializer.CEMENTING_PASTE, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_RAILING,
                    "Metal Railing",
                    "A metal-plated concrete railing that acts a a simple barrier to prevent people from falling.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 12 );
                        append( ResourceInitializer.CEMENTING_PASTE, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_RAMP,
                    "Metal Ramp",
                    "An inclined metal-plated concrete floor for travelling up or down levels. Can also be used to make an angled roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_SPIKE_WALL,
                    "Metal Spike Wall",
                    "These incredibly sharp metal spikes are dangerous to any that touch them. Large creatures take more damage.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 12 );
                        append( ResourceInitializer.CEMENTING_PASTE, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_TRAPDOOR,
                    "Metal Trapdoor",
                    "This metal-plated concrete slab can be used to secure hatches.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 10 );
                        append( ResourceInitializer.CEMENTING_PASTE, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_TREE_PLATFORM,
                    "Metal Tree Platform",
                    "Attaches to a large tree, enabling you to build on it.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 1800 );
                        append( ResourceInitializer.CEMENTING_PASTE, 1400 );
                        append( ResourceInitializer.FIBER, 600 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_WALL,
                    "Metal Wall",
                    "A metal-plated concrete wall that insulates the inside from the outside and separates rooms.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 25 );
                        append( ResourceInitializer.CEMENTING_PASTE, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_WINDOW,
                    "Metal Window",
                    "Metal plates on hinges that cover windows to provide protection from projectiles and spying.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_WINDOWFRAME,
                    "Metal Windowframe",
                    "A metal-plated concrete wall, with a hole for a window.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 20 );
                        append( ResourceInitializer.CEMENTING_PASTE, 6 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    SLOPED_METAL_ROOF,
                    "Sloped Metal Roof",
                    "An inclined metal roof. Slightly different angle than the ramp.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 35 );
                        append( ResourceInitializer.CEMENTING_PASTE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    SLOPED_METAL_WALL_LEFT,
                    "Sloped Metal Wall Left",
                    "A sturdy metal sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 15 );
                        append( ResourceInitializer.CEMENTING_PASTE, 5 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    SLOPED_METAL_WALL_RIGHT,
                    "Sloped Metal Wall Right",
                    "A sturdy metal sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 15 );
                        append( ResourceInitializer.CEMENTING_PASTE, 5 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    WALL_TORCH,
                    "Wall Torch",
                    "A torch on a metal connector that lights and warms the immediate area. Must be placed on a wall.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 4 );
                        append( ResourceInitializer.FLINT, 1 );
                        append( ResourceInitializer.STONE, 1 );
                        append( ResourceInitializer.WOOD, 2 );
                        append( ResourceInitializer.METAL_INGOT, 1 );
                        append( ResourceInitializer.CEMENTING_PASTE, 1 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));
            add(new InitEngram(
                    METAL_SPIRAL_STAIRCASE,
                    "Metal Staircase",
                    "A metal spiral staircase, useful in constructing multi-level buildings.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 125 );
                        append( ResourceInitializer.CEMENTING_PASTE, 35 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));

            // -- STRUCTURES > METAL > SIGNS --
            add(new InitEngram(
                    METAL_BILLBOARD,
                    "Metal Billboard",
                    "A large metal billboard for landmark navigation or relaying messages.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.CEMENTING_PASTE, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.SIGNS));
            add(new InitEngram(
                    METAL_SIGN,
                    "Metal Sign",
                    "A small metal sign for landmark navigation or relaying messages.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.CEMENTING_PASTE, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.SIGNS));
            add(new InitEngram(
                    METAL_WALL_SIGN,
                    "Metal Wall Sign",
                    "A simple metal wall sign for basic messages, requires attaching to a wall.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 5 );
                        append( ResourceInitializer.CEMENTING_PASTE, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.SIGNS));

            // -- STRUCTURES > METAL > STORAGE --
            add(new InitEngram(
                    VAULT,
                    "Vault",
                    "A large metal vault to securely store goods in.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 300 );
                        append( ResourceInitializer.CEMENTING_PASTE, 60 );
                        append( ResourceInitializer.OIL, 30 );
                        append( ResourceInitializer.POLYMER, 60 );
                    }},
                    CategoryInitializer.STRUCTURES.METAL.STORAGE));

            // -- STRUCTURES > MISC --
            add(new InitEngram(
                    PAINTING_CANVAS,
                    "Painting Canvas",
                    "A canvas sheet stretched over a wooden frame. Perfect for painting on.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.FIBER, 4 );
                        append( ResourceInitializer.WOOD, 2 );
                        append( ResourceInitializer.HIDE, 1 );
                    }},
                    CategoryInitializer.STRUCTURES.MISC));
            add(new InitEngram(
                    SINGLE_PANEL_FLAG,
                    "Single-Panel Flag",
                    "Mark your tribe's territory and show off its colors by placing this and painting it.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 10 );
                        append( ResourceInitializer.FIBER, 100 );
                        append( ResourceInitializer.HIDE, 20 );
                        append( ResourceInitializer.THATCH, 30 );
                    }},
                    CategoryInitializer.STRUCTURES.MISC));
            add(new InitEngram(
                    MULTI_PANEL_FLAG,
                    "Multi-Panel Flag",
                    "Mark your tribes territory and show off its colours by placing this and dying it.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 10 );
                        append( ResourceInitializer.FIBER, 100 );
                        append( ResourceInitializer.HIDE, 20 );
                        append( ResourceInitializer.THATCH, 30 );
                    }},
                    CategoryInitializer.STRUCTURES.MISC));
            add(new InitEngram(
                    TRAINING_DUMMY,
                    "Training Dummy",
                    "Attack this training dummy to test your DPS!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 80 );
                        append( ResourceInitializer.FIBER, 500 );
                        append( ResourceInitializer.HIDE, 10 );
                        append( ResourceInitializer.THATCH, 500 );
                    }},
                    CategoryInitializer.STRUCTURES.MISC));
            add(new InitEngram(
                    TREE_SAP_TAP,
                    "Tree Sap Tap",
                    "Attach this to a large tree to tap its sap over time.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 100 );
                        append( ResourceInitializer.CEMENTING_PASTE, 40 );
                    }},
                    CategoryInitializer.STRUCTURES.MISC));
            add(new InitEngram(
                    TROPHY_BASE,
                    "Trophy Base",
                    "Provides the base upon which you can place a Trophy!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.OBSIDIAN, 80 );
                    }},
                    CategoryInitializer.STRUCTURES.MISC));
            add(new InitEngram(
                    TROPHY_WALL_MOUNT,
                    "Trophy Wall-Mount",
                    "Provides the wall-mount upon which you can place a trophy!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 200 );
                        append( ResourceInitializer.CEMENTING_PASTE, 80 );
                    }},
                    CategoryInitializer.STRUCTURES.MISC));
            add(new InitEngram(
                    WAR_MAP,
                    "War Map",
                    "A map of the Island upon which you can draw your plans.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.FIBER, 4 );
                        append( ResourceInitializer.WOOD, 2 );
                        append( ResourceInitializer.HIDE, 1 );
                    }},
                    CategoryInitializer.STRUCTURES.MISC));

            // -- STRUCTURES > PIPES --
            // -- STRUCTURES > PIPES > METAL --
            add(new InitEngram(
                    METAL_IRRIGATION_PIPE_INCLINED,
                    "Metal Irrigation Pipe - Inclined",
                    "An inclined metal pipe for an irrigation network, used for transporting water up and down hills.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 4 );
                        append( ResourceInitializer.CEMENTING_PASTE, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.METAL));
            add(new InitEngram(
                    METAL_IRRIGATION_PIPE_INTAKE,
                    "Metal Irrigation Pipe - Intake",
                    "The metal intake for an irrigation network that transports water over land.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 15 );
                        append( ResourceInitializer.CEMENTING_PASTE, 5 );
                        append( ResourceInitializer.WOOD, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.METAL));
            add(new InitEngram(
                    METAL_IRRIGATION_PIPE_INTERSECTION,
                    "Metal Irrigation Pipe - Intersection",
                    "A plus-shaped metal intersection for an irrigation network, used for splitting one water source into three.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 6 );
                        append( ResourceInitializer.CEMENTING_PASTE, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.METAL));
            add(new InitEngram(
                    METAL_IRRIGATION_PIPE_STRAIGHT,
                    "Metal Irrigation Pipe - Straight",
                    "A straight metal pipe for an irrigation network, used for transporting water across land.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 2 );
                        append( ResourceInitializer.CEMENTING_PASTE, 1 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.METAL));
            add(new InitEngram(
                    METAL_IRRIGATION_PIPE_TAP,
                    "Metal Irrigation Pipe - Tap",
                    "This metal tap allows access to the water in an irrigation network. Can refill containers, irrigate crop plots, or provide a refreshing drink.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 10 );
                        append( ResourceInitializer.CEMENTING_PASTE, 4 );
                        append( ResourceInitializer.WOOD, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.METAL));
            add(new InitEngram(
                    METAL_IRRIGATION_PIPE_VERTICAL,
                    "Metal Irrigation Pipe - Vertical",
                    "A vertical metal pipe for an irrigation network, used for transporting water up and down cliffs.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 2 );
                        append( ResourceInitializer.CEMENTING_PASTE, 1 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.METAL));
            add(new InitEngram(
                    METAL_WATER_RESERVOIR,
                    "Metal Water Reservoir",
                    "Attach this to pipes to store rain, irrigation, or manually deposited water for later use. Will automatically irrigate nearby crops if attached to a tap. Stores a large amount of water!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.METAL_INGOT, 75 );
                        append( ResourceInitializer.CEMENTING_PASTE, 25 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.METAL));

            // -- STRUCTURES > PIPES > STONE --
            add(new InitEngram(
                    STONE_IRRIGATION_PIPE_INCLINED,
                    "Stone Irrigation Pipe - Inclined",
                    "An inclined stone pipe for an irrigation network, used for transporting water up and down hills.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.STONE));
            add(new InitEngram(
                    STONE_IRRIGATION_PIPE_INTAKE,
                    "Stone Irrigation Pipe - Intake",
                    "The stone intake for an irrigation network that transports water over land.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 25 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.STONE));
            add(new InitEngram(
                    STONE_IRRIGATION_PIPE_INTERSECTION,
                    "Stone Irrigation Pipe - Intersection",
                    "A plus-shaped stone intersection for an irrigation network, used for splitting one water source into three.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.STONE));
            add(new InitEngram(
                    STONE_IRRIGATION_PIPE_STRAIGHT,
                    "Stone Irrigation Pipe - Straight",
                    "A straight stone pipe for an irrigation network, used for transporting water across land.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.STONE));
            add(new InitEngram(
                    STONE_IRRIGATION_PIPE_TAP,
                    "Stone Irrigation Pipe - Tap",
                    "This stone tap allows access to the water in an irrigation network. Can refill containers, irrigate crop plots, or provide a refreshing drink.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 10 );
                        append( ResourceInitializer.WOOD, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.STONE));
            add(new InitEngram(
                    STONE_IRRIGATION_PIPE_VERTICAL,
                    "Stone Irrigation Pipe - Vertical",
                    "A vertical stone pipe for an irrigation network, used for transporting water up and down cliffs.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 5 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.STONE));
            add(new InitEngram(
                    WATER_RESERVOIR,
                    "Water Reservoir",
                    "A standing storage device for holding water. Automatically fills up during rain, can be filled up with the use of a water skin/jar.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 30 );
                        append( ResourceInitializer.CEMENTING_PASTE, 5 );
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.STONE));

            // -- STRUCTURES > STONE --
            add(new InitEngram(
                    BEHEMOTH_REINFORCED_DINOSAUR_GATE,
                    "Behemoth Reinforced Dinosaur Gate",
                    "A large, reinforced wooden gate that can be used with a Behemoth Gateway to keep dinosaurs in or out.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 450 );
                        append( ResourceInitializer.WOOD, 450 );
                        append( ResourceInitializer.THATCH, 450 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    BEHEMOTH_REINFORCED_DINOSAUR_GATEWAY,
                    "Behemoth Stone Dinosaur Gateway",
                    "A massive brick-and-mortar gateway that can be used for penning up huge dinosaurs.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 450 );
                        append( ResourceInitializer.WOOD, 450 );
                        append( ResourceInitializer.THATCH, 450 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    REINFORCED_DINOSAUR_GATE,
                    "Reinforced Dinosaur Gate",
                    "A large, reinforced wooden gate that can be used with a Gateway to keep dinosaurs in or out.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 60 );
                        append( ResourceInitializer.WOOD, 30 );
                        append( ResourceInitializer.THATCH, 20 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    REINFORCED_TRAPDOOR,
                    "Reinforced Trapdoor",
                    "This small reinforced door can be used to secure hatches.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 20 );
                        append( ResourceInitializer.WOOD, 14 );
                        append( ResourceInitializer.THATCH, 8 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    REINFORCED_WINDOW,
                    "Reinforced Window",
                    "Reinforced window covering to provide protection from projectiles and spying.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 8 );
                        append( ResourceInitializer.WOOD, 4 );
                        append( ResourceInitializer.THATCH, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    REINFORCED_WOODEN_DOOR,
                    "Reinforced Wooden Door",
                    "A reinforced wooden door that provides entrance to structures. Can be locked for security.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 20 );
                        append( ResourceInitializer.WOOD, 14 );
                        append( ResourceInitializer.THATCH, 8 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    SLOPED_STONE_ROOF,
                    "Sloped Stone Roof",
                    "An inclined stone roof. Slightly different angle than the ramp.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 60 );
                        append( ResourceInitializer.WOOD, 30 );
                        append( ResourceInitializer.THATCH, 20 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    SLOPED_STONE_WALL_LEFT,
                    "Sloped Stone Wall Left",
                    "A sturdy stone sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 20 );
                        append( ResourceInitializer.WOOD, 10 );
                        append( ResourceInitializer.THATCH, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    SLOPED_STONE_WALL_RIGHT,
                    "Sloped Stone Wall Right",
                    "A sturdy stone sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 20 );
                        append( ResourceInitializer.WOOD, 10 );
                        append( ResourceInitializer.THATCH, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_CEILING,
                    "Stone Ceiling",
                    "A stable brick-and-mortar ceiling that insulates the inside from the outside, and doubles as a floor for higher levels.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 60 );
                        append( ResourceInitializer.WOOD, 30 );
                        append( ResourceInitializer.THATCH, 20 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_DINOSAUR_GATEWAY,
                    "Stone Dinosaur Gateway",
                    "A large brick-and-mortar gateway that can be used with a Gate to keep most dinosaurs in or out.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 140 );
                        append( ResourceInitializer.WOOD, 70 );
                        append( ResourceInitializer.THATCH, 50 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_DOORFRAME,
                    "Stone Doorframe",
                    "A stone wall that provides entrance to a structure.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 30 );
                        append( ResourceInitializer.WOOD, 16 );
                        append( ResourceInitializer.THATCH, 12 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_FENCE_FOUNDATION,
                    "Stone Fence Foundation",
                    "This strong, narrow foundation is used to build walls around an area.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 15 );
                        append( ResourceInitializer.WOOD, 10 );
                        append( ResourceInitializer.THATCH, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_FOUNDATION,
                    "Stone Foundation",
                    "A foundation is required to build structures in an area. This one is made from brick-and-mortar.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 80 );
                        append( ResourceInitializer.WOOD, 40 );
                        append( ResourceInitializer.THATCH, 30 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_HATCHFRAME,
                    "Stone Hatchframe",
                    "This brick-and-mortar ceiling has a hole in it for trapdoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 50 );
                        append( ResourceInitializer.WOOD, 25 );
                        append( ResourceInitializer.THATCH, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_PILLAR,
                    "Stone Pillar",
                    "Adds structural integrity to the area it is built on. Can also act as stilts for buildings on inclines.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 30 );
                        append( ResourceInitializer.WOOD, 15 );
                        append( ResourceInitializer.THATCH, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_RAILING,
                    "Stone Railing",
                    "A brick-and-mortar railing that acts as a simple barrier to prevent people from falling.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 60 );
                        append( ResourceInitializer.WOOD, 30 );
                        append( ResourceInitializer.THATCH, 20 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_WALL,
                    "Stone Wall",
                    "A brick-and-mortar wall that insulates the inside from the outside and separates rooms.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 40 );
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.THATCH, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_WINDOWFRAME,
                    "Stone Windowframe",
                    "A brick-and-mortar wall with a hole for a window.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 35 );
                        append( ResourceInitializer.WOOD, 18 );
                        append( ResourceInitializer.THATCH, 12 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_SPIRAL_STAIRCASE,
                    "Stone Staircase",
                    "A stone spiral staircase, useful in constructing multi-level buildings.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.STONE, 200 );
                        append( ResourceInitializer.WOOD, 100 );
                        append( ResourceInitializer.THATCH, 75 );
                    }},
                    CategoryInitializer.STRUCTURES.STONE));

            // -- STRUCTURES > THATCH --
            add(new InitEngram(
                    SLOPED_THATCH_ROOF,
                    "Sloped Thatch Roof",
                    "An inclined Thatch roof. Slightly different angle than the ramp.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.WOOD, 4 );
                        append( ResourceInitializer.FIBER, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));
            add(new InitEngram(
                    SLOPED_THATCH_WALL_LEFT,
                    "Sloped Thatch Wall Left",
                    "A simple Thatch sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with a roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 10 );
                        append( ResourceInitializer.WOOD, 2 );
                        append( ResourceInitializer.FIBER, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));
            add(new InitEngram(
                    SLOPED_THATCH_WALL_RIGHT,
                    "Sloped Thatch Wall Right",
                    "A simple Thatch sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with a roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 10 );
                        append( ResourceInitializer.WOOD, 2 );
                        append( ResourceInitializer.FIBER, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));
            add(new InitEngram(
                    THATCH_DOOR,
                    "Thatch Door",
                    "Enough sticks bundled together works as a simple door. Can be locked for security, but not very strong.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 7 );
                        append( ResourceInitializer.FIBER, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));
            add(new InitEngram(
                    THATCH_DOORFRAME,
                    "Thatch Doorframe",
                    "This thatch wall has an entrance in it, but requires more wood to stay stable.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 8 );
                        append( ResourceInitializer.WOOD, 6 );
                        append( ResourceInitializer.FIBER, 6 );
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));
            add(new InitEngram(
                    THATCH_FOUNDATION,
                    "Thatch Foundation",
                    "A foundation is required to build structures. This one is a wooden frame with some smooth bundles of sticks that act as a floor.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 20 );
                        append( ResourceInitializer.WOOD, 6 );
                        append( ResourceInitializer.FIBER, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));
            add(new InitEngram(
                    THATCH_ROOF,
                    "Thatch Roof",
                    "A thatched roof to protect you from the elements. Not stable enough to build on.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.WOOD, 4 );
                        append( ResourceInitializer.FIBER, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));
            add(new InitEngram(
                    THATCH_WALL,
                    "Thatch Wall",
                    "A simple wall made of bundled sticks, and stabilized by a wooden frame. Fairly fragile, but better than nothing.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 10 );
                        append( ResourceInitializer.FIBER, 2 );
                        append( ResourceInitializer.FIBER, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));

            // -- STRUCTURES > WOOD --
            add(new InitEngram(
                    DINOSAUR_GATE,
                    "Dinosaur Gate",
                    "A large wooden gate that can be used with a gateway to keep dinosaurs in or out. Cannot be destroyed by any dinosaur.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 60 );
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.FIBER, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    DINOSAUR_GATEWAY,
                    "Dinosaur Gateway",
                    "A large wood and stone gateway that can be used with a gate to keep most dinosaurs in or out.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 140 );
                        append( ResourceInitializer.STONE, 40 );
                        append( ResourceInitializer.THATCH, 35 );
                        append( ResourceInitializer.FIBER, 25 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    ROPE_LADDER,
                    "Rope Ladder",
                    "A simple rope ladder used to climb up or down tall structures. Can also be used to extend existing ladders.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 15 );
                        append( ResourceInitializer.THATCH, 140 );
                        append( ResourceInitializer.FIBER, 180 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    SLOPED_WOOD_WALL_LEFT,
                    "Sloped Wood Wall Left",
                    "A sturdy Wooden sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.THATCH, 5 );
                        append( ResourceInitializer.FIBER, 5 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    SLOPED_WOOD_WALL_RIGHT,
                    "Sloped Wood Wall Right",
                    "A sturdy Wooden sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.THATCH, 5 );
                        append( ResourceInitializer.FIBER, 5 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    SLOPED_WOODEN_ROOF,
                    "Sloped Wooden Roof",
                    "An inclined wooden roof. Slightly different angle than the ramp.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 60 );
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.FIBER, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    STANDING_TORCH,
                    "Standing Torch",
                    "A torch on a small piece of wood that lights and warms the immediate area.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.THATCH, 8 );
                        append( ResourceInitializer.FLINT, 1 );
                        append( ResourceInitializer.STONE, 1 );
                        append( ResourceInitializer.WOOD, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_CAGE,
                    "Wooden Cage",
                    "A portable cage in which to imprison victims.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 110 );
                        append( ResourceInitializer.THATCH, 30 );
                        append( ResourceInitializer.FIBER, 25 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_CATWALK,
                    "Wooden Catwalk",
                    "A thin walkway for bridging areas together. Made from sturdy wood.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.THATCH, 7 );
                        append( ResourceInitializer.FIBER, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_CEILING,
                    "Wooden Ceiling",
                    "A stable wooden ceiling that insulates the inside from the outside, and doubles as a floor for higher levels.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 60 );
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.FIBER, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_DOOR,
                    "Wooden Door",
                    "A stable wooden door that provides entrance to structures. Can be locked for security.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.THATCH, 7 );
                        append( ResourceInitializer.FIBER, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_DOORFRAME,
                    "Wooden Doorframe",
                    "A wooden wall that provides entrance to a structure.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 30 );
                        append( ResourceInitializer.THATCH, 8 );
                        append( ResourceInitializer.FIBER, 6 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_FENCE_FOUNDATION,
                    "Wooden Fence Foundation",
                    "This very cheap, narrow foundation is used to build fences around an area.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 10 );
                        append( ResourceInitializer.THATCH, 3 );
                        append( ResourceInitializer.FIBER, 2 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_FOUNDATION,
                    "Wooden Foundation",
                    "A foundation is required to build structures. This one is made from sturdy wood.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 80 );
                        append( ResourceInitializer.THATCH, 20 );
                        append( ResourceInitializer.FIBER, 15 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_HATCHFRAME,
                    "Wooden Hatchframe",
                    "A wooden ceiling with a hole in it for trapdoors.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 50 );
                        append( ResourceInitializer.THATCH, 12 );
                        append( ResourceInitializer.FIBER, 8 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_LADDER,
                    "Wooden Ladder",
                    "A simple wooden ladder used to climb up or down tall structures. Can also be used to extend existing ladders.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 4 );
                        append( ResourceInitializer.THATCH, 7 );
                        append( ResourceInitializer.FIBER, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_PILLAR,
                    "Wooden Pillar",
                    "Adds structural integrity to the area it is built on. Can also act as stilts for buildings on inclines.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 40 );
                        append( ResourceInitializer.THATCH, 10 );
                        append( ResourceInitializer.FIBER, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_RAILING,
                    "Wooden Railing",
                    "A sturdy wooden railing that acts a a simple barrier to prevent people from falling.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.THATCH, 5 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_RAMP,
                    "Wooden Ramp",
                    "An inclined wooden floor for travelling up or down. Can also be used to make an angled roof.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 60 );
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.FIBER, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_SPIKE_WALL,
                    "Wooden Spike Wall",
                    "These incredibly sharp wooden stakes are dangerous to any that touch them. Larger creatures take more damage.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 40 );
                        append( ResourceInitializer.HIDE, 10 );
                        append( ResourceInitializer.FIBER, 30 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_TRAPDOOR,
                    "Wooden Trapdoor",
                    "This small wooden door can be used to secure hatches.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 20 );
                        append( ResourceInitializer.THATCH, 7 );
                        append( ResourceInitializer.FIBER, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_TREE_PLATFORM,
                    "Wooden Tree Platform",
                    "Attaches to a large tree, enabling you to build on it.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 1600 );
                        append( ResourceInitializer.METAL_INGOT, 200 );
                        append( ResourceInitializer.CEMENTING_PASTE, 600 );
                        append( ResourceInitializer.FIBER, 600 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_WALL,
                    "Wooden Wall",
                    "A sturdy wooden wall that insulates the inside from the outside, separates rooms, and provides structural integrity.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 40 );
                        append( ResourceInitializer.THATCH, 10 );
                        append( ResourceInitializer.FIBER, 7 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_WINDOW,
                    "Wooden Window",
                    "Wooden beams on hinges that cover windows to provide protection from projectiles and spying.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 8 );
                        append( ResourceInitializer.THATCH, 2 );
                        append( ResourceInitializer.FIBER, 1 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_WINDOWFRAME,
                    "Wooden Windowframe",
                    "A wooden wall with a hole for a window.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 36 );
                        append( ResourceInitializer.THATCH, 9 );
                        append( ResourceInitializer.FIBER, 6 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));
            add(new InitEngram(
                    WOODEN_SPIRAL_STAIRCASE,
                    "Wooden Staircase",
                    "A wooden spiral staircase, useful in constructing multi-level buildings.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 200 );
                        append( ResourceInitializer.THATCH, 50 );
                        append( ResourceInitializer.FIBER, 35 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));

            // -- STRUCTURES > WOOD > SIGNS --
            add(new InitEngram(
                    WOODEN_BILLBOARD,
                    "Wooden Billboard",
                    "A large wooden billboard for landmark navigation or relaying messages.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 60 );
                        append( ResourceInitializer.THATCH, 15 );
                        append( ResourceInitializer.FIBER, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.SIGNS));
            add(new InitEngram(
                    WOODEN_SIGN,
                    "Wooden Sign",
                    "A simple wooden sign for landmark navigation or relaying messages.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 5 );
                        append( ResourceInitializer.THATCH, 3 );
                        append( ResourceInitializer.FIBER, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.SIGNS));
            add(new InitEngram(
                    WOODEN_WALL_SIGN,
                    "Wooden Wall Sign",
                    "A simple wooden wall sign for basic messages, requires attaching to a wall.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 5 );
                        append( ResourceInitializer.THATCH, 3 );
                        append( ResourceInitializer.FIBER, 4 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.SIGNS));

            // -- STRUCTURES > WOOD > STORAGE --
            add(new InitEngram(
                    BOOKSHELF,
                    "Bookshelf",
                    "A large bookshelf to store Blueprints, Notes, and other small trinkets in.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 100 );
                        append( ResourceInitializer.THATCH, 45 );
                        append( ResourceInitializer.FIBER, 35 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.STORAGE));
            add(new InitEngram(
                    FEEDING_TROUGH,
                    "Feeding Trough",
                    "Put food for your nearby pets in this, and they'll automatically eat it when hungry!",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 120 );
                        append( ResourceInitializer.THATCH, 60 );
                        append( ResourceInitializer.FIBER, 40 );
                        append( ResourceInitializer.METAL_ORE, 8 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.STORAGE));
            add(new InitEngram(
                    LARGE_STORAGE_BOX,
                    "Large Storage Box",
                    "A large box to store goods in.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 50 );
                        append( ResourceInitializer.THATCH, 35 );
                        append( ResourceInitializer.FIBER, 25 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.STORAGE));
            add(new InitEngram(
                    STORAGE_BOX,
                    "Storage Box",
                    "A small box to store goods in.",
                    new SparseIntArray() {{
                        append( ResourceInitializer.WOOD, 25 );
                        append( ResourceInitializer.THATCH, 20 );
                        append( ResourceInitializer.FIBER, 10 );
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.STORAGE));
        }
    };

    public static List<InitEngram> getEngrams() {
        return engrams;
    }

    public static int getCount() {
        return engrams.size();
    }
}
