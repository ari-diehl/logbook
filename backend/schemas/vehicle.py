from pydantic import BaseModel
from typing import Optional


class VehicleBase(BaseModel):
    license_plate: str
    manufacturer: str
    model: str
    mileage: float


class VehicleCreate(VehicleBase):
    pass


class VehicleUpdate(VehicleBase):
    license_plate: Optional[str] = None
    manufacturer: Optional[str] = None
    model: Optional[str] = None
    mileage: Optional[float] = None


class VehicleResponse(VehicleBase):
    id: int

    class Config:
        orm_mode = True
