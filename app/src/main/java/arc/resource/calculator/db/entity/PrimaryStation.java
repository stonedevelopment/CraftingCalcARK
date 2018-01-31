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
            childColumns = "stationId")},
    indices = {
        @Index(value = "stationId",
            unique = true)})

public class PrimaryStation {

  @NonNull
  @PrimaryKey
  private final String stationId;

  public PrimaryStation(@NonNull String stationId) {
    this.stationId = stationId;
  }

  @NonNull
  public String getStationId() {
    return stationId;
  }
}
