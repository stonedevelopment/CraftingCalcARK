package com.gmail.jaredstone1982.craftingcalcark.model.initializers;

import android.util.SparseIntArray;

import com.gmail.jaredstone1982.craftingcalcark.R;
import com.gmail.jaredstone1982.craftingcalcark.model.InitEngram;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides data to initialize the database with a full list of InitEngram objects
 */
public class EngramInitializer {
    public static final String VERSION = "0.1.1";

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
    public static final int GREENHOUSE_ROOF = R.drawable.structures_greenhouse_greenhouse_roof;
    public static final int GREENHOUSE_WALL = R.drawable.structures_greenhouse_greenhouse_wall;
    public static final int GREENHOUSE_WINDOW = R.drawable.structures_greenhouse_greenhouse_window;
    public static final int SLOPED_GREENHOUSE_WALL_LEFT = R.drawable.structures_greenhouse_sloped_greenhouse_wall_left;
    public static final int SLOPED_GREENHOUSE_WALL_RIGHT = R.drawable.structures_greenhouse_sloped_greenhouse_wall_right;

    // -- STRUCTURES > METAL --
    public static final int BALLISTA_TURRET = R.drawable.structures_metal_ballista_turret;
    public static final int BEHEMOTH_GATE = R.drawable.structures_metal_behemoth_gate;
    public static final int BEHEMOTH_GATEWAY = R.drawable.structures_metal_behemoth_gateway;
    public static final int CATAPULT_TURRET = R.drawable.structures_metal_catapult_turret;
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
    public static final int MINIGUN_TURRET = R.drawable.structures_metal_minigun_turret;
    public static final int PRIMITIVE_CANNON = R.drawable.structures_metal_primitive_cannon;
    public static final int ROCKET_TURRET = R.drawable.structures_metal_rocket_turret;
    public static final int SLOPED_METAL_ROOF = R.drawable.structures_metal_sloped_metal_roof;
    public static final int SLOPED_METAL_WALL_LEFT = R.drawable.structures_metal_sloped_metal_wall_left;
    public static final int SLOPED_METAL_WALL_RIGHT = R.drawable.structures_metal_sloped_metal_wall_right;
    public static final int WALL_TORCH = R.drawable.structures_metal_wall_torch;

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

    // -- STRUCTURES > WOOD > SIGNS --
    public static final int WOODEN_BILLBOARD = R.drawable.wood_signs_wooden_billboard;
    public static final int WOODEN_SIGN = R.drawable.wood_signs_wooden_sign;
    public static final int WOODEN_WALL_SIGN = R.drawable.wood_signs_wooden_wall_sign;

    // -- STRUCTURES > WOOD > STORAGE --
    public static final int BOOKSHELF = R.drawable.wood_storage_bookshelf;
    public static final int FEEDING_TROUGH = R.drawable.wood_storage_feeding_trough;
    public static final int LARGE_STORAGE_BOX = R.drawable.wood_storage_large_storage_box;
    public static final int STORAGE_BOX = R.drawable.wood_storage_storage_box;

    // -- WEAPONS > AMMO --
    public static final int ADVANCED_BULLET = R.drawable.weapons_ammo_advanced_bullet;
    public static final int ADVANCED_RIFLE_BULLET = R.drawable.weapons_ammo_advanced_rifle_bullet;
    public static final int ADVANCED_SNIPER_BULLET = R.drawable.weapons_ammo_advanced_sniper_bullet;
    public static final int BALLISTA_BOLT = R.drawable.weapons_ammo_ballista_bolt;
    public static final int C4_CHARGE = R.drawable.weapons_ammo_c4_charge;
    public static final int CANNON_BALL = R.drawable.weapons_ammo_cannon_ball;
    public static final int CHAIN_BOLA = R.drawable.weapons_ammo_chain_bola;
    public static final int GRAPPLING_HOOK = R.drawable.weapons_ammo_grappling_hook;
    public static final int METAL_ARROW = R.drawable.weapons_ammo_metal_arrow;
    public static final int ROCKET_PROPELLED_GRENADE = R.drawable.weapons_ammo_rocket_propelled_grenade;
    public static final int SIMPLE_BULLET = R.drawable.weapons_ammo_simple_bullet;
    public static final int SIMPLE_RIFLE_AMMO = R.drawable.weapons_ammo_simple_rifle_ammo;
    public static final int SIMPLE_SHUTGUN_AMMO = R.drawable.weapons_ammo_simple_shotgun_ammo;
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
    public static final int TEK_GRENADE = R.drawable.weapons_explosive_tek_grenade;

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

    private static List<InitEngram> engrams = new ArrayList<InitEngram>() {
        {
            // -- ARMOR > CHITIN --
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

            // -- ARMOR > CLOTH --
            add(new InitEngram(
                    CLOTH_BOOTS,
                    "Cloth Boots",
                    "Hide-soled shoes provide some protection from the heat and cold, but only minimal protection from injuries.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FIBER, 25);
                        append(ResourceInitializer.HIDE, 6);
                    }},
                    CategoryInitializer.ARMOR.CLOTH));

            // -- ARMOR > FUR --
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

            // -- ARMOR > GHILLIE --
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

            // -- ARMOR > HIDE --
            add(new InitEngram(
                    HIDE_BOOTS,
                    "Hide Boots",
                    "Keeps you warm while providing some physical protection.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.HIDE, 12);
                        append(ResourceInitializer.FIBER, 5);
                    }},
                    CategoryInitializer.ARMOR.HIDE));

            // -- ARMOR > METAL --
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

            // -- ARMOR > SCUBA --
            add(new InitEngram(
                    SCUBA_FLIPPERS,
                    "SCUBA Flippers",
                    "Provides little defense, but tremendous hypothermal insulation, specifically when underwater.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 40);
                        append(ResourceInitializer.HIDE, 40);
                        append(ResourceInitializer.FIBER, 4);
                        append(ResourceInitializer.METAL_INGOT, 2);
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

            // -- STRUCTURES > BEDS --
            add(new InitEngram(
                    BUNK_BED,
                    "Bunk Bed",
                    "This modern bunk-style bed has two mattresses, and a high thread count. Acts as a respawn point for you and your tribe with half cooldown time.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.POLYMER, 80);
                        append(ResourceInitializer.PELT, 200);
                        append(ResourceInitializer.METAL_INGOT, 320);
                        append(ResourceInitializer.FIBER, 280);
                        append(ResourceInitializer.HIDE, 120);
                    }},
                    CategoryInitializer.STRUCTURES.BEDS));

            // -- STRUCTURES > COOKING --
            add(new InitEngram(
                    CAMPFIRE,
                    "Campfire",
                    "Perfect for cooking meat, staying warm, and providing light.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 12);
                        append(ResourceInitializer.FLINT, 1);
                        append(ResourceInitializer.STONE, 16);
                        append(ResourceInitializer.WOOD, 2);
                    }},
                    CategoryInitializer.STRUCTURES.COOKING));

            // -- STRUCTURES > CRAFTING --
            add(new InitEngram(
                    CHEMISTRY_BENCH,
                    "Chemistry Bench",
                    "Place materials in this to transmute chemical substances with extreme efficiency!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 250);
                        append(ResourceInitializer.CEMENTING_PASTE, 250);
                        append(ResourceInitializer.SPARKPOWDER, 100);
                        append(ResourceInitializer.CRYSTAL, 250);
                        append(ResourceInitializer.POLYMER, 250);
                        append(ResourceInitializer.ELECTRONICS, 250);
                    }},
                    CategoryInitializer.STRUCTURES.CRAFTING));

            // -- STRUCTURES > DEFENSES --
            add(new InitEngram(
                    BEAR_TRAP,
                    "Bear Trap",
                    "Immobilizes humans and small creatures.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.FIBER, 5);
                        append(ResourceInitializer.METAL_INGOT, 3);
                        append(ResourceInitializer.HIDE, 6);
                    }},
                    CategoryInitializer.STRUCTURES.DEFENSES));

            // -- STRUCTURES > ELECTRIC --
            add(new InitEngram(
                    AIR_CONDITIONER,
                    "Air Conditioner",
                    "Requires electricity to run. Provides insulation from both the heat and the cold to an area.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 80);
                        append(ResourceInitializer.ELECTRONICS, 15);
                        append(ResourceInitializer.POLYMER, 5);
                        append(ResourceInitializer.CRYSTAL, 15);
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRIC));

            // -- STRUCTURES > ELECTRICAL --
            add(new InitEngram(
                    ELECTRICAL_CABLE_INTERSECTION,
                    "Electrical Cable - Intersection",
                    "A plus-shaped intersection for a power grid, used for splitting one power cable into three.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 2);
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ID));

            // -- STRUCTURES > ELECTRICAL > DEFENSES --
            add(new InitEngram(
                    AUTO_TURRET,
                    "Auto Turret",
                    "Requires electricity to run. Consumes bullets while firing. Can be configured to automatically attack hostiles within range.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.ELECTRONICS, 70);
                        append(ResourceInitializer.METAL_INGOT, 140);
                        append(ResourceInitializer.CEMENTING_PASTE, 50);
                        append(ResourceInitializer.POLYMER, 20);
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.DEFENSES));

            // -- STRUCTURES > ELECTRICAL > ELEVATORS --
            add(new InitEngram(
                    ELEVATOR_TRACK,
                    "Elevator Track",
                    "Attach an Elevator Platform to these to complete an elevator!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 140);
                        append(ResourceInitializer.POLYMER, 35);
                        append(ResourceInitializer.CEMENTING_PASTE, 25);
                        append(ResourceInitializer.ELECTRONICS, 20);
                    }},
                    CategoryInitializer.STRUCTURES.ELECTRICAL.ELEVATORS));

            // -- STRUCTURES > FARMING --
            add(new InitEngram(
                    COMPOST_BIN,
                    "Compost Bin",
                    "A large bin for converting thatch and dung into high-quality fertilizer.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 50);
                        append(ResourceInitializer.THATCH, 15);
                        append(ResourceInitializer.FIBER, 12);
                    }},
                    CategoryInitializer.STRUCTURES.FARMING));

            // -- STRUCTURES > FURNITURE --
            // -- STRUCTURES > FURNITURE > STONE --
            add(new InitEngram(
                    GRAVESTONE,
                    "Gravestone",
                    "A simple unadorned stone headstone to mark a grave or commemorate a loved one.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 70);
                        append(ResourceInitializer.CEMENTING_PASTE, 8);
                    }},
                    CategoryInitializer.STRUCTURES.FURNITURE.STONE));

            // -- STRUCTURES > FURNITURE > WOOD --
            add(new InitEngram(
                    WARDRUMS,
                    "War Drums",
                    "A set of tribal wardrums, to let everyone around hear the power of your tribe.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 380);
                        append(ResourceInitializer.HIDE, 220);
                        append(ResourceInitializer.FIBER, 200);
                        append(ResourceInitializer.THATCH, 200);
                    }},
                    CategoryInitializer.STRUCTURES.FURNITURE.WOOD));

            // -- STRUCTURES > FRIDGES --
            add(new InitEngram(
                    PRESERVING_BIN,
                    "Preserving Bin",
                    "Burns simple fuel at low temperatures to dehydrate food and kill bacteria. Keeps perishables from spoiling for a small time.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 30);
                        append(ResourceInitializer.WOOD, 30);
                        append(ResourceInitializer.THATCH, 10);
                        append(ResourceInitializer.FIBER, 20);
                    }},
                    CategoryInitializer.STRUCTURES.FRIDGES));

            // -- STRUCTURES > GREENHOUSE --
            add(new InitEngram(
                    GREENHOUSE_CEILING,
                    "Greenhouse Ceiling",
                    "A metal-framed, glass ceiling that provides insulation, and doubles as a floor for higher levels. Excellent for growing crops indoors.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 10);
                        append(ResourceInitializer.CRYSTAL, 35);
                        append(ResourceInitializer.CEMENTING_PASTE, 10);
                    }},
                    CategoryInitializer.STRUCTURES.GREENHOUSE));

            // -- STRUCTURES > METAL --
            add(new InitEngram(
                    BALLISTA_TURRET,
                    "Ballista Turret",
                    "Survivors can mount this and fire devastating ballista arrows, useful for bringing down large dinos and structures!",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 500);
                        append(ResourceInitializer.METAL_INGOT, 300);
                        append(ResourceInitializer.STONE, 250);
                        append(ResourceInitializer.FIBER, 200);
                        append(ResourceInitializer.THATCH, 100);
                        append(ResourceInitializer.HIDE, 100);
                    }},
                    CategoryInitializer.STRUCTURES.METAL.ID));

            // -- STRUCTURES > METAL > SIGNS --
            add(new InitEngram(
                    METAL_SIGN,
                    "Metal Sign",
                    "A small metal sign for landmark navigation or relaying messages.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 5);
                        append(ResourceInitializer.CEMENTING_PASTE, 3);
                    }},
                    CategoryInitializer.STRUCTURES.METAL.SIGNS));

            // -- STRUCTURES > METAL > STORAGE --
            add(new InitEngram(
                    VAULT,
                    "Vault",
                    "A large metal vault to securely store goods in.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 300);
                        append(ResourceInitializer.CEMENTING_PASTE, 60);
                        append(ResourceInitializer.OIL, 30);
                        append(ResourceInitializer.POLYMER, 60);
                    }},
                    CategoryInitializer.STRUCTURES.METAL.STORAGE));

            // -- STRUCTURES > MISC --
            add(new InitEngram(
                    MULTI_PANEL_FLAG,
                    "Multi-Panel Flag",
                    "Mark your tribes territory and show off its colours by placing this and dying it.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 10);
                        append(ResourceInitializer.FIBER, 100);
                        append(ResourceInitializer.HIDE, 20);
                        append(ResourceInitializer.THATCH, 30);
                    }},
                    CategoryInitializer.STRUCTURES.MISC));

            // -- STRUCTURES > PIPES --
            // -- STRUCTURES > PIPES > METAL --
            add(new InitEngram(
                    METAL_IRRIGATION_PIPE_INCLINED,
                    "Metal Irrigation Pipe - Inclined",
                    "An inclined metal pipe for an irrigation network, used for transporting water up and down hills.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.METAL_INGOT, 4);
                        append(ResourceInitializer.CEMENTING_PASTE, 2);
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.METAL));

            // -- STRUCTURES > PIPES > STONE --
            add(new InitEngram(
                    STONE_IRRIGATION_PIPE_INCLINED,
                    "Stone Irrigation Pipe - Inclined",
                    "An inclined stone pipe for an irrigation network, used for transporting water up and down hills.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 10);
                    }},
                    CategoryInitializer.STRUCTURES.PIPES.STONE));

            // -- STRUCTURES > STONE --
            add(new InitEngram(
                    REINFORCED_DINOSAUR_GATE,
                    "Reinforced Dinosaur Gate",
                    "A large, reinforced wooden gate that can be used with a Gateway to keep dinosaurs in or out.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 60);
                        append(ResourceInitializer.WOOD, 30);
                        append(ResourceInitializer.THATCH, 20);
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    REINFORCED_TRAPDOOR,
                    "Reinforced Trapdoor",
                    "This small reinforced door can be used to secure hatches.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 20);
                        append(ResourceInitializer.WOOD, 14);
                        append(ResourceInitializer.THATCH, 8);
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    REINFORCED_WINDOW,
                    "Reinforced Window",
                    "Reinforced window covering to provide protection from projectiles and spying.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 8);
                        append(ResourceInitializer.WOOD, 4);
                        append(ResourceInitializer.THATCH, 3);
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    REINFORCED_WOODEN_DOOR,
                    "Reinforced Wooden Door",
                    "A reinforced wooden door that provides entrance to structures. Can be locked for security.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 20);
                        append(ResourceInitializer.WOOD, 14);
                        append(ResourceInitializer.THATCH, 8);
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    SLOPED_STONE_ROOF,
                    "Sloped Stone Roof",
                    "An inclined stone roof. Slightly different angle than the ramp.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 60);
                        append(ResourceInitializer.WOOD, 30);
                        append(ResourceInitializer.THATCH, 20);
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    SLOPED_STONE_WALL_LEFT,
                    "Sloped Stone Wall Left",
                    "A sturdy stone sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 20);
                        append(ResourceInitializer.WOOD, 10);
                        append(ResourceInitializer.THATCH, 10);
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    SLOPED_STONE_WALL_RIGHT,
                    "Sloped Stone Wall Right",
                    "A sturdy stone sloped wall that insulates the inside from the outside, separates rooms, and provides structural integrity. Used in conjunction with the roof.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 20);
                        append(ResourceInitializer.WOOD, 10);
                        append(ResourceInitializer.THATCH, 10);
                    }},
                    CategoryInitializer.STRUCTURES.STONE));
            add(new InitEngram(
                    STONE_CEILING,
                    "Stone Ceiling",
                    "A stable brick-and-mortar ceiling that insulates the inside from the outside, and doubles as a floor for higher levels.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.STONE, 60);
                        append(ResourceInitializer.WOOD, 30);
                        append(ResourceInitializer.THATCH, 20);
                    }},
                    CategoryInitializer.STRUCTURES.STONE));

            // -- STRUCTURES > THATCH --
            add(new InitEngram(
                    THATCH_DOOR,
                    "Thatch Door",
                    "Enough sticks bundled together works as a simple door. Can be locked for security, but not very strong.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.THATCH, 7);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.STRUCTURES.THATCH));

            // -- STRUCTURES > WOOD --
            add(new InitEngram(
                    DINOSAUR_GATE,
                    "Dinosaur Gate",
                    "A large wooden gate that can be used with a gateway to keep dinosaurs in or out. Cannot be destroyed by any dinosaur.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 60);
                        append(ResourceInitializer.THATCH, 15);
                        append(ResourceInitializer.FIBER, 10);
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.ID));

            // -- STRUCTURES > WOOD > SIGNS --
            add(new InitEngram(
                    WOODEN_SIGN,
                    "Wooden Sign",
                    "A simple wooden sign for landmark navigation or relaying messages.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 5);
                        append(ResourceInitializer.THATCH, 3);
                        append(ResourceInitializer.FIBER, 4);
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.SIGNS));

            // -- STRUCTURES > WOOD > STORAGE --
            add(new InitEngram(
                    STORAGE_BOX,
                    "Storage Box",
                    "A small box to store goods in.",
                    new SparseIntArray() {{
                        append(ResourceInitializer.WOOD, 25);
                        append(ResourceInitializer.THATCH, 20);
                        append(ResourceInitializer.FIBER, 10);
                    }},
                    CategoryInitializer.STRUCTURES.WOOD.STORAGE));

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
        }
    };

    /**
     * Return a list of InitEngram Objects as generated for database
     *
     * @return SparseArray of InitEngram Objects
     */
    public static List<InitEngram> getEngrams() {
        return engrams;
    }

    public static int getCount() {
        return engrams.size();
    }
}