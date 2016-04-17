package com.yantur.conngoogleplayservices;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Artur on 4/17/2016.
 */
public class BackgroundRequest extends AsyncTask<String, Void, String> {
    Context context;
    TextView textView;
    ProgressDialog dialog;

    public BackgroundRequest(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Please wait...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... urls) {

        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urls[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStreamReader is = new InputStreamReader(httpURLConnection.getInputStream());
            BufferedReader br = new BufferedReader(is);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            sb.setLength(0);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject_i = jsonArray.getJSONObject(i);
                sb.append("TITLE: " + jsonObject_i.getString("title") + "\n");
                sb.append("URL: " + jsonObject_i.getString("link") + "\n");
                sb.append("CONTENT: " + jsonObject_i.getString("snippet") + "\n\n");
            }

            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String output) {
        super.onPostExecute(output);
        dialog.dismiss();
        textView.setText(output);
    }
}
