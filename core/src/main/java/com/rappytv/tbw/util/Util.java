package com.rappytv.tbw.util;

import com.rappytv.tbw.TbwAddon;
import net.labymod.api.util.I18n;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Util {

    public static void msg(String text, boolean prefix) {
        TbwAddon.get().displayMessage(prefix ? TbwAddon.prefix + text : text);
    }

    public static String getTranslation(String key, Object ...args) {
        String translation = I18n.getTranslation(key, args);
        if(translation == null)
            return key;
        return translation;
    }

    public static String formatNumber(int number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(number);
    }
}
