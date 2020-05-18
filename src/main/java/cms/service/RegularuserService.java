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
    public void delete(Regularuser user){
        assignVehicle(user.getUsername(),null);
        vehicleService.assignVehicle(user.getVehicleid(),null);

    }

    @Transactional
    public void modify(Regularuser user,String username, String name,String password,String vehicle,String licence){
        user.setUsername(username);
        user.setLicensenumber(licence);
        user.setFullname(name);
        if(!password.trim().equals("")){
            user.setPassword(password);
        }if(vehicle!=null) {
            Vehicle v = vehicleService.findById(Integer.valueOf(vehicle));
            user.setVehicleid(v.getId());
        }
        dao.update(user);
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
    public List<Regularuser> findAvailable(){
        return dao.findAvailable();
    }
    @Transactional
    public void assignVehicle(String username,String vehicle){
        Regularuser user = find(username);
        modify(user,user.getUsername(),user.getFullname(),user.getPassword(),vehicle,user.getLicensenumber());
    }

    @Transactional
    public void create(String username, String name,String password,String vehicle,String licence){

        Vehicle v = vehicleService.find(vehicle);
        if(v!=null) {
            dao.create(username, name, v.getId(), licence, password);
            v.setDriver(username);
            v.setAvailability(false);
        }else {dao.create(username,name,licence,password);}

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

    public List<Regularuser> findTruckless() {
       return dao.truckLess();
    }
}
