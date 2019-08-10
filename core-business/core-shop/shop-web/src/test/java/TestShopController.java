import com.xzn.ShopApplication;
import com.xzn.shop.service.ShopService;
import com.xzn.user.api.UserService;
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
    private UserService userService;

    @Test
    public void testSayHello() {

//        Mockito.doReturn("bbbbb").when(userService.login("aaaa"));
//        Mockito.when(userService.login("aaaa")).thenReturn("bbbbb");
        Mockito.doReturn("bbbbb").when(userService.login("aaaa"));
        userService.login("aaaa");
    }


}
