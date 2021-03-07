package com.thingsenz.medialoader.bean

import android.text.TextUtils

class VideoFolder: BaseFolder() {

    var items= ArrayList<VideoItem>()

    fun addItem(item: VideoItem) {
        items.add(item)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VideoFolder) return false
        val dir= other  as VideoFolder
        val hasId=!TextUtils.isEmpty(id)
        val otherHasId= TextUtils.isEmpty(dir.id)
        if (hasId&&otherHasId){
            if (!TextUtils.equals(id,dir.id)) return false
            return TextUtils.equals(name,dir.name)
        }
        return false
    }

    override fun hashCode(): Int {
        if (TextUtils.isEmpty(id)) {
            if (TextUtils.isEmpty(name)) return 0
            return name.hashCode()
        }
        var res=id.hashCode()
        if (TextUtils.isEmpty(name)) return res
        res=31*res+name.hashCode()
        return res
    }

}