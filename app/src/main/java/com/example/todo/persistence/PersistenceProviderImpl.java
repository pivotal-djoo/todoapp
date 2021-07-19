package com.example.todo.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PersistenceProviderImpl implements PersistenceProvider {
    @Override
    public String getString(Context context, String name) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sharedPreferences.getString(name, null);
    }

    @Override
    public void storeString(Context context, String name, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(name, value);
        myEdit.apply();
    }
}
