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
        @Index(value = "nameId"),
        @Index(value = "parentId"),
        @Index(value = {"nameId", "parentId"},
            unique = true)})

public class BaseFolder {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String nameId;

  @NonNull
  private final String parentId;

  @Ignore
  public BaseFolder(String nameId, String parentId) {
    this(Util.generateUUID(), nameId, parentId);
  }

  public BaseFolder(@NonNull String id, @NonNull String nameId, @NonNull String parentId) {
    this.id = id;
    this.nameId = nameId;
    this.parentId = parentId;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getNameId() {
    return nameId;
  }

  @NonNull
  public String getParentId() {
    return parentId;
  }
}
