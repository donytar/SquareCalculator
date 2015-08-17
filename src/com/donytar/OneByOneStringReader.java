package com.donytar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OneByOneStringReader {

    public interface Receiver {
        /**
         * <p>Receive string from stream</p>
         * @param string    String from stream
         * @return true to continue read stream
         */
        boolean onStringRead(String string) throws InvalidFormatException;
    }

    public static void read(InputStream inputStream, Receiver receiver) throws IOException, InvalidFormatException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                if (!receiver.onStringRead(line)) {
                    break;
                }
            }
        } finally {
            br.close();
        }
    }
}
