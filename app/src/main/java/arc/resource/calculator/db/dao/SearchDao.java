package arc.resource.calculator.db.dao;

import android.arch.persistence.room.Dao;

@Dao
public interface SearchDao {

//  @Query("select * from name "
//      + "inner join engram on engram.nameId = name.id "
//      + "where text like :query")
//  List<Name> search(String query);

//  @Query("select engram.id, contentFolder, imageFolder, imageFile, name.text "
//      + "from engram, imageLocation, name "
//      + "where name.text like :query "
//      + "and engram.nameId = name.id "
//      + "and engram.imageLocationId = imageLocation.id "
//      + "and engram.dlcId = null")
//  List<Displayable> search(String query);
//
//  @Query("select dlcEngram.id, contentFolder, imageFolder, imageFile, name.text "
//      + "from dlcEngram, imageLocation, name "
//      + "where name.text like :query "
//      + "and dlcEngram.nameId = name.id "
//      + "and dlcEngram.imageLocationId = imageLocation.id "
//      + "and dlcEngram.dlcId = :dlcId")
//  List<Displayable> searchWithDLC(String query, String dlcId);
//
//  List<Displayable> searchWithMod(String query, String modId);
}