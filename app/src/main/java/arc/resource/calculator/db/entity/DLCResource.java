package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
    foreignKeys = {
        @ForeignKey(entity = BaseResource.class,
            parentColumns = "id",
            childColumns = "resourceId"),
        @ForeignKey(entity = BaseDLC.class,
            parentColumns = "id",
            childColumns = "dlcId")},
    indices = {
        @Index(value = {"dlcId"}),
        @Index(value = {"resourceId"},
            unique = true)})

public class DLCResource {

  @NonNull
  @PrimaryKey
  private final String resourceId;

  @NonNull
  private final String dlcId;

  public DLCResource(@NonNull String resourceId, @NonNull String dlcId) {
    this.resourceId = resourceId;
    this.dlcId = dlcId;
  }

  @NonNull
  public String getResourceId() {
    return resourceId;
  }

  @NonNull
  public String getDlcId() {
    return dlcId;
  }
}
