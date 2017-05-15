package model;

import control.CVApplication;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.net.ServerSocket;
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
    private SocketChannel makeChannel;

    private volatile SocketChannel threadChannel;

    private String myIpAddress;
    private int myPort;

    private ByteBuffer buffOutput = ByteBuffer.allocate(BuffSize);
    private ByteBuffer buffInput = ByteBuffer.allocate(BuffSize);

    private Group chatWindow;
    private Stage stageForChat;
    private TextArea input;
    private TextArea output;
    private Polygon sendButton;

    private boolean runWhileController;

    private static boolean findConnectionWhileController;

    private int sendReceiveStat = -1;

    public Chat(Group group, Stage stageForChat) {

        this.chatWindow = group;
        this.runWhileController = true;
        this.stageForChat = stageForChat;
        this.input = (TextArea) chatWindow.getChildren().get(9);
        this.output = (TextArea)chatWindow.getChildren().get(10);
        this.sendButton = (Polygon) chatWindow.getChildren().get(14);

        init();
    }

    private void init() {

        findConnectionWhileController = true;

        try {
            InetAddress ip = InetAddress.getLocalHost();
            myIpAddress = ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        findFreePort();

        chatWindow.getChildren().get(12).setOnMouseClicked(event13 -> {
            disconnect();
            CVApplication.isChatWindowOn = Boolean.FALSE;
            chatWindow = null;
            stageForChat.close();
            runWhileController = false;
        });

        chatWindow.getChildren().get(14).setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                sendMessage(makeChannel, input.getText(), true);
                input.clear();
            }
        });

        chatWindow.getChildren().get(9).setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    sendMessage(makeChannel, input.getText(), true);
                    input.clear();
                }
            }
        });
    }

    private void findFreePort() {
        int tryPort = 4444;
        while (true) {
            try {
                new ServerSocket(tryPort);
                myPort = tryPort;
                break;
            } catch (IOException e) {
                tryPort++;
            }
        }
    }

    private void boundListener() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(myIpAddress, myPort));
            serverSocketChannel.configureBlocking(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
     }

    private void process() throws InterruptedException, IOException {

        if (makeChannel == null){
            connect();
        }

        if (makeChannel != null) {
            readChannel(makeChannel);
        }
    }

    private SocketChannel getValidConnection(){

        Thread t0 = new Thread() {
            @Override
            public void run() {
                for (int i = 3333; i < 3338; i++) {
                    threadSleep(SleepTime);
                    try {
                        if(threadChannel != null || !findConnectionWhileController || !CVApplication.isChatWindowOn){
                            break;
                        }
                        threadChannel = SocketChannel.open(new InetSocketAddress("192.168.0.100", i));
                        findConnectionWhileController = false;
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t0.start();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 3333; i < 3338; i++) {
                    threadSleep(SleepTime);
                    try {
                        if(threadChannel != null || !findConnectionWhileController || !CVApplication.isChatWindowOn){
                            break;
                        }
                        threadChannel = SocketChannel.open(new InetSocketAddress("192.168.0.101", i));
                        findConnectionWhileController = false;
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();

        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = 3333; i < 3338; i++) {
                    threadSleep(SleepTime);
                    try {
                        if(threadChannel != null || !findConnectionWhileController || !CVApplication.isChatWindowOn){
                            break;
                        }
                        threadChannel = SocketChannel.open(new InetSocketAddress("192.168.0.102", i));
                        findConnectionWhileController = false;
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.start();

        Thread t3 = new Thread() {
            @Override
            public void run() {
                for (int i = 3333; i < 3338; i++) {
                    threadSleep(SleepTime);
                    try {
                        if(threadChannel != null || !findConnectionWhileController || !CVApplication.isChatWindowOn){
                            break;
                        }
                        threadChannel = SocketChannel.open(new InetSocketAddress("192.168.0.103", i));
                        findConnectionWhileController = false;
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t3.start();

        Thread t4 = new Thread() {
            @Override
            public void run() {
                for (int i = 3333; i < 3338; i++) {
                    threadSleep(SleepTime);
                    try {
                        if(threadChannel != null || !findConnectionWhileController || !CVApplication.isChatWindowOn){
                            break;
                        }

                        threadChannel = SocketChannel.open(new InetSocketAddress("192.168.0.104", i));
                        findConnectionWhileController = false;
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t4.start();

        threadSleep(1000);

        return threadChannel;
    }

    private void connect() throws InterruptedException, IOException {
        try {
            if (serverSocketChannel == null){
                boundListener();
            }

            makeChannel = getValidConnection();
            threadSleep(SleepTime);

            if (makeChannel != null) {
                sendMessage(makeChannel,  "ONLINE".toUpperCase() + "\n", false);
                sendButton.setFill(Color.rgb(127,255,0));
            }

        } catch (Exception e) {
            throw new InterruptedException();
        }
    }

    private int readChannel(SocketChannel ch) throws IOException {

        int readBytesNum = 0;
        if (!ch.isOpen() || ch.socket().isClosed() || !ch.socket().isBound() ) {
            return readBytesNum;
        }

        try {
            while ((readBytesNum = ch.read(buffInput)) > 0) {
                String receivedMessage = buffToString(buffInput, readBytesNum);

                if (receivedMessage.equals(DisconnectMessage)){
                    disconnect();
                } else {
                    if (!receivedMessage.equals(DisconnectMessage)) {
                        output.setStyle("-fx-text-fill: chartreuse");
                        output.appendText("\n" + (sendReceiveStat != 0 ? "\tDMITRIY_LYASHENKO:\n" : "") + receivedMessage);
                        setSendReceiveStat(0);
                    } else {
                        output.setStyle("-fx-text-fill: chartreuse");
                        output.appendText(DisconnectMessage);
                        disconnect();
                    }
                }
                buffInput.clear();
            }
        } catch (ClosedChannelException e) {
            disconnect();
            throw e;
        }
        return readBytesNum;
    }

    @Override
    public void run() {
        boundListener();
        while (runWhileController) {
            try {
                threadSleep(SleepTime);
                process();
            } catch (Exception e) {
                sendButton.setFill(Color.rgb(255, 40, 0));
                setSendReceiveStat(-1);
                makeChannel = null;
                threadChannel = null;
                e.printStackTrace();
            }
        }
        disconnect();
    }

    private void disconnect() {
        try {
            if (makeChannel != null && makeChannel.isOpen()) {
                sendMessage(makeChannel, DisconnectMessage, false);
                makeChannel.close();
            }

            if (threadChannel != null && threadChannel.isOpen()) {
                threadChannel.close();
            }

            if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
                serverSocketChannel.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            makeChannel = null;
            serverSocketChannel = null;
            threadChannel = null;
            findConnectionWhileController = true;
        }
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
                    output.setStyle("-fx-text-fill: white");
                    output.appendText((sendReceiveStat != 1 ? "\n\tYOU:" : "") + "\t\n" +
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

    public void threadSleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSendReceiveStat(int sendReceiveStat) {
        this.sendReceiveStat = sendReceiveStat;
    }

    public SocketChannel getThreadChannel() {
        return threadChannel;
    }

    public void setThreadChannel(SocketChannel threadChannel) {
        this.threadChannel = threadChannel;
    }

}
