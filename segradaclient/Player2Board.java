package com.example.segradaclient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

//This class pupulate the image butttons to create  segrada pattern board.
public class Player2Board extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    //Iinstatiate all the image buttons in the list to be used to access the fragments.
    ImageButton img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img15,img16,img17,img18,img19,img20;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_p2_board,container,false);

        // Inflate the layout for this fragment
        setUpBoard(v);

        return v;
    }

    public void setUpBoard(View view){
        img1 = view.findViewById(R.id.imBtn1);
        img2 =  view.findViewById(R.id.imBtn2);
        img3 =  view.findViewById(R.id.imBtn3);
        img4 =  view.findViewById(R.id.imBtn4);
        img5 = view.findViewById(R.id.imBtn5);
        img6 =  view.findViewById(R.id.imBtn6);
        img7 =  view.findViewById(R.id.imBtn7);
        img8 = view.findViewById(R.id.imBtn8);
        img9 = view.findViewById(R.id.imBtn9);
        img10 =view.findViewById(R.id.imBtn10);
        img11 =  view.findViewById(R.id.imBtn11);
        img12 =  view.findViewById(R.id.imBtn12);
        img13= view.findViewById(R.id.imBtn13);
        img14 =  view.findViewById(R.id.imBtn14);
        img15 = view.findViewById(R.id.imBtn15);
        img16 = view.findViewById(R.id.imBtn16);
        img17 =  view.findViewById(R.id.imBtn17);
        img18 =  view.findViewById(R.id.imBtn18);
        img19 =view.findViewById(R.id.imBtn19);
        img20 = view.findViewById(R.id.imBtn20);

        img1.setImageDrawable(getResources().getDrawable(R.color.grey));
        img2.setImageDrawable(getResources().getDrawable(R.drawable.blue6));
        img3.setImageDrawable(getResources().getDrawable(R.color.purple));
        img4.setImageDrawable(getResources().getDrawable(R.drawable.two));
        img5.setImageDrawable(getResources().getDrawable(R.drawable.yellow4));

        img6.setImageDrawable(getResources().getDrawable(R.color.yellow));
        img7.setImageDrawable(getResources().getDrawable(R.drawable.green6));
        img8.setImageDrawable(getResources().getDrawable(R.drawable.purple5));
        img9.setImageDrawable(getResources().getDrawable(R.color.grey));
        img10.setImageDrawable(getResources().getDrawable(R.color.green));

        img11.setImageDrawable(getResources().getDrawable(R.color.grey));
        img12.setImageDrawable(getResources().getDrawable(R.drawable.red2));
        img13.setImageDrawable(getResources().getDrawable(R.drawable.red6));
        img14.setImageDrawable(getResources().getDrawable(R.drawable.yellow4));
        img15.setImageDrawable(getResources().getDrawable(R.color.grey));

        img16.setImageDrawable(getResources().getDrawable(R.color.green));
        img17.setImageDrawable(getResources().getDrawable(R.drawable.six));
        img18.setImageDrawable(getResources().getDrawable(R.color.yellow));
        img19.setImageDrawable(getResources().getDrawable(R.drawable.three));
        img20.setImageDrawable(getResources().getDrawable(R.color.purple));
    }



}
