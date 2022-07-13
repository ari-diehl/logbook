from services.base import BaseService
from schemas.vehicle_comment import VehicleCommentCreate, VehicleCommentUpdate
from models import VehicleComment


class VehicleCommentService(BaseService[VehicleComment, VehicleCommentCreate, VehicleCommentUpdate]):
    pass


vehicle_comment_service = VehicleCommentService(VehicleComment)
