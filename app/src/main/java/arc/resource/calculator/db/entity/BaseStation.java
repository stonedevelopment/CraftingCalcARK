package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "station",
    indices = {
        @Index(value = {"imageLocationId", "nameId"},
            unique = true)})

public class BaseStation {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String imageLocationId;

  @NonNull
  private final String nameId;

  @Ignore
  public BaseStation(String imageLocationId, String nameId) {
    this(Util.generateUUID(), imageLocationId, nameId);
  }

  public BaseStation(@NonNull String id, @NonNull String imageLocationId, @NonNull String nameId) {
    this.id = id;
    this.imageLocationId = imageLocationId;
    this.nameId = nameId;
  }

  @NonNull
  public String getId() {
    return id;
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
