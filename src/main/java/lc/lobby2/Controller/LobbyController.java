package lc.lobby2.Controller;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import lc.core2.entities.Database;
import lc.core2.entities.Jugador;
import lc.core2.entities.Ranks;
import lc.core2.utils.IconMenu;
import lc.core2.utils.ItemUtils;
import lc.lobby2.Listener.Events;
import lc.lobby2.Lobby2;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.World;
import org.bukkit.block.Skull;

import org.bukkit.enchantments.Enchantment;

public class LobbyController {
    private static Scoreboard scoreboard = null;
    private static IconMenu invColorSelector = null;
    private static IconMenu invSelector = null;
    private static IconMenu invVanidad = null;
    private static IconMenu invStats_MAIN = null;
    private static IconMenu invStats_HG = null;
    private static IconMenu invStats_HG_kills = null;
    private static IconMenu invStats_HG_deaths = null;
    private static IconMenu invStats_HG_part_ganadas = null;
    private static IconMenu invStats_HG_part_jugadas = null;
    private static IconMenu invStats_CHG = null;
    private static IconMenu invStats_CHG_kills = null;
    private static IconMenu invStats_CHG_deaths = null;
    private static IconMenu invStats_CHG_part_ganadas = null;
    private static IconMenu invStats_CHG_part_jugadas = null;
    private static IconMenu invStats_EW = null;
    private static IconMenu invStats_EW_kills = null;
    private static IconMenu invStats_EW_deaths = null;
    private static IconMenu invStats_EW_part_ganadas = null;
    private static IconMenu invStats_EW_part_jugadas = null;
    private static IconMenu invStats_SW = null;
    private static IconMenu invStats_SW_kills = null;
    private static IconMenu invStats_SW_deaths = null;
    private static IconMenu invStats_SW_part_ganadas = null;
    private static IconMenu invStats_SW_part_jugadas = null;
    private static IconMenu invStats_KITPVP = null;
    private static IconMenu invStats_KITPVP_kills = null;
    private static IconMenu invStats_KITPVP_deaths = null;
    private static IconMenu invStats_POTPVP = null;
    private static IconMenu invStats_POTPVP_kills = null;
    private static IconMenu invStats_POTPVP_deaths = null;
    private static IconMenu invStats_PVPG = null;
    private static IconMenu invStats_PVPG_kills = null;
    private static IconMenu invStats_PVPG_deaths = null;
    private static IconMenu invStats_PVPG_part_ganadas = null;
    private static IconMenu invStats_PVPG_part_jugadas = null;
    private static IconMenu invStats_PVPG_nucleos = null;
    private static IconMenu invStats_PVPG_monumentos = null;
    private static IconMenu invStats_PVPG_lanas = null;
    private static ItemUtils selector;
    private static IconMenu invMinigame = null;
    private static IconMenu invRedes = null;
    private static ItemUtils rangos;
    private static ItemUtils stats;
    private static ItemUtils vanidad;
    private static ItemStack PVPGamesIS;
    private static ItemStack Glass;
    private static ItemStack tnttag;
    private static ItemStack puentes;
    private static ItemStack buildbattle;
    private static ItemStack facebook;
    private static ItemStack twitter;
    private static ItemStack foro;
    private static ItemStack discord;
	private static Player prueba;
	public static HashMap<String, Team> TEAMS = new HashMap<String, Team>();
    static {
        selector = new ItemUtils(Material.COMPASS, Short.valueOf((short)0), 1, ChatColor.GREEN + "Selector De Servidores", ChatColor.GRAY + "Click derecho para abrir el selector de servidores");
        rangos = new ItemUtils(Material.ENDER_CHEST, Short.valueOf((short)0), 1, ChatColor.GREEN + "Rangos", ChatColor.GRAY + "Click derecho para abrir el menÃº de rangos");
        stats = new ItemUtils(Material.PAPER, Short.valueOf((short)0), 1, ChatColor.GREEN + "TOP Jugadores", ChatColor.GRAY + "Click derecho para abrir el menÃº de estadisticas");
        vanidad = new ItemUtils(Material.TRAPPED_CHEST, Short.valueOf((short)0), 1, ChatColor.GREEN + "Vanidad", ChatColor.GRAY + "Click derecho para abrir el menÃº de vanidad");
        PVPGamesIS = new ItemStack(Material.WOOL, 1, (short)14);
        Glass = new ItemStack(Material.STAINED_GLASS_PANE, 1,(short)8);
        facebook = new ItemStack(Material.INK_SACK, 1,(short) 6);
        twitter = new ItemStack(Material.INK_SACK, 1,(short)12);
        discord = new ItemStack(Material.INK_SACK, 1,(short) 5);
        foro = new ItemStack(Material.INK_SACK, 1,(short) 8);
        ItemMeta meta = Glass.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 2, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		List<String> lore = Lists.newArrayList();
    	lore.add(ChatColor.YELLOW+"www.minelc.com");
    	meta.setLore(lore);
		Glass.setItemMeta(meta);
		
		//tnttag
		tnttag = new ItemStack(Material.TNT, 1);
		ItemMeta tagmeta = tnttag.getItemMeta();
		tagmeta.addEnchant(Enchantment.DURABILITY, 2 , true);
		tagmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		tnttag.setItemMeta(tagmeta);
		//puentes
				puentes = new ItemStack(Material.BRICK, 1);
				ItemMeta tagpuentes = tnttag.getItemMeta();
				tagpuentes.addEnchant(Enchantment.DURABILITY, 2 , true);
				tagpuentes.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				puentes.setItemMeta(tagpuentes);
		//buildbattle
				buildbattle = new ItemStack(Material.CAKE, 1);
				ItemMeta tagbuild = tnttag.getItemMeta();
				tagbuild.addEnchant(Enchantment.DURABILITY, 2 , true);
				tagbuild.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				buildbattle.setItemMeta(tagpuentes);
				
    }

    public LobbyController() {
    }

    public static void onQuit(Player p) {
        Lobby2.cooldown.remove(p.getName());

        Jugador.removeJugador(p.getName());
        if (scoreboard.getTeams().contains(scoreboard.getTeam(p.getName()))) {
            scoreboard.getTeam(p.getName()).unregister();
        }

    }
    /* ESCOREBOARD*/
    

	@SuppressWarnings("deprecation")
	public static void updateScoreboard(Player Online) {
			try {
				Jugador jugOnline = Jugador.getJugador(Online);
				Scoreboard sb = Online.getScoreboard();
				
				if(sb == null || sb.getEntries().isEmpty()) {
					sb = Bukkit.getScoreboardManager().getNewScoreboard();
					jugOnline.getBukkitPlayer().setScoreboard(sb);
					
					if(TEAMS.containsKey("kills"+Online.getName())) {
						TEAMS.remove("kills"+Online.getName());
					}
					if(TEAMS.containsKey("deaths"+Online.getName())) {
						TEAMS.remove("deaths"+Online.getName());
					}
					if(TEAMS.containsKey("kdr"+Online.getName())) {
						TEAMS.remove("kdr"+Online.getName());
					}
					if(TEAMS.containsKey("jugadores"+Online.getName())) {
						TEAMS.remove("jugadores"+Online.getName());
					}
				}

				if(!TEAMS.containsKey("kills"+Online.getName())) {
					Team tm = sb.registerNewTeam(ChatColor.YELLOW+" ");
					tm.addEntry(ChatColor.YELLOW+" ");
					TEAMS.put("kills"+Online.getName(), tm);
				}
				if(!TEAMS.containsKey("deaths"+Online.getName())) {
					Team tm = sb.registerNewTeam(ChatColor.RED+" ");
					tm.addEntry(ChatColor.RED+" ");
					TEAMS.put("deaths"+Online.getName(), tm);
				}
				if(!TEAMS.containsKey("kdr"+Online.getName())) {
					Team tm = sb.registerNewTeam(ChatColor.GREEN+" ");
					tm.addEntry(ChatColor.GREEN+" ");
					TEAMS.put("kdr"+Online.getName(), tm);
				}
				
				if(!TEAMS.containsKey("lvl"+Online.getName())) {
					Team tm = sb.registerNewTeam(ChatColor.AQUA+" ");
					tm.addEntry(ChatColor.AQUA+" ");
					TEAMS.put("lvl"+Online.getName(), tm);
				}
				
				Objective objHealth = sb.getObjective("ShowHealth");
				if(objHealth == null) {
					objHealth = sb.registerNewObjective("ShowHealth", "health");
					objHealth.setDisplaySlot(DisplaySlot.BELOW_NAME);
					objHealth.setDisplayName(ChatColor.RED+"❤");
				}
				
				Objective objGame = sb.getObjective("Game");
				if(objGame == null) {
					objGame = sb.registerNewObjective("Game", "dummy");
					
					objGame.setDisplaySlot(DisplaySlot.SIDEBAR);
					
					objGame.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"));
					
					//nick
					objGame.getScore("   ").setScore(15);
					objGame.getScore(ChatColor.GREEN+""+ChatColor.BOLD+"Nick").setScore(14);
					objGame.getScore(ChatColor.GREEN + ""+ Online.getName()).setScore(13);
					//rango
					objGame.getScore("  ").setScore(12);
					objGame.getScore(ChatColor.RED+""+ChatColor.BOLD+"Rango").setScore(10);
					String rango = jugOnline.getRank().toString();
					if(rango.equalsIgnoreCase("owner")) {
						objGame.getScore(ChatColor.DARK_RED+""+ jugOnline.getRank()).setScore(9);
					}else if(rango.equalsIgnoreCase("admin") || rango.equalsIgnoreCase("ruby")) {
						objGame.getScore(ChatColor.RED+""+ jugOnline.getRank()).setScore(9);
					} else if(rango.equalsIgnoreCase("mod") || rango.equalsIgnoreCase("ayudante")) {
						objGame.getScore(ChatColor.DARK_PURPLE+""+ jugOnline.getRank()).setScore(9);
					} else if(rango.equalsIgnoreCase("builder")) {
						objGame.getScore(ChatColor.LIGHT_PURPLE+""+ jugOnline.getRank()).setScore(9);
					} else if(rango.equalsIgnoreCase("elite")) {
						objGame.getScore(ChatColor.GOLD+""+ jugOnline.getRank()).setScore(9);
					} else if(rango.equalsIgnoreCase("svip")) {
						objGame.getScore(ChatColor.GREEN+""+ jugOnline.getRank()).setScore(9);
					} else if(rango.equalsIgnoreCase("vip")) {
						objGame.getScore(ChatColor.AQUA+""+ jugOnline.getRank()).setScore(9);
					} else if(rango.equalsIgnoreCase("premium")) {
						objGame.getScore(ChatColor.BLUE+""+ jugOnline.getRank()).setScore(9);
					} else {
						objGame.getScore(ChatColor.YELLOW+""+ jugOnline.getRank()).setScore(9);
					}
					//locins
					objGame.getScore("   ").setScore(8);
					objGame.getScore(ChatColor.YELLOW+""+ChatColor.BOLD+"LCoins").setScore(7);
					objGame.getScore(ChatColor.YELLOW+""+jugOnline.getLcoins()).setScore(6);
					objGame.getScore("    ").setScore(5);
					//vip points

					objGame.getScore(ChatColor.AQUA+""+ChatColor.BOLD+"Vip-Points").setScore(4);
				    objGame.getScore(ChatColor.AQUA+""+jugOnline.getRankpoints()).setScore(3);
					objGame.getScore(" ").setScore(2);
					objGame.getScore(ChatColor.YELLOW + "play.minelc.com").setScore(1);
				}
				
				TEAMS.get("kills"+Online.getName()).setPrefix(ChatColor.YELLOW+""+jugOnline.getCHG_Stats_kills());
				TEAMS.get("deaths"+Online.getName()).setPrefix(ChatColor.RED+""+jugOnline.getCHG_Stats_deaths());
				TEAMS.get("kdr"+Online.getName()).setPrefix(ChatColor.GREEN+""+jugOnline.getCHG_Stats_kdr());
				TEAMS.get("lvl"+Online.getName()).setPrefix(ChatColor.AQUA+""+jugOnline.getCHG_Stats_Level());
				
				for(Player tmOnline : Bukkit.getOnlinePlayers()) {
					Jugador jugTM = Jugador.getJugador(tmOnline);
					
					try {
						Team tm = sb.getTeam(jugTM.getBukkitPlayer().getName());
						
						if(tm != null) {
							continue;
						}
						
						tm = sb.registerNewTeam(jugTM.getBukkitPlayer().getName());
						
						if(jugTM.isHideRank())
							tm.setPrefix(ChatColor.GRAY.toString());
						else if(jugTM.is_Owner())
							tm.setPrefix(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "+jugTM.getNameTagColor());
						else if(jugOnline.getBukkitPlayer().getName()=="obed_007") 
							tm.setPrefix(ChatColor.DARK_GRAY+";"+ChatColor.AQUA+"DEV"+ChatColor.DARK_GRAY+";" + jugOnline.getNameTagColor());
						
						else if(jugTM.is_Admin())
							tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "+jugTM.getNameTagColor());
						else if(jugTM.is_MODERADOR())
							tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "+jugTM.getNameTagColor());
						else if(jugTM.is_AYUDANTE())
							tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.AYUDANTE.name()+" "+jugTM.getNameTagColor());
						else if(jugTM.is_YOUTUBER()) {
							String youtuber = ChatColor.RED+""+ChatColor.BOLD+"YouTuber ";
							tm.setPrefix(youtuber+jugTM.getNameTagColor());	
						} 
						else if(jugTM.is_BUILDER())
							tm.setPrefix(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+jugTM.getNameTagColor());
						else if(jugTM.is_RUBY())
							tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+jugTM.getNameTagColor());
						else if(jugTM.is_ELITE())
							tm.setPrefix(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "+jugTM.getNameTagColor());
						else if(jugTM.is_RUBY())
							tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" " + jugTM.getNameTagColor());
						else if(jugTM.is_SVIP())
							tm.setPrefix(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "+jugTM.getNameTagColor());
						else if(jugTM.is_VIP())
							tm.setPrefix(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "+jugTM.getNameTagColor());
						else if(jugTM.is_Premium())
							tm.setPrefix(ChatColor.YELLOW.toString());
						else
							tm.setPrefix(ChatColor.GRAY.toString());
						
						tm.addPlayer(tmOnline);
						} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				
				//quitar?
				for(Player tmOnline : Bukkit.getOnlinePlayers()) {
					Scoreboard sbTM = tmOnline.getScoreboard();
					try {
						
						if(sbTM == null) {
							continue;
						}
						Team tm = sbTM.getTeam(jugOnline.getBukkitPlayer().getName());
						
						if(tm != null) {
							continue;
						}
						
						tm = sbTM.registerNewTeam(jugOnline.getBukkitPlayer().getName());
						
						if(jugOnline.isHideRank())
							tm.setPrefix(ChatColor.GRAY.toString());
						else if(jugOnline.is_Owner())
							tm.setPrefix(ChatColor.DARK_RED+""+ChatColor.BOLD+Ranks.OWNER.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.getBukkitPlayer().getName()=="obed_007") 
							tm.setPrefix(ChatColor.DARK_GRAY+";"+ChatColor.AQUA+"DEV"+ChatColor.DARK_GRAY+";" + jugOnline.getNameTagColor());
						
						else if(jugOnline.is_Admin())
							tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.ADMIN.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.is_MODERADOR())
							tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+Ranks.MOD.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.is_AYUDANTE())
							tm.setPrefix(ChatColor.DARK_PURPLE+""+ChatColor.BOLD+ Ranks.AYUDANTE.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.is_YOUTUBER()) {
							String youtuber = ChatColor.RED+""+ChatColor.BOLD+"YouTuber ";
							tm.setPrefix(youtuber+jugOnline.getNameTagColor());	
						}else if(jugOnline.is_BUILDER())
							tm.setPrefix(ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+Ranks.BUILDER.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.is_RUBY())
							tm.setPrefix(ChatColor.RED+""+ChatColor.BOLD+Ranks.RUBY.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.is_ELITE())
							tm.setPrefix(ChatColor.GOLD+""+ChatColor.BOLD+Ranks.ELITE.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.is_SVIP())
							tm.setPrefix(ChatColor.GREEN+""+ChatColor.BOLD+Ranks.SVIP.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.is_VIP())
							tm.setPrefix(ChatColor.AQUA+""+ChatColor.BOLD+Ranks.VIP.name()+" "+jugOnline.getNameTagColor());
						else if(jugOnline.is_Premium())
							tm.setPrefix(ChatColor.YELLOW.toString());
						else
							tm.setPrefix(ChatColor.GRAY.toString());
						
						tm.addPlayer(jugOnline.getBukkitPlayer());
						} catch(Exception ex) {
						ex.printStackTrace();
					}
				}

			} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
    public static void onJoin(Player p, Jugador j) {
    	Jugador jugg = Jugador.getJugador(p);
		jugg.setBukkitPlayer(p);
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		jugg.setBukkitPlayer(p);
		updateScoreboard(p);
		p.setGameMode(GameMode.ADVENTURE);
		//Effects
		
		//Items
		ItemUtils perfil = new ItemUtils(p.getName(), 1, ChatColor.GREEN+"Perfil", ChatColor.GRAY+"Click derecho para abrir tu perfil");
		
		p.getInventory().setItem(0, selector);
		p.getInventory().setItem(2, rangos);
		p.getInventory().setItem(4, stats);
		p.getInventory().setItem(6, vanidad);
		p.getInventory().setItem(8, perfil);
		
		//potions
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
		p.setWalkSpeed(0.3f);
		
		//welcome old
		/*
		p.sendMessage(ChatColor.GOLD+""+ChatColor.STRIKETHROUGH+"= = = = = = = = = = = = = = = = = = = = = = = =");
		p.sendMessage("");
		p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"                      Website: ");
		p.sendMessage("                      "+ChatColor.AQUA+"http://minelc.com");
		p.sendMessage("");
		p.sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"                      Tienda: ");
		p.sendMessage("                 "+ChatColor.AQUA+"http://tienda.minelc.com/");
		p.sendMessage("");
		p.sendMessage(ChatColor.GOLD+""+ChatColor.STRIKETHROUGH+"= = = = = = = = = = = = = = = = = = = = = = = =");
		 */
		
		String username = p.getName();
		p.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"▂▃▄▄▅▅███████▅▅▄▄▃▂");
		p.sendMessage("");
		p.sendMessage(ChatColor.YELLOW+"        Bienvenido "+ChatColor.GOLD+ username +ChatColor.YELLOW+" a");
		p.sendMessage("              "+ChatColor.DARK_AQUA+""+ChatColor.BOLD+"MineLC"+ChatColor.GOLD+""+ChatColor.BOLD+" Network");
		p.sendMessage("");
		p.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+"                  Sitio Web:");
		p.sendMessage("              "+ChatColor.AQUA+"https://www.minelc.com/");
		p.sendMessage("");
		p.sendMessage(ChatColor.BLUE+""+ChatColor.BOLD+"                  Discord:");
		p.sendMessage(ChatColor.BLUE+ "              https://discord.gg/K7JPM8E");
		p.sendMessage("");
		p.sendMessage(ChatColor.WHITE+""+ChatColor.BOLD+" Compra los mejores rangos en:");
		p.sendMessage("           "+ChatColor.AQUA+"https://www.minelc.com/shop");
		p.sendMessage("");
		p.sendMessage(ChatColor.GOLD+""+ChatColor.BOLD+"▂▃▄▄▅▅███████▅▅▄▄▃▂");
		
		if(!j.isOpciones_SVS_Enable_Players()) {
			for(Player Online : Bukkit.getOnlinePlayers()) {
				j.getBukkitPlayer().hidePlayer(Online);
			}
		}
		
		if(j.isOpciones_SVS_Enabled_Minilobby()) {
			p.sendMessage(ChatColor.GREEN+"Mini-Lobby (Rapido) esta activado, puedes desactivarlo desde el menu de servidores!");
		}
	}
    


    private static IconMenu getInvStats_HG_kills() {
        if (invStats_HG_kills == null) {
            invStats_HG_kills = new IconMenu("TOP Asesinatos - HG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_HG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_kills", "SV_HG");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_HG_kills.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " asesinatos"));
            }

            invStats_HG_kills.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_HG_kills;
    }

    private static IconMenu getInvStats_HG_partidas_ganadas() {
        if (invStats_HG_part_ganadas == null) {
            invStats_HG_part_ganadas = new IconMenu("TOP Partidas Ganadas - HG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_HG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_ganadas", "SV_HG");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_HG_part_ganadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas ganadas"));
            }

            invStats_HG_part_ganadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_HG_part_ganadas;
    }

    private static IconMenu getInvStats_HG_partidas_jugadas() {
        if (invStats_HG_part_jugadas == null) {
            invStats_HG_part_jugadas = new IconMenu("TOP Partidas Jugadas - HG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_HG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_jugadas", "SV_HG");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_HG_part_jugadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas jugadas"));
            }

            invStats_HG_part_jugadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_HG_part_jugadas;
    }

    private static IconMenu getInvStats_HG_deaths() {
        if (invStats_HG_deaths == null) {
            invStats_HG_deaths = new IconMenu("TOP Muertes - HG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_HG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_deaths", "SV_HG");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_HG_deaths.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " muertes"));
            }

            invStats_HG_deaths.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_HG_deaths;
    }

    private static IconMenu getInvStats_HG() {
        if (invStats_HG == null) {
            invStats_HG = new IconMenu("TOP Jugadores - HG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                    case 10:
                        LobbyController.getInvStats_HG_kills().open(e.getPlayer());
                        break;
                    case 12:
                        LobbyController.getInvStats_HG_partidas_ganadas().open(e.getPlayer());
                        break;
                    case 14:
                        LobbyController.getInvStats_HG_partidas_jugadas().open(e.getPlayer());
                        break;
                    case 16:
                        LobbyController.getInvStats_HG_deaths().open(e.getPlayer());
                        break;
                    case 31:
                        LobbyController.getInvStats_MAIN().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invStats_HG.setOption(10, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Asesinatos   ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mÃ¡s asesinatos"});
            invStats_HG.setOption(12, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Ganadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mÃ¡s partidas ganadas"});
            invStats_HG.setOption(14, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Jugadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mÃ¡s partidas jugadas"});
            invStats_HG.setOption(16, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Muertes  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mÃ¡s muertes"});
            invStats_HG.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_HG;
    }

    private static IconMenu getInvStats_CHG_kills() {
        if (invStats_CHG_kills == null) {
            invStats_CHG_kills = new IconMenu("TOP Asesinatos - CHG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_CHG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_kills", "SV_CHG");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_CHG_kills.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " asesinatos"));
            }

            invStats_CHG_kills.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_CHG_kills;
    }

    private static IconMenu getInvStats_CHG_partidas_ganadas() {
        if (invStats_CHG_part_ganadas == null) {
            invStats_CHG_part_ganadas = new IconMenu("TOP Partidas Ganadas - CHG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_CHG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_ganadas", "SV_CHG");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_CHG_part_ganadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas ganadas"));
            }

            invStats_CHG_part_ganadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_CHG_part_ganadas;
    }

    private static IconMenu getInvStats_CHG_partidas_jugadas() {
        if (invStats_CHG_part_jugadas == null) {
            invStats_CHG_part_jugadas = new IconMenu("TOP Partidas Jugadas - CHG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_CHG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_jugadas", "SV_CHG");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_CHG_part_jugadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas jugadas"));
            }

            invStats_CHG_part_jugadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_CHG_part_jugadas;
    }

    private static IconMenu getInvStats_CHG_deaths() {
        if (invStats_CHG_deaths == null) {
            invStats_CHG_deaths = new IconMenu("TOP Muertes - CHG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_CHG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_deaths", "SV_CHG");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_CHG_deaths.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " muertes"));
            }

            invStats_CHG_deaths.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_CHG_deaths;
    }

    private static IconMenu getInvStats_CHG() {
        if (invStats_CHG == null) {
            invStats_CHG = new IconMenu("TOP Jugadores - CHG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                    case 10:
                        LobbyController.getInvStats_CHG_kills().open(e.getPlayer());
                        break;
                    case 12:
                        LobbyController.getInvStats_CHG_partidas_ganadas().open(e.getPlayer());
                        break;
                    case 14:
                        LobbyController.getInvStats_CHG_partidas_jugadas().open(e.getPlayer());
                        break;
                    case 16:
                        LobbyController.getInvStats_CHG_deaths().open(e.getPlayer());
                        break;
                    case 31:
                        LobbyController.getInvStats_MAIN().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invStats_CHG.setOption(10, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Asesinatos   ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mÃ¡s asesinatos"});
            invStats_CHG.setOption(12, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Ganadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mÃ¡s partidas ganadas"});
            invStats_CHG.setOption(14, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Jugadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mÃ¡s partidas jugadas"});
            invStats_CHG.setOption(16, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Muertes  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mÃ¡s muertes"});
            invStats_CHG.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_CHG;
    }

    private static IconMenu getInvStats_SW_kills() {
        if (invStats_SW_kills == null) {
            invStats_SW_kills = new IconMenu("TOP Asesinatos - SW", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_SW().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_kills", "SV_SKYWARS");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_SW_kills.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " asesinatos"));
            }

            invStats_SW_kills.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_SW_kills;
    }

    private static IconMenu getInvStats_SW_partidas_ganadas() {
        if (invStats_SW_part_ganadas == null) {
            invStats_SW_part_ganadas = new IconMenu("TOP Partidas Ganadas - SW", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_SW().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_ganadas", "SV_SKYWARS");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_SW_part_ganadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas ganadas"));
            }

            invStats_SW_part_ganadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_SW_part_ganadas;
    }

    private static IconMenu getInvStats_SW_partidas_jugadas() {
        if (invStats_SW_part_jugadas == null) {
            invStats_SW_part_jugadas = new IconMenu("TOP Partidas Jugadas - SW", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_SW().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_jugadas", "SV_SKYWARS");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_SW_part_jugadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas jugadas"));
            }

            invStats_SW_part_jugadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_SW_part_jugadas;
    }

    private static IconMenu getInvStats_SW_deaths() {
        if (invStats_SW_deaths == null) {
            invStats_SW_deaths = new IconMenu("TOP Muertes - SW", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_SW().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_deaths", "SV_SKYWARS");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_SW_deaths.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " muertes"));
            }

            invStats_SW_deaths.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_SW_deaths;
    }

    private static IconMenu getInvStats_SW() {
        if (invStats_SW == null) {
            invStats_SW = new IconMenu("TOP Jugadores - SW", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                    case 10:
                        LobbyController.getInvStats_SW_kills().open(e.getPlayer());
                        break;
                    case 12:
                        LobbyController.getInvStats_SW_partidas_ganadas().open(e.getPlayer());
                        break;
                    case 14:
                        LobbyController.getInvStats_SW_partidas_jugadas().open(e.getPlayer());
                        break;
                    case 16:
                        LobbyController.getInvStats_SW_deaths().open(e.getPlayer());
                        break;
                    case 31:
                        LobbyController.getInvStats_MAIN().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invStats_SW.setOption(10, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Asesinatos   ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas asesinatos"});
            invStats_SW.setOption(12, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Ganadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas partidas ganadas"});
            invStats_SW.setOption(14, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Jugadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas partidas jugadas"});
            invStats_SW.setOption(16, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Muertes  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas muertes"});
            invStats_SW.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_SW;
    }

    private static IconMenu getInvStats_KITPVP_kills() {
        if (invStats_KITPVP_kills == null) {
            invStats_KITPVP_kills = new IconMenu("TOP Asesinatos - KITPVP", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_KITPVP().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_kills", "SV_KITPVP");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_KITPVP_kills.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " asesinatos"));
            }

            invStats_KITPVP_kills.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_KITPVP_kills;
    }

    private static IconMenu getInvStats_KITPVP_deaths() {
        if (invStats_KITPVP_deaths == null) {
            invStats_KITPVP_deaths = new IconMenu("TOP Muertes - KITPVP", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_KITPVP().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_deaths", "SV_KITPVP");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_KITPVP_deaths.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " muertes"));
            }

            invStats_KITPVP_deaths.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_KITPVP_deaths;
    }

    private static IconMenu getInvStats_POTPVP_kills() {
        if (invStats_POTPVP_kills == null) {
            invStats_POTPVP_kills = new IconMenu("TOP Asesinatos - PotPVP", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_POTPVP().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_kills", "SV_POTPVP");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_POTPVP_kills.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " asesinatos"));
            }

            invStats_POTPVP_kills.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_POTPVP_kills;
    }

    private static IconMenu getInvStats_POTPVP_deaths() {
        if (invStats_POTPVP_deaths == null) {
            invStats_POTPVP_deaths = new IconMenu("TOP Muertes - PotPVP", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_POTPVP().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_deaths", "SV_POTPVP");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_POTPVP_deaths.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " muertes"));
            }

            invStats_POTPVP_deaths.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_POTPVP_deaths;
    }

    private static IconMenu getInvStats_POTPVP() {
        if (invStats_POTPVP == null) {
            invStats_POTPVP = new IconMenu("TOP Jugadores - PotPVP", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                    case 11:
                        LobbyController.getInvStats_POTPVP_kills().open(e.getPlayer());
                        break;
                    case 15:
                        LobbyController.getInvStats_POTPVP_deaths().open(e.getPlayer());
                        break;
                    case 31:
                        LobbyController.getInvStats_MAIN().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invStats_POTPVP.setOption(11, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Asesinatos   ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas asesinatos"});
            invStats_POTPVP.setOption(15, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Muertes  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas muertes"});
            invStats_POTPVP.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_POTPVP;
    }

    private static IconMenu getInvStats_KITPVP() {
        if (invStats_KITPVP == null) {
            invStats_KITPVP = new IconMenu("TOP Jugadores - KITPVP", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                    case 11:
                        LobbyController.getInvStats_KITPVP_kills().open(e.getPlayer());
                        break;
                    case 15:
                        LobbyController.getInvStats_KITPVP_deaths().open(e.getPlayer());
                        break;
                    case 31:
                        LobbyController.getInvStats_MAIN().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invStats_KITPVP.setOption(11, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Asesinatos   ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas asesinatos"});
            invStats_KITPVP.setOption(15, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Muertes  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas muertes"});
            invStats_KITPVP.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_KITPVP;
    }

    private static IconMenu getInvStats_PVPG_kills() {
        if (invStats_PVPG_kills == null) {
            invStats_PVPG_kills = new IconMenu("TOP Asesinatos - PVPG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_PVPG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_kills", "SV_PVPGAMES");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_PVPG_kills.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " asesinatos"));
            }

            invStats_PVPG_kills.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_PVPG_kills;
    }

    private static IconMenu getInvStats_PVPG_partidas_ganadas() {
        if (invStats_PVPG_part_ganadas == null) {
            invStats_PVPG_part_ganadas = new IconMenu("TOP Partidas Ganadas - PVPG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_PVPG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_ganadas", "SV_PVPGAMES");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_PVPG_part_ganadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas ganadas"));
            }

            invStats_PVPG_part_ganadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_PVPG_part_ganadas;
    }

    private static IconMenu getInvStats_PVPG_partidas_jugadas() {
        if (invStats_PVPG_part_jugadas == null) {
            invStats_PVPG_part_jugadas = new IconMenu("TOP Partidas Jugadas - PVPG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_PVPG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_jugadas", "SV_PVPGAMES");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_PVPG_part_jugadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas jugadas"));
            }

            invStats_PVPG_part_jugadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_PVPG_part_jugadas;
    }

    private static IconMenu getInvStats_PVPG_deaths() {
        if (invStats_PVPG_deaths == null) {
            invStats_PVPG_deaths = new IconMenu("TOP Muertes - PVPG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_PVPG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_deaths", "SV_PVPGAMES");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_PVPG_deaths.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " muertes"));
            }

            invStats_PVPG_deaths.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_PVPG_deaths;
    }

    private static IconMenu getInvStats_PVPG_Monumentos() {
        if (invStats_PVPG_monumentos == null) {
            invStats_PVPG_monumentos = new IconMenu("TOP Monumentos - PVPG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_PVPG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_monuments_destroyed", "SV_PVPGAMES");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_PVPG_monumentos.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " monumentos"));
            }

            invStats_PVPG_monumentos.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_PVPG_monumentos;
    }

    private static IconMenu getInvStats_PVPG_Nucleos() {
        if (invStats_PVPG_nucleos == null) {
            invStats_PVPG_nucleos = new IconMenu("TOP Nucleos - PVPG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_PVPG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_cores_leakeds", "SV_PVPGAMES");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_PVPG_nucleos.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " Nucleos"));
            }

            invStats_PVPG_nucleos.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_PVPG_nucleos;
    }

    private static IconMenu getInvStats_PVPG_Lanas() {
        if (invStats_PVPG_lanas == null) {
            invStats_PVPG_lanas = new IconMenu("TOP Lanas - PVPG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_PVPG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_wools_placed", "SV_PVPGAMES");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_PVPG_lanas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " Lanas"));
            }

            invStats_PVPG_lanas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_PVPG_lanas;
    }

    private static IconMenu getInvStats_PVPG() {
        if (invStats_PVPG == null) {
            invStats_PVPG = new IconMenu("TOP Jugadores - PVPG", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                    case 10:
                        LobbyController.getInvStats_PVPG_kills().open(e.getPlayer());
                        break;
                    case 11:
                        LobbyController.getInvStats_PVPG_partidas_ganadas().open(e.getPlayer());
                        break;
                    case 12:
                        LobbyController.getInvStats_PVPG_partidas_jugadas().open(e.getPlayer());
                        break;
                    case 13:
                        LobbyController.getInvStats_PVPG_deaths().open(e.getPlayer());
                        break;
                    case 14:
                        LobbyController.getInvStats_PVPG_Monumentos().open(e.getPlayer());
                        break;
                    case 15:
                        LobbyController.getInvStats_PVPG_Nucleos().open(e.getPlayer());
                        break;
                    case 16:
                        LobbyController.getInvStats_PVPG_Lanas().open(e.getPlayer());
                        break;
                    case 31:
                        LobbyController.getInvStats_MAIN().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invStats_PVPG.setOption(10, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Asesinatos   ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas asesinatos"});
            invStats_PVPG.setOption(11, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Ganadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas partidas ganadas"});
            invStats_PVPG.setOption(12, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Jugadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas partidas jugadas"});
            invStats_PVPG.setOption(13, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Muertes  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas muertes"});
            invStats_PVPG.setOption(14, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Monumentos Destruidos  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas monumentos destruidos"});
            invStats_PVPG.setOption(15, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Nucleos Derramados ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas nucleos derramados"});
            invStats_PVPG.setOption(16, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Lanas  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas lanas colocadas"});
            invStats_PVPG.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_PVPG;
    }

    public static IconMenu getInvStats_MAIN() {
        if (invStats_MAIN == null) {
            invStats_MAIN = new IconMenu("TOP Jugadores", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                    case 10:
                        LobbyController.getInvStats_HG().open(e.getPlayer());
                        break;
                    case 12:
                        LobbyController.getInvStats_SW().open(e.getPlayer());
                        break;
                    case 14:
                        LobbyController.getInvStats_PVPG().open(e.getPlayer());
                        break;
                    case 16:
                        LobbyController.getInvStats_KITPVP().open(e.getPlayer());
                        break;
                    case 28:
                        LobbyController.getInvStats_POTPVP().open(e.getPlayer());
                        break;
                    case 30:
                        LobbyController.getInvStats_EW().open(e.getPlayer());
                        break;
                    case 32:
                        LobbyController.getInvStats_CHG().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invStats_MAIN.setOption(12, new ItemStack(Material.FISHING_ROD), "" + ChatColor.GREEN + ChatColor.BOLD + "SkyWars   ", new String[]{ChatColor.GRAY + "Click para abrir el menu"});
            invStats_MAIN.setOption(14, new ItemStack(Material.IRON_CHESTPLATE), "" + ChatColor.GREEN + ChatColor.BOLD + "PVPGames ", new String[]{" ", ChatColor.GRAY + "Click para abrir el menu"});
            invStats_MAIN.setOption(16, new ItemStack(Material.IRON_SWORD), "" + ChatColor.GREEN + ChatColor.BOLD + "KitPVP   ", new String[]{ChatColor.GRAY + "Click para abrir el menu"});
            invStats_MAIN.setOption(28, new ItemStack(Material.POTION, 1, (short)16421), "" + ChatColor.GREEN + ChatColor.BOLD + "PotPVP   ", new String[]{ChatColor.GRAY + "Click para abrir el menu"});
            invStats_MAIN.setOption(30, new ItemStack(Material.DRAGON_EGG), "" + ChatColor.GREEN + ChatColor.BOLD + "EggWars   ", new String[]{ChatColor.GRAY + "Click para abrir el menu"});
            invStats_MAIN.setOption(32, new ItemStack(Material.BOW), "" + ChatColor.GREEN + ChatColor.BOLD + "Hunger Games Clasicos   ", new String[]{ChatColor.GRAY + "Click para abrir el menu"});
        }

        return invStats_MAIN;
    }

    private static IconMenu getInvStats_EW_kills() {
        if (invStats_EW_kills == null) {
            invStats_EW_kills = new IconMenu("TOP Asesinatos - EggWars", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_EW().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_kills", "SV_BEDWARS");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_EW_kills.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " asesinatos"));
            }

            invStats_EW_kills.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_EW_kills;
    }

    private static IconMenu getInvStats_EW_partidas_ganadas() {
        if (invStats_EW_part_ganadas == null) {
            invStats_EW_part_ganadas = new IconMenu("TOP Partidas Ganadas - EggWars", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_EW().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_ganadas", "SV_BEDWARS");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_EW_part_ganadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas ganadas"));
            }

            invStats_EW_part_ganadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_EW_part_ganadas;
    }

    private static IconMenu getInvStats_EW_partidas_jugadas() {
        if (invStats_EW_part_jugadas == null) {
            invStats_EW_part_jugadas = new IconMenu("TOP Partidas Jugadas - EggWars", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_EW().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_partidas_jugadas", "SV_BEDWARS");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_EW_part_jugadas.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " partidas jugadas"));
            }

            invStats_EW_part_jugadas.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_EW_part_jugadas;
    }

    private static IconMenu getInvStats_EW_deaths() {
        if (invStats_EW_deaths == null) {
            invStats_EW_deaths = new IconMenu("TOP Muertes - EggWars", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    if (e.getPosition() == 31) {
                        LobbyController.getInvStats_EW().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            LinkedHashMap<String, Integer> top = Database.getTop(18, "stats_deaths", "SV_BEDWARS");
            int slot = 0;
            Iterator var3 = top.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Integer> es = (Entry)var3.next();
                invStats_EW_deaths.setOption(slot++, new ItemUtils((String)es.getKey(), 1, "" + ChatColor.GOLD + ChatColor.BOLD + "#" + slot + ChatColor.DARK_GRAY + " - " + ChatColor.RED + (String)es.getKey(), "" + ChatColor.GRAY + es.getValue() + " muertes"));
            }

            invStats_EW_deaths.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_EW_deaths;
    }

    private static IconMenu getInvStats_EW() {
        if (invStats_EW == null) {
            invStats_EW = new IconMenu("TOP Jugadores - EggWars", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                    case 10:
                        LobbyController.getInvStats_EW_kills().open(e.getPlayer());
                        break;
                    case 12:
                        LobbyController.getInvStats_EW_partidas_ganadas().open(e.getPlayer());
                        break;
                    case 14:
                        LobbyController.getInvStats_EW_partidas_jugadas().open(e.getPlayer());
                        break;
                    case 16:
                        LobbyController.getInvStats_EW_deaths().open(e.getPlayer());
                        break;
                    case 31:
                        LobbyController.getInvStats_MAIN().open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invStats_EW.setOption(10, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Asesinatos   ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas asesinatos"});
            invStats_EW.setOption(12, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Ganadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas partidas ganadas"});
            invStats_EW.setOption(14, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Partidas Jugadas", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas partidas jugadas"});
            invStats_EW.setOption(16, new ItemStack(Material.SIGN), "" + ChatColor.GREEN + ChatColor.BOLD + "Muertes  ", new String[]{ChatColor.GRAY + "Click para mostrar a los usuarios con", ChatColor.GRAY + "mas muertes"});
            invStats_EW.setOption(31, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invStats_EW;
    }

    public static IconMenu getInvSelector() {
        if (invSelector == null) {
            invSelector = new IconMenu(ChatColor.translateAlternateColorCodes('&', "&6&k!!&a&lServidores&6&k!!"),54, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                   /* case 11:
             puentes  ((MobsController)MobsController.getAll().get("mob6")).openInventory(e.getPlayer());
                        break; */
                    case 0:
    	   /*skywars */ //((MobsController)MobsController.getAll().get("mob5")).openInventory(e.getPlayer());
                    	 Lobby2.sendPlayerToServer(e.getPlayer(), "lobby7", "lobby7");
                        break;
                    case 2:
            /*tsw*/     ((MobsController)MobsController.getAll().get("mob3")).openInventory(e.getPlayer());
                        break;
                    case 4:
            /*eggwars */  //((MobsController)MobsController.getAll().get("mob1")).openInventory(e.getPlayer());
                    	 Lobby2.sendPlayerToServer(e.getPlayer(), "lobby6", "lobby6");
                        break;
                    case 8:
                    	 Lobby2.sendPlayerToServer(e.getPlayer(), "kitpvp", "kitpvp");
          /*kitpvp  ((MobsController)MobsController.getAll().get("mob4")).openInventory(e.getPlayer());*/
                        break;
                    case 6:
                    	 Lobby2.sendPlayerToServer(e.getPlayer(), "practice", "practice");
           /*practice ((MobsController)MobsController.getAll().get("mob4")).openInventory(e.getPlayer());*/
                        break;

                    case 20:
                     Lobby2.sendPlayerToServer(e.getPlayer(), "survival2", "survival2");
        /*survival ((MobsController)MobsController.getAll().get("mob0")).openInventory(e.getPlayer());*/
                        break;
                    case 22:
                    Lobby2.sendPlayerToServer(e.getPlayer(), "creativo", "creativo");
         /*creativo ((MobsController)MobsController.getAll().get("mob0")).openInventory(e.getPlayer());*/
                        break;
                    case 24:
                    	 Lobby2.sendPlayerToServer(e.getPlayer(), "buildteam", "buildteam");
       /*buildteam	  ((MobsController)MobsController.getAll().get("mob0")).openInventory(e.getPlayer());*/
                        break;                 
                    case 27:
          /*pvpgames */   ((MobsController)MobsController.getAll().get("mob7")).openInventory(e.getPlayer());
                        break;
                    case 35:
        /*chg*/      ((MobsController)MobsController.getAll().get("mob8")).openInventory(e.getPlayer());
                        break;
                    case 45:
        /*lobbys*/   ((MobsController)MobsController.getAll().get("lobbies")).openInventory(e.getPlayer());
                        break;
                        
                    case 32:
                    	((MobsController)MobsController.getAll().get("mob11")).openInventory(e.getPlayer());
                    	break;
                    case 47:
                    	redesSociales().open(e.getPlayer());
                    	break;
                    case 53:
                      e.getPlayer().closeInventory();
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fCompra Los mejores rangos y a un bajo"));
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fprecio, obten nuevos kits, coins y más"));
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&ewww.minelc.com/shop"));
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                                        break;
                        
                    case 51:
                        LobbyController.changeMiniLobby(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
        }

        if (PVPGamesIS.getType() == Material.WOOL) {
            PVPGamesIS = new ItemStack(Material.OBSIDIAN);
        } else if (PVPGamesIS.getType() == Material.OBSIDIAN) {
            PVPGamesIS = new ItemStack(Material.IRON_CHESTPLATE);
        } else {
            PVPGamesIS = new ItemStack(Material.WOOL, 1, (short)14);
        }

       // invSelector.setOption(11, new ItemStack(Material.BRICK, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Los Puentes", new String[]{"", ChatColor.GRAY + "Equipos de 1vs1, 2vs2 y 4vs4.", ChatColor.GRAY + "Para ganar debes tirarte en el portal enemigo ", ChatColor.GRAY + "pero cuidado, no olvides proteger el tuyo!", "", ChatColor.BOLD +"" +ChatColor.DARK_RED + "!Modalidad Beta! ","",ChatColor.YELLOW + "Hay " + countPlayersInServer("puentes") + " Jugadores.","", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(0, new ItemStack(Material.FISHING_ROD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aSkywars&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Apareceras en una isla. Para conseguir", ChatColor.WHITE + "la victoria sobrevive y ataca a los", ChatColor.WHITE + "enemigos de las otras islas!",ChatColor.WHITE + "Recibe una recompensa",ChatColor.WHITE + "al cumplir los retos!", "", ChatColor.GOLD + "Hay " +( countPlayersInServer("sw") +  countPlayersInServer("lobby7") )+ " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(2, new ItemStack(Material.ENDER_PEARL, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aTeamSkywars&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Apareceras en una isla junto a tu equipo.", ChatColor.WHITE + "Para conseguir la victoria sobrevive y ataca a los", ChatColor.WHITE + "enemigos de las otras islas!",ChatColor.WHITE + "sé el vencedor!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("tsw") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(4, new ItemStack(Material.DRAGON_EGG, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aEggWars&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Destruye los huevos enemigos para", ChatColor.WHITE + "ganar, pero no olvides proteger el tuyo", ChatColor.WHITE + "para poder reaparecer si mueres!", "", ChatColor.GOLD + "Hay " + (countPlayersInServer("ew") + countPlayersInServer("lobby6") ) + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(6, new ItemStack(Material.ANVIL, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aPractice&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Crea tu kit y demustra tus", ChatColor.WHITE + "habilidades en pvp a travez de", ChatColor.WHITE + "duelos!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("practice") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(8, new ItemStack(Material.IRON_SWORD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aKitPvP&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Selecciona un kit y demustra tus", ChatColor.WHITE + "habilidades asesinando a los demás", ChatColor.WHITE + "jugadores!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("kitpvp") + " jugadores!", "", ChatColor.YELLOW + "Click para ingresar!"});
        invSelector.setOption(20, new ItemStack(Material.PORK, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aSurvival&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Un survival con parcelas, economia ,",ChatColor.WHITE+"Mundo PvP, Tiendas, Eventos y", ChatColor.WHITE + "muchas otras cosas para asegurar tu diversion!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("survival") + " jugadores!", "", ChatColor.YELLOW + "Click para ingresar!"});
        invSelector.setOption(22, new ItemStack(Material.STAINED_GLASS, 1, (short)3), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aCreativo&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Aqui puedes demostrar tus habilidades", ChatColor.WHITE + "como constructor o solo construir por diversión", "", ChatColor.GOLD + "Hay " + countPlayersInServer("creativo") + " jugadores!", "", ChatColor.YELLOW + "Click para ingresar!"});
        invSelector.setOption(24, new ItemStack(Material.EMERALD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aBuildTeam&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"Servidor exclusivo para miembros del BuildTeam", ChatColor.WHITE + "Enterate como pertenecer al equipo en", ChatColor.YELLOW + "www.minelc.com", "", ChatColor.GRAY + "Hay " + countPlayersInServer("buildteam") + " jugadores!", "", ChatColor.YELLOW + "Click para ingresar!"});
        invSelector.setOption(27, PVPGamesIS, "" + ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', "&6&kii&aPvPGames&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Algunos juegos de PVP estrategico", ChatColor.WHITE + "como CTW, DTC, TDM.", "", ChatColor.GOLD + "Hay " + countPlayersInServer("pvpg") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(30, new ItemStack(Material.DIAMOND_SWORD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&c&lKITMAP&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"Disfruta este modo con tus colegas.", ChatColor.WHITE + "Crea tu faction!", ChatColor.WHITE + "No dejes que te raideen!", "", ChatColor.DARK_RED + "Muy Pronto!", ChatColor.YELLOW + ""});
        // invSelector.setOption(30, new ItemStack(Material.BED, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aBedWars&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"Destruye las camas enemigas antes de", ChatColor.WHITE + "que ellos rompan la tuya.", ChatColor.WHITE + "Juega 1vs1 , 2vs2 3vs3, y mas!", "", ChatColor.DARK_RED + "Muy Pronto!", ChatColor.YELLOW + ""});
        invSelector.setOption(32, new ItemStack(Material.MOB_SPAWNER, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aEDLB&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"Ven con amigos a jugar escapa de la bestia!", ChatColor.translateAlternateColorCodes('&', "&5&lBETA!"), ChatColor.translateAlternateColorCodes('&', "&6» &fSkins de bestia"),ChatColor.translateAlternateColorCodes('&', "&6» &fCorredor kits"),ChatColor.translateAlternateColorCodes('&', "&6» &fCheckPoints"), "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(35, new ItemStack(Material.BOW, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aJuegos del Hambre Clasicos&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Juegos del hambre clásicos con kits. Para", ChatColor.WHITE + "conseguir la victoria tienes que", ChatColor.WHITE + "eliminar a los demas jugadores!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("chg") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menú de arenas!"});
        
        
        invSelector.setOption(45, new ItemStack(Material.ENCHANTED_BOOK, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Selector De Lobby", new String[]{"", ChatColor.YELLOW + "Click para abrir el selector de lobby"});
        invSelector.setOption(47, new ItemStack(Material.BEACON, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Rede Sociales", new String[]{"", ChatColor.YELLOW + "Click para ver nuestras rede sociales!"});
        invSelector.setOption(51, new ItemStack(Material.FIREWORK_CHARGE, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "MiniLobby", new String[]{"", ChatColor.YELLOW + "Click para activar o desactivar la minilobby"});
        invSelector.setOption(53, new ItemStack(Material.BLAZE_ROD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aTienda&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"¿Quieres ganar más coins y obtener nuevos kits?", ChatColor.WHITE + "Visita nuestra tienda y compra rangos al mejor precio!!", ChatColor.YELLOW + "www.minelc.com/shop"});
        
        
        //glass
        int i;
		 for ( i=9 ; i<=17 ; i++ ) {
			 invSelector.setOption(i, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});  
		 }
		 
		 int e;
		 for ( e=36 ; e<=44 ; e++ ) {
			 invSelector.setOption(e, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 }
		 invSelector.setOption(1, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(3, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(5, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(7, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(18, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(19, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(21, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(23, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(25, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(26, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(28, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(29, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(31, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(33, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(34, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(46, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(48, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(49, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(50, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(52, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
        
       
        
               return invSelector;
    }

    private static void changeMiniLobby(Player p) {
        Jugador jug = Jugador.getJugador(p);
        
   
        if (jug.isOpciones_SVS_Enabled_Minilobby()) {
            p.sendMessage(ChatColor.GREEN + "Fuiste enviado a el lobby normal!");
            p.teleport(Events.spawn2);

            jug.setOpciones_SVS_Enabled_Minilobby(false);
            Database.savePlayerOpciones_SVS(jug);
        } else {
            p.sendMessage(ChatColor.GREEN + "Fuiste enviado a el mini-lobby!");
            p.teleport(Events.spawn);
            jug.setOpciones_SVS_Enabled_Minilobby(true);
            Database.savePlayerOpciones_SVS(jug);
        }

    }

    public static IconMenu updateInvSelector() {
        if (invSelector == null) {
            invSelector = new IconMenu(ChatColor.translateAlternateColorCodes('&', "&a&lServidores"),54, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                   /* case 11:
             puentes  ((MobsController)MobsController.getAll().get("mob6")).openInventory(e.getPlayer());
                        break; */
                    case 0:
    	   /*skywars */ Lobby2.sendPlayerToServer(e.getPlayer(), "lobby7", "lobby7");
                        break;
                    case 2:
            /*tsw*/     ((MobsController)MobsController.getAll().get("mob3")).openInventory(e.getPlayer());
                        break;
                    case 4:
            /*eggwars */  Lobby2.sendPlayerToServer(e.getPlayer(), "lobby6", "lobby6");
                        break;
                    case 8:
                    	 Lobby2.sendPlayerToServer(e.getPlayer(), "kitpvp", "kitpvp");
          /*kitpvp  ((MobsController)MobsController.getAll().get("mob4")).openInventory(e.getPlayer());*/
                        break;
                    case 6:
                    	 Lobby2.sendPlayerToServer(e.getPlayer(), "practice", "practice");
           /*practice ((MobsController)MobsController.getAll().get("mob4")).openInventory(e.getPlayer());*/
                        break;

                    case 20:
                     Lobby2.sendPlayerToServer(e.getPlayer(), "survival2", "survival2");
        /*survival ((MobsController)MobsController.getAll().get("mob0")).openInventory(e.getPlayer());*/
                        break;
                    case 22:
                    Lobby2.sendPlayerToServer(e.getPlayer(), "creativo", "creativo");
         /*creativo ((MobsController)MobsController.getAll().get("mob0")).openInventory(e.getPlayer());*/
                        break;
                    case 24:
                    	 Lobby2.sendPlayerToServer(e.getPlayer(), "buildteam", "buildteam");
       /*buildteam	  ((MobsController)MobsController.getAll().get("mob0")).openInventory(e.getPlayer());*/
                        break;                 
                    case 27:
          /*pvpgames */   ((MobsController)MobsController.getAll().get("mob7")).openInventory(e.getPlayer());
                        break;
                    case 35:
        /*chg*/      ((MobsController)MobsController.getAll().get("mob8")).openInventory(e.getPlayer());
                        break;
                    case 45:
        /*lobbys*/   ((MobsController)MobsController.getAll().get("lobbies")).openInventory(e.getPlayer());
                        break;
                        
                    case 32:
                    	((MobsController)MobsController.getAll().get("mob11")).openInventory(e.getPlayer());
                    	break;
                    case 47:
                    	redesSociales().open(e.getPlayer());
                    	break;
                    case 53:
                      e.getPlayer().closeInventory();
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fCompra Los mejores rangos y a un bajo"));
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fprecio, obten nuevos kits, coins y más"));
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&ewww.minelc.com/shop"));
                      e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                                        break;
                        
                    case 51:
                        LobbyController.changeMiniLobby(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
        }

        if (PVPGamesIS.getType() == Material.WOOL) {
            PVPGamesIS = new ItemStack(Material.OBSIDIAN);
        } else if (PVPGamesIS.getType() == Material.OBSIDIAN) {
            PVPGamesIS = new ItemStack(Material.IRON_CHESTPLATE);
        } else {
            PVPGamesIS = new ItemStack(Material.WOOL, 1, (short)14);
        }

       // invSelector.setOption(11, new ItemStack(Material.BRICK, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Los Puentes", new String[]{"", ChatColor.GRAY + "Equipos de 1vs1, 2vs2 y 4vs4.", ChatColor.GRAY + "Para ganar debes tirarte en el portal enemigo ", ChatColor.GRAY + "pero cuidado, no olvides proteger el tuyo!", "", ChatColor.BOLD +"" +ChatColor.DARK_RED + "!Modalidad Beta! ","",ChatColor.YELLOW + "Hay " + countPlayersInServer("puentes") + " Jugadores.","", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(0, new ItemStack(Material.FISHING_ROD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aSkywars&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Apareceras en una isla. Para conseguir", ChatColor.WHITE + "la victoria sobrevive y ataca a los", ChatColor.WHITE + "enemigos de las otras islas!",ChatColor.WHITE + "Recibe una recompenza",ChatColor.WHITE + "al cumplir los retos!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("sw") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(2, new ItemStack(Material.ENDER_PEARL, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aTeamSkywars&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Apareceras en una isla junto a tu equipo.", ChatColor.WHITE + "Para conseguir la victoria sobrevive y ataca a los", ChatColor.WHITE + "enemigos de las otras islas!",ChatColor.WHITE + "sé el vencedor!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("tsw") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(4, new ItemStack(Material.DRAGON_EGG, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aEggWars&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Destruye los huevos enemigos para", ChatColor.WHITE + "ganar, pero no olvides proteger el tuyo", ChatColor.WHITE + "para poder reaparecer si mueres!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("ew") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(6, new ItemStack(Material.ANVIL, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aPractice&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Crea tu kit y demustra tus", ChatColor.WHITE + "habilidades en pvp a travez de", ChatColor.WHITE + "duelos!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("practice") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(8, new ItemStack(Material.IRON_SWORD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aKitPvP&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Selecciona un kit y demustra tus", ChatColor.WHITE + "habilidades asesinando a los demás", ChatColor.WHITE + "jugadores!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("kitpvp") + " jugadores!", "", ChatColor.YELLOW + "Click para ingresar!"});
        invSelector.setOption(20, new ItemStack(Material.PORK, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aSurvival&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Un survival con parcelas, economia ,",ChatColor.WHITE+"Mundo PvP, Tiendas, Eventos y", ChatColor.WHITE + "muchas otras cosas para asegurar tu diversion!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("survival") + " jugadores!", "", ChatColor.YELLOW + "Click para ingresar!"});
        invSelector.setOption(22, new ItemStack(Material.STAINED_GLASS, 1, (short)3), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aCreativo&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Aqui puedes demostrar tus habilidades", ChatColor.WHITE + "como constructor o solo construir por diversión", "", ChatColor.GOLD + "Hay " + countPlayersInServer("creativo") + " jugadores!", "", ChatColor.YELLOW + "Click para ingresar!"});
        invSelector.setOption(24, new ItemStack(Material.EMERALD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aBuildTeam&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"Servidor exclusivo para miembros del BuildTeam", ChatColor.WHITE + "Enterate como pertenecer al equipo en", ChatColor.YELLOW + "www.minelc.com", "", ChatColor.GRAY + "Hay " + countPlayersInServer("buildteam") + " jugadores!", "", ChatColor.YELLOW + "Click para ingresar!"});
        invSelector.setOption(27, PVPGamesIS, "" + ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', "&6&kii&aPvPGames&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Algunos juegos de PVP estrategico", ChatColor.WHITE + "como CTW, DTC, TDM.", "", ChatColor.GOLD + "Hay " + countPlayersInServer("pvpg") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invSelector.setOption(30, new ItemStack(Material.DIAMOND_SWORD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&c&lKITMAP&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"Disfruta este modo con tus colegas", ChatColor.WHITE + "Crea tu faction!", ChatColor.WHITE + "No dejes que te raideen!", "", ChatColor.DARK_RED + "Muy Pronto!", ChatColor.YELLOW + ""});
        invSelector.setOption(32, new ItemStack(Material.MOB_SPAWNER, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aEDLB&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"Ven con amigos a jugar escapa de la bestia!", ChatColor.translateAlternateColorCodes('&', "&5&lBETA!."), ChatColor.translateAlternateColorCodes('&', "&6» &fSkins de bestia"),ChatColor.translateAlternateColorCodes('&', "&6» &fCorredor kits"),ChatColor.translateAlternateColorCodes('&', "&6» &fCheckPoints"), "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
       //invSelector.setOption(32, new ItemStack(Material.FIREWORK, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aMiniJuegos&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"Ven con amigos a jugar distintos minijuegos!", ChatColor.translateAlternateColorCodes('&', "&6» &fTNT-TAG"), ChatColor.translateAlternateColorCodes('&', "&6» &fLos Puentes"),ChatColor.translateAlternateColorCodes('&', "&6» &fBuildBattle"),ChatColor.translateAlternateColorCodes('&', "&6» &fZombies Minigames"), "", ChatColor.YELLOW + "Click para abrir el menu!"});
        invSelector.setOption(35, new ItemStack(Material.BOW, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aJuegos del Hambre Clasicos&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Juegos del hambre clásicos con kits. Para", ChatColor.WHITE + "conseguir la victoria tienes que", ChatColor.WHITE + "eliminar a los demas jugadores!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("chg") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menú de arenas!"});
        
        
        invSelector.setOption(45, new ItemStack(Material.ENCHANTED_BOOK, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Selector De Lobby", new String[]{"", ChatColor.YELLOW + "Click para abrir el selector de lobby"});
        invSelector.setOption(47, new ItemStack(Material.BEACON, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Redes Sociales", new String[]{"", ChatColor.YELLOW + "Click para ver nuestras redes sociales!"});
        invSelector.setOption(51, new ItemStack(Material.FIREWORK_CHARGE, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "MiniLobby", new String[]{"", ChatColor.YELLOW + "Click para activar o desactivar la minilobby"});
        invSelector.setOption(53, new ItemStack(Material.BLAZE_ROD, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aTienda&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"),ChatColor.WHITE+"¿Quieres ganar más coins y obtener nuevos kits?", ChatColor.WHITE + "Visita nuestra tienda y compra rangos al mejor precio!!", ChatColor.YELLOW + "www.minelc.com/shop"});
        
        
        //glass
        int i;
		 for ( i=9 ; i<=17 ; i++ ) {
			 invSelector.setOption(i, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});  
		 }
		 
		 int e;
		 for ( e=36 ; e<=44 ; e++ ) {
			 invSelector.setOption(e, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 }
		 invSelector.setOption(1, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(3, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(5, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(7, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(18, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(19, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(21, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(23, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(25, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(26, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(28, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(29, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(31, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(33, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(34, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(46, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(48, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(49, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(50, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
		 invSelector.setOption(52, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});   
        
       
        
               return invSelector;
    }

    public static int countPlayersInServer(String svname) {
        int count = 0;
        Iterator var3 = ServersController.getAllServers().iterator();

        while(var3.hasNext()) {
            ServersController svs = (ServersController)var3.next();

            try {
                if (svs.getName().toLowerCase().startsWith(svname)) {
                    count += svs.getCurrentPlayers();
                }
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

        return count;
    }
    public static int GetPlayersInServer(String svname) {
        int count = 0;
        Iterator var3 = ServersController.getAllServers().iterator();

        while(var3.hasNext()) {
            ServersController svs = (ServersController)var3.next();

            try {
                if (svs.getName().toLowerCase().startsWith(svname)) {
                    count += svs.getCurrentPlayers();
                }
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

        return count;
    }
    public static IconMenu redesSociales() {
        if (invRedes == null) {
        	invRedes = new IconMenu(ChatColor.BOLD + "" + ChatColor.GREEN + "Redes Sociales",54, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                   /* case 11:
             puentes  ((MobsController)MobsController.getAll().get("mob6")).openInventory(e.getPlayer());
                        break; */
                    case 20:
                    	e.getPlayer().closeInventory();
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fComunicate con nosotros vía Facebook"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fEnterate de las novedades, información y publicaciones"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&frelacionadas con el servidor en:"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&Esto estará muy pronto."));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                           
                        break;
                    case 22:
                    	e.getPlayer().closeInventory();
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fSiguenos en nuestro twitter oficial"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fParticipa de sorteos, eventos y"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fenterate de nuevas modalidades"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fsiguenos en @MineLCNetwork"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                                        
                        break;
                    case 24:
                    	  e.getPlayer().closeInventory();
                          e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                          e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fForma parte de nuestro servidor de discord"));
                          e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fpuedes hablar con la comunidad, realizar reportes"));
                          e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fescuchar música y jugar en llamada!!"));
                          e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&ehttps://discord.gg/K7JPM8E"));
                          e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                                          
                        break;
                    case 31:
                    	e.getPlayer().closeInventory();
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fVisita nuestro foro oficial"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fReporta usuarios ilegales, comparte tus ideas"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fe informate de las novedades del servidor en:"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&ewww.minelc.com"));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m----------------------------------"));
                                        
                        break;
                   
                    }

                }
            }, Lobby2.getInstance());
        }
       
       // invSelector.setOption(11, new ItemStack(Material.BRICK, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Los Puentes", new String[]{"", ChatColor.GRAY + "Equipos de 1vs1, 2vs2 y 4vs4.", ChatColor.GRAY + "Para ganar debes tirarte en el portal enemigo ", ChatColor.GRAY + "pero cuidado, no olvides proteger el tuyo!", "", ChatColor.BOLD +"" +ChatColor.DARK_RED + "!Modalidad Beta! ","",ChatColor.YELLOW + "Hay " + countPlayersInServer("puentes") + " Jugadores.","", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        invRedes.setOption(20, facebook,"" + ChatColor.translateAlternateColorCodes('&', "&9&lFacebook"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Siguenos en Facebook :","", ChatColor.GOLD + "www.facebook.com/MinelcServer"});
        invRedes.setOption(22, twitter, "" + ChatColor.translateAlternateColorCodes('&', "&b&lTwitter"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Buscanos en Twitter como:","", ChatColor.GOLD + "@MineLCNetwork"});
        invRedes.setOption(24, discord, "" + ChatColor.translateAlternateColorCodes('&', "&5&lDiscord"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Se parte de nuestro Discord: ","", ChatColor.GOLD + "https://discord.gg/K7JPM8E"});
        invRedes.setOption(31, foro, "" + ChatColor.translateAlternateColorCodes('&', "&8&lForo"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Visita nuestro foro:","",ChatColor.GOLD + "www.minelc.com"   });
       
    
        
        //glass
        int i;
		 for ( i=0 ; i<=54 ; i++ ) {
			if(i>=0 && i<=8 || i>=45 && i<=53 || i == 9|| i == 18|| i == 27|| i == 36 || i == 17 || i == 26 || i == 35 || i == 44) {
				invRedes.setOption(i, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});  
			}
		 }
		 
        
               return invRedes;
    }
    public static IconMenu MiniGame() {
        if (invMinigame == null) {
        	invMinigame = new IconMenu(ChatColor.BOLD + "" + ChatColor.GREEN + "EDLB",54, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    switch(e.getPosition()) {
                   /* case 11:
             puentes  ((MobsController)MobsController.getAll().get("mob6")).openInventory(e.getPlayer());
                        break; */
                    case 20:
    	   /*tnttag */ ((MobsController)MobsController.getAll().get("mob9")).openInventory(e.getPlayer());
                        break;
                    case 22:
            /*puentes*/     ((MobsController)MobsController.getAll().get("mob10")).openInventory(e.getPlayer());
                        break;
                    case 24:
            /*eggwars */ Lobby2.sendPlayerToServer(e.getPlayer(), "buildbattle", "buildbattle");
                        break;
                   
                    }

                }
            }, Lobby2.getInstance());
        }

       // invSelector.setOption(11, new ItemStack(Material.BRICK, 1), "" + ChatColor.GREEN + ChatColor.BOLD + "Los Puentes", new String[]{"", ChatColor.GRAY + "Equipos de 1vs1, 2vs2 y 4vs4.", ChatColor.GRAY + "Para ganar debes tirarte en el portal enemigo ", ChatColor.GRAY + "pero cuidado, no olvides proteger el tuyo!", "", ChatColor.BOLD +"" +ChatColor.DARK_RED + "!Modalidad Beta! ","",ChatColor.YELLOW + "Hay " + countPlayersInServer("puentes") + " Jugadores.","", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        //invMinigame.setOption(20, tnttag, "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aTNT-TAG&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Lucha contra otros jugadores en una arena", ChatColor.WHITE + "evita a los jugadores con tnt, si eres golpeado", ChatColor.WHITE + "deberas pegar a otro sin la tnt para",ChatColor.WHITE + "salvarte antes de que explotes",ChatColor.WHITE + "el ultimo en pie gana!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("tnttag") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        //invMinigame.setOption(22, puentes, "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aPuentes&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Dos equipos luchando por la victoria", ChatColor.WHITE + "deberas cruzar a su isla y tirarte en su portal", ChatColor.WHITE + "para poder anotar un punto!",ChatColor.WHITE + "no olvides de proteger el tuyo!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("puentes") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        //invMinigame.setOption(24, buildbattle, "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aBuildBattle&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.WHITE + "Unete a una partida de construcción", ChatColor.WHITE + "demuestra tus habilidades y tu creatividad", ChatColor.WHITE + "la mejor construccion gana!", "", ChatColor.GOLD + "Hay " + countPlayersInServer("buildbattle") + " jugadores!", "", ChatColor.YELLOW + "Click para abrir el menu de arenas!"});
        //invMinigame.setOption(30, new ItemStack(Material.BARRIER, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aTNT-RUN&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.DARK_RED + "Proximamente."});
        //invMinigame.setOption(32, new ItemStack(Material.BARRIER, 1), "" + ChatColor.translateAlternateColorCodes('&', "&6&kii&aZombies-MiniGame&6&kii"), new String[]{"",ChatColor.translateAlternateColorCodes('&', "&6&m-------------------------------"), ChatColor.DARK_RED + "Proximamente."});
    
        
        //glass
        int i;
		 for ( i=0 ; i<=54 ; i++ ) {
			if(i>=0 && i<=8 || i>=45 && i<=53 || i == 9|| i == 18|| i == 27|| i == 36 || i == 17 || i == 26 || i == 35 || i == 44) {
				invMinigame.setOption(i, Glass, "" + ChatColor.translateAlternateColorCodes('&', "&3&lMineLC &6&lNetwork"), new String[] {"",ChatColor.YELLOW+"www.minelc.com"});  
			}
		 }
		 
        
               return invMinigame;
    }
    
    public static IconMenu getInvRangos(Player p) {
        Jugador j = Jugador.getJugador(p);
        IconMenu invRangos = (new IconMenu(ChatColor.AQUA+"Menu de Rangos", 45, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent e) {
                e.setWillClose(false);
                e.setWillDestroy(true);
                if (e.getPosition() == 11) {
                    LobbyController.getInvRangoVIP(e.getPlayer()).open(e.getPlayer());
                } else if (e.getPosition() == 13) {
                    LobbyController.getInvRangoSVIP(e.getPlayer()).open(e.getPlayer());
                } else if (e.getPosition() == 15) {
                    LobbyController.getInvRangoElite(e.getPlayer()).open(e.getPlayer());
                } else if (e.getPosition() == 22) {
                    LobbyController.getInvRangoRuby(e.getPlayer()).open(e.getPlayer());
                } else {
                    e.setWillClose(true);
                    e.getPlayer().sendMessage(ChatColor.GREEN + "Mas informacion sobre los rangos en " + ChatColor.GOLD + "www.minelc.com/shop");
                }

            }
        }, Lobby2.getInstance(), true)).setOption(11, new ItemStack(Material.IRON_INGOT, 1), "" + ChatColor.AQUA + ChatColor.BOLD + ChatColor.UNDERLINE + "Rango VIP", new String[]{"", "" + ChatColor.GREEN + ChatColor.BOLD + "Beneficios:", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Prefijo " + ChatColor.AQUA + ChatColor.BOLD + "VIP", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Acceso en todos los servidores a todos los", ChatColor.YELLOW + "  articulos que contengan \"solo para VIP\".", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Acceso prioritario al servidor.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Y mas..", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"}).setOption(13, new ItemStack(Material.GOLD_INGOT, 1), "" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + "Rango SVIP", new String[]{"", "" + ChatColor.GREEN + ChatColor.BOLD + "Beneficios:", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Todos los beneficios del rango " + ChatColor.AQUA + ChatColor.BOLD + "VIP.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Prefijo " + ChatColor.GREEN + ChatColor.BOLD + "SVIP.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Acceso en todos los servidores a todos los", ChatColor.YELLOW + "  articulos que contengan \"solo para SVIP\".", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Acceso prioritario al servidor.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Y mas..", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"}).setOption(15, new ItemStack(Material.DIAMOND, 1), "" + ChatColor.GOLD + ChatColor.BOLD + ChatColor.UNDERLINE + "Rango ELITE", new String[]{"", "" + ChatColor.GREEN + ChatColor.BOLD + "Beneficios:", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Todos los beneficios del rango " + ChatColor.GREEN + ChatColor.BOLD + "SVIP.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Prefijo " + ChatColor.GOLD + ChatColor.BOLD + "ELITE.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Acceso en todos los servidores a todos los", ChatColor.YELLOW + "  articulos que contengan \"solo para ELITE\".", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Acceso prioritario al servidor.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Y mas..", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"}).setOption(22, new ItemStack(Material.EMERALD, 1), "" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "Rango RUBY", new String[]{"", "" + ChatColor.GREEN + ChatColor.BOLD + "Beneficios:", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Todos los beneficios del rango " + ChatColor.GOLD + ChatColor.BOLD + "ELITE.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Prefijo " + ChatColor.RED + ChatColor.BOLD + "RUBY.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Acceso en todos los servidores a todos los", ChatColor.YELLOW + "  articulos que contengan \"solo para RUBY\".", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Acceso prioritario al servidor.", ChatColor.DARK_GRAY + "- " + ChatColor.YELLOW + "Y mas..", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        if (j.getRank() != Ranks.VIP && j.getRank() != Ranks.SVIP && j.getRank() != Ranks.ELITE && j.getRank() != Ranks.RUBY) {
            invRangos.setOption(31, new ItemUtils(p.getName(), 1), ChatColor.GREEN + "Informacion Del Rango", new String[]{ChatColor.GRAY + "Rango: " + ChatColor.GOLD + j.getRank().toString(), ChatColor.GRAY + "VIP-Points: " + ChatColor.GOLD + j.getRankpoints()});
        } else {
            invRangos.setOption(31, new ItemUtils(p.getName(), 1), ChatColor.GREEN + "Informacion Del Rango", new String[]{ChatColor.GRAY + "Rango: " + ChatColor.GOLD + j.getRank().name(), ChatColor.GRAY + "DuraciÃ³n: " + ChatColor.GOLD + getDuration(j.getRankduration()), ChatColor.GRAY + "VIP-Points: " + ChatColor.GOLD + j.getRankpoints()});
        }

        return invRangos;
    }

    private static String getDuration(long time) {
        String duracion = "";
        if (time <= 0L) {
            return "";
        } else {
            time = (time - System.currentTimeMillis()) / 1000L / 60L;
            float time2 = (float)time;
            if (time2 > 2880.0F) {
                time2 = time2 / 24.0F / 60.0F;
                duracion = (int)time2 + " dias, ";
                time2 = (time2 - (float)((int)time2)) * 24.0F * 60.0F;
            } else if (time2 > 1440.0F) {
                time2 = time2 / 24.0F / 60.0F;
                duracion = (int)time2 + " dia, ";
                time2 = (time2 - (float)((int)time2)) * 24.0F * 60.0F;
            }

            if (time2 > 120.0F) {
                time2 /= 60.0F;
                duracion = duracion + (int)time2 + " horas y ";
                time2 = (time2 - (float)((int)time2)) * 60.0F;
            } else if (time2 > 60.0F) {
                time2 /= 60.0F;
                duracion = duracion + (int)time2 + " hora y ";
                time2 = (time2 - (float)((int)time2)) * 60.0F;
            } else {
                duracion = duracion.replace(", ", "");
            }

            if (time2 == 1.0F) {
                duracion = duracion + (int)time2 + " minuto";
            } else if (time2 > 1.0F) {
                duracion = duracion + (int)time2 + " minutos";
            } else {
                duracion = duracion.replace(" y ", "");
            }

            return duracion;
        }
    }

    private static IconMenu getInvRangoVIP(Player p) {
        final Jugador j = Jugador.getJugador(p);
        IconMenu invRangoVIP = new IconMenu(ChatColor.AQUA+"Compra Rango VIP", 27, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent e) {
                if (e.getPosition() == 11) {
                    if (j.getRankpoints() >= 150) {
                        j.removeRankpoints(150);
                        if (j.getRank() == Ranks.VIP) {
                            j.addRankduration(604800000L);
                        } else {
                            j.setRank(Ranks.VIP);
                            j.setRankduration(604800000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                } else if (e.getPosition() == 13) {
                    if (j.getRankpoints() >= 300) {
                        j.removeRankpoints(300);
                        if (j.getRank() == Ranks.VIP) {
                            j.addRankduration(1296000000L);
                        } else {
                            j.setRank(Ranks.VIP);
                            j.setRankduration(1296000000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                } else if (e.getPosition() == 15) {
                    if (j.getRankpoints() >= 500) {
                        j.removeRankpoints(500);
                        if (j.getRank() == Ranks.VIP) {
                            j.addRankduration(2592000000L);
                        } else {
                            j.setRank(Ranks.VIP);
                            j.setRankduration(2592000000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                }

                e.setWillDestroy(true);
            }
        }, Lobby2.getInstance(), true);
        if (j.getRankpoints() >= 150) {
            invRangoVIP.setOption(11, new ItemStack(Material.WATCH, 7), "" + ChatColor.AQUA + ChatColor.BOLD + "Rango VIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "7 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "7 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$1.5 EUR Ã³ 150 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(11, new ItemStack(Material.WATCH, 7), "" + ChatColor.AQUA + ChatColor.BOLD + "Rango VIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "7 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "7 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$1.5 EUR Ã³ 150 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        if (j.getRankpoints() >= 300) {
            invRangoVIP.setOption(13, new ItemStack(Material.WATCH, 15), "" + ChatColor.AQUA + ChatColor.BOLD + "Rango VIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "15 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "15 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$3 EUR Ã³ 300 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(13, new ItemStack(Material.WATCH, 15), "" + ChatColor.AQUA + ChatColor.BOLD + "Rango VIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "15 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "15 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$3 EUR Ã³ 300 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        if (j.getRankpoints() >= 500) {
            invRangoVIP.setOption(15, new ItemStack(Material.WATCH, 30), "" + ChatColor.AQUA + ChatColor.BOLD + "Rango VIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "30 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "30 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$5 EUR Ã³ 500 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(15, new ItemStack(Material.WATCH, 30), "" + ChatColor.AQUA + ChatColor.BOLD + "Rango VIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "30 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "30 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$5 EUR Ã³ 500 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        return invRangoVIP;
    }

    private static IconMenu getInvRangoSVIP(Player p) {
        final Jugador j = Jugador.getJugador(p);
        IconMenu invRangoVIP = new IconMenu(ChatColor.GREEN+"Compra Rango SVIP", 27, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent e) {
                if (e.getPosition() == 11) {
                    if (j.getRankpoints() >= 300) {
                        j.removeRankpoints(300);
                        if (j.getRank() == Ranks.SVIP) {
                            j.addRankduration(604800000L);
                        } else {
                            j.setRank(Ranks.SVIP);
                            j.setRankduration(604800000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "www.minelc.com/shop" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                } else if (e.getPosition() == 13) {
                    if (j.getRankpoints() >= 600) {
                        j.removeRankpoints(600);
                        if (j.getRank() == Ranks.SVIP) {
                            j.addRankduration(1296000000L);
                        } else {
                            j.setRank(Ranks.SVIP);
                            j.setRankduration(1296000000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "www.minelc.com/shop" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                } else if (e.getPosition() == 15) {
                    if (j.getRankpoints() >= 1000) {
                        j.removeRankpoints(1000);
                        if (j.getRank() == Ranks.SVIP) {
                            j.addRankduration(2592000000L);
                        } else {
                            j.setRank(Ranks.SVIP);
                            j.setRankduration(2592000000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "www.minelc.com/shop" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                }

                e.setWillDestroy(true);
            }
        }, Lobby2.getInstance(), true);
        if (j.getRankpoints() >= 300) {
            invRangoVIP.setOption(11, new ItemStack(Material.WATCH, 7), "" + ChatColor.GREEN + ChatColor.BOLD + "Rango SVIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "7 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "7 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$3 EUR Ã³ 300 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(11, new ItemStack(Material.WATCH, 7), "" + ChatColor.GREEN + ChatColor.BOLD + "Rango SVIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "7 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "7 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$3 EUR Ã³ 300 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        if (j.getRankpoints() >= 600) {
            invRangoVIP.setOption(13, new ItemStack(Material.WATCH, 15), "" + ChatColor.GREEN + ChatColor.BOLD + "Rango SVIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "15 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "15 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$6 EUR Ã³ 600 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(13, new ItemStack(Material.WATCH, 15), "" + ChatColor.GREEN + ChatColor.BOLD + "Rango SVIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "15 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "15 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$6 EUR Ã³ 600 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        if (j.getRankpoints() >= 1000) {
            invRangoVIP.setOption(15, new ItemStack(Material.WATCH, 30), "" + ChatColor.GREEN + ChatColor.BOLD + "Rango SVIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "30 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "30 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$10 EUR Ã³ 1000 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(15, new ItemStack(Material.WATCH, 30), "" + ChatColor.GREEN + ChatColor.BOLD + "Rango SVIP" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "30 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "30 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$10 EUR Ã³ 1000 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        return invRangoVIP;
    }

    public static IconMenu getInvRangoRuby(Player p) {
        final Jugador j = Jugador.getJugador(p);
        IconMenu invRangoRuby = new IconMenu(ChatColor.RED+"Compra rango Ruby", 27, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent e) {
                if (e.getPosition() == 11) {
                    if (j.getRankpoints() >= 1000) {
                        j.removeRankpoints(1000);
                        if (j.getRank() == Ranks.RUBY) {
                            j.addRankduration(604800000L);
                        } else {
                            j.setRank(Ranks.RUBY);
                            j.setRankduration(604800000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                } else if (e.getPosition() == 13) {
                    if (j.getRankpoints() >= 1700) {
                        j.removeRankpoints(1700);
                        if (j.getRank() == Ranks.RUBY) {
                            j.addRankduration(1296000000L);
                        } else {
                            j.setRank(Ranks.RUBY);
                            j.setRankduration(1296000000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                } else if (e.getPosition() == 15) {
                    if (j.getRankpoints() >= 2300) {
                        j.removeRankpoints(2300);
                        if (j.getRank() == Ranks.RUBY) {
                            j.addRankduration(2592000000L);
                        } else {
                            j.setRank(Ranks.RUBY);
                            j.setRankduration(2592000000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                }

                e.setWillDestroy(true);
            }
        }, Lobby2.getInstance(), true);
        if (j.getRankpoints() >= 1000) {
            invRangoRuby.setOption(11, new ItemStack(Material.WATCH, 7), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango RUBY" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "7 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "7 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "1000 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoRuby.setOption(11, new ItemStack(Material.WATCH, 7), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango RUBY" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "7 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "7 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "1000 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        if (j.getRankpoints() >= 1700) {
            invRangoRuby.setOption(13, new ItemStack(Material.WATCH, 15), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango RUBY" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "15 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "15 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "1700 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoRuby.setOption(13, new ItemStack(Material.WATCH, 15), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango RUBY" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "15 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "15 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "1700 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        if (j.getRankpoints() >= 2300) {
            invRangoRuby.setOption(15, new ItemStack(Material.WATCH, 30), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango RUBY" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "30 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "30 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "2300 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoRuby.setOption(15, new ItemStack(Material.WATCH, 30), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango RUBY" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "30 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "30 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "2300 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        return invRangoRuby;
    }

    private static IconMenu getInvRangoElite(Player p) {
        final Jugador j = Jugador.getJugador(p);
        IconMenu invRangoVIP = new IconMenu(ChatColor.GOLD+"Compra rango Elite", 27, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent e) {
                if (e.getPosition() == 11) {
                    if (j.getRankpoints() >= 450) {
                        j.removeRankpoints(450);
                        if (j.getRank() == Ranks.ELITE) {
                            j.addRankduration(604800000L);
                        } else {
                            j.setRank(Ranks.ELITE);
                            j.setRankduration(604800000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                } else if (e.getPosition() == 13) {
                    if (j.getRankpoints() >= 900) {
                        j.removeRankpoints(900);
                        if (j.getRank() == Ranks.ELITE) {
                            j.addRankduration(1296000000L);
                        } else {
                            j.setRank(Ranks.ELITE);
                            j.setRankduration(1296000000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                } else if (e.getPosition() == 15) {
                    if (j.getRankpoints() >= 1500) {
                        j.removeRankpoints(1500);
                        if (j.getRank() == Ranks.ELITE) {
                            j.addRankduration(2592000000L);
                        } else {
                            j.setRank(Ranks.ELITE);
                            j.setRankduration(2592000000L);
                        }

                        Database.savePlayerRank(j);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Rango comprado con exito!");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_YES, 1.2F, 1.2F);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "No tienes suficientes " + ChatColor.AQUA + "VIP-Points " + ChatColor.RED + "para comprar este rango. Visita " + ChatColor.GOLD + "http://tienda.minelc.com" + ChatColor.RED + " para que puedas obtener este rango.");
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.2F, 1.2F);
                    }
                }

                e.setWillDestroy(true);
            }
        }, Lobby2.getInstance(), true);
        if (j.getRankpoints() >= 450) {
            invRangoVIP.setOption(11, new ItemStack(Material.WATCH, 7), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango ELITE" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "7 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "7 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$4.5 EUR Ã³ 450 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(11, new ItemStack(Material.WATCH, 7), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango ELITE" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "7 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "7 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$4.5 EUR Ã³ 450 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        if (j.getRankpoints() >= 900) {
            invRangoVIP.setOption(13, new ItemStack(Material.WATCH, 15), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango ELITE" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "15 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "15 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$9 EUR Ã³ 900 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(13, new ItemStack(Material.WATCH, 15), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango ELITE" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "15 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "15 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$9 EUR Ã³ 900 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        if (j.getRankpoints() >= 1500) {
            invRangoVIP.setOption(15, new ItemStack(Material.WATCH, 30), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango ELITE" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "30 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "30 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$15 EUR Ã³ 1500 VIP-Points", "", "" + ChatColor.BLUE + ChatColor.BOLD + "Â» Click Para Comprar Â«"});
        } else {
            invRangoVIP.setOption(15, new ItemStack(Material.WATCH, 30), "" + ChatColor.GOLD + ChatColor.BOLD + "Rango ELITE" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + ChatColor.BOLD + "30 Dias", new String[]{"", ChatColor.GREEN + "Duracion: " + ChatColor.GOLD + "30 Dias.", "", ChatColor.GREEN + "Precio: " + ChatColor.GOLD + "$15 EUR Ã³ 1500 VIP-Points", "", ChatColor.RED + "Actualmente tienes " + j.getRankpoints() + " VIP-Points, Visita", ChatColor.GOLD + "http://tienda.minelc.com/" + ChatColor.RED + " para comprar mas!"});
        }

        return invRangoVIP;
    }

    public static IconMenu getInvVanidad(Player player) {
        if (invVanidad == null) {
            invVanidad = (new IconMenu("Vanidad", 45, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    Player player = e.getPlayer();
                    switch(e.getPosition()) {
                    case 30:
                    player.performCommand("wings");
                    case 11:
                    case 13:
                    case 15:
                    case 28:
                    case 32:
                    case 34:
                    default:
                    }
                }
            }, Lobby2.getInstance())).setOption(11, new ItemStack(Material.BEACON, 1), ChatColor.GREEN + "Opciones", new String[]{ChatColor.GRAY + "Abre el menu de opciones."}).setOption(13, new ItemStack(Material.REDSTONE, 1), ChatColor.GREEN + "Particulas", new String[]{ChatColor.GRAY + "Abre el menÃº de particulas."}).setOption(15, new ItemStack(Material.EGG, 1), ChatColor.GREEN + "CosmÃ©ticos", new String[]{ChatColor.GRAY + "Abre el menÃº de cosmÃ©ticos."}).setOption(28, new ItemStack(Material.JUKEBOX, 1, (short)2), ChatColor.GREEN + "MÃºsica", new String[]{ChatColor.GRAY + "Abre el menÃº de mÃºsica."}).setOption(30, new ItemStack(Material.FEATHER), ChatColor.GREEN + "Alas", new String[]{ChatColor.GRAY + "Abre el menÃº de alas."});
        }

        return invVanidad;
    }

    public static IconMenu getInvPerfil(Player p) {
        IconMenu invPerfil = (new IconMenu(ChatColor.GOLD+"Perfil de " + p.getName(), 36, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent e) {
                e.setWillClose(false);
                e.setWillDestroy(true);
                if (e.getPosition() == 11) {
                    LobbyController.getInvStats(e.getPlayer()).open(e.getPlayer());
                } else if (e.getPosition() == 15) {
                    LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                }

            }
        }, Lobby2.getInstance(), true)).setOption(11, new ItemStack(Material.PAPER, 1), ChatColor.GREEN + "Estadisticas", new String[]{ChatColor.GRAY + "Muestra tus estadisticas de cada", ChatColor.GRAY + "juego y un resumen de todas!"}).setOption(15, new ItemStack(Material.REDSTONE_COMPARATOR, 1), ChatColor.GREEN + "Opciones", new String[]{ChatColor.GRAY + "Permite cambiar algunas opciones en ", ChatColor.GRAY + "la lobby."});
        return invPerfil;
    }

    public static IconMenu getInvStats(Player p) {
        Jugador j = Jugador.getJugador(p);
        IconMenu invStats = new IconMenu(ChatColor.GREEN+"Estadisticas de " + p.getName(), 54, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent e) {
                e.setWillClose(false);
                e.setWillDestroy(false);
            }
        }, Lobby2.getInstance(), true);
        updateInvStats(invStats, j);
        return invStats;
    }

    private static void updateInvStats(final IconMenu invStats, final Jugador jug) {
        Bukkit.getScheduler().runTaskAsynchronously(Lobby2.getInstance(), new Runnable() {
            public void run() {
                Database.loadPlayerSV_HG_SYNC(jug);
                invStats.setOption(10, new ItemStack(Material.BOW), "" + ChatColor.GREEN + ChatColor.BOLD + "Hunger Games   ", new String[]{" ", ChatColor.YELLOW + "Victorias" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getHG_Stats_Partidas_ganadas(), ChatColor.YELLOW + "Derrotas" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + (jug.getHG_Stats_Partidas_jugadas() - jug.getHG_Stats_Partidas_ganadas()), ChatColor.YELLOW + "Asesinatos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getHG_Stats_kills(), ChatColor.YELLOW + "Muertes" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getHG_Stats_deaths() + " "});
                Database.loadPlayerSV_SKYWARS_SYNC(jug);
                invStats.setOption(12, new ItemStack(Material.FISHING_ROD), "" + ChatColor.GREEN + ChatColor.BOLD + "SkyWars   ", new String[]{" ", ChatColor.YELLOW + "Victorias" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getSKYW_Stats_Partidas_ganadas(), ChatColor.YELLOW + "Derrotas" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + (jug.getSKYW_Stats_Partidas_jugadas() - jug.getSKYW_Stats_Partidas_ganadas()), ChatColor.YELLOW + "Asesinatos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getSKYW_Stats_kills(), ChatColor.YELLOW + "Muertes" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getSKYW_Stats_deaths() + " "});
                Database.loadPlayerSV_PVPGAMES_SYNC(jug);
                invStats.setOption(14, new ItemStack(Material.IRON_CHESTPLATE), "" + ChatColor.GREEN + ChatColor.BOLD + "PVPGames ", new String[]{" ", ChatColor.YELLOW + "Victorias" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPVPG_Stats_Partidas_ganadas(), ChatColor.YELLOW + "Derrotas" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + (jug.getPVPG_Stats_Partidas_jugadas() - jug.getPVPG_Stats_Partidas_ganadas()), ChatColor.YELLOW + "Asesinatos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPVPG_Stats_kills(), ChatColor.YELLOW + "Muertes" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPVPG_Stats_deaths(), ChatColor.YELLOW + "Nucleos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPVPG_Stats_cores_leakeds(), ChatColor.YELLOW + "Monumentos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPVPG_Stats_monuments_destroyed(), ChatColor.YELLOW + "lanas" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPVPG_Stats_wools_placed() + " "});
                Database.loadPlayerSV_KITPVP_SYNC(jug);
                invStats.setOption(16, new ItemStack(Material.IRON_SWORD), "" + ChatColor.GREEN + ChatColor.BOLD + "KitPVP   ", new String[]{" ", ChatColor.YELLOW + "Asesinatos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getKitPVP_Stats_kills(), ChatColor.YELLOW + "Muertes" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getKitPVP_Stats_deaths() + " "});
                Database.loadPlayerSV_POTPVP_SYNC(jug);
                invStats.setOption(28, new ItemStack(Material.POTION, 1, (short)16421), "" + ChatColor.GREEN + ChatColor.BOLD + "PotPVP   ", new String[]{" ", ChatColor.YELLOW + "Asesinatos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPOTPVP_Stats_kills(), ChatColor.YELLOW + "Muertes" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getPOTPVP_Stats_deaths() + " "});
                Database.loadPlayerSV_BEDWARS_SYNC(jug);
                invStats.setOption(30, new ItemStack(Material.DRAGON_EGG), "" + ChatColor.GREEN + ChatColor.BOLD + "EggWars   ", new String[]{" ", ChatColor.YELLOW + "Victorias" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getBEDW_Stats_Partidas_ganadas(), ChatColor.YELLOW + "Derrotas" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + (jug.getBEDW_Stats_Partidas_jugadas() - jug.getBEDW_Stats_Partidas_ganadas()), ChatColor.YELLOW + "Asesinatos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getBEDW_Stats_kills(), ChatColor.YELLOW + "Muertes" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getBEDW_Stats_deaths() + " "});
                Database.loadPlayerSV_CHG_SYNC(jug);
                invStats.setOption(32, new ItemStack(Material.BOW), "" + ChatColor.GREEN + ChatColor.BOLD + "Hunger Games Clasicos  ", new String[]{" ", ChatColor.YELLOW + "Victorias" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getCHG_Stats_Partidas_ganadas(), ChatColor.YELLOW + "Derrotas" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + (jug.getCHG_Stats_Partidas_jugadas() - jug.getCHG_Stats_Partidas_ganadas()), ChatColor.YELLOW + "Asesinatos" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getCHG_Stats_kills(), ChatColor.YELLOW + "Muertes" + ChatColor.DARK_GRAY + ": " + ChatColor.GRAY + jug.getCHG_Stats_deaths() + " "});
            }
        });
    }

    public static IconMenu getInvSettings(Player p) {
        final Jugador j = Jugador.getJugador(p);
        IconMenu invSettings = new IconMenu(ChatColor.GREEN+"Opciones", 54, new IconMenu.OptionClickEventHandler() {
            public void onOptionClick(IconMenu.OptionClickEvent e) {
                switch(e.getPosition()) {
                case 10:
                case 19:
                    j.setOpciones_SVS_Enable_Players(!j.isOpciones_SVS_Enable_Players());
                    Player Online;
                    Iterator var3;
                    if (j.isOpciones_SVS_Enable_Players()) {
                        var3 = Bukkit.getOnlinePlayers().iterator();

                        while(var3.hasNext()) {
                            Online = (Player)var3.next();
                            e.getPlayer().showPlayer(Online);
                        }
                    } else {
                        var3 = Bukkit.getOnlinePlayers().iterator();

                        while(var3.hasNext()) {
                            Online = (Player)var3.next();
                            e.getPlayer().hidePlayer(Online);
                        }
                    }

                    Database.savePlayerOpciones_SVS(j);
                    break;
                case 12:
                case 21:
                    j.setOpciones_SVS_Enable_Chat(!j.isOpciones_SVS_Enable_Chat());
                    Database.savePlayerOpciones_SVS(j);
                    break;
                case 14:
                case 23:
                    j.setOpciones_SVS_Enable_Stacker(!j.isOpciones_SVS_Enable_Stacker());
                    Database.savePlayerOpciones_SVS(j);
                    break;
                case 24:
                case 33:
					if(Database.isMojanPremium(j.getPlayerName())){
						j.setOnlineMode(!j.isOnlineMode());
						//Database.setPremium(j.getPlayerName());
						Database.savePlayerBungee(j);
						j.getBukkitPlayer().kickPlayer(ChatColor.GREEN + "Cuenta premium activada!");
						e.setWillClose(false);
						e.setWillDestroy(true);
					} else {
						j.getBukkitPlayer().sendMessage(ChatColor.RED + "Tu cuenta no es premium.");
					}
					break;
                case 29:
                case 38:
                    if (j.is_VIP()) {
                        j.setHideRank(!j.isHideRank());
                        Database.savePlayerRank(j);
                    }
                    break;
                case 31:
                case 40:
                    e.setWillClose(false);
                    e.setWillDestroy(true);
                    LobbyController.getinvColors().open(e.getPlayer());
                    return;
                }

                e.setWillClose(false);
                e.setWillDestroy(true);
                LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
            }
        }, Lobby2.getInstance(), true);
        updateInvSettings(invSettings, j);
        return invSettings;
    }

    private static IconMenu getinvColors() {
        if (invColorSelector == null) {
            invColorSelector = new IconMenu("Colores", 54, new IconMenu.OptionClickEventHandler() {
                public void onOptionClick(IconMenu.OptionClickEvent e) {
                    e.setWillClose(false);
                    e.setWillDestroy(false);
                    Jugador jug = Jugador.getJugador(e.getPlayer());
                    switch(e.getPosition()) {
                    case 10:
                        if (jug.is_VIP()) {
                            jug.setNameTagColor(ChatColor.DARK_AQUA);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 11:
                        if (jug.is_VIP()) {
                            jug.setNameTagColor(ChatColor.YELLOW);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 12:
                        if (jug.is_VIP()) {
                            jug.setNameTagColor(ChatColor.GREEN);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 13:
                        if (jug.is_VIP()) {
                            jug.setNameTagColor(ChatColor.LIGHT_PURPLE);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 14:
                        if (jug.is_VIP()) {
                            jug.setNameTagColor(ChatColor.GRAY);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 15:
                        if (jug.is_VIP()) {
                            jug.setNameTagColor(ChatColor.DARK_PURPLE);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 16:
                        if (jug.is_SVIP()) {
                            jug.setNameTagColor(ChatColor.WHITE);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                    case 17:
                    case 18:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    default:
                        break;
                    case 19:
                        if (jug.is_SVIP()) {
                            jug.setNameTagColor(ChatColor.DARK_GREEN);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop mpara ver informacion de rangos!");
                        }
                        break;
                    case 20:
                        if (jug.is_SVIP()) {
                            jug.setNameTagColor(ChatColor.RED);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 21:
                        if (jug.is_SVIP()) {
                            jug.setNameTagColor(ChatColor.DARK_GRAY);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 22:
                        if (jug.is_SVIP()) {
                            jug.setNameTagColor(ChatColor.GOLD);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 23:
                        if (jug.is_ELITE()) {
                            jug.setNameTagColor(ChatColor.AQUA);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 24:
                        if (jug.is_ELITE()) {
                            jug.setNameTagColor(ChatColor.BLUE);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                        break;
                    case 25:
                        if (jug.is_ELITE()) {
                            jug.setNameTagColor(ChatColor.DARK_RED);
                            Database.savePlayerRank(jug);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Tu color de nombre fue cambiado correctamente!");
                            LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                        } else {
                            e.getPlayer().sendMessage(ChatColor.RED + "No tienes rango suficiente para elegir color, visita: www.minelc.com/shop para ver informacion de rangos!");
                        }
                    case 40:
                        LobbyController.getInvSettings(e.getPlayer()).open(e.getPlayer());
                    }

                }
            }, Lobby2.getInstance());
            invColorSelector.setOption(10, new ItemStack(Material.WOOL, 1, (short)3), ChatColor.DARK_AQUA + "Cian oscuro", new String[]{ChatColor.GRAY + "Requiere rango VIP o superior."});
            invColorSelector.setOption(11, new ItemStack(Material.WOOL, 1, (short)4), ChatColor.YELLOW + "Amarillo", new String[]{ChatColor.GRAY + "Requiere rango VIP o superior."});
            invColorSelector.setOption(12, new ItemStack(Material.WOOL, 1, (short)5), ChatColor.GREEN + "Lima", new String[]{ChatColor.GRAY + "Requiere rango VIP o superior."});
            invColorSelector.setOption(13, new ItemStack(Material.WOOL, 1, (short)6), ChatColor.LIGHT_PURPLE + "Rosa", new String[]{ChatColor.GRAY + "Requiere rango VIP o superior."});
            invColorSelector.setOption(14, new ItemStack(Material.WOOL, 1, (short)8), ChatColor.GRAY + "Gris", new String[]{ChatColor.GRAY + "Requiere rango VIP o superior."});
            invColorSelector.setOption(15, new ItemStack(Material.WOOL, 1, (short)10), ChatColor.DARK_PURPLE + "Purpura", new String[]{ChatColor.GRAY + "Requiere rango VIP o superior."});
            invColorSelector.setOption(16, new ItemStack(Material.WOOL, 1, (short)0), ChatColor.WHITE + "Blanco", new String[]{ChatColor.GRAY + "Requiere rango SVIP o superior."});
            invColorSelector.setOption(19, new ItemStack(Material.WOOL, 1, (short)13), ChatColor.DARK_GREEN + "Verde", new String[]{ChatColor.GRAY + "Requiere rango SVIP o superior."});
            invColorSelector.setOption(20, new ItemStack(Material.WOOL, 1, (short)14), ChatColor.RED + "Rojo", new String[]{ChatColor.GRAY + "Requiere rango SVIP o superior."});
            invColorSelector.setOption(21, new ItemStack(Material.WOOL, 1, (short)7), ChatColor.DARK_GRAY + "Gris Oscuro", new String[]{ChatColor.GRAY + "Requiere rango SVIP o superior."});
            invColorSelector.setOption(22, new ItemStack(Material.WOOL, 1, (short)1), ChatColor.GOLD + "Dorado", new String[]{ChatColor.GRAY + "Requiere rango SVIP o superior."});
            invColorSelector.setOption(23, new ItemStack(Material.WOOL, 1, (short)9), ChatColor.AQUA + "Cian", new String[]{ChatColor.GRAY + "Requiere rango Elite."});
            invColorSelector.setOption(24, new ItemStack(Material.WOOL, 1, (short)11), ChatColor.BLUE + "Azul", new String[]{ChatColor.GRAY + "Requiere rango Elite."});
            invColorSelector.setOption(25, new ItemStack(Material.REDSTONE_BLOCK, 1), ChatColor.DARK_RED + "Rojo oscuro", new String[]{ChatColor.GRAY + "Requiere rango Elite."});
            invColorSelector.setOption(40, new ItemStack(Material.MAP), "" + ChatColor.GRAY + ChatColor.BOLD + "Regresar", new String[0]);
        }

        return invColorSelector;
    }

    private static void updateInvSettings(IconMenu invPerfil, Jugador j) {
        if (j.isOpciones_SVS_Enable_Players()) {
            invPerfil.setOption(10, new ItemStack(Material.WATCH), ChatColor.GREEN + "Visibilidad De Jugadores", new String[]{ChatColor.GRAY + "Permite ocultar o mostrar", ChatColor.GRAY + "a los jugadores conectados"});
            invPerfil.setOption(19, new ItemStack(Material.INK_SACK, 1, (short)10), ChatColor.GREEN + "Activado", new String[]{ChatColor.GRAY + "Click para desactivar"});
        } else {
            invPerfil.setOption(10, new ItemStack(Material.WATCH), ChatColor.RED + "Visibilidad De Jugadores", new String[]{ChatColor.GRAY + "Permite ocultar o mostrar", ChatColor.GRAY + "a los jugadores conectados"});
            invPerfil.setOption(19, new ItemStack(Material.INK_SACK, 1, (short)8), ChatColor.RED + "Desactivado", new String[]{ChatColor.GRAY + "Click para activar"});
        }

        if (j.isOpciones_SVS_Enable_Chat()) {
            invPerfil.setOption(12, new ItemStack(Material.PAPER), ChatColor.GREEN + "Chat De Jugadores", new String[]{ChatColor.GRAY + "Permite ocultar o mostrar", ChatColor.GRAY + "a el chat de jugadores"});
            invPerfil.setOption(21, new ItemStack(Material.INK_SACK, 1, (short)10), ChatColor.GREEN + "Activado", new String[]{ChatColor.GRAY + "Click para desactivar"});
        } else {
            invPerfil.setOption(12, new ItemStack(Material.PAPER), ChatColor.RED + "Chat De Jugadores", new String[]{ChatColor.GRAY + "Permite ocultar o mostrar", ChatColor.GRAY + "a el chat de jugadores"});
            invPerfil.setOption(21, new ItemStack(Material.INK_SACK, 1, (short)8), ChatColor.RED + "Desactivado", new String[]{ChatColor.GRAY + "Click para activar"});
        }

        if (j.isOpciones_SVS_Enable_Stacker()) {
            invPerfil.setOption(14, new ItemStack(Material.SADDLE), ChatColor.GREEN + "Apilador De Jugadores", new String[]{ChatColor.GRAY + "Permite apilar a los jugadores"});
            invPerfil.setOption(23, new ItemStack(Material.INK_SACK, 1, (short)10), ChatColor.GREEN + "Activado", new String[]{ChatColor.GRAY + "Click para desactivar"});
        } else {
            invPerfil.setOption(14, new ItemStack(Material.SADDLE), ChatColor.RED + "Apilador De Jugadores", new String[]{ChatColor.GRAY + "Permite apilar a los jugadores"});
            invPerfil.setOption(23, new ItemStack(Material.INK_SACK, 1, (short)8), ChatColor.RED + "Desactivado", new String[]{ChatColor.GRAY + "Click para activar"});
        }

        if (j.isOnlineMode()) {
            invPerfil.setOption(24, new ItemUtils(j.getBukkitPlayer().getName(), 1), ChatColor.GREEN + "Cuenta Premium", new String[]{ChatColor.GRAY + "Esta opcion te pertmitira acceder al", ChatColor.GRAY + "servidor sin ingresar tu contraseÃ±a", "", ChatColor.DARK_RED + "Atencion: " + ChatColor.RED + "Esta opcion solo funciona en", ChatColor.RED + "usuarios premium (Minecraft Comprado)", ChatColor.RED + "si no eres premium te quedaras sin", ChatColor.RED + "acceso al servidor!"});
            invPerfil.setOption(33, new ItemStack(Material.INK_SACK, 1, (short)10), ChatColor.GREEN + "Activado", new String[]{ChatColor.GRAY + "Click para desactivar"});
        } else {
            invPerfil.setOption(24, new ItemUtils(j.getBukkitPlayer().getName(), 1), ChatColor.RED + "Cuenta Premium", new String[]{ChatColor.GRAY + "Esta opcion te pertmitira acceder al", ChatColor.GRAY + "servidor sin ingresar tu contraseÃ±a", "", ChatColor.DARK_RED + "Atencion: " + ChatColor.RED + "Esta opcion solo funciona en", ChatColor.RED + "usuarios premium (Minecraft Comprado)", ChatColor.RED + "si no eres premium te quedaras sin", ChatColor.RED + "acceso al servidor!"});
            invPerfil.setOption(33, new ItemStack(Material.INK_SACK, 1, (short)8), ChatColor.RED + "Desactivado", new String[]{ChatColor.GRAY + "Click para activar"});
        }

        if (!j.isHideRank()) {
            invPerfil.setOption(20, new ItemUtils(Material.ENDER_CHEST, 1), ChatColor.GREEN + "Visibilidad De Rango", new String[]{ChatColor.GRAY + "Permite ocultar o mostrar", ChatColor.GRAY + "tu rango"});
            invPerfil.setOption(29, new ItemStack(Material.INK_SACK, 1, (short)10), ChatColor.GREEN + "Activado", new String[]{ChatColor.GRAY + "Click para desactivar"});
        } else {
            invPerfil.setOption(20, new ItemUtils(Material.ENDER_CHEST, 1), ChatColor.RED + "Visibilidad De Rango", new String[]{ChatColor.GRAY + "Permite ocultar o mostrar", ChatColor.GRAY + "tu rango"});
            invPerfil.setOption(29, new ItemStack(Material.INK_SACK, 1, (short)8), ChatColor.RED + "Desactivado", new String[]{ChatColor.GRAY + "Click para activar"});
        }

        invPerfil.setOption(22, new ItemUtils(Material.WOOL, 1), ChatColor.RED + "Color de nombre", new String[]{ChatColor.GRAY + "Selecciona el color de tu nombre"});
        invPerfil.setOption(31, new ItemStack(Material.INK_SACK, 1, (short)10), ChatColor.RED + "Desactivado", new String[]{ChatColor.GRAY + "Click para activar"});
    }
}
