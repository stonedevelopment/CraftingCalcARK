package arc.resource.calculator.model.json;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder( { "id", "name", "stations", "resources" } )
public class JSONDataObject {
    private long id;
    private String name;
    private List<Station> stations;
    private List<Resource> resources;

    public JSONDataObject( long id, String name ) {
        this.id = id;
        this.name = name;
        this.stations = new ArrayList<>();
        this.resources = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations( List<Station> stations ) {
        this.stations = stations;
    }

    public void addStation( Station station ) {
        this.stations.add( station );
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources( List<Resource> resources ) {
        this.resources = resources;
    }

    public void addResource( Resource resource ) {
        this.resources.add( resource );
    }

    @JsonPropertyOrder( { "name", "image_folder", "image_file", "categories" } )
    public static class Station {
        private String name;
        private String image_folder;
        private String image_file;
        private List<Category> categories;

        public Station( String name, String image_folder, String image_file ) {
            this.name = name;
            this.image_folder = image_folder;
            this.image_file = image_file;
            this.categories = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }

        public String getImage_folder() {
            return image_folder;
        }

        public void setImage_folder( String image_folder ) {
            this.image_folder = image_folder;
        }

        public String getImage_file() {
            return image_file;
        }

        public void setImage_file( String image_file ) {
            this.image_file = image_file;
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories( List<Category> categories ) {
            this.categories = categories;
        }

        public void addCategory( Category category ) {
            this.categories.add( category );
        }
    }

    @JsonPropertyOrder( { "name", "engrams", "categories" } )
    public static class Category {
        private String name;
        private List<Engram> engrams;
        private List<Category> categories;

        public Category( String name ) {
            this.name = name;
            this.engrams = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }

        public List<Engram> getEngrams() {
            return engrams;
        }

        public void setEngrams( List<Engram> engrams ) {
            this.engrams = engrams;
        }

        public void addEngram( Engram engram ) {
            this.engrams.add( engram );
        }

        public List<Category> getCategories() {
            return categories;
        }

        public void setCategories( List<Category> categories ) {
            this.categories = categories;
        }

        public void addCategory( Category category ) {
            this.categories.add( category );
        }

    }

    @JsonPropertyOrder( { "name", "description", "image_folder", "image_file", "yield", "level", "composition" } )
    public static class Engram {
        private String name;
        private String description;
        private String image_folder;
        private String image_file;
        private int yield;
        private int level;
        private List<CompositionElement> composition;

        public Engram( String name, String description, String image_folder, String image_file, int yield, int level ) {
            this.name = name;
            this.description = description;
            this.yield = yield;
            this.level = level;
            this.image_folder = image_folder;
            this.image_file = image_file;
            this.composition = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription( String description ) {
            this.description = description;
        }

        public String getImage_folder() {
            return image_folder;
        }

        public void setImage_folder( String image_folder ) {
            this.image_folder = image_folder;
        }

        public String getImage_file() {
            return image_file;
        }

        public void setImage_file( String image_file ) {
            this.image_file = image_file;
        }

        public int getYield() {
            return yield;
        }

        public void setYield( int yield ) {
            this.yield = yield;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel( int level ) {
            this.level = level;
        }

        public List<CompositionElement> getComposition() {
            return composition;
        }

        public void setComposition( List<CompositionElement> composition ) {
            this.composition = composition;
        }

        public void addCompositionElement( CompositionElement compositionElement ) {
            this.composition.add( compositionElement );
        }
    }

    @JsonPropertyOrder( { "name", "quantity" } )
    public static class CompositionElement {
        private String name;
        private int quantity;

        public CompositionElement( String name, int quantity ) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    @JsonPropertyOrder( { "name", "image_folder", "image_file" } )
    public static class Resource {
        private String name;
        private String image_folder;
        private String image_file;

        public Resource( String name, String image_folder, String image_file ) {
            this.name = name;
            this.image_folder = image_folder;
            this.image_file = image_file;
        }

        public String getName() {
            return name;
        }

        public void setName( String name ) {
            this.name = name;
        }

        public String getImage_folder() {
            return image_folder;
        }

        public void setImage_folder( String image_folder ) {
            this.image_folder = image_folder;
        }

        public String getImage_file() {
            return image_file;
        }

        public void setImage_file( String image_file ) {
            this.image_file = image_file;
        }
    }
}
