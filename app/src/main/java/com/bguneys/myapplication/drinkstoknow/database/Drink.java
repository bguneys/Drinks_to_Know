package com.bguneys.myapplication.drinkstoknow.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME)
public class Drink {

    @ColumnInfo(name = Constants.COLUMN_ITEM_ID)
    @PrimaryKey(autoGenerate = true)
    private int mItemId;

    @ColumnInfo(name = Constants.COLUMN_ITEM_NAME)
    private String mItemName;

    @ColumnInfo(name = Constants.COLUMN_ITEM_DESCRIPTION)
    private String mItemDescription;

    @ColumnInfo(name = Constants.COLUMN_ITEM_FAVOURITE)
    private Boolean mItemFavourite;

    public Drink(int id, @NonNull String name, @NonNull String description, boolean favourite) {
        this.mItemId = id;
        this.mItemName = name;
        this.mItemDescription = description;
        this.mItemFavourite = favourite;
    }

    //Getters & Setters
    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int mItemId) {
        this.mItemId = mItemId;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String mItemName) {
        this.mItemName = mItemName;
    }

    public String getItemDescription() {
        return mItemDescription;
    }

    public void setItemDescription(String mItemDescription) {
        this.mItemDescription = mItemDescription;
    }

    public Boolean getItemFavourite() {
        return mItemFavourite;
    }

    public void setItemFavourite(Boolean mItemFavourite) {
        this.mItemFavourite = mItemFavourite;
    }
}
