from sqlalchemy.orm import Session

from services.base import BaseService
from schemas.vehicle_comment import VehicleCommentCreate, VehicleCommentUpdate
from models import VehicleComment


class VehicleCommentService(BaseService[VehicleComment, VehicleCommentCreate, VehicleCommentUpdate]):
    def read_by_vehicle_id(self, db: Session, vehicle_id: int):
        return db.query(self.model).filter(self.model.vehicle_id == vehicle_id).all()


vehicle_comment_service = VehicleCommentService(VehicleComment)
