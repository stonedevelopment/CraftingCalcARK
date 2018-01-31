package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
    foreignKeys = {
        @ForeignKey(entity = BaseFolder.class,
            parentColumns = "id",
            childColumns = "folderId"),
        @ForeignKey(entity = BaseDLC.class,
            parentColumns = "id",
            childColumns = "dlcId")},
    indices = {
        @Index(value = "dlcId"),
        @Index(value = "folderId"),
        @Index(value = "parentId"),
        @Index(value = {"dlcId", "folderId", "parentId"},
            unique = true)})

public class DLCFolder {

  @NonNull
  @PrimaryKey
  private final String folderId;

  @NonNull
  private final String dlcId;

  @NonNull
  private final String parentId;

  public DLCFolder(@NonNull String folderId, @NonNull String parentId, @NonNull String dlcId) {
    this.folderId = folderId;
    this.parentId = parentId;
    this.dlcId = dlcId;
  }

  @NonNull
  public String getFolderId() {
    return folderId;
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
