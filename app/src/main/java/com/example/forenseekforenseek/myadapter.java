package com.example.forenseekforenseek;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.forenseekforenseek.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<Model,myadapter.myviewholder>
{
    ArrayList<Model>Model = new ArrayList<>();


    public myadapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder,int position, @NonNull Model Model)
    {
       holder.name.setText(Model.getName());
        holder.record.setText(Model.getRecord());
        holder.ID.setText(Model.getID());
        holder.Email.setText(Model.getEmail());
        holder.Address.setText(Model.getAddress());

       //Glide.with(holder.img1.getContext()).load(Model.geturl()).into(holder.img1);

        holder.medit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1100)
                        .create();

                View myview=dialogPlus.getHolderView();

                final EditText purl=myview.findViewById(R.id.uid);
                final EditText name=myview.findViewById(R.id.uname);
                final EditText record =myview.findViewById(R.id.uemail);
                final EditText email=myview.findViewById(R.id.uemail1);
                final EditText address=myview.findViewById(R.id.uaddress);
                Button submit=myview.findViewById(R.id.submit);


                purl.setText(Model.getID());
                name.setText(Model.getName());
                record.setText(Model.getRecord());
                email.setText(Model.getEmail());
                address.setText(Model.getRecord());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("ID",purl.getText().toString());
                        map.put("Name",name.getText().toString());
                        map.put("Record",record.getText().toString());
                        map.put("Email",email.getText().toString());
                        map.put("Address",address.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("People")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }

                });



            }


        });


        holder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delete Item");
                builder.setMessage("Are you sure you want to delete this item");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("People")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list,parent,false);
        return new myviewholder(view);
    }

    public void filterlist(ArrayList<Model> filteredlist) {
        Model = filteredlist;
    }


    public class myviewholder extends RecyclerView.ViewHolder
    {
        //ImageView img1;
        TextView name,ID,record,Email,Address;
        ImageView medit,mdelete;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
           // img1= (ImageView) itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.Name);
            ID=(TextView)itemView.findViewById(R.id.ID);
            Email=(TextView)itemView.findViewById(R.id.Email);
            Address=(TextView)itemView.findViewById(R.id.Address);
            record=(TextView)itemView.findViewById(R.id.Record);
            medit=(ImageView)itemView.findViewById(R.id.medit);
            mdelete=(ImageView)itemView.findViewById(R.id.mdelete);
        }
    }
}
