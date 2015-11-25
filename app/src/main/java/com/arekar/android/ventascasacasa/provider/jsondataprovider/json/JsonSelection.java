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
import android.database.Cursor;
import android.net.Uri;

import com.arekar.android.ventascasacasa.provider.jsondataprovider.base.AbstractSelection;

public class JsonSelection extends AbstractSelection<JsonSelection> {
    @Override
    protected Uri baseUri() {
        return JsonColumns.CONTENT_URI;
    }

    public JsonCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new JsonCursor(cursor);
    }



    public JsonCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    public JsonCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new JsonCursor(cursor);
    }

    public JsonCursor query(Context context) {
        return query(context, null);
    }


    public JsonSelection id(long... value) {
        addEquals("json." + JsonColumns._ID, toObjectArray(value));
        return this;
    }

    public JsonSelection idNot(long... value) {
        addNotEquals("json." + JsonColumns._ID, toObjectArray(value));
        return this;
    }

    public JsonSelection orderById(boolean desc) {
        orderBy("json." + JsonColumns._ID, desc);
        return this;
    }

    public JsonSelection orderById() {
        return orderById(false);
    }

    public JsonSelection data(String... value) {
        addEquals(JsonColumns.DATA, value);
        return this;
    }

    public JsonSelection dataNot(String... value) {
        addNotEquals(JsonColumns.DATA, value);
        return this;
    }

    public JsonSelection dataLike(String... value) {
        addLike(JsonColumns.DATA, value);
        return this;
    }

    public JsonSelection dataContains(String... value) {
        addContains(JsonColumns.DATA, value);
        return this;
    }

    public JsonSelection dataStartsWith(String... value) {
        addStartsWith(JsonColumns.DATA, value);
        return this;
    }

    public JsonSelection dataEndsWith(String... value) {
        addEndsWith(JsonColumns.DATA, value);
        return this;
    }

    public JsonSelection orderByData(boolean desc) {
        orderBy(JsonColumns.DATA, desc);
        return this;
    }

    public JsonSelection orderByData() {
        orderBy(JsonColumns.DATA, false);
        return this;
    }

    public JsonSelection lastModified(Date... value) {
        addEquals(JsonColumns.LAST_MODIFIED, value);
        return this;
    }

    public JsonSelection lastModifiedNot(Date... value) {
        addNotEquals(JsonColumns.LAST_MODIFIED, value);
        return this;
    }

    public JsonSelection lastModified(long... value) {
        addEquals(JsonColumns.LAST_MODIFIED, toObjectArray(value));
        return this;
    }

    public JsonSelection lastModifiedAfter(Date value) {
        addGreaterThan(JsonColumns.LAST_MODIFIED, value);
        return this;
    }

    public JsonSelection lastModifiedAfterEq(Date value) {
        addGreaterThanOrEquals(JsonColumns.LAST_MODIFIED, value);
        return this;
    }

    public JsonSelection lastModifiedBefore(Date value) {
        addLessThan(JsonColumns.LAST_MODIFIED, value);
        return this;
    }

    public JsonSelection lastModifiedBeforeEq(Date value) {
        addLessThanOrEquals(JsonColumns.LAST_MODIFIED, value);
        return this;
    }

    public JsonSelection orderByLastModified(boolean desc) {
        orderBy(JsonColumns.LAST_MODIFIED, desc);
        return this;
    }

    public JsonSelection orderByLastModified() {
        orderBy(JsonColumns.LAST_MODIFIED, false);
        return this;
    }

    public JsonSelection userId(String... value) {
        addEquals(JsonColumns.USER_ID, value);
        return this;
    }

    public JsonSelection userIdNot(String... value) {
        addNotEquals(JsonColumns.USER_ID, value);
        return this;
    }

    public JsonSelection userIdLike(String... value) {
        addLike(JsonColumns.USER_ID, value);
        return this;
    }

    public JsonSelection userIdContains(String... value) {
        addContains(JsonColumns.USER_ID, value);
        return this;
    }

    public JsonSelection userIdStartsWith(String... value) {
        addStartsWith(JsonColumns.USER_ID, value);
        return this;
    }

    public JsonSelection userIdEndsWith(String... value) {
        addEndsWith(JsonColumns.USER_ID, value);
        return this;
    }

    public JsonSelection orderByUserId(boolean desc) {
        orderBy(JsonColumns.USER_ID, desc);
        return this;
    }

    public JsonSelection orderByUserId() {
        orderBy(JsonColumns.USER_ID, false);
        return this;
    }

    public JsonSelection md5(String... value) {
        addEquals(JsonColumns.MD5, value);
        return this;
    }

    public JsonSelection md5Not(String... value) {
        addNotEquals(JsonColumns.MD5, value);
        return this;
    }

    public JsonSelection md5Like(String... value) {
        addLike(JsonColumns.MD5, value);
        return this;
    }

    public JsonSelection md5Contains(String... value) {
        addContains(JsonColumns.MD5, value);
        return this;
    }

    public JsonSelection md5StartsWith(String... value) {
        addStartsWith(JsonColumns.MD5, value);
        return this;
    }

    public JsonSelection md5EndsWith(String... value) {
        addEndsWith(JsonColumns.MD5, value);
        return this;
    }

    public JsonSelection orderByMd5(boolean desc) {
        orderBy(JsonColumns.MD5, desc);
        return this;
    }

    public JsonSelection orderByMd5() {
        orderBy(JsonColumns.MD5, false);
        return this;
    }
}
