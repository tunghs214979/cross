package com.it.loganalyze.util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonConverterForDebug {
    static Pattern getPattern_for_msg = Pattern.compile("\\[msg\\s+\"(.+?)\"\\]");
    static Pattern pattern_for_attack = Pattern.compile("\\[file \".+\\/(.*?).conf\"\\]");
    static Pattern pattern_for_id = Pattern.compile("\\[id\\s+\"(\\d+)\"\\]");
    static Pattern getPattern_for_severity = Pattern.compile("\\[severity\\s+\"([A-Z]+)\"\\]");
    static Pattern pattern_for_date = Pattern.compile("(?:(?:0[1-9])|(?:[12][0-9])|(?:3[01])|[1-9])\\/\\b(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\b\\/(?>\\d\\d){1,2}\\:(?:2[0123]|[01]?[0-9]):(?:[0-5][0-9]):(?:(?:[0-5]?[0-9]|60)(?:[:.,][0-9]+)?)");

    public static void parseLogFile(String logFile) {
    	String outputFile = "src/main/resources/com/it/loganalyze/log/debug.json";
        List<String> logEntries = null;
        try {
            logEntries = Files.readAllLines(Paths.get(logFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (logEntries != null) {
            StringBuilder json = new StringBuilder("{\n");
            int lineCount = 1;
            for (String logEntry : logEntries) {
                json.append("  \"line_").append(lineCount).append("\": {\n");
                Matcher matcher = pattern_for_date.matcher(logEntry);
                if (matcher.find()) {
                    json.append("    \"Time\": \"").append(matcher.group()).append("\",\n");
                }
                matcher = pattern_for_attack.matcher(logEntry);
                if (matcher.find()) {
                    json.append("    \"Attackname\": \"").append(matcher.group(1)).append("\",\n");
                }
                matcher = getPattern_for_msg.matcher(logEntry);
                if (matcher.find()) {
                    json.append("    \"Msg\": \"").append(matcher.group(1)).append("\",\n");
                }
                matcher = pattern_for_id.matcher(logEntry);
                if (matcher.find()) {
                    json.append("    \"id\": \"").append(matcher.group(1)).append("\",\n");
                }
                matcher = getPattern_for_severity.matcher(logEntry);
                if (matcher.find()) {
                    json.append("    \"Severity\": \"").append(matcher.group(1)).append("\",\n");
                }
                json.setLength(json.length() - 2);
                json.append("\n  },\n");
                lineCount++;
            }
            json.setLength(json.length() - 2);
            json.append("\n}");
            try {
                Files.write(Paths.get(outputFile), json.toString().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
            }
        }
    }
}

