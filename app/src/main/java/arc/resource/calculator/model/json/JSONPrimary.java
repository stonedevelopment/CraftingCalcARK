package arc.resource.calculator.model.json;

import java.util.List;

public class JSONPrimary {

  private List<Station> stations;
  private List<Resource> resources;
  private List<Quality> qualities;

  public JSONPrimary() {

  }

  public List<Station> getStations() {
    return stations;
  }

  public void setStations(List<Station> stations) {
    this.stations = stations;
  }

  public void addStation(Station station) {
    this.stations.add(station);
  }

  public List<Resource> getResources() {
    return resources;
  }

  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }

  public void addResource(Resource resource) {
    this.resources.add(resource);
  }

  public List<Quality> getQualities() {
    return qualities;
  }

  public void setQualities(
      List<Quality> qualities) {
    this.qualities = qualities;
  }

  public static class Quality {

    private String name;
    private String color;

    public Quality() {

    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }
  }

  public static class Station {

    private String name;
    private String imageFolder;
    private String imageFile;
    private List<Engram> engrams;
    private List<Category> categories;

    public Station() {

    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getImageFolder() {
      return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
      this.imageFolder = imageFolder;
    }

    public String getImageFile() {
      return imageFile;
    }

    public void setImageFile(String imageFile) {
      this.imageFile = imageFile;
    }

    public List<Category> getCategories() {
      return categories;
    }

    public void setCategories(List<Category> categories) {
      this.categories = categories;
    }

    public void addCategory(Category category) {
      this.categories.add(category);
    }

    public List<Engram> getEngrams() {
      return engrams;
    }

    public void setEngrams(List<Engram> engrams) {
      this.engrams = engrams;
    }

    public void addEngram(Engram engram) {
      this.engrams.add(engram);
    }
  }

  public static class Category {

    private String name;
    private List<Engram> engrams;
    private List<Category> categories;

    public Category() {

    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<Engram> getEngrams() {
      return engrams;
    }

    public void setEngrams(List<Engram> engrams) {
      this.engrams = engrams;
    }

    public void addEngram(Engram engram) {
      this.engrams.add(engram);
    }

    public List<Category> getCategories() {
      return categories;
    }

    public void setCategories(List<Category> categories) {
      this.categories = categories;
    }

    public void addCategory(Category category) {
      this.categories.add(category);
    }

  }

  public static class Engram {

    private String name;
    private String description;
    private String imageFolder;
    private String imageFile;
    private int yield;
    private int level;
    private List<Composite> composition;

    public Engram() {

    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getImageFolder() {
      return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
      this.imageFolder = imageFolder;
    }

    public String getImageFile() {
      return imageFile;
    }

    public void setImageFile(String imageFile) {
      this.imageFile = imageFile;
    }

    public int getYield() {
      return yield;
    }

    public void setYield(int yield) {
      this.yield = yield;
    }

    public int getLevel() {
      return level;
    }

    public void setLevel(int level) {
      this.level = level;
    }

    public List<Composite> getComposition() {
      return composition;
    }

    public void setComposition(List<Composite> composition) {
      this.composition = composition;
    }

    public void addCompositionElement(Composite composite) {
      this.composition.add(composite);
    }
  }

  public static class Composite {

    private String name;
    private int quantity;

    public Composite() {

    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getQuantity() {
      return quantity;
    }

    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }
  }

  public static class Resource {

    private String name;
    private String imageFolder;
    private String imageFile;

    public Resource() {

    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getImageFolder() {
      return imageFolder;
    }

    public void setImageFolder(String imageFolder) {
      this.imageFolder = imageFolder;
    }

    public String getImageFile() {
      return imageFile;
    }

    public void setImageFile(String imageFile) {
      this.imageFile = imageFile;
    }
  }

  public static class Override {

    private List<Station> stations;

    public Override() {

    }

    public List<Station> getStations() {
      return stations;
    }

    public void setStations(List<Station> stations) {
      this.stations = stations;
    }

    public static class Station {

      private String name;
      private List<Engram> engrams;
      private List<Category> categories;

      public Station() {

      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }

      public List<Category> getCategories() {
        return categories;
      }

      public void setCategories(List<Category> categories) {
        this.categories = categories;
      }

      public void addCategory(Category category) {
        this.categories.add(category);
      }

      public List<Engram> getEngrams() {
        return engrams;
      }

      public void setEngrams(List<Engram> engrams) {
        this.engrams = engrams;
      }

      public void addEngram(Engram engram) {
        this.engrams.add(engram);
      }
    }
  }
}
