package arc.resource.calculator.model.json;

import java.util.List;

public class JSONDLC extends JSONPrimary {

  private String name;
  private String contentFolder;
  private String imageFile;
  private Override override;

  public JSONDLC() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setContentFolder(String contentFolder) {
    this.contentFolder = contentFolder;
  }

  public String getContentFolder() {
    return contentFolder;
  }

  public String getImageFile() {
    return imageFile;
  }

  public void setImageFile(String imageFile) {
    this.imageFile = imageFile;
  }

  public Override getOverride() {
    return override;
  }

  public void setOverride(Override override) {
    this.override = override;
  }

  public class Override {

    private List<Station> stations;

    public Override() {

    }

    public List<Station> getStations() {
      return stations;
    }

    public void setStations(List<Station> stations) {
      this.stations = stations;
    }
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
