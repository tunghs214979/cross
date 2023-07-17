package com.it.loganalyze.util;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.*;

public class JsonConverter {
    private String inputFileName;
    private String outputFileName;

    public JsonConverter(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public void convert() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(inputFileName));
        JSONObject result = new JSONObject();

        for (int i = 0; i < lines.size(); i++) {
            try {
                JSONObject line = new JSONObject(lines.get(i));
                String key = "line_" + (i + 1);
                JSONObject value = new JSONObject();
                value.put("Timestamp", line.getJSONObject("transaction").optString("time", ""));
                value.put("transaction_id", line.getJSONObject("transaction").optString("transaction_id", ""));
                value.put("remote_address", line.getJSONObject("transaction").optString("remote_address", ""));
                value.put("remote_port", line.getJSONObject("transaction").optInt("remote_port", 0));
                value.put("local_address", line.getJSONObject("transaction").optString("local_address", ""));
                value.put("local_port", line.getJSONObject("transaction").optInt("local_port", 0));
                value.put("request_line", line.getJSONObject("request").optString("request_line", ""));
                //value.put("respond_status", line.getJSONObject("response").optString("status_line", ""));
                value.put("respond_body", line.getJSONObject("response").optString("body", ""));
                //value.put("audit_data_messages", line.getJSONArray("audit_data").getJSONObject(0).optString("messages", ""));

                // Add attacktype
                JSONArray messages = line.getJSONObject("audit_data").getJSONArray("messages");
                for (int j = 0; j < messages.length(); j++) {
                    String message = messages.getString(j);
                    if (message.contains("[file \"/etc/modsecurity/rules/RESPONSE-980-CORRELATION.conf\"]")) {
                        String attacktype = message.split("\\[file \"/etc/modsecurity/rules/")[1].split("\\.conf")[0];
                        value.put("attacktype", attacktype);
                        break;
                    }
                }

                result.put(key, value);
            } catch (JSONException e) {              
            }
        }

        try (PrintWriter out = new PrintWriter(outputFileName)) {
            out.println(result.toString(4));
        }
    }
}
