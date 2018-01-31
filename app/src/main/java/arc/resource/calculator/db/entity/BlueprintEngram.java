package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(
    foreignKeys = {
        @ForeignKey(entity = BaseEngram.class,
            parentColumns = "id",
            childColumns = "engramId"),
        @ForeignKey(entity = BaseQuality.class,
            parentColumns = "id",
            childColumns = "qualityId")},
    indices = {
        @Index(value = "qualityId"),
        @Index(value = "engramId"),
        @Index(value = {"engramId", "qualityId"},
            unique = true)})

public class BlueprintEngram {

  @NonNull
  @PrimaryKey
  private final String engramId;

  @NonNull
  private final String qualityId;

  public BlueprintEngram(@NonNull String engramId, @NonNull String qualityId) {
    this.engramId = engramId;
    this.qualityId = qualityId;
  }

  @NonNull
  public String getEngramId() {
    return engramId;
  }

  @NonNull
  public String getQualityId() {
    return qualityId;
  }
}
