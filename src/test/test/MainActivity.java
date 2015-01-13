package test.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity
{
    int test;
    Player player = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button Door = new Button(this);
        player = new Player(this, test);
        Door.setText(Integer.toString(player.getHi()));
        AbsListView.LayoutParams bp;
        bp = new AbsListView.LayoutParams(50, 50);
        Door.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View V)
            {
                loli();
            }
        });
        LinearLayout ll = (LinearLayout)findViewById(R.id.machin);
        ll.addView(Door, bp);
    }

    public void loli()
    {
        Button Doors = new Button(this);
        setContentView(R.layout.main);
        Doors.setText(Integer.toString(player.getHi()));
        AbsListView.LayoutParams bp;
        bp = new AbsListView.LayoutParams(50, 50);
        player.incHi();
        Doors.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View V)
            {
                loli();
            }
        });
        LinearLayout ll = (LinearLayout)findViewById(R.id.machin);
        ll.addView(Doors, bp);
    }
}