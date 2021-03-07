package com.thingsenz.medialoader.bean

class FileResult: BaseResult {

    var items: List<FileItem>? = null

    constructor() {}
    constructor(totalSize: Long,items: List<FileItem>?):super(totalSize) {this.items=items}


}