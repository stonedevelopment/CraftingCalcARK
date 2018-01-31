package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
    foreignKeys = {
        @ForeignKey(entity = BaseStation.class,
            parentColumns = "id",
            childColumns = "stationId"),
        @ForeignKey(entity = BaseDLC.class,
            parentColumns = "id",
            childColumns = "dlcId")},
    indices = {
        @Index(value = {"dlcId"}),
        @Index(value = {"stationId"},
            unique = true)})

public class DLCStation {

  @NonNull
  @PrimaryKey
  private final String stationId;

  @NonNull
  private final String dlcId;

  public DLCStation(@NonNull String stationId, @NonNull String dlcId) {
    this.stationId = stationId;
    this.dlcId = dlcId;
  }

  @NonNull
  public String getStationId() {
    return stationId;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }
}