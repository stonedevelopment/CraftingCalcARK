package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"stationId", "nameId"},
    foreignKeys = {
        @ForeignKey(entity = DLCStation.class,
            parentColumns = "id",
            childColumns = "stationId"),
        @ForeignKey(entity = Name.class,
            parentColumns = "id",
            childColumns = "nameId")
    },
    indices = {
        @Index(value = "stationId"),
        @Index(value = "nameId"),
        @Index(value = {"stationId", "nameId"}, unique = true)})

public class StationName {

  @NonNull
  private final String stationId;

  @NonNull
  private final String nameId;

  public StationName(@NonNull String stationId, @NonNull String nameId) {
    this.stationId = stationId;
    this.nameId = nameId;
  }

  @NonNull
  public String getStationId() {
    return stationId;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }
}
