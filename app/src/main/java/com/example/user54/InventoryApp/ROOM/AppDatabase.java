package com.example.user54.InventoryApp.ROOM;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemSwitch;
import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.controll;


@Database(entities = {ItemCard.class, ItemSwitch.class, ItemUnit.class}, version =  98 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDaoCard itemCard();

    public abstract UserDaoSwitch itemSwitch();
    public abstract UserDaoUnit itemUnit();

    private static AppDatabase InstanceDatabase;
    private static String DatabaseName="InventoryDBase";


    static final Migration FROM_1_TO_2 = new Migration(54, 55) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE Repo
//                    ADD COLUMN createdAt TEXT");
        }
    };

    public static synchronized AppDatabase getInstanceDatabase(Context context) {



        if (InstanceDatabase == null) {

            InstanceDatabase = Room.databaseBuilder(context, AppDatabase.class, DatabaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                   .fallbackToDestructiveMigrationFrom(36, 37, 38, 39,40)
                    .build();

        }

        return InstanceDatabase;

    }

}

