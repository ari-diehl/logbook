from datetime import datetime
from pydantic import BaseModel
from typing import Optional


class TripBase(BaseModel):
    start: datetime
    end: datetime
    driver_id: int
    vehicle_id: int
    location_from: str
    location_to: str
    distance: Optional[int] = None
    completed: bool
    comment: Optional[str] = None


class TripCreate(TripBase):
    pass


class TripUpdate(TripBase):
    id: int
    start: Optional[datetime] = None
    end: Optional[datetime] = None
    driver_id: Optional[int] = None
    vehicle_id: Optional[int] = None
    location_from: Optional[str] = None
    location_to: Optional[str] = None
    completed: Optional[bool] = None


class TripResponse(TripBase):
    id: int

    class Config:
        orm_mode = True
