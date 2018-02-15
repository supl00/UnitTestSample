package com.gazua.ddeokrok.coinman.util;

public class StringUtil
{
    public static String getDefString(String paramString)
    {
        String str;
        if ((paramString != null) && (paramString.length() != 0))
        {
            str = paramString;
            if (!"".equals(paramString)) {}
        }
        else
        {
            str = "";
        }
        return str;
    }

    public static boolean isEmpty(CharSequence paramCharSequence)
    {
        return (paramCharSequence == null) || (paramCharSequence.length() == 0);
    }

    public static String nvl(Object paramObject)
    {
        if (paramObject == null) {
            return "";
        }
        return paramObject.toString().trim();
    }
}
