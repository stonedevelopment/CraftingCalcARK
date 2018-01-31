package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "quality",
    foreignKeys = {
        @ForeignKey(entity = Name.class,
            parentColumns = "id",
            childColumns = "nameId")},
    indices = {
        @Index(value = "nameId")})

public class BaseQuality {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String nameId;

  @NonNull
  private final String color;

  @Ignore
  public BaseQuality(String nameId, String color) {
    this(Util.generateUUID(), nameId, color);
  }

  public BaseQuality(@NonNull String id, @NonNull String nameId, @NonNull String color) {
    this.id = id;
    this.nameId = nameId;
    this.color = color;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }

  @NonNull
  public String getColor() {
    return color;
  }
}