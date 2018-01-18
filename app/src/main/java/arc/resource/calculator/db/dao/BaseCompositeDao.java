package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.BaseComposite;
import java.util.List;

@Dao
public interface BaseCompositeDao {

  //  get object by id
  @Query("select * from composite "
      + "where id = :id "
      + "limit 1")
  BaseComposite get(String id);

  //  get id by its matching parameters
  @Query("select id from composite " +
      "where resourceId = :resourceId and " +
      "quantity = :quantity ")
  String getId(String resourceId, int quantity);

  @Query("select * from composite "
      + "where id in (:ids)")
  List<BaseComposite> getAll(List<String> ids);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(BaseComposite composite);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(BaseComposite... composites);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<BaseComposite> composites);

  //  delete all records from table
  @Query("delete from composite")
  void deleteAll();
}
