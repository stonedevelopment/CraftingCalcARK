package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import arc.resource.calculator.util.Util;

@Entity(indices = {
    @Index(value = "text",
        unique = true)})

public class Name {

  @NonNull
  @PrimaryKey
  private String id;

  @NonNull
  private final String text;

  @Ignore
  public Name(@NonNull String text) {
    this(Util.generateUUID(), text);
  }

  public Name(@NonNull String id, @NonNull String text) {
    this.id = id;
    this.text = text;
  }

  @NonNull
  public String getId() {
    return id;
  }

  @NonNull
  public String getText() {
    return text;
  }
}