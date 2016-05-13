package com.example.isha.emergency;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isha.emergency.R;

import java.util.ArrayList;


public class SelectContactsFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    public static final int RESULT_OK = -1;
    private Uri uriContact;
    private String contactID;
    static String contactName = "";
    static String contactNumber = "";
    DBAdapter db;
    static ListView list;
    static ArrayList<Econtacts> econtacts = new ArrayList<>();
    CustomAdaptor adapter;
    View rootView;
    Button btn;
    //Holder holder;
    ImageButton btn1, btn2;
    private static LayoutInflater inflator = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_select, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        db = new DBAdapter(getActivity());
        econtacts = db.getAllContacts();
        adapter = new CustomAdaptor(getActivity(), econtacts);
        list.setAdapter(adapter);
        btn = (Button) rootView.findViewById(R.id.contacts);
        btn.setOnClickListener(this);
       /* btn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            getActivity(). startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            adapter.notifyDataSetChanged();
        }
    });*/
  /*      btn1=(ImageButton)rootView.findViewById(R.id.alert);
       // btn.setOnClickListener(this);
        btn2=(ImageButton)rootView.findViewById(R.id.remove);
       // btn.setOnClickListener(this);
        inflator=(LayoutInflater)rootView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return rootView;

    }

    public class Holder
    {
        ImageButton b1,b2;
    }
    public View getView(int position,View convertView,ViewGroup parent)
    {
        View rowView=convertView;

        if(convertView==null) {

            rowView = inflator.inflate(R.layout.custom_row, null);
            holder = new Holder();
            // holder.pid = (TextView) rowView.findViewById(R.id.id);
            holder.b1 = (ImageButton) rowView.findViewById(R.id.alert);
            holder.b2 = (ImageButton) rowView.findViewById(R.id.remove);
            holder.b1.setTag(position);
            holder.b2.setTag(position);
            rowView.setTag(position);
        }
        else
        {
            int index=(Integer)rowView.getTag();
        }

            //holder.pid.setText(econtacts.get(position).get_id());

        return rowView;
    }

public void alertAction(View v){
    Toast.makeText(rootView.getContext(),"Inside",Toast.LENGTH_LONG).show();
    int i = (Integer)v.getTag();
    Uri uri = Uri.parse("smsto:"+econtacts.get(i).get_econtacts());
    Intent it = new Intent(Intent.ACTION_SENDTO, uri);
    it.putExtra("sms_body", "I am in danger");
    startActivity(it);  // do your code

}

    public void removeAction(View v){
              int index = (Integer)v.getTag();
       Toast.makeText(getActivity(),"called",Toast.LENGTH_LONG).show();
       Econtacts contact=econtacts.get(index);
       // db.delete(index);
        //econtacts.remove(index);
        //Toast.makeText(getActivity(),"call here",Toast.LENGTH_LONG).show();

        adapter.notifyDataSetChanged();// do your code


    }
    @Override
    public void onClick(View v) {



            switch (v.getId()) {

              case R.id.contacts:
                  getActivity(). startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
                  adapter.notifyDataSetChanged();
                  break;
                case R.id.alert:
                    break;

                case R.id.remove:
                    break;

                default:
                    break;
            }

        }




    }
*/
        return rootView;
    }

    @Override
    public void onClick(View v) {

        getActivity(). startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
        adapter.notifyDataSetChanged();
    }
}

