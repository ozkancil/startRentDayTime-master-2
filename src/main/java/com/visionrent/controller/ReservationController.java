package com.visionrent.controller;

import com.visionrent.domain.Car;
import com.visionrent.domain.User;
import com.visionrent.dto.ReservationDTO;
import com.visionrent.dto.request.ReservationRequest;
import com.visionrent.dto.request.ReservationUpdateRequest;
import com.visionrent.dto.response.CarAvailabilityResponse;
import com.visionrent.dto.response.ResponseMessage;
import com.visionrent.dto.response.VRResponse;
import com.visionrent.service.CarService;
import com.visionrent.service.ReservationService;
import com.visionrent.service.UserService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UserService userService;

	@Autowired
	private CarService carService;
	/**
	 * US: a user can make a reservation for him/herself.
	 * @param carId carID for reservation
	 * @param reservationRequest reservation information
	 * @return VRResponse
	 * {
	 *     "pickUpTime":"07/16/2022 19:00:00",
	 *     "dropOffTime":"07/17/2022 21:00:00",
	 *     "pickUpLocation":"Ankara",
	 *     "dropOffLocation":"Ankara"
	 * }
	 * http://localhost:8080/reservations/add?carId=1
	 */
	@PostMapping("/add")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<VRResponse> makeReservation(@RequestParam("carId") Long carId,
	                                                  @RequestBody @Valid ReservationRequest reservationRequest){

		Car car = carService.findCarById(carId);

		User user = userService.getCurrentUser();

		reservationService.createReservation(reservationRequest,user,car);

		VRResponse response = new VRResponse(ResponseMessage.RESERVATION_CREATED_RESPONSE_MESSAGE,true);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	/**
	 * US: admin can make a reservation for a user
	 * @param userId for whom this reservation is being made
	 * @param carId carID for reservation
	 * @param reservationRequest reservation information
	 * @return response entity
	 * {
	 *     "pickUpTime":"07/16/2022 19:00:00",
	 *     "dropOffTime":"07/17/2022 21:00:00",
	 *     "pickUpLocation":"Ankara",
	 *     "dropOffLocation":"Ankara"
	 * }
	 * http://localhost:8080/reservations/add/auth?carId=2&userId=16
	 */
	@PostMapping("/add/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VRResponse> addReservation(@RequestParam("carId") Long carId,
													 @RequestParam("userId") Long userId,
	                                                 @RequestBody @Valid ReservationRequest reservationRequest){
		Car car = carService.findCarById(carId);
		User user = userService.getById(userId);
		reservationService.createReservation(reservationRequest,user,car);
		VRResponse response = new VRResponse(ResponseMessage.RESERVATION_CREATED_RESPONSE_MESSAGE,true);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	/**
	 * US: admin can update a reservation
	 * @param carId regarding the reservation
	 * @param reservationId
	 * @param reservationUpdateRequest
	 * @return
	{
	"pickUpTime": "04/01/2023 19:00:00",
	"dropOffTime": "04/09/2023 21:00:00",
	"pickUpLocation": "Ankara",
	"dropOffLocation": "Ankara",
	"status":"CREATED"
	}
	 * http://localhost:8080/reservations/admin/auth?carId=1&reservationId=1
	 */
	@PutMapping("/admin/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VRResponse>updateReservation(@RequestParam("carId") Long carId,
	                                                   @RequestParam("reservationId") Long reservationId,
	                                                   @RequestBody @Valid ReservationUpdateRequest reservationUpdateRequest){

		Car car = carService.findCarById(carId);
		reservationService.updateReservation(reservationId,car,reservationUpdateRequest);
		VRResponse response = new VRResponse(ResponseMessage.RESERVATION_UPDATED_RESPONSE_MESSAGE,true);
		return new ResponseEntity<>(response,HttpStatus.OK);

	}

	//http://localhost:8080/reservations/auth?carId=2&pickUpTime=04/01/2023 19:00:00&dropOffTime=04/10/2023 20:00:00
	@GetMapping("/auth")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<VRResponse>checkCarIsAvaiblable(@RequestParam("carId") Long carId,
	                                                      @RequestParam("pickUpTime") @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss") LocalDateTime pickUpTime,
	                                                      @RequestParam("dropOffTime") @DateTimeFormat(pattern = "MM/dd/yyyy HH:mm:ss") LocalDateTime dropOffTime){

		//TODO there is a bug here ->
		// when you send the same pickUp and dropOff time, we do not get any error
		// we get total calculation of 0// Car car = carService.findCarById(carId);
		Car car = carService.findCarById(carId);
		boolean isAvailable = reservationService.checkCarAvailability(car,pickUpTime,dropOffTime);
		Double totalPrice = reservationService.getTotalPrice(car,pickUpTime,dropOffTime);
		//TODO (advance imp.) if it is not available, return another response WITH -> POSSIBLE CANDIDATE TIMES...
		VRResponse response = new CarAvailabilityResponse(ResponseMessage.CAR_AVAILABLE_MESSAGE,true,isAvailable,totalPrice);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	//http://localhost:8080/reservations/admin/all
	@GetMapping("/admin/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ReservationDTO>> getAllReservations(){
		List<ReservationDTO> allReservations = reservationService.getAllReservations();
		return ResponseEntity.ok(allReservations);
	}

	@GetMapping("/admin/auth/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<ReservationDTO>>getAllUserReservationsByPage(@RequestParam("userId")Long userId,
	                                                                        @RequestParam("page") int page,
	                                                                        @RequestParam("size") int size,
	                                                                        @RequestParam("sort") String prop,
	                                                                        @RequestParam(value = "direction",required = false,defaultValue = "DESC")Sort.Direction direction){

		Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
		User user = userService.getById(userId);

		Page<ReservationDTO>allReservations = reservationService.findReservationPageByUser(user,pageable);
		return ResponseEntity.ok(allReservations);
	}


	//http://localhost:8080/reservations/admin/all/pages?size=2&page=0&sort=id&direction=DESC
	@GetMapping("/admin/all/pages")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<ReservationDTO>>getAllUserReservationsByPage(@RequestParam("page") int page,
	                                                                        @RequestParam("size") int size,
	                                                                        @RequestParam("sort") String prop,
	                                                                        @RequestParam(value = "direction",required = false,defaultValue = "DESC")Sort.Direction direction){

		Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

		Page<ReservationDTO>allReservations = reservationService.getReservationPage(pageable);
		return ResponseEntity.ok(allReservations);
	}


	/**
	 * ADMIN should bring all reservations (by page) according to a "userId"
	 */
	//http://localhost:8080/reservations/2/admin
	@GetMapping("{id}/admin")
	public ResponseEntity<ReservationDTO>getReservationById(@PathVariable Long id){
		ReservationDTO reservationDTO = reservationService.getReservationDTO(id);
		return ResponseEntity.ok(reservationDTO);
	}

	/**
	 * A logged-in user is able to bring all his/her reservations by page
	 * http://localhost:8080/reservations/admin/all?size=2&page=0&sort=id&direction=DESC
	 */
	@GetMapping("/auth/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<ReservationDTO>>getLoggedInUserReservationsByPage(@RequestParam("page") int page,
	                                                                        @RequestParam("size") int size,
	                                                                        @RequestParam("sort") String prop,
	                                                                        @RequestParam(value = "direction",required = false,defaultValue = "DESC")Sort.Direction direction){

		Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
		User user = userService.getCurrentUser();

		Page<ReservationDTO>allReservations = reservationService.findReservationPageByUser(user,pageable);
		return ResponseEntity.ok(allReservations);
	}
	/**
	 * A logged-in user is able to bring a reservation that he/she has according to an ID
	 * http://localhost:8080/reservations/1/auth
	 */
	@GetMapping("/{id}/auth")
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	public ResponseEntity<ReservationDTO>getLoggedInUserReservationById(@PathVariable Long id){
		User user = userService.getCurrentUser();
		ReservationDTO reservationDTO = reservationService.findByReservationIdAndLoggedInUser(id,user);
		return ResponseEntity.ok(reservationDTO);
	}

	/**
	 * http://localhost:8080/reservations/admin/1/auth
	 * @param id reservationId to delete
	 * @return VRResponse
	 */
	@DeleteMapping("/admin/{id}/auth")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VRResponse>deleteReservation(@PathVariable Long id){
		reservationService.removeByReservationId(id);
		VRResponse response = new VRResponse(ResponseMessage.RESERVATION_DELETED_RESPONSE_MESSAGE,true);
		return ResponseEntity.ok(response);
	}


































}
