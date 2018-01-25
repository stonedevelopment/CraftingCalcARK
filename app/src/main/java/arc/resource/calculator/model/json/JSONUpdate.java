package arc.resource.calculator.model.json;

import java.util.List;

public class JSONUpdate {

  private String version;
  private Boolean updatePrimary;
  private List<String> dlcFiles;
  private List<String> modFiles;

  public JSONUpdate() {

  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Boolean updatePrimary() {
    return getUpdatePrimary();
  }

  public Boolean getUpdatePrimary() {
    return updatePrimary;
  }

  public void setUpdatePrimary(Boolean updatePrimary) {
    this.updatePrimary = updatePrimary;
  }

  public List<String> getDlcFiles() {
    return dlcFiles;
  }

  public void setDlcFiles(List<String> dlcFiles) {
    this.dlcFiles = dlcFiles;
  }

  public List<String> getModFiles() {
    return modFiles;
  }

  public void setModFiles(List<String> modFiles) {
    this.modFiles = modFiles;
  }
}
