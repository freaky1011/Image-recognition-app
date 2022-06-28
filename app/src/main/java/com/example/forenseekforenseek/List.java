package com.example.forenseekforenseek;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class UserShowList extends AppCompatActivity {
    ListView bene_listView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter2;
    UserHelperClass helperClass;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        searchView = findViewById(R.id.search);

        bene_listView = findViewById(R.id.recview);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("People");
        arrayList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_adddata, arrayList);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    helperClass = ds.getValue(UserHelperClass.class);
                    arrayList.add(helperClass.getID() + "\t\t" + helperClass.getName());
                }

                bene_listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}