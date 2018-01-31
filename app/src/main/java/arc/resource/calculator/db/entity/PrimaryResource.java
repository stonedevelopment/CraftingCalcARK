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
            childColumns = "resourceId")},
    indices = {
        @Index(value = {"resourceId"},
            unique = true)})

public class PrimaryResource {

  @NonNull
  @PrimaryKey
  private final String resourceId;

  public PrimaryResource(@NonNull String resourceId) {
    this.resourceId = resourceId;
  }

  @NonNull
  public String getResourceId() {
    return resourceId;
  }
}
