package com.dkproject.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name="user_token")

class UserDataStore @Inject constructor(@ApplicationContext private val context: Context){
    companion object{
        private val KEY_TOKEN = stringPreferencesKey("token")
    }

    suspend fun setToken(token:String){
        context.dataStore.edit {mutablePreferences ->
            mutablePreferences[KEY_TOKEN]=token
        }
    }

    suspend fun getToken():String?{
        return context.dataStore.data.map {preferences->
            preferences[KEY_TOKEN]
        }.firstOrNull()
    }

    suspend fun clearToken(){
        context.dataStore.edit {
            it.clear()
        }
    }
}

