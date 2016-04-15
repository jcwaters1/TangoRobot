package waters.client;

        import java.io.BufferedWriter;
        import java.io.IOException;
        import java.io.OutputStreamWriter;
        import java.io.PrintWriter;
        import java.net.InetAddress;
        import java.net.Socket;
        import java.net.UnknownHostException;

        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

public class Client extends Activity {

    private Socket socket;

    private static final int SERVERPORT = 5010;
    private static final String SERVER_IP = "192.168.43.1";

    byte[] f = new byte[]{'f'};
    byte[] l = new byte[]{'l'};
    byte[] r = new byte[]{'r'};
    byte[] b = new byte[]{'b'};
    byte[] s = new byte[]{'s'};
    byte[] c = new byte[]{'c'};



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

        new Thread(new ClientThread()).start();
    }

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
                    out.println(f);
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
                    out.println(l);
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
                    out.println(r);
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
                    out.println(s);
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
                    out.println(c);
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
                    out.println(b);
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