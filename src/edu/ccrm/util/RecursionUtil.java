package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public final class RecursionUtil {
    private RecursionUtil(){}

    public static long computeDirectorySize(Path root) throws IOException {
        final long[] total = {0L};
        if (Files.notExists(root)) return 0L;
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                total[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }
        });
        return total[0];
    }
}