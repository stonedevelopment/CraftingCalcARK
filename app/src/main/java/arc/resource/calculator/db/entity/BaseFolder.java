package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(tableName = "folder",
    indices = {
        @Index(value = {"nameId"},
            unique = true)})

public class BaseFolder {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String nameId;

  @Ignore
  public BaseFolder(String nameId) {
    this(Util.generateUUID(), nameId);
  }

  public BaseFolder(@NonNull String id, @NonNull String nameId) {
    this.id = id;
    this.nameId = nameId;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }
}
