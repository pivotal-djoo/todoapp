package com.example.todo.persistence;

import android.content.Context;

public interface PersistenceProvider {
    String getString(Context context, String name);
    void storeString(Context context, String name, String value);
}
