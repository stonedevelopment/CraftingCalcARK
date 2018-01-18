package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"engramId", "nameId"},
    foreignKeys = {
        @ForeignKey(entity = DLCEngram.class,
            parentColumns = "id",
            childColumns = "engramId"),
        @ForeignKey(entity = Name.class,
            parentColumns = "id",
            childColumns = "nameId")
    },
    indices = {
        @Index(value = "engramId"),
        @Index(value = "nameId"),
        @Index(value = {"engramId", "nameId"}, unique = true)})

public class EngramName {

  @NonNull
  private final String engramId;

  @NonNull
  private final String nameId;

  public EngramName(@NonNull String engramId, @NonNull String nameId) {
    this.engramId = engramId;
    this.nameId = nameId;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }
}
