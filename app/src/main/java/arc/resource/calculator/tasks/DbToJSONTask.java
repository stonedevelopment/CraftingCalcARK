/*
 * Copyright (c) 2020 Jared Stone
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-NoDerivatives 4.0 International
 * License. To view a copy of this license, visit
 *
 * http://creativecommons.org/licenses/by-nc-nd/4.0/
 *
 * or send a letter to
 *
 *  Creative Commons,
 *  PO Box 1866,
 *  Mountain View, CA 94042, USA.
 */

package arc.resource.calculator.tasks;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import arc.resource.calculator.R;
import arc.resource.calculator.db.DatabaseContract;
import arc.resource.calculator.model.Station;
import arc.resource.calculator.model.category.Category;
import arc.resource.calculator.model.engram.Engram;
import arc.resource.calculator.model.resource.CompositeResource;
import arc.resource.calculator.model.resource.Resource;
import arc.resource.calculator.util.JSONUtil;

public class DbToJSONTask extends AsyncTask<Void, String, Boolean> {
    public static final String TAG = DbToJSONTask.class.getSimpleName();

    private final String COLUMN_DESCRIPTION = DatabaseContract.EngramEntry.COLUMN_DESCRIPTION;
    private final String COLUMN_FROM = DatabaseContract.TotalConversionEntry.COLUMN_FROM;
    private final String COLUMN_CRAFTING_TIME = "crafting_time";
    private final String COLUMN_IMAGE_FILE = "image_file";
    private final String COLUMN_IMAGE_PATH = "image_path";
    private final String COLUMN_LEVEL = DatabaseContract.EngramEntry.COLUMN_LEVEL;
    private final String COLUMN_NAME = DatabaseContract.EngramEntry.COLUMN_NAME;
    private final String COLUMN_POINTS = DatabaseContract.EngramEntry.COLUMN_POINTS;
    private final String COLUMN_QUANTITY = DatabaseContract.CompositionEntry.COLUMN_QUANTITY;
    private final String COLUMN_TO = DatabaseContract.TotalConversionEntry.COLUMN_TO;
    private final String COLUMN_YIELD = DatabaseContract.EngramEntry.COLUMN_YIELD;
    private final String COLUMN_XP = DatabaseContract.EngramEntry.COLUMN_XP;

    //  json
    private final String COMPOSITION = DatabaseContract.CompositionEntry.TABLE_NAME;
    private final String DATA = "data";
    private final String DETAILS = "details";
    private final String ENGRAMS = "engrams";
    private final String FOLDERS = "folders";
    private final String LOGO_IMAGE_NAME = "logo.webp";
    private final String LOGO_IMAGE_FILE = "logo_image_file";
    private final String FOLDER_IMAGE_NAME = "folder.webp";
    private final String FOLDER_IMAGE_FILE = "folder_image_file";
    private final String RESOURCES = "resources";

    private WeakReference<Context> mContext;
    private Exception mException;
    private Listener mListener;

    private JSONObject mJSONObject;

    private ArrayList<Uri> mAttachmentUriList = new ArrayList<>();

    public DbToJSONTask(Context context, JSONObject jsonObject, Listener listener) {
        setContext(context);
        setListener(listener);
        mJSONObject = jsonObject;
    }

    private Context getContext() {
        return mContext.get();
    }

    private void setContext(Context context) {
        mContext = new WeakReference<>(context);
    }

    private Listener getListener() {
        return mListener;
    }

    private void setListener(Listener listener) {
        mListener = listener;
    }

    private void updateStatus(String statusMessage) {
        getListener().onUpdate(statusMessage);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        for (String value : values) {
            updateStatus(value);
        }
    }

    @Override
    protected void onPostExecute(Boolean didUpdate) {
        if (mException == null) {
            getListener().onFinish(mAttachmentUriList);
        } else {
            getListener().onError(mException);
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            getListener().onStart();

            try {
                for (long dlcId = 1; dlcId <= 5; dlcId++) {
                    String jsonString = JSONUtil.readRawJsonFileToJsonString(getContext(), R.raw.data_editable);
                    mJSONObject = new JSONObject(jsonString);

                    Uri uri = writeJSONObjectToFile(getNameByDlcId(dlcId), createJSONObjectForDLC(dlcId));
                    mAttachmentUriList.add(uri);
                }
            } catch (JSONException e) {
                getListener().onError(e);
            }

            return true;
        } catch (Exception e) {
            mException = e;
            return false;
        }
    }

    private JSONObject createJSONObjectForDLC(long dlcId) throws JSONException {
        Log.d(TAG, "createJSONObjectForDLC: " + getNameByDlcId(dlcId));
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(COLUMN_NAME, getNameByDlcId(dlcId));
        jsonObject.put(COLUMN_DESCRIPTION, "");
        jsonObject.put(COLUMN_IMAGE_PATH, buildImagePathByDLCId(dlcId));
        jsonObject.put(LOGO_IMAGE_FILE, LOGO_IMAGE_NAME);
        jsonObject.put(FOLDER_IMAGE_FILE, FOLDER_IMAGE_NAME);

        //  start with stations by dlcId
        //  next, check folders by station and parent of 0
        //  next, check engrams by station and parent of 0

        jsonObject.put(ENGRAMS, createJSONArrayForDLCEngrams(dlcId));
        jsonObject.put(RESOURCES, createJSONArrayForResources(dlcId));

        return jsonObject;
    }

    private JSONArray createJSONArrayForDLCEngrams(long dlcId) throws JSONException {
        Log.d(TAG, "createJSONArrayForDLCEngrams: ");
        JSONArray jsonArray = new JSONArray();

        List<Station> stations = queryForStations(DatabaseContract.StationEntry.buildUriWithDLCId(dlcId));
        for (Station station : stations) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(COLUMN_NAME, station.getName());
            jsonObject.put(COLUMN_DESCRIPTION, "");
            jsonObject.put(COLUMN_IMAGE_FILE, station.getFile());

            JSONArray engramArray = createJSONArrayForEngrams(dlcId, 0, station.getId());
            jsonObject.put(ENGRAMS, engramArray);

            JSONArray folderArray = createJSONArrayForFolders(dlcId, 0, station.getId());
            jsonObject.put(FOLDERS, folderArray);

            //  do not add if there are no engrams or folders
            if (engramArray.length() > 0 || folderArray.length() > 0)
                jsonArray.put(jsonObject);
        }

        Log.d(TAG, jsonArray.toString());
        return jsonArray;
    }

    private JSONArray createJSONArrayForFolders(long dlcId, long parentId, long stationId) throws JSONException {
        Log.d(TAG, "createJSONArrayForFolders: ");
        JSONArray jsonArray = new JSONArray();

        List<Category> categories = queryForCategories(DatabaseContract.CategoryEntry.buildUriWithStationId(dlcId, parentId, stationId));
        for (Category category : categories) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(COLUMN_NAME, category.getName());

            JSONArray engramArray = createJSONArrayForEngrams(dlcId, category.getId(), stationId);
            jsonObject.put(ENGRAMS, engramArray);

            JSONArray folderArray = createJSONArrayForFolders(dlcId, category.getId(), stationId);
            jsonObject.put(FOLDERS, folderArray);

            //  do not add if there are no engrams or folders
            if (engramArray.length() > 0 || folderArray.length() > 0)
                jsonArray.put(jsonObject);
        }

        Log.d(TAG, jsonArray.toString());
        return jsonArray;
    }

    private JSONArray createJSONArrayForEngrams(long dlcId, long parentId, long stationId) throws JSONException {
        Log.d(TAG, "createJSONArrayForEngrams: ");
        JSONArray jsonArray = new JSONArray();

        List<ConversionEngram> engrams = queryForEngrams(DatabaseContract.EngramEntry.buildUriWithCategoryIdAndStationId(dlcId, parentId, stationId));
        for (ConversionEngram engram : engrams) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(COLUMN_NAME, engram.getName());
            jsonObject.put(COLUMN_DESCRIPTION, engram.getDescription());
            jsonObject.put(COLUMN_IMAGE_FILE, engram.getFile());
            jsonObject.put(COLUMN_LEVEL, engram.getRequiredLevel());
            jsonObject.put(COLUMN_YIELD, engram.getYield());
            jsonObject.put(COLUMN_POINTS, 0);
            jsonObject.put(COLUMN_XP, 0);
            jsonObject.put(COLUMN_CRAFTING_TIME, 0);

            JSONArray compositionArray = createJSONArrayForComposition(dlcId, engram.getId());
            jsonObject.put(COMPOSITION, compositionArray);

            jsonArray.put(jsonObject);
        }

        Log.d(TAG, jsonArray.toString());
        return jsonArray;
    }

    private JSONArray createJSONArrayForComposition(long dlcId, long engramId) throws JSONException {
        JSONArray jsonArray = new JSONArray();

        List<CompositeResource> composition = queryForComposition(dlcId, DatabaseContract.CompositionEntry.buildUriWithEngramId(dlcId, engramId));
        for (CompositeResource resource : composition) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(COLUMN_NAME, resource.getName());
            jsonObject.put(COLUMN_QUANTITY, resource.getQuantity());

            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    private JSONArray createJSONArrayForResources(long dlcId) throws JSONException {
        Log.d(TAG, "createJSONArrayForResources: ");
        JSONArray jsonArray = new JSONArray();

        List<Resource> resources = queryForResources(DatabaseContract.ResourceEntry.buildUriWithDLCId(dlcId));
        for (Resource resource : resources) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put(COLUMN_NAME, resource.getName());
            jsonObject.put(COLUMN_DESCRIPTION, "");
            jsonObject.put(COLUMN_IMAGE_FILE, resource.getFile());

            jsonArray.put(jsonObject);
        }

        Log.d(TAG, jsonArray.toString());
        return jsonArray;
    }

    private List<Station> queryForStations(Uri uri) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) return new ArrayList<>();

            List<Station> stations = new ArrayList<>();
            while (cursor.moveToNext()) {
                Station station = Station.fromCursor(cursor);
                stations.add(station);
            }

            return stations;
        }
    }

    private List<Category> queryForCategories(Uri uri) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) return new ArrayList<>();

            List<Category> categoryMap = new ArrayList<>();
            while (cursor.moveToNext()) {
                Category category = Category.fromCursor(cursor);
                categoryMap.add(category);
            }

            return categoryMap;
        }
    }

    private List<ConversionEngram> queryForEngrams(Uri uri) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) return new ArrayList<>();

            List<ConversionEngram> engrams = new ArrayList<>();
            while (cursor.moveToNext()) {
                ConversionEngram engram = ConversionEngram.fromCursor(cursor);

                engrams.add(engram);
            }

            return engrams;
        }
    }

    private Resource queryForResource(Uri uri) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) return null;
            if (!cursor.moveToFirst()) return null;

            return Resource.fromCursor(cursor);
        }
    }

    private List<CompositeResource> queryForComposition(long dlcId, Uri uri) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) return new ArrayList<>();

            List<String> resourceNames = new ArrayList<>();
            List<Long> resourceIds = new ArrayList<>();
            List<CompositeResource> composition = new ArrayList<>();
            while (cursor.moveToNext()) {
                long resourceId = cursor.getLong(cursor.getColumnIndex(DatabaseContract.CompositionEntry.COLUMN_RESOURCE_KEY));
                int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseContract.CompositionEntry.COLUMN_QUANTITY));

                //  skip ids already added
                if (resourceIds.contains(resourceId)) continue;

                Resource resource = queryForResource(DatabaseContract.ResourceEntry.buildUriWithOnlyId(resourceId));
                if (resource == null) {
                    Log.d(TAG, "queryForComposition: null: " + resourceId + "/ " + quantity);
                    continue;
                }

                //  skip names already added (duplicates arise with each station id)
                if (resourceNames.contains(resource.getName())) continue;

                resourceIds.add(resourceId);
                resourceNames.add(resource.getName());

                composition.add(new CompositeResource(resource, quantity));
            }

            return composition;
        }
    }

    private List<Resource> queryForResources(Uri uri) {
        try (Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null)) {
            if (cursor == null) return new ArrayList<>();

            List<Resource> resources = new ArrayList<>();
            while (cursor.moveToNext()) {
                resources.add(Resource.fromCursor(cursor));
            }

            return resources;
        }
    }

    private Uri writeJSONObjectToFile(String name, JSONObject object) throws IOException {
        publishProgress("Writing new JSON object to file...");

        File path = getContext().getExternalFilesDir(null);
        File file = new File(path, name + ".json");

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(object.toString().getBytes());

        return Uri.parse("file:///" + file.getPath());
    }

    private String getNameByDlcId(long dlcId) {
        if (dlcId == 1) {
            return "Primary";
        } else if (dlcId == 2) {
            return "Primitive Plus";
        } else if (dlcId == 3) {
            return "Scorched Earth";
        } else if (dlcId == 4) {
            return "Aberration";
        } else if (dlcId == 5) {
            return "Extinction";
        }
        return null;
    }

    private String buildImagePathByDLCId(long dlcId) {
        if (dlcId == 1)
            return getNameByDlcId(dlcId) + "/";

        return "DLC/" + getNameByDlcId(dlcId) + "/";
    }

    public interface Listener {
        void onError(Exception e);

        void onStart();

        void onUpdate(String message);

        void onFinish(ArrayList<Uri> jsonAttachmentUriList);
    }

    static class ConversionEngram extends Engram {
        private String description;
        private int requiredLevel;

        ConversionEngram(long id, String name, String folder, String file, int yield, String description, int requiredLevel) {
            super(id, name, folder, file, yield);
            this.description = description;
            this.requiredLevel = requiredLevel;
        }

        public static ConversionEngram fromCursor(Cursor cursor) {
            long _id = cursor.getLong(cursor.getColumnIndex(DatabaseContract.EngramEntry._ID));
            String name = cursor
                    .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_NAME));
            String folder = cursor
                    .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FOLDER));
            String file = cursor
                    .getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_IMAGE_FILE));
            int yield = cursor
                    .getInt(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_YIELD));
            String description = cursor.getString(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_DESCRIPTION));
            int requiredLevel = cursor.getInt(cursor.getColumnIndex(DatabaseContract.EngramEntry.COLUMN_LEVEL));

            return new ConversionEngram(_id, name, folder, file, yield, description, requiredLevel);
        }

        String getDescription() {
            return description;
        }

        int getRequiredLevel() {
            return requiredLevel;
        }
    }
}