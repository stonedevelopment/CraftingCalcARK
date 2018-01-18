package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "composite",
    foreignKeys = {
        @ForeignKey(entity = DLCResource.class,
            parentColumns = "id",
            childColumns = "resourceId")},
    indices = {
        @Index(value = "resourceId"),
        @Index(value = {"resourceId", "quantity"},
            unique = true)})

public class BaseComposite {

  @NonNull
  @PrimaryKey
  private final String id;

  //  id from DLCResource
  @NonNull
  private final String resourceId;

  @NonNull
  private final Integer quantity;

  @Ignore
  public BaseComposite(String resourceId, Integer quantity) {
    this(Util.generateUUID(), resourceId, quantity);
  }

  public BaseComposite(@NonNull String id, @NonNull String resourceId, @NonNull Integer quantity) {
    this.id = id;
    this.resourceId = resourceId;
    this.quantity = quantity;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getResourceId() {
    return resourceId;
  }

  @NonNull
  public Integer getQuantity() {
    return quantity;
  }
}
