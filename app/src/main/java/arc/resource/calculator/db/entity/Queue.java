package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(foreignKeys = {
    @ForeignKey(entity = BaseEngram.class,
        parentColumns = "id",
        childColumns = "engramId")},
    indices = {
        @Index(value = "engramId",
            unique = true)})
public class Queue {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String engramId;

  @NonNull
  private final Integer quantity;

  @Ignore
  public Queue(String engramId, Integer quantity) {
    this(Util.generateUUID(), engramId, quantity);
  }

  public Queue(@NonNull String id, @NonNull String engramId, @NonNull Integer quantity) {
    this.id = id;
    this.engramId = engramId;
    this.quantity = quantity;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public Integer getQuantity() {
    return quantity;
  }
}
