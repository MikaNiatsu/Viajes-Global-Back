package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.*;

public class DataMapper {



    public static ActivityEntity activityDTOtoEntity(ActivityDTO dto) {
        return new ActivityEntity(
                dto.getActivity_id(),
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getLocation(),
                dto.getCategory(),
                dto.getImages(),
                dto.getRating(),
                dto.getStock()
        );
    }

    public static ActivityDTO activityEntityToDTO(ActivityEntity entity) {
        return new ActivityDTO(
                entity.getActivity_id(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getLocation(),
                entity.getCategory(),
                entity.getImages(),
                entity.getRating(),
                entity.getStock()
        );
    }

    public static BookingDetailDTO bookingDetailEntityToDTO(BookingDetailEntity entity) {
        return new BookingDetailDTO(
                entity.getBooking_detail_id(),
                entity.getFlight_id(),
                entity.getNum_adults(),
                entity.getNum_children(),
                entity.getBookingEntity()
        );
    }

    public static BookingDetailEntity bookingDetailDTOtoEntity(BookingDetailDTO dto) {
        return new BookingDetailEntity(
                dto.getBooking_detail_id(),
                dto.getFlight_id(),
                dto.getNum_adults(),
                dto.getNum_children(),
                dto.getBookingEntity()
        );
    }

    public static BookingDTO bookingEntityToDTO(BookingEntity entity) {
        return new BookingDTO(
                entity.getBooking_id(),
                entity.getCustomer_id(),
                entity.getBooking_date(),
                entity.getBooking_status(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getPackageEntity()
        );
    }

    public static BookingEntity bookingDTOtoEntity(BookingDTO dto) {
        return new BookingEntity(
                dto.getBooking_id(),
                dto.getCustomer_id(),
                dto.getBooking_date(),
                dto.getBooking_status(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getPackageEntity()
        );
    }

    public static FlightDTO flightEntityToDTO(FlightEntity entity) {
        return new FlightDTO(
                entity.getFlightId(),
                entity.getAirline(),
                entity.getOrigin(),
                entity.getDestination(),
                entity.getDepartureDate(),
                entity.getArrivalDate(),
                entity.getPrice(),
                entity.getImages(),
                entity.getDescription(),
                entity.getRating(),
                entity.getStock()
        );
    }

    public static FlightEntity flightDTOtoEntity(FlightDTO dto) {
        return new FlightEntity(
                dto.getFlightId(),
                dto.getAirline(),
                dto.getOrigin(),
                dto.getDestination(),
                dto.getDepartureDate(),
                dto.getArrivalDate(),
                dto.getPrice(),
                dto.getImages(),
                dto.getDescription(),
                dto.getRating(),
                dto.getStock()
        );
    }
    public static HotelDTO hotelEntityToDTO(HotelEntity entity) {
        return new HotelDTO(
                entity.getHotel_id(),
                entity.getName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getCountry(),
                entity.getPrice_per_night(),
                entity.getImages(),
                entity.getDescription(),
                entity.getRating(),
                entity.getStock()
        );
    }

    public static HotelEntity hotelDTOtoEntity(HotelDTO dto) {
        return new HotelEntity(
                dto.getHotel_id(),
                dto.getName(),
                dto.getAddress(),
                dto.getCity(),
                dto.getCountry(),
                dto.getPrice_per_night(),
                dto.getImages(),
                dto.getDescription(),
                dto.getRating(),
                dto.getStock()
        );
    }

    public static PackageDTO packageEntityToDTO(PackageEntity entity) {
        return new PackageDTO(
                entity.getPackage_id(),
                entity.getPrice(),
                entity.getHotelEntity(),
                entity.getFlightEntity(),
                entity.getActivityEntity()
        );
    }

    public static PackageEntity packageDTOToEntity(PackageDTO dto) {
        return new PackageEntity(
                dto.getPackage_id(),
                dto.getPrice(),
                dto.getHotelEntity(),
                dto.getFlightEntity(),
                dto.getActivityEntity()
        );
    }

    public DataMapper() {
        throw new IllegalStateException("Utility class");
    }
}
