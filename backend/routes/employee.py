from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import Optional

from database import get_db
from schemas.employee import EmployeeResponse, EmployeeCreate, EmployeeUpdate
from services.employee import employee_service
from auth import get_current_employee

router = APIRouter(prefix="/employees")


@router.get("/", response_model=list[EmployeeResponse])
def read_employees(skip: int = 0, limit: int = 100, current_employee=Depends(get_current_employee), db: Session = Depends(get_db)):
    if current_employee.role != "invoice":
        raise HTTPException(status_code=401)

    return employee_service.read_multi(db, skip, limit)


@router.get("/{employee_id}", response_model=EmployeeResponse)
def read_employee(employee_id: int, current_employee=Depends(get_current_employee), db: Session = Depends(get_db)):
    if current_employee.id != employee_id and current_employee.role != "invoice":
        raise HTTPException(status_code=401)

    employee = employee_service.read(db, employee_id)

    if not employee:
        raise HTTPException(status_code=404)

    return employee

#current_employee=Depends(get_current_employee),
@router.post("/", response_model=EmployeeResponse)
def create_employee(employee_create: EmployeeCreate, db: Session = Depends(get_db)):
    #if current_employee.role != "invoice":
        #raise HTTPException(status_code=401)

    employee = employee_service.read(db, employee_create.id)

    if employee:
        raise HTTPException(status_code=400)

    return employee_service.create(db, employee_create)


@router.put("/", response_model=EmployeeResponse)
def update_employee(employee_update: EmployeeUpdate, password_only: Optional[bool] = False, current_employee=Depends(get_current_employee), db: Session = Depends(get_db)):
    if password_only and current_employee.id == employee_update.id:
        employee = employee_service.read(db, employee_update.id)

        return employee_service.change_password(db, employee, employee_update.password)

    if current_employee.role != "invoice":
        raise HTTPException(status_code=401)

    employee = employee_service.read(db, employee_update.id)

    if not employee:
        raise HTTPException(status_code=404)

    return employee_service.update(db, employee, employee_update)


@router.delete("/{employee_id}", response_model=EmployeeResponse)
def delete_employee(employee_id: int, current_employee=Depends(get_current_employee), db: Session = Depends(get_db)):
    if current_employee.role != "invoice":
        raise HTTPException(status_code=401)

    employee = employee_service.read(db, employee_id)

    if not employee:
        raise HTTPException(status_code=404)

    return employee_service.delete(db, employee_id)
