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
        @ForeignKey(entity = BaseMod.class,
            parentColumns = "id",
            childColumns = "modId")},
    indices = {
        @Index(value = {"modId"}),
        @Index(value = {"folderId"},
            unique = true)})

public class ModFolder {

  @NonNull
  @PrimaryKey
  private final String folderId;

  @NonNull
  private final String modId;

  public ModFolder(@NonNull String folderId, @NonNull String modId) {
    this.folderId = folderId;
    this.modId = modId;
  }

  @NonNull
  public String getFolderId() {
    return folderId;
  }

  @NonNull
  public String getModId() {
    return modId;
  }
}
