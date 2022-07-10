from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from database import get_db
from schemas.auth import EmployeeLogin, EmployeeLoginResponse
from services.employee import crypt_context, employee_service

router = APIRouter(prefix="/auth")


@router.post("/employee_login", response_model=EmployeeLoginResponse)
def login(employee_login: EmployeeLogin, db: Session = Depends(get_db)):
    employee = employee_service.read(db, employee_login.id)

    if not employee:
        raise HTTPException(status_code=404)

    if not crypt_context.verify(employee_login.password, employee.password):
        raise HTTPException(status_code=400)

    return employee_service.login(employee)
