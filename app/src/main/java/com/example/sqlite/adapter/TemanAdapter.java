package com.example.sqlite.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.EditData;
import com.example.sqlite.LihatData;
import com.example.sqlite.MainActivity;
import com.example.sqlite.R;
import com.example.sqlite.database.DBController;
import com.example.sqlite.database.Teman;
import com.google.android.material.transition.Hold;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listdata;

    public TemanAdapter(ArrayList<Teman> listdata) {
        this.listdata = listdata;
    }

    @Override
    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View view = layoutInf.inflate(R.layout.rowdatateman,parent,false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemanViewHolder holder, int position) {
        String nm,tlp;

        nm = listdata.get(position).getNama();
        tlp = listdata.get(position).getTelpon();

        holder.namaTxt.setTextColor(Color.BLUE);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nm);
        holder.telponTxt.setText(tlp);
    }

    @Override
    public int getItemCount() { return (listdata != null)?listdata.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt,telponTxt;
        private Context context;
        String id, nama, telpon;
        Bundle bundle = new Bundle();
        DBController controller;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);
            context = itemView.getContext();
            controller = new DBController(context);

            cardku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nama = listdata.get(getAdapterPosition()).getNama();
                    telpon = listdata.get(getAdapterPosition()).getTelpon();

                    bundle.putString("nama", nama.trim());
                    bundle.putString("telpon", telpon.trim());

                    Intent intent = new Intent(context, LihatData.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            cardku.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    PopupMenu pop = new PopupMenu(context, view);
                    pop.inflate(R.menu.popup_menu);

                    pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mnedit:
                                    id = listdata.get(getAdapterPosition()).getId();
                                    nama = listdata.get(getAdapterPosition()).getNama();
                                    telpon = listdata.get(getAdapterPosition()).getTelpon();

                                    bundle.putString("id", id.trim());
                                    bundle.putString("nama", nama.trim());
                                    bundle.putString("telpon", telpon.trim());

                                    Intent intent = new Intent(context, EditData.class);
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                    break;
                                case R.id.mndelete:
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Hapus Data");
                                    builder.setMessage("Apakah anda yakin ingin menghapus kontak ini?");
                                    builder.setCancelable(true);
                                    builder.setPositiveButton("Ya",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    id = listdata.get(getAdapterPosition()).getId();
                                                    controller.hapusData(id);

                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            }
                                    );
                                    builder.setNegativeButton("Batal",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            }
                                    );
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    break;
                            }
                            return false;
                        }
                    });
                    pop.show();
                    return false;
                }
            });
        }
    }
}
