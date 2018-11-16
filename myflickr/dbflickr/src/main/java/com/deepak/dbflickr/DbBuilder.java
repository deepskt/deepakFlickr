package com.deepak.dbflickr;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DbBuilder {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.deepak.androidflickr.database");
        addPhotoTable(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    private static void addPhotoTable(Schema schema) {
        Entity photoFlickr = schema.addEntity("PhotoFlickr");
        photoFlickr.addStringProperty("id").primaryKey();
        photoFlickr.addIntProperty("page").index();
        photoFlickr.addStringProperty("owner");
        photoFlickr.addStringProperty("secret");
        photoFlickr.addStringProperty("server");
        photoFlickr.addStringProperty("farm");
        photoFlickr.addStringProperty("title");
        photoFlickr.addIntProperty("ispublic");
        photoFlickr.addIntProperty("isfriend");
        photoFlickr.addIntProperty("isfamily");
        photoFlickr.addByteArrayProperty("imageData");
        photoFlickr.addStringProperty("url");
    }
}
