package com.thingsenz.medialoader.bean

class VideoResult : BaseResult {
    var folders: List<VideoFolder>? = null
    var items: List<VideoItem>? = null

    constructor() {}
    constructor(folders: List<VideoFolder>?, items: List<VideoItem>?) {
        this.folders = folders
        this.items = items
    }

    constructor(folders: List<VideoFolder>?, items: List<VideoItem>?, totalSize: Long) : super(
        totalSize
    ) {
        this.folders = folders
        this.items = items
    }
}