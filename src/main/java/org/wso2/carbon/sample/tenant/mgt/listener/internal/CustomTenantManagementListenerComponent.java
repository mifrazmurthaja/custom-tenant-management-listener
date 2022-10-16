package org.wso2.carbon.sample.tenant.mgt.listener.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.stratos.common.listeners.TenantMgtListener;

import javax.jws.soap.SOAPBinding;

/**
 * @scr.component name="sample.user.operation.event.listener.dscomponent" immediate=true
 */

public class CustomTenantManagementListenerComponent {

    private static Log log = LogFactory.getLog(CustomTenantManagementListenerComponent.class);

    protected void activate(ComponentContext context) {

        org.wso2.carbon.sample.tenant.mgt.listener.CustomTenantManagementListener customTenantManagementListener = new
                org.wso2.carbon.sample.tenant.mgt.listener.CustomTenantManagementListener();
        //register the custom listener as an OSGI service.
        context.getBundleContext().registerService(
                TenantMgtListener.class.getName(), customTenantManagementListener, null);

        log.info("CustomTenantManagementListener bundle activated successfully..");
    }

    protected void deactivate(ComponentContext context) {
        if (log.isDebugEnabled()) {
            log.debug("CustomTenantManagementListener is deactivated ");
        }
    }

}
