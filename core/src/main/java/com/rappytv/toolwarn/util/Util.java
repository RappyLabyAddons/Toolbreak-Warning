package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.TbwAddon;
import net.labymod.api.Laby;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Util {

    public static void msg(String text, boolean prefix) {
        Laby.references().chatExecutor().displayClientMessage(prefix ? TbwAddon.prefix + text : text);
    }

    public static String formatNumber(int number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(number);
    }
}
