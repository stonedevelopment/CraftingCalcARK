package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(foreignKeys = {
    @ForeignKey(entity = StationEngram.class,
        parentColumns = "id",
        childColumns = "engramId"),
    @ForeignKey(entity = BaseComposite.class,
        parentColumns = "id",
        childColumns = "compositeId")},
    indices = {
        @Index(value = "engramId"),
        @Index(value = "compositeId"),
        @Index(value = {"engramId", "compositeId"}, unique = true)})

public class EngramComposite {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String engramId;

  @NonNull
  private final String compositeId;

  @Ignore
  public EngramComposite(String engramId, String compositeId) {
    this(Util.generateUUID(), engramId, compositeId);
  }

  public EngramComposite(@NonNull String id, @NonNull String engramId,
      @NonNull String compositeId) {
    this.id = id;
    this.engramId = engramId;
    this.compositeId = compositeId;
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
  public String getCompositeId() {
    return compositeId;
  }

}
