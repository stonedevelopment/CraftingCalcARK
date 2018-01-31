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
        @ForeignKey(entity = BaseMod.class,
            parentColumns = "id",
            childColumns = "modId")},
    indices = {
        @Index(value = {"modId"}),
        @Index(value = {"stationId"},
            unique = true)})

public class ModStation {

  @NonNull
  @PrimaryKey
  private final String stationId;

  @NonNull
  private final String modId;

  public ModStation(@NonNull String stationId, @NonNull String modId) {
    this.stationId = stationId;
    this.modId = modId;
  }

  @NonNull
  public String getStationId() {
    return stationId;
  }

  @NonNull
  public String getModId() {
    return modId;
  }
}