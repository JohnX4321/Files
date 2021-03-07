package com.thingsenz.medialoader.bean

class AudioResult : BaseResult {
    var items: List<AudioItem>? = null

    constructor() {}
    constructor(items: List<AudioItem>?) {
        this.items = items
    }

    constructor(totalSize: Long, items: List<AudioItem>?) : super(totalSize) {
        this.items = items
    }
}