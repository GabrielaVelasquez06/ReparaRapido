package com.example.repararapido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.repararapido.Model.Personal;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PersonalDetail extends AppCompatActivity {

    TextView personal_nombre,personal_horario,personal_descripcion;
    ImageView personal_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnSolicitar;
    String menuId="";
    FirebaseDatabase database;
    DatabaseReference personal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_detail);


        database = FirebaseDatabase.getInstance();
        personal = database.getReference("Personal");

        btnSolicitar = findViewById(R.id.btnSolicitar);
        personal_descripcion = findViewById(R.id.personal_descripcion);
        personal_nombre = findViewById(R.id.personal_name);
        personal_horario = findViewById(R.id.personal_horario);
        personal_image = findViewById(R.id.img_personal);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent() != null)
            menuId = getIntent().getStringExtra("MenuId");
        if(!menuId.isEmpty()){
            getDetailPersonal(menuId);
        }

    }

    private void getDetailPersonal(String menuId) {

        personal.child(menuId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Personal personal = dataSnapshot.getValue(Personal.class);
                Picasso.get().load(personal.getImage()).into(personal_image);
                personal_descripcion.setText(personal.getDescripcion());
                personal_horario.setText(personal.getHorario());
                personal_nombre.setText(personal.getName());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}