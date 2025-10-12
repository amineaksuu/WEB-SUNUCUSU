import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Basit bir HTTP sunucusu (socket tabanlı).
 * Port: 1989
 *
 * Çalıştırma:
 * javac SimpleWebServer.java
 * java SimpleWebServer
 *
 * Ardından tarayıcıdan: http://localhost:1989/ adresine gidin.
 */
public class SimpleWebServer {

    // Sunucunun dinleyeceği port
    private static final int PORT = 1989;

    public static void main(String[] args) {
        System.out.println("Basit Web Sunucusu başlatılıyor... Port: " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Sunucu hazır. Tarayıcıdan http://localhost:" + PORT + " adresine bağlanın.");
            while (true) {
                // Yeni bir istemci bağlantısı kabul et
                Socket clientSocket = serverSocket.accept();
                // Her bağlantıyı ayrı bir Thread ile işleyelim
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (BindException be) {
            System.err.println("Port " + PORT + " zaten kullanılıyor veya erişim yok. (Hata: " + be.getMessage() + ")");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Bağlantıyı işleyen sınıf
    private static class ClientHandler implements Runnable {
        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Yeni bağlantı: " + socket.getInetAddress() + ":" + socket.getPort());
            try (
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                BufferedOutputStream dataOut = new BufferedOutputStream(output);
            ) {
                String requestLine = reader.readLine();
                System.out.println("İstek: " + requestLine);

                String header;
                while ((header = reader.readLine()) != null && header.length() != 0) {}

                String html = buildHtmlContent();

                byte[] contentBytes = html.getBytes(StandardCharsets.UTF_8);
                String response =
                        "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html; charset=utf-8\r\n" +
                        "Content-Length: " + contentBytes.length + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n";

                dataOut.write(response.getBytes(StandardCharsets.UTF_8));
                dataOut.write(contentBytes);
                dataOut.flush();

            } catch (IOException e) {
                System.err.println("Bağlantı işlenirken hata: " + e.getMessage());
            } finally {
                try { socket.close(); } catch (IOException ignore) {}
                System.out.println("Bağlantı kapatıldı: " + socket.getInetAddress());
            }
        }

        // HTML içeriği — Amine Aksu bilgileriyle
        private String buildHtmlContent() {
            String name = "Amine Aksu";
            String studentNo = "1240505053";
            String bio = "Ben Amine Aksu, 19 yaşındayım. İstanbul’da yaşıyorum ve Kırklareli Üniversitesi Yazılım Mühendisliği bölümünde öğrenciyim. "
                       + "Yazılım geliştirmeyi, yeni teknolojiler öğrenmeyi ve özellikle Java ile projeler yapmayı seviyorum. "
                       + "Ağ programlama, web tasarımı ve veri yapıları alanlarında kendimi geliştirmeye çalışıyorum.";

            return "<!doctype html>\n" +
                    "<html lang=\"tr\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "  <title>Amine Aksu - Web Sunucu</title>\n" +
                    "  <style>\n" +
                    "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(120deg, #f0f7ff, #dbeafe); color: #222; padding: 40px; }\n" +
                    "    .card { max-width: 800px; margin: 0 auto; background: white; border-radius: 12px; box-shadow: 0 8px 24px rgba(0,0,0,0.08); padding: 30px; text-align: center; }\n" +
                    "    h1 { color: #0c4a6e; font-size: 38px; margin-bottom: 8px; }\n" +
                    "    h2 { color: #2563eb; font-size: 20px; margin-bottom: 20px; font-weight: 600; }\n" +
                    "    p { line-height: 1.7; color: #334155; font-size: 17px; text-align: justify; }\n" +
                    "    .meta { font-size: 13px; color: #64748b; margin-top: 20px; }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div class=\"card\">\n" +
                    "    <h1>" + escapeHtml(name) + "</h1>\n" +
                    "    <h2>Öğrenci No: " + escapeHtml(studentNo) + "</h2>\n" +
                    "    <p>" + escapeHtml(bio) + "</p>\n" +
                    "    <div class=\"meta\">Sunucu: Java Socket ile yazılmış basit HTTP sunucusu · Port: " + PORT + "</div>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>";
        }

        private String escapeHtml(String s) {
            return s.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
        }
    }
}
