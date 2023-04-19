/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.model.skill;

public class L1SkillId {
	public static final int SKILLS_BEGIN = 1;

	// Regular Magic Lv1-11

	// Common Magic Level 1
	public static final int HEAL = 					1;
	public static final int LIGHT = 				2;
	public static final int SHIELD = 				3;
	public static final int ENERGY_BOLT = 			4;
	public static final int TELEPORT = 				5;
	public static final int ICE_DAGGER = 			6;
	public static final int WIND_CUTTER = 			7;
	public static final int HOLY_WEAPON = 			8;

	// Common Magic Level 2
	public static final int CURE_POISON = 			9;
	public static final int CHILL_TOUCH = 			10;
	public static final int CURSE_POISON = 			11;
	public static final int ENCHANT_WEAPON = 		12;
	public static final int DETECTION = 			13;
	public static final int DECREASE_WEIGHT = 		14;
	public static final int FIRE_ARROW = 			15;
	public static final int STALAC = 				16;

	// Common Magic Level 3
	public static final int LIGHTNING = 			17;
	public static final int TURN_UNDEAD = 			18;
	public static final int EXTRA_HEAL = 			19;
	public static final int CURSE_BLIND = 			20;
	public static final int BLESSED_ARMOR = 		21;
	public static final int FROZEN_CLOUD = 			22;
	public static final int WEAK_ELEMENTAL = 		23;

	// Common Magic Level 4 // none = 24
	public static final int FIREBALL = 				25;
	public static final int PHYSICAL_ENCHANT_DEX = 	26;
	public static final int WEAPON_BREAK = 			27;
	public static final int VAMPIRIC_TOUCH = 		28;
	public static final int SLOW = 					29;
	public static final int EARTH_JAIL = 			30;
	public static final int COUNTER_MAGIC = 		31;
	public static final int MEDITATION = 			32;

	// Common Magic Level 5
	public static final int CURSE_PARALYZE = 		33;
	public static final int CALL_LIGHTNING = 		34;
	public static final int GREATER_HEAL = 			35;
	public static final int TAMING_MONSTER = 		36;
	public static final int REMOVE_CURSE = 			37;
	public static final int CONE_OF_COLD = 			38;
	public static final int MANA_DRAIN = 			39;
	public static final int DARKNESS = 				40;

	// Common Magic Level 6
	public static final int CREATE_ZOMBIE = 		41;
	public static final int PHYSICAL_ENCHANT_STR = 	42;
	public static final int HASTE = 				43;
	public static final int CANCELLATION = 			44;
	public static final int ERUPTION = 				45;
	public static final int SUNBURST = 				46;
	public static final int WEAKNESS = 				47;
	public static final int BLESS_WEAPON = 			48;

	// Wizard Magic Level 7
	public static final int HEAL_ALL = 				49;
	public static final int FREEZING_ARMOUR = 		50;
	public static final int SUMMON_MONSTER = 		51;
	public static final int HOLY_WALK = 			52;
	public static final int TORNADO = 				53;
	public static final int GREATER_HASTE = 		54;
	public static final int BERSERKERS = 			55;
	public static final int ENCHANT_ACCURACY = 		56;

	// Wizard Magic Level 8
	public static final int FULL_HEAL = 			57;
	public static final int FIRE_WALL = 			58;
	public static final int BLIZZARD = 				59;
	public static final int INVISIBILITY = 			60;
	public static final int RESURRECTION = 			61;
	public static final int EARTHQUAKE = 			62;
	public static final int LIFE_STREAM = 			63;
	public static final int SILENCE = 				64;

	// Wizard Magic Level 9
	public static final int LIGHTNING_STORM = 		65;
	public static final int FOG_OF_SLEEPING = 		66;
	public static final int SHAPE_CHANGE = 			67;
	public static final int IMMUNE_TO_HARM = 		68;
	public static final int MASS_TELEPORT = 		69;
	public static final int FIRE_STORM = 			70;
	public static final int DECAY_POTION = 			71;
	public static final int COUNTER_DETECTION = 	72;

	// Wizard Magic Level 10
	//public static final int CREATE_MAGICAL_WEAPON = 73;
	public static final int DEATH_HEAL = 			73;
	public static final int METEOR_STRIKE = 		74;
	public static final int GREATER_RESURRECTION = 	75;
	public static final int IceMETEOR_STRIKE = 		76;
	public static final int DISINTEGRATE = 			77;
	public static final int ABSOLUTE_BARRIER = 		78;
	public static final int ADVANCE_SPIRIT = 		79;
	public static final int ICE_SPIKE = 			80;

	// Wizard Magic Level 11
	public static final int ETERNITY = 				243;

	// none = 81 - 86

	// Knight skills
	public static final int SHOCK_STUN = 			87;
	public static final int REDUCTION_ARMOR = 		88;
	public static final int BOUNCE_ATTACK = 		89;
	public static final int SOLID_CARRIAGE = 		90;
	public static final int COUNTER_BARRIER = 		91;
	public static final int ABSOLUTE_BLADE = 		92;
	public static final int PRIDE = 				93;
	public static final int BLOWATTACK = 			94;
	public static final int FORCE_STUN = 			242;
	// passive
	public static final int COUNTER_BARRIER_VETERAN = 512;
	public static final int REDUCTION_ARMOUR_VETERAN = 568;
	public static final int RAGING_FORCE = 			569;

	// none = 95-96
	// Dark Spirit Magic Level 1
	public static final int BLIND_HIDING = 			97;
	public static final int ENCHANT_VENOM = 		98;
	public static final int SHADOW_ARMOR = 			99;
	public static final int DRESS_MIGHTY = 			109;
	public static final int BRING_STONE = 			100;

	// Dark Spirit Magic Level 2
	public static final int MOVING_ACCELERATION = 	101;
	public static final int BURNING_SPIRIT = 		566;
	public static final int DARK_BLIND = 			103;
	public static final int VENOM_RESIST = 			104;
	public static final int DRESS_DEXTERITY = 		110;

	// Dark Spirit Magic Level 3
	public static final int DOUBLE_BRAKE = 			105;
	public static final int UNCANNY_DODGE = 		106;
	public static final int SHADOW_FANG = 			107;
	public static final int FINAL_BURN = 			108;
	public static final int DRESS_EVASION = 		565;

	// Dark Spirit Magic Level 4
	public static final int ARMOR_BREAK = 			112;
	public static final int SHADOW_STAFF = 			199;
	public static final int LUCIFER = 				234;
	public static final int ASSASSIN = 				233;

	// passive
	public static final int ARMOUR_BREAK_DESTINY = 	510;
	public static final int DOUBLE_BREAK_DESTINY = 	511;
	public static final int 파이널번 = 				517; // final burn
	public static final int LUCIFER_DESTINY = 		567;

	// Pledge buffs
	public static final int CLAN_BUFF1 = 			505;
	public static final int CLAN_BUFF2 = 			506;
	public static final int CLAN_BUFF3 = 			507;
	public static final int CLAN_BUFF4 = 			508;

	// Royal Magic
	public static final int TRUE_TARGET = 			113;
	public static final int GLOWING_WEAPON = 		114;
	public static final int SHINING_SHIELD = 		115;
	public static final int SHINING_ARMOUR = 		116;
	public static final int BRAVE_MENTAL = 			117;
	public static final int MAJESTY = 				118;
	public static final int GRACE_AVATAR = 			122;
	public static final int EMPIRE = 				123;
	public static final int AURA = 					10531;
	public static final int PRIME = 				241;

	// none = 119-120 124-128

	// Spirit Magic
	public static final int RESIST_MAGIC = 			129;
	public static final int BODY_TO_MIND = 			130;
	public static final int TELEPORT_TO_MOTHER = 	131;
	public static final int TRIPLE_ARROW = 			132;
	public static final int ELEMENTAL_FALL_DOWN = 	133;
	public static final int COUNTER_MIRROR = 		134;
	public static final int SOUL_BARRIER = 			135;
	public static final int INFERNO = 				136;
	public static final int CLEAR_MIND = 			137;
	public static final int RESIST_ELEMENTAL = 		138;
	// none = 139 - 144
	public static final int RETURN_TO_NATURE = 		145;
	public static final int BLOODY_SOUL = 			146;
	public static final int ELEMENTAL_PROTECTION = 	147;
	public static final int FIRE_WEAPON = 			148;
	public static final int WIND_SHOT = 			149;
	public static final int WIND_WALK = 			150;
	public static final int FIRE_SHIELD = 			151;
	public static final int ENTANGLE = 				152;
	public static final int ERASE_MAGIC = 			153;
	public static final int LESSER_ELEMENTAL = 		154;

	public static final int FIRE_BLESS = 			155;
	public static final int STORM_EYE = 			156;
	public static final int EARTH_BIND = 			157;
	public static final int NATURES_TOUCH = 		158;
	public static final int EARTH_BLESS = 			159;
	public static final int AQUA_PROTECTER = 		160;
	public static final int AREA_OF_SILENCE = 		161;
	public static final int GREATER_ELEMENTAL = 	162;
	public static final int BURNING_WEAPON = 		163;
	public static final int NATURES_BLESSING = 		164;

	public static final int CALL_OF_NATURE = 		165;
	public static final int STORM_SHOT = 			166;
	public static final int CYCLONE = 				167;
	public static final int IRON_SKIN = 			168;
	public static final int EXOTIC_VITALIZE = 		169;
	public static final int WATER_LIFE = 			170;
	public static final int ELEMENTAL_FIRE = 		171;
	public static final int STORM_WALK = 			172;
	public static final int POLLUTE_WATER = 		173;
	public static final int STRIKER_GALE = 			174;

	public static final int SOUL_OF_FLAME = 		175;
	public static final int ADDITIONAL_FIRE = 		176;
	public static final int FOCUS_WAVE = 			177;
	public static final int HURRICANE = 			178;
	public static final int SANDSTORM = 			179;

	// Dragon Knight Level 1
	public static final int DRAGON_SKIN = 			181;
	public static final int BURNING_SLASH = 		182;
	public static final int DESTROY = 				183;
	public static final int MAGMA_BREATH = 			184;
	public static final int AWAKEN_ANTHARAS = 		185;

	// Dragon Knight Level 2
	public static final int BLOOD_LUST = 			186;
	public static final int FOE_SLAYER = 			187;
	public static final int FEAR = 					188;
	public static final int AWAKEN_FAFURION = 		190;
	//public static final int DESTROY_PEER = 196; // do not add until new client

	// Dragon Knight Level 3
	public static final int MORTAL_BODY = 			191;
	public static final int THUNDER_GRAB = 			192;
	public static final int DESTROY_HORROR = 		193;
	public static final int EYE_OF_DRAGON = 		194;
	public static final int AWAKEN_VALAKAS = 		195;

	// Dragon Knight Level 4
	public static final int AWAKEN_LINDVIOR = 		197;
	// passive
	public static final int THUNDER_GRAB_BRAVE = 	513;
	public static final int FOE_SLAYER_BRAVE = 		514;
	public static final int AURACIA = 				518;

	// Dragon Knight Level 5
	public static final int HALPHAS = 				245;

	// Illusionist
	public static final int MIRROR_IMAGE = 			201;
	public static final int CONFUSION = 			202;
	public static final int SMASH = 				203;
	public static final int ILLUSION_OGRE = 		204;
	public static final int CUBE_OGRE = 			205;
	public static final int CONCENTRATION = 		206;
	public static final int MIND_BREAK = 			207;
	public static final int BONE_BREAK = 			208;
	public static final int ILLUSION_LICH = 		209;

	public static final int CUBE_GOLEM = 			210;
	public static final int PATIENCE = 				211;
	public static final int PHANTASM = 				212;
	public static final int AM_BREAK = 				213;
	public static final int ILLUSION_GOLEM = 		214;
	public static final int CUBE_LICH = 			215;
	public static final int INSIGHT = 				216;
	public static final int PANIC = 				217;
	public static final int REDUCE_WEIGHT = 		218;
	public static final int ILLUSION_AVATAR = 		219;

	public static final int CUBE_AVATAR = 			220;
	public static final int SKILLS_END = 			220; // TODO why?
	public static final int IMPACT = 				222;
	public static final int FOCUS_SPIRIT = 			223;
	public static final int MOBIUS = 				224;
	public static final int POTENTIAL = 			246;
	// passive
	public static final int DARK_HORSE = 			516;

	// Warrior - Active
	public static final int HOWL = 					225;
	public static final int GIGANTIC = 				226;
	public static final int POWER_GRIP = 			228;
	public static final int TOMAHAWK = 				229;
	public static final int DESPERADO = 			230;
	public static final int TITAN_RISING = 			231;
	// passive
	public static final int DEMOLITION = 			247;

	// Fencer - Active
	public static final int JUDGEMENT = 			235;
	public static final int PHANTOM = 				236;
	public static final int PANTERA = 				237;
	public static final int HELLFIRE = 				238;
	public static final int BLADE = 				239;

	public static final int AVENGER = 				244;

	public static final int HEUKSA = 				248;

	// Fencer - Passive
	public static final int INFINITY_A = 			550;
	public static final int INFINITY_B = 			551;
	public static final int INFINITY_D = 			552;
	public static final int DAMASCUS = 				553;
	public static final int PARADOX = 				554;
	public static final int GROUS = 				555;
	public static final int RAGE = 					556;
	public static final int PHANTOM_R = 			557;
	public static final int PHANTOM_D = 			558;
	public static final int FLAME = 				559;
	public static final int INFINITY_BL = 			560;
	public static final int SURVIVE = 				561;
	public static final int PANTERA_S = 			562;

	// Status
	public static final int STATUS_DRAGONPERL = 	999;
	public static final int STATUS_BEGIN = 			1000;
	public static final int STATUS_BRAVE = 			1000;

	public static final int STATUS_RanKing = 		6420;

	public static final int STATUS_FANTASY_FOOD = 	1030; // STATUS_Precious food
	public static final int STATUS_마녀마력회복제 = 	1031; // STATUS_Witch Magic Restoration Potion
	public static final int STATUS_안전모드 = 			1032; // STATUS_safe mode
	public static final int STATUS_힘업6 = 			1033; // STATUS_Power up 6
	public static final int STATUS_힘업7 = 			1034; // STATUS_Strengup 7
	public static final int STATUS_덱업6 = 			1035; // STATUS_Deckup 6
	public static final int STATUS_덱업7 = 			1036; // STATUS_Deckup 7

	public static final int STATUS_AUTOROOT = 		76261; // for master autorouting instructions
	public static final int STATUS_MENT = 			7626; // Added Master CommentsCommand
	public static final int STATUS_HASTE = 			1001;
	public static final int STATUS_BLUE_POTION = 	1002;
	public static final int STATUS_UNDERWATER_BREATH = 1003;
	public static final int STATUS_WISDOM_POTION = 	1004;
	public static final int SAEL = 					5959; // Sael Buff
	public static final int CRAY = 					5858; // Cray Buff
	public static final int GUNTHERS_ADVICE = 		6060; // Gunther's advice
	public static final int DRAGON_GROWTH_BUFF = 	6072; // Dragon's Growth Buff
	public static final int CLAN_BLESS_BUFF = 		6073;
	public static final int HALPAS_FOLLOWER_BUFF =	6062; // Halfas' Follower Buff

	// attendance
	public static final int ATTENDANCE_CHECK = 		6000;
	public static final int ATTENDANCE_CHECK_DELAY = 5000;

	/** Magic eye buff for 4 units */
	public static final int ANTA_MAAN = 			7671; // Earth Dragon's Magic Eye
	public static final int FAFU_MAAN = 			7672; // water dragon mystic eye
	public static final int VALA_MAAN = 			7673; // fire dragon mystic eye
	public static final int LIND_MAAN = 			7674; // wind dragon mystic eye
	public static final int BIRTH_MAAN = 			7675; // birth mystic eye
	public static final int SHAPE_MAAN = 			7676; // shape mystic eye
	public static final int LIFE_MAAN = 			7677; // of life mystic eye
	public static final int HALPAS_MAAN = 			7678; // black dragon mystic eye
	public static final int NEVER_MAAN = 			7679; // absolute mystic eye

	/** Antharas/Fafurion Mystic Eye Buff */
	public static final int DRAGONBLOOD_A = 		55001;
	public static final int DRAGONBLOOD_P = 		55002;
	public static final int DRAGONBLOOD_L = 		55003;
	public static final int DRAGONRAID_BUFF = 		55005;

	public static final int PCROOM_BUFF = 			777888; // PC room_buff

	public static final int 강화버프_활력 = 			56000; // Enhanced Buff_Vitality
	public static final int 강화버프_공격 = 			56001; // Enhanced Buff_Attack
	public static final int 강화버프_방어 = 			56002; // Enhanced Buff_Defense
	public static final int 강화버프_마법 = 			56003; // Enhanced Buff_Magic
	public static final int 강화버프_스턴 = 			56004; // Enhanced Buff_Stun
	public static final int 강화버프_홀드 = 			56005; // Enhanced Buff_Hold

	public static final int 강화버프_완력 = 			56006;
	public static final int 강화버프_민첩 = 			56007;
	public static final int 강화버프_지식 = 			56008;
	public static final int 강화버프_지혜 = 			56009;

	public static final int STATUS_POISON = 		1006;
	public static final int STATUS_POISON_SILENCE = 1007;
	public static final int STATUS_POISON_PARALYZING = 1008;
	public static final int STATUS_POISON_PARALYZED = 1009;
	public static final int STATUS_CURSE_PARALYZING = 1010;
	public static final int STATUS_CURSE_PARALYZED = 1011;
	public static final int STATUS_FLOATING_EYE = 	1012;
	public static final int STATUS_HOLY_WATER = 	1013;
	public static final int STATUS_HOLY_MITHRIL_POWDER = 1014;
	public static final int STATUS_HOLY_WATER_OF_EVA = 1015;
	public static final int STATUS_ELFBRAVE = 		1016;
	public static final int STATUS_CANCLEEND = 		1016;
	public static final int STATUS_CURSE_BARLOG = 	1017;
	public static final int STATUS_CURSE_YAHEE = 	1018;
	public static final int STATUS_PET_FOOD = 		1019;
	public static final int STATUS_PINK_NAME = 		1020;
	public static final int STATUS_TIKAL_BOSSJOIN = 1021;
	public static final int STATUS_TIKAL_BOSSDIE = 	1022;
	public static final int STATUS_CHAT_PROHIBITED = 1023;
	public static final int STATUS_COMA_3 = 		1024;
	public static final int STATUS_COMA_5 = 		1025;
	public static final int FEATHER_BUFF_A = 		1026;
	public static final int FEATHER_BUFF_B = 		1027;
	public static final int FEATHER_BUFF_C = 		1028;
	public static final int FEATHER_BUFF_D = 		1029;
	// public static final int STATUS_END = 1023;
	public static final int GMSTATUS_BEGIN = 		2000;
	public static final int GMSTATUS_INVISIBLE = 	2000;
	public static final int GMSTATUS_HPBAR = 		2001;
	public static final int GMSTATUS_SHOWTRAPS = 	2002;
	public static final int GMSTATUS_END = 			2002;
	public static final int COOKING_NOW = 			2999;
	public static final int COOKING_BEGIN = 		3000;
	/** 1차요리 효과 (노멀) */
	public static final int COOKING_1_0_N = 		3000;
	public static final int COOKING_1_1_N = 		3001;
	public static final int COOKING_1_2_N = 		3002;
	public static final int COOKING_1_3_N = 		3003;
	public static final int COOKING_1_4_N = 		3004;
	public static final int COOKING_1_5_N = 		3005;
	public static final int COOKING_1_6_N = 		3006;
	public static final int COOKING_1_7_N = 		3007;

	/** 2차요리 효과 (노멀) */
	public static final int COOKING_1_8_N = 		3008;
	public static final int COOKING_1_9_N = 		3009;
	public static final int COOKING_1_10_N = 		3010;
	public static final int COOKING_1_11_N = 		3011;
	public static final int COOKING_1_12_N = 		3012;
	public static final int COOKING_1_13_N = 		3013;
	public static final int COOKING_1_14_N = 		3014;
	public static final int COOKING_1_15_N = 		3015;

	/** 3차요리 효과 (노멀) */
	public static final int COOKING_1_16_N = 		3016;
	public static final int COOKING_1_17_N = 		3017;
	public static final int COOKING_1_18_N = 		3018;
	public static final int COOKING_1_19_N = 		3019;
	public static final int COOKING_1_20_N = 		3020;
	public static final int COOKING_1_21_N = 		3021;
	public static final int COOKING_1_22_N = 		3022;
	public static final int COOKING_1_23_N = 		3023;

	/**1st cooking effect (illusion) */
	public static final int COOKING_1_0_S = 		3050;
	public static final int COOKING_1_1_S = 		3051;
	public static final int COOKING_1_2_S = 		3052;
	public static final int COOKING_1_3_S = 		3053;
	public static final int COOKING_1_4_S = 		3054;
	public static final int COOKING_1_5_S = 		3055;
	public static final int COOKING_1_6_S = 		3056;
	public static final int COOKING_1_7_S = 		3057;

	/** Secondary Cooking Effect (Illusion)*/
	public static final int COOKING_1_8_S = 		3058;
	public static final int COOKING_1_9_S = 		3059;
	public static final int COOKING_1_10_S = 		3060;
	public static final int COOKING_1_11_S = 		3061;
	public static final int COOKING_1_12_S = 		3062;
	public static final int COOKING_1_13_S = 		3063;
	public static final int COOKING_1_14_S = 		3064;
	public static final int COOKING_1_15_S = 		3065;

	/** 3rd cooking effect (illusion) */
	public static final int COOKING_1_16_S = 		3066;
	public static final int COOKING_1_17_S = 		3067;
	public static final int COOKING_1_18_S = 		3068;
	public static final int COOKING_1_19_S = 		3069;
	public static final int COOKING_1_20_S = 		3070;
	public static final int COOKING_1_21_S = 		3071;
	public static final int COOKING_1_22_S = 		3072;
	public static final int COOKING_1_23_S = 		3073;

	public static final int SPECIAL_COOKING = 		3074;
	public static final int COOKING_END = 			3075;
	public static final int SPECIAL_COOKING2 = 		3076;

	public static final int COOKING_NEW_한우 = 		3077;
	public static final int COOKING_NEW_연어 = 		3078;
	public static final int COOKING_NEW_칠면조 = 		3079;
	public static final int COOKING_NEW_닭고기 = 		3080;
	public static final int 메티스축복주문서 = 			3081; // Metis Blessing Scroll?
	public static final int METIS_CUISINE = 		3082;
	public static final int METIS_SOUP = 			3083;
	public static final int SPICY_RAMEN = 			3084;
	public static final int PSY_COOL_DRINK = 		3085;
	public static final int WIND_SHACKLE = 			3086; //mobshackle
	public static final int COOKING_NEW_탐한우 = 		3087;
	public static final int COOKING_NEW_탐연어 = 		3088;
	public static final int COOKING_NEW_탐칠면조 = 	3089;
	public static final int COOKING_NEW_탐닭고기 = 	3090;
	public static final int ICE_LANCE = 			3091;
	public static final int STATUS_FREEZE = 		10071;
	public static final int CURSE_PARALYZE2 = 		10101;

	public static final int STATUS_SPOT1 = 			20072;
	public static final int STATUS_SPOT2 = 			20073;
	public static final int STATUS_SPOT3 = 			20074;
	public static final int STATUS_SPOT4 = 			20174;

	public static final int STATUS_IGNITION = 		20075;
	public static final int STATUS_QUAKE = 			20076;
	public static final int STATUS_SHOCK = 			20077;
	public static final int STATUS_BALANCE = 		20078;
	public static final int 린드가호딜레이 = 			8178; // Linda Hodley
	public static final int 할파스가호lv1 = 			100000;
	public static final int STATUS_FRUIT = 			20079;
	public static final int STATUS_OVERLAP = 		20080;
	public static final int EXP_POTION = 			20081;
	public static final int EXP_POTION_cash = 		30080;
	public static final int EXP_POTION2 = 			9278;
	public static final int EXP_POTION3 = 			9279;
	public static final int DRAGON_EME_1 = 			7785;
	public static final int DRAGON_EME_2 = 			7786;
	public static final int DRAGON_PUPLE = 			7787;
	public static final int DRAGON_TOPAZ = 			7788;
	public static final int Tam_Fruit1 = 			7791;
	public static final int Tam_Fruit2 = 			7792;
	public static final int Tam_Fruit3 = 			7793;
    public static final int Tam_Fruit4 = 			7794;
	public static final int Tam_Fruit5 = 			7795;
	public static final int STATUS_BLUE_POTION2 = 	20082;
	public static final int STATUS_DESHOCK = 		20083;
	public static final int STATUS_CUBE = 			20084;
	public static final int STATUS_CASHSCROLL = 	7893;
	public static final int STATUS_CASHSCROLL2 = 	7894;
	public static final int STATUS_CASHSCROLL3 = 	7895;
	public static final int STATUS_커츠투사 = 			16551;
	public static final int STATUS_커츠현자 = 			16553;
	public static final int STATUS_커츠명궁 = 			16552;
	public static final int STATUS_시원한얼음조각 = 	84850;

	public static final int STATUS_BLUE_POTION3 = 	22004;

	public static final int MOB_SLOW_18 = 			30000; // 슬로우 18번모션
	public static final int MOB_SLOW_1 = 			30001; // 슬로우 1번모션
	public static final int MOB_CURSEPARALYZ_19 = 	30002; // 커스 19번모션
	public static final int MOB_COCA = 				30003; // 코카트리스 얼리기공격
	public static final int MOB_BASILL = 			30004; // 바실리스크 얼리기에볼
	public static final int MOB_RANGESTUN_19 = 		30005; // 범위스턴 19번모션
	public static final int MOB_RANGESTUN_18 = 		30006; // 범위스턴 18번모션
	public static final int MOB_CURSEPARALYZ_18 = 	30007; // 커스 18번모션
	public static final int MOB_DISEASE_30 = 		30008; // 디지즈 30번모션
	public static final int MOB_WEAKNESS_1 = 		30009; // 위크니스 1번모션
	public static final int MOB_DISEASE_1 = 		30079; // 디지즈 1번모션
	public static final int MOB_SHOCKSTUN_30 = 		30081; // 쇼크스턴 30번모션
	public static final int MOB_WINDSHACKLE_1 = 	30084; // 윈드셰클 1번모션

	public static final int ANTA_SKILL_1 = 			10188; // 세이 리라프
	public static final int ANTA_SKILL_2 = 			10189; // 티 세토르
	public static final int ANTA_SKILL_3 = 			10190; // 뮤즈 삼
	public static final int ANTA_SKILL_4 = 			10191;// 너츠 삼
	public static final int ANTA_SKILL_5 = 			10192; // 티프 삼
	public static final int ANTA_SKILL_6 = 			10193; // 리라프
	public static final int ANTA_SKILL_7 = 			10194; // 켄 로우
	public static final int ANTA_SKILL_8 = 			10195; // 티기르
	public static final int ANTA_SKILL_9 = 			10196; // 켄 티기르
	public static final int ANTA_SKILL_10 = 		10197; // 루오 타
	public static final int ANTA_SKILL_11 = 		10198; // 케 네시
	public static final int ANTA_SKILL_12 = 		10199; // 뮤즈 심
	public static final int ANTA_SKILL_13 = 		10200; // 너츠 심
	public static final int ANTA_SKILL_14 = 		10201; // 티프 심

	public static final int PAPOO_SKILL = 			10502; // 리오타! 누스건 카푸
	public static final int PAPOO_SKILL1 = 			10507; // 리오타! 피로이나
	public static final int PAPOO_SKILL2 = 			10508; // 리오타! 라나 폰폰
	public static final int PAPOO_SKILL3 = 			10509; // 리오나! 레오 폰폰
	public static final int PAPOO_SKILL4 = 			10510; // 리오타!테나 론디르
	public static final int PAPOO_SKILL5 = 			10512; // 리오타!네나 론디르
	public static final int PAPOO_SKILL6 = 			10514; // 리오타!라나 오이므
	public static final int PAPOO_SKILL7 = 			10515; // 리오타! 레포 오이므 //7870한테만적용
	public static final int PAPOO_SKILL8 = 			10516; // 리오타! 테나 웨인라크
	public static final int PAPOO_SKILL9 = 			10519; // 리오타! 네나 우누스
	public static final int PAPOO_SKILL10 = 		10520; // 리오타! 오니즈 웨인라크
	public static final int PAPOO_SKILL11 = 		10521; // 리오타! 오니즈 쿠스온 웨인라크

	public static final int BEGINNER_HELP_CLICK_DAILY = 10522;
	public static final int DUNGEON_DELAY = 		10523;
	public static final int STR_SHAVED_ICE = 		10524;
	public static final int DEX_SHAVED_ICE = 		10525;
	public static final int INT_SHAVED_ICE = 		10526;

	public static final int DANTES_BUFF = 			10527;
	public static final int ENERGY_OF_BLACK = 		10528;
	public static final int LEVEL_UP_BONUS = 		10529;
	public static final int ZERO_REDUC = 			10535;

	public static final int STATUS_WISDOM_POTION2 = 10530;
	public static final int LORD_BUFF = 			10534;

	public static final int PAPOO_구슬_딜레이 = 		90000;
	public static final int HAVEN_SKILL_PREP = 		4065;

	public static final int 가입신청딜레이 = 			76271; // Subscription application delay
	public static final int CHAT_PARTY_BUFF = 		76272;

	public static final int REPORT_DELAY = 			76273;
	public static final int PARTY_INVITE_DELAY = 	76274;

	public static final int RANKING_BUFF_1 = 		80000;
	public static final int RANKING_BUFF_2 = 		80001;
	public static final int RANKING_BUFF_3 = 		80002;
	public static final int RANKING_BUFF_4 = 		80003;
	public static final int RANKING_BUFF_5 = 		80007;
	public static final int RANKING_BUFF_6 = 		80008;
	public static final int RANKING_BUFF_7 = 		80009;
	public static final int RANKING_BUFF_8 = 		80010;
	public static final int RANKING_BUFF_9 = 		80011;
	public static final int RANKING_BUFF_10 = 		80012;
	public static final int RANKING_BUFF_11 = 		80013;
	public static final int SEAL_BUFF = 			80014;
	public static final int DRAGON_BLESS_STAGE1 = 	80015;
	public static final int DRAGON_BLESS_STAGE2 = 	80016;
	public static final int DRAGON_BLESS_STAGE3 = 	80017;
	public static final int KYULJUN_CASHSCROLL1 = 	80018; // Decisive Order
	public static final int PC_ROOM = 				80019;
	public static final int REVENGE_TIME = 			80020;
	public static final int AINHASAD_GAHO = 		80021;

	public static final int NORMAL_PROTECTION = 	80004;
	public static final int COMBO_BUFF = 			80006;

	public static final int DESPERADO_ABSOLUTE = 	515;

	// SAFE POLY and dodo pack
	public static final int POLY_SAFE = 			15846;
	public static final int 천하장사버프 = 				15410; // Cheonhajangsa Buff
	public static final int 순간이동지배 = 				15847; // teleport control
	//public static final int POLY_SAFE = 15846;
	public static final int VALA_BUFF = 			50000; // Valakas Blood Buff
	public static final int CHAINSWORD1 = 			50002;
	public static final int CHAINSWORD2 = 			50003;
	public static final int CHAINSWORD3 = 			50004;
	public static final int GIRAN_PRISON_TIME = 	50005;

	/** Pet Skills */
	public static final int Fighting = 				5000;

	/** leaves of growth */
	/** 3919 30% 3920 50% 3921 100% */
	public static final int GrowthFoliage = 		3919;

	/** Pitbull's Jagerbam (fighting spirit) */
	public static final int YeagerNight = 			3922;

	public static final int DogBlood = 				4001;

	/** dungeon type debuff */
	public static final int DungeonPoison = 		8200;
	public static final int DungeonFlare = 			8201;

	/** dragon weapon series */
	public static final int 발라카스의장검 = 			18982; // Valakas Prefect's Longsword
	public static final int 발라카스의양손검 = 			18986; // Valakas' two-handed sword
	public static final int 파푸리온의활 = 				18998; // Papurion's Bow
	public static final int 파푸리온의이도류 = 			19002; // Papurion's Two Swords
	public static final int 안타라스의지팡이 = 			19010; // Staff of Antharas
	public static final int 안타라스의도끼 = 			19006; // Ax of Antharas
	public static final int 린드비오르의키링크 = 			18994; // Lindvior's Key Link
	public static final int 린드비오르의체인소드 = 		18990; // Lindvior's Chainsword

	public static final int HALPAS_FAITH_DELAY = 	4454;
	public static final int HALPAS_FAITH_PVP_REDUC = 4453;
	public static final int HALPAS_FAITH_STANDBY = 	4452;

	public static final int MISO_1_ATT = 			8135; // Misophia Attack
	public static final int MISO_2_DEF = 			8136; // Misophia Defense
	public static final int MISO_3_GRW = 			8137; // Misopia Growth

}

