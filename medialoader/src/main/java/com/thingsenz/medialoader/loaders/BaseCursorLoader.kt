package com.thingsenz.medialoader.loaders

import android.content.Context
import android.content.CursorLoader
import com.thingsenz.medialoader.inter.ILoader

class BaseCursorLoader(context: Context,iLoader: ILoader): CursorLoader(context) {

    init {
        projection=iLoader.getSelectProjection()
        uri=iLoader.getQueryUri()
        selection=iLoader.getSelections()
        selectionArgs=iLoader.getSelectionArgs()
        sortOrder=iLoader.getSortOrderSql()
    }

}