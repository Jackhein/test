package test.test;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.renderscript.Element;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import test.test.R.id;
import test.test.R.layout;

public class MainActivity extends Activity
{
    private TextView room_name = null;
    private TextView description = null;
    //private Button character = null;
    private TextView door = null;
    //private Element source = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        LinearLayout Button_door;
        Button_door = (LinearLayout)findViewById(R.id.machin);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Name
        room_name = (TextView) findViewById(R.id.Room_Name);
        
        //Description
        description = (TextView) findViewById(R.id.Description);

        try
        {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            LinearLayout txtl_co = (LinearLayout)findViewById(R.id.machin);
            LayoutParams txtp_co = new LayoutParams (
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
            TextView txtt_co = new TextView(this);
            if (netInfo != null && netInfo.isConnectedOrConnecting())
            {
                txtt_co.setText("Ook !");
            }
            else
            {
                txtt_co.setText("Ook Ooook !!");
            }
            txtl_co.addView(txtt_co, txtp_co);
        }
        catch(Exception e)
        {
            LinearLayout ll = (LinearLayout)findViewById(R.id.machin);
            LayoutParams bp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            TextView error = new TextView(this);
            error.setText("INTERNET = " + e);
            ll.addView(error, bp);
        }
        Button myButton = new Button(this);
        myButton.setText("Push Me");
        LinearLayout ll = (LinearLayout)findViewById(R.id.machin);
        LayoutParams lp = new LayoutParams (
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);

        myButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View V)
            {
                Button_Room();
            }
        });
    }

    public void Button_Room()
    {
        try {
            // Connection to URL and take all XML data
            URL url = new URL("http://www.geektest.org/bundles/mioreygeektest/karazhan/couloir.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            LinearLayout lll = (LinearLayout)findViewById(R.id.machin);
            LayoutParams lbp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            TextView ook= new TextView(this);
            ook.setText("it's ook");
            lll.addView(ook, lbp);

            NodeList piece_nl = doc.getElementsByTagName("piece");

            if (piece_nl != null && piece_nl.getLength() > 0)
            {
                Button Door[] = new Button[piece_nl.getLength()];

                for (int i = 0; i < piece_nl.getLength(); i++)
                {
                    Node door_n = piece_nl.item(i);
                    if (door_n.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element elem = (Element) door_n;
                        
                        Door[i].setText("toot");
                        Door[i].setId(i);
                        Door[i].setOnClickListener(new View.OnClickListener()
                        {
                            public void onClick(View V)
                            {
                                for (int j = 0; findViewById(j) != null; j++)
                                {
                                    V = (View) findViewById(1);
                                    ((ViewManager)V.getParent()).removeView(V);
                                }
                            }
                        });
                        LinearLayout ll = (LinearLayout)findViewById(R.id.machin);
                        LayoutParams bp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                        ll.addView(Door[i], bp);
                    }
                }
            }
        }
        catch (Exception e) {
            LinearLayout ll = (LinearLayout)findViewById(R.id.machin);
            LayoutParams bp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            TextView error = new TextView(this);
            error.setText("XML Pasing Excpetion = " + e);
            ll.addView(error, bp);
        }
    }
}
            /*
*/

        //Characterd
        //character = (Button) findViewById(R.id.Character);