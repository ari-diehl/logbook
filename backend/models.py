from sqlalchemy import Column, DateTime, Float, Integer, String, BigInteger, ForeignKey
from sqlalchemy.orm import relationship

from database import Base


class Employee(Base):
    __tablename__ = "employee"
    id = Column(BigInteger, primary_key=True, index=True)
    personnel_number = Column(Integer, unique=True, index=True)
    # Passwort für die Fahrtenbuch-App
    password = Column(String)
    first_name = Column(String)
    last_name = Column(String)
    # Berufsbezeichnung, z.B. driver, invoice, maintenance
    role = Column(String)

    trips = relationship("Trip", back_populates="driver")
    vehicle_comments = relationship(
        "VehicleComment", back_populates="employee")


class Trip(Base):
    __tablename__ = "trip"
    id = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    start = Column(DateTime)
    end = Column(DateTime)
    driver_id = Column(ForeignKey("employee.id"))
    vehicle_id = Column(ForeignKey("vehicle.id"))
    departure_street = Column(String)
    departure_house_number = Column(String)
    departure_postal_code = Column(String)
    departure_locality = Column(String)
    departure_country = Column(String)
    arrival_street = Column(String)
    arrival_house_number = Column(String)
    arrival_postal_code = Column(String)
    arrival_locality = Column(String)
    arrival_country = Column(String)
    distance = Column(Float)

    driver = relationship("Employee", back_populates="trips")
    vehicle = relationship("Vehicle", back_populates="trips")


class Vehicle(Base):
    __tablename__ = "vehicle"
    id = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    license_plate = Column(String, unique=True, index=True)
    manufacturer = Column(String)
    model = Column(String)
    mileage = Column(Float)

    trips = relationship("Trip", back_populates="vehicle")
    comments = relationship("VehicleComment", back_populates="vehicle")


class VehicleComment(Base):
    __tablename__ = "vehicle_comment"
    id = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    employee_id = Column(ForeignKey("employee.id"))
    vehicle_id = Column(ForeignKey("vehicle.id"))
    text = Column(String)
    datetime = Column(DateTime)

    employee = relationship("Employee", back_populates="vehicle_comments")
    vehicle = relationship("Vehicle", back_populates="comments")
