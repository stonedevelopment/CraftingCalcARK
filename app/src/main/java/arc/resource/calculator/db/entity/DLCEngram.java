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
            childColumns = "engramId"),
        @ForeignKey(entity = BaseDLC.class,
            parentColumns = "id",
            childColumns = "dlcId")},
    indices = {
        @Index(value = {"dlcId"}),
        @Index(value = {"parentId"}),
        @Index(value = {"engramId"},
            unique = true)})

public class DLCEngram {

  @NonNull
  @PrimaryKey
  private final String engramId;

  @NonNull
  private final String dlcId;

  @NonNull
  private final String parentId;

  public DLCEngram(@NonNull String engramId, @NonNull String parentId, @NonNull String dlcId) {
    this.engramId = engramId;
    this.parentId = parentId;
    this.dlcId = dlcId;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }

  @NonNull
  public String getParentId() {
    return parentId;
  }
}
