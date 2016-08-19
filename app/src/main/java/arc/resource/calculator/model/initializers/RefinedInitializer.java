package arc.resource.calculator.model.initializers;

import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.R;
import arc.resource.calculator.model.InitEngram;

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
public class RefinedInitializer {

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

    private static List<InitEngram> engrams = new ArrayList<InitEngram>() {
        {
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
        }
    };

    public static List<InitEngram> getEngrams() {
        return engrams;
    }

    public static int getCount() {
        return engrams.size();
    }
}
