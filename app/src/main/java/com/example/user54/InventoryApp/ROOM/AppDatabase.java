package com.example.user54.InventoryApp.ROOM;



import android.content.ContentUris;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.user54.InventoryApp.Model.ActiveTable;
import com.example.user54.InventoryApp.Model.AssestItem;
import com.example.user54.InventoryApp.Model.AssestItem_backup;
import com.example.user54.InventoryApp.Model.AssestItem_info;
import com.example.user54.InventoryApp.Model.CurrencyName;
import com.example.user54.InventoryApp.Model.ItemCard;
import com.example.user54.InventoryApp.Model.ItemInfo;
import com.example.user54.InventoryApp.Model.ItemInfoBackUp;
import com.example.user54.InventoryApp.Model.ItemInfoExp;
import com.example.user54.InventoryApp.Model.ItemInfoExpRec;
import com.example.user54.InventoryApp.Model.ItemQR;
import com.example.user54.InventoryApp.Model.ItemQty;
import com.example.user54.InventoryApp.Model.ItemSwitch;
import com.example.user54.InventoryApp.Model.ItemUnit;
import com.example.user54.InventoryApp.Model.MainSetting;
import com.example.user54.InventoryApp.Model.Order;
import com.example.user54.InventoryApp.Model.Password;
import com.example.user54.InventoryApp.Model.SewooSetting;
import com.example.user54.InventoryApp.Model.Stk;
import com.example.user54.InventoryApp.Model.TransferItemsInfo;
import com.example.user54.InventoryApp.Model.TransferVhfSerial;
import com.example.user54.InventoryApp.Model.UnitName;
import com.example.user54.InventoryApp.controll;


@Database(entities = {ItemCard.class, ItemSwitch.class, ItemUnit.class, AssestItem.class,
        AssestItem_backup.class,
        AssestItem_info.class,
        ItemInfoExpRec.class,
        ItemInfoExp.class,
        ItemQty.class,
        ItemInfo.class,
        Order.class,
        Password.class,
        SewooSetting.class,
        Stk.class,
        MainSetting.class,
        TransferItemsInfo.class,
        TransferVhfSerial.class,
        ItemInfoBackUp.class,
        ItemQR.class,
        CurrencyName.class,
        ActiveTable.class,
        UnitName.class


}, version =  102 , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDaoCard itemCard();

    public abstract UserDaoSwitch itemSwitch();
    public abstract UserDaoUnit itemUnit();
    public abstract UserDaoAssesst itemAssest();
    public abstract UserDaoAssesst_info itemAssestInfo();
    public abstract UserDaoAssesst_infoBackup itemAssestInfoBackup();
    public abstract UserDaoInfoExp itemInfoExp();
    public abstract UserDaoInfoExpRec itemInfoExpRec();
    public abstract UserDaoItemQty itemQty();

    public abstract UserDaoItemInfo itemInfo();
    public abstract UserDaoOrder order();
    public abstract UserDaoPassword password();
    public abstract UserDaoStkSewoo stkSewoo();
    public abstract UserDaoStk stk();
    public abstract UserDaoMainSetting mainSetting();
    public abstract UserDaoTrancfer trancfer();
    public abstract UserDaoTrancferSerial trancferSerial();
    public abstract UserDaoItemInfoBackUp itemInfoBackUp();
    public abstract UserDaoQR itemQR();
    public abstract UserDaoCurrency currency();

    public abstract UserDaoActivity activity();
    public abstract UserDaoUnitName unitName();



    private static AppDatabase InstanceDatabase;
    private static String DatabaseName="InventoryDBase";


    static final Migration FROM_1_TO_2 = new Migration(99, 102) {
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

