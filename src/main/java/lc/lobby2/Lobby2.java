package lc.lobby2;

import com.google.common.collect.Maps;
import lc.lobby2.Controller.ConfigController;
import lc.lobby2.Controller.LobbyController;
import lc.lobby2.Controller.MobsController;
import lc.lobby2.Controller.ServersController;
import lc.lobby2.Listener.Events;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.*;

public final class Lobby2 extends JavaPlugin {
    private static Lobby2 instance;
    private int countdown = 10;
    private boolean titlechange;
    public static Map<String, Long> cooldown = Maps.newHashMap();
    public static ArrayList<String> bossbar = new ArrayList<>();

    public Lobby2() {
    }



    public void onEnable() {

        try {
            instance = this;
            World world = Bukkit.getWorld("world");
            world.setSpawnLocation(0, 64, 0);
            world.setAutoSave(false);
            world.getWorldBorder().setCenter(world.getSpawnLocation());
            world.getWorldBorder().setSize(500.0D);
            this.loadWorld("world2");
            World world2 = Bukkit.getWorld("world2");
            world2.setSpawnLocation(0, 30, 0);
            world2.setAutoSave(false);
            world2.getWorldBorder().setCenter(world.getSpawnLocation());
            world2.getWorldBorder().setSize(500.0D);
            Iterator var4 = world.getEntities().iterator();



            Entity mb;
            while(var4.hasNext()) {
                mb = (Entity)var4.next();
                mb.remove();
            }

            var4 = world2.getEntities().iterator();

            while(var4.hasNext()) {
                mb = (Entity)var4.next();
                mb.remove();
            }

            ConfigController cfg = new ConfigController();
            cfg.loadConfigFiles(new String[]{"config.yml", "servers.yml", "mobs.yml"});
            this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            Iterator var5 = cfg.getServersConfig().getKeys(false).iterator();

            String m;
            while(var5.hasNext()) {
                m = (String)var5.next();
                ServersController.getServer(m);
            }

            var5 = cfg.getMobsConfig().getKeys(false).iterator();

            while(var5.hasNext()) {
                m = (String)var5.next();
                MobsController.getMob(m);
            }

            this.UpdateServers();
            this.updateMobs();
            this.updateSelector();
            this.AutoRestart();

            this.getServer().getPluginManager().registerEvents(new Events(), this);
            this.AutoFlyJump();
        } catch (Exception var6) {
            var6.printStackTrace();
            Bukkit.shutdown();
        }

    }

    private boolean loadWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generateStructures(false);
        worldCreator.generator(new ChunkGenerator() {
            public List<BlockPopulator> getDefaultPopulators(World world) {
                return Arrays.asList();
            }

            public boolean canSpawn(World world, int x, int z) {
                return true;
            }

            public byte[] generate(World world, Random random, int x, int z) {
                return new byte['耀'];
            }

            /*public Location getFixedSpawnLocation(World world, Random random) {
                return new Location(world, 0.0D, 64.0D, 0.0D);
            }*/
        });
        World world = worldCreator.createWorld();
        world.setDifficulty(Difficulty.NORMAL);
        world.setSpawnFlags(false, false);
        world.setPVP(true);
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);
        world.setTicksPerAnimalSpawns(0);
        world.setTicksPerMonsterSpawns(0);
        world.setGameRuleValue("mobGriefing", "true");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("showDeathMessages", "false");
        world.setGameRuleValue("spectatorsGenerateChunks", "false");
        boolean loaded = false;
        Iterator var6 = this.getServer().getWorlds().iterator();

        while(var6.hasNext()) {
            World w = (World)var6.next();
            if (w.getName().equals(world.getName())) {
                loaded = true;
                break;
            }
        }

        return loaded;
    }

    public void AutoFlyJump() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                while(var2.hasNext()) {
                    Player p = (Player)var2.next();
                    if (!p.getAllowFlight()) {
                        p.setAllowFlight(true);
                    }
                }

            }
        }, 40L, 30L);
    }

    private void AutoRestart() {
        Random random = new Random();
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                if (Lobby2.this.countdown > 1) {
                    Lobby2.this.broadcast(ChatColor.RED + "El servidor sera reiniciado en " + Lobby2.this.countdown + " segundos!");
                } else if (Lobby2.this.countdown == 1) {
                    Lobby2.this.broadcast(ChatColor.RED + "El servidor sera reiniciado en " + Lobby2.this.countdown + " segundo!");
                } else if (Lobby2.this.countdown == 0) {
                    Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                    while(var2.hasNext()) {
                        Player p = (Player)var2.next();
                        p.kickPlayer(ChatColor.RED + "Reiniciando servidor!");
                    }

                    Bukkit.shutdown();
                }

                Lobby2 var10000 = Lobby2.this;
                var10000.countdown = var10000.countdown - 1;
            }
        }, (long)('ꣀ' + random.nextInt(7500)) * 20L, 20L);
    }

    public void broadcast(String msg) {
        Iterator var3 = Bukkit.getOnlinePlayers().iterator();

        while(var3.hasNext()) {
            Player Online = (Player)var3.next();
            Online.sendMessage(msg);
        }

    }

    public void onDisable() {
        if (ConfigController.getInstance().getDefaultConfig().getBoolean("KillBungeeMobsOnStop")) {
            Iterator var2 = MobsController.getAllEntities().keySet().iterator();

            while(var2.hasNext()) {
                LivingEntity mb = (LivingEntity)var2.next();
                mb.remove();
            }
        }

    }


    private void UpdateServers() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            public void run() {
                ServersController.pingAllServers();
            }
        }, 40L, 20L);
    }

    private void updateSelector() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                LobbyController.updateInvSelector();
            }
        }, 40L, 25L);
    }

    private void updateMobs() {
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                MobsController.updateAllMobs();
            }
        }, 80L, 55L);
    }

    public static void sendPlayerToServer(Player player, String server, String svName) {
        try {
            if (hasCooldown(player)) {
                return;
            }

            cooldown.put(player.getName(), System.currentTimeMillis() + 1500L);
            player.sendMessage(ChatColor.GREEN + "Conectando al servidor " + ChatColor.YELLOW + svName + ChatColor.GREEN + "!");
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(getInstance(), "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    private static boolean hasCooldown(Player p) {
        boolean ret = false;
        if (cooldown.containsKey(p.getName())) {
            if (cooldown.get(p.getName()) < System.currentTimeMillis()) {
                cooldown.remove(p.getName());
            } else {
                ret = true;
            }
        }

        return ret;
    }

    public static Lobby2 getInstance() {
        return instance;
    }

}
