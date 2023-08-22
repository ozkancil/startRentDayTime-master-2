package com.visionrent.domain;

import com.visionrent.domain.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "t_reservation")
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //CLEAN CODE
    /**
     * All entities have the same naming as "id"
     * It would be a better way to change the naming as reservationId/userId/carId
     */
    @OneToOne
    @JoinColumn(name = "car_id",referencedColumnName = "id")
    private Car car;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;



    @Column(nullable = false)
    private LocalDateTime pickUpTime;

    @Column(nullable = false)
    private LocalDateTime dropOffTime;

    @Column(nullable = false)
    private String pickUpLocation;

    @Column(nullable = false)
    private String dropOffLocation;

    @Enumerated(EnumType.STRING)
    @Column(length = 30,nullable = false)
    //TODO check the reason of length usage here
    private ReservationStatus status;

    @Column(nullable = false)
    private Double totalPrice;



}
