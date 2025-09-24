package edu.ccrm.service;

import edu.ccrm.config.AppConfig;
import edu.ccrm.util.RecursionUtil;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

public class BackupService {
    private final AppConfig cfg = AppConfig.getInstance();

    public Path createBackup() throws IOException {
        Path export = cfg.getExportFolder();
        if (Files.notExists(export)) throw new NoSuchFileException("Export folder not found: " + export);
        String ts = LocalDateTime.now().format(cfg.getTimestampFormatter());
        Path target = cfg.getBackupFolder().resolve("backup_" + ts);
        Files.createDirectories(target);
        try (var stream = Files.walk(export)) {
            stream.filter(Files::isRegularFile).forEach(src -> {
                try {
                    Path rel = export.relativize(src);
                    Path dest = target.resolve(rel);
                    Files.createDirectories(dest.getParent());
                    Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) { throw new RuntimeException(e); }
            });
        }
        return target;
    }

    public long getBackupSize(Path backupFolder) throws IOException {
        return RecursionUtil.computeDirectorySize(backupFolder);
    }

    public void listBackups() throws IOException {
        Path b = cfg.getBackupFolder();
        if (Files.notExists(b)) {
            System.out.println("No backups folder exists yet.");
            return;
        }
        try (var s = Files.list(b)) {
            s.forEach(p -> System.out.println(p.getFileName()));
        }
    }
}