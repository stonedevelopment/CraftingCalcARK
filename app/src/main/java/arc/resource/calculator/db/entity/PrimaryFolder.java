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
            childColumns = "folderId")},
    indices = {
        @Index(value = "folderId"),
        @Index(value = "parentId"),
        @Index(value = {"folderId", "parentId"},
            unique = true)})

public class PrimaryFolder {

  @NonNull
  @PrimaryKey
  private final String folderId;

  @NonNull
  private final String parentId;

  public PrimaryFolder(@NonNull String folderId, @NonNull String parentId) {
    this.folderId = folderId;
    this.parentId = parentId;
  }

  @NonNull
  public String getFolderId() {
    return folderId;
  }

  @NonNull
  public String getParentId() {
    return parentId;
  }
}
