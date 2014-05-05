package com.geotmt.dsp;

import org.apache.http.protocol.HTTP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2014/4/23.
 */
public class UdpClient implements Runnable {
    private int port;
    private DatagramSocket client;
    private DatagramPacket data;
    private InetSocketAddress isa;

    public UdpClient(int port) {
        this.port = port;
    }

    public void run() {
        try {
            for(int i=0;i<100;i++) {
                client = new DatagramSocket();
                String request = "key+10.111.31.14";
                byte[] bs = request.getBytes(HTTP.UTF_8);
                isa = new InetSocketAddress("localhost", port);
                data = new DatagramPacket(bs, bs.length);
                data.setSocketAddress(isa);
                data.setData(bs);
                client.send(data);
                byte[] recvBuf = new byte[1024];
                DatagramPacket recvPacket
                        = new DatagramPacket(recvBuf, recvBuf.length);
                client.receive(recvPacket);
                String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength(), HTTP.UTF_8);
                System.out.println("收到:" + recvStr);
                //Thread.sleep(1000);
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}