package br.com.chicorialabs.astranovos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.chicorialabs.astranovos.data.dao.PostDao
import br.com.chicorialabs.astranovos.data.entities.db.LaunchDb
import br.com.chicorialabs.astranovos.data.entities.db.PostDb
import br.com.chicorialabs.astranovos.data.entities.db.PostDbConverters

/**
 * Essa classe abstrata declara uma database e possui a organização
 * recomendada pela Google. A implementação concreta
 * também fica sob responsabilidade do Room.
 */
@Database(
    entities = [PostDb::class, LaunchDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PostDbConverters::class)
abstract class PostDatabase : RoomDatabase(){
    abstract val dao: PostDao

    companion object {

        @Volatile
        private var INSTANCE: PostDatabase? = null

        fun getInstance(context: Context) : PostDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java,
                        "post_cache_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}