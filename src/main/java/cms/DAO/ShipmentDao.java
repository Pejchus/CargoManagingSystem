package cms.DAO;


import cms.model.Regularuser;
import org.springframework.stereotype.Repository;
import cms.model.Shipment;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Repository
public class ShipmentDao extends baseDao<Shipment>{

    @PersistenceContext
    private EntityManager em;

    protected ShipmentDao() {
        super(Shipment.class);
    }

    public Shipment find(Integer id){
        Objects.requireNonNull(id);
        return em.find(Shipment.class, id);
    }
    public List<Shipment> findAll(){
        return em.createQuery("SELECT s FROM Shipment s WHERE s.id IS NOT NULL", Shipment.class).getResultList();
    }

    public List<Shipment> findByStatus(String status){
        return em.createQuery("SELECT s FROM Shipment s WHERE s.status = :status", Shipment.class).getResultList();
    }
    public boolean setStatus(String status, Integer id){
        Shipment s = find(id);
        s.setStatus(status);
        try {
            em.persist(s);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteShipment(Integer id){
        Shipment s = find(id);
        em.getTransaction();
        try {
            em.remove(s);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void createShipment(String cargo,  String destination){
        Shipment s = new Shipment();
        s.setStatus("Pending");
        s.setDestination(destination);
        em.getTransaction();
        em.persist(s);
        em.getTransaction().commit();
    }

}
