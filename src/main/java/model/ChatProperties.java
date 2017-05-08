package model;

import java.nio.ByteBuffer;

public interface ChatProperties {

    int BuffSize = 300;
    String DisconnectMessage = ("\nConnection is closed. Thank you for using this chat.\n").toUpperCase();
    int SleepTime = 250;
    String Delimiter = String.valueOf((char)(19));


    default String buffToString(ByteBuffer b, int readBytesNum) {
        if (b == null || readBytesNum <= 0) {
            return "";
        }
        return new String(b.array(), 0, readBytesNum);
    }
}
