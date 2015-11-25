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

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.arekar.android.ventascasacasa.provider.jsondataprovider.base.AbstractContentValues;

public class JsonContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return JsonColumns.CONTENT_URI;
    }

    public int update(ContentResolver contentResolver, @Nullable JsonSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public int update(Context context, @Nullable JsonSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public JsonContentValues putData(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("data must not be null");
        mContentValues.put(JsonColumns.DATA, value);
        return this;
    }


    public JsonContentValues putLastModified(@NonNull Date value) {
        if (value == null) throw new IllegalArgumentException("lastModified must not be null");
        mContentValues.put(JsonColumns.LAST_MODIFIED, value.getTime());
        return this;
    }


    public JsonContentValues putLastModified(long value) {
        mContentValues.put(JsonColumns.LAST_MODIFIED, value);
        return this;
    }

    public JsonContentValues putUserId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("userId must not be null");
        mContentValues.put(JsonColumns.USER_ID, value);
        return this;
    }

    public JsonContentValues putId(@NonNull long value) {
        if (value <= 0) throw new IllegalArgumentException("id must be greater than 0");
        mContentValues.put(JsonColumns._ID, value);
        return this;
    }

    public JsonContentValues putMd5(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("md5 must not be null");
        mContentValues.put(JsonColumns.MD5, value);
        return this;
    }

}
