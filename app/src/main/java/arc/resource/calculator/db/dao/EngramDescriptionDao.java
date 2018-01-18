package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.EngramDescription;
import java.util.List;

@Dao
public interface EngramDescriptionDao {

  @Query("select descriptionId from engramdescription "
      + "where engramId = :dlcEngramId "
      + "limit 1")
  String getDescriptionId(String dlcEngramId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  void insert(EngramDescription engramDescription);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(EngramDescription... engramDescriptions);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<EngramDescription> engramDescriptions);

  //  delete all records from table
  @Query("delete from engramdescription")
  void deleteAll();
}
