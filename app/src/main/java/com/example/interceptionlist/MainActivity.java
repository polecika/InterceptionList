package com.example.interceptionlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Генератор случайностей
    private Random random = new Random();
    // Наш адаптер
    private ItemDataAdapter adapter;
    // Список картинок, которые мы будем брать для нашего списка
    private List<Drawable> images = new ArrayList<>();
    String[] arrayText;
    private static int arrayPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        ListView listView = findViewById(R.id.listView);

        setSupportActionBar(toolbar);

        fillImages();

        arrayText = getString(R.string.large_text).split("\n\n");
        // При тапе по кнопке добавим один новый элемент списка
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateRandomItemData();
            }
        });

        // Создаем и устанавливаем адаптер на наш список
        adapter = new ItemDataAdapter(this, null);
        listView.setAdapter(adapter);

        

        // При долгом тапе по элементу списка будем удалять его
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                showItemData(i);
                return true;
            }
        });}

        // Заполним различными картинками, которые встроены в сам Android
        // ContextCompat обеспечит нам поддержку старых версий Android
        private void fillImages() {
            images.add(ContextCompat.getDrawable(MainActivity.this,
                    android.R.drawable.ic_menu_report_image));
            images.add(ContextCompat.getDrawable(MainActivity.this,
                    android.R.drawable.ic_menu_add));
            images.add(ContextCompat.getDrawable(MainActivity.this,
                    android.R.drawable.ic_menu_agenda));
            images.add(ContextCompat.getDrawable(MainActivity.this,
                    android.R.drawable.ic_menu_camera));
            images.add(ContextCompat.getDrawable(MainActivity.this,
                    android.R.drawable.ic_menu_call));
        }

        // Создадим ну почти случайные данные для нашего списка.
        // random.nextInt(граница_последнего_элемента)
        // Для каждого элемента мы возьмем 1 случайную картинку
        // из 5, которые мы сделали вначале.
        private void generateRandomItemData() {
            arrayPosition = random.nextInt(arrayText.length - 1);
            adapter.addItem(new ItemData(
                    images.get(random.nextInt(images.size())),
                    arrayText[arrayPosition],
                    "" + arrayText[arrayPosition].length()));
        }

        // Покажем сообщение с данными
        private void showItemData(int position){
            ItemData itemData = adapter.getItem(position);
            Toast.makeText(MainActivity.this,
                    "Title: " + itemData.getTitle() + "\n" +
                            "Subtitle: " + itemData.getSubtitle(),
                    Toast.LENGTH_SHORT).show();
        }
    }