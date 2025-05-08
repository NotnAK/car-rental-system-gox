package com.gox.config;

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

    /**
     * Оборачиваем все методы PhotoService в транзакцию.
     * Можно расширить pointcut под другие сервисы по аналогии.
     */
    @Around("execution(* com.gox.domain.service.PhotoService.*(..))")
    public Object aroundPhotoService(ProceedingJoinPoint pjp) throws Throwable {
        // Определяем поведение транзакции (readOnly=false, изолированность по умолчанию)
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
