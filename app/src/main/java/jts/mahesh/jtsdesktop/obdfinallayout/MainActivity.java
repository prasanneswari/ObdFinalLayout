package jts.mahesh.jtsdesktop.obdfinallayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/*import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;*/


import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static jts.mahesh.jtsdesktop.obdfinallayout.Login.username;
import static jts.mahesh.jtsdesktop.obdfinallayout.Login.pwd;
import static jts.mahesh.jtsdesktop.obdfinallayout.Login.macid;




import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements MqttCallback {

    public static String Mac_Id, Rpm, Speed, Clnt, Air,E_Load,maf_ap,Maf_air,Acc_D,Acc_E;
    JSONObject jsonObject, allMachinesArray;
    JSONArray machinesArray;
    String[] allMachinesData;
    int intValue = 0;
    Handler handler = new Handler();

    private static String TAG = "MQTT_android";
    String payload = "the payload";
    MqttAndroidClient client;
    MqttConnectOptions options = new MqttConnectOptions();

    String[] text1 = {"MILEAGE STATISTICS", "DRIVING STATISTICS", "RECENT TRIPS"};
    String[] text2 = {"13.8", "29.3%", "221"};
    String[] text3 = {"KILOMETERS PER LITERS", "BRAKE SHOE LIFE", "KILOMETERS"};
    Boolean[] vbln = {true, true, true};
    String[] text5 ;
    String[] text7 = {"Air", "KMPH AVG SPEED", "COOLANT"};
    String[] text8 = {"ACC D", "ACC E", "MAF AP"};
    String[] text9 = {"MAF AIR", "AVG RPM", " E LOAD"};
    String[] text4;
    String[] text6;
   /* String[] text4 = new String[]{"41,400", String.valueOf(Speed), String.valueOf(Speed)};
    String[] text6 = new String[]{"3,000", String.valueOf(Rpm), String.valueOf(Rpm)};*/
    ListView lstv;

    Handler parlercheck;
    ListAdapter reqAdapter;
    private Handler mHandler;
    SharedPreferences apref;
    boolean prefsEditor1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstv=(ListView)findViewById(R.id.List1);
        this.mHandler = new Handler();
        //runnable.run();
       /* lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Obd.class);
                startActivity(intent);
            }
        });*/

       /* final ProgressBar pb_green = (ProgressBar) findViewById(R.id.bargreen);
        final ProgressBar pb_yellow = (ProgressBar) findViewById(R.id.baryellow);
        final ProgressBar pb_red = (ProgressBar) findViewById(R.id.barred);


        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (intValue < 70) {
                    intValue++;

                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                            pb_green.setProgress(intValue);
                            pb_yellow.setProgress(intValue);
                            pb_red.setProgress(intValue);

                        }
                    });
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/



        apref = getPreferences(MODE_PRIVATE);
        prefsEditor1= apref.edit().commit();
        lstv = (ListView) findViewById(R.id.List1);
        reqAdapter = new ListAdapter(this, text1, text2, text3, text4, text5, text6, text7, text8, text9, vbln);
        lstv.setAdapter(reqAdapter);

       /* String svdun = Run();
        if(!svdun.equals("")){
            try {
                MessageArrivedfn(svdun);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
        subscribe_scada();

    }

    @Override
    public void onBackPressed() {

     //   this.mHandler.removeCallbacks(runnable);
        super.onBackPressed();

    }

    /*private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
           *//* Log.d("--number of threads:--", String.valueOf(Thread.activeCount()));
            text4 = new String[]{"41,400", String.valueOf(Speed), String.valueOf(Speed)};
            text6 = new String[]{"3,000", String.valueOf(Rpm), String.valueOf(Rpm)};
*//*


            MainActivity.this.mHandler.postDelayed(runnable, 5000);


        }
    };*/

    public void subscribe_scada() {
        Log.d("Enetered ", "in sub func ");
        //Bundle b = getIntent().getExtras();
        String clientId = MqttClient.generateClientId();
        final String topic = "Vehicle_A";
        //String server_ip = "tcp://jtha.in:1883";
        String server_ip = "tcp://cld003.jts-prod.in:1883";
        Log.d("Enetered ", "subscribeScada");
        client = new MqttAndroidClient(this.getApplicationContext(), server_ip,
                        clientId);

        Log.d("Enetered ", "subscribeScada1");
        try {
            options.setUserName("esp");
            options.setPassword("jtsesp01".toCharArray());
            IMqttToken token = client.connect(options);
            Log.d("Enetered ", "subscribeScada2");
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    //t.cancel();
                    Log.d("Enetered ", "subscribeScada3");
                    client.setCallback(MainActivity.this);
                    int qos = 2;
                    try {
                        IMqttToken subToken = client.subscribe(topic, qos);
                        Log.d("Enetered ", "subscribeScada4");
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                // successfully subscribed
                                //tv.setText("Successfully subscribed to: " + topic);
                                Log.d("success", "came here");
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                                // The subscription could not be performed, maybe the user was not
                                // authorized to subscribe on the specified topic e.g. using wildcards
                                // Toast.makeText(MainActivity.this, "Couldn't subscribe to: " + topic, Toast.LENGTH_SHORT).show();
                                Log.d("failure", "came here");
                                //tv.setText("Couldn't subscribe to: " + topic);

                            }
                        });
                        Log.d(TAG, "here we are");
                    } catch (MqttException e) {
                        e.printStackTrace();
                        Log.d("error", "!");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Log.d("error", "2");
                    }
                    byte[] encodedPayload = new byte[0];
                    try {
                        encodedPayload = payload.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        client.publish(topic, message);
                    } catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
            Log.d(TAG, "onFailure");
        }
    }


    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Log.d(">>>MessageArrived>>>", String.valueOf(mqttMessage));

        String jsonResponse = String.valueOf(mqttMessage);
        Log.d(">>>JsonResponse>>>", String.valueOf(jsonResponse));

       // apref.edit().putString("jsonResponse", jsonResponse).commit();

      // String sample= {"e_load": 0, "clnt": 79, "maf_air": 0, "speed": 0, "acc_D": 0, "rpm": 0, "acc_E": 0, "maf_ap": 95, "air": 0};
        MessageArrivedfn(jsonResponse);
        Log.d("--number of threads:--", String.valueOf(Thread.activeCount()));

    }


    public void MessageArrivedfn(String mqttmsg) throws JSONException {


          try {
              Log.d("[[[JsnenterdAMCArray>", " entered inside try" );
              JSONObject responseJSON = new JSONObject(mqttmsg);
             /* Mac_Id = responseJSON.getInt("Status");
              Log.d("iam heew","All Machines Data" +Mac_Id);*/
              Rpm = String.valueOf(responseJSON.getInt("rpm"));
              Log.d("iam heew","All Machines Rpm" +Rpm);
              Speed = String.valueOf(responseJSON.getInt("speed"));
              Log.d("iam heew","All Machines Speed" +Speed);
              Clnt = String.valueOf(responseJSON.getInt("clnt"));
              Log.d("iam heew","All Machines Clnt" +Clnt);
              Air = String.valueOf(responseJSON.getInt("air"));
              Log.d("iam heew","All Machines Air" +Air);
              E_Load =String.valueOf(responseJSON.getInt("e_load"));
              Log.d("iam heew","All Machines E_Load" +E_Load);
              maf_ap = String.valueOf(responseJSON.getInt("maf_ap"));
              Log.d("iam heew","All Machines maf_ap" +maf_ap);
              Maf_air = String.valueOf(responseJSON.getInt("maf_air"));
              Log.d("iam heew","All Machines Maf_air" +Maf_air);
              Acc_D =String.valueOf(responseJSON.getInt("acc_D"));
              Log.d("iam heew","All Machines Acc_D" +Acc_D);
              Acc_E =String.valueOf(responseJSON.getInt("acc_E"));
              Log.d("iam heew","All Machines Acc_E" +Acc_E);
              text4 = new String[]{String.valueOf(Air), String.valueOf(Speed), String.valueOf(Clnt)};
              text6 = new String[]{ String.valueOf(Maf_air), String.valueOf(Rpm), String.valueOf(E_Load)};
              text5 = new String[]{String.valueOf(Acc_D), String.valueOf(Acc_E), String.valueOf(maf_ap)};

              lstv = (ListView) findViewById(R.id.List1);
              reqAdapter = new ListAdapter(this, text1, text2, text3, text4, text5, text6, text7, text8, text9, vbln);
              lstv.setAdapter(reqAdapter);

              /*int firstPosition = lstv.getFirstVisiblePosition();
              Log.d("","");*/
              int index = lstv.getFirstVisiblePosition();
              Log.d("getFirstVisiblePosition", String.valueOf(index));
              View v = lstv.getChildAt(0);
              int top = (v == null) ? 0 : (v.getTop() - lstv.getPaddingTop());
              reqAdapter = new ListAdapter(this, text1, text2, text3, text4, text5, text6, text7, text8, text9, vbln);
              lstv.setAdapter(reqAdapter);
              // restore index and position
              lstv.setSelectionFromTop(index, top);
              // lstv.setSelection(firstPosition);

         } catch (JSONException e) {
         e.printStackTrace();
          }



    }


    private String Run(){
        String json = apref.getString("jsonResponse","");
        return json;
    }

    @Override
    public void deliveryComplete (IMqttDeliveryToken iMqttDeliveryToken){

    }
}
















/*
public class MainActivity extends AppCompatActivity {
    public static String Mac_Id, Rpm, Speed, Clnt, Air;
    String[]  text4;

    String[] text1 = {"MILEAGE STATISTICS", "DRIVING STATISTICS", "RECENT TRIPS"};
    String[] text2 = {"13.8", "29.3%", "221"};
    String[] text3 = {"KILOMETERS PER LITERS", "BRAKE SHOE LIFE", "KILOMETERS"};
    Boolean[] vbln = {true,true,true};
    String[] text5 = {"721 ", "32 ", "32 "};
    String[] text6;
    String[] text7 = {"KM", "KMPH RUG SPEED", "KMPH RUG SPEED"};
    String[] text8 = {"HOURS", "TYRE PS!", "TYRE PS!"};
    String[] text9 = {"LITRES", "RUG RPM", " RUG RPM"};

    ListView lstv;

    Handler parlercheck;
    ListAdapter reqAdapter;
    private Handler mHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstv = (ListView) findViewById(R.id.List1);
        this.mHandler = new Handler();
        runnable.run();
        reqAdapter = new ListAdapter(this, text1, text2, text3, text4, text5, text6, text7, text8, text9,vbln);
        lstv.setAdapter(reqAdapter);

       */
/* lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(MainActivity.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,Obd.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        });*//*


    }

    @Override
    public void onBackPressed() {

        this.mHandler.removeCallbacks(runnable);
        super.onBackPressed();

    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("--number of threads:--", String.valueOf(Thread.activeCount()));
            text4 = new String[]{"41,400", String.valueOf(Speed), String.valueOf(Speed)};
            text6 = new String[]{"3,000", String.valueOf(Rpm), String.valueOf(Rpm)};

            httpRequest1();


            MainActivity.this.mHandler.postDelayed(runnable, 5000);


        }
    };




    public void httpRequest1() {
        Log.d("hello1","inside came.......");
        RequestQueue queue = Volley.newRequestQueue(this);
        // String url ="http://cld002.jts-prod.in/service/get_cust_support_num";
        String url=" http://cld003.jts-prod.in:20102/Data?parameter_list=rpm,speed,clnt_temp,air";
// Request a string response from the provided URL.
        Log.d("hello1",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hello1","inside came.......");
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Log.d("hello :;;;;", response.toString());
                        JSONObject responseJSON = null;

                        try {
                            responseJSON = new JSONObject(response);

                            Log.d("hello1","response came.......");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {

                            Mac_Id = responseJSON.getString("Status");
                            Log.d("iam heew", "All Machines Data" + Mac_Id);
                            Rpm = responseJSON.getString("rpm");
                            Log.d("iam heew", "All Machines Rpm" + Rpm);
                            Speed = responseJSON.getString("speed");
                            Log.d("iam heew", "All Machines Speed" + Speed);

                            Clnt = responseJSON.getString("clnt_temp");
                            Log.d("iam heew", "All Machines Clnt" + Clnt);

                            Air = responseJSON.getString("air");
                            Log.d("iam heew", "All Machines Air" + Air);


                            reqAdapter = new ListAdapter(getApplicationContext(), text1, text2, text3, text4, text5, text6, text7, text8, text9,vbln);
                            lstv.setAdapter(reqAdapter);


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("hello1","error.......");
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");



            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}

*/


