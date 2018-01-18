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
    @ForeignKey(entity = BaseResource.class,
        parentColumns = "id",
        childColumns = "resourceId")},
    indices = {
        @Index(value = "dlcId"),
        @Index(value = "resourceId"),
        @Index(value = {"dlcId", "resourceId"}, unique = true)})

public class DLCResource {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String dlcId;

  @NonNull
  private final String resourceId;

  @Ignore
  public DLCResource(String dlcId, String resourceId) {
    this(Util.generateUUID(), dlcId, resourceId);
  }

  public DLCResource(@NonNull String id, @NonNull String dlcId, @NonNull String resourceId) {
    this.id = id;
    this.dlcId = dlcId;
    this.resourceId = resourceId;
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
  public String getResourceId() {
    return resourceId;
  }
}
