package com.example.swainstha.roomies;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    private Socket socket;
    String urlString = "https://rocky-forest-87980.herokuapp.com/";
    private String channelID = "CHANNEL_ID_FROM_YOUR_SCALEDRONE_DASHBOARD";
    private String roomName = "observable-room";
    private EditText editText;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private ImageButton sendButton;
    MemberData data;
    String name = "";
    String color = "";
    boolean belongsToCurrentUser = false;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        name = getRandomName();
        color = getRandomColor();
        data = new MemberData(name, color);
        initSocket();

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        editText = (EditText) view.findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(getContext());
        messagesView = (ListView) view.findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        sendButton = view.findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                if (message.length() > 0) {
                    String result = "";
                    try {
                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.accumulate("name", name);
                            jsonObject.accumulate("color", color);
                            jsonObject.accumulate("message", message);
                        } catch(JSONException je) {
                            Log.i("MESSSAGE","JSON exception");
                        }

                        SendMessageClass sendMessageClass = new SendMessageClass();
                        result = sendMessageClass.execute(jsonObject).get();
                    } catch(InterruptedException ie) {
                        Log.i("MESSSAGE","Interrupted exception");
                        ie.printStackTrace();
                    }catch(ExecutionException ee) {
                        Log.i("MESSSAGE","execution exception");
                        ee.printStackTrace();
                    }
                    Log.i("Message Sent", result);
                    editText.getText().clear();
//                    MemberData data = new MemberData();
//                    Message msg = new Message(message,data,true);
//                    messageAdapter.add(msg);
//                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            }
        });
    }

    public void initSocket() {

        //creating a socket to connect to the node js server using socket.io.client
        try {
            //check for internet connection
            if (!isOnline()) {
                throw new Exception();
            }

            socket = IO.socket(urlString);
            socket.connect(); //connecting to the server

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            }).on("chatFromServer", new Emitter.Listener() {

                @Override
                public void call(Object... args) {

                    final Message message;
                    try {
                        JSONObject received = (JSONObject) args[0];
                        MemberData memberData = new MemberData(received.getString("name"),received.getString("color"));
                        if(memberData.getName().equals(name)) {
                            belongsToCurrentUser = true;
                        } else {
                            belongsToCurrentUser = false;
                        }
                        message = new Message(received.getString("message"), memberData,belongsToCurrentUser);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageAdapter.add(message);
                            messagesView.setSelection(messagesView.getCount() - 1);
                        }
                    });
                    } catch (JSONException je) {
                        Log.i("Received","Error parsing received message");
                    }
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                }

            });
        } catch (URISyntaxException e) {
            Log.i("INFO", "Uri syntax exception");
        } catch (Exception e) {
            Log.i("INFO", "No internet connection");
            //Toast.makeText(this, "No Internet", Toast.LENGTH_LONG).show();
        }
    }

    //check if the device is connected to the internet
    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        //check if the connection id wifi or mobile data
        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        return isConnected;

    }

    public class SendMessageClass extends AsyncTask<JSONObject, Void, String> {
        @Override
        protected String doInBackground(JSONObject... urls) {

            try {
                //sending message to server
                socket.emit("chatToServer",urls[0]);
                Log.i("INFO","chatme" + " " + urls[0].toString());
                return "Success";

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("INFO","Failed Sending. May be no internet connection");
                return "Failed";
            }
        }

    }


    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }

}
