package lc.lobby2.Controller;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;

public class ServersController {
    private static HashMap<String, ServersController> Sv = new HashMap();
    private static Gson gson = new Gson();
    private static List<ServersController> Servers = Lists.newArrayList();
    private String name = "";
    private ConfigurationSection config;
    private int interval = 5;
    private int timeout = 1000;
    private boolean online = false;
    private int currentplayers = 0;
    private String BungeeName = "";
    private int maxplayers = 0;
    private String motd = "";
    private String ip = "";

    public static ServersController getServer(String s) {
        return Sv.containsKey(s) ? (ServersController)Sv.get(s) : new ServersController(s);
    }

    public ServersController(String s) {
        this.name = s;
        this.config = ConfigController.getInstance().getServersConfig().getConfigurationSection(s);
        this.timeout = this.config.getInt("timeout");
        this.BungeeName = this.config.getString("BungeeServerName");
        this.ip = this.getIPFromHost(this.config.getString("ip"));
        Sv.put(s, this);
        Servers.add(this);
    }

    public String getIPFromHost(String host) {
        String ret = host;

        try {
            InetAddress address = InetAddress.getByName(host);
            ret = address.getHostAddress();
        } catch (UnknownHostException var4) {
            var4.printStackTrace();
        }

        return ret;
    }

    public String getName() {
        return this.name;
    }

    public int getUpdateInterval() {
        return this.config.getInt("UpdateInterval");
    }

    public String getIP() {
        return this.ip;
    }

    public boolean isOnline() {
        return this.online;
    }

    public String getBungeeCordServerName() {
        return this.BungeeName;
    }

    public int getCurrentPlayers() {
        return this.currentplayers;
    }

    public int getMaxPlayers() {
        return this.maxplayers;
    }

    public String getMotd() {
        return this.motd;
    }

    public int getPort() {
        return this.config.getInt("port");
    }

    public boolean updatePING() {
        if (this.interval <= 0) {
            this.interval = this.getUpdateInterval();
            return true;
        } else {
            --this.interval;
            return false;
        }
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void ping() throws IOException, ParseException {
        Throwable var1 = null;
        Object var2 = null;

        try {
            Socket socket = new Socket(this.getIP(), this.getPort());

            try {
                socket.setSoTimeout(this.timeout);
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream handshake = new DataOutputStream(b);
                handshake.writeByte(0);
                writeVarInt(handshake, 47);
                writeVarInt(handshake, this.getIP().length());
                handshake.writeBytes(this.getIP());
                handshake.writeShort(this.getPort());
                writeVarInt(handshake, 1);
                writeVarInt(dataOutputStream, b.size());
                dataOutputStream.write(b.toByteArray());
                dataOutputStream.writeByte(1);
                dataOutputStream.writeByte(0);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                readVarInt(dataInputStream);
                int id = readVarInt(dataInputStream);
                if (id == -1) {
                    this.online = false;
                    this.currentplayers = 0;
                    this.motd = "";
                    dataOutputStream.close();
                    outputStream.close();
                    inputStreamReader.close();
                    inputStream.close();
                    socket.close();
                    return;
                }

                if (id != 0) {
                    this.online = false;
                    this.currentplayers = 0;
                    this.motd = "";
                    dataOutputStream.close();
                    outputStream.close();
                    inputStreamReader.close();
                    inputStream.close();
                    socket.close();
                    return;
                }

                int length = readVarInt(dataInputStream);
                if (length == -1) {
                    this.online = false;
                    this.currentplayers = 0;
                    this.motd = "";
                    dataOutputStream.close();
                    outputStream.close();
                    inputStreamReader.close();
                    inputStream.close();
                    socket.close();
                    return;
                }

                if (length == 0) {
                    this.online = false;
                    this.currentplayers = 0;
                    this.motd = "";
                    dataOutputStream.close();
                    outputStream.close();
                    inputStreamReader.close();
                    inputStream.close();
                    socket.close();
                    return;
                }

                byte[] in = new byte[length];
                dataInputStream.readFully(in);
                String json = new String(in);
                long now = System.currentTimeMillis();
                dataOutputStream.writeByte(9);
                dataOutputStream.writeByte(1);
                dataOutputStream.writeLong(now);
                readVarInt(dataInputStream);
                id = readVarInt(dataInputStream);
                if (id == -1) {
                    this.online = false;
                    this.currentplayers = 0;
                    this.motd = "";
                    dataOutputStream.close();
                    outputStream.close();
                    inputStreamReader.close();
                    inputStream.close();
                    socket.close();
                    return;
                }

                if (id != 1) {
                    this.online = false;
                    this.currentplayers = 0;
                    this.motd = "";
                    dataOutputStream.close();
                    outputStream.close();
                    inputStreamReader.close();
                    inputStream.close();
                    socket.close();
                    return;
                }

                dataInputStream.readLong();
                StatusResponse resp = (StatusResponse)gson.fromJson(json, StatusResponse.class);
                this.currentplayers = resp.getPlayers().getOnline();
                this.maxplayers = resp.getPlayers().getMax();
                this.motd = resp.getDescription();
                if (this.motd != null && !this.motd.equals("")) {
                    this.online = true;
                }

                dataOutputStream.close();
                outputStream.close();
                inputStreamReader.close();
                inputStream.close();
                socket.close();
            } finally {
                if (socket != null) {
                    socket.close();
                }

            }

        } catch (Throwable var23) {
            if (var1 == null) {
                var1 = var23;
            } else if (var1 != var23) {
                var1.addSuppressed(var23);
            }

            throw var23;
        }
    }

    public static void pingAllServers() {
        Iterator var1 = Servers.iterator();

        while(var1.hasNext()) {
            ServersController sv = (ServersController)var1.next();
            if (sv.updatePING()) {
                try {
                    sv.ping();
                } catch (IOException var3) {
                    sv.online = false;
                    sv.currentplayers = 0;
                    sv.motd = "";
                } catch (ParseException var4) {
                    sv.online = false;
                    sv.currentplayers = 0;
                    sv.motd = "";
                }
            }
        }

    }

    public static List<ServersController> getAllServers() {
        return Servers;
    }

    private static int readVarInt(DataInputStream stream) throws IOException {
        int out = 0;
        int bytes = 0;

        byte in;
        do {
            in = stream.readByte();
            out |= (in & 127) << bytes++ * 7;
            if (bytes > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while((in & 128) == 128);

        return out;
    }

    private static void writeVarInt(DataOutputStream stream, int value) throws IOException {
        do {
            int part = value & 127;
            value >>>= 7;
            if (value != 0) {
                part |= 128;
            }

            stream.writeByte(part);
        } while(value != 0);

    }

    public class Player {
        private String name;
        private String id;

        public Player() {
        }

        public String getName() {
            return this.name;
        }

        public String getId() {
            return this.id;
        }
    }

    public class Players {
        private int max;
        private int online;
        private List<Player> sample;

        public Players() {
        }

        public int getMax() {
            return this.max;
        }

        public int getOnline() {
            return this.online;
        }

        public List<Player> getSample() {
            return this.sample;
        }
    }

    public class StatusResponse {
        private Object description;
        private Players players;
        private Version version;
        private String favicon;
        private int time;

        public StatusResponse() {
        }

        public String getDescription() {
            return this.description instanceof LinkedTreeMap ? (String)((LinkedTreeMap)this.description).get("text") : this.description.toString();
        }

        public Players getPlayers() {
            return this.players;
        }

        public Version getVersion() {
            return this.version;
        }

        public String getFavicon() {
            return this.favicon;
        }

        public int getTime() {
            return this.time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public class Version {
        private String name;
        private String protocol;

        public Version() {
        }

        public String getName() {
            return this.name;
        }

        public String getProtocol() {
            return this.protocol;
        }
    }
}
