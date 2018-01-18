package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(indices = {
    @Index(value = "name",
        unique = true)})
public class Language {

  @NonNull
  @PrimaryKey
  private final String id;

  @NonNull
  private final String name;

  @Ignore
  public Language(String name) {
    this(Util.generateUUID(), name);
  }

  public Language(@NonNull String id, @NonNull String name) {
    this.id = id;
    this.name = name;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getName() {
    return name;
  }
}
