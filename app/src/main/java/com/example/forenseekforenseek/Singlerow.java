package com.example.forenseekforenseek;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import android.app.SearchManager;
import android.widget.SearchView.OnQueryTextListener;

import java.util.ArrayList;

public class Singlerow extends AppCompatActivity {
    RecyclerView recview;
    myadapter adapter;
    FloatingActionButton fb;

    ImageView Search;
    DatabaseReference myRef;
    ArrayList<Model>Model= new ArrayList<>();

    //private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlerow);

        recview = (RecyclerView) findViewById(R.id.recview);
//       RecyclerView.LayoutManager manager = new LinearLayoutManager(Singlerow.this);
      //  recview.setLayoutManager(manager);

       recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("People"), Model.class)
                        .build();

        adapter = new myadapter(options);
        recview.setAdapter(adapter);
        //final EditText searchquery = (EditText) findViewById(R.id.searchquery);
       // Search = (ImageView) findViewById(R.id.search);
//        myRef = FirebaseDatabase.getInstance().getReference("People");
//        myRef.keepSynced(true);
//
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                try {
//                        String ID = snapshot.child("ID").getValue(String.class).toUpperCase();
//                        String Name = snapshot.child("Name").getValue(String.class).toUpperCase();
//                        String Record = snapshot.child("Record").getValue(String.class).toUpperCase();
//
//                        Model list = new Model();
//
//                        list.setID(ID);
//                        list.setName(Name);
//                        list.setRecord(Record);
//
//                        Model.add(list);
//                        if (Model.size()>0){
//                            recview.setAdapter(adapter);
//                        }
//
//                }catch (Exception e){}
//
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Singlerow.this, "Error", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//       // final EditText searchquery = (EditText) findViewById(R.id.searchquery);
//        //Search = (ImageButton) findViewById(R.id.search);
//
//
////       //Search.setOnClickListener(new View.OnClickListener() {
////           // @Override
////           // public void onClick(View v) {
////             //   String s = searchquery.getText().toString();
////                //if (s.isEmpty()) {
////                    Toast.makeText(Singlerow.this, "No Result", Toast.LENGTH_SHORT).show();
////                } else {
////                    filter(s);
////                }
////            }
////        });


        fb = (FloatingActionButton) findViewById(R.id.fadd);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), adddata.class));

            }
        });
    }

//    private void filter(String s) {
//        ArrayList<Model> filteredlist=new ArrayList<>();
//        for(Model item :Model){
//            if (item.getID().toLowerCase().contains(s.toLowerCase()) ){
//                filteredlist.add(item);
//            }
//            else if (item.getName().toLowerCase().contains(s.toLowerCase()) ){
//                filteredlist.add(item);
//            }
//            else if (item.getRecord().toLowerCase().contains(s.toLowerCase()) ){
//                filteredlist.add(item);
//            }
//        }
//        adapter.filterlist(filteredlist);
//    }






    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mainmenu,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processsearch(s);
                return false;
            }





            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("People")
                                .orderByChild("ID").startAt(s).endAt(s+"\uf8ff"),
                                Model.class).build();





        adapter=new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }






}

