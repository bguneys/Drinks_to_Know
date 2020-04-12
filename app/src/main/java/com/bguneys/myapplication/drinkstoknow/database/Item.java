package com.bguneys.myapplication.drinkstoknow.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME)
public class Item {

    @ColumnInfo(name = Constants.COLUMN_ITEM_ID)
    @PrimaryKey(autoGenerate = true)
    private int mItemId;

    @ColumnInfo(name = Constants.COLUMN_ITEM_NAME)
    private String mItemName;

    @ColumnInfo(name = Constants.COLUMN_ITEM_DESCRIPTION)
    private String mItemDescription;

    @ColumnInfo(name = Constants.COLUMN_ITEM_IMAGE)
    private int mItemImage;

    @ColumnInfo(name = Constants.COLUMN_ITEM_FAVOURITE)
    private Boolean mItemFavourite;

    //Constructors
    @Ignore
    public Item(@NonNull String name, @NonNull String description, int image, boolean favourite) {
        this.mItemName = name;
        this.mItemDescription = description;
        this.mItemImage = image;
        this.mItemFavourite = favourite;
    }

    public Item(int mItemId, @NonNull String mItemName, @NonNull String mItemDescription, int mItemImage, boolean mItemFavourite) {
        this.mItemId = mItemId;
        this.mItemName = mItemName;
        this.mItemDescription = mItemDescription;
        this.mItemImage = mItemImage;
        this.mItemFavourite = mItemFavourite;
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

    public int getItemImage() {
        return mItemImage;
    }

    public void setItemImage(int mItemImage) {
        this.mItemImage = mItemImage;
    }

    public Boolean isItemFavourite() {
        return mItemFavourite;
    }

    public void setItemFavourite(Boolean mItemFavourite) {
        this.mItemFavourite = mItemFavourite;
    }
}
