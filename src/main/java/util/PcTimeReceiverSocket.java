package util;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class PcTimeReceiverSocket implements WebSocket.Listener {

    private WebSocket webSocket;

    // Start the WebSocket client and connect to the server
    public static void startClient(String token) {
        PcTimeReceiverSocket client = new PcTimeReceiverSocket();
        String serverUri = "ws://localhost:8082/?token=" + token; // Replace with your WebSocket server URL

        HttpClient clientHttp = HttpClient.newHttpClient();
        clientHttp.newWebSocketBuilder()
                .buildAsync(URI.create(serverUri), client)
                .thenAccept(webSocket -> {
                    client.webSocket = webSocket;
                    System.out.println("Connected to WebSocket server");
                })
                .exceptionally(e -> {
                    System.err.println("Failed to connect to WebSocket server: " + e.getMessage());
                    return null;
                });
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("WebSocket connection opened");
        webSocket.request(1);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("Received message: " + data);
        webSocket.request(1); // Request the next message
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed with status " + statusCode + " and reason: " + reason);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    @Override
    public CompletionStage<?> onPing(WebSocket webSocket, java.nio.ByteBuffer message) {
        webSocket.request(1);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<?> onPong(WebSocket webSocket, java.nio.ByteBuffer message) {
        webSocket.request(1);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, java.nio.ByteBuffer data, boolean last) {
        webSocket.request(1);
        return CompletableFuture.completedFuture(null);
    }
}
