package test.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import org.w3c.dom.Element;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class MainActivity extends Activity
{    
    private TextView room_name = null;
    private TextView description = null;
    private Bundle source;
    private LinearLayout button_door;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //Name
        room_name = (TextView) findViewById(R.id.Room_Name);
        
        //Description
        description = (TextView) findViewById(R.id.Description);

        //Button
        button_door = (LinearLayout)findViewById(R.id.Door);

        //Get source from old click
        source = getIntent().getExtras();

        try {
            // Try to connect to Internet
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            try {
                //Try to connect to specific URL
                URL url = new URL("http://www.geektest.org/bundles/mioreygeektest/karazhan/couloir.xml");
                if (source != null)
                {
                    url = new URL("http://www.geektest.org/bundles/mioreygeektest/karazhan/" + source.getString("source")+ ".xml");
                }

                //Get XML to document
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(url.openStream()));
                doc.getDocumentElement().normalize();

                //Get rootnodelist from document
                NodeList piece_nl = doc.getElementsByTagName("piece");
                Node piece_n = (Node) piece_nl.item(0);
                NamedNodeMap piece_nnm = piece_n.getAttributes();

                //Set text from current room
                room_name.setText(piece_nnm.item(0).getNodeValue());
                description.setText(piece_nnm.item(1).getNodeValue());

                //Get root for every door
                Element piece_e = (Element) piece_nl.item(0);
                NodeList door_nl = piece_e.getChildNodes().item(1).getChildNodes();

                for (int i = 1; i < door_nl.getLength(); i++)
                {
                    //Get XML data
                    Element door_e = (Element) door_nl.item(i);
                    NamedNodeMap door_nnm = door_e.getAttributes();

                    //Add Button and Set XML data
                    Button Door = new Button(this);
                    Node door_n = door_nnm.item(1);

                    //Add change to new button's instance
                    Door.setTag(door_nnm.item(0).getNodeValue());

                    //Set LayoutParams to modify for next setting
                    LayoutParams bp;

                    //Try to get an image for each button
                    try
                    {
                        //Set image if connection succed
                        URL img_url = new URL("http://www.worldofmunchkin.com/icons/img/m6.gif");
                        InputStream img_is = (InputStream) img_url.getContent();
                        Drawable img_d = Drawable.createFromStream(img_is, "img");
                        //setBackgroundDrawable is only for API below API16
                        Door.setBackgroundDrawable(img_d);
                        bp = new LayoutParams(50, 50);
                    }
                    catch (IOException e)
                    {
                        //Set text if connection failed
                        Door.setText(door_n.getNodeValue());
                        bp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    }

                    //Set listener on click
                    Door.setOnClickListener(new View.OnClickListener()
                    {
                        public void onClick(View V)
                        {
                            //Set new activity for next restart
                            Intent new_activity = new Intent(getApplicationContext(), MainActivity.class);

                            //Try to execute (in case of lost of connectivity
                            try
                            {
                                //Set new URL source for next room
                                new_activity.putExtra("source", (String) V.getTag());

                                //Restart activity
                                finish();
                                startActivity(new_activity);
                            }
                            catch (Exception e)
                            {
                                //Restart activity
                                finish();
                                startActivity(new_activity);
                            }
                        }
                    });

                    //Add button view
                    LinearLayout ll = (LinearLayout)findViewById(R.id.Door);
                    ll.addView(Door, bp);
                }
            }
            catch (Exception e)
            {
                //MSG for connectivity failure to source URL
                LinearLayout ll = (LinearLayout)findViewById(R.id.Door);
                LayoutParams bp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                TextView error = new TextView(this);
                error.setText("XML Pasing Excpetion = " + e + "\nPlease, verify you're connectivity and restart this application");
                ll.addView(error, bp);
            }
        }
        catch(Exception e)
        {
            //MSG for connectivity failure to internet
            LinearLayout ll = (LinearLayout)findViewById(R.id.Door);
            LayoutParams bp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            TextView error = new TextView(this);
            error.setText("INTERNET = " + e + "\nPlease, verify you're connectivity and authorization for this application and restart it");
            ll.addView(error, bp);
        }
    }
}