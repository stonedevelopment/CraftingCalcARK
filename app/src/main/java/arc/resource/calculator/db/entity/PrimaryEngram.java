package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(
    primaryKeys = {"engramId", "parentId"},
    foreignKeys = {
        @ForeignKey(entity = BaseEngram.class,
            parentColumns = "id",
            childColumns = "engramId")},
    indices = {
        @Index(value = "engramId"),
        @Index(value = "parentId"),
        @Index(value = {"engramId", "parentId", "yield"},
            unique = true)})

public class PrimaryEngram {

  @NonNull
  private final String engramId;

  @NonNull
  private final String parentId;

  @NonNull
  private final Integer yield;

  public PrimaryEngram(@NonNull String engramId, @NonNull String parentId, @NonNull Integer yield) {
    this.engramId = engramId;
    this.parentId = parentId;
    this.yield = yield;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public String getParentId() {
    return parentId;
  }

  @NonNull
  public Integer getYield() {
    return yield;
  }
}