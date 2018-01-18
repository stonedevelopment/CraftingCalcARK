package arc.resource.calculator.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import java.util.List;

/**
 * Created by jared on 1/8/2018.
 */

public class DLCWithStations {

  @Embedded
  DLCStation dlcStation;

  @Relation(entity = DLCStation.class, parentColumn = "kkkid", entityColumn = "id")
  List<BaseStation> stations;
}
