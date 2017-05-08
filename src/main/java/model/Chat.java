package model;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.IOException;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Solyk on 08.05.2017.
 */
public class Chat implements ChatProperties, Runnable{

    private ServerSocketChannel serverSocketChannel;
    private SocketChannel clSocketChannel;
    private SocketChannel makeChannel;

    private String myIpAddress;
    private int myPort;

    private String toIpAddress;
    private int toPort;

    private ByteBuffer buffOutput = ByteBuffer.allocate(BuffSize);
    private ByteBuffer buffInput = ByteBuffer.allocate(BuffSize);

    private Group chatWindow;

    private int sendReceiveStat = -1;
    private String partnerName = null;

    public Chat(Group group) {

        this.chatWindow = group;

        try {
            serverSocketChannel = ServerSocketChannel.open();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        init();
    }

    private void init() {
        toIpAddress = "192.168.0.103";

        try {
            InetAddress ip = InetAddress.getLocalHost();
            myIpAddress = ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        myPort = 4444;

//        try {
//            myPort = create(new int[] { 4444, 4584, 4843 });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        otherInit();
    }

//    private int create(int[] ports) throws IOException {
//        for (int port : ports) {
//            try {
//                new ServerSocket(port);
//                return port;
//            } catch (IOException ex) {
//
//            }
//        }
//        throw new IOException("no free port found");
//    }

    private void boundListener() {
        try {
            if (serverSocketChannel.socket().isBound()) {
                serverSocketChannel.close();
            }
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(myIpAddress, myPort));
            serverSocketChannel.configureBlocking(false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
     }

    private void process() throws InterruptedException, IOException {

        if (makeChannel != null) {
            readChannel(makeChannel);
            return;
        }

        if (clSocketChannel == null) {
            clSocketChannel = serverSocketChannel.accept();
            Thread.sleep(SleepTime);
            if (clSocketChannel != null) {
                sendMessage(clSocketChannel, Delimiter + ("Connection with " + " successfully established on \"").toUpperCase() +
                         "\"\n", false);
            }
        }

        if (clSocketChannel != null) {
            readChannel(clSocketChannel);
        }

    }

    private int readChannel(SocketChannel ch) throws IOException {

        int readBytesNum = 0;
        if (!ch.isOpen() || ch.socket().isClosed() || !ch.socket().isBound()) {
            return readBytesNum;
        }

        try {
            while ((readBytesNum = ch.read(buffInput)) > 0) {
                String receivedMessage = buffToString(buffInput, readBytesNum);
                System.out.println(receivedMessage);
                if (partnerName == null) {
                    String[] connectionMessage = receivedMessage.split(Delimiter);
                    if (receivedMessage.contains(Delimiter) && connectionMessage.length > 1) {
                        partnerName = connectionMessage[0];
                        TextArea out = (TextArea)chatWindow.getChildren().get(10);
                        out.appendText("\n" + connectionMessage[1]);
                    }
                    else {
                        TextArea out = (TextArea)chatWindow.getChildren().get(10);
                        out.appendText("\nINVALID CONNECTION MESSAGE FORMAT RECEIVED.");
                    }
                }
                else {
                    if (!receivedMessage.equals(DisconnectMessage)) {
                        TextArea out = (TextArea)chatWindow.getChildren().get(10);
                        out.appendText("\n" + (sendReceiveStat != 0 ? " says:\n" : "") +  receivedMessage);
                        setSendReceiveStat(0);
                    }
                    else {
                        TextArea out = (TextArea)chatWindow.getChildren().get(10);
                        out.appendText(DisconnectMessage);

                        disconnect(false);
                    }
                }
                buffInput.clear();
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
        return readBytesNum;
    }

    @Override
    public void run() {
        System.out.println("Thread run");
        boundListener();
        boolean whileController = true;
        while (whileController) {
            try {
                process();
                if (makeChannel == null){
                    connect();
                }
            } catch (Exception e) {
                disconnect(true);
                whileController = false;
            }
        }
        System.out.println("Run coplite");
    }

    public void disconnect(boolean verbose) {

        try {
            if (makeChannel != null && makeChannel.isOpen()) {
                if (verbose) {
                    sendMessage(makeChannel, DisconnectMessage, false);
                }
                makeChannel.close();
            }
            if (clSocketChannel != null && clSocketChannel.isOpen()) {
                if (verbose) {
                    sendMessage(clSocketChannel, DisconnectMessage, false);
                }
                clSocketChannel.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            makeChannel = null;
            clSocketChannel = null;

            Polygon sendB = (Polygon) chatWindow.getChildren().get(14);
            sendB.setFill(Color.rgb(255,40,0));
            setSendReceiveStat(-1);
        }
    }

    private void connect() throws InterruptedException, IOException {
        toPort = 3333;
            try {
                makeChannel = SocketChannel.open(new InetSocketAddress(toIpAddress, toPort));
            } catch (Exception e) {
                throw new InterruptedException();
            }

        Polygon sendB = (Polygon) chatWindow.getChildren().get(14);
        sendB.setFill(Color.rgb(0,255,169));
    }

    private void sendMessage(SocketChannel channel, String message, boolean verbose) {

        if (channel == null || message == null) {
            return;
        }

        buffOutput.clear();
        buffOutput.put(message.getBytes());
        buffOutput.flip();

        try {
            while (buffOutput.hasRemaining()) {
                channel.write(buffOutput);
                if (verbose) {
                    TextArea out = (TextArea)chatWindow.getChildren().get(10);
                    out.appendText((sendReceiveStat != 1 ? "\n\tYour " + "answer:":"") + "\n\t" +
                            buffToString(buffOutput, buffOutput.limit()));
                    setSendReceiveStat(1);
                }
            }

            buffOutput.flip();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void otherInit() {

        chatWindow.getChildren().get(14).setOnMouseClicked(new EventHandler<MouseEvent>(){
            TextArea input = (TextArea) chatWindow.getChildren().get(9);
            @Override
            public void handle(MouseEvent event) {
                sendMessage(makeChannel != null ? makeChannel : clSocketChannel, input.getText(), true);
                input.clear();
            }
        });
    }

    public void setSendReceiveStat(int sendReceiveStat) {
        this.sendReceiveStat = sendReceiveStat;
    }
}
