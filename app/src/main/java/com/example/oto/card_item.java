package com.example.oto;

public class card_item {
    private String _name;
    private String _origin;
    private String _dest;
    private String _time;
    private String _id;


    public card_item(String name, String origin, String dest, String time, String id){
        _name=name;
        _origin=origin;
        _dest=dest;
        _time=time;
        _id=id;

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
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

}
