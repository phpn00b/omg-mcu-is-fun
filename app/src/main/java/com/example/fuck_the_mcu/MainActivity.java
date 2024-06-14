package com.example.fuck_the_mcu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

import android_serialport_api.SerialPort;

public class MainActivity extends AppCompatActivity {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final char Space = ' ';
    private Thread threadRunner;
    private AtomicBoolean keepFuckingAround = new AtomicBoolean(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        threadRunner = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SerialPort port = new SerialPort(new File("/dev/ttyS4"), 115200, 0, false);
                    InputStream stream = port.getInputStream();
                    while (keepFuckingAround.get()) {
                        int length = stream.available();
                        if (length > 0) {
                            byte[] buffer = new byte[length];
                            stream.read(buffer);
                            if (buffer[0] == 0x01)
                                System.out.println("OMG ASCII: " + new String(buffer, StandardCharsets.US_ASCII));
                            else
                                System.out.println("NON ASCII: " + bytesToHex(buffer));
                        }
                    }
                    port.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        threadRunner.start();
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    protected void onDestroy() {
        keepFuckingAround.set(false);
        try {
            threadRunner.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        super.onDestroy();
    }
}