package org.revo.Util;

import com.comcast.viper.hlsparserj.tags.UnparsedTag;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

/**
 * Created by ashraf on 16/04/17.
 */
public class Util {
    public final static String stream = "#EXTM3U\n" +
            "# Created with Bento4 mp4-hls.py version 1.1.0r621\n" +
            "\n" +
            "# Media Playlists\n";


    public static String generateKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String TOString(List<UnparsedTag> tags, Function<String, String> function) {
        StringBuilder builder = new StringBuilder();
        for (Iterator i$ = tags.iterator(); i$.hasNext(); builder.append("\n")) {
            UnparsedTag tag = (UnparsedTag) i$.next();
            builder.append(tag.getRawTag());
            if (tag.getURI() != null && tag.getTagName().equals("EXTINF"))
                builder.append("\n").append(function.apply(tag.getURI()));
        }
        return builder.toString();
    }

    public static byte[] secret(String encryption_key) {
        final int len = encryption_key.length();
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            int h = hexToBin(encryption_key.charAt(i));
            int l = hexToBin(encryption_key.charAt(i + 1));

            out[i / 2] = (byte) (h * 16 + l);
        }
        return out;
    }

    private static int hexToBin(char ch) {
        if ('0' <= ch && ch <= '9') {
            return ch - '0';
        }
        if ('A' <= ch && ch <= 'F') {
            return ch - 'A' + 10;
        }
        if ('a' <= ch && ch <= 'f') {
            return ch - 'a' + 10;
        }
        return -1;
    }
}
