package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.Description;
import java.util.List;

@Dao
public interface DescriptionDao {

  //  get object by id
  @Query("select * from description " +
      "where id = :descriptionId")
  Description get(String descriptionId);

  //  get id by its matching parameters
  @Query("select id from description " +
      "where text = :description")
  String getId(String description);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(Description description);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(Description... descriptions);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<Description> descriptions);

  //  delete all records from table
  @Query("delete from description")
  void deleteAll();
}
