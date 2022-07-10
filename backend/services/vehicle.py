from services.base import BaseService
from schemas.vehicle import VehicleCreate, VehicleUpdate
from models import Vehicle


class VehicleService(BaseService[Vehicle, VehicleCreate, VehicleUpdate]):
    pass


vehicle_service = VehicleService(Vehicle)
