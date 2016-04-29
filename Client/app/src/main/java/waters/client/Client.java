package waters.client;

        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.OutputStreamWriter;
        import java.io.PrintWriter;
        import java.net.InetAddress;
        import java.net.Socket;
        import java.net.UnknownHostException;

        import android.app.Activity;
        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.wifi.WifiManager;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import java.util.ArrayList;
        import java.util.Locale;

        import android.app.Activity;
        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.os.Bundle;
        import android.speech.RecognizerIntent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import static java.lang.Thread.sleep;

public class Client extends Activity {

    private Socket socket;
    private TextView txtSpeechInput;
//    private
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private WifiManager wifiManager;
    public static String voice = "";

    private static final int SERVERPORT = 5010;
    private static final String SERVER_IP = "192.168.43.1";

    static boolean gotResult = false;

    EditText input;
//    EditText adfName;
//    EditText adfNameLoad;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forwardButton = (Button) findViewById(R.id.forwardButton);
        forwardButton.setOnClickListener(forwardListener);

        Button leftButton = (Button) findViewById(R.id.leftButton);
        leftButton.setOnClickListener(leftListener);

        Button rightButton = (Button) findViewById(R.id.rightButton);
        rightButton.setOnClickListener(rightListener);

        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(stopListener);

        Button reverseButton = (Button) findViewById(R.id.reverseButton);
        reverseButton.setOnClickListener(reverseListener);

        Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(connectListener);

        Button recordADFButton = (Button) findViewById(R.id.recordADFButton);
        recordADFButton.setOnClickListener(recordADFButtonListener);

        Button saveLandmarkButton = (Button) findViewById(R.id.saveLandmarkButton);
        saveLandmarkButton.setOnClickListener(saveLandmarkButtonListener);

        Button goToLandmark = (Button) findViewById(R.id.goToLandmark);
        goToLandmark.setOnClickListener(goToLandmarkListener);

//        Button goToL2Button = (Button) findViewById(R.id.goToL2Button);
//        goToL2Button.setOnClickListener(goToL2ButtonListener);

//        Button btnSpeak = (Button) findViewById(R.id.btnSpeak);
//        btnSpeak.setOnClickListener(btnSpeakListener);

        Button saveADF = (Button) findViewById(R.id.saveADF);
        saveADF.setOnClickListener(saveADFListener);

        Button loadADF = (Button) findViewById(R.id.loadADF);
        loadADF.setOnClickListener(loadADFListener);

        Button startSafePath = (Button) findViewById(R.id.startSafePath);
        startSafePath.setOnClickListener(startSafePathListener);

        Button stopSafePath = (Button) findViewById(R.id.stopSafePath);
        stopSafePath.setOnClickListener(stopSafePathListener);

        input = (EditText)findViewById(R.id.inputData);
//        adfName = (EditText)findViewById(R.id.adfName);
//        adfNameLoad = (EditText)findViewById(R.id.adfNameLoad);

        new Thread(new ClientThread()).start();
    }

//    View.OnClickListener btnSpeakListener =
//            new View.OnClickListener(){
//
//                @Override
//                public void onClick(View v) {
//                    PrintWriter out = null;
//
//                    wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
//                    //System.out.println("here");
//
//                    wifiManager.setWifiEnabled(false);
//
//                    promptSpeechInput();
//
//                    try {
//                        sleep(15000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    wifiManager.setWifiEnabled(true);
//
//                    new Thread (new ClientThread()).start();
//
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    out.println("c");
//                    System.out.println("voice: "+ voice);
//
//                    if(voice.equals("go to Landmark one")){
////                        PrintWriter out = null;
////                        try {
////                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
//                        System.out.println("out: " + out);
//                        System.out.println("L1 1");
//                        out.println("goto1");
//                    }else if(voice.equals("go to Landmark 1")){
////                        PrintWriter out = null;
////                        try {
////                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
//                        System.out.println("out: " + out);
//                        System.out.println("L1 2");
//                        out.println("goto1");
//                    }else if(voice.equals("go to Landmark two")){
////                        PrintWriter out = null;
////                        try {
////                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
//                        System.out.println("out: " + out);
//                        System.out.println("L2 1");
//                        out.println("goto2");
//                    }else if(voice.equals("go to Landmark 2")){
////                        PrintWriter out = null;
////                        try {
////                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
//                        System.out.println("out: " + out);
//                        System.out.println("L2 2");
//                        out.println("goto2");
//                    }else{
//                        System.out.println("Error3");
//                    }
//
//                }};
//
    View.OnClickListener startSafePathListener=
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("startSafePathRecord");
                }};

    View.OnClickListener stopSafePathListener=
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("stopSafePathRecord");
                }};

    View.OnClickListener loadADFListener=
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String adf = input.getText().toString();
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("adfLoad " + adf);
                }};

    View.OnClickListener saveADFListener=
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String adf = input.getText().toString();
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("adfSave " + adf);
                }};

    View.OnClickListener goToLandmarkListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String name = input.getText().toString();
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("goto " + name);
                }};

//    View.OnClickListener goToL2ButtonListener =
//            new View.OnClickListener(){
//
//                @Override
//                public void onClick(View v) {
//                    String name = input.getText().toString();
//                    PrintWriter out = null;
//                    try {
//                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    out.println("goto2");
//                }};

    View.OnClickListener saveLandmarkButtonListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String name = input.getText().toString();
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("save " + name);
                }};

    View.OnClickListener recordADFButtonListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("recordADF");
                }};

    View.OnClickListener forwardListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("f");
                }};

    View.OnClickListener leftListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("l");
                }};

    View.OnClickListener rightListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("r");
                }};

    View.OnClickListener stopListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("s");
                }};

    View.OnClickListener connectListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("c");
                }};

    View.OnClickListener reverseListener =
            new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("b");
                }};

//    public void onClick(View view) {
//        try {
//            EditText et = (EditText) findViewById(R.id.EditText01);
//            String str = et.getText().toString();
//            PrintWriter out = new PrintWriter(new BufferedWriter(
//                    new OutputStreamWriter(socket.getOutputStream())),
//                    true);
//            out.println(str);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void setVoice(String voice){
//        this.voice = voice;
//    }

    /**
     * Showing google speech input dialog
//     * */
//    private void promptSpeechInput() {
//        //System.out.println("here");
//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//
//        try {
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException a) {
//            System.out.println("error");
//        }
//    }
//
//    /**
//     * Receiving speech input
//     * */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        switch (requestCode) {
//            case REQ_CODE_SPEECH_INPUT: {
//                if (resultCode == RESULT_OK && null != data) {
//                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    //txtSpeechInput.setText(result.get(0));
//
//                    //use the result.get(0) to control robot here...
//
////                    voice = result.get(0);
//                    Client.voice = result.get(0);
//
//
//                }
//                break;
//            }
//
//        }
//        Client.gotResult = true;
//    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}