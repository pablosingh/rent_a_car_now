package com.digitalhouse.rentacarnow.service;

public final class PhoneUtils {

    private PhoneUtils() {
    }

    public static String normalize(String phone) {
        if (phone == null) return null;
        String trimmed = phone.trim();
        if (trimmed.isEmpty()) return null;
        String cleaned = trimmed.replaceAll("[\\s\\-().]+", "");
        if (!cleaned.matches("^\\+?[0-9]{8,15}$")) {
            throw new IllegalArgumentException("Ingresá un teléfono válido (8 a 15 dígitos, ej +5491155556666).");
        }
        return cleaned;
    }

    public static String toWaDigits(String phone) {
        String digits = phone.replaceAll("[^0-9]", "");
        if (digits.startsWith("00")) digits = digits.substring(2);
        if (digits.startsWith("0")) digits = digits.substring(1);
        if (digits.startsWith("54")) return digits;
        if (digits.startsWith("15")) return "54" + "9" + digits;
        if (digits.length() == 10) return "549" + digits;
        return "54" + digits;
    }

    public static String toWaLink(String phone, String text) {
        String digits = toWaDigits(phone);
        String base = "https://wa.me/" + digits;
        if (text == null || text.isBlank()) return base;
        try {
            return base + "?text=" + java.net.URLEncoder.encode(text, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            return base;
        }
    }
}
