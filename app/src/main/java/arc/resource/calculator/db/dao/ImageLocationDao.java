package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.ImageLocation;

@Dao
public interface ImageLocationDao {

  @Query("select id from imagelocation "
      + "where imageLocationId = :imageFolder "
      + "and imageFile = :imageFile "
      + "limit 1")
  String getId(String imageFolder, String imageFile);

  @Insert(onConflict = IGNORE)
  long insert(ImageLocation imageLocation);
}
