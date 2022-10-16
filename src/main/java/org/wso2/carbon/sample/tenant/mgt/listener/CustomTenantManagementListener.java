package org.wso2.carbon.sample.tenant.mgt.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.identity.core.model.IdentityEventListenerConfig;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.stratos.common.beans.TenantInfoBean;
import org.wso2.carbon.stratos.common.exception.StratosException;
import org.wso2.carbon.stratos.common.listeners.TenantMgtListener;

public class CustomTenantManagementListener implements TenantMgtListener {

    private static final int EXEC_ORDER = 41;
    private static final Log log = LogFactory.getLog(CustomTenantManagementListener.class);

    /**
     * Add the default email templates under the registry: /_system/config/identity/config/emailTemplate
     * as per the old implementation even when the old org.wso2.carbon.identity.mgt.IdentityMgtEventListener
     * is disabled.
     * Refer to the default logic in org.wso2.carbon.identity.mgt.listener.TenantManagementListener
     *
     * @param tenantInfo Information about the newly created tenant
     */
    @Override
    public void onTenantCreate(TenantInfoBean tenantInfo) throws StratosException {
        try {
            PrivilegedCarbonContext.getThreadLocalCarbonContext().startTenantFlow();
            PrivilegedCarbonContext.getThreadLocalCarbonContext().setTenantDomain(tenantInfo.getTenantDomain());
            PrivilegedCarbonContext.getThreadLocalCarbonContext().setTenantId(tenantInfo.getTenantId());

            IdentityEventListenerConfig identityEventListenerConfig = IdentityUtil.readEventListenerProperty
                    (org.wso2.carbon.user.core.listener.UserOperationEventListener.class.getName(), org.wso2.carbon.identity.mgt.IdentityMgtEventListener.class.getName());

            if (identityEventListenerConfig != null) {
                org.wso2.carbon.identity.mgt.config.Config emailConfigFile = org.wso2.carbon.identity.mgt.config.ConfigBuilder.getInstance().loadEmailConfigFile();
                org.wso2.carbon.identity.mgt.config.EmailNotificationConfig emailNotificationConfig = new org.wso2.carbon.identity.mgt.config.EmailNotificationConfig();
                emailNotificationConfig.setProperties(emailConfigFile.getProperties());
                org.wso2.carbon.identity.mgt.config.ConfigBuilder.getInstance().saveConfiguration(org.wso2.carbon.identity.mgt.config.StorageType.REGISTRY, tenantInfo.getTenantId(),
                        emailNotificationConfig);
            }
        } catch (org.wso2.carbon.identity.mgt.IdentityMgtConfigException e) {
            log.error("Error occurred while saving default email templates in registry for tenant: "
                    + tenantInfo.getTenantDomain());
        } finally {
            PrivilegedCarbonContext.getThreadLocalCarbonContext().endTenantFlow();
        }
    }

    @Override
    public void onTenantUpdate(TenantInfoBean tenantInfoBean) throws StratosException {

    }

    @Override
    public void onTenantDelete(int i) {

    }

    @Override
    public void onTenantRename(int i, String s, String s1) throws StratosException {

    }

    @Override
    public void onTenantInitialActivation(int i) throws StratosException {

    }

    @Override
    public void onTenantActivation(int i) throws StratosException {

    }

    @Override
    public void onTenantDeactivation(int i) throws StratosException {

    }

    @Override
    public void onSubscriptionPlanChange(int i, String s, String s1) throws StratosException {

    }

    @Override
    public int getListenerOrder() {
        return EXEC_ORDER;
    }

    @Override
    public void onPreDelete(int i) throws StratosException {

    }
}
