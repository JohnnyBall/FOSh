/*≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡
   PROJECT:                   FOSh-Salinity-Module
   AUTHOR:                    Damien Christopher Rembold
   DATE:                      2014-10-08
   FILENAME:                  PROTOCOL.java
   PURPOSE:                   PROTOCOL container class
   VERSION:                   20141008-1824
≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡*/
package FOShSalinityModule;

/*  This is a package-scope class that contains all of the standardized 
communications commands for the FOSh project.  Functionality can be achieved
by importing this class into your FOSh project and referencing the commands
directly by using PROTOCOL.<command> syntax.  */

//=[BEGIN IMPORTS]==============================================================
//=[END IMPORTS]================================================================

//=[BEGIN CLASS PROTOCOL]=======================================================
public class PROTOCOL
{
    public static final String GREET            = "Hello.";
    public static final String GREET_RESPOND    = "Good day, fine sir!  Who might you be?";
    public static final String DBM_ALIVE        = "Why, madame, I am the database module!";
    public static final String PHM_ALIVE        = "Why, madame, I am the pH module!";
    public static final String SAM_ALIVE        = "Why, madame, I am the salinity module!";
    public static final String TEM_ALIVE        = "Woman, I am the temperature module, and you shall treat me with utmost respect!";
    public static final String REQ_BIOME_LIST   = "Say, mein herr database, might I have a copy of the biome list?";
    public static final String SEND_BIOME_LIST  = "Surely so, mon cheri.  The biome list is:";
    public static final String REQ_BIOME        = "I say database!  Might I have a copy of this specific biome?";
    public static final String SEND_BIOME       = "Most certainly, fine lass!  That particular biome is:";
    public static final String REQ_PH_LIMITS    = "My dear, would you be so kind as to inform me of the pH limits?";
    public static final String SEND_PH_LIMITS   = "But of course, stately sir!  The pH limits are:";
    public static final String REQ_SAL_LIMITS   = "My dear, would you be so kind as to inform me of the salinity limits?";
    public static final String SEND_SAL_LIMITS  = "But of course, stout sir!  The salinity limits are:";
    public static final String REQ_TEMP_LIMITS  = "Lowly wench!  Deliver unto me the temperature limits lest I thrash thee about the head and shoulders!";
    public static final String SEND_TEMP_LIMITS = "At once dear sir!  Please accept these temperature limits:";
    public static final String REQ_PH           = "I say pH module!  Might I inquire as to the current ph?";
    public static final String SEND_PH          = "But of course, my only love.  The current pH is:";
    public static final String REQ_SAL          = "I say salinity module!  Might I inquire as to the current salinity?";
    public static final String SEND_SAL         = "But of course, my tasty cabbage.  The current salinity is:";
    public static final String REQ_TEMP         = "I say temperature module!  Might I inquire as to the current temperature?";
    public static final String SEND_TEMP        = "Scandalous wench!  Take this temperature and leave my sight!";
    public static final String SEND_PH_PROB     = "My lady, I have determined that there is a problem with the current pH!";
    public static final String ACK_PH_PROB      = "Oh noes!  We must act upon this injustice post-haste!";
    public static final String SEND_SAL_PROB    = "Dearest turnip, I have determined that there is a problem with the current salinity!";
    public static final String ACK_SAL_PROB     = "My word!  We shall act at once to thwart this abhorrid salinity problem!";
    public static final String SEND_TEMP_PROB   = "Barbarous heifer!  Something is astray with my temperature and the fault is surely yours!";
    public static final String ACK_TEMP_PROB    = "Of course sir!  I shall address this foul temperature problem at once!";
    
    PROTOCOL()
    {
        /*  This class exists only to provide public static Strings in order
        to standardize communications protocol.  Individual classes can be 
        given access to a PROTOCOL class, and subsequently, protocol commands
        can be utilized using the PROTOCOL.<command> syntax.  
            Alternatively, "global" addressing can be used, e.g.:
        FOShSalinityModule.PROTOCOL.<command>.  This will work so long as the 
        class remains associated with the class loaded, and thus maintains
        strongly reachable status.  In more complex applications, it is possible
        for the class to be GCed, making class ownership more reliable in those
        instances.  */
    }
}
//=[END CLASS PROTOCOL]=========================================================

//≡[EOF]≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡