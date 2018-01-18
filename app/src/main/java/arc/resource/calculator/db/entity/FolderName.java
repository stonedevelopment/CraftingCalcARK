package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"folderId", "nameId"},
    foreignKeys = {
        @ForeignKey(entity = DLCFolder.class,
            parentColumns = "id",
            childColumns = "folderId"),
        @ForeignKey(entity = Name.class,
            parentColumns = "id",
            childColumns = "nameId")
    },
    indices = {
        @Index(value = "folderId"),
        @Index(value = "nameId"),
        @Index(value = {"folderId", "nameId"}, unique = true)})

public class FolderName {

  @NonNull
  private final String folderId;

  @NonNull
  private final String nameId;

  public FolderName(@NonNull String folderId, @NonNull String nameId) {
    this.folderId = folderId;
    this.nameId = nameId;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }

  @NonNull
  public String getFolderId() {
    return folderId;
  }
}
