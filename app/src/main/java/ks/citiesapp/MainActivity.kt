package ks.citiesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import ks.citiesapp.data.repository.database.AppDatabase
import ks.citiesapp.ui.screen.main.MainScreen
import ks.citiesapp.ui.theme.CitiesAppTheme


class MainActivity : ComponentActivity() {

    lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

        setContent {
            CitiesAppTheme {
                MainScreen(database = database)
            }
        }
    }
}