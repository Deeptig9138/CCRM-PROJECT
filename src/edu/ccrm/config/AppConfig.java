package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

public final class AppConfig {
    private static AppConfig instance;
    private final Path dataFolder;
    private final Path exportFolder;
    private final Path backupFolder;
    private final DateTimeFormatter timestampFormatter;

    private AppConfig() {
        this.dataFolder = Paths.get("data");
        this.exportFolder = dataFolder.resolve("export");
        this.backupFolder = Paths.get("backups");
        this.timestampFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public Path getDataFolder() { return dataFolder; }
    public Path getExportFolder() { return exportFolder; }
    public Path getBackupFolder() { return backupFolder; }
    public DateTimeFormatter getTimestampFormatter() { return timestampFormatter; }
}