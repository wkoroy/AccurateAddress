package com.zirdaapps.getaddress;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "GETYOURADR";

    TextView tvEnabledGPS;
    TextView tvStatusGPS;
    TextView tvLocationGPS;
    TextView tvEnabledNet;
    TextView tvStatusNet;
    TextView tvLocationNet;


    // Button btnOk;
    Button cpgps;
    ImageButton copyaddr;
    ImageButton share;
    ImageButton settingloc;
    TextView textView1;




    TextView tcountry;
    TextView tpostalcode;
    TextView tstate;
    TextView tcounty;
    TextView tcity;
    TextView tcity_district;
    TextView tsuburb;
    TextView tneighbourhood;
    TextView troad;
    TextView thouse_number;
    TextView action;
    ProgressBar pbar;




    /* Your ad unit id. Replace with your actual ad unit id. */
    private static final String AD_UNIT_ID = "ca-app-pub-3322161032922618/9042951481";//"ca-app-pub-2057340203915227/6427033993";


    static boolean gpsmsg=false;
    static boolean orientmsg=false;

    private LocationManager locationManager;
    StringBuilder sbGPS = new StringBuilder();
    StringBuilder sbNet = new StringBuilder();
    String mlat ,mlon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvEnabledGPS = (TextView) findViewById(R.id.tvEnabledGPS);
        //tvStatusGPS = (TextView) findViewById(R.id.tvStatusGPS);
        tvLocationGPS = (TextView) findViewById(R.id.tvLocationGPS);
        tvEnabledNet = (TextView) findViewById(R.id.tvEnabledNet);
        //tvStatusNet = (TextView) findViewById(R.id.tvStatusNet);
        tvLocationNet = (TextView) findViewById(R.id.tvLocationNet);


        tcountry = (TextView) findViewById(R.id.tcountry );
        tpostalcode = (TextView) findViewById(R.id.tpostcode );
        tstate = (TextView) findViewById(R.id.tstate);
        tcounty = (TextView) findViewById(R.id.tcounty);
        tcity = (TextView) findViewById(R.id.tcity);
        tcity_district = (TextView) findViewById(R.id.tcity_district);
        tsuburb = (TextView) findViewById(R.id.tsuburb );
        tneighbourhood = (TextView) findViewById(R.id.tneighbourhood);
        troad = (TextView) findViewById(R.id.troad);
        thouse_number = (TextView) findViewById(R.id.thouse_number );
        action = (TextView) findViewById(R.id.action );
        //pbar=(ProgressBar) findViewById(R.id.progressBar1 );




        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        tcity= (TextView) findViewById(R.id.tcity);
        // btnOk = (Button) findViewById(R.id.buttonurl);
        cpgps = (Button) findViewById(R.id.cpgpsbutton);
        copyaddr = (ImageButton) findViewById(R.id.copyadr);
        share = (ImageButton) findViewById(R.id.share);

        settingloc= (ImageButton) findViewById(R.id.settingloc);

        pbar = (ProgressBar) findViewById(R.id.progressBar1);
        pbar.setVisibility(View.VISIBLE);
        pbar.setProgress(0);

        if(Integer.valueOf(Build.VERSION.SDK_INT) <11) {
            copyaddr.setVisibility(View.INVISIBLE);
            cpgps.setVisibility(View.INVISIBLE);
        }
        else {


        }

        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            private JSONArray sbuf;

            @Override // Обработчик события кнопки
            public void onClick(View v) {
                // TODO Auto-generated method stub


				/*RequestTask rt=new RequestTask();
				rt.execute("http://isaev.url.ph/getQuery.php");*/
                //pbar.setProgress(50);

                String address=tstate.getText()+"\n"+tcounty.getText()+"\n "+tcity.getText()+"\n"+tcity_district.getText()+ "\n"+tsuburb.getText()
                        +"\n"+tneighbourhood.getText()+"\n"+troad.getText()+"\n"+thouse_number.getText()+"\n "+ getString(R.string.lat)+mlat +"; "+getString(R.string.lon)+mlon;;
                copyToClipBoard(address);

                Toast toast = Toast.makeText(getApplicationContext(),
                        getString(R.string.copyedmsg),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
                //AsyncTask<String, String, String> buf= new RequestTask().execute(mlat,mlon);
            }



        };


        View.OnClickListener doshare = new View.OnClickListener() {
            private JSONArray sbuf;

            @Override // Обработчик события кнопки
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mlat.length()>2 && mlon.length()>2) {

                    String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=" + mlat.toString() + "," + mlon.toString() + "");


                    //  Uri.parse("geo:"+mlat+","+mlon).toString();


                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, uri.toString());
                    shareIntent.setType("text/html");
                    startActivity(Intent.createChooser(shareIntent, "share "));
                    //AsyncTask<String, String, String> buf= new RequestTask().execute(mlat,mlon);
                }


            }



        };


        View.OnClickListener gpsdatacopy = new View.OnClickListener(){
            @Override // Обработчик события кнопки
            public void onClick(View v){

                String address=getString(R.string.lat)+mlat +"; "+getString(R.string.lon)+mlon;
                copyToClipBoard(address);




                Toast toast = Toast.makeText(getApplicationContext(),
                        (Uri.parse("geo:"+mlat+","+mlon).toString()),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
                 /* Toast toast = Toast.makeText(getApplicationContext(),
                          getString(R.string.copyedmsg),
                          Toast.LENGTH_SHORT);
                  toast.setGravity(Gravity.TOP, 0, 0);
                  toast.show();*/



            }


        };
        //		btnOk.setOnClickListener(oclBtnOk);
        cpgps.setOnClickListener(gpsdatacopy);
        copyaddr.setOnClickListener(oclBtnOk);
        share.setOnClickListener(doshare);

        if (!orientmsg) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.updatemsg)
                    ,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
            orientmsg=true;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
        checkEnabled();

    }

    @Override
    protected void onPause() {


        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
      /*if (provider.equals(LocationManager.GPS_PROVIDER)) {
        tvStatusGPS.setText("Status: " + String.valueOf(status));
      } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
        tvStatusNet.setText("Status: " + String.valueOf(status));
      }*/
        }
    };

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void copyToClipBoard(String text)
    {
        /*ClipboardManager ClipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipMan.setText(text);*/

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", text);
        clipboard.setPrimaryClip(clip);
    }

    public void setstatusaction(String staction)
    {
        action.setText(staction);
        return;
    }
    public void showLocation(Location location) {
        pbar.setProgress(0);
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvLocationGPS.setText(formatLocation(location));

            pbar.setProgress(50);
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            tvLocationNet.setText(formatLocation(location));
            pbar.setProgress(50);
        }
        mlat=""+location.getLatitude();
        mlon=""+location.getLongitude();



        AsyncTask<String, String, String> buf5 = new RequestTask().execute("" + location.getLatitude(), "" + location.getLongitude());



    }





    private String formatLocation(Location location) {

        if (location == null)
            return "";
        return String.format(
                getString(R.string.coorformatlabel),
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));


    }

    private String IsEnabledToWord(boolean flag)
    {
        if(flag==true) return getString(R.string.on_st);
        else return getString(R.string.of_st);
    }
    private void checkEnabled() {
        boolean gps_en=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean prov_en=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        tvEnabledGPS.setText(getString(R.string.statesconst)+  IsEnabledToWord(gps_en));
        tvEnabledNet.setText(getString(R.string.statesconst) +IsEnabledToWord(prov_en));

        if(! gpsmsg && ! gps_en) {
            gpsmsg=true;

            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.warninggps),
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 10, 0);
            toast.show();
        }
        if( gps_en)
        {
            setstatusaction( getString(R.string.waitgpscoord));
            pbar.setProgress(10);
        }
        else
        if( prov_en)
        {
            setstatusaction(getString(R.string.waitmobicoord));
            pbar.setProgress(10);
        }

        if(! gps_en && !prov_en)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getString(R.string.err_definelocation),
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            setstatusaction(getString(R.string.err_settings_location));
        }



    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    };


    class RequestTask extends AsyncTask<String, String,String > {

        String town;
        private final String LOG_TAG = "urlget";
        public JSONObject jsonObject = null;
        String str3;
        Location location;
        public boolean internetcon;
        public int statuscon=0;

        RequestTask() {
            internetcon = true;
        }

        protected String doInBackground(String... coords) {
            internetcon = true;
            URL url;
            String lon="56.4552",lat="45.5667";
            try {
                int cntparam=0;
                for (String coord : coords) {
                    if(cntparam == 0) lat=coord;
                    else
                    if(cntparam == 1) lon=coord;

                    cntparam++;
                }
				/*url = new URL(
						"http://open.mapquestapi.com/nominatim/v1/reverse?format=json&limit=1&lat="
								+ location.getLatitude() + "&lon="
								+ location.getLongitude());*/

                url = new URL(
                        "http://open.mapquestapi.com/nominatim/v1/reverse?key=GH6D1BdTZNGTIVnHWFwuAyCABy4dJ3bc&format=json&limit=1&lat="
                                +lat + "&lon="
                                + lon);
                // url = new
                // URL("http://open.mapquestapi.com/nominatim/v1/reverse?format=json&limit=1&lat=51.7386&lon=36.1366");

                HttpURLConnection con = (HttpURLConnection) url
                        .openConnection();

                if(/* con.getResponseCode() == HttpURLConnection.HTTP_OK*/true) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    br.close();

                    String tmp = null;
                    char[] arr = sb.toString().toCharArray();
                    for (int i = 0; i < sb.toString().length(); i++) {
                        if (arr[i] != '\n')
                            tmp += arr[i];

                    }

                    // textView1.setText(sb.toString());
                    jsonObject = new JSONObject(sb.toString());

                    //String str2 = jsonObject.getString("address");
                    // str3 = jsonObject.getJSONObject("address").getString(
                    //yt   "neighbourhood");

                    town = str3;
                    internetcon=true;

                }
                else {
                    internetcon=false;
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                internetcon=false;
                statuscon=1;
                ////////////////////////////////////////////////////////////////////////////////////
/*
                Toast toast = Toast.makeText(getApplicationContext(),
                        "catch (IOException e)",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();*/


                e.printStackTrace();

                return str3;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                internetcon=true;
                statuscon=2;

                e.printStackTrace();
                return str3;
///////////////////////////////////////////////////////////////////////////////////////////////////
               /* Toast toast = Toast.makeText(getApplicationContext(),
                        "catch (JSONException e)",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();*/
            }
            return str3;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            boolean gps_en = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean prov_en = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            setstatusaction( getString(R.string.serverconnect));
            pbar.setProgress(30);

		/* if (gps_en)
				location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			else if (prov_en)
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
*/
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            try {
                if(internetcon) {
                    if (!jsonObject.getJSONObject("address").isNull("country"))
                        tcountry.setText(jsonObject.getJSONObject("address").getString("country"));
                    else tcountry.setText("");

	        /*tpostalcode.append(jsonObject.getJSONObject("address").getString("postcode" ));
			 tstate.append(jsonObject.getJSONObject("address").getString("state" ));
			 tcounty.append(jsonObject.getJSONObject("address").getString("county" ));
			 tcity.append(jsonObject.getJSONObject("address").getString("city" ));
			 tcity_district.append(jsonObject.getJSONObject("address").getString("city_district" ));
			 tsuburb.append(jsonObject.getJSONObject("address").getString("suburb" ));
			 tneighbourhood.append(jsonObject.getJSONObject("address").getString("neighbourhood" ));
			 troad.append(jsonObject.getJSONObject("address").getString("road" ));
			 thouse_number.append(jsonObject.getJSONObject("address").getString("house_number" )); */

                    if (!jsonObject.getJSONObject("address").isNull("postcode"))
                        tpostalcode.setText(jsonObject.getJSONObject("address").getString("postcode"));
                    else tpostalcode.setText("");
                    if (!jsonObject.getJSONObject("address").isNull("state"))
                        tstate.setText(jsonObject.getJSONObject("address").getString("state"));
                    else tstate.setText("");
                    if (!jsonObject.getJSONObject("address").isNull("county"))
                        tcounty.setText(jsonObject.getJSONObject("address").getString("county"));
                    else tcounty.setText("");
                    if (!jsonObject.getJSONObject("address").isNull("city"))
                        tcity.setText(jsonObject.getJSONObject("address").getString("city"));
                    else tcity.setText("");
                    if (!jsonObject.getJSONObject("address").isNull("city_district"))
                        tcity_district.setText(jsonObject.getJSONObject("address").getString("city_district"));
                    else tcity_district.setText("");
                    if (!jsonObject.getJSONObject("address").isNull("suburb"))
                        tsuburb.setText(jsonObject.getJSONObject("address").getString("suburb"));
                    else tsuburb.setText("");
                    if (!jsonObject.getJSONObject("address").isNull("neighbourhood"))
                        tneighbourhood.setText(jsonObject.getJSONObject("address").getString("neighbourhood"));
                    else tneighbourhood.setText("");
                    if (!jsonObject.getJSONObject("address").isNull("road"))
                        troad.setText(jsonObject.getJSONObject("address").getString("road"));
                    else troad.setText("");
                    if (!jsonObject.getJSONObject("address").isNull("house_number"))
                        thouse_number.setText(jsonObject.getJSONObject("address").getString("house_number"));
                    else thouse_number.setText("");

                    pbar.setProgress(100);
                    setstatusaction(getString(R.string.data_success));
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getString(R.string.err_nointernet),
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    pbar.setProgress(0);
                    setstatusaction( getString(R.string.err_connect_internet)/*+statuscon*/);

                }
            }


            catch(JSONException e )
            {
                e.printStackTrace();
            }

        }

        TextView settext(TextView  tv, String text)
        {
            tv.setText( tv.getText()+"     "+text);
            return  tv;
        }

    }





}
