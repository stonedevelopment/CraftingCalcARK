package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import arc.resource.calculator.util.Util;

@Entity(indices = {
    @Index(value = {"contentFolder", "imageFolder", "imageFile"},
        unique = true)})

public class ImageLocation {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String contentFolder;

  @Nullable
  private final String imageFolder;

  @NonNull
  private final String imageFile;

  @Ignore
  public ImageLocation(String contentFolder, String imageFolder, String imageFile) {
    this(Util.generateUUID(), contentFolder, imageFolder, imageFile);
  }

  public ImageLocation(@NonNull String id, @NonNull String contentFolder,
      @Nullable String imageFolder, @NonNull String imageFile) {
    this.id = id;
    this.contentFolder = contentFolder;
    this.imageFolder = imageFolder;
    this.imageFile = imageFile;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getContentFolder() {
    return contentFolder;
  }

  @Nullable
  public String getImageFolder() {
    return imageFolder;
  }

  @NonNull
  public String getImageFile() {
    return imageFile;
  }
}
