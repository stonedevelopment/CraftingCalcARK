package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(primaryKeys = {"resourceId", "nameId"},
    foreignKeys = {
        @ForeignKey(entity = DLCResource.class,
            parentColumns = "id",
            childColumns = "resourceId"),
        @ForeignKey(entity = Name.class,
            parentColumns = "id",
            childColumns = "nameId")},
    indices = {
        @Index(value = "resourceId"),
        @Index(value = "nameId"),
        @Index(value = {"resourceId", "nameId"}, unique = true)})

public class ResourceName {

  @NonNull
  private final String resourceId;

  @NonNull
  private final String nameId;

  public ResourceName(@NonNull String resourceId, @NonNull String nameId) {
    this.resourceId = resourceId;
    this.nameId = nameId;
  }

  @NonNull
  public String getResourceId() {
    return resourceId;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }
}
