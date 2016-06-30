package com.example.q.cs496_prj1;

/**
 * Created by q on 2016-06-29.
 */
public class Address {
    public String name;
    public String addr;
    public String email;
    public String gender;
    private boolean is_expand;

    String get_name() {
        return this.name;
    }

    String get_addr() {
        return this.addr;
    }

    String get_email() {
        return this.email;
    }

    String get_gender() {
        return this.gender;
    }

    Boolean get_is_expand() {
        return this.is_expand;
    }

    void set_is_expanded(boolean flag) {
        this.is_expand = flag;
    }

    void flip_is_expanded() {
        this.is_expand = !this.is_expand;
    }

    Address (String name, String addr, String email, String gender) {
        this.name = name;
        this.addr = addr;
        this.email = email;
        this.gender = gender;
        this.is_expand = false;
    }
}
