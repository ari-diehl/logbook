from sqlalchemy import Boolean, Column, DateTime, Integer, String, BigInteger, ForeignKey
from sqlalchemy.orm import relationship

from database import Base


class Employee(Base):
    __tablename__ = "employee"
    # Personalnummer
    id = Column(BigInteger, primary_key=True, index=True)
    # Passwort für die Fahrtenbuch-App
    password = Column(String)
    first_name = Column(String)
    last_name = Column(String)
    # Berufsbezeichnung, z.B. driver, invoice, maintenance
    role = Column(String)

    trips = relationship("Trip", back_populates="driver")


class Trip(Base):
    __tablename__ = "trip"
    id = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    start = Column(DateTime)
    end = Column(DateTime)
    driver_id = Column(ForeignKey("employee.id"))
    vehicle_id = Column(ForeignKey("vehicle.id"))
    location_from = Column(String)
    location_to = Column(String)
    distance = Column(Integer, nullable=True)
    completed = Column(Boolean)
    comment = Column(String, nullable=True)

    driver = relationship("Employee", back_populates="trips")
    vehicle = relationship("Vehicle", back_populates="trips")


class Vehicle(Base):
    __tablename__ = "vehicle"
    # KFZ-Kennzeichen
    id = Column(String, primary_key=True, index=True)
    manufacturer = Column(String)
    model = Column(String)
    mileage = Column(Integer)

    trips = relationship("Trip", back_populates="vehicle")
