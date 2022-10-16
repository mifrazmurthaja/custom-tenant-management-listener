# custom-tenant-management-listener
A custom tenant management event listener to create the old (deprecated) email templates for new tenants, even when the old (deprecated) org.wso2.carbon.identity.mgt.IdentityMgtEventListener is disabled (should not be enabled).
1) Stop the server if it is already running.
2) Build the project using following command ```mvn clean install```.
3) Copy the jar file org.wso2.carbon.sample.tenant.mgt.listener-1.0.0.jar from the target directory to <IS_HOME>/repository/components/dropins folder.
4) Start the server.
5) Create a new tenant and observe whether the email templates are created in the desired registry path.