package com.jinrong.pdf.utils;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberUtil.class);

    public static Integer parseInt(String strNumber) {
        Integer number = null;
        try {
            if (StringUtil.isNotBlank(strNumber)) {
                number = Integer.valueOf(Integer.parseInt(strNumber.trim()));
            } else {
                LOGGER.error("Failed to parse the string to a Integer object. The string is blank!");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse the string '{}' to a Integer object.", strNumber, e);
        }
        return number;
    }

    public static Long parseLong(String strNumber) {
        Long number = null;
        try {
            if (StringUtil.isNotBlank(strNumber)) {
                number = Long.valueOf(Long.parseLong(strNumber.trim()));
            } else {
                LOGGER.error("Failed to parse the string to a Long object. The string is blank!");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse the string '{}' to a Long object.", strNumber, e);
        }
        return number;
    }

    public static Double parseDouble(String strNumber) {
        Double number = null;
        try {
            if (StringUtil.isNotBlank(strNumber)) {
                number = Double.valueOf(Double.parseDouble(strNumber.trim()));
            } else {
                LOGGER.error("Failed to parse the string to a Double object. The string is blank!");
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse the string '{}' to a Double object.", strNumber, e);
        }
        return number;
    }

    public static Double parseDouble(String strNumber, int scale) {
        Double number = parseDouble(strNumber);
        if (number == null)
            return null;
        return round(number, scale);
    }

    public static Double round(Double number, int scale) {
        return Double.valueOf((new BigDecimal(String.valueOf(number))).setScale(scale, 4).doubleValue());
    }
}
