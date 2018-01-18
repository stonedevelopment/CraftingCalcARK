package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(foreignKeys = {
    @ForeignKey(entity = DLCStation.class,
        parentColumns = "id",
        childColumns = "stationId"),
    @ForeignKey(entity = DLCEngram.class,
        parentColumns = "id",
        childColumns = "engramId")},
    indices = {
        @Index(value = "stationId"),
        @Index(value = "engramId"),
        @Index(value = {"stationId", "engramId", "yield"}, unique = true)
    })

public class StationEngram {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String stationId;

  @NonNull
  private final String engramId;

  @NonNull
  private final Integer yield;

  @Ignore
  public StationEngram(String stationId, String engramId, Integer yield) {
    this(Util.generateUUID(), stationId, engramId, yield);
  }

  public StationEngram(@NonNull String id, @NonNull String stationId, @NonNull String engramId,
      @NonNull Integer yield) {
    this.id = id;
    this.stationId = stationId;
    this.engramId = engramId;
    this.yield = yield;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getStationId() {
    return stationId;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public Integer getYield() {
    return yield;
  }
}
