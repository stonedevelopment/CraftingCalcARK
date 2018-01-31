package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.ImageLocation;

@Dao
public interface ImageLocationDao {

  @Query("select * from imagelocation "
      + "where id = :id "
      + "limit 1")
  ImageLocation get(String id);

  @Query("select id from imagelocation "
      + "where contentFolder = :contentFolder "
      + "and imageFolder = :imageFolder "
      + "and imageFile = :imageFile "
      + "limit 1")
  String getId(String contentFolder, String imageFolder, String imageFile);

  @Insert(onConflict = IGNORE)
  long insert(ImageLocation imageLocation);
}
