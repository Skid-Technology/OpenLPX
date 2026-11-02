package it.ytnoos.lpx.netty;

import it.ytnoos.lpx.LPXBukkit;
import it.ytnoos.lpx.config.ConfigManager;
import it.ytnoos.lpx.config.Messages;

import org.bukkit.ChatColor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class NettyHealthReport {

    private final DecimalFormat decimalFormat;

    private final String mainColor;

    private final String text;

    public NettyHealthReport(LPXBukkit lPXBukkit) {
        DecimalFormat decimalFormat2 = new DecimalFormat("#0");
        this.decimalFormat = decimalFormat2;
        ConfigManager configManager = lPXBukkit.getConfigManager();
        Messages messages = configManager.getMessages();
        this.mainColor = messages.getMainColor();
        this.text = messages.getSecondColor();
    }

    public String createText(NettyHealthMonitor nettyHealthMonitor) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(this.mainColor)
                .append("Packet Tick Delay Report ")
                .append(this.text)
                .append("min")
                .append(ChatColor.GRAY)
                .append("/")
                .append(this.text)
                .append("med")
                .append(ChatColor.GRAY)
                .append("/")
                .append(this.text)
                .append("95%ile")
                .append(ChatColor.GRAY)
                .append("/")
                .append(this.text)
                .append("max\n");
        BigDecimal rollingAverage = nettyHealthMonitor.getRollingAverage();
        BigDecimal bigDecimal = nettyHealthMonitor.createBigDecimal();
        BigDecimal bigDecimal2 = nettyHealthMonitor.createBigDecimal6();
        BigDecimal rollingAverage6 = nettyHealthMonitor.getRollingAverage6();
        BigDecimal bigDecimal3 = bigDecimal;
        BigDecimal bigDecimal4 = rollingAverage;
        StringBuilder stringBuilder2 = stringBuilder;
        String text2 = (String) "10 seconds";
        rollingAverage = bigDecimal4;
        bigDecimal = bigDecimal3;
        BigDecimal bigDecimal5 = bigDecimal2;
        BigDecimal bigDecimal6 = bigDecimal5;
        BigDecimal bigDecimal7 = bigDecimal;
        BigDecimal bigDecimal8 = rollingAverage;
        String text3 = text2;
        StringBuilder stringBuilder3 = stringBuilder2;
        this.handleBigDecimal(
                stringBuilder3, text3, bigDecimal8, bigDecimal7, bigDecimal6, rollingAverage6);
        rollingAverage = nettyHealthMonitor.getRollingAverage2();
        bigDecimal = nettyHealthMonitor.createBigDecimal2();
        BigDecimal bigDecimal9 = nettyHealthMonitor.createBigDecimal7();
        BigDecimal rollingAverage7 = nettyHealthMonitor.getRollingAverage7();
        BigDecimal bigDecimal10 = bigDecimal;
        BigDecimal bigDecimal11 = rollingAverage;
        String text4 = "minute";
        stringBuilder2 = stringBuilder;
        text2 = text4;
        rollingAverage = bigDecimal11;
        bigDecimal = bigDecimal10;
        bigDecimal5 = bigDecimal9;
        BigDecimal bigDecimal12 = bigDecimal5;
        BigDecimal bigDecimal13 = bigDecimal;
        BigDecimal bigDecimal14 = rollingAverage;
        String text5 = text2;
        StringBuilder stringBuilder4 = stringBuilder2;
        this.handleBigDecimal(
                stringBuilder4, text5, bigDecimal14, bigDecimal13, bigDecimal12, rollingAverage7);
        rollingAverage = nettyHealthMonitor.getRollingAverage3();
        bigDecimal = nettyHealthMonitor.createBigDecimal3();
        BigDecimal bigDecimal15 = nettyHealthMonitor.createBigDecimal8();
        BigDecimal rollingAverage32 = nettyHealthMonitor.getRollingAverage32();
        BigDecimal bigDecimal16 = bigDecimal;
        BigDecimal bigDecimal17 = rollingAverage;
        String text6 = "5 minutes";
        stringBuilder2 = stringBuilder;
        text2 = text6;
        rollingAverage = bigDecimal17;
        bigDecimal = bigDecimal16;
        bigDecimal5 = bigDecimal15;
        BigDecimal bigDecimal18 = bigDecimal5;
        BigDecimal bigDecimal19 = bigDecimal;
        BigDecimal bigDecimal20 = rollingAverage;
        String text7 = text2;
        StringBuilder stringBuilder5 = stringBuilder2;
        this.handleBigDecimal(
                stringBuilder5, text7, bigDecimal20, bigDecimal19, bigDecimal18, rollingAverage32);
        stringBuilder.append("\n \n");
        stringBuilder.append(this.mainColor).append("General status: ");
        double value = nettyHealthMonitor.createBigDecimal5().doubleValue();
        Double value2 = ((Double) value);
        stringBuilder.append(this.createText2(((double) value2)));
        StringBuilder stringBuilder6 = stringBuilder.append(ChatColor.GRAY).append(" (90%ile: ");
        Double value3 = ((Double) value);
        stringBuilder6.append(this.createText(value3)).append(")\n");
        Map entries = nettyHealthMonitor.createEntries();
        if (!entries.isEmpty()) {
            stringBuilder.append("\n");
            stringBuilder.append(this.mainColor).append("Netty Threads:\n");
            ArrayList<Integer> values = new ArrayList<>(entries.keySet());
            Collections.sort(values);
            for (Integer value4 : values) {
                NettyHealthMonitor.Sample sample = (NettyHealthMonitor.Sample) entries.get(value4);
                double value5 = sample.getBigDecimal().doubleValue();
                int limit = sample.getLimit();
                stringBuilder6 =
                        stringBuilder
                                .append(ChatColor.GRAY)
                                .append("[")
                                .append(value4 + 1)
                                .append("] ");
                Double value6 = ((Double) value5);
                stringBuilder6
                        .append(this.createText2(((double) value6)))
                        .append(" ")
                        .append(ChatColor.GRAY);
                stringBuilder
                        .append("(")
                        .append(limit)
                        .append(" ")
                        .append(limit == 1 ? "player" : "players")
                        .append(")");
                Double value7 = ((Double) value5);
                if (this.testValue(((double) value7))) {
                    stringBuilder6 = stringBuilder.append(" (90%ile: ");
                    Double value8 = ((Double) value5);
                    stringBuilder6.append(this.createText(value8)).append(") ");
                }
                stringBuilder.append("\n");
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private void handleBigDecimal(
            StringBuilder stringBuilder,
            String text2,
            BigDecimal bigDecimal,
            BigDecimal bigDecimal2,
            BigDecimal bigDecimal3,
            BigDecimal bigDecimal4) {
        stringBuilder.append(this.text).append("last ").append(text2).append(" ");
        if (bigDecimal != null) {
            double value = bigDecimal.doubleValue();
            Double value2 = ((Double) value);
            StringBuilder stringBuilder2 =
                    stringBuilder.append(this.createChatColor(((double) value2)));
            Double value3 = ((Double) value);
            stringBuilder2.append(this.createText(value3)).append(ChatColor.GRAY).append("/");
        } else {
            stringBuilder.append(ChatColor.GRAY).append("?/");
        }
        if (bigDecimal2 != null) {
            double value4 = bigDecimal2.doubleValue();
            Double value5 = ((Double) value4);
            StringBuilder stringBuilder3 =
                    stringBuilder.append(this.createChatColor(((double) value5)));
            Double value6 = ((Double) value4);
            stringBuilder3.append(this.createText(value6)).append(ChatColor.GRAY).append("/");
        } else {
            stringBuilder.append(ChatColor.GRAY).append("?/");
        }
        if (bigDecimal3 != null) {
            double value7 = bigDecimal3.doubleValue();
            Double value8 = ((Double) value7);
            StringBuilder stringBuilder4 =
                    stringBuilder.append(this.createChatColor(((double) value8)));
            Double value9 = ((Double) value7);
            stringBuilder4.append(this.createText(value9)).append(ChatColor.GRAY).append("/");
        } else {
            stringBuilder.append(ChatColor.GRAY).append("?/");
        }
        if (bigDecimal4 != null) {
            double value10 = bigDecimal4.doubleValue();
            Double value11 = ((Double) value10);
            StringBuilder stringBuilder5 =
                    stringBuilder.append(this.createChatColor(((double) value11)));
            Double value12 = ((Double) value10);
            stringBuilder5.append(this.createText(value12));
        } else {
            stringBuilder.append(ChatColor.GRAY).append("?");
        }
        stringBuilder.append("\n");
    }

    private String createText(double value) {
        return this.decimalFormat.format(value);
    }

    private String createText2(double value) {
        if (value == 0.0) {
            return ChatColor.GREEN + "HEALTHY :D";
        } else if (value <= 1.0) {
            return ChatColor.YELLOW + "UNSTABLE :/";
        } else {
            return value <= 2.0 ? ChatColor.RED + "BAD :(" : ChatColor.DARK_RED + "VERY BAD D:";
        }
    }

    private boolean testValue(double value) {
        return value > 2.0;
    }

    private ChatColor createChatColor(double value) {
        if (value <= 1.0) {
            return ChatColor.GREEN;
        } else if (value <= 2.0) {
            return ChatColor.YELLOW;
        } else if (value <= 3.0) {
            return ChatColor.GOLD;
        } else {
            return value <= 5.0 ? ChatColor.RED : ChatColor.DARK_RED;
        }
    }
}
