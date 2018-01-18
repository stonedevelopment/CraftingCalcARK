package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(foreignKeys = {
    @ForeignKey(entity = BaseDLC.class,
        parentColumns = "id",
        childColumns = "dlcId"),
    @ForeignKey(entity = BaseFolder.class,
        parentColumns = "id",
        childColumns = "folderId")},
    indices = {
        @Index(value = "dlcId"),
        @Index(value = "folderId"),
        @Index(value = {"dlcId", "folderId"}, unique = true)})

public class DLCFolder {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String dlcId;

  @NonNull
  private final String folderId;

  @Ignore
  public DLCFolder(String dlcId, String folderId) {
    this(Util.generateUUID(), dlcId, folderId);
  }

  public DLCFolder(@NonNull String id, @NonNull String dlcId, @NonNull String folderId) {
    this.id = id;
    this.dlcId = dlcId;
    this.folderId = folderId;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }

  @NonNull
  public String getFolderId() {
    return folderId;
  }
}
