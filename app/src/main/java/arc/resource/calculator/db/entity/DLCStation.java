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
    @ForeignKey(entity = BaseStation.class,
        parentColumns = "id",
        childColumns = "stationId")},
    indices = {
        @Index(value = "dlcId"),
        @Index(value = "stationId"),
        @Index(value = {"dlcId", "stationId"}, unique = true)})

public class DLCStation {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String dlcId;

  @NonNull
  private final String stationId;

  @Ignore
  public DLCStation(String dlcId, String stationId) {
    this(Util.generateUUID(), dlcId, stationId);
  }

  public DLCStation(@NonNull String id, @NonNull String dlcId, @NonNull String stationId) {
    this.id = id;
    this.dlcId = dlcId;
    this.stationId = stationId;
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
  public String getStationId() {
    return stationId;
  }

}
