/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.test;

import android.content.Context;
import android.widget.ArrayAdapter;

public class Player extends ArrayAdapter<Object>
{
    Context mc;
    int hi;

    public Player(Context context, int resource) {
        super(context, resource);
        
        this.mc = context;
        this.hi = resource;
    }

    public void incHi() {
        this.hi++;
    }

    public int getHi() {
        return hi;
    }

    public void setHi(int hi) {
        this.hi = hi;
    }

    public Context getMc() {
        return mc;
    }

    public void setMc(Context mc) {
        this.mc = mc;
    }
}
