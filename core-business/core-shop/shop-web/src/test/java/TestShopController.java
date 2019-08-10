import com.xzn.ShopApplication;
import com.xzn.shop.service.ShopService;
import com.xzn.user.api.UserRemoteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopApplication.class)
public class TestShopController {

    @Autowired
    private ShopService shopService;

    @SpyBean
    private UserRemoteService userRemoteService;

    @Test
    public void testSayHello() {

//        Mockito.doReturn("bbbbb").when(userRemoteService.login("aaaa"));
//        Mockito.when(userRemoteService.login("aaaa")).thenReturn("bbbbb");
        Mockito.doReturn("bbbbb").when(userRemoteService.login("aaaa"));
        userRemoteService.login("aaaa");
    }


}
