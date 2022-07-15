from sqlalchemy.orm import Session

from services.base import BaseService
from schemas.vehicle import VehicleCreate, VehicleUpdate
from models import Vehicle


class VehicleService(BaseService[Vehicle, VehicleCreate, VehicleUpdate]):
    def read_by_license_plate(self, db: Session, license_plate: str):
        return db.query(self.model).filter(self.model.license_plate == license_plate).first()


vehicle_service = VehicleService(Vehicle)
