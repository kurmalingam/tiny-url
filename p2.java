import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class LinkShortener {
    private static final String BASE_URL = "http://short.ly/";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private Map<String, String> urlMap;
    private Map<String, String> reverseUrlMap;
    private Random random;

    public LinkShortener() {
        urlMap = new HashMap<>();
        reverseUrlMap = new HashMap<>();
        random = new Random();
    }

    public String shortenURL(String longUrl) {
        if (reverseUrlMap.containsKey(longUrl)) {
            return BASE_URL + reverseUrlMap.get(longUrl);
        }

        String shortUrl = generateShortURL();
        while (urlMap.containsKey(shortUrl)) {
            shortUrl = generateShortURL();
        }

        urlMap.put(shortUrl, longUrl);
        reverseUrlMap.put(longUrl, shortUrl);
        return BASE_URL + shortUrl;
    }

    public String expandURL(String shortUrl) {
        String key = shortUrl.replace(BASE_URL, "");
        if (urlMap.containsKey(key)) {
            return urlMap.get(key);
        }
        return "Error: Invalid short URL.";
    }

    private String generateShortURL() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkShortener linkShortener = new LinkShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option: 1. Shorten URL 2. Expand URL 3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    System.out.println("Enter the long URL:");
                    String longUrl = scanner.nextLine();
                    String shortUrl = linkShortener.shortenURL(longUrl);
                    System.out.println("Short URL: " + shortUrl);
                    break;
                case 2:
                    System.out.println("Enter the short URL:");
                    String shortUrlInput = scanner.nextLine();
                    String expandedUrl = linkShortener.expandURL(shortUrlInput);
                    System.out.println("Long URL: " + expandedUrl);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
