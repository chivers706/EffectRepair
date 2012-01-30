package main.java.multitallented.plugins.herostronghold.effects;

import multitallented.redcastlemedia.bukkit.herostronghold.HeroStronghold;
import multitallented.redcastlemedia.bukkit.herostronghold.effect.Effect;
import multitallented.redcastlemedia.bukkit.herostronghold.region.Region;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

public class EffectRepair extends Effect {

    public final HeroStronghold aPlugin;

    public EffectRepair(HeroStronghold plugin) {
        super(plugin);
        this.aPlugin = plugin;
        registerEvent(Type.PLAYER_INTERACT, new RepairListener(this), Priority.Highest);
    }

    @Override
    public void init(HeroStronghold plugin) {
        super.init(plugin);
    }

    public class RepairListener extends PlayerListener {

        private final EffectRepair effect;
        public RepairListener(EffectRepair effect) {
            this.effect = effect;
        }

        private Material getRequiredReagent(Material material) {
            switch (material) {
                case WOOD_SWORD:
                case WOOD_AXE:
                case WOOD_HOE:
                case WOOD_PICKAXE:
                case WOOD_SPADE:
                    return Material.WOOD;
                case STONE_SWORD:
                case STONE_AXE:
                case STONE_HOE:
                case STONE_PICKAXE:
                case STONE_SPADE:
                    return Material.COBBLESTONE;
                case SHEARS:
                case FLINT_AND_STEEL:
                case IRON_CHESTPLATE:
                case IRON_LEGGINGS:
                case IRON_BOOTS:
                case IRON_HELMET:
                case IRON_SWORD:
                case IRON_AXE:
                case IRON_HOE:
                case IRON_PICKAXE:
                case IRON_SPADE:
                    return Material.IRON_INGOT;
                case GOLD_CHESTPLATE:
                case GOLD_LEGGINGS:
                case GOLD_BOOTS:
                case GOLD_HELMET:
                case GOLD_SWORD:
                case GOLD_AXE:
                case GOLD_HOE:
                case GOLD_PICKAXE:
                case GOLD_SPADE:
                    return Material.GOLD_INGOT;
                case DIAMOND_CHESTPLATE:
                case DIAMOND_LEGGINGS:
                case DIAMOND_BOOTS:
                case DIAMOND_HELMET:
                case DIAMOND_SWORD:
                case DIAMOND_AXE:
                case DIAMOND_HOE:
                case DIAMOND_PICKAXE:
                case DIAMOND_SPADE:
                    return Material.DIAMOND;
                case LEATHER_BOOTS:
                case LEATHER_CHESTPLATE:
                case LEATHER_HELMET:
                case LEATHER_LEGGINGS:
                    return Material.LEATHER;
                case FISHING_ROD:
                case BOW:
                    return Material.STRING;
                default:
                    return null;
            }
        }

        private int getRepairCost(ItemStack is) {
            Material mat = is.getType();
            int amt = 1;
            switch (mat) {
                case BOW:
                case FISHING_ROD:
                    amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 2.0);
                    return amt < 1 ? 1 : amt;
                case LEATHER_BOOTS:
                case IRON_BOOTS:
                case GOLD_BOOTS:
                case DIAMOND_BOOTS:
                    amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 3.0);
                    return amt < 1 ? 1 : amt;
                case LEATHER_HELMET:
                case IRON_HELMET:
                case GOLD_HELMET:
                case DIAMOND_HELMET:
                    amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 4.0);
                    return amt < 1 ? 1 : amt;
                case LEATHER_CHESTPLATE:
                case IRON_CHESTPLATE:
                case GOLD_CHESTPLATE:
                case DIAMOND_CHESTPLATE:
                    amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 7.0);
                    return amt < 1 ? 1 : amt;
                case LEATHER_LEGGINGS:
                case IRON_LEGGINGS:
                case GOLD_LEGGINGS:
                case DIAMOND_LEGGINGS:
                    amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 6.0);
                    return amt < 1 ? 1 : amt;
                case WOOD_SWORD:
                case STONE_SWORD:
                case IRON_SWORD:
                case GOLD_SWORD:
                case DIAMOND_SWORD:
                	amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 2.0);
                	return amt < 1 ? 1 : amt;
                case WOOD_PICKAXE:
                case STONE_PICKAXE:
                case IRON_PICKAXE:
                case GOLD_PICKAXE:
                case DIAMOND_PICKAXE:
                	amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 2.0);
                	return amt < 1 ? 1 : amt;
                case WOOD_HOE:
                case STONE_HOE:
                case IRON_HOE:
                case GOLD_HOE:
                case DIAMOND_HOE:
                	amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 2.0);
                	return amt < 1 ? 1 : amt;
                case WOOD_SPADE:
                case STONE_SPADE:
                case IRON_SPADE:
                case GOLD_SPADE:
                case DIAMOND_SPADE:
                	amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 1.0);
                	return amt < 1 ? 1 : amt;
                case WOOD_AXE:
                case STONE_AXE:
                case IRON_AXE:
                case GOLD_AXE:
                case DIAMOND_AXE:
                	amt = (int) ((is.getDurability() / (double) mat.getMaxDurability()) * 2.0);
                	return amt < 1 ? 1 : amt;
                default:
                    return 0;
            }
        }

        @Override
        public void onPlayerInteract(PlayerInteractEvent event) {
            if (event.isCancelled() || !event.getClickedBlock().getType().equals(Material.IRON_BLOCK)) {
                return;
            }
            
            Region r = effect.getContainingRegion(event.getClickedBlock().getLocation());
            if (r == null) {
                return;
            }
            
            if (effect.regionHasEffect(effect.aPlugin.getRegionManager().getRegionType(r.getType()).getEffects(), "repair") == 0) {
                return;
            }
            
            
            Player player = event.getPlayer();
            
            if (!r.isMember(player.getName()) && !r.isOwner(player.getName())) {
                return;
            }
            
            
            ItemStack is = player.getItemInHand();
            Material reagent = getRequiredReagent(is.getType());
            if (is == null) {
                player.sendMessage(ChatColor.GRAY + "[HeroStronghold] You must hold the item you wish to repair.");
                return;
            }
            int repairCost = getRepairCost(is);
            if (repairCost == 0) {
                player.sendMessage(ChatColor.GRAY + "[HeroStronghold] That item isn't something you can repair here.");
                event.setCancelled(true);
                return;
            }
            ItemStack cost = new ItemStack(reagent, repairCost);
            if (!hasReagentCost(player, cost)) {
                player.sendMessage(ChatColor.GRAY + "[HeroStronghold] You don't have enough "+ reagent.name().toLowerCase().replace("_", " "));
                return;
            }
            
            String isName = is.toString();
            player.getInventory().remove(cost);
            is.setDurability((short) 0);
            //player.sendMessage(ChatColor.GRAY + "[HeroStronghold] " + isName + "  repaired");
        }
        protected boolean hasReagentCost(Player player, ItemStack itemStack) {
        	int amount = 0;
        	for(ItemStack stack : player.getInventory().all(itemStack.getType()).values()) {
        		amount += stack.getAmount();
        		if(amount >= itemStack.getAmount()) {
        			return true;
                        }
        	}
        	return false;
        }
    }
}