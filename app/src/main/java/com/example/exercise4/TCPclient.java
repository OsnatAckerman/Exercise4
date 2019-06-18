package com.example.exercise4;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TCPclient {

    private static TCPclient self = null;
    private Socket socket;
    private static final Lock mutex = new ReentrantLock(true);

    private TCPclient() {
    }

    public static TCPclient getInstance() {
        mutex.lock();
        if (self == null) {
            self = new TCPclient();
        }
        mutex.unlock();
        return self;
    }

    public void connect(final String ip, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mutex.lock();
                try {
                    InetAddress serverAddr = InetAddress.getByName(ip);
                    socket = new Socket(serverAddr, port);
                    System.out.println("connect");
                } catch (IOException e) {
                    System.out.println((e.toString()));
                } finally {
                    mutex.unlock();
                }
            }
        }).start();
    }

    public void send(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mutex.lock();
                try {
                    if (socket != null) {
                        DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
                        outToServer.writeUTF(msg);
                        outToServer.flush();
                        System.out.println(msg);

                    }
                } catch (IOException e) {
                    System.out.println((e.toString()));
                } finally {
                    mutex.unlock();
                }
            }
        }).start();
    }

    public void disconnect() {
        mutex.lock();
        try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println((e.toString()));
        }
        mutex.unlock();
    }

}