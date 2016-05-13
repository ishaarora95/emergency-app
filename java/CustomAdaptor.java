package com.example.isha.emergency;

/**
 * Created by Isha on 4/14/2016.
 */


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdaptor extends BaseAdapter implements View.OnClickListener {
    private static LayoutInflater inflator = null;
    Context context;
    ArrayList<Econtacts> econtacts;
    DBAdapter db;
    MediaPlayer mp;


    public CustomAdaptor(Context context, ArrayList<Econtacts> econtacts) {
        this.econtacts = econtacts;
        this.context = context;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = new DBAdapter(context);
    }

    @Override
    public int getCount() {
        return econtacts.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof ImageButton) {
            Toast.makeText(context, "phno:" + v.getTag().toString(), Toast.LENGTH_SHORT).show();
            if (v.getTag().toString().substring(0, 3).equals("img")) {
                mp = MediaPlayer.create(context, R.raw.soundclip);
                try {
                    if (mp.isPlaying()) {
                        mp.stop();
                        mp.release();
                        mp = MediaPlayer.create(context, R.raw.soundclip);
                    }
                    mp.start();
                } catch (Exception e) {
                    Toast.makeText(context, "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "Inside", Toast.LENGTH_SHORT).show();

                String item = (v.getTag().toString().substring(3, (v.getTag().toString().length())));
                //   /*    int i = (Integer)v.getTag();
                Uri uri = Uri.parse("smsto:" + item);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "I am in danger.My location is https://www.google.co.in/maps/@" + ViewLocationFragment.currentLatitude + "," + ViewLocationFragment.currentLongitude + "," + "14z?hl=en&hl=en");
                context.startActivity(it);
            } else if (v.getTag().toString().substring(0, 3).equals("rem")) {


                String item = (v.getTag().toString().substring(3, (v.getTag().toString().length())));
                Toast.makeText(context, "item:" + item, Toast.LENGTH_SHORT).show();
                db.delete(item);
                CustomAdaptor adaptor = new CustomAdaptor(context, db.getAllContacts());
                SelectContactsFragment.list.setAdapter(adaptor);

            }


        }
        if (v.getTag().toString().substring(0, 2).equals("ph")) {
            String item = (v.getTag().toString().substring(2, (v.getTag().toString().length())));
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + item));
            Toast.makeText(context, "call:" + item, Toast.LENGTH_SHORT).show();
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            context.startActivity(intent);
        }

    }

    public class Holder
    {
        TextView pid,pname,pph;
        ImageButton b1,b2;
    }
    public View getView(int position,View convertView,ViewGroup parent)
    {
        View rowView=convertView;
        Holder holder;
        if(convertView==null) {
            rowView = inflator.inflate(R.layout.custom_row, null);
            holder = new Holder();
            // holder.pid = (TextView) rowView.findViewById(R.id.id);
            holder.pname = (TextView) rowView.findViewById(R.id.name);
            holder.pph = (TextView) rowView.findViewById(R.id.phone);
            holder.b1 = (ImageButton) rowView.findViewById(R.id.alert);
            holder.b2 = (ImageButton) rowView.findViewById(R.id.remove);
            rowView.setTag(holder);
        }
        else
        {
            holder=(Holder)rowView.getTag();
        }

        if(econtacts.size()<=0)
        {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_LONG).show();
        }
        else {
            //Econtacts e=new Econtacts();;
            //holder.pid.setText(econtacts.get(position).get_id());
            holder.pname.setText(econtacts.get(position).get_ename());
            holder.pname.setTag("ph" + econtacts.get(position).get_econtacts());
            holder.pname.setOnClickListener(this);
            holder.pph.setText(econtacts.get(position).get_econtacts());
            holder.pph.setTag("ph" + econtacts.get(position).get_econtacts());
            holder.pph.setOnClickListener(this);
            holder.b1.setTag("img" +econtacts.get(position).get_econtacts());
            holder.b1.setOnClickListener(this);
            holder.b2.setTag("rem" + econtacts.get(position).get_econtacts());
            holder.b2.setOnClickListener(this);
        }
        return rowView;
    }
}
































































































































































































































