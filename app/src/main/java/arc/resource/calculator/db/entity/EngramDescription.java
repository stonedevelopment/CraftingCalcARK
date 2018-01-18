package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"engramId", "descriptionId"},
    foreignKeys = {
        @ForeignKey(entity = DLCEngram.class,
            parentColumns = "id",
            childColumns = "engramId"),
        @ForeignKey(entity = Description.class,
            parentColumns = "id",
            childColumns = "descriptionId")
    },
    indices = {
        @Index(value = "engramId"),
        @Index(value = "descriptionId"),
        @Index(value = {"engramId", "descriptionId"}, unique = true)})

public class EngramDescription {

  @NonNull
  private final String engramId;

  @NonNull
  private final String descriptionId;

  public EngramDescription(@NonNull String engramId, @NonNull String descriptionId) {
    this.engramId = engramId;
    this.descriptionId = descriptionId;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public String getDescriptionId() {
    return descriptionId;
  }
}
