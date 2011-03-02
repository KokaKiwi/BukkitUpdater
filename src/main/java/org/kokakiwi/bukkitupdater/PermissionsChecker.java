package org.kokakiwi.bukkitupdater;

import java.util.logging.Logger;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijikokun.bukkit.Permissions.Permissions;

public class PermissionsChecker {
	
	private enum PermissionHandler {
		PERMISSIONS,
		GROUP_MANAGER,
		NONE
	}
	
	private final Logger logger = Logger.getLogger("Minecraft.BukkitUpdater.PermissionsChecker");
	
	private final BukkitUpdater plugin;
	private final PermissionHandler handler;
	private Plugin permissionPlugin;

	public PermissionsChecker(BukkitUpdater bukkitUpdater) {
		this.plugin = bukkitUpdater;
		
		if(plugin.getPluginManager().getPlugin("GroupManager") != null)
		{
			permissionPlugin = plugin.getPluginManager().getPlugin("GroupManager");
			handler = PermissionHandler.GROUP_MANAGER;
			logger.info("BukkitUpdater : Using Group Manager v" + permissionPlugin.getDescription().getVersion());
		}else if(plugin.getPluginManager().getPlugin("Permissions") != null) {
			permissionPlugin = plugin.getPluginManager().getPlugin("Permissions");
			handler = PermissionHandler.PERMISSIONS;
			logger.info("BukkitUpdater : Using Permissions v" + permissionPlugin.getDescription().getVersion());
		}else {
			handler = PermissionHandler.NONE;
			logger.info("BukkitUpdater : None permissions plugins loaded, using OPs settings!");
		}
	}
	
	public boolean has(Player player, String permission)
	{
		if(handler == PermissionHandler.GROUP_MANAGER)
		{
			GroupManager groupManager = (GroupManager) permissionPlugin;
			return groupManager.getHandler().has(player, permission);
		}else if(handler == PermissionHandler.PERMISSIONS) {
			Permissions permissions = (Permissions) permissionPlugin;
			return permissions.getHandler().has(player, permission);
		}else {
			return player.isOp();
		}
	}
}
