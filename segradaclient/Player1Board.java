package com.example.segradaclient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//This class pupulate the image butttons to create  segrada pattern board.
public class Player1Board extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    int turn =0;
    //Declare the list to house all the blocks
    List<RowColumn> rowColumnList =new ArrayList<>();
    //Iinstatiate all the image buttons in the list to be used to access the fragments.
    ImageButton img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img15,img16,img17,img18,img19,img20;

    public void addToBlock(){

        Cell cell1 = new Cell("blank",img1.getDrawable().getCurrent(),0,0, (String) img1.getTag(),6,false);
        Cell cell2 = new Cell("blue",img2.getDrawable().getCurrent(),0,1, (String) img2.getTag(),0,false);
        Cell cell3 = new Cell("blank",img3.getDrawable().getCurrent(),0,2, (String) img3.getTag(),0,false);
        Cell cell4 = new Cell("blank",img4.getDrawable().getCurrent(),0,3, (String) img4.getTag(),0,false);
        Cell cell5 = new Cell("blank",img5.getDrawable().getCurrent(),0,4, (String) img5.getTag(),1,false);

        Cell cell6 = new Cell("blank",img6.getDrawable().getCurrent(),1,0, (String) img6.getTag(),0,false);
        Cell cell7 = new Cell("blank",img7.getDrawable().getCurrent(),1,1, (String) img7.getTag(),5,false);
        Cell cell8 = new Cell("blue",img8.getDrawable().getCurrent(),1,2, (String) img8.getTag(),0,false);
        Cell cell9 = new Cell("blank",img9.getDrawable().getCurrent(),1,3, (String) img9.getTag(),0,false);
        Cell cell10 = new Cell("blank",img10.getDrawable().getCurrent(),1,4, (String) img10.getTag(),0,false);

        Cell cell11 = new Cell("blank",img11.getDrawable().getCurrent(),2,0, (String) img11.getTag(),4,false);
        Cell cell12 = new Cell("red",img12.getDrawable().getCurrent(),2,1, (String) img12.getTag(),0,false);
        Cell cell13 = new Cell("blank",img13.getDrawable().getCurrent(),2,2, (String) img13.getTag(),2,false);
        Cell cell14 = new Cell("blue",img14.getDrawable().getCurrent(),2,3, (String) img14.getTag(),0,false);
        Cell cell15 = new Cell("blank",img15.getDrawable().getCurrent(),2,4, (String) img15.getTag(),0,false);

        Cell cell16 = new Cell("green",img16.getDrawable().getCurrent(),3,0, (String) img16.getTag(),0,false);
        Cell cell17 = new Cell("blank",img17.getDrawable().getCurrent(),3,1, (String) img17.getTag(),6,false);
        Cell cell18 = new Cell("yellow",img18.getDrawable().getCurrent(),3,2, (String) img18.getTag(),0,false);
        Cell cell19 = new Cell("blank",img19.getDrawable().getCurrent(),3,3, (String) img19.getTag(),3,false);
        Cell cell20 = new Cell("purple",img20.getDrawable().getCurrent(),3,4, (String) img20.getTag(),0,false);


        RowColumn rowColumn1 = new RowColumn(img1,cell1);
        RowColumn rowColumn2 = new RowColumn(img1,cell2);
        RowColumn rowColumn3 = new RowColumn(img1,cell3);
        RowColumn rowColumn4 = new RowColumn(img1,cell4);
        RowColumn rowColumn5 = new RowColumn(img1,cell5);
        RowColumn rowColumn6 = new RowColumn(img1,cell6);
        RowColumn rowColumn7 = new RowColumn(img1,cell7);
        RowColumn rowColumn8 = new RowColumn(img1,cell8);
        RowColumn rowColumn9 = new RowColumn(img1,cell9);
        RowColumn rowColumn10 = new RowColumn(img1,cell10);
        RowColumn rowColumn11 = new RowColumn(img1,cell11);
        RowColumn rowColumn12 = new RowColumn(img1,cell12);
        RowColumn rowColumn13 = new RowColumn(img1,cell13);
        RowColumn rowColumn14 = new RowColumn(img1,cell14);
        RowColumn rowColumn15 = new RowColumn(img1,cell15);
        RowColumn rowColumn16 = new RowColumn(img1,cell16);
        RowColumn rowColumn17 = new RowColumn(img1,cell17);
        RowColumn rowColumn18 = new RowColumn(img1,cell18);
        RowColumn rowColumn19 = new RowColumn(img1,cell19);
        RowColumn rowColumn20 = new RowColumn(img1,cell20);

        rowColumnList.add(rowColumn1);
        rowColumnList.add(rowColumn2);
        rowColumnList.add(rowColumn3);
        rowColumnList.add(rowColumn4);
        rowColumnList.add(rowColumn5);
        rowColumnList.add(rowColumn6);
        rowColumnList.add(rowColumn7);
        rowColumnList.add(rowColumn8);
        rowColumnList.add(rowColumn9);
        rowColumnList.add(rowColumn10);
        rowColumnList.add(rowColumn11);
        rowColumnList.add(rowColumn12);
        rowColumnList.add(rowColumn13);
        rowColumnList.add(rowColumn14);
        rowColumnList.add(rowColumn15);
        rowColumnList.add(rowColumn16);
        rowColumnList.add(rowColumn17);
        rowColumnList.add(rowColumn18);
        rowColumnList.add(rowColumn19);
        rowColumnList.add(rowColumn20);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_p1_board,container,false);
        // Inflate the layout for this fragment
        setUpBoard(v);

        Game game =(Game)getActivity();
        ArrayList<RowColumn> temp =new ArrayList<>(rowColumnList.size());
        temp.addAll(rowColumnList);
        game.frag1(temp);
        Bundle bundle = new Bundle();
        bundle.putSerializable("valuesArray", temp);

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

        img1.setImageDrawable(getResources().getDrawable(R.drawable.yellow1));
        img2.setImageDrawable(getResources().getDrawable(R.color.grey));
        img3.setImageDrawable(getResources().getDrawable(R.color.grey));
        img4.setImageDrawable(getResources().getDrawable(R.drawable.purple6));
        img5.setImageDrawable(getResources().getDrawable(R.drawable.four));

        img6.setImageDrawable(getResources().getDrawable(R.color.purple));
        img7.setImageDrawable(getResources().getDrawable(R.drawable.green3));
        img8.setImageDrawable(getResources().getDrawable(R.color.blue));
        img9.setImageDrawable(getResources().getDrawable(R.drawable.blue1));
        img10.setImageDrawable(getResources().getDrawable(R.color.grey));

        img11.setImageDrawable(getResources().getDrawable(R.drawable.four));
        img12.setImageDrawable(getResources().getDrawable(R.color.yellow));
        img13.setImageDrawable(getResources().getDrawable(R.drawable.red3));
        img14.setImageDrawable(getResources().getDrawable(R.color.grey));
        img15.setImageDrawable(getResources().getDrawable(R.color.purple));

        img16.setImageDrawable(getResources().getDrawable(R.drawable.green6));
        img17.setImageDrawable(getResources().getDrawable(R.drawable.purple5));
        img18.setImageDrawable(getResources().getDrawable(R.color.grey));
        img19.setImageDrawable(getResources().getDrawable(R.drawable.yellow2));
        img20.setImageDrawable(getResources().getDrawable(R.color.blue));
        addToBlock();
    }


}
