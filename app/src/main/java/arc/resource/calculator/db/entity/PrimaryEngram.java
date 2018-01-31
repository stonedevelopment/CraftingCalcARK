package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
    foreignKeys = {
        @ForeignKey(entity = BaseEngram.class,
            parentColumns = "id",
            childColumns = "engramId")},
    indices = {
        @Index(value = "engramId"),
        @Index(value = "parentId"),
        @Index(value = {"engramId", "parentId"},
            unique = true)})

public class PrimaryEngram {

  @NonNull
  @PrimaryKey
  private final String engramId;

  @NonNull
  private final String parentId;

  public PrimaryEngram(@NonNull String engramId, @NonNull String parentId) {
    this.engramId = engramId;
    this.parentId = parentId;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public String getParentId() {
    return parentId;
  }
}
