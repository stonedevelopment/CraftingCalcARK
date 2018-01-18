package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"dlcId", "nameId"},
    foreignKeys = {
        @ForeignKey(entity = BaseDLC.class,
            parentColumns = "id",
            childColumns = "dlcId"),
        @ForeignKey(entity = Name.class,
            parentColumns = "id",
            childColumns = "nameId")},
    indices = {
        @Index(value = "dlcId"),
        @Index(value = "nameId"),
        @Index(value = {"dlcId", "nameId"}, unique = true)})

public class DLCName {

  @NonNull
  private final String dlcId;

  @NonNull
  private final String nameId;

  public DLCName(@NonNull String dlcId, @NonNull String nameId) {
    this.dlcId = dlcId;
    this.nameId = nameId;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }
}
