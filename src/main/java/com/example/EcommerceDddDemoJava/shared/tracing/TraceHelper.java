package com.example.EcommerceDddDemoJava.shared.tracing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TraceHelper {

    private static int serialNo = 1;

    public static void logInfo(String source, String message) {
        log.info("{}: [TRACE] {} | {}", serialNo++, source, message);
    }

    public static void logError(String source, String message) {
        log.error("{}: [TRACE] {} | {}", serialNo++, source, message);
    }
}