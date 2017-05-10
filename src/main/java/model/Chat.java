package model;

import control.CVApplication;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Solyk on 08.05.2017.
 */
public class Chat implements ChatProperties, Runnable{

    private ServerSocketChannel serverSocketChannel;
    private SocketChannel clientChannel;
    private SocketChannel makeChannel;

    private String myIpAddress;
    private int myPort;

    private String toIpAddress;
    private int toPort;

    private ByteBuffer buffOutput = ByteBuffer.allocate(BuffSize);
    private ByteBuffer buffInput = ByteBuffer.allocate(BuffSize);

    private Group chatWindow;
    private Stage stageForChat;
    private TextArea input;
    private TextArea output;
    private Polygon sendButton;

    private boolean runWhileController;

    private int sendReceiveStat = -1;
    private String partnerName = null;


    public Chat(Group group, Stage stageForChat) {

        this.chatWindow = group;
        this.runWhileController = true;
        this.stageForChat = stageForChat;
        this.input = (TextArea) chatWindow.getChildren().get(9);
        this.output = (TextArea)chatWindow.getChildren().get(10);
        this.sendButton = (Polygon) chatWindow.getChildren().get(14);

        try {
            serverSocketChannel = ServerSocketChannel.open();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        init();
    }

    private void init() {
        toIpAddress = "192.168.0.100";

        try {
            InetAddress ip = InetAddress.getLocalHost();
            myIpAddress = ip.getHostAddress();
            System.out.println(myIpAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        myPort = 4444;

        chatWindow.getChildren().get(12).setOnMouseClicked(event13 -> {
            disconnect();
            CVApplication.isChatWindowOn = false;
            chatWindow = null;
            stageForChat.close();
            runWhileController = false;
        });

        chatWindow.getChildren().get(14).setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                sendMessage(makeChannel != null ? makeChannel : clientChannel, input.getText(), true);
                input.clear();
            }
        });
    }

    private void boundListener() {
        try {
            if (serverSocketChannel.socket().isBound()) {
                serverSocketChannel.socket().close();
                serverSocketChannel.close();
            }
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
            return;
        }
//
//        if (clientChannel == null) {
//            clientChannel = serverSocketChannel.accept();
//            Thread.sleep(SleepTime);
//            if (clientChannel != null) {
//                sendMessage(clientChannel,  Delimiter + ("Connection with " + " successfully established on \"").toUpperCase() +
//                         "\"\n", false);
//            }
//        }
//
//        if (clientChannel != null) {
//            readChannel(clientChannel);
//        }
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
                }
//                if (partnerName == null) {
//                    String[] connectionMessage = receivedMessage.split(Delimiter);
//                    if (receivedMessage.contains(Delimiter) && connectionMessage.length > 1) {
//                        partnerName = connectionMessage[0];
//                        output.setStyle("-fx-text-fill: chartreuse");
//                        output.appendText("\n" + connectionMessage[1]);
//                    }
//                    else {
//                        output.setStyle("-fx-text-fill: chartreuse");
//                        output.appendText("\nINVALID CONNECTION MESSAGE FORMAT RECEIVED.");
//                    }
//                }
//                else {
                    if (!receivedMessage.equals(DisconnectMessage)) {
                        output.setStyle("-fx-text-fill: chartreuse");
                        output.appendText("\n" + (sendReceiveStat != 0 ? "\tDMITRIY_LYASHENKO:\n" : "") +  receivedMessage);
                        setSendReceiveStat(0);
                    }
                    else {
                        output.setStyle("-fx-text-fill: chartreuse");
                        output.appendText(DisconnectMessage);
                        disconnect();
                    }
//                }
                buffInput.clear();
            }
        } catch (ClosedChannelException e) {
            e.printStackTrace();
            disconnect();
            CVApplication.isChatWindowOn = false;
        }
        return readBytesNum;
    }

    @Override
    public void run() {
        System.out.println("Thread run");
        boundListener();
        while (runWhileController) {
            try {
                System.out.println("Before Process");
                Thread.sleep(1000);
                process();
                System.out.println("After Process");
            } catch (Exception e) {
                sendButton.setFill(Color.rgb(255, 40, 0));
                setSendReceiveStat(-1);
                makeChannel = null;
//                clientChannel = null;
                System.out.println(e.getMessage() + " ------ " + e.toString());
            }
        }
        disconnect();
        System.out.println("Run complete");
    }

    private void disconnect() {
        try {
            if (makeChannel != null && makeChannel.isOpen()) {
                sendMessage(makeChannel, DisconnectMessage, false);
                System.out.println("makeChannel.close();");
                makeChannel.close();
            }
//            if (clientChannel != null && clientChannel.isOpen()) {
//                sendMessage(clientChannel, DisconnectMessage, false);
//                System.out.println("clientChannel.close();");
//                clientChannel.close();
//            }
            if (serverSocketChannel != null && serverSocketChannel.isOpen()) {
                System.out.println("serverSocketChannel.close();");
                serverSocketChannel.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            makeChannel = null;
//            clientChannel = null;
            serverSocketChannel = null;
        }
    }

    private void connect() throws InterruptedException, IOException {
        toPort = 3333;

        try {
            makeChannel = SocketChannel.open(new InetSocketAddress(toIpAddress, toPort));
            Thread.sleep(SleepTime);
            if (makeChannel != null) {
                sendMessage(makeChannel,  Delimiter + ("Connection with " + " successfully established on \"").toUpperCase() +
                        "\"\n", false);
            }
            sendButton.setFill(Color.rgb(127,255,0));
        } catch (Exception e) {
            System.out.println("ConnectFAil");
            throw new InterruptedException();
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

    public void setSendReceiveStat(int sendReceiveStat) {
        this.sendReceiveStat = sendReceiveStat;
    }

}
