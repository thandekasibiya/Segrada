package com.example.segradaclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.example.segradaclient.Game.PoolDice;

 // An adapter for the die in the pool

public class DiePoolAdapter extends RecyclerView.Adapter<DiePoolAdapter.ViewHolder> {
    private ActivityAListener listener;
    //to use to access the board's dice
    private Game gameActivity;


    static public ArrayList recyclerViewList =new ArrayList<>();
    private int clickedCount=0;
    private List<Dice> diceList;
    Activity activity;

    /**
     * @param diceList
     * @param activity
     */
    public DiePoolAdapter(List<Dice> diceList, Activity activity) {
        this.diceList = diceList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.die_item_list,parent,false);
        ViewHolder holder=new ViewHolder(view);

        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.imageButton.setImageDrawable(diceList.get(position).getImage());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedPoolDie(position);
                clickedCount++;
            }
        });

    }

    @Override
    public int getItemCount() {
        return diceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageButton imageButton;
        ConstraintLayout Layout;
        public ViewHolder( @NonNull View itemView) {
            super(itemView);

            imageButton=itemView.findViewById(R.id.dieList);
            Layout=itemView.findViewById(R.id.ConstraintLayout);
        }
    }

    /**
     * @param pos
     */
    public void SelectedPoolDie(final int pos) {
        if (clickedCount/2==0){}
        final Dice die = PoolDice.get(pos);
        die.setSelected();
        AlertDialog.Builder a_builder=new AlertDialog.Builder(activity);
        a_builder.setMessage("Is that the die you want to place in your window?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recyclerViewList.add(die);
                        PoolDice.remove(pos);
                        notifyDataSetChanged();
                       gameActivity =(Game) activity;
                       gameActivity.placeDieOnCell(die);

                    }
                })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                die.isSelected=false;
                dialog.cancel();
                Toast.makeText(activity,"Choose another die", Toast.LENGTH_SHORT).show();
            }
        })
        ;
        AlertDialog alertDialog =a_builder.create();
        alertDialog.setTitle("DICE");
        alertDialog.show();

        Toast.makeText(activity,"Placed in the window", Toast.LENGTH_SHORT).show();
    }



    public interface ActivityAListener{
        void onInputASent(ArrayList input);
    }



}
