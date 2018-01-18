package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import java.util.List;

/**
 * Created by jared on 1/9/2018.
 */

public class FolderWithFolders {

  @Embedded
  StationFolder stationFolder;

  @Relation(entity = BaseFolder.class, parentColumn = "id", entityColumn = "parentId")
  List<BaseFolder> folders;
}
