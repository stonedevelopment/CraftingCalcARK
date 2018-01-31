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
        @ForeignKey(entity = BaseMod.class,
            parentColumns = "id",
            childColumns = "modId")},
    indices = {
        @Index(value = {"modId"}),
        @Index(value = {"engramId"},
            unique = true)})

public class ModEngram {

  @NonNull
  @PrimaryKey
  private final String engramId;

  @NonNull
  private final String modId;

  public ModEngram(@NonNull String engramId, @NonNull String modId) {
    this.engramId = engramId;
    this.modId = modId;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public String getModId() {
    return modId;
  }
}
