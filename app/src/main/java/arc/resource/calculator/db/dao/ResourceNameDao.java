package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.ResourceName;
import java.util.List;

@Dao
public interface ResourceNameDao {

  //  get resource id by name id
  @Query("select nameId from resourcename "
      + "where resourceId = :resourceId "
      + "limit 1")
  String getNameId(String resourceId);

  //  get name id by resource id
  @Query("select resourceId from resourcename "
      + "where nameId = :nameId "
      + "limit 1")
  String getResourceId(String nameId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  void insert(ResourceName resourceName);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(ResourceName... resourceNames);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<ResourceName> resourceNames);

  //  delete all records from table
  @Query("delete from resourcename")
  void deleteAll();
}
