package com.linh.cryptoviewer2.data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class WatchlistItemLocal : RealmObject {
    @PrimaryKey
    var coinId: String = ""
}