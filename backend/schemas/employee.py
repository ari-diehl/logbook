from pydantic import BaseModel
from typing import Optional


class EmployeeBase(BaseModel):
    personnel_number: int
    first_name: str
    last_name: str
    role: str


class EmployeeCreate(EmployeeBase):
    password: str


class EmployeeUpdate(EmployeeBase):
    personnel_number: Optional[int] = None
    password: Optional[str] = None
    first_name: Optional[str] = None
    last_name: Optional[str] = None
    role: Optional[str] = None


class EmployeeResponse(EmployeeBase):
    id: int

    class Config:
        orm_mode = True
