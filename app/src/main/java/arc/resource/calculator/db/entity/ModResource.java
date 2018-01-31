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
        @ForeignKey(entity = BaseMod.class,
            parentColumns = "id",
            childColumns = "modId")},
    indices = {
        @Index(value = {"modId"}),
        @Index(value = {"resourceId"},
            unique = true)})

public class ModResource {

  @NonNull
  @PrimaryKey
  private final String resourceId;

  @NonNull
  private final String modId;

  public ModResource(@NonNull String resourceId, @NonNull String modId) {
    this.resourceId = resourceId;
    this.modId = modId;
  }

  @NonNull
  public String getResourceId() {
    return resourceId;
  }

  @NonNull
  public String getModId() {
    return modId;
  }
}
