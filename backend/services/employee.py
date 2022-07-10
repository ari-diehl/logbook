from sqlalchemy.orm import Session
from datetime import datetime, timedelta
from jose import jwt
from passlib.context import CryptContext

from services.base import BaseService
from schemas.auth import EmployeeLoginResponse
from schemas.employee import EmployeeCreate, EmployeeUpdate
from models import Employee

crypt_context = CryptContext(schemes=["bcrypt"], deprecated="auto")


class EmployeeService(BaseService[Employee, EmployeeCreate, EmployeeUpdate]):

    def login(self, obj_db: Employee) -> EmployeeLoginResponse:
        return EmployeeLoginResponse(id=obj_db.id, first_name=obj_db.first_name, last_name=obj_db.last_name, role=obj_db.role, access_token=jwt.encode({"sub": str(obj_db.id), "exp": datetime.utcnow() + timedelta(hours=8)}, "4880f7a45cc62e08c5583078c59e8373acd4dd3f19db8eda606a61e5d101aa9c", "HS256"))

    def change_password(self, db: Session, obj_db: Employee, password: str):
        obj_db.password = crypt_context.hash(password)

        db.add(obj_db)
        db.commit()
        db.refresh(obj_db)

        return obj_db

    def create(self, db: Session, obj_create: EmployeeCreate) -> Employee:
        obj_create.password = crypt_context.hash(obj_create.password)

        return super().create(db, obj_create)


employee_service = EmployeeService(Employee)
