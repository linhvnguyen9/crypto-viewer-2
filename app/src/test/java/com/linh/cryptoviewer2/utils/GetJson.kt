package com.linh.cryptoviewer2

import java.io.File

/**
 * Helper function which will load JSON from
 * the path specified
 *
 * @param path : Path of JSON file
 * @return json : JSON from file at given path
 */
fun Any.getJson(path : String) : String {
    // Load the JSON response
    val uri = this.javaClass.classLoader.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}