from services.vehicle import vehicle_service
from schemas.vehicle import VehicleResponse, VehicleCreate, VehicleUpdate
from database import get_db
from sqlalchemy.orm import Session
from fastapi import APIRouter, Depends, HTTPException


router = APIRouter(prefix="/vehicles")


@router.get("/", response_model=list[VehicleResponse])
def read_vehicles(license_plate: str = None, skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    if license_plate:
        vehicle = vehicle_service.read_by_license_plate(db, license_plate)

        if not vehicle:
            raise HTTPException(status_code=404)

        return [vehicle]

    return vehicle_service.read_multi(db, skip, limit)


@router.get("/{vehicle_id}", response_model=VehicleResponse)
def read_vehicle(vehicle_id: int, db: Session = Depends(get_db)):
    vehicle = vehicle_service.read(db, vehicle_id)

    if not vehicle:
        raise HTTPException(status_code=404)

    return vehicle


@router.post("/", response_model=VehicleResponse)
def create_vehicle(vehicle_create: VehicleCreate, db: Session = Depends(get_db)):

    return vehicle_service.create(db, vehicle_create)


@router.put("/{vehicle_id}", response_model=VehicleResponse)
def update_vehicle(vehicle_id: int, vehicle_update: VehicleUpdate, db: Session = Depends(get_db)):
    vehicle = vehicle_service.read(db, vehicle_id)

    if not vehicle:
        raise HTTPException(status_code=404)

    return vehicle_service.update(db, vehicle, vehicle_update)


@router.delete("/{vehicle_id}", response_model=VehicleResponse)
def delete_vehicle(vehicle_id: int, db: Session = Depends(get_db)):
    vehicle = vehicle_service.read(db, vehicle_id)

    if not vehicle:
        raise HTTPException(status_code=404)

    return vehicle_service.delete(db, vehicle_id)
