package com.example.EcommerceDddDemoJava.shared.tracing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TraceHelper {

    private static int serialNo = 1;
    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public static CompletableFuture<Void> logInfoAsync(String source, String message) {
        log.info("{}: [TRACE] {} | {}", serialNo++, source, message);
        return delay(3000);
    }

    public static CompletableFuture<Void> logErrorAsync(String source, String message) {
        log.error("{}: [TRACE] {} | {}", serialNo++, source, message);
        return delay(3000);
    }

    private static CompletableFuture<Void> delay(long milliseconds) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        scheduler.schedule(() -> future.complete(null), milliseconds, TimeUnit.MILLISECONDS);
        return future;
    }
}