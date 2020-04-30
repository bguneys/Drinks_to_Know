package com.bguneys.project.softwareterms.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_NAME)
public class Item {

    @ColumnInfo(name = Constants.COLUMN_ITEM_ID)
    @PrimaryKey
    private int mItemId;

    @ColumnInfo(name = Constants.COLUMN_ITEM_NAME)
    private String mItemName;

    @ColumnInfo(name = Constants.COLUMN_ITEM_SUMMARY)
    private String mItemSummary;

    @ColumnInfo(name = Constants.COLUMN_ITEM_DESCRIPTION)
    private String mItemDescription;

    @ColumnInfo(name = Constants.COLUMN_ITEM_IMAGE)
    private int mItemImage;

    @ColumnInfo(name = Constants.COLUMN_ITEM_FAVOURITE)
    private Boolean mItemFavourite;

    @ColumnInfo(name = Constants.COLUMN_ITEM_SOURCE_TEXT)
    private String mItemSourceText;

    @ColumnInfo(name = Constants.COLUMN_ITEM_SOURCE_TEXT_URL)
    private String mItemSourceTextUrl;

    @ColumnInfo(name = Constants.COLUMN_ITEM_SOURCE_IMAGE)
    private String mItemSourceImage;

    @ColumnInfo(name = Constants.COLUMN_ITEM_SOURCE_IMAGE_URL)
    private String mItemSourceImageUrl;

    @ColumnInfo(name = Constants.COLUMN_ITEM_GROUP)
    private String mItemGroup;

    //Constructors
    @Ignore
    public Item(@NonNull String name,
                @NonNull String summary,
                @NonNull String description,
                int image, boolean favourite,
                String itemSourceText,
                String itemSourceTextUrl,
                String itemSourceImage,
                String itemSourceImageUrl,
                String itemGroup) {

        this.mItemName = name;
        this.mItemSummary = summary;
        this.mItemDescription = description;
        this.mItemImage = image;
        this.mItemFavourite = favourite;
        this.mItemSourceText = itemSourceText;
        this.mItemSourceTextUrl = itemSourceTextUrl;
        this.mItemSourceImage = itemSourceImage;
        this.mItemSourceImageUrl = itemSourceImageUrl;
        this.mItemGroup = itemGroup;
    }

    public Item(int mItemId,
                @NonNull String mItemName,
                @NonNull String mItemSummary,
                @NonNull String mItemDescription,
                int mItemImage,
                boolean mItemFavourite,
                String mItemSourceText,
                String mItemSourceTextUrl,
                String mItemSourceImage,
                String mItemSourceImageUrl,
                String mItemGroup) {

        this.mItemId = mItemId;
        this.mItemName = mItemName;
        this.mItemSummary = mItemSummary;
        this.mItemDescription = mItemDescription;
        this.mItemImage = mItemImage;
        this.mItemFavourite = mItemFavourite;
        this.mItemSourceText = mItemSourceText;
        this.mItemSourceTextUrl = mItemSourceTextUrl;
        this.mItemSourceImage = mItemSourceImage;
        this.mItemSourceImageUrl = mItemSourceImageUrl;
        this.mItemGroup = mItemGroup;
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

    public String getItemSummary() {
        return mItemSummary;
    }

    public void setItemSummary(String mItemSummary) {
        this.mItemSummary = mItemSummary;
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

    public String getItemSourceText() {
        return mItemSourceText;
    }

    public void setItemSourceText(String mItemSourceText) {
        this.mItemSourceText = mItemSourceText;
    }

    public String getItemSourceTextUrl() {
        return mItemSourceTextUrl;
    }

    public void setItemSourceTextUrl(String mItemSourceTextUrl) {
        this.mItemSourceTextUrl = mItemSourceTextUrl;
    }

    public String getItemSourceImage() {
        return mItemSourceImage;
    }

    public void setItemSourceImage(String mItemSourceImage) {
        this.mItemSourceImage = mItemSourceImage;
    }

    public String getItemSourceImageUrl() {
        return mItemSourceImageUrl;
    }

    public void setItemSourceImageUrl(String mItemSourceImageUrl) {
        this.mItemSourceImageUrl = mItemSourceImageUrl;
    }

    public String getItemGroup() {
        return mItemGroup;
    }

    public void setItemGroup(String mItemGroup) {
        this.mItemGroup = mItemGroup;
    }
}
