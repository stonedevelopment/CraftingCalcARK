package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.Name;
import java.util.List;

@Dao
public interface NameDao {

  //  get object by id
  @Query("select * from name "
      + "where id = :id")
  Name get(String id);

  //  get id by matching text
  @Query("select id from name "
      + "where text = :name")
  String getId(String name);

  //  get all objects
  @Query("select * from name "
      + "order by text")
  List<Name> getAll();

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(Name name);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(Name... names);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<Name> names);

  //  delete all records from table
  @Query("delete from name")
  int deleteAll();
}
