package arc.resource.calculator.data.initializers;

import java.util.ArrayList;
import java.util.List;

import arc.resource.calculator.model.Category;

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
public class CategoryInitializer {
    public static final String VERSION = "245.0";

    public static class MISC {

        public static final long ID = 100;
    }

    public static class STRUCTURES {

        public static final long ID = 200;
        public static final long THATCH = 201;

        public static class WOOD {

            public static final long ID = 202;
            public static final long SIGNS = 2021;
            public static final long STORAGE = 2022;
        }

        public static final long STONE = 203;

        public static final long COOKING = 204;
        public static final long BEDS = 205;
        public static final long CRAFTING = 206;
        public static final long MISC = 207;

        public static class FURNITURE {

            public static final long ID = 208;
            public static final long WOOD = 2081;
            public static final long STONE = 2082;
        }

        public static final long FARMING = 209;

        public static class PIPES {

            public static final long ID = 210;
            public static final long STONE = 2101;
            public static final long METAL = 2102;
        }

        public static final long FRIDGES = 211;

        public static class METAL {

            public static final long ID = 212;
            public static final long SIGNS = 2121;
            public static final long STORAGE = 2122;
        }

        public static final long DEFENSES = 213;

        public static final long GREENHOUSE = 214;

        public static class ELECTRICAL {

            public static final long ID = 215;
            public static final long DEFENSES = 2151;
            public static final long ELEVATORS = 2152;
        }

        public static final long ELECTRIC = 216;

    }

    public static class WEAPONS {

        public static final long ID = 300;

        public static class PRIMITIVE {

            public static final long ID = 301;
            public static final long MELEE = 3011;
            public static final long RANGED = 3012;
        }

        public static final long AMMO = 302;

        public static final long TRIPWIRES = 303;
        public static final long EXPLOSIVES = 304;
        public static final long FIREARMS = 305;
        public static final long MELEE = 306;
        public static final long ATTACHMENTS = 307;
        public static final long RANGED = 308;
    }

    public static class ARMOR {

        public static final long ID = 400;
        public static final long CLOTH = 401;
        public static final long SHIELDS = 402;
        public static final long HIDE = 403;
        public static final long GHILLIE = 404;
        public static final long CHITIN = 405;
        public static final long FUR = 406;
        public static final long METAL = 407;
        public static final long SCUBA = 408;
        public static final long RIOT = 409;
        public static final long MISC = 410;
    }

    public static class SADDLES {

        public static final long ID = 500;
    }

    public static class NAVIGATION {

        public static final long ID = 600;
    }

    public static class COMMUNICATION {

        public static final long ID = 700;
    }

    public static class OFFHAND {

        public static final long ID = 800;
    }

    public static class COMPOSITES {

        public static final long ID = 900;
    }

    public static class CONSUMABLES {

        public static final long ID = 1000;
        public static final long DRUGS = 1001;
        public static final long FOOD = 1002;
    }

    public static class DYES {

        public static final long ID = 1100;
    }

    public static class KIBBLE {

        public static final long ID = 1200;
    }

    public static class REFINED {

        public static final long ID = 1300;
    }

    private static List<Category> categories = new ArrayList<Category>() {
        {
            // -- LEVEL 0
            add(new Category( MISC.ID, "Misc"));
            add(new Category( STRUCTURES.ID, "Structures"));
            add(new Category( WEAPONS.ID, "Weapons"));
            add(new Category( ARMOR.ID, "Armor"));
            add(new Category( SADDLES.ID, "Saddles"));
            add(new Category( NAVIGATION.ID, "Navigation"));
            add(new Category( COMMUNICATION.ID, "Communication"));
            add(new Category( OFFHAND.ID, "Off-Hand"));
            add(new Category( COMPOSITES.ID, "Composite"));
            add(new Category( CONSUMABLES.ID, "Consumables"));
            add(new Category( DYES.ID, "Dyes"));
            add(new Category( KIBBLE.ID, "Kibble"));
            add(new Category( REFINED.ID, "Refined"));

            // -- LEVEL 1 > STRUCTURES
            add( new Category( STRUCTURES.THATCH, "Thatch", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.WOOD.ID, "Wood", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.STONE, "Stone", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.COOKING, "Cooking", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.BEDS, "Beds", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.CRAFTING, "Crafting", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.MISC, "Misc", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.FURNITURE.ID, "Furniture", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.FARMING, "Farming", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.PIPES.ID, "Pipes", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.FRIDGES, "Fridges", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.METAL.ID, "Metal", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.DEFENSES, "Defenses", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.GREENHOUSE, "Greenhouse", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.ELECTRICAL.ID, "Electrical", STRUCTURES.ID ) );
            add( new Category( STRUCTURES.ELECTRIC, "Electric", STRUCTURES.ID ) );

            // -- LEVEL 1 > WEAPONS
            add( new Category( WEAPONS.PRIMITIVE.ID, "Primitive", WEAPONS.ID ) );
            add( new Category( WEAPONS.AMMO, "Ammo", WEAPONS.ID ) );
            add( new Category( WEAPONS.TRIPWIRES, "Tripwires", WEAPONS.ID ) );
            add( new Category( WEAPONS.EXPLOSIVES, "Explosives", WEAPONS.ID ) );
            add( new Category( WEAPONS.ATTACHMENTS, "Attachments", WEAPONS.ID ) );
            add( new Category( WEAPONS.FIREARMS, "Firearms", WEAPONS.ID ) );
            add( new Category( WEAPONS.MELEE, "Melee", WEAPONS.ID ) );
            add( new Category( WEAPONS.RANGED, "Ranged", WEAPONS.ID ) );

            // -- LEVEL 1 > ARMOR
            add( new Category( ARMOR.CLOTH, "Cloth", ARMOR.ID ) );
            add( new Category( ARMOR.SHIELDS, "Shields", ARMOR.ID ) );
            add( new Category( ARMOR.HIDE, "Hide", ARMOR.ID ) );
            add( new Category( ARMOR.GHILLIE, "Ghillie", ARMOR.ID ) );
            add( new Category( ARMOR.CHITIN, "Chitin", ARMOR.ID ) );
            add( new Category( ARMOR.FUR, "Fur", ARMOR.ID ) );
            add( new Category( ARMOR.METAL, "Metal", ARMOR.ID ) );
            add( new Category( ARMOR.SCUBA, "SCUBA", ARMOR.ID ) );
            add( new Category( ARMOR.RIOT, "Riot", ARMOR.ID ) );
            add( new Category( ARMOR.MISC, "Misc", ARMOR.ID ) );

            // -- LEVEL 1 > CONSUMABLES
            add( new Category( CONSUMABLES.DRUGS, "Drugs", CONSUMABLES.ID ) );
            add( new Category( CONSUMABLES.FOOD, "Food", CONSUMABLES.ID ) );

            // -- LEVEL 2 > STRUCTURES > WOOD
            add( new Category( STRUCTURES.WOOD.SIGNS, "Signs", STRUCTURES.WOOD.ID ) );
            add( new Category( STRUCTURES.WOOD.STORAGE, "Storage", STRUCTURES.WOOD.ID ) );

            // -- LEVEL 2 > STRUCTURES > FURNITURE
            add( new Category( STRUCTURES.FURNITURE.WOOD, "Wood", STRUCTURES.FURNITURE.ID ) );
            add( new Category( STRUCTURES.FURNITURE.STONE, "Stone", STRUCTURES.FURNITURE.ID ) );

            // -- LEVEL 2 > STRUCTURES > PIPES
            add( new Category( STRUCTURES.PIPES.STONE, "Stone", STRUCTURES.PIPES.ID ) );
            add( new Category( STRUCTURES.PIPES.METAL, "Metal", STRUCTURES.PIPES.ID ) );

            // -- LEVEL 2 > STRUCTURES > METAL
            add( new Category( STRUCTURES.METAL.SIGNS, "Signs", STRUCTURES.METAL.ID ) );
            add( new Category( STRUCTURES.METAL.STORAGE, "Storage", STRUCTURES.METAL.ID ) );

            // -- LEVEL 2 > STRUCTURES > ELECTRICAL
            add( new Category( STRUCTURES.ELECTRICAL.DEFENSES, "Defenses", STRUCTURES.ELECTRICAL.ID ) );
            add( new Category( STRUCTURES.ELECTRICAL.ELEVATORS, "Elevators", STRUCTURES.ELECTRICAL.ID ) );

            // -- LEVEL 2 > WEAPONS > PRIMITIVE
            add( new Category( WEAPONS.PRIMITIVE.MELEE, "Melee", WEAPONS.PRIMITIVE.ID ) );
            add( new Category( WEAPONS.PRIMITIVE.RANGED, "Ranged", WEAPONS.PRIMITIVE.ID ) );
        }
    };

    public static List<Category> getCategories() {
        return categories;
    }

    public static int getCount() {
        return categories.size();
    }
}
