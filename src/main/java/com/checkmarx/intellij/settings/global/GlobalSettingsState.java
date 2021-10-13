package com.checkmarx.intellij.settings.global;

import com.checkmarx.intellij.Bundle;
import com.checkmarx.intellij.Constants;
import com.checkmarx.intellij.Resource;
import com.checkmarx.intellij.Utils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * State object for not sensitive global settings for the plugin.
 */
@Getter
@Setter
@EqualsAndHashCode
@State(name = Constants.GLOBAL_SETTINGS_STATE_NAME, storages = @Storage(Constants.GLOBAL_SETTINGS_STATE_FILE))
public class GlobalSettingsState implements PersistentStateComponent<GlobalSettingsState> {

    private static final Logger LOGGER = Utils.getLogger(GlobalSettingsState.class);

    public static GlobalSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(GlobalSettingsState.class);
    }

    @NotNull
    private String serverURL = "";

    private boolean useAuthURL;

    @NotNull
    private String authURL = "";

    @NotNull
    private String tenantName = "";

    @NotNull
    private String additionalParameters = "";

    @Override
    public @Nullable GlobalSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull GlobalSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public void apply(@NotNull GlobalSettingsState state) {
        String msg = validate(state);
        if (msg != null) {
            LOGGER.warn(msg);
        }
        loadState(state);
    }

    public boolean isValid() {
        return validate(this) == null;
    }

    private static String validate(@NotNull GlobalSettingsState state) {

        if (StringUtils.isBlank(state.getServerURL())) {
            return Bundle.missingFieldMessage(Resource.SERVER_URL);
        }
        if (state.isUseAuthURL() && StringUtils.isBlank(state.getAuthURL())) {
            return Bundle.missingFieldMessage(Resource.AUTH_URL);
        }
        if (StringUtils.isBlank(state.getTenantName())) {
            return Bundle.missingFieldMessage(Resource.TENANT_NAME);
        }
        return null;
    }
}

