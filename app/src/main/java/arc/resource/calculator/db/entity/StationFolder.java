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
    @ForeignKey(entity = DLCFolder.class,
        parentColumns = "id",
        childColumns = "folderId")},
    indices = {
        @Index(value = "stationId"),
        @Index(value = "folderId"),
        @Index(value = {"stationId", "folderId"}, unique = true)
    })

public class StationFolder {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String stationId;

  @NonNull
  private final String folderId;

  @Ignore
  public StationFolder(String stationId, String folderId) {
    this(Util.generateUUID(), stationId, folderId);
  }

  public StationFolder(@NonNull String id, @NonNull String stationId, @NonNull String folderId) {
    this.id = id;
    this.stationId = stationId;
    this.folderId = folderId;
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
  public String getFolderId() {
    return folderId;
  }
}
