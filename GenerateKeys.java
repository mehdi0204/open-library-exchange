import java.io.*;
import java.security.*;
import java.util.Base64;

public class GenerateKeys {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        String privateKey = "-----BEGIN PRIVATE KEY-----\n"
                + Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(kp.getPrivate().getEncoded())
                + "\n-----END PRIVATE KEY-----\n";
        write("src/main/resources/privateKey.pem", privateKey);

        String publicKey = "-----BEGIN PUBLIC KEY-----\n"
                + Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(kp.getPublic().getEncoded())
                + "\n-----END PUBLIC KEY-----\n";
        write("src/main/resources/publicKey.pem", publicKey);

        System.out.println("Clés générées avec succès !");
        System.out.println("  -> src/main/resources/privateKey.pem");
        System.out.println("  -> src/main/resources/publicKey.pem");
    }

    static void write(String path, String content) throws Exception {
        new File(path).getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(content);
        }
    }
}
