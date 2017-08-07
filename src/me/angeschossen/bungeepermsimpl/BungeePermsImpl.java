package me.angeschossen.bungeepermsimpl;

import me.angeschossen.bungeepermsimpl.methods.Datemanager;
import me.angeschossen.bungeepermsimpl.methods.Filemanager;
import me.angeschossen.bungeepermsimpl.methods.Logmanager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import net.alpenblock.bungeeperms.BungeePerms;
import net.alpenblock.bungeeperms.Group;
import net.alpenblock.bungeeperms.PermissionsManager;
import net.alpenblock.bungeeperms.User;

/**
 * Created by David on 06.08.2017.
 */
public class BungeePermsImpl extends JavaPlugin {

    private static BungeePermsImpl instance;

    public static BungeePermsImpl getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        Datemanager.getInstance().startAll();
    }


    public PermissionsManager getPermissionManager() {
        return BungeePerms.getInstance().getPermissionsManager();
    }

    public Group getGroup(String group) {
        return getPermissionManager().getGroup(group);
    }

    public User getUser(UUID uuid) {
        return getPermissionManager().getUser(uuid);
    }

    public void setGroup(User user, Group group, Player player) {

        try {
            List<String> data = user.getPerms();
            for (String key : data) {
                getPermissionManager().removeUserPerm(user, key);
                data.remove(key);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        getPermissionManager().removeUserGroup(user, getPermissionManager().getMainGroup(user));
        getPermissionManager().addUserGroup(user, group);
        Logmanager.getInstance().addRankLog("Set rank " + group.getName() + " for " + user.getName() + " (" + user.getUUID() + ")" + " Handler: " + player.getName() + " (" + player.getUniqueId() + ")");

    }

    public void setGroupVoteReward(User user, Group group) {

        try {
            List<String> data = user.getPerms();
            for (String key : data) {
                getPermissionManager().removeUserPerm(user, key);
                data.remove(key);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        getPermissionManager().removeUserGroup(user, getPermissionManager().getMainGroup(user));
        getPermissionManager().addUserGroup(user, group);
        Filemanager.getInstance().registerData(user.getUUID().toString(), Datemanager.getInstance().todayDateString());


    }

    public void setDefaultGroup(User user) {
        getPermissionManager().removeUserGroup(user, getPermissionManager().getMainGroup(user));
        getPermissionManager().addUserGroup(user, getPermissionManager().getDefaultGroups().get(0));
    }


    public boolean isInGroup(User user, Group group) {
        if (!getPermissionManager().getMainGroup(user).equals(group)) {
            return false;
        }
        return true;
    }
}
