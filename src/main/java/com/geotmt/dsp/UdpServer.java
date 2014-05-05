package com.geotmt.dsp;

import org.apache.http.protocol.HTTP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2014/4/23.
 */
public class UdpServer implements Runnable {
    private int port;
    private DatagramSocket server_sock;
    private DatagramPacket pac;
    private byte recv_buffer[];
    private String recv_string;

    public UdpServer(int port) {
        this.port = port;
        init();
    }

    public void init() {
        try {
            server_sock = new DatagramSocket(new InetSocketAddress(port));
            recv_buffer = new byte[100];//接收缓冲区，byte型
            pac = new DatagramPacket(recv_buffer, recv_buffer.length);//构造一个packet
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) { // 一直监听
                server_sock.receive(pac);//阻塞式接收数据
                //将byte[]转化成string
                recv_string = new String(recv_buffer, 0, pac.getLength(),HTTP.UTF_8);
                //System.out.println(recv_string);
                String[] request;
                String ip,result="";
                if (recv_string != null) {
                    request = recv_string.split("\\+");
                    if (request[0].equals("tag")) {
                        ip=request[1];
                        result = DmpUtility.getTagData(ip);
                    }
                    if (request[0].equals("key")) {
                        ip=request[1];
                        result = DmpUtility.getKeyData(ip);
                    }
                    if (request[0].equals("url")) {
                        ip=request[1];
                        result = DmpUtility.getUrlData(ip);
                    }
                    int port = pac.getPort();
                    InetAddress addr = pac.getAddress();
                    byte[] sendBuf;
                    sendBuf = result.getBytes(HTTP.UTF_8);
                    DatagramPacket sendPacket
                            = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
                    server_sock.send(sendPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port =30001;
        new Thread(new UdpServer(port)).start();
    }
}
