package com.gox.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
public class ServiceTransactionAspect {

    private final PlatformTransactionManager txManager;

    public ServiceTransactionAspect(PlatformTransactionManager txManager) {
        this.txManager = txManager;
    }

    @Around("execution(* com.gox.domain.service.*.*(..)) || " +
            "execution(* com.gox.domain.service.*Factory.*(..))")
    public Object aroundTransactionalServices(ProceedingJoinPoint pjp) throws Throwable {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(pjp.getSignature().toShortString());
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = txManager.getTransaction(def);
        try {
            Object result = pjp.proceed();
            txManager.commit(status);
            return result;
        } catch (Throwable ex) {
            txManager.rollback(status);
            throw ex;
        }
    }
}
