from fastapi.security import OAuth2PasswordBearer
from fastapi import Depends, HTTPException
from sqlalchemy.orm import Session
from jose import jwt, JWTError

from models import Employee
from database import get_db

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="auth/employee_login")


def get_current_employee(db: Session = Depends(get_db), token: str = Depends(oauth2_scheme)):
    try:
        payload = jwt.decode(
            token, "4880f7a45cc62e08c5583078c59e8373acd4dd3f19db8eda606a61e5d101aa9c", algorithms=["HS256"])

        employee_id = payload.get("sub")

        if not employee_id:
            raise HTTPException(
                status_code=401)

        employee = db.query(Employee).filter(
            Employee.id == int(employee_id)).first()

        if not employee:
            raise HTTPException(
                status_code=401)

        return employee
    except JWTError:
        raise HTTPException(
            status_code=401)
