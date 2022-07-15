from pydantic import BaseModel

from schemas.employee import EmployeeResponse


class EmployeeLogin(BaseModel):
    personnel_number: int
    password: str


class EmployeeLoginResponse(EmployeeResponse):
    access_token: str
