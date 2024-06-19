package de.wagentim.collector.utils;

import de.wagentim.collector.entity.KeyValuePair;

import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class StringUtils
{
    public static final KeyValuePair parserKeyValue(String input, String deli)
    {
        StringTokenizer st = new StringTokenizer(input, deli);
        KeyValuePair kvp = new KeyValuePair();
        int index = 0;
        while(st.hasMoreElements())
        {
            String txt = st.nextToken().trim();
            if(index == 0)
            {
                kvp.setKey(txt);
            }
            else if(index == 1)
            {
                kvp.setValue(txt);
            }
            else
            {
                break;
            }

            index++;
        }

        return kvp;
    }

    public static String toUTF8(String input)
    {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static boolean checkNullOrEmpty(final String input)
    {
        return (input == null || input.isEmpty()) ? true : false;
    }
}
