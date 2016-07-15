package com.gmail.jaredstone1982.craftingcalcark.model.initializers;

import com.gmail.jaredstone1982.craftingcalcark.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Proposed idea for having a hierarchy of categories to which the user can filter results
 * <p/>
 * Will be along the same lines as in-game.
 */
public class CategoryInitializer {

    public static class MISC {
        public static final long ID = 1;
    }

    public static class STRUCTURES {
        public static final long ID = 2;
        public static final long THATCH = 201;

        private static final class WOOD {
            public static final long ID = 202;
            public static final long SIGNS = 2021;
            public static final long STORAGE = 2022;
        }

        public static final long STONE = 203;
        public static final long COOKING = 204;
        public static final long BEDS = 205;
        public static final long CRAFTING = 206;
        public static final long MISC = 207;

        private static final class FURNITURE {
            public static final long ID = 208;
            public static final long WOOD = 2081;
            public static final long STONE = 2082;
        }

        public static final long FARMING = 209;

        private static final class PIPES {
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
        public static final long ID = 3;

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
        public static final long ID = 4;
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
        public static final long ID = 5;
    }

    public static class NAVIGATION {
        public static final long ID = 6;
    }

    public static class COMMUNICATION {
        public static final long ID = 7;
    }

    public static class OFFHAND {
        public static final long ID = 8;
    }

    public static class COMPOSITES {
        public static final long ID = 9;
    }

    public static class CONSUMABLES {
        public static final long ID = 10;
        public static final long DRUGS = 1001;
        public static final long FOOD = 1002;
    }

    public static class DYES {
        public static final long ID = 11;
    }

    public static class KIBBLE {
        public static final long ID = 12;
    }

    public static class REFINED {
        public static final long ID = 13;
    }

    private static List<Category> categories = new ArrayList<Category>() {
        {
            // -- LEVEL 0
            add(new Category(MISC.ID, "Misc"));
            add(new Category(STRUCTURES.ID, "Structures"));
            add(new Category(WEAPONS.ID, "Weapons"));
            add(new Category(ARMOR.ID, "Armor"));
            add(new Category(SADDLES.ID, "Saddles"));
            add(new Category(NAVIGATION.ID, "Navigation"));
            add(new Category(COMMUNICATION.ID, "Communication"));
            add(new Category(OFFHAND.ID, "Off-Hand"));
            add(new Category(COMPOSITES.ID, "Composite"));
            add(new Category(CONSUMABLES.ID, "Consumable"));
            add(new Category(DYES.ID, "Dyes"));
            add(new Category(KIBBLE.ID, "Kibble"));
            add(new Category(REFINED.ID, "Refined"));

            // -- LEVEL 1 > STRUCTURES
            add(new Category(1, STRUCTURES.THATCH, "Thatch", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.WOOD.ID, "Wood", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.STONE, "Stone", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.COOKING, "Cooking", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.BEDS, "Beds", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.CRAFTING, "Crafting", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.MISC, "Misc", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.FURNITURE.ID, "Furniture", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.FARMING, "Farming", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.PIPES.ID, "Pipes", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.FRIDGES, "Fridges", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.METAL.ID, "Metal", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.DEFENSES, "Defenses", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.GREENHOUSE, "Greenhouse", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.ELECTRICAL.ID, "Electrical", STRUCTURES.ID));
            add(new Category(1, STRUCTURES.ELECTRIC, "Electric", STRUCTURES.ID));

            // -- LEVEL 1 > WEAPONS
            add(new Category(1, WEAPONS.PRIMITIVE.ID, "Primitive", WEAPONS.ID));
            add(new Category(1, WEAPONS.AMMO, "Ammo", WEAPONS.ID));
            add(new Category(1, WEAPONS.TRIPWIRES, "Tripwires", WEAPONS.ID));
            add(new Category(1, WEAPONS.EXPLOSIVES, "Explosives", WEAPONS.ID));
            add(new Category(1, WEAPONS.ATTACHMENTS, "Attachments", WEAPONS.ID));
            add(new Category(1, WEAPONS.FIREARMS, "Firearms", WEAPONS.ID));
            add(new Category(1, WEAPONS.MELEE, "Melee", WEAPONS.ID));
            add(new Category(1, WEAPONS.RANGED, "Ranged", WEAPONS.ID));

            // -- LEVEL 1 > ARMOR
            add(new Category(1, ARMOR.CLOTH, "Cloth", ARMOR.ID));
            add(new Category(1, ARMOR.SHIELDS, "Shields", ARMOR.ID));
            add(new Category(1, ARMOR.HIDE, "Hide", ARMOR.ID));
            add(new Category(1, ARMOR.GHILLIE, "Ghillie", ARMOR.ID));
            add(new Category(1, ARMOR.CHITIN, "Chitin", ARMOR.ID));
            add(new Category(1, ARMOR.FUR, "Fur", ARMOR.ID));
            add(new Category(1, ARMOR.METAL, "Metal", ARMOR.ID));
            add(new Category(1, ARMOR.SCUBA, "Scuba", ARMOR.ID));
            add(new Category(1, ARMOR.RIOT, "Riot", ARMOR.ID));
            add(new Category(1, ARMOR.MISC, "Misc", ARMOR.ID));

            // -- LEVEL 1 > CONSUMABLES
            add(new Category(1, CONSUMABLES.DRUGS, "Drugs", CONSUMABLES.ID));
            add(new Category(1, CONSUMABLES.FOOD, "Food", CONSUMABLES.ID));

            // -- LEVEL 2 > STRUCTURES > WOOD
            add(new Category(2, STRUCTURES.WOOD.SIGNS, "Signs", STRUCTURES.WOOD.ID));
            add(new Category(2, STRUCTURES.WOOD.STORAGE, "Storage", STRUCTURES.WOOD.ID));

            // -- LEVEL 2 > STRUCTURES > FURNITURE
            add(new Category(2, STRUCTURES.FURNITURE.WOOD, "Wood", STRUCTURES.FURNITURE.ID));
            add(new Category(2, STRUCTURES.FURNITURE.STONE, "Stone", STRUCTURES.FURNITURE.ID));

            // -- LEVEL 2 > STRUCTURES > PIPES
            add(new Category(2, STRUCTURES.PIPES.STONE, "Stone", STRUCTURES.PIPES.ID));
            add(new Category(2, STRUCTURES.PIPES.METAL, "Metal", STRUCTURES.PIPES.ID));

            // -- LEVEL 2 > STRUCTURES > METAL
            add(new Category(2, STRUCTURES.METAL.SIGNS, "Signs", STRUCTURES.METAL.ID));
            add(new Category(2, STRUCTURES.METAL.STORAGE, "Storage", STRUCTURES.METAL.ID));

            // -- LEVEL 2 > STRUCTURES > ELECTRICAL
            add(new Category(2, STRUCTURES.ELECTRICAL.DEFENSES, "Defenses", STRUCTURES.ELECTRICAL.ID));
            add(new Category(2, STRUCTURES.ELECTRICAL.ELEVATORS, "Elevators", STRUCTURES.ELECTRICAL.ID));

            // -- LEVEL 2 > WEAPONS > PRIMITIVE
            add(new Category(2, WEAPONS.PRIMITIVE.MELEE, "Melee", WEAPONS.PRIMITIVE.ID));
            add(new Category(2, WEAPONS.PRIMITIVE.RANGED, "Ranged", WEAPONS.PRIMITIVE.ID));
        }
    };

    public static List<Category> getCategories() {
        return categories;
    }
}
