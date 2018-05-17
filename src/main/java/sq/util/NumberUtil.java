package sq.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtil {
    public static long parseLong(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        } else {
            long returnValue = 0;
            try {
                returnValue = Long.parseLong(value);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return returnValue;
        }
    }

    public static double parseDouble(final String value) {
        return parseDouble(value, 2);
    }

    public static double parseDouble(final String value, int digit) {
        String patten = "";
        switch (digit) {
            case 1:
                patten = "#0.#";
                break;
            case 2:
                patten = "#0.##";
                break;
            case 3:
                patten = "#0.###";
                break;
            case 4:
                patten = "#0.####";
                break;
            default:
                patten = "#0.00";
        }
        DecimalFormat df = new DecimalFormat(patten);
        if (value == null || value.trim().isEmpty()) {
            return 0;
        } else {
            try {
                return df.parse(value).doubleValue();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    public static float parseFloat(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        } else {
            float returnvalue = 0;
            try {
                returnvalue = Float.parseFloat(value);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return returnvalue;
        }
    }

    public static int parseInt(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        } else {
            int returnvalue = 0;
            try {
                returnvalue = Integer.parseInt(value);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            return returnvalue;
        }
    }

    public static boolean isLong(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        } else {
            try {
                Long.parseLong(value);
            } catch (final Exception e) {
                return false;
            }
            return true;
        }
    }

    public static String generateFourNumber(final int i) {
        final int id = i + 1;
        final StringBuilder buffer = new StringBuilder();
        if (id < 10) {
            buffer.append("000").append(id);
        } else if (id < 100) {
            buffer.append("00").append(id);
        } else if (id < 1000) {
            buffer.append("0").append(id);
        } else if (id < 10000) {
            buffer.append(id);
        }
        return buffer.toString();
    }

    public static void main(final String[] args) {

        System.out.println(parseDouble("0.8854123456", 4));
        System.out.println(parseDouble("0.8854123456", 2));
        System.out.println(parseDouble("0.8834", 4));
        System.out.println(parseDouble("0.0000", 4) + "");
        System.out.println(parseDouble("0.8", 3));
    }

    public static String generateNumber(final String start, final int count,
										final long id) {
        if (id > Math.pow(10, count - 1)) {
            return start + id;
        }
        final StringBuilder buffer = new StringBuilder(start);

        for (int i = 1; i < count + 1; i++) {
            if (id < Math.pow(10, i)) {
                buffer.append(StringUtils.repeat("0", count - i - 1)).append
						(id);
                break;
            }
        }

        return buffer.toString();
    }

    public static String generateNumber(final int count, final long id) {
        if (id >= Math.pow(10, count)) {
            return "" + id;
        }
        final StringBuilder buffer = new StringBuilder();

        for (int i = 1; i < count + 1; i++) {
            if (id < Math.pow(10, i)) {
                buffer.append(StringUtils.repeat("0", count - i)).append(id);
                break;
            }
        }

        return buffer.toString();
    }

    public static boolean isInt(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        } else {
            try {
                Integer.parseInt(value);
            } catch (final Exception e) {
                return false;
            }
            return true;
        }
    }

    public static String generateDouble(double value) {
        return generateDouble(value, 0);
    }

    public static String generateDouble(double value, int digit) {
        StringBuffer pattern = new StringBuffer();
        pattern.append("#0");
        switch (digit) {
            case 1:
                pattern.append(".0");
                break;

            case 2:
                pattern.append(".00");
                break;

            case 3:
                pattern.append(".000");
                break;

            case 4:
                pattern.append(".0000");
                break;
            default:
                break;
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());// 格式化设置
        return decimalFormat.format(value);
    }

    public static String generateFloat(float value) {
        return generateFloat(value, 0);
    }

    public static String generateFloat(float value, int digit) {
        StringBuffer pattern = new StringBuffer();
        pattern.append("#0");
        switch (digit) {
            case 1:
                pattern.append(".0");
                break;

            case 2:
                pattern.append(".00");
                break;

            case 3:
                pattern.append(".000");
                break;

            case 4:
                pattern.append(".0000");
                break;
            default:
                break;
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString());// 格式化设置
        return decimalFormat.format(value);
    }

    public static String genRandomNum(int n) {
        int maxNum = 62;
        int i;
        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < n) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
}
