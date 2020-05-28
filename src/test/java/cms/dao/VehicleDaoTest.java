
package cms.dao;
import cms.Main;
import cms.model.Vehicle;
import cms.service.SystemInitializer;
import org.junit.Assert;
import org.junit.Test;
import cms.DAO.*;
import cms.model.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
// DataJpaTest does not load all the application beans, it starts only persistence-related stuff
@DataJpaTest
// Exclude SystemInitializer from the startup, we don't want the admin account here
@ComponentScan(basePackageClasses = Main.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SystemInitializer.class)})
public class VehicleDaoTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private VehicleDao dao;



    @Test
    public  void CreateVehicleTest(){
        Vehicle v = new Vehicle();
        v.setLicenseplate("1111111");
        v.setAvailability(true);
        em.persist(v);

        try {
            dao.createVehicle("1111111","null");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Vehicle res = dao.find("1111111");
        Assert.assertEquals(v.getLicenseplate(), res.getLicenseplate());
        Assert.assertEquals(v.getAvailability(), res.getAvailability());
    }
    @Test
    public void assignVehicleTest() throws Exception {
        Regularuser user = new Regularuser();
        user.setFullname("Frajer Nevydany");
        user.setPassword("somfrajer");
        user.setUsername("frajer");
        em.persist(user);

        Vehicle v = new Vehicle();
        v.setLicenseplate("1111111");
        v.setAvailability(true);


        dao.createVehicle("1111111","null");
        dao.assignVehicle(v.getLicenseplate(),user.getUsername());
        Assert.assertEquals(user.getVehicleid(), v.getId());
    }

    @Test
    public void createAndDestroyVehicleTest() throws Exception {
        Vehicle v = new Vehicle();
        v.setLicenseplate("1111111");
        v.setAvailability(true);

        dao.createVehicle("1111111","null");
        Vehicle res = dao.find("1111111");
        Assert.assertNotNull(res);
        Assert.assertEquals(res.getLicenseplate(),v.getLicenseplate());
        dao.destroyVehicle("1111111");
        res = dao.find("1111111");
        Assert.assertEquals(null,res);
    }

    @Test
    public void findTest(){
        Vehicle v = new Vehicle();
        v.setLicenseplate("1111111");
        v.setAvailability(true);

        em.persist(v);
        Vehicle res =dao.find(v.getLicenseplate());
        Assert.assertNotNull(res);
        Assert.assertEquals(res.getLicenseplate(),v.getLicenseplate());
    }
}
