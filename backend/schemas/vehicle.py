from pydantic import BaseModel
from typing import Optional


class VehicleBase(BaseModel):
    id: str
    manufacturer: str
    model: str
    mileage: int


class VehicleCreate(VehicleBase):
    pass


class VehicleUpdate(VehicleBase):
    manufacturer: Optional[str] = None
    model: Optional[str] = None
    mileage: Optional[int] = None


class VehicleResponse(VehicleBase):

    class Config:
        orm_mode = True
