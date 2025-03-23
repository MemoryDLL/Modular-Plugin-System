package dev.memorydealer.modules.endportal;

import dev.memorydealer.core.module.Module;
import dev.memorydealer.modules.endportal.listeners.PreventEndPortalActivationListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.time.LocalDateTime;

public class EndPortalModule implements Module {

    private final Plugin plugin;
    private boolean enabled = false;
    private PreventEndPortalActivationListener preventEndPortalActivationListener;

    public EndPortalModule(Plugin plugin, boolean enabled) {

        this.plugin = plugin;
        this.enabled = enabled;
    }

    public void start() {
        if (!enabled) return;
        LocalDateTime baseline = LocalDateTime.of(2025, 3, 28, 0, 0);
        LocalDateTime endAllowedTime = baseline.plusMonths(1); // Opens 28 April 2025

        preventEndPortalActivationListener = new PreventEndPortalActivationListener(endAllowedTime, enabled);

        Bukkit.getPluginManager().registerEvents(preventEndPortalActivationListener, plugin);
    }

    @Override
    public void stop() {
        HandlerList.unregisterAll(preventEndPortalActivationListener);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;

        this.enabled = enabled;
        if (enabled) start();
        else stop();
    }

    @Override
    public String getName() {
        return "End Portal Module";
    }
}