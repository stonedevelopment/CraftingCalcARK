package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "composite",
    indices = {
        @Index(value = "engramId"),
        @Index(value = "resourceId"),
        @Index(value = {"engramId", "resourceId"},
            unique = true)})

public class BaseComposite {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String engramId;

  @NonNull
  private final String resourceId;

  @NonNull
  private final Integer quantity;

  @Ignore
  public BaseComposite(@NonNull String engramId, String resourceId, Integer quantity) {
    this(Util.generateUUID(), engramId, resourceId, quantity);
  }

  public BaseComposite(@NonNull String id, @NonNull String engramId, @NonNull String resourceId,
      @NonNull Integer quantity) {
    this.id = id;
    this.engramId = engramId;
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

  @NonNull
  public String getEngramId() {
    return engramId;
  }
}
