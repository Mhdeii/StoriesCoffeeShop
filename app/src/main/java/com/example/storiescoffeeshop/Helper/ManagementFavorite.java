package com.example.storiescoffeeshop.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.storiescoffeeshop.Domain.Items;

import java.util.ArrayList;

public class ManagementFavorite {
    private Context context;
    private TinyDB tinyDB;

    public ManagementFavorite(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertItem(Items item) {
        ArrayList<Items> listpop = getListFavorite();
        boolean existAlready = false;
        for (Items favoriteItem : listpop) {
            if (favoriteItem.getTitle().equals(item.getTitle())) {
                existAlready = true;
                break;
            }
        }
        if (!existAlready) {
            listpop.add(item);
            tinyDB.putListObject("FavoriteList", listpop);
            Toast.makeText(context, "Added to your Favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Item is already in your Favorites", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<Items> getListFavorite() {
        return tinyDB.getListObject("FavoriteList");
    }

    public void removeItem(ArrayList<Items> listItem, int position) {
        listItem.remove(position);
        tinyDB.putListObject("FavoriteList", listItem);
    }

    public void clearFavorites() {
        ArrayList<Items> emptyList = new ArrayList<>();
        tinyDB.putListObject("FavoriteList", emptyList);
        Toast.makeText(context, "Favorites Cleared", Toast.LENGTH_SHORT).show();
    }
}
