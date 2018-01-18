package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"folderId", "engramId"},
    foreignKeys = {
        @ForeignKey(entity = DLCFolder.class,
            parentColumns = "id",
            childColumns = "folderId"),
        @ForeignKey(entity = DLCEngram.class,
            parentColumns = "id",
            childColumns = "engramId")
    },
    indices = {
        @Index(value = "folderId"),
        @Index(value = "engramId"),
        @Index(value = {"folderId", "engramId"}, unique = true)})

public class FolderEngram {

  @NonNull
  private final String folderId;

  @NonNull
  private final String engramId;

  public FolderEngram(@NonNull String folderId, @NonNull String engramId) {
    this.folderId = folderId;
    this.engramId = engramId;
  }

  @NonNull
  public String getFolderId() {
    return folderId;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }
}
