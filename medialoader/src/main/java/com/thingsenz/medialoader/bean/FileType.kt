package com.thingsenz.medialoader.bean

import com.thingsenz.medialoader.config.FileLoaderConfig

enum class FileType(var property: FileProperty) {

    DOC(FileProperty(null,FileLoaderConfig.documentMIME)),
    APK(FileProperty(FileLoaderConfig.apkExtension,null)),
    ZIP(FileProperty(FileLoaderConfig.zipExtension,null));

}