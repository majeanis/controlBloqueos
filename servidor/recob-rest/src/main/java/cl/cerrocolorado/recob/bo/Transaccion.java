package cl.cerrocolorado.recob.bo;

import cl.cerrocolorado.recob.bo.utils.BeansFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 * @author mauricio.camara
 */
public class Transaccion
{
    private final String nombre;
    private final PlatformTransactionManager txManager;
    private TransactionStatus txStatus;
    
    public Transaccion(String nombre)
    {
        this.nombre = nombre;
        this.txManager = BeansFactory.getTransactionManager();
    }

    public Transaccion()
    {
        this.nombre = "";
        this.txManager = BeansFactory.getTransactionManager();
    }

    public String getNombre()
    {
        return nombre;
    }
    
    public TransactionStatus getTransactionStatus()
    {
        return txStatus;
    }

    public void begin(int propagationBehavior)
    {
        DefaultTransactionDefinition txDef = new DefaultTransactionDefinition();

        if(!nombre.isEmpty())
        {
            txDef.setName(nombre);
        }

        txDef.setPropagationBehavior(propagationBehavior);
        txStatus = txManager.getTransaction(txDef);
    }

    public void begin()
    {
        begin(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    public void commit()
    {
        txManager.commit(txStatus);
    }
    
    public void rollback()
    {
        txManager.rollback(txStatus);
    }
}
