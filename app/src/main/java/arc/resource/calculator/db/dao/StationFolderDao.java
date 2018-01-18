package arc.resource.calculator.db.dao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import arc.resource.calculator.db.entity.StationFolder;
import java.util.List;

@Dao
public interface StationFolderDao {

  @Query("select * from stationfolder "
      + "where stationId = :dlcStationId "
      + "and folderId = :dlcFolderId "
      + "limit 1")
  StationFolder get(String dlcStationId, String dlcFolderId);

  //  get id by matching parameters
  @Query("select id from stationfolder "
      + "where stationId = :dlcStationId "
      + "and folderId = :dlcFolderId "
      + "limit 1")
  String getId(String dlcStationId, String dlcFolderId);

  @Query("select stationId from StationFolder "
      + "where folderId = :dlcFolderId "
      + "limit 1")
  String getStationId(String dlcFolderId);

  @Query("select folderId from stationfolder "
      + "where stationId = :dlcStationId")
  List<String> getFolderIds(String dlcStationId);

  @Query("select * from stationfolder "
      + "where stationId = :dlcStationId")
  List<StationFolder> getAll(String dlcStationId);

  //  insert one object
  @Insert(onConflict = IGNORE)
  long insert(StationFolder stationFolder);

  //  insert multiple objects
  @Insert(onConflict = IGNORE)
  void insert(StationFolder... stationFolders);

  //  insert a list of objects
  @Insert(onConflict = IGNORE)
  void insert(List<StationFolder> stationFolders);

  //  delete all records from table
  @Query("delete from stationfolder")
  void deleteAll();
}
