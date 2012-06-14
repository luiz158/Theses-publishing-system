package cz.muni.fi.pv243.tps.ejb;

import javax.transaction.UserTransaction;

public class TransactionProxy implements TransactionProxyable {
    private TransactionProxyable proxyable;

    private UserTransaction transaction;

    public TransactionProxy(UserTransaction transaction, TransactionProxyable proxyable) {
        this.proxyable = proxyable;
        this.transaction = transaction;
    }

    @Override
    public Object execute() {
        Object result;
        try {
            transaction.begin();
            result = proxyable.execute();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
