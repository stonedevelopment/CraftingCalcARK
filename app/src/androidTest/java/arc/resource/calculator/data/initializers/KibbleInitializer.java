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
public class KibbleInitializer {
    private static final int KIBBLE = R.drawable.kibble;
    private static final String DESCRIPTION = "This pet food recipe has been carefully designed to give balanced nutrition to almost any creature native to the island. Includes plant fibers to help with digestion and egg to bind the mix. Humans have difficulty digesting this.";

    private static List<InitEngram> engrams = new ArrayList<InitEngram>() {
        {
            add( new InitEngram(
                    KIBBLE,
                    "Ankylo Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.ANKYLO_EGG, 1 );
                        append( ResourceInitializer.SAVOROOT, 1 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Araneo Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.ARANEO_EGG, 1 );
                        append( ResourceInitializer.SAVOROOT, 1 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Argentavis Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.ARGENTAVIS_EGG, 1 );
                        append( ResourceInitializer.CITRONAL, 1 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Bronto Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.BRONTO_EGG, 1 );
                        append( ResourceInitializer.ROCKARROT, 1 );
                        append( ResourceInitializer.COOKED_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Carno Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.CARNO_EGG, 1 );
                        append( ResourceInitializer.SAVOROOT, 1 );
                        append( ResourceInitializer.COOKED_MEAT, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Compy Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.COMPY_EGG, 1 );
                        append( ResourceInitializer.CITRONAL, 1 );
                        append( ResourceInitializer.COOKED_FISH_MEAT, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Dilo Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.DILO_EGG, 1 );
                        append( ResourceInitializer.CITRONAL, 1 );
                        append( ResourceInitializer.COOKED_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Dimetrodon Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.DIMETRODON_EGG, 1 );
                        append( ResourceInitializer.CITRONAL, 1 );
                        append( ResourceInitializer.COOKED_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Dimorph Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.DIMORPH_EGG, 1 );
                        append( ResourceInitializer.LONGRASS, 1 );
                        append( ResourceInitializer.COOKED_MEAT_OR_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Diplo Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.DIPLO_EGG, 1 );
                        append( ResourceInitializer.SAVOROOT, 1 );
                        append( ResourceInitializer.RARE_FLOWER, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Dodo Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.DODO_EGG, 1 );
                        append( ResourceInitializer.ROCKARROT, 1 );
                        append( ResourceInitializer.COOKED_MEAT, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Gallimimus Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.GALLIMIMUS_EGG, 1 );
                        append( ResourceInitializer.SAVOROOT, 1 );
                        append( ResourceInitializer.COOKED_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Kairuku Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.KAIRUKU_EGG, 1 );
                        append( ResourceInitializer.SAVOROOT, 1 );
                        append( ResourceInitializer.COOKED_MEAT_OR_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Lystro Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.LYSTRO_EGG, 1 );
                        append( ResourceInitializer.ROCKARROT, 1 );
                        append( ResourceInitializer.COOKED_PRIME_MEAT_OR_PRIME_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Pachy Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.PACHY_EGG, 1 );
                        append( ResourceInitializer.CITRONAL, 1 );
                        append( ResourceInitializer.COOKED_MEAT, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Parasaur Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.PARASAUR_EGG, 1 );
                        append( ResourceInitializer.LONGRASS, 1 );
                        append( ResourceInitializer.COOKED_MEAT_OR_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Pteranodon Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.PTERANODON_EGG, 1 );
                        append( ResourceInitializer.ROCKARROT, 1 );
                        append( ResourceInitializer.COOKED_MEAT_OR_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Pulmonoscorpios Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.PULMONOSCORPIUS_EGG, 1 );
                        append( ResourceInitializer.LONGRASS, 1 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Quetzal Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.QUETZAL_EGG, 1 );
                        append( ResourceInitializer.ROCKARROT, 3 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 3 );
                        append( ResourceInitializer.MEJOBERRY, 100 );
                        append( ResourceInitializer.FIBER, 120 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Raptor Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.RAPTOR_EGG, 1 );
                        append( ResourceInitializer.LONGRASS, 1 );
                        append( ResourceInitializer.COOKED_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Rex Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.REX_EGG, 1 );
                        append( ResourceInitializer.LONGRASS, 1 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Sarco Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.SARCO_EGG, 1 );
                        append( ResourceInitializer.ROCKARROT, 1 );
                        append( ResourceInitializer.COOKED_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Spino Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.SPINO_EGG, 1 );
                        append( ResourceInitializer.SAVOROOT, 1 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Stego Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.STEGO_EGG, 1 );
                        append( ResourceInitializer.CITRONAL, 1 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Terror Bird Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.TERROR_BIRD_EGG, 1 );
                        append( ResourceInitializer.CITRONAL, 1 );
                        append( ResourceInitializer.COOKED_MEAT_OR_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Titanoboa Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.TITANOBOA_EGG, 1 );
                        append( ResourceInitializer.LONGRASS, 1 );
                        append( ResourceInitializer.COOKED_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Trike Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.TRIKE_EGG, 1 );
                        append( ResourceInitializer.SAVOROOT, 1 );
                        append( ResourceInitializer.COOKED_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
            add( new InitEngram(
                    KIBBLE,
                    "Carbonemys Kibble",
                    DESCRIPTION,
                    new SparseIntArray() {{
                        append( ResourceInitializer.CARBONEMYS_EGG, 1 );
                        append( ResourceInitializer.ROCKARROT, 1 );
                        append( ResourceInitializer.PRIME_MEAT_JERKY, 1 );
                        append( ResourceInitializer.MEJOBERRY, 2 );
                        append( ResourceInitializer.FIBER, 3 );
                    }},
                    CategoryInitializer.KIBBLE.ID ) );
        }
    };

    public static List<InitEngram> getEngrams() {
        return engrams;
    }

    public static int getCount() {
        return engrams.size();
    }
}
