package cms.service;

import cms.DAO.ArchiveDao;
import cms.DAO.RegularuserDao;
import cms.DAO.ShipmentDao;
import cms.model.Regularuser;
import cms.model.Shipment;
import cms.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegularuserService {

    private final RegularuserDao dao;
    private final ArchiveDao archivedao;
    private final ShipmentDao shipmentdao;
    private final VehicleService vehicleService;

    @Autowired
    public RegularuserService(RegularuserDao dao, ArchiveDao archivedao, ShipmentDao shipmentdao, VehicleService vehicleService) {
        this.archivedao=archivedao;
        this.shipmentdao=shipmentdao;
        this.dao = dao;
        this.vehicleService = vehicleService;
    }


    @Transactional
    public Regularuser find(String username){
        return dao.find(username);
    }

    @Transactional
    public List<Regularuser> findAll(){
        return dao.findAll();
    }

    @Transactional
    public void setShipmentFinished(Integer id, String description){
        dao.setShipmentFinished(id);
        Shipment shipment = shipmentdao.find(id);
        archivedao.archiveShipment(id,description,shipment.getStatus());
        shipmentdao.deleteShipment(id);
    }
    @Transactional
    public void setShipmentFailed(Integer id){
        dao.setShipmentFailed(id);
    }

    @Transactional
    public void create(String username, String name,String password,String vehicle,String licence){
        Vehicle v = vehicleService.find(vehicle);
        dao.create(username, name, v.getId(), licence,password);
    }

    @Transactional
    public boolean autentificate(String username,String password){
        Regularuser user= dao.find(username);
        if(user==null){
            return false;
        }
        System.out.println(user.getPassword()+"="+password);
        if(user.getPassword().equals(password)){
            return true;
        }
        return false;
    }
}
