package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import arc.resource.calculator.util.Util;

@Entity(tableName = "folder",
    indices = {
        @Index(value = "parentId")})

public class BaseFolder {

  @NonNull
  @PrimaryKey
  private final String id;

  @Nullable
  private final String parentId;

  @Ignore
  public BaseFolder() {
    this(null);
  }

  @Ignore
  public BaseFolder(String parentId) {
    this(Util.generateUUID(), parentId);
  }

  public BaseFolder(@NonNull String id, @Nullable String parentId) {
    this.id = id;
    this.parentId = parentId;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @Nullable
  public String getParentId() {
    return parentId;
  }
}
