package org.odds;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Hex;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Tests {
    public static void main(String[] args) {
        // requestStuff();

        mariadbPasswd("testings");
    }

    public static void mariadbPasswd(String p) {
        byte[] txt2Bytes = p.getBytes(StandardCharsets.UTF_8);
        byte[] sha1 = DigestUtils.sha1(DigestUtils.sha1(txt2Bytes));
        String res = "*".concat(Hex.toHexString(sha1)).toUpperCase();

        System.out.println(res);
    }

    public static void requestStuff() throws IOException, InterruptedException {
        HttpClient cli = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://666b14587013419182d22e3b.mockapi.io/api/v1/stuff")).build();
        HttpResponse<String> resp = cli.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println(resp.body());
        JSONArray arr = new JSONArray(resp.body());
        arr.forEach((jsonObj) -> {
            System.out.println(new JSONObject(jsonObj.toString()).get("name"));
        });
    }
}
