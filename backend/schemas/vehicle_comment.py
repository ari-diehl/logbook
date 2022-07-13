from datetime import datetime
from pydantic import BaseModel
from typing import Optional


class VehicleCommentBase(BaseModel):
    employee_id: int
    vehicle_id: int
    text: str
    datetime: datetime


class VehicleCommentCreate(VehicleCommentBase):
    pass


class VehicleCommentUpdate(VehicleCommentBase):
    employee_id: Optional[int] = None
    vehicle_id: Optional[int] = None
    text: Optional[str] = None
    datetime: Optional[datetime] = None


class VehicleCommentResponse(VehicleCommentBase):
    id: int

    class Config:
        orm_mode = True
