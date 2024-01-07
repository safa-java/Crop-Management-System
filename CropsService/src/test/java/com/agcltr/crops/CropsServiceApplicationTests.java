package com.agcltr.crops;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.agcltr.crops.entity.Crops;
import com.agcltr.crops.repo.CropsRepository;
import com.agcltr.crops.service.CropsServiceImpl;
import com.agcltr.crops.service.CropsServiceI;

@SpringBootTest
class CropsServiceApplicationTests {
	
	@InjectMocks
	private CropsServiceImpl cservice;
	
//	@Autowired
//	private CropsServiceI cserviceI;
	
	@Mock
	private CropsRepository crepo;
	
	@InjectMocks
	private Crops crop;
	
	@BeforeEach
	public void cropsServiceSetUp() {
		crop = new Crops("2bf34798", "PineApple", "S K Farms", "fruit", 70, "ABC, Bhopal", 120.00, 9432932984L, "image1");
	}
	
	@Test
    void testCreateCrops() {
       Mockito.when(crepo.save(crop)).thenReturn(crop);
       assertThat(cservice.createCrops(crop)).isEqualTo(crop);
       //verify(crepo, times(1)).findAll();

   }
	
	@Test
	void testGetAllCrops() {
		Crops c1 = new Crops("3bf333798", "Rice", "S K Farms", "Grain", 170, "ABC, Bhopal", 50.00, 9998394484L, "img1");
		Crops c2 = new Crops("2fbf34798", "Tomatoes", "S S Fruits", "fruit", 300, "MP", 12.00, 9993295184L, "img2");
		List<Crops> list = Arrays.asList(c1, c2);
		
		Mockito.when(crepo.findAll()).thenReturn(list);

		List<Crops> result = cservice.getAllCrops();
		assertEquals(2, result.size());
		assertEquals("Rice", result.get(0).getCropName());
		       
	}
	
	
}
