from datetime import datetime
from pydantic import BaseModel
from typing import Optional


class TripBase(BaseModel):
    start: datetime
    end: datetime
    driver_id: int
    vehicle_id: str
    location_from: str
    location_to: str
    distance: int


class TripCreate(TripBase):
    pass


class TripUpdate(TripBase):
    id: int
    start: Optional[datetime] = None
    end: Optional[datetime] = None
    driver_id: Optional[int] = None
    vehicle_id: Optional[str] = None
    location_from: Optional[str] = None
    location_to: Optional[str] = None


class TripResponse(TripBase):
    id: int

    class Config:
        orm_mode = True
