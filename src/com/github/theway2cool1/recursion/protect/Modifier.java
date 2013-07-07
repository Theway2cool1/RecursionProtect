package com.github.theway2cool1.recursion.protect;

public enum Modifier{
	DISABLE_CREATURE_SPAWN("nospawn", "When applied, non-player entities will not spawn in the specified region."),
	DISABLE_DAMAGE("nodamage", "When applied, players will not take damage in the specified region."),
	DISABLE_ENTITY_GRIEF("noentitygrief", "When applied, non-player entities will not be able to modify blocks in the specified region."),
	DISABLE_ENTITY_TELEPORT_IN("nocreaturetpin", "When applied, non-player entities will not be able to teleport into the specified region (ex. Endermen)"),
	DISABLE_ENTITY_TELEPORT_OUT("nocreaturetpout", "When applied, non-player entities will not be able to teleport out of the specified region (ex. Endermen)"),
	DISABLE_FIRE_SPREAD("nofirespread", "When applied, fire will not spread in the specified region."),
	DISABLE_FIRE_BURN("nofireburn", "When applied, fire will not burn blocks away in the specified region."),
	DISABLE_LAVA_FLOW("nolavaflow", "When applied, lava will not flow in the specified region."),
	DISABLE_LIGHTNING_STRIKE("nolightning", "When applied, lightning will not strike in the specified region."),
	DISABLE_PVE("nopve", "When applied, players will not be able to harm non-player entities in the specified region (and vice versa)."),
	DISABLE_PVP("nopvp", "When applied, players will not be able to harm other players in the specified region."),
	DISABLE_WATER_FLOW("nowaterflow", "When applied, water will not flow in the specified region."),
	ENFORCE_BUILD_PERMISSIONS("enforcebuild", "When applied, players will require explicit permission to place blocks in the specified region (rprotect.region.place.<region-name>)."),
	ENFORCE_DESTROY_PERMISSIONS("enforcedestroy", "When applied, players will require explicit permission to break blocks in the specified region (rprotect.region.break.<region-name>)."),
	ENFORCE_ITEM_USE_PERMISSIONS("enforceuse", "When applied, players will require explicit permission to use items in the specified region (rprotect.region.use.<region-name>).");
	String label;
	String desc;
	Modifier(String label, String desc){
		this.label=label;
		this.desc=desc;
	}
	public String getLabel(){
		return label;
	}
	public String getDescription(){
		return desc;
	}
	public static boolean isValid(String label){
		for(Modifier m : values()){
			if(m.getLabel().equalsIgnoreCase(label)) return true;
		}
		return false;
	}
}
