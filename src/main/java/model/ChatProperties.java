package model;

import java.nio.ByteBuffer;

public interface ChatProperties {

    int BuffSize = 300;
    String DisconnectMessage = "exit";
    int SleepTime = 250;
    String ConnectText = "Connect";
    String Delimiter = String.valueOf((char)(19));


    default String buffToString(ByteBuffer b, int readBytesNum) {
        if (b == null || readBytesNum <= 0) {
            return "";
        }
        return new String(b.array(), 0, readBytesNum);
    }
}
