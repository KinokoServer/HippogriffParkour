package net.ramuremo.hippogriffparkour.event;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public final class ParkourStartEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Block block;
    private boolean cancelled = false;
    public ParkourStartEvent(Player player, Block block) {
        this.player = player;
        this.block = block;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @Nonnull HandlerList getHandlers() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
