package com.linh.cryptoviewer2.data.local.dao

import com.linh.cryptoviewer2.data.local.model.WatchlistItemLocal
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchlistDaoImpl @Inject constructor(private val realm: Realm): WatchlistDao {
    override suspend fun insert(watchlistItemLocal: WatchlistItemLocal) {
        realm.write {
            val item = query<WatchlistItemLocal>("coinId = $0", watchlistItemLocal.coinId).first().find()

            if (item == null) {
                copyToRealm(watchlistItemLocal)
            }
        }
    }

    override suspend fun getAll(): List<WatchlistItemLocal> {
        return realm.query<WatchlistItemLocal>().find()
    }

    override suspend fun delete(coinId: String) {
        realm.write {
            val writeTask = query<WatchlistItemLocal>("coinId == $0", coinId).find()
            delete(writeTask.first())
        }
    }
}