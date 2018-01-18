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
    @ForeignKey(entity = BaseEngram.class,
        parentColumns = "id",
        childColumns = "engramId")},
    indices = {
        @Index(value = "dlcId"),
        @Index(value = "engramId"),
        @Index(value = {"dlcId", "engramId"}, unique = true)
    })

public class DLCEngram {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String dlcId;

  @NonNull
  private final String engramId;

  @Ignore
  public DLCEngram(String dlcId, String engramId) {
    this(Util.generateUUID(), dlcId, engramId);
  }

  public DLCEngram(@NonNull String id, @NonNull String dlcId, @NonNull String engramId) {
    this.id = id;
    this.dlcId = dlcId;
    this.engramId = engramId;
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
  public String getEngramId() {
    return engramId;
  }
}
