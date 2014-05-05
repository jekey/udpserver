package com.geotmt.dsp;

/**
 * Created by Administrator on 2014/4/23.
 */
public class Test  {
    public static void main(String[] args) throws InterruptedException {
        int port = 11111;
        new Thread(new UdpServer(port)).start();// start udp server,listen request
        new Thread(new UdpClient(port)).start();// start udp client,send data
    }
}
