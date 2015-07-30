package com.example.iot23.fridgevendor.handlemessage;

/**
 * Created by iao on 15. 7. 20.
 */
public class HandleMessage {
    private static final String STARTIDENTIFIER = "#";
    private static final String MIDDLEIDENTIFIER = "|";
    private static final String ENDIDENTIFIER = "$";

    public String makeSendMessage(String command) {
        return STARTIDENTIFIER + command + MIDDLEIDENTIFIER + ENDIDENTIFIER;
    }

    public String makeSendMessage(String command, String c1) {
        return STARTIDENTIFIER + command + MIDDLEIDENTIFIER + c1 + ENDIDENTIFIER;
    }

    public String makeSendMessage(String command, String c1, String c2) {
        return STARTIDENTIFIER + command +
                MIDDLEIDENTIFIER + c1 +
                MIDDLEIDENTIFIER + c2 + ENDIDENTIFIER;
    }

    public String makeSendMessage(String command, String c1, String c2, String c3) {
        return STARTIDENTIFIER + command +
                MIDDLEIDENTIFIER + c1 +
                MIDDLEIDENTIFIER + c2 +
                MIDDLEIDENTIFIER + c3 + ENDIDENTIFIER;
    }

    public String makeSendMessage(String command, String c1, String c2, String c3, String c4) {
        return STARTIDENTIFIER + command +
                MIDDLEIDENTIFIER + c1 +
                MIDDLEIDENTIFIER + c2 +
                MIDDLEIDENTIFIER + c3 +
                MIDDLEIDENTIFIER + c4 + ENDIDENTIFIER;
    }


}
