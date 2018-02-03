package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(
    foreignKeys = {
        @ForeignKey(entity = BaseMod.class,
            parentColumns = "id",
            childColumns = "modId")},
    indices = {
        @Index(value = {"modId"})},
    inheritSuperIndices = true)

public class ModEngram extends PrimaryEngram {

  @NonNull
  private final String modId;

  public ModEngram(String engramId, String parentId, Integer yield, @NonNull String modId) {
    super(engramId, parentId, yield);
    this.modId = modId;
  }

  @NonNull
  public String getModId() {
    return modId;
  }
}
