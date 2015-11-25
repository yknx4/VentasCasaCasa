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

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arekar.android.ventascasacasa.provider.jsondataprovider.base.AbstractCursor;

public class JsonCursor extends AbstractCursor implements JsonModel {
    public JsonCursor(Cursor cursor) {
        super(cursor);
    }

    public long getId() {
        Long res = getLongOrNull(JsonColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    @NonNull
    public String getData() {
        String res = getStringOrNull(JsonColumns.DATA);
        if (res == null)
            throw new NullPointerException("The value of 'data' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    @NonNull
    public Date getLastModified() {
        Date res = getDateOrNull(JsonColumns.LAST_MODIFIED);
        if (res == null)
            throw new NullPointerException("The value of 'last_modified' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    @NonNull
    public String getUserId() {
        String res = getStringOrNull(JsonColumns.USER_ID);
        if (res == null)
            throw new NullPointerException("The value of 'user_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    @NonNull
    public String getMd5() {
        String res = getStringOrNull(JsonColumns.MD5);
        if (res == null)
            throw new NullPointerException("The value of 'md5' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
