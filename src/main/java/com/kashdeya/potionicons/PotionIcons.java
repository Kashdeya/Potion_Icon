package com.kashdeya.potionicons;

import java.io.File;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.kashdeya.potionicons.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class PotionIcons {
	
	@Instance(value = Reference.MOD_ID)
	public static PotionIcons instance;
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_COMMON)
	public static CommonProxy proxy;
	
	public static boolean infoOff = false;
	
	public static Configuration config;	
	public static final File configDir = new File("config/PotionIcons");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		this.proxy.preInit(e);
		MinecraftForge.EVENT_BUS.register(instance);
		
		File f = new File(configDir, "PotionIcons.cfg");
        config = new Configuration(f);
		config.load();
		String category;
		category = "Potion Icons";
		
	    PotionIcons.infoOff = config.getBoolean("Disable Potion Icons",  category, true, "Disable the potion Icons in the top right of your screen?");
	    
	    config.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e){
		this.proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		this.proxy.postInit(e);
	}
	
	@SubscribeEvent
	public void onPotionOverlayEvent(RenderGameOverlayEvent.Pre e){
			if (infoOff){
		if(e.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS){
				e.setCanceled(true);
			}
		}
	}
}
