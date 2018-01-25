package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "engram",
    indices = {
        @Index(value = {"descriptionId"}),
        @Index(value = {"imageLocationId"}),
        @Index(value = {"nameId"}),
        @Index(value = {"parentId"}),
        @Index(value = {"requiredLevel"}),
        @Index(value = {"nameId", "parentId"},
            unique = true)})

public class BaseEngram {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String descriptionId;

  @NonNull
  private final String imageLocationId;

  @NonNull
  private final String nameId;

  @NonNull
  private final String parentId;

  @NonNull
  private final Integer requiredLevel;

  @NonNull
  private final Integer yield;

  @Ignore
  public BaseEngram(@NonNull String descriptionId, String imageLocationId, String nameId,
      @NonNull String parentId, Integer requiredLevel, Integer yield) {
    this(Util.generateUUID(), descriptionId, imageLocationId, nameId, parentId, requiredLevel,
        yield);
  }

  public BaseEngram(@NonNull String id, @NonNull String descriptionId,
      @NonNull String imageLocationId, @NonNull String nameId,
      @NonNull String parentId, @NonNull Integer requiredLevel, @NonNull Integer yield) {
    this.id = id;
    this.descriptionId = descriptionId;
    this.imageLocationId = imageLocationId;
    this.nameId = nameId;
    this.parentId = parentId;
    this.requiredLevel = requiredLevel;
    this.yield = yield;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getDescriptionId() {
    return descriptionId;
  }

  @NonNull
  public String getImageLocationId() {
    return imageLocationId;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }

  @NonNull
  public String getParentId() {
    return parentId;
  }

  @NonNull
  public Integer getRequiredLevel() {
    return requiredLevel;
  }

  @NonNull
  public Integer getYield() {
    return yield;
  }
}