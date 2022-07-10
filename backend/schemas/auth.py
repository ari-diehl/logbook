from pydantic import BaseModel

from schemas.employee import EmployeeResponse


class EmployeeLogin(BaseModel):
    id: int
    password: str


class EmployeeLoginResponse(EmployeeResponse):
    access_token: str
