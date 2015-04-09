package test.com.bnpp.ism.technicalcomponents;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bnpp.ism.technicalcomponents.application.Application;
import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCatalogDao;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;

@RunWith(SpringJUnit4ClassRunner.class)
// 1
@SpringApplicationConfiguration(classes = Application.class)
// 2
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class PushInitialDBContentTest {

	@Autowired
	ComponentCatalogDao componentCatalogDao;

	@Value("${local.server.port}")
	int port;

	@Before
	public void setUp() {
		ComponentCatalog defaultCatalog = new ComponentCatalog();
		defaultCatalog.setName("default");
		defaultCatalog.setDescription("Default Global Catalog");
		componentCatalogDao.save(defaultCatalog);
	}

	@Test
	public void dummy() {

	}
}
