package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(indices = {
    @Index(value = {"imageFolder", "imageFile"},
        unique = true)})

public class ImageLocation {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String imageFolder;

  @NonNull
  private final String imageFile;

  @Ignore
  public ImageLocation(String imageFolder, String imageFile) {
    this(Util.generateUUID(), imageFolder, imageFile);
  }

  public ImageLocation(@NonNull String id, @NonNull String imageFolder, @NonNull String imageFile) {
    this.id = id;
    this.imageFolder = imageFolder;
    this.imageFile = imageFile;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getImageFolder() {
    return imageFolder;
  }

  @NonNull
  public String getImageFile() {
    return imageFile;
  }
}
