package lc.lobby2.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import lc.core2.utils.IconMenu;
import lc.lobby2.Lobby2;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Colorable;

public class MobsController
{
  private static HashMap<String, MobsController> Mob = new HashMap();
  private String name;
  private Location loc;
  private int InvSize;
  private String InvTitle;
  private IconMenu inv;
  private IconMenu invnj;
  private IconMenu inv2_0;
  public boolean customStyle = false;
  private LivingEntity ent;
  private static HashMap<LivingEntity, MobsController> Ents = new HashMap();
  private ConfigurationSection config;
  private ConfigurationSection configServers;
  private String entity;
  private String MobID;
  private LinkedHashMap<Integer, ItemStack> slots = new LinkedHashMap();
  private LinkedHashMap<ItemStack, Integer> SPlayers = new LinkedHashMap();
  private LinkedHashMap<Integer, ItemStack> slotsNJ = new LinkedHashMap();
  private Set<ConfigurationSection> confSect = Sets.newHashSet();
  private static HashMap<Entity, Location> locs = new HashMap();
  private LinkedHashMap<Integer, ConfigurationSection> ItemsSlots = new LinkedHashMap();
  private LinkedHashMap<Integer, ConfigurationSection> ItemsSlotsNJ = new LinkedHashMap();
  private String configName = "";
  
  public static MobsController getMob(String s) {
    String ss = s.toLowerCase();
    return Mob.containsKey(ss) ? (MobsController)Mob.get(ss) : new MobsController(s);
  }
  
  public MobsController(String s) {
    if (s.equalsIgnoreCase("lobbies")) {
      this.config = ConfigController.getInstance().getMobsConfig().getConfigurationSection(s);
      this.configName = s;
      this.configServers = this.config.getConfigurationSection("Servers");
      this.InvSize = InvSize(Integer.valueOf(this.config.getInt("InventorySize")));
      this.InvTitle = Colorize(this.config.getString("InventoryTitle"));
      this.customStyle = this.config.getBoolean("CustomStyle");
      Mob.put(s.toLowerCase(), this);
      createInventory();
    } else {
      this.config = ConfigController.getInstance().getMobsConfig().getConfigurationSection(s);
      this.configName = s;
      this.configServers = this.config.getConfigurationSection("Servers");
      this.name = Colorize(this.config.getString("name"));
      this.loc = getLocation(this.config.getString("location"));
      this.entity = this.config.getString("mobtype");
      this.InvSize = InvSize(Integer.valueOf(this.config.getInt("InventorySize")));
      this.InvTitle = Colorize(this.config.getString("InventoryTitle"));
      this.customStyle = this.config.getBoolean("CustomStyle");
      this.MobID = this.config.getString("MobID");
      Mob.put(s.toLowerCase(), this);
      createInventory();
     SpawnMob();
    } 
  }


  
  public String getName() { return this.name; }


  
  public String getConfigName() { return this.configName; }

  
  public void SpawnMob() {
    if (!checkID()) {
      if (!this.loc.getChunk().isLoaded()) {
        this.loc.getChunk().load();
      }
      
      this.ent = (LivingEntity)getEntity(this.loc, this.entity);
      this.ent.setCanPickupItems(false);
      this.ent.setCustomName(this.name);
      this.ent.setCustomNameVisible(true);
      this.ent.setRemoveWhenFarAway(false);
      this.config.set("MobID", this.ent.getUniqueId().toString());
      ConfigController.getInstance().saveMobsConfig();
    } 
    
    Bukkit.getScheduler().runTaskLater(Lobby2.getInstance(), new Runnable() {
          public void run() {
           MobsController.this.noAI(MobsController.this.ent);
            MobsController.this.ent.setCustomNameVisible(true);
          }
        },  12L);
    Ents.put(this.ent, this);
    locs.put(this.ent, this.loc);
    setContents();
  } 
  
  public void noAI(LivingEntity bukkitEntity) {
    EntityLiving nmsEntity = ((CraftLivingEntity)bukkitEntity).getHandle();
    NBTTagCompound tag = new NBTTagCompound();
    nmsEntity.c(tag);
    tag.setInt("NoAI", 1);
    tag.setInt("Silent", 1);
    tag.setInt("Invulnerable", 1);
    tag.setInt("CustomNameVisible", 1);
    nmsEntity.f(tag);
  } 

  
  public String getContents() { return this.config.isSet("Contents") ? this.config.getString("Contents") : "0:true,0:true,0:true,0:true,0:true"; }

  
  private void setContents() {
    if (this.config.isSet("Contents")) {
      String[] Armor = this.config.getString("Contents").split(",");
      String Sword = Armor[0];
      int SwordID = Integer.valueOf(Sword.split(":")[0]).intValue();
      boolean SwordEnchant = Boolean.valueOf(Sword.split(":")[1]).booleanValue();
      String hat = Armor[1];
      int hatID = Integer.valueOf(hat.split(":")[0]).intValue();
      boolean hatEnchant = Boolean.valueOf(hat.split(":")[1]).booleanValue();
      String Pech = Armor[2];
      int pechID = Integer.valueOf(Pech.split(":")[0]).intValue();
      boolean pechEnchant = Boolean.valueOf(Pech.split(":")[1]).booleanValue();
      String Pant = Armor[3];
      int pantID = Integer.valueOf(Pant.split(":")[0]).intValue();
      boolean pantEnchant = Boolean.valueOf(Pant.split(":")[1]).booleanValue();
      String Boots = Armor[4];
      int BootsID = Integer.valueOf(Boots.split(":")[0]).intValue();
      boolean BootsEnchant = Boolean.valueOf(Boots.split(":")[1]).booleanValue();
      
      if (SwordID != 0) {
        ItemStack h = getitem(SwordID, 0, 1, "", getLore(""));
        if (SwordEnchant) {
          addEnchantmentGlow(h);
        }
        
        this.ent.getEquipment().setItemInHand(h);
      } 
      
      if (hatID != 0) {
        ItemStack h = getitem(hatID, 0, 1, "", getLore(""));
        if (hatEnchant) {
          addEnchantmentGlow(h);
        }
        
        this.ent.getEquipment().setHelmet(h);
      } 
      
      if (pechID != 0) {
        ItemStack h = getitem(pechID, 0, 1, "", getLore(""));
        if (pechEnchant) {
          addEnchantmentGlow(h);
        }
        
        this.ent.getEquipment().setChestplate(h);
      } 
      
      if (pantID != 0) {
        ItemStack h = getitem(pantID, 0, 1, "", getLore(""));
        if (pantEnchant) {
          addEnchantmentGlow(h);
        }
        
        this.ent.getEquipment().setLeggings(h);
      } 
      
      if (BootsID != 0) {
        ItemStack h = getitem(BootsID, 0, 1, "", getLore(""));
        if (BootsEnchant) {
          addEnchantmentGlow(h);
        }
        
        this.ent.getEquipment().setBoots(h);
      } 
    } 
  }

  
  private boolean checkID() {
    boolean ret = false;
    Iterator var3 = this.loc.getWorld().getLivingEntities().iterator();
    
    while (var3.hasNext()) {
      LivingEntity le = (LivingEntity)var3.next();
      if (le.getUniqueId().toString().equalsIgnoreCase(this.MobID)) {
        if (!le.isDead()) {
          this.ent = le;
          ret = true;
        } 
        
        break;
      } 
    } 
    return ret;
  }

  
  public LivingEntity getMob() { return this.ent; }


  
  public Location getLocation() { return this.loc; }


  
  public String getMobType() { return this.entity; }


  
  public int getInventorySize() { return this.InvSize; }


  
  public String getInventoryTitle() { return this.InvTitle; }


  
  public Set<String> getServers() { return this.configServers.getKeys(false); }

  public void createInventory() {
      if (this.isCustom()) {
          this.inv = new IconMenu(this.getInventoryTitle(), this.getInventorySize(), e -> {
              e.setWillDestroy(false);
              e.setWillClose(false);
              MobsController.this.sendToServer(e.getPosition(), e.getPlayer());
          }, Lobby2.getInstance());
      } else {
          this.invnj = new IconMenu(this.getInventoryTitle(), 54, e -> {
              e.setWillDestroy(false);
              e.setWillClose(false);
              if (e.getPosition() == 4) {
                  MobsController.this.openInventory(e.getPlayer());
              } else {
                  MobsController.this.sendToServerNJ(e.getPosition(), e.getPlayer());
              }

          }, Lobby2.getInstance());
          this.inv2_0 = new IconMenu(this.getInventoryTitle(), 54, e -> {
              e.setWillDestroy(false);
              e.setWillClose(false);
              if (e.getPosition() == 40) {
                  MobsController.this.openGamesInProgress(e.getPlayer());
              } else {
                  MobsController.this.sendToServer(e.getPosition(), e.getPlayer());
              }

          }, Lobby2.getInstance());
      }

      ConfigurationSection c = null;
      Iterator var3 = this.getServers().iterator();

      while(var3.hasNext()) {
          String items = (String)var3.next();
          c = this.configServers.getConfigurationSection(items);
          this.ItemsSlots.put(c.getInt("slot"), c);
          this.confSect.add(c);
      }

  }

  
  public void openInventory(Player p) {
    if (isCustom()) {
      this.inv.open(p);
    } else {
      this.inv2_0.open(p);
    } 
  }


  
  public void openGamesInProgress(Player p) { this.invnj.open(p); }

  
  public void updateInventory() {
    ConfigurationSection c = null;
    Iterator var3 = this.confSect.iterator();
    
    while (var3.hasNext()) {
      ConfigurationSection it = (ConfigurationSection)var3.next();
      
      try {
        ServersController server = ServersController.getServer(it.getName());




        
        if (server.isOnline()) {
          if ((!it.getBoolean("ItemNotJoinableIsFull") || server.getCurrentPlayers() < server.getMaxPlayers()) && !checkMotdContain(server.getMotd(), it.getString("ItemNotJoinableIfMotdContains"))) {
            ItemStack item = getitem(it.getString("ItemOnlineID"), it.getInt("ItemOnlineAmmount"), it.getString("ItemName"), it.getStringList("ItemOnlineLore"));
            ArrayList lore = Lists.newArrayList();
            Iterator var8 = item.getItemMeta().getLore().iterator();
            
            while (var8.hasNext()) {
              String s = (String)var8.next();
              lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
            } 
            
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
            im.setLore(getLore(lore));
            item.setItemMeta(im);
            if (it.getBoolean("ItemOnlineGlow")) {
              addEnchantmentGlow(item);
            }
            
            this.inv.setOption(it.getInt("slot"), item); continue;
          } 
          ItemStack item = getitem(it.getString("ItemNotJoinableID"), it.getInt("ItemNotJoinableAmmount"), it.getString("ItemName"), it.getStringList("ItemNotJoinableLore"));
          ArrayList lore = Lists.newArrayList();
          Iterator var8 = item.getItemMeta().getLore().iterator();
          
          while (var8.hasNext()) {
            String s = (String)var8.next();
            lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
          } 
          
          ItemMeta im = item.getItemMeta();
          im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
          im.setLore(getLore(lore));
          item.setItemMeta(im);
          if (it.getBoolean("ItemNotJoinableGlow")) {
            addEnchantmentGlow(item);
          }
          
          this.inv.setOption(it.getInt("slot"), item);
          continue;
        } 
        ItemStack item = getitem(it.getString("ItemOfflineID"), it.getInt("ItemOfflineAmmount"), it.getString("ItemName"), it.getStringList("ItemOfflineLore"));
        ArrayList lore = Lists.newArrayList();
        Iterator var8 = item.getItemMeta().getLore().iterator();
        
        while (var8.hasNext()) {
          String s = (String)var8.next();
          lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
        } 
        
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
        im.setLore(getLore(lore));
        item.setItemMeta(im);
        if (it.getBoolean("ItemOfflineGlow")) {
          addEnchantmentGlow(item);
        }
        
        this.inv.setOption(it.getInt("slot"), item);
      }
      catch (Exception var9) {
        var9.printStackTrace();
      } 
    } 
  }

  
  public void updateInventory2_0() {
    ConfigurationSection c = null;
    this.slots.clear();
    this.slotsNJ.clear();
    this.SPlayers.clear();
    this.ItemsSlots.clear();
    this.ItemsSlotsNJ.clear();
    Iterator var3 = this.confSect.iterator();
    
    while (var3.hasNext()) {
      ConfigurationSection it = (ConfigurationSection)var3.next();
      
      try {
        ServersController server = ServersController.getServer(it.getName());




        
        if (server.isOnline()) {
          if ((!it.getBoolean("ItemNotJoinableIsFull") || server.getCurrentPlayers() < server.getMaxPlayers()) && !checkMotdContain(server.getMotd(), it.getString("ItemNotJoinableIfMotdContains"))) {
            ItemStack item = getitem(it.getString("ItemOnlineID"), it.getInt("ItemOnlineAmmount"), it.getString("ItemName"), it.getStringList("ItemOnlineLore"));
            ArrayList lore = Lists.newArrayList();
            Iterator var8 = item.getItemMeta().getLore().iterator();
            
            while (var8.hasNext()) {
              String s = (String)var8.next();
              lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
            } 
            
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
            im.setLore(getLore(lore));
            item.setItemMeta(im);
            item.setAmount(server.getCurrentPlayers());
            if (it.getBoolean("ItemOnlineGlow")) {
              addEnchantmentGlow(item);
            }
            
            addSlot(item, it, server.getCurrentPlayers()); continue;
          } 
          ItemStack item = getitem(it.getString("ItemNotJoinableID"), it.getInt("ItemNotJoinableAmmount"), it.getString("ItemName"), it.getStringList("ItemNotJoinableLore"));
          ArrayList lore = Lists.newArrayList();
          Iterator var8 = item.getItemMeta().getLore().iterator();
          
          while (var8.hasNext()) {
            String s = (String)var8.next();
            lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
          } 
          
          ItemMeta im = item.getItemMeta();
          im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
          im.setLore(getLore(lore));
          item.setItemMeta(im);
          item.setAmount(server.getCurrentPlayers());
          if (it.getBoolean("ItemNotJoinableGlow")) {
            addEnchantmentGlow(item);
          }
          
          addSlotNJ(item, 9, it, server.getCurrentPlayers());
          continue;
        } 
        ItemStack item = getitem(it.getString("ItemOfflineID"), it.getInt("ItemOfflineAmmount"), it.getString("ItemName"), it.getStringList("ItemOfflineLore"));
        ArrayList lore = Lists.newArrayList();
        Iterator var8 = item.getItemMeta().getLore().iterator();
        
        while (var8.hasNext()) {
          String s = (String)var8.next();
          lore.add(s.replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
        } 
        
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(im.getDisplayName().replace("<MaxPlayers>", String.valueOf(server.getMaxPlayers())).replace("<CurrentPlayers>", String.valueOf(server.getCurrentPlayers())).replace("<Motd>", server.getMotd()));
        im.setLore(getLore(lore));
        item.setItemMeta(im);
        item.setAmount(server.getCurrentPlayers());
        if (it.getBoolean("ItemOfflineGlow")) {
          addEnchantmentGlow(item);
        }
        
        addSlotNJ(item, 9, it, server.getCurrentPlayers());
      }
      catch (Exception var9) {
        var9.printStackTrace();
      } 
    } 
  }

  
  public void orderSlots() {
    LinkedHashMap<Integer, ItemStack> NEWslots = (LinkedHashMap)this.slots.clone();
    LinkedHashMap<Integer, ConfigurationSection> NEWItemsSlots = (LinkedHashMap)this.ItemsSlots.clone();
    LinkedHashMap<ItemStack, Integer> NEWSPlayers = (LinkedHashMap)this.SPlayers.clone();
    this.slots.clear();
    this.ItemsSlots.clear();
    this.SPlayers.clear();
    int added = 0;
    int slot = 0;
    int HighPlayers = 0;
    ConfigurationSection c = null;
    
    for (ItemStack is = null; !NEWslots.isEmpty(); slot = 0) {
      Iterator var10 = NEWslots.entrySet().iterator();
      
      while (var10.hasNext()) {
        Entry<Integer, ItemStack> sl = (Entry)var10.next();
        int online = ((Integer)NEWSPlayers.get(sl.getValue())).intValue();
        if (online >= HighPlayers) {
          HighPlayers = online;
          slot = ((Integer)sl.getKey()).intValue();
          c = (ConfigurationSection)NEWItemsSlots.get(Integer.valueOf(slot));
          is = (ItemStack)sl.getValue();
        } 
      } 
      
      if (!this.ItemsSlots.values().contains(c)) {
        addSlotOnly(is, 19, c);
        added++;
        if (added >= 7) {
          NEWslots.clear();
          
          break;
        } 
      } 
      NEWslots.remove(Integer.valueOf(slot));
      HighPlayers = 0;
    } 
  }

  
  public void addSlot(ItemStack item, ConfigurationSection c, int players) {
    int slot = 19;
    if (!isBad(slot) && !this.slots.containsKey(Integer.valueOf(slot))) {
      this.SPlayers.put(item, Integer.valueOf(players));
      this.slots.put(Integer.valueOf(slot), item);
      this.ItemsSlots.put(Integer.valueOf(slot), c);
    } else {
      addSlot(item, slot + 1, c, players);
    } 
  }
  
  public void addSlot(ItemStack item, int slot, ConfigurationSection c, int players) {
    if (slot <= 25) {
      if (!isBad(slot) && !this.slots.containsKey(Integer.valueOf(slot))) {
        this.SPlayers.put(item, Integer.valueOf(players));
        this.slots.put(Integer.valueOf(slot), item);
        this.ItemsSlots.put(Integer.valueOf(slot), c);
      } else {
        addSlot(item, slot + 1, c, players);
      } 
    }
  }
  
  public void addSlotOnly(ItemStack item, int slot, ConfigurationSection c) {
    if (slot <= 25) {
      if (!isBad(slot) && !this.slots.containsKey(Integer.valueOf(slot))) {
        this.slots.put(Integer.valueOf(slot), item);
        this.ItemsSlots.put(Integer.valueOf(slot), c);
      } else {
        addSlotOnly(item, slot + 1, c);
      } 
    }
  }
  
  public void addSlotNJ(ItemStack item, int slot, ConfigurationSection c, int players) {
    if (slot <= 53) {
      if (!this.slotsNJ.containsKey(Integer.valueOf(slot)) && !this.slotsNJ.containsValue(Integer.valueOf(slot))) {
        this.slotsNJ.put(Integer.valueOf(slot), item);
        this.ItemsSlotsNJ.put(Integer.valueOf(slot), c);
      } else {
        addSlotNJ(item, slot + 1, c, players);
      } 
    }
  }

  
  public boolean isCustom() { return this.customStyle; }

  
  public void reloadMob() {
    String s = this.configName;
    this.config = ConfigController.getInstance().getMobsConfig().getConfigurationSection(s);
    this.configServers = this.config.getConfigurationSection("Servers");
    this.name = Colorize(this.config.getString("name"));
    this.loc = getLocation(this.config.getString("location"));
    this.entity = this.config.getString("mobtype");
    this.InvSize = InvSize(Integer.valueOf(this.config.getInt("InventorySize")));
    this.InvTitle = Colorize(this.config.getString("InventoryTitle"));
    this.customStyle = this.config.getBoolean("CustomStyle");
    this.MobID = this.config.getString("MobID");
    Mob.put(s.toLowerCase(), this);
    this.ent.remove();
    createInventory();
    SpawnMob();
  }
  
  private boolean isBad(int n) {
    switch (n) {
      case 9:
        return true;
      case 17:
        return true;
      case 18:
        return true;
      case 26:
        return true;
      case 27:
        return true;
      case 35:
        return true;
      case 36:
        return true;
    } 
    return false;
  }

  
  public void sendToServer(int i, Player p) {
    if (this.ItemsSlots.containsKey(Integer.valueOf(i))) {
      Lobby2.sendPlayerToServer(p, ServersController.getServer(((ConfigurationSection)this.ItemsSlots.get(Integer.valueOf(i))).getName()).getBungeeCordServerName(), ServersController.getServer(((ConfigurationSection)this.ItemsSlots.get(Integer.valueOf(i))).getName()).getName());
    }
  }

  
  public void sendToServerNJ(int i, Player p) {
    if (this.ItemsSlotsNJ.containsKey(Integer.valueOf(i))) {
      Lobby2.sendPlayerToServer(p, ServersController.getServer(((ConfigurationSection)this.ItemsSlotsNJ.get(Integer.valueOf(i))).getName()).getBungeeCordServerName(), ServersController.getServer(((ConfigurationSection)this.ItemsSlotsNJ.get(Integer.valueOf(i))).getName()).getName());
    }
  }

  public static void updateAllMobs() {
      Iterator var1 = getAll().entrySet().iterator();

      while(var1.hasNext()) {
          Entry mbs = (Entry)var1.next();

          try {
              MobsController mb = (MobsController)mbs.getValue();
              if (mb.isCustom()) {
                  mb.updateInventory();
              } else {
                  mb.updateInventory2_0();
                  mb.inv2_0.clear();
                  mb.invnj.clear();
                  mb.orderSlots();
                  Iterator var4 = ((LinkedHashMap)mb.slots.clone()).entrySet().iterator();

                  Entry kNJ;
                  while(var4.hasNext()) {
                      kNJ = (Entry)var4.next();

                      try {
                          mb.inv2_0.setOption((Integer)kNJ.getKey(), (ItemStack)kNJ.getValue());
                      } catch (Exception var8) {
                          var8.printStackTrace();
                      }
                  }

                  var4 = ((LinkedHashMap)mb.slotsNJ.clone()).entrySet().iterator();

                  while(var4.hasNext()) {
                      kNJ = (Entry)var4.next();

                      try {
                          mb.invnj.setOption((Integer)kNJ.getKey(), (ItemStack)kNJ.getValue());
                      } catch (Exception var7) {
                          var7.printStackTrace();
                      }
                  }

                  List<String> list = Lists.newArrayList();
                  int sl = mb.slotsNJ.size();
                  if (sl <= 0) {
                      sl = 1;
                  }

                  ItemStack item = getitem("35:4", sl, Colorize(ConfigController.getInstance().getDefaultConfig().getConfigurationSection("Messages").getString("GamesInProgress")), list);
                  if (mb.ItemsSlots.containsKey(19)) {
                      item = getitem(((ConfigurationSection)mb.ItemsSlots.get(19)).getString("ItemNotJoinableID"), sl, Colorize(Lobby2.getInstance().getConfig().getConfigurationSection("Messages").getString("GamesInProgress")), list);
                  }

                  mb.inv2_0.setOption(40, item);
                  ItemStack item2 = getitem(ConfigController.getInstance().getDefaultConfig().getString("ItemGoBack"), 1, Colorize(ConfigController.getInstance().getDefaultConfig().getConfigurationSection("Messages").getString("MenuGoBack")), list);
                  mb.invnj.setOption(4, item2);
              }
          } catch (Exception var9) {
              var9.printStackTrace();
          }
      }

  }


  
  public static HashMap<String, MobsController> getAll() { return Mob; }

  
  public static void onDisable() {
	  Iterator var1 = getAllEntities().keySet().iterator();
    
    while (var1.hasNext()) {
      LivingEntity ent = (LivingEntity)var1.next();
      ent.remove();
    } 
  }


  
  public static HashMap<Entity, Location> getAllEntitiesLocations() { return locs; }


  
  public static HashMap<LivingEntity, MobsController> getAllEntities() { return Ents; }


  
  public LinkedHashMap<Integer, ConfigurationSection> getItemsSlots() { return this.ItemsSlots; }


  
  public LinkedHashMap<Integer, ConfigurationSection> getItemsSlotsNJ() { return this.ItemsSlotsNJ; }

  
  private static Location getLocation(String s) {
    String[] Locs = s.split(",");
    World w = Bukkit.getWorld(Locs[0]);
    double x = Double.valueOf(Locs[1].replace(" ", "")).doubleValue();
    double y = Double.valueOf(Locs[2].replace(" ", "")).doubleValue();
    double z = Double.valueOf(Locs[3].replace(" ", "")).doubleValue();
    float yaw = Float.valueOf(Locs[4].replace(" ", "")).floatValue();
    float pitch = Float.valueOf(Locs[5].replace(" ", "")).floatValue();
    return new Location(w, x, y, z, yaw, pitch);
  }
  
  private static int InvSize(Integer i) {
    switch (i.intValue())
    { default:
        i = Integer.valueOf(54); break;
      case 9:
      case 18:
      case 27:
      case 36:
      case 45:
      case 54:
        break; }  return i.intValue();
  }

  
  private static Entity getEntity(Location loc, String s) {
    Entity ent = null;
    if (s.contains(":")) {
      String[] e = s.split(":");
      String entityType = e[0];
      ent = loc.getWorld().spawnEntity(loc, EntityType.fromName(entityType));
      if (ent.getType() == EntityType.HORSE) {
        if (!s.toUpperCase().contains("DONKEY"))
        {
          if (!s.toUpperCase().contains("MULE"))
          {
            if (!s.toUpperCase().contains("SKELETON_HORSE"))
            {
              if (s.toUpperCase().contains("UNDEAD_HORSE")); } 
          }
        }
      } else if (ent.getType() == EntityType.OCELOT) {
        if (s.toUpperCase().contains("BLACK_CAT")) {
          ((Ocelot)ent).setCatType(Ocelot.Type.BLACK_CAT);
        } else if (s.toUpperCase().contains("RED_CAT")) {
          ((Ocelot)ent).setCatType(Ocelot.Type.RED_CAT);
        } else if (s.toUpperCase().contains("SIAMESE_CAT")) {
          ((Ocelot)ent).setCatType(Ocelot.Type.SIAMESE_CAT);
        } 
      } else if (ent.getType() == EntityType.SKELETON) {
        if (s.toUpperCase().contains("WITHER"));
      
      }
      else if (ent.getType() == EntityType.SHEEP && s.toUpperCase().contains("COLOR=")) {
        String color = "WHITE";
        if (e[1].toUpperCase().contains("COLOR=")) {
          color = e[1].split("LOR=")[1];
        } else if (e[1].toUpperCase().contains("COLOR=")) {
          color = e[2].split("LOR=")[1];
        } 
        
        ((Colorable)ent).setColor(DyeColor.valueOf(color));
      } 
      
      if (s.toUpperCase().contains("SIZE") && (ent.getType() == EntityType.SLIME || ent.getType() == EntityType.MAGMA_CUBE)) {
        if (s.contains("4")) {
          ((Slime)ent).setSize(4);
        } else if (s.contains("10")) {
          ((Slime)ent).setSize(10);
        } else if (s.contains("16")) {
          ((Slime)ent).setSize(16);
        } 
      }
      
      if (s.toUpperCase().contains("BABY")) {
        ((Ageable)ent).setBaby();
      }
    } else {
      ent = loc.getWorld().spawnEntity(loc, EntityType.fromName(s));
    } 
    
    return ent;
  }
  
  private static ItemStack getitem(String s, int ammount, String DisplayName, List<String> Lore) {
    if (ammount <= 0) {
      ammount = 1;
    }
    
    String[] itemString = s.split(":");
    int ItemID = Integer.valueOf(itemString[0]).intValue();
    byte ItemByte = Byte.valueOf(itemString[1]).byteValue();
    ItemStack item = new ItemStack(Material.getMaterial(ItemID), ammount, (short)ItemByte);
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(Colorize(DisplayName));
    List<String> newLore = Lists.newArrayList();
    Iterator var11 = Lore.iterator();

    
    while (var11.hasNext()) {
      String str = (String)var11.next();
      if (str.contains("==")) {
        String[] var15;
        int var14 = (var15 = str.split("==")).length;
        
        for (int var13 = 0; var13 < var14; var13++) {
          String str2 = var15[var13];
          newLore.add(Colorize(str2));
        }  continue;
      } 
      newLore.add(Colorize(str));
    } 

    
    im.setLore(Colorize(newLore));
    item.setItemMeta(im);
    return item;
  }

  
  private static ItemStack getitem(int id, int b, int ammount, String DisplayName, List<String> Lore) {
    if (ammount <= 0) {
      ammount = 1;
    }
    
    byte ItemByte = (byte)b;
    ItemStack item = new ItemStack(Material.getMaterial(id), ammount, (short)ItemByte);
    ItemMeta im = item.getItemMeta();
    im.setDisplayName(Colorize(DisplayName));
    List<String> newLore = Lists.newArrayList();
    Iterator var11 = Lore.iterator();

    
    while (var11.hasNext()) {
      String str = (String)var11.next();
      if (str.contains("==")) {
        String[] var15;
        int var14 = (var15 = str.split("==")).length;
        
        for (int var13 = 0; var13 < var14; var13++) {
          String str2 = var15[var13];
          newLore.add(Colorize(str2));
        }  continue;
      } 
      newLore.add(Colorize(str));
    } 

    
    im.setLore(Colorize(newLore));
    item.setItemMeta(im);
    return item;
  }

  
  private static List<String> getLore(String s) {
    List<String> lore = Lists.newArrayList();
    String[] var5;
    int var4 = (var5 = s.split("==")).length;
    
    for (int var3 = 0; var3 < var4; var3++) {
      String t = var5[var3];
      lore.add(t);
    } 
    
    return lore;
  }
  
  private static List<String> getLore(List<String> slore) {
    List<String> lore = Lists.newArrayList();
    Iterator var3 = slore.iterator();
    
    while (var3.hasNext()) {
      String s = (String)var3.next();
      String[] var7;
      int var6 = (var7 = s.split("==")).length;
      
      for (int var5 = 0; var5 < var6; var5++) {
        String t = var7[var5];
        lore.add(t);
      } 
    } 
    
    return lore;
  }
  
  private static boolean checkMotdContain(String motd, String contain) {
    if (motd.isEmpty()) {
      return false;
    }
    String[] var5;
    int var4 = (var5 = contain.split(",")).length;
    
    for (int var3 = 0; var3 < var4; var3++) {
      String cont = var5[var3];
      if (motd.toLowerCase().contains(cont.toLowerCase())) {
        return true;
      }
    } 
    
    return false;
  }


  
  private static String Colorize(String s) { return (s == null) ? null : ChatColor.translateAlternateColorCodes('&', s); }

  
  private static List<String> Colorize(List<String> s) {
    List<String> txt = Lists.newArrayList();
    Iterator var3 = s.iterator();
    
    while (var3.hasNext()) {
      String t = (String)var3.next();
      txt.add(Colorize(t));
    } 
    
    return txt;
  }
  
  public static void addEnchantmentGlow(ItemStack item) {
    Enchantment glow = EnchantGlow.getGlow();
    item.addEnchantment(glow, 1);
  }
  
  public static class EnchantGlow
    extends EnchantmentWrapper {
    private static Enchantment glow;
    
    public EnchantGlow(int id) { super(id); }


    
    public boolean canEnchantItem(ItemStack item) { return true; }


    
    public boolean conflictsWith(Enchantment other) { return false; }


    
    public EnchantmentTarget getItemTarget() { return null; }


    
    public int getMaxLevel() { return 10; }


    
    public String getName() { return "Glow"; }


    
    public int getStartLevel() { return 1; }

    
    public static Enchantment getGlow() {
      if (glow != null) {
        return glow;
      }
      try {
    	Field f = Enchantment.class.getDeclaredField("acceptingNew");
        f = Enchantment.class.getDeclaredField("acceptingNew");
        f.setAccessible(true);
        f.set(null, Boolean.valueOf(true));
      } catch (Exception var1) {
        var1.printStackTrace();
      } 
      
      glow = new EnchantGlow('�');
      Enchantment.registerEnchantment(glow);
      return glow;
    }

    
    public static void addGlow(ItemStack item) {
      Enchantment glow = getGlow();
      item.addEnchantment(glow, 1);
    }
  }
}