package org.example;

import ch.qos.logback.classic.pattern.MessageConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SensitiveDataConverter extends MessageConverter {

    public static final Map<String, Pattern> SENSITIVE_PATTERNS = Map.of(
        "手机号", Pattern.compile("1[3-9]\\d{9}"),
        "邮箱", Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"),
        "密码字段", Pattern.compile("(?<=password=).*?(?=[,}\\s])")
    );

    @Override
    public String convert(ILoggingEvent event) {
        String message = event.getFormattedMessage();
        if (message == null || message.isEmpty()) {
            return message;
        }

        try {
            return maskSensitiveInfo(message);
        } catch (Exception e) {
            return message;
        }
    }

    private String maskSensitiveInfo(String message) {
        String result = message;

        for (var entry : SENSITIVE_PATTERNS.entrySet()) {
            String type = entry.getKey();
            Pattern pattern = entry.getValue();
            Matcher matcher = pattern.matcher(result);

            while (matcher.find()) {
                String sensitive = matcher.group();
                String masked = getMaskedValue(sensitive, type);
                result = result.replace(sensitive, masked);
            }
        }

        return result;
    }

    private String getMaskedValue(String original, String type) {
        switch (type) {
            case "手机号":
                return original.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            case "邮箱":
                String[] parts = original.split("@");
                String name = parts[0].substring(0, Math.min(3, parts[0].length())) + "***";
                return name + "@" + parts[1];
            case "密码字段":
                return "*".repeat(original.length());
            default:
                return original;
        }
    }

}
