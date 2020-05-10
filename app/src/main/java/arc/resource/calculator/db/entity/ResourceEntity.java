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

package arc.resource.calculator.db.entity;

import androidx.room.Entity;

import org.json.JSONException;
import org.json.JSONObject;

import arc.resource.calculator.db.dao.ResourceDao;

/**
 * Resource object for base game data (vanilla)
 * <p>
 * Resources for DLC game data should extend from this class
 * <p>
 * Example fromJSON:
 * "uuid": "a065cfa0-6a18-4a91-9848-d701484dd31e",
 * "name": "Allosaurus Brain",
 * "description": "",
 * "imageFile": "allosaurus_brain.webp",
 * "lastUpdated": 1589045798912
 */
@Entity(tableName = ResourceDao.tableName)
public class ResourceEntity {
    private final String uuid;

    //  name of resource
    private final String name;

    //  description of resource TODO: 3/28/2020 add descriptions for resources from website
    private final String description;

    //  filename of image in /assets folder
    private final String image;

    public ResourceEntity(String uuid, String name, String description, String image) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    /**
     * "uuid": "a065cfa0-6a18-4a91-9848-d701484dd31e",
     * "name": "Allosaurus Brain",
     * "description": "",
     * "imageFile": "allosaurus_brain.webp",
     * "lastUpdated": 1589045798912
     */
    public static ResourceEntity fromJSON(JSONObject jsonObject) throws JSONException {
        String uuid = jsonObject.getString("uuid");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        String imageFile = jsonObject.getString("imageFile");
        return new ResourceEntity(uuid, name, description, imageFile);
    }
}
