package com.thingsenz.medialoader.bean

class PhotoItem: BaseItem {

    var checked: Boolean = false
    constructor() {}
    constructor(id: Int,displayName: String?,path: String?): super(id, displayName, path)
    constructor(id: Int,displayName: String?,path: String?,size: Long): super(id, displayName, path, size)
    constructor(id: Int,displayName: String?,path: String?,size: Long,modified: Long): super(id, displayName, path, size, modified)

}