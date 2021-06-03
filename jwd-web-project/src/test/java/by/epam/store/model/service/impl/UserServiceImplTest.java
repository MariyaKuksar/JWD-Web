package by.epam.store.model.service.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import by.epam.store.model.dao.UserDao;

public class UserServiceImplTest {
	@Mock
	private UserDao userDao;
	private AutoCloseable autoCloseable;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@BeforeClass
	public void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
	}
	
	@AfterClass
	public void tierDown() throws Exception {
		autoCloseable.close();
	}
	
	
}
