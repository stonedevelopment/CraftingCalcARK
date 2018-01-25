package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "dlc",
    indices = {
        @Index(value = {"contentFolder", "imageLocationId", "nameId"},
            unique = true)})

public class BaseDLC {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String contentFolder;

  @NonNull
  private final String imageLocationId;

  @NonNull
  private final String nameId;

  @Ignore
  public BaseDLC(String contentFolder, String imageLocationId, String nameId) {
    this(Util.generateUUID(), contentFolder, imageLocationId, nameId);
  }

  public BaseDLC(@NonNull String id, @NonNull String contentFolder, @NonNull String imageLocationId, @NonNull String nameId) {
    this.id = id;
    this.contentFolder = contentFolder;
    this.imageLocationId = imageLocationId;
    this.nameId = nameId;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getContentFolder() {
    return contentFolder;
  }

  @NonNull
  public String getImageLocationId() {
    return imageLocationId;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }
}
