package jts.mahesh.jtsdesktop.obdfinallayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;

public class ListAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] Txt1;
/*
    private String[] Image;
*/
    private String[] Txt2;
    private String[] Txt3;
    private String[] Txt4;
    private String[] Txt5;
    private String[] Txt6;
    private String[] Txt7;
    private String[] Txt8;
    private String[] Txt9;
    private Boolean[] bln;

Boolean vs=false;
/*
    private String[] Button;
*/


    TextView txt1, txt2, txt3, txt4, txt5, txt6,txt7,txt8,txt9;
    ImageView img;
    Button btn;
    LinearLayout vsblt;

    public ListAdapter(Context context, String[] text1, String[] text2, String[] text3, String[] text4, String[] text5, String[] text6, String[] text7, String[] text8, String[] text9,Boolean[] vsbl) {
        super(context, R.layout.activity_main, text1);
        this.context = context;

        this.Txt1 = text1;
/*
        this.Image = image;
*/
        this.Txt2 = text2;
        this.Txt3 = text3;
        this.Txt4 = text4;
        this.Txt5 = text5;
        this.Txt6 = text6;
        this.Txt7 = text7;
        this.Txt8 = text8;
        this.Txt9 = text9;
        this.bln = vsbl;
/*
        this.Button = button;
*/
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_list_adapter, parent, false);

        txt1 = (TextView) rowView.findViewById(R.id.text1);
        txt2 = (TextView) rowView.findViewById(R.id.text2);
        txt3 = (TextView) rowView.findViewById(R.id.text3);
        txt4 = (TextView) rowView.findViewById(R.id.text4);
        txt5 = (TextView) rowView.findViewById(R.id.text5);
        txt6 = (TextView) rowView.findViewById(R.id.text6);
        txt7 = (TextView) rowView.findViewById(R.id.text7);
        txt8 = (TextView) rowView.findViewById(R.id.text8);
        txt9 = (TextView) rowView.findViewById(R.id.text9);
        vsblt= (LinearLayout) rowView.findViewById(R.id.vsblid);

        btn= (Button) rowView.findViewById(R.id.buttonx);
      /*  Drawable draw=res.getDrawable(R.drawable.progr);
        progressBar.setProgressDrawable(draw);*/

        /*img = (ImageView) rowView.findViewById(R.id.image);
        btn = (Button) rowView.findViewById(R.id.button);
*/
        if(!bln[position]) {
            vsblt.setVisibility(View.VISIBLE);

        }else if(bln[position]){
            vsblt.setVisibility(View.GONE);

        }
        if (position==0) {
            btn.setBackgroundResource(R.drawable.green);

        }
        else   if (position==1) {
            btn.setBackgroundResource(R.drawable.red);

        }
        else  if (position==2) {
            btn.setBackgroundResource(R.drawable.green);

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!bln[position]) {
                    bln[position]=true;
                    notifyDataSetChanged();
                }else if(bln[position]){
                    bln[position]=false;
                    notifyDataSetChanged();
                }
            }
        });


        try {
            txt1.setText(Txt1[position]);
            txt2.setText(Txt2[position]);
            txt3.setText(Txt3[position]);
            txt4.setText(Txt4[position]);
            txt5.setText(Txt5[position]);
            txt6.setText(Txt6[position]);
            txt7.setText(Txt7[position]);
            txt8.setText(Txt8[position]);
            txt9.setText(Txt9[position]);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return rowView;
    }

}