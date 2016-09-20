package arc.resource.calculator.data.initializers;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import arc.resource.calculator.helpers.Helper;
import arc.resource.calculator.model.Category;
import arc.resource.calculator.model.engram.InitEngram;

/**
 * This will ultimately allow us to get rid of all initializers and read/update a json file when
 * initializing database. \nn/.
 * <p/>
 * Category, Resource, Engram->Composition, ComplexResource
 */
public class ConvertToJSON {
    private static final String TAG = ConvertToJSON.class.getSimpleName();

    static Context sContext;

    public static void beginConversion( Context context ) throws JSONException, IOException {
        JSONObject jsonToWriteToFile = new JSONObject();

        sContext = context;

        jsonToWriteToFile.put( "category", convertCategories() );
        jsonToWriteToFile.put( "resource", convertResources() );
        jsonToWriteToFile.put( "engram", convertEngrams() );
        jsonToWriteToFile.put( "complex_resource", convertComplexResources() );

        Helper.Log( TAG, jsonToWriteToFile.toString() );

//        EditText textView = ( EditText ) findViewById( R.id.textView );
//        textView.setText( jsonToWriteToFile.toString() );

        File path = sContext.getExternalFilesDir( null );
        File file = new File( path, "jsonExport.txt" );

        try ( FileOutputStream fileOutputStream = new FileOutputStream( file ) ) {
            fileOutputStream.write( jsonToWriteToFile.toString().getBytes() );
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        Helper.Log( TAG, "Filename: " + file.getPath() + " " + file.getName() );
    }

    private static JSONArray convertCategories() throws JSONException {
        List<Category> categoryList = CategoryInitializer.getCategories();

        JSONArray jsonArray = new JSONArray();

        for ( Category category : categoryList ) {
            JSONObject jsonCategoryObject = new JSONObject();
            jsonCategoryObject.put( "id", category.getId() );
            jsonCategoryObject.put( "name", category.getName() );
            jsonCategoryObject.put( "parent_id", category.getParent() );

            jsonArray.put( jsonCategoryObject );
        }

        return jsonArray;
    }

    private static JSONArray convertResources() throws JSONException {
        SparseArray<String> resourceList = ResourceInitializer.getResources();

        JSONArray jsonArray = new JSONArray();

        for ( int i = 0; i < resourceList.size(); i++ ) {
            JSONObject jsonObject = new JSONObject();

            String name = resourceList.valueAt( i );
            int drawable = resourceList.keyAt( i );

            jsonObject.put( "name", name );
            jsonObject.put( "drawable", sContext.getResources().getResourceEntryName( drawable ) );

            jsonArray.put( jsonObject );
        }

        Helper.Log( TAG, jsonArray.toString() );

        return jsonArray;
    }

    private static JSONArray convertEngrams() throws JSONException {
        List<InitEngram> engramList = EngramInitializer.getEngrams();

        JSONArray jsonArray = new JSONArray();

        for ( InitEngram engram : engramList ) {
            JSONObject jsonEngramObject = new JSONObject();

            jsonEngramObject.put( "name", engram.getName() );
            jsonEngramObject.put( "description", engram.getDescription() );
            jsonEngramObject.put( "drawable", sContext.getResources().getResourceEntryName( engram.getDrawable() ) );
            jsonEngramObject.put( "category_id", engram.getCategoryId() );

            JSONArray jsonCompositionArray = new JSONArray();
            SparseIntArray composition = engram.getComposition();
            for ( int i = 0; i < composition.size(); i++ ) {
                JSONObject jsonCompositionObject = new JSONObject();

                int quantity = composition.valueAt( i );
                int drawable = composition.keyAt( i );

                jsonCompositionObject.put( "quantity", quantity );
                jsonCompositionObject.put( "drawable", sContext.getResources().getResourceEntryName( drawable ) );

                jsonCompositionArray.put( jsonCompositionObject );
            }

            jsonEngramObject.put( "composition", jsonCompositionArray );

            jsonArray.put( jsonEngramObject );
        }

        Helper.Log( TAG, jsonArray.toString() );

        return jsonArray;
    }

    private static JSONArray convertComplexResources() throws JSONException {
        SparseArray<String> complexResourceList = ComplexResourceInitializer.getResources();

        JSONArray jsonArray = new JSONArray();

        for ( int i = 0; i < complexResourceList.size(); i++ ) {
            JSONObject jsonObject = new JSONObject();

            String name = complexResourceList.valueAt( i );
            int drawable = complexResourceList.keyAt( i );

            jsonObject.put( "name", name );
            jsonObject.put( "drawable", sContext.getResources().getResourceEntryName( drawable ) );

            jsonArray.put( jsonObject );
        }

        Helper.Log( TAG, jsonArray.toString() );

        return jsonArray;
    }
}
