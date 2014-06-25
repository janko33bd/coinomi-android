package com.coinomi.wallet;

import android.content.SharedPreferences;
import android.text.format.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Giannis Dzegoutanis
 * @author Andreas Schildbach
 */
public class Configuration {

    public final int lastVersionCode;

    private final SharedPreferences prefs;

    public static final String PREFS_KEY_BTC_PRECISION = "btc_precision";
    public static final String PREFS_KEY_CONNECTIVITY_NOTIFICATION = "connectivity_notification";
    public static final String PREFS_KEY_EXCHANGE_CURRENCY = "exchange_currency";
    public static final String PREFS_KEY_TRUSTED_PEER = "trusted_peer";
    public static final String PREFS_KEY_TRUSTED_PEER_ONLY = "trusted_peer_only";
    public static final String PREFS_KEY_DISCLAIMER = "disclaimer";
    public static final String PREFS_KEY_SELECTED_ADDRESS = "selected_address";
    private static final String PREFS_KEY_LABS_QR_PAYMENT_REQUEST = "labs_qr_payment_request";

    private static final String PREFS_KEY_LAST_VERSION = "last_version";
    private static final String PREFS_KEY_LAST_USED = "last_used";
    private static final String PREFS_KEY_BEST_CHAIN_HEIGHT_EVER = "best_chain_height_ever";
    private static final String PREFS_KEY_CACHED_EXCHANGE_CURRENCY = "cached_exchange_currency";
    private static final String PREFS_KEY_CACHED_EXCHANGE_RATE = "cached_exchange_rate";
    private static final String PREFS_KEY_LAST_EXCHANGE_DIRECTION = "last_exchange_direction";
    private static final String PREFS_KEY_CHANGE_LOG_VERSION = "change_log_version";
    public static final String PREFS_KEY_REMIND_BACKUP = "remind_backup";

    private static final int PREFS_DEFAULT_BTC_SHIFT = 3;
    private static final int PREFS_DEFAULT_BTC_PRECISION = 2;

    private static final Logger log = LoggerFactory.getLogger(Configuration.class);

    public Configuration(final SharedPreferences prefs)
    {
        this.prefs = prefs;

        this.lastVersionCode = prefs.getInt(PREFS_KEY_LAST_VERSION, 0);
    }

    public void updateLastVersionCode(final int currentVersionCode)
    {
        prefs.edit().putInt(PREFS_KEY_LAST_VERSION, currentVersionCode).commit();

        if (currentVersionCode > lastVersionCode)
            log.info("detected app upgrade: " + lastVersionCode + " -> " + currentVersionCode);
        else if (currentVersionCode < lastVersionCode)
            log.warn("detected app downgrade: " + lastVersionCode + " -> " + currentVersionCode);
    }

    public long getLastUsedAgo()
    {
        final long now = System.currentTimeMillis();

        return now - prefs.getLong(PREFS_KEY_LAST_USED, 0);
    }

    public void touchLastUsed()
    {
        final long prefsLastUsed = prefs.getLong(PREFS_KEY_LAST_USED, 0);
        final long now = System.currentTimeMillis();
        prefs.edit().putLong(PREFS_KEY_LAST_USED, now).commit();

        log.info("just being used - last used {} minutes ago", (now - prefsLastUsed) / DateUtils.MINUTE_IN_MILLIS);
    }

    public void setBestChainHeightEver(final int bestChainHeightEver)
    {
        prefs.edit().putInt(PREFS_KEY_BEST_CHAIN_HEIGHT_EVER, bestChainHeightEver).commit();
    }
}
