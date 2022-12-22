package org.bopre.test.spring.sqlfiller.utils.io;

import org.bopre.test.spring.sqlfiller.exception.FailedReadResourceException;
import org.bopre.test.spring.sqlfiller.exception.NoResourceFoundException;

import java.io.*;
import java.nio.charset.Charset;

public class ResourcesUtils {

    private ResourcesUtils() {
        //do not create
    }

    public static String readResourceAsString(String path) {
        return readResourceAsString(path, Charset.defaultCharset());
    }

    public static String readResourceAsString(String path, Charset charset) {
        try {
            final InputStream resourceStream = ResourcesUtils.class.getResourceAsStream(path);
            if (resourceStream == null)
                throw new NoResourceFoundException("no such resource found: " + path);
            return readStreamAsString(resourceStream, charset);
        } catch (IOException e) {
            throw new FailedReadResourceException("resource: " + path, e);
        }
    }

    public static String readStreamAsString(InputStream inputStream, Charset charset) throws IOException {
        final StringBuilder result = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            int ch = 0;
            while ((ch = reader.read()) != -1) {
                result.append((char) ch);
            }
        }
        return result.toString();
    }

}
