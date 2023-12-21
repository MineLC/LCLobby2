package lc.lobby2.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TreeMap;
import java.util.logging.Level;

import lc.lobby2.Lobby2;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigController {
    private final File configFolder;
    private final TreeMap<String, Configuration> configs;
    private static ConfigController instance;
    private static ConfigurationSection Lg = Lobby2.getInstance().getConfig().getConfigurationSection("Messages");

    public ConfigController() {
        this.configs = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        instance = this;
        this.configFolder = Lobby2.getInstance().getDataFolder();
        if (!this.configFolder.exists()) {
            this.configFolder.mkdirs();
        }

    }

    public static ConfigController getInstance() {
        return instance;
    }

    public void loadConfigFiles(String... filenames) {
        String[] var5 = filenames;
        int var4 = filenames.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String filename = var5[var3];
            File configFile = new File(this.configFolder, filename);

            try {
                if (!configFile.exists()) {
                    configFile.createNewFile();
                    InputStream in = Lobby2.getInstance().getResource(filename);
                    if (in != null) {
                        try {
                            OutputStream out = new FileOutputStream(configFile);
                            byte[] buf = new byte[1024];

                            int len;
                            while((len = in.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }

                            out.close();
                            in.close();
                        } catch (Exception var12) {
                            var12.printStackTrace();
                        }
                    }
                }

                Configuration config = new Configuration(configFile);
                config.load();
                this.configs.put(filename, config);
            } catch (IOException var13) {
                var13.printStackTrace();
            } catch (InvalidConfigurationException var14) {
                var14.printStackTrace();
            }
        }

    }

    public String Lang(String S) {
        return Lg.isSet(S) ? Lg.getString(S) : "Error, please check your config";
    }

    public YamlConfiguration getConfig(String filename) {
        return this.configs.containsKey(filename) ? ((Configuration)this.configs.get(filename)).getConfig() : null;
    }

    public YamlConfiguration getDefaultConfig() {
        return ((Configuration)this.configs.get("config.yml")).getConfig();
    }

    public YamlConfiguration getServersConfig() {
        return ((Configuration)this.configs.get("servers.yml")).getConfig();
    }

    public YamlConfiguration getMobsConfig() {
        return ((Configuration)this.configs.get("mobs.yml")).getConfig();
    }

    public void saveMobsConfig() {
        this.getMobsConfig().saveToString();

        try {
            ((Configuration)this.configs.get("mobs.yml")).getConfig().save(Lobby2.getInstance().getDataFolder() + File.separator + "mobs.yml");
        } catch (IOException var2) {
            Bukkit.getLogger().log(Level.WARNING, "Error saving mobs.yml config");
        }

    }

    private static class Configuration {
        private File configFile;
        private YamlConfiguration config;

        public Configuration(File configFile) {
            this.configFile = configFile;
            this.config = new YamlConfiguration();
        }

        public YamlConfiguration getConfig() {
            return this.config;
        }

        public void load() throws IOException, InvalidConfigurationException {
            this.config.load(this.configFile);
        }
    }
}
