package tangoremotecontrol.tangoremotecontrol;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TangoWiFiControl extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */

    byte[] forward = new byte[]{'f'};
    byte[] left = new byte[]{'l'};
    byte[] right = new byte[]{'r'};
    byte[] stop = new byte[]{'s'};
    byte[] reverse = new byte[]{'b'};
    byte[] connecttoSerial = new byte[] {'c'};

    String ip = "0.0.0.0";
    int port = 5010;

    Socket toTango;
    OutputStream outputStream;

    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };


    //----------------------------------------------------------------------------------------------
    private final View.OnTouchListener forwardListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                outputStream.write(forward);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("forward");
            return false;
        }
    };
    private final View.OnTouchListener leftListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                outputStream.write(left);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("left");
            return false;
        }
    };
    private final View.OnTouchListener rightListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                outputStream.write(right);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("right");
            return false;
        }
    };
    private final View.OnTouchListener stopListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                outputStream.write(stop);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("stop");
            return false;
        }
    };
    private final View.OnTouchListener reverseListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                outputStream.write(reverse);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("reverse");
            return false;
        }
    };

    private final View.OnTouchListener connectListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                outputStream.write(reverse);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                toTango = new Socket(ip, port);
                outputStream = toTango.getOutputStream();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Connection Failed.");
            }

            if(toTango != null) {
                TextView text = (TextView) findViewById(R.id.connectedText);
                text.setText("connected");
            }else{
                System.out.println(toTango);
            }
            return false;
        }
    };

    private final View.OnTouchListener connectSerialListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            try {
                outputStream.write(connecttoSerial);
            } catch (IOException e) {
                System.out.println("Connection to robot failed.");
            }
            return false;
       }
    };

    //---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tango_wi_fi_control);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        //show();

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });

//--------------------------------------------------------------------------------------------------


        findViewById(R.id.forwardButton).setOnTouchListener(forwardListener);
        findViewById(R.id.leftButton).setOnTouchListener(leftListener);
        findViewById(R.id.rightButton).setOnTouchListener(rightListener);
        findViewById(R.id.stopButton).setOnTouchListener(stopListener);
        findViewById(R.id.reverseButton).setOnTouchListener(reverseListener);
        findViewById(R.id.connectButton).setOnTouchListener(connectListener);
        findViewById(R.id.conToSerial).setOnTouchListener(connectSerialListener);

    }
//--------------------------------------------------------------------------------------------------


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
