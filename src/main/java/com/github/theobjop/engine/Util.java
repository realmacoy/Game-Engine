package com.github.theobjop.engine;

import com.sun.javaws.Main;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.BufferUtils.createByteBuffer;

public class Util {
    private static final Random RND = new Random();
    private static ResourceLocator resourceLocator = new DefaultResourceLocator();

    public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (SeekableByteChannel fc = Files.newByteChannel(path)) {
                buffer = createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try (
                InputStream source = resourceLocator.getResourceAsStream(resource);
                ReadableByteChannel rbc = Channels.newChannel(source)) {
                buffer = createByteBuffer(bufferSize);

                while (true) {
                    int bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        buffer.flip();
        return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public static PointerBuffer longToPointer(long pointer) {
        return BufferUtils.createPointerBuffer(1).put(pointer);
    }

    public static float rnd() {
        return rndFloat();
    }

    public static int rndInt() {
        return RND.nextInt();
    }

    public static int rnd(int high) {
        return RND.nextInt(high);
    }

    public static float rndFloat() {
        return RND.nextFloat();
    }

    public static int rnd(int low, int high) {
        if (low==high)
            return low;
        return RND.nextInt(high - low) + low;
    }

    public static float rnd(float low, float high) {
        if (low==high)
            return low;
        return low + (RND.nextFloat() * (high - low));
    }

    public static void log(String msg) {
        getLogger().log(Level.INFO, msg);
    }

    public static void warn(String msg) {
        getLogger().log(Level.WARNING, msg);
    }

    public static void error(String msg) {
        error(msg, null);
    }

    public static Logger getLogger() {
        return Logger.getLogger(Util.class.getName());
    }

    public static void error(String msg, Throwable t) {
        if (t!=null) getLogger().log(Level.SEVERE, msg, t);
        else getLogger().log(Level.SEVERE, msg);
    }


    /**
     * Loads the given input stream into a source code string.
     * @param in the input stream
     * @return the resulting source code String
     * @author Nitram
     */
    public static String readFile(InputStream in) {
        try {
            final StringBuffer sBuffer = new StringBuffer();
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            final char[] buffer = new char[1024];

            int cnt;
                while ((cnt = br.read(buffer, 0, buffer.length)) > -1) {
                    sBuffer.append(buffer, 0, cnt);
                }

            br.close();
            in.close();
            return sBuffer.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error trying to read file: " + e.getLocalizedMessage());
        }
    }

    public static List<String> readAllLines(String fileName) {
        try {
            List<String> list = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(Util.getResourceAsStream(fileName)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ByteBuffer readBytes(String filename) {
        try {
            File file = new File(Util.getResource(filename).toURI());
            InputStream in = new FileInputStream(file);
            int fileSize = (int) file.length();
            byte[] byteBuf = new byte[fileSize];

            int b, i = 0;
            while ((b = in.read()) != -1) {
                byteBuf[i++] = (byte)b;
            }
            ByteBuffer buf = createByteBuffer(fileSize);
            buf.put(byteBuf);
            return buf;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static URL getResource(String str) {
        URL u = getResourceLocator().getResource(str);
        return u;
    }

    public static InputStream getResourceAsStream(String str) {
        InputStream in = getResourceLocator().getResourceAsStream(str);
        return in;
    }

    public static void setResourceLocator(ResourceLocator r) {
        resourceLocator = r;
    }

    public static ResourceLocator getResourceLocator() {
        return resourceLocator;
    }

    public static float[] listToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }

    public static String loadResource(String fileName) {
        String result;
        try (InputStream in = Util.getResourceAsStream(fileName); Scanner scanner = new Scanner(in, "UTF-8")) {
            result = scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static Font getTTFFontFromFile(String fontFile) {
        return getTTFFontFromFile(fontFile, 40);
    }

    public static Font getTTFFontFromFile(String fontFile, int fontSize) {
        InputStream in = Util.getResourceAsStream("/fonts/Finale.ttf");
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        return font;
    }

    public static int[] listIntToArray(List<Integer> list) {
        int[] result = list.stream().mapToInt((Integer v) -> v).toArray();
        return result;
    }

    public static boolean existsResourceFile(String fileName) {
        boolean result;
        try (InputStream is = Main.class.getResourceAsStream(fileName)) {
            result = is != null;
        } catch (Exception excp) {
            result = false;
        }
        return result;
    }

    public static final class DefaultResourceLocator implements ResourceLocator {

        static File ROOT;

        DefaultResourceLocator() {
            ROOT = new File(".");
        }

        public DefaultResourceLocator(String newRoot) {
            ROOT = new File(newRoot);
        }

        private static File createFile(String ref) {
            File file = new File(ROOT, ref);
            if (!file.exists()) {
                file = new File(ref);
            }

            return file;
        }

        public InputStream getResourceAsStream(String ref) {
            InputStream in = Main.class.getResourceAsStream(ref);
            if (in==null) { // try file system
                try { return new FileInputStream(createFile(ref)); }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return in;
        }

        public URL getResource(String ref) {
            URL url = Main.class.getResource(ref);
            if (url==null) {
                try {
                    File f = createFile(ref);
                    if (f.exists())
                        return f.toURI().toURL();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return url;
        }
    }

    public interface ResourceLocator {
        URL getResource(String str);
        InputStream getResourceAsStream(String str);
    }
}