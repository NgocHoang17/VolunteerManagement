package vn.edu.volunteer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import vn.edu.volunteer.service.AdminInitializationService;

@Component
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private AdminInitializationService adminInitializationService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        
        transactionTemplate.execute(status -> {
            try {
                adminInitializationService.initializeAdminUser();
                return null;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }
} 