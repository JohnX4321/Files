package com.thingsenz.medialoader.bean

class PhotoResult : BaseResult {
    var folders: List<PhotoFolder>? = null
    var items: List<PhotoItem>? = null

    constructor() {}
    constructor(folders: List<PhotoFolder>?, items: List<PhotoItem>?) {
        this.folders = folders
        this.items = items
    }

    constructor(folders: List<PhotoFolder>?, items: List<PhotoItem>?, totalSize: Long) : super(
        totalSize
    ) {
        this.folders = folders
        this.items = items
    }
}