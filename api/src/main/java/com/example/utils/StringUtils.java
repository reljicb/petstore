package com.example.utils;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

public class StringUtils {
  public static final String EMPTY_STRING_MARKER = "<empty>";
  public static final String TRIMMED_STRING_TAIL = "... (trimmed)";

  /**
   * Ensures the string is not null nor empty.
   * Returns the original string.
   * 
   * @param expression
   *           a string to be checked
   * @throws IllegalArgumentException
   *            if {@code str} is null or empty
   */
  public static String checkNotNullNorEmpty(String str) {
    checkArgument(!Strings.isNullOrEmpty(str));
    return str;
  }

  /**
   * Ensures the string is not null nor empty.
   * Returns the original string.
   * 
   * @param expression
   *           a string to be checked
   * @throws IllegalArgumentException
   *            if {@code str} is null or empty
   */
  public static String checkNotNullNorEmpty(String str, String msg, Object... args) {
    checkArgument(!Strings.isNullOrEmpty(str), msg, args);
    return str;
  }

  /**
   * If the text is longer then maxLength, it cuts of the tail, adds {@code TRIMMED_STRING_TAIL}, and
   * returns the resulting string.
   */
  public static String trimLongText(String text, int maxLength) {
    checkArgument(maxLength >= 0);

    if (text == null) {
      return null;
    }

    if (text.length() > maxLength) {
      return text.substring(0, maxLength) + TRIMMED_STRING_TAIL;
    } else {
      return text;
    }
  }

  public static final String DEFAULT_NULL_TEXT = "<null>";

  /**
   * Returns defaultText, if text is null. Otherwise it returns text.
   */
  public static String nullToDefault(Object text, String defaultText) {
    return text == null ? defaultText : text.toString();
  }

  /**
   * Returns {@link DEFAULT_NULL_TEXT} if text is null. Otherwise it returns text.
   */
  public static String nullToDefault(Object text) {
    return nullToDefault(text, DEFAULT_NULL_TEXT);
  }

  public static final String DEFAULT_EMPTY_TEXT = "<empty>";

  /**
   * Returns defaultText, if text is an empty string. Otherwise it returns text.
   */
  public static String emptyToDefault(String text, String defaultText) {
    return text != null && text.isEmpty() ? defaultText : text;
  }

  /**
   * Returns {@link DEFAULT_EMPTY_TEXT} if text is an empty string. Otherwise it returns text.
   */
  public static String emptyToDefault(String text) {
    return emptyToDefault(text, DEFAULT_EMPTY_TEXT);
  }

  /**
   * Returns {@link DEFAULT_NULL_TEXT} if text is null, or {@link DEFAULT_EMPTY_TEXT} if text is an empty string.
   * Otherwise it returns text.
   */
  public static String nullOrEmptyToDefault(String text) {
    return emptyToDefault(nullToDefault(text, DEFAULT_NULL_TEXT), DEFAULT_EMPTY_TEXT);
  }

  public static String multiLineToSingleLine(String text) {
    return multiLineToSingleLine(text, ImmutableMap.of("\r\n", "\\r\\n", "\n", "\\n", "\r", "\\r"));
  }

  public static String multiLineToSingleLine(String text, boolean showLineBreaks) {
    return showLineBreaks ? multiLineToSingleLine(text)
      : multiLineToSingleLine(text, ImmutableMap.of("\r\n", "", "\n", "", "\r", ""));
  }

  public static String multiLineToSingleLine(String text, ImmutableMap<String, String> lineBreakReplacement) {
    if (text == null) {
      return null;
    }

    String[] pair = new String[] {"\r\n", "\\r\\n"};
    if (lineBreakReplacement != null && lineBreakReplacement.containsKey(pair[0])) {
      text = text.replace(pair[0], lineBreakReplacement.get(pair[0]));
    } else {
      text = text.replace(pair[0], pair[1]);
    }

    pair = new String[] {"\n", "\\n"};
    if (lineBreakReplacement != null && lineBreakReplacement.containsKey(pair[0])) {
      text = text.replace(pair[0], lineBreakReplacement.get(pair[0]));
    } else {
      text = text.replace(pair[0], pair[1]);
    }

    pair = new String[] {"\r", "\\r"};
    if (lineBreakReplacement != null && lineBreakReplacement.containsKey(pair[0])) {
      text = text.replace(pair[0], lineBreakReplacement.get(pair[0]));
    } else {
      text = text.replace(pair[0], pair[1]);
    }

    return text;
  }

  /**
   * Returns multiLineToSingleLine(trimLongText(nullToDefault(text, {@code EMPTY_STRING_MARKER}), maxLength)),
   * which is a log format respecting, memory conserving, always visible string.
   */
  public static String safeAndShort(String text, int maxLength) {
    return multiLineToSingleLine(trimLongText(nullOrEmptyToDefault(text), maxLength));
  }

  public static String firstNotNullNorEmpty(String... args) {
    if (args == null || args.length == 0) {
      return null;
    }

    for (String arg : args) {
      if (!Strings.isNullOrEmpty(arg)) {
        return arg;
      }
    }

    return null;
  }

  /**
   * If text starts with head, returns text without head.
   * Otherwise it returns text.
   */
  public static String ltrim(String text, String head) {
    if (text == null) {
      return null;
    }

    if (Strings.isNullOrEmpty(head)) {
      return text;
    }

    if (text.startsWith(head)) {
      return text.substring(head.length());
    }

    return text;
  }

  /**
   * If text ends with tail, returns text without tail.
   * Otherwise it returns text.
   */
  public static String rtrim(String text, String tail) {
    if (text == null) {
      return null;
    }

    if (Strings.isNullOrEmpty(tail)) {
      return text;
    }

    if (text.endsWith(tail)) {
      return text.substring(0, text.length() - tail.length());
    }

    return text;
  }
}
