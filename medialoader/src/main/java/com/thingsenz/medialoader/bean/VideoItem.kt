package com.thingsenz.medialoader.bean

class VideoItem : BaseItem {
    var duration: Long = 0
    var isChecked = false

    constructor() {}
    constructor(
        id: Int,
        displayName: String?,
        path: String?,
        size: Long,
        modified: Long,
        duration: Long
    ) : super(id, displayName, path, size, modified) {
        this.duration = duration
    }
}