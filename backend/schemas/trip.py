from datetime import datetime
from pydantic import BaseModel
from typing import Optional


class TripBase(BaseModel):
    start: datetime
    end: datetime
    driver_id: int
    vehicle_id: str
    departure_street: str
    departure_house_number: str
    departure_postal_code: str
    departure_locality: str
    departure_country: str
    arrival_street: str
    arrival_house_number: str
    arrival_postal_code: str
    arrival_locality: str
    arrival_country: str
    distance: float


class TripCreate(TripBase):
    pass


class TripUpdate(TripBase):
    start: Optional[datetime] = None
    end: Optional[datetime] = None
    driver_id: Optional[int] = None
    vehicle_id: Optional[str] = None
    departure_street: Optional[str] = None
    departure_house_number: Optional[str] = None
    departure_postal_code: Optional[str] = None
    departure_locality: Optional[str] = None
    departure_country: Optional[str] = None
    arrival_street: Optional[str] = None
    arrival_house_number: Optional[str] = None
    arrival_postal_code: Optional[str] = None
    arrival_locality: Optional[str] = None
    arrival_country: Optional[str] = None
    distance: Optional[float] = None


class TripResponse(TripBase):
    id: int

    class Config:
        orm_mode = True
