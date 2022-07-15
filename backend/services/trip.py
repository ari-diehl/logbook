from services.base import BaseService
from schemas.trip import TripCreate, TripUpdate
from models import Trip


class TripService(BaseService[Trip, TripCreate, TripUpdate]):
    pass


trip_service = TripService(Trip)
