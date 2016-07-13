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

    // List of constants set to each InitEngram resource id
    public static final int REINFORCED_DINOSAUR_GATE = R.drawable.structure_stone_reinforced_dinosaur_gate;
    public static final int REINFORCED_TRAPDOOR = R.drawable.structure_stone_reinforced_trapdoor;
    public static final int REINFORCED_WINDOW = R.drawable.structure_stone_reinforced_window;
    public static final int REINFORCED_WOODEN_DOOR = R.drawable.structure_stone_reinforced_wooden_door;
    public static final int SLOPED_STONE_ROOF = R.drawable.structure_stone_sloped_stone_roof;
    public static final int SLOPED_STONE_WALL_LEFT = R.drawable.structure_stone_sloped_stone_wall_left;
    public static final int SLOPED_STONE_WALL_RIGHT = R.drawable.structure_stone_sloped_stone_wall_right;
    public static final int STONE_CEILING = R.drawable.structure_stone_stone_ceiling;
    public static final int STONE_DINOSAUR_GATEWAY = R.drawable.structure_stone_stone_dinosaur_gateway;
    public static final int STONE_DOORFRAME = R.drawable.structure_stone_stone_doorframe;
    public static final int STONE_FENCE_FOUNDATION = R.drawable.structure_stone_stone_fence_foundation;
    public static final int STONE_FOUNDATION = R.drawable.structure_stone_stone_foundation;
    public static final int STONE_HATCHFRAME = R.drawable.structure_stone_stone_hatchframe;
    public static final int STONE_PILLAR = R.drawable.structure_stone_stone_pillar;
    public static final int STONE_RAILING = R.drawable.structure_stone_stone_railing;
    public static final int STONE_WALL = R.drawable.structure_stone_stone_wall;
    public static final int STONE_WINDOWFRAME = R.drawable.structure_stone_stone_windowframe;

    private static List<InitEngram> engrams = new ArrayList<InitEngram>() {
        {
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
}