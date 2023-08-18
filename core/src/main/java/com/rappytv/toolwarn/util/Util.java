package com.rappytv.toolwarn.util;

import com.rappytv.toolwarn.TbwAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Util {

    public static void msg(Component component, boolean prefix) {
        Component message = Component.empty();
        if(prefix)
            message.append(TbwAddon.prefix);
        message.append(component);
        Laby.references().chatExecutor().displayClientMessage(message);
    }

    public static String formatNumber(int number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);

        return formatter.format(number);
    }
}
