package lc.lobby2.Listener;

import com.google.common.collect.Lists;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import lc.core2.entities.Database;
import lc.core2.entities.Jugador;
import lc.core2.entities.Ranks;
import lc.lobby2.Controller.LobbyController;
import lc.lobby2.Controller.MobsController;
import lc.lobby2.Lobby2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.util.Vector;


public class Events implements Listener {
	private static ItemStack book;
	
	public static Location spawn = Bukkit.getWorld("world").getSpawnLocation(); //minilobby
	public static Location spawn2 = Bukkit.getWorld("world2").getSpawnLocation();
	static {
		book = new ItemStack(Material.BOOK, 1);
		ItemMeta meta = book.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 2 , true);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lNovedades"));
		List<String> lore = Lists.newArrayList();
    	lore.add(ChatColor.YELLOW+"Click para leer acerca de las novedades.");
    	meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		book.setItemMeta(meta);
	}
    public Events() {
    }

   /* @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        Jugador j = Jugador.getJugador(p);
        if (!j.is_VIP()) {
            p.setFlying(false);
            Location loc = p.getLocation().clone();
            Vector vel = p.getVelocity().clone();
            Vector jump = vel.multiply(0.3D);
            Vector look = loc.getDirection().normalize().multiply(1.25D);
            p.setVelocity(look.add(jump));
            p.setAllowFlight(false);
            p.playSound(loc, Sound.ENDERDRAGON_WINGS, 0.7F, (float)(0.5D + Math.random()));
            e.setCancelled(true);
        }

    }
    */
    
	@EventHandler
	  public void onPreCommand(PlayerCommandPreprocessEvent event)
	  {
		String msg = event.getMessage().toLowerCase();
	    if ((msg.startsWith("/gamemode")) || 
	      (msg.startsWith("/minecraft:gamemode")) || 
	      (msg.startsWith("/summon")) || 
	      (msg.startsWith("/minecraft:summon")) || 
	      (msg.startsWith("/minecraft:worldborder")) ||
	      (msg.startsWith("/worldborder")) ||
	      (msg.startsWith("/help")) || 
	      (msg.startsWith("/plugins")) ||
	      (msg.startsWith("/minecraft:op")) ||
	      (msg.equalsIgnoreCase("/op")) ||
	      (msg.startsWith("/minecraft:deop")) ||
	      (msg.startsWith("/kick")) ||
	      (msg.startsWith("/minecraft:kick")) ||
	      (msg.startsWith("/tp")) ||
	      (msg.startsWith("/minecraft:tp")) ||
	      (msg.startsWith("/say")) ||
	      (msg.startsWith("/minecraft:say")) ||
	      (msg.startsWith("/minecraft:me")) ||
	      (msg.startsWith("/ver")) || 
	      (msg.startsWith("/pl")) || 
	      (msg.startsWith("/bukkit")))
	    {
	      event.setCancelled(true);
	      Player player = event.getPlayer();
	      player.sendMessage(ChatColor.RED+"No tienes permitido hacer esto!");
	      Bukkit.getLogger().log(Level.INFO, "AntiCrash: " +player.getName()+" : "+ msg);
	      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	      LocalDateTime now = LocalDateTime.now();
	      String date= "["+ dtf.format(now) + "] "+ player.getName() + " : ";
	      String logger = date+msg;
	     
	    }

	  }

	
	

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        e.blockList().clear();
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onAsyncPRELogin(AsyncPlayerPreLoginEvent e) {
        Jugador jug = Jugador.getJugador(e.getName());
        Database.loadPlayerRank_SYNC(jug);
        Database.loadPlayerOpciones_SVS_SYNC(jug);
        Database.loadPlayerBungee_SYNC(jug);
        Database.loadPlayerCoins_SYNC(jug);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage((String)null);
        Player p = e.getPlayer();
        p.getInventory().setItem(1, book);

        Jugador jug = Jugador.getJugador(p);
        jug.setBukkitPlayer(p);
        p.teleport(spawn);
        
        LobbyController.onJoin(p, jug);
        Iterator var5 = Bukkit.getOnlinePlayers().iterator();

        while(var5.hasNext()) {
            Player Online = (Player)var5.next();
            Jugador j2 = Jugador.getJugador(Online);
            if (!j2.isOpciones_SVS_Enable_Players()) {
                Online.hidePlayer(p);
            }
        }

        jug.checkRankduration();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage((String)null);
       // LobbyController.onQuit(e.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        e.setLeaveMessage((String)null);
    }
    

    @EventHandler(
        priority = EventPriority.HIGHEST
    )
    
   
    public void onChat(AsyncPlayerChatEvent e) {
        if (!e.isCancelled()) {
            Player p = e.getPlayer();
            Jugador j = Jugador.getJugador(p);
            if (!j.isOpciones_SVS_Enable_Chat()) {
                p.sendMessage(ChatColor.RED + "Tienes el chat desactivado, activalo para poder escribir.");
                e.setCancelled(true);
            } else {
                String msg = e.getMessage().replace("%", "");

                try {
                    Iterator var6 = Bukkit.getOnlinePlayers().iterator();

                    while(var6.hasNext()) {
                        Player rc = (Player)var6.next();
                        if (!Jugador.getJugador(rc).isOpciones_SVS_Enable_Chat()) {
                            e.getRecipients().remove(rc);
                        }
                    }
                } catch (Exception var7) {
                    var7.printStackTrace();
                }

                if (j.isHideRank()) {
                    e.setFormat(ChatColor.YELLOW + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + msg);
                } else if (j.is_Owner()) {
                    e.setFormat("" + ChatColor.DARK_RED + ChatColor.BOLD + Ranks.OWNER.name() + " " + ChatColor.DARK_GRAY + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_Admin()) {
                    e.setFormat("" + ChatColor.RED + ChatColor.BOLD + Ranks.ADMIN.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_MODERADOR()) {
                    e.setFormat("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + Ranks.MOD.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_AYUDANTE()) {
                    e.setFormat("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + Ranks.AYUDANTE.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_YOUTUBER()) {
                    e.setFormat("" + ChatColor.RED + ChatColor.BOLD + "You" + ChatColor.WHITE + ChatColor.BOLD + "Tuber " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_BUILDER()) {
                    e.setFormat("" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + Ranks.BUILDER.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_RUBY()) {
                    e.setFormat(ChatColor.AQUA + "★ " + ChatColor.RED + ChatColor.BOLD + Ranks.RUBY.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_ELITE()) {
                    e.setFormat("" + ChatColor.GOLD + ChatColor.BOLD + Ranks.ELITE.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_SVIP()) {
                    e.setFormat("" + ChatColor.GREEN + ChatColor.BOLD + Ranks.SVIP.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_VIP()) {
                    e.setFormat("" + ChatColor.AQUA + ChatColor.BOLD + Ranks.VIP.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + ChatColor.translateAlternateColorCodes('&', msg));
                } else if (j.is_Premium()) {
                    e.setFormat("" + ChatColor.BLUE + ChatColor.BOLD + Ranks.PREMIUM.name() + " " + j.getNameTagColor() + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + msg);
                } else {
                    e.setFormat(ChatColor.YELLOW + p.getName() + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + msg);
                }

            }
        }
    }


     @EventHandler
 	public void ArmorStandGenerador(PlayerArmorStandManipulateEvent e) {
 		e.setCancelled(true);
 	}
    
    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        e.setCancelled(true);
        if (e.getCause() == DamageCause.VOID) {
            Bukkit.getScheduler().runTaskLater(Lobby2.getInstance(), new Runnable() {
                public void run() {
                    e.getEntity().teleport(e.getEntity().getWorld().getSpawnLocation());
                }
            }, 1L);
        }

    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockMove(BlockFromToEvent e) {
        e.setCancelled(true);
    }


  

   /*  @EventHandler
        public void onInventoryClickEvent(InventoryClickEvent event) {
            
            Player player = (Player) event.getWhoClicked();
                  if(event.
                 player
                 p.closeInventory();
            }
            
          */
        
    
    @EventHandler
    public void onInvClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL) {
            ItemStack is = e.getPlayer().getItemInHand();
            if (is != null && is.getType() != Material.AIR) {
                Player p = e.getPlayer();
                if (!p.isOp()) {
                    e.setCancelled(true);
                }
                

                switch(is.getTypeId()) {
                case 130:
                    LobbyController.getInvRangos(p).open(p);
                    break;
                case 146:
                    //p.performCommand("chestcommands open example");
                    p.chat("/menu");
                   
                    //p.sendMessage(ChatColor.RED + "Esto estará disponible en la próxima actualización (Muy Pronto).");
                    break;
                case 339:
                    LobbyController.getInvStats_MAIN().open(p);
                    break;
                case 345:
                    LobbyController.getInvSelector().open(p);
                    break;
                case 397:
                    LobbyController.getInvPerfil(p).open(p);
                }

            }
        }
    }

    @EventHandler
    public void onPlayerInteractWithEntity(final PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof LivingEntity) {
            final Player p = e.getPlayer();
            if (MobsController.getAllEntities().containsKey(e.getRightClicked())) {
                e.setCancelled(true);
                if (e.getPlayer().getItemInHand().getType() == Material.AIR) {
                    ((MobsController)MobsController.getAllEntities().get(e.getRightClicked())).openInventory(p);
                } else {
                    Bukkit.getScheduler().runTaskLater(Lobby2.getInstance(), new Runnable() {
                        public void run() {
                            ((MobsController)MobsController.getAllEntities().get(e.getRightClicked())).openInventory(p);
                        }
                    }, 2L);
                }

            } else if (e.getRightClicked() instanceof Player) {
           
                    Player toStack = (Player)e.getRightClicked();
                    if (p != toStack) {
                        if (p.getOpenInventory() == null) {
                            if (toStack.getOpenInventory() == null) {
                                if (toStack.getVehicle() == e.getPlayer()) {
                                    e.setCancelled(true);
                                } else if (toStack.getPassenger() == e.getPlayer()) {
                                    e.setCancelled(true);
                                } else if (toStack.getPassenger() == null || toStack.getVehicle() == null) {
                                    p.setPassenger(toStack);
                                    e.getPlayer().sendMessage(ChatColor.GREEN + "Has apilado a " + ChatColor.YELLOW + e.getRightClicked().getName() + ChatColor.GREEN + "!");
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }
                 
            }
        }
    }

    @SuppressWarnings("deprecation")
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR||
           (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            Player p = event.getPlayer();
            Entity entity = p.getPassenger();
            if (entity != null) {
                entity.leaveVehicle();
                this.multiplyVelocity(entity, 1.8D, 1.3D, 1.8D, p.getLocation().getDirection(), 0.3D);
            }
            else if(p.getItemInHand().getType() == Material.BOOK) {
            	
            	Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "ibooks open examplebook " + p.getName());
            	
            	}
        }

    }

    private void multiplyVelocity(Entity e, double strength, double yVel, double yVelMaximum, Vector prevVector, double yMultiply) {
        if (prevVector.length() != 0.0D) {
            prevVector.normalize();
            prevVector.multiply(strength);
            prevVector.setY(prevVector.getY() + yVel);
            if (prevVector.getY() > yVelMaximum) {
                prevVector.setY(yVelMaximum);
            }

            if (e.isOnGround()) {
                prevVector.setY(prevVector.getY() + yMultiply);
            }

            e.setVelocity(prevVector);
        }

    }
}
