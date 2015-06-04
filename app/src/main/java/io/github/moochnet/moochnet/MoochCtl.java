package io.github.moochnet.moochnet;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.stericson.RootShell.RootShell;
import com.stericson.RootShell.exceptions.RootDeniedException;
import com.stericson.RootShell.execution.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class MoochCtl extends ActionBarActivity {
    private static String DEBUG = "io.github.moochnet.moochnet";
    private static ArrayList<String> GATE = new ArrayList<String>(){{
        add("10.0.0.0");
        add("10.0.0.1");
        add("10.1.0.1");
        add("10.0.1.1");
        add("192.168.0.0");
        add("192.168.0.1");
        add("192.168.1.0");
        add("192.168.1.1");
        add("192.168.0.2");
        add("xfinity.nnu.com");
    }};
    private static String charset = "UTF-8";
    private CookieHandler cookie = null;
    private GenDeets moochClass = new GenDeets();
    private Switch randomizeMac;
    private Switch randomizeEmail;
    private Switch randomizeZip;
    private Button moochNet;
    private EditText uMac;
    private EditText uEmail;
    private EditText uZip;
    private Boolean doRandomMac;
    private Boolean doRandomEmail;
    private Boolean doRandomZip;
    private String setTextMac(){
        String t = null;
        if(getDoRandomMac()) {
            t = moochClass.genMacAddress();
        }else{
            t = moochClass.getrMAC();
        }
        uMac.setText(t);
        return t;
    }
    private String setTextEmail(){
        String t = null;
        if(getDoRandomEmail()){
            t = moochClass.genEmailAddress();
        }else{
            t = moochClass.getrEmail();
        }
        uEmail.setText(t);
        return t;
    }
    private String setTextZip(){
        String t = null;
        if(getDoRandomZip()){
            t = moochClass.genZipCode();
        }else{
            t = moochClass.getrZip();
        }
        uZip.setText(t);
        return t;
    }
    private Boolean getDoRandomMac(){
        return doRandomMac;
    }
    private Boolean getDoRandomEmail(){
        return doRandomEmail;
    }
    private Boolean getDoRandomZip(){
        return doRandomZip;
    }
    private void setAllListeners(){
        randomizeMac = (Switch) findViewById(R.id.rndmac);
        if(randomizeMac!=null){
            randomizeMac.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    doRandomMac = randomizeMac.isChecked();
                }
            });
        }
        randomizeEmail = (Switch) findViewById(R.id.rndemail);
        if(randomizeEmail!=null){
            randomizeEmail.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    doRandomEmail = randomizeEmail.isChecked();
                }
            });
        }
        randomizeZip = (Switch) findViewById(R.id.rndzip);
        if(randomizeZip!=null){
            randomizeZip.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    doRandomZip = randomizeZip.isChecked();
                }
            });
        }
        moochNet = (Button) findViewById(R.id.buttonmch);
        if(moochNet!=null){
            moochNet.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    mooch();
                }
            });
        }
        uMac = (EditText) findViewById(R.id.dspmac);
        uEmail = (EditText) findViewById(R.id.dspemail);
        uZip = (EditText) findViewById(R.id.dspzip);
    }
    private void ActionMac(String gendmac){
        StringBuilder stemp = new StringBuilder(128);
        stemp.append("busybox ifconfig wlan0 hw ether ");
        stemp.append(gendmac);
        WifiManager wifiMan = (WifiManager) this.getSystemService( Context.WIFI_SERVICE );
        boolean wifiWasEnabled = wifiMan.isWifiEnabled();
        if ( wifiWasEnabled ) {
            wifiMan.setWifiEnabled(false);
        }
        try {
            Command command = new Command(0, stemp.toString());
            RootShell.getShell(true).runRootCommand(command);
        } catch (IOException e) {
            Log.e(DEBUG, e.getMessage());
        } catch (TimeoutException e) {
            Log.e(DEBUG, e.getMessage());
        } catch (RootDeniedException e) {
            Log.e(DEBUG, e.getMessage());
        }
        if ( wifiWasEnabled ) {
            wifiMan.setWifiEnabled(true);
        }
    }
    private void actionPost(String url, String query){
        URLConnection connection = null;
        InputStream response = null;
        cookie.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        try {
            connection = new URL(url).openConnection();
        }catch (MalformedURLException e){
            Log.e(DEBUG, e.getMessage());
        }catch (IOException e){
            Log.e(DEBUG, e.getMessage());
        }
        if(connection!=null){
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A"); // Do as if you're using Firefox 3.6.3.
            try (OutputStream output = connection.getOutputStream()) {
                output.write(query.getBytes(charset));
            } catch (IOException e) {
                Log.e(DEBUG, e.getMessage());
            }
            try {
                response = connection.getInputStream();
            } catch (IOException e) {
                Log.e(DEBUG, e.getMessage());
            }
        }
        Map.Entry<String, List<String>> HEADERS = null;
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            String k = header.getKey();
            List<String> v = header.getValue();
            List<String> a = new ArrayList<>();
            a.add(k); a.addAll(v);
            HEADERS.setValue(a);
        }
        encodeResponse(connection, response);
        setCookie(connection);
    }
    private void setCookie(URLConnection connection){
        List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
        // Then use the same cookies on all subsequent requests.
        for (String cookie : cookies) {
            connection.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
        }
    }
    private void encodeResponse(URLConnection connection, InputStream response){
        String contentType = connection.getHeaderField("Content-Type");
        String charset = null;
        for (String param : contentType.replace(" ", "").split(";")) {
            if (param.startsWith("charset=")) {
                charset = param.split("=", 2)[1];
                break;
            }
        }
        if (charset != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null;) {
                    // ... System.out.println(line) ?
                }
            } catch (UnsupportedEncodingException e){
                Log.e(DEBUG, e.getMessage());
            } catch (IOException e){
                Log.e(DEBUG, e.getMessage());
            }
        }
        else {
            // It's likely binary content, use InputStream/OutputStream.
        }
    }
    private void ActionEmail(String gendemail, String gendzip){
        ArrayList<String> httpors = new ArrayList<>();
        httpors.add("http://");
        httpors.add("https://");
        String url = null;
        String paramemail = gendemail;
        String paramzip = gendzip;
        String query = null;
        String path = "/xfinitywifi/main";
        for(int x=0; x<httpors.size(); x++){
            for(int y=0; y<GATE.size();y++){
                url = httpors.get(x) + GATE.get(y) + path;
                try {
                    query = String.format("spn_postal=%s&spn_email=%s&spn_terms=%s",
                            URLEncoder.encode(paramzip, charset),
                            URLEncoder.encode(paramemail, charset),
                            URLEncoder.encode("checked", charset));
                }catch(UnsupportedEncodingException e){
                    Log.e(DEBUG, e.getMessage());
                }
                actionPost(url, query);
            }
        }
    }
    private void mooch(){
        ActionMac(setTextMac());
        ActionEmail(setTextEmail(), setTextZip());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mooch_ctl);
        setAllListeners();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mooch_ctl, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
