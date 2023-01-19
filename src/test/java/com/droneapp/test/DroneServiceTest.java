package com.droneapp.test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.droneapp.dto.DroneDTO;
import com.droneapp.dto.MedicationDTO;
import com.droneapp.dto.ModelDTO;
import com.droneapp.dto.StateDTO;
import com.droneapp.entity.Drone;
import com.droneapp.entity.Model;
import com.droneapp.entity.State;
import com.droneapp.exception.BussinessException;
import com.droneapp.repository.DroneRepository;
import com.droneapp.repository.ModelRepository;
import com.droneapp.repository.StateRepository;
import com.droneapp.service.DroneService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
public class DroneServiceTest extends DroneApplicationTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	@MockBean
	private DroneRepository droneRepository;
	@MockBean
	private ModelRepository modelRepository;
	@MockBean
	private StateRepository stateRepository;

	@Autowired
	DroneService droneService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void registerDroneShouldDisplaySuccessMessage() throws Exception {

		State state = new State();
		state.setId(4);
		state.setName("Loading");
		when(stateRepository.findByName(anyString())).thenReturn(state);

		Model model = new Model();
		model.setId(2);
		when(modelRepository.findByName(anyString())).thenReturn(model);

		this.mockMvc.perform(post("/drone/register").contentType(MediaType.APPLICATION_JSON).content(getDronAsJson()))
				.andExpect(status().isOk()).andExpect(content().string(containsString("Drone registred successfully")));

	}

	@Test
	public void registerDroneShouldInvalidModel() throws Exception {

		State state = new State();
		state.setId(4);
		when(stateRepository.findByName(anyString())).thenReturn(state);

		when(modelRepository.findByName(anyString())).thenReturn(null);

		BussinessException thrown = assertThrows(BussinessException.class,
				() -> droneService.addDrone(preperDroneRequestInvalidModelName()));

		assertTrue(thrown.getLocalizedMessage().contentEquals("The Model not found"));
	}

	@Test
	public void registerDroneShouldExceedDroneWeight() throws Exception {

		State state = new State();
		state.setId(4);
		state.setName("LOADED");
		when(stateRepository.findByName(anyString())).thenReturn(state);

		Model model = new Model();
		model.setId(2);
		when(modelRepository.findByName(anyString())).thenReturn(model);

		BussinessException thrown = assertThrows(BussinessException.class,
				() -> droneService.addDrone(preperDroneRequestForExccedLimtWeight()));

		assertTrue(thrown.getLocalizedMessage()
				.contentEquals("the drone prevent to be in loaded state because it exceed the drone weight"));
	}

	@Test
	public void registerDroneShouldPreventDroneToBeLoading() throws Exception {

		State state = new State();
		state.setId(4);
		state.setName("LOADING");
		when(stateRepository.findByName(anyString())).thenReturn(state);

		Model model = new Model();
		model.setId(2);
		when(modelRepository.findByName(anyString())).thenReturn(model);

		BussinessException thrown = assertThrows(BussinessException.class,
				() -> droneService.addDrone(preperDroneRequestForPreventDroneToBeInLoadingState()));

		assertTrue(thrown.getLocalizedMessage()
				.contentEquals("the drone prevent to be in LOADING  state because the battery is below 25 %"));
	}

	@Test
	public void registerDroneWithDuplicateSerialNumber() throws Exception {

		State state = new State();
		state.setId(4);
		state.setName("LOADING");
		when(stateRepository.findByName(anyString())).thenReturn(state);

		Model model = new Model();
		model.setId(2);
		when(modelRepository.findByName(anyString())).thenReturn(model);

		Drone testDrone = new Drone();
		testDrone.setSerialNumber("duplicate");
		when(droneRepository.findBySerialNumber(anyString())).thenReturn(testDrone);

		BussinessException thrown = assertThrows(BussinessException.class,
				() -> droneService.addDrone(preperDroneRequestForDuplicateSerialNmber()));

		assertTrue(thrown.getLocalizedMessage().contentEquals("Serial Number is Duplicate"));
	}

	String getDronAsJsonWithInvalidModel() {
		String droneJsonStr = "{\r\n"
				+ "    \"serialNumber\": \"11\",\r\n"
				+ "    \"model\":  {\"name\":\"Lightweighttt\"},\r\n"
				+ "    \"weightLimit\": 56,\r\n"
				+ "    \"bettaryCapacity\":25,\r\n"
				+ "    \"state\": {\"name\":\"LOADING\"},\r\n"
				+ "    \"medications\": [\r\n"
				+ "        {\r\n"
				+ "            \"name\": \"dfdfADDFD3434_-\",\r\n"
				+ "            \"weight\": \"0\",\r\n"
				+ "            \"code\": \"ASA_55\",\r\n"
				+ "            \"image\": \"data:image/png;base64,itest image 2\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"name\": \"medication2\",\r\n"
				+ "            \"weight\": \"77\",\r\n"
				+ "            \"code\": \"2\",\r\n"
				+ "            \"image\": \"data:image/png;base64,test image\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return droneJsonStr;
	}

	String getDronAsJson() {
		String droneJsonStr = "{\r\n"
				+ "    \"serialNumber\": \"11\",\r\n"
				+ "    \"model\":  {\"name\":\"Lightweight\"},\r\n"
				+ "    \"weightLimit\": 56,\r\n"
				+ "    \"bettaryCapacity\":25,\r\n"
				+ "    \"state\": {\"name\":\"LOADING\"},\r\n"
				+ "    \"medications\": [\r\n"
				+ "        {\r\n"
				+ "            \"name\": \"dfdfADDFD3434_-\",\r\n"
				+ "            \"weight\": \"0\",\r\n"
				+ "            \"code\": \"ASA_55\",\r\n"
				+ "            \"image\": \"data:image/png;base64,itest image 2\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"name\": \"medication2\",\r\n"
				+ "            \"weight\": \"77\",\r\n"
				+ "            \"code\": \"2\",\r\n"
				+ "            \"image\": \"data:image/png;base64,test image\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}"				+ "        }\r\n" + "    ]\r\n" + "}";
		return droneJsonStr;
	}

	DroneDTO preperDroneRequest() {
		DroneDTO droneRequest = new DroneDTO();

		droneRequest.setSerialNumber("123");
		droneRequest.setBettaryCapacity(20f);
		droneRequest.setWeightLimit(22f);

		ModelDTO modelDTO = new ModelDTO();
		modelDTO.setName("Lightweight");
		droneRequest.setModel(modelDTO);

		StateDTO stateDTO = new StateDTO();
		stateDTO.setName("LOADING");
		droneRequest.setState(stateDTO);

		MedicationDTO medicationDTO = new MedicationDTO();
		medicationDTO.setCode("ASA");
		medicationDTO.setImage("data:image/png;base64,test image");
		medicationDTO.setName("asavf");
		medicationDTO.setWeight(14f);

		List<MedicationDTO> medicationDTOs = new ArrayList();
		medicationDTOs.add(medicationDTO);
		droneRequest.setMedications(medicationDTOs);

		return droneRequest;
	}

	DroneDTO preperDroneRequestForExccedLimtWeight() {
		DroneDTO droneRequest = new DroneDTO();

		droneRequest.setSerialNumber("123");
		droneRequest.setBettaryCapacity(20f);
		droneRequest.setWeightLimit(22f);

		ModelDTO modelDTO = new ModelDTO();
		modelDTO.setName("Lightweight");
		droneRequest.setModel(modelDTO);

		StateDTO stateDTO = new StateDTO();
		stateDTO.setName("LOADING");
		droneRequest.setState(stateDTO);

		MedicationDTO medicationDTO = new MedicationDTO();
		medicationDTO.setCode("ASA");
		medicationDTO.setImage("data:image/png;base64,test image");
		medicationDTO.setName("asavf");
		medicationDTO.setWeight(50f);

		List<MedicationDTO> medicationDTOs = new ArrayList();
		medicationDTOs.add(medicationDTO);
		droneRequest.setMedications(medicationDTOs);

		return droneRequest;
	}

	DroneDTO preperDroneRequestForDuplicateSerialNmber() {
		DroneDTO droneRequest = new DroneDTO();

		droneRequest.setSerialNumber("duplicate");
		droneRequest.setBettaryCapacity(50f);
		droneRequest.setWeightLimit(22f);

		ModelDTO modelDTO = new ModelDTO();
		modelDTO.setName("Lightweight");
		droneRequest.setModel(modelDTO);

		StateDTO stateDTO = new StateDTO();
		stateDTO.setName("LOADING");
		droneRequest.setState(stateDTO);

		MedicationDTO medicationDTO = new MedicationDTO();
		medicationDTO.setCode("ASA");
		medicationDTO.setImage("data:image/png;base64,test image");
		medicationDTO.setName("asavf");
		medicationDTO.setWeight(50f);

		List<MedicationDTO> medicationDTOs = new ArrayList();
		medicationDTOs.add(medicationDTO);
		droneRequest.setMedications(medicationDTOs);

		return droneRequest;
	}

	DroneDTO preperDroneRequestForPreventDroneToBeInLoadingState() {
		DroneDTO droneRequest = new DroneDTO();

		droneRequest.setSerialNumber("123");
		droneRequest.setBettaryCapacity(20f);
		droneRequest.setWeightLimit(22f);

		ModelDTO modelDTO = new ModelDTO();
		modelDTO.setName("Lightweight");
		droneRequest.setModel(modelDTO);

		StateDTO stateDTO = new StateDTO();
		stateDTO.setName("LOADING");
		droneRequest.setState(stateDTO);

		MedicationDTO medicationDTO = new MedicationDTO();
		medicationDTO.setCode("ASA");
		medicationDTO.setImage("data:image/png;base64,test image");
		medicationDTO.setName("asavf");
		medicationDTO.setWeight(50f);

		List<MedicationDTO> medicationDTOs = new ArrayList();
		medicationDTOs.add(medicationDTO);
		droneRequest.setMedications(medicationDTOs);

		return droneRequest;
	}

	DroneDTO preperDroneRequestInvalidModelName() {
		DroneDTO droneRequest = new DroneDTO();

		droneRequest.setSerialNumber("123");
		droneRequest.setBettaryCapacity(20f);
		droneRequest.setWeightLimit(22f);

		ModelDTO modelDTO = new ModelDTO();
		modelDTO.setName("Lightweighttt");
		droneRequest.setModel(modelDTO);

		StateDTO stateDTO = new StateDTO();
		stateDTO.setName("LOADING");
		droneRequest.setState(stateDTO);

		MedicationDTO medicationDTO = new MedicationDTO();
		medicationDTO.setCode("ASA");
		medicationDTO.setImage("data:image/png;base64,test image");
		medicationDTO.setName("asavf");
		medicationDTO.setWeight(14f);

		List<MedicationDTO> medicationDTOs = new ArrayList();
		medicationDTOs.add(medicationDTO);
		droneRequest.setMedications(medicationDTOs);

		return droneRequest;
	}
}
