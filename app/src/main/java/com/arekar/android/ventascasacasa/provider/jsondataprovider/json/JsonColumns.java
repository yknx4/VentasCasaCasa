/*
 * This source is part of the
 *      _____  ___   ____
 *  __ / / _ \/ _ | / __/___  _______ _
 * / // / , _/ __ |/ _/_/ _ \/ __/ _ `/
 * \___/_/|_/_/ |_/_/ (_)___/_/  \_, /
 *                              /___/
 * repository.
 *
 * Copyright (C) 2012-2015 Benoit 'BoD' Lubek (BoD@JRAF.org)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.arekar.android.ventascasacasa.provider.jsondataprovider.json;

import android.net.Uri;
import android.provider.BaseColumns;

import com.arekar.android.ventascasacasa.provider.jsondataprovider.JsonDataProvider;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonColumns;

public class JsonColumns implements BaseColumns {

    public static final long ROW_CLIENTS_ID = 1;
    public static final long ROW_PRODUCTS_ID = 2;
    public static final long ROW_SALES_ID = 3;
    public static final long ROW_PAYMENTS_ID = 4;

    public static final String TABLE_NAME = "json";
    public static final Uri CONTENT_URI = Uri.parse(JsonDataProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    public static final String _ID = BaseColumns._ID;

    public static final String DATA = "data";

    public static final String LAST_MODIFIED = "last_modified";

    public static final String USER_ID = "user_id";

    public static final String MD5 = "md5";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            DATA,
            LAST_MODIFIED,
            USER_ID,
            MD5
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(DATA) || c.contains("." + DATA)) return true;
            if (c.equals(LAST_MODIFIED) || c.contains("." + LAST_MODIFIED)) return true;
            if (c.equals(USER_ID) || c.contains("." + USER_ID)) return true;
            if (c.equals(MD5) || c.contains("." + MD5)) return true;
        }
        return false;
    }

}
