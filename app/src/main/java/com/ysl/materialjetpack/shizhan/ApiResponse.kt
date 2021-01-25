package com.ysl.materialjetpack.shizhan

class ApiResponse<T>(data: T?, errorCode: Int, message: String) {
    var data : T? = null
    var errorCode : Int = 0
    var message : String = ""
}