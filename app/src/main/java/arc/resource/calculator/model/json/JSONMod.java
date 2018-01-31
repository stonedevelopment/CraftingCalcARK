package arc.resource.calculator.model.json;

import java.util.List;

public class JSONMod extends JSONPrimary {

  private String name;
  private String contentFolder;
  private String imageFile;
  private Override override;

  public JSONMod() {
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
}
