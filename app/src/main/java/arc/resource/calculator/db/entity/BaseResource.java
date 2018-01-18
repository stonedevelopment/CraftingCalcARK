package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "resource",
    indices = {
        @Index(value = {"imageFolder", "imageFile"},
            unique = true)})

public class BaseResource {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String imageFolder;

  @NonNull
  private final String imageFile;

  @Ignore
  public BaseResource(String imageFolder, String imageFile) {
    this(Util.generateUUID(), imageFolder, imageFile);
  }

  public BaseResource(@NonNull String id, @NonNull String imageFolder, @NonNull String imageFile) {
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
