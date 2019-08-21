package com.example.oto;

public class card_item_List {
    private String _id;
    private String _origin;
    private String _dest;
    private String _time;
    private String _driver;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_origin() {
        return _origin;
    }

    public void set_origin(String _origin) {
        this._origin = _origin;
    }

    public String get_dest() {
        return _dest;
    }

    public void set_dest(String _dest) {
        this._dest = _dest;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_driver() {
        return _driver;
    }

    public void set_driver(String _driver) {
        this._driver = _driver;
    }

    public card_item_List(String id, String origin, String dest, String time, String driver){
        _id=id;
        _origin=origin;
        _dest=dest;
        _driver=driver;
        _time=time;
    }

    public card_item_List() {

    }


}
