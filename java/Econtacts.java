package com.example.isha.emergency;

/**
 * Created by Isha on 4/14/2016.
 */

        import java.io.Serializable;

/**
 * Created by Isha on 3/6/2016.
 */
public class Econtacts implements Serializable {
    //   private String _id;
    private String _econtacts;
    private String _ename;
    public Econtacts()
    {

    }
    public Econtacts(String _econtacts,String _ename) {

        this._econtacts = _econtacts;
        this._ename=_ename;
        //    this._id=_id;

    }

    public String get_econtacts() {
        return _econtacts;
    }

  /*  public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }*/


    public void set_econtacts(String _econtacts) {
        this._econtacts = _econtacts;
    }

    public String get_ename() {
        return _ename;
    }

    public void set_ename(String _ename) {
        this._ename = _ename;
    }


}
