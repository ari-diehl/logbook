from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session

from database import get_db
from schemas.vehicle import VehicleResponse, VehicleCreate, VehicleUpdate
from services.vehicle import vehicle_service

router = APIRouter(prefix="/vehicles")


@router.get("/", response_model=list[VehicleResponse])
def read_all(skip: int = 0, limit: int = 100, db: Session = Depends(get_db)):
    return vehicle_service.read_multi(db, skip, limit)


@router.get("/{vehicle_id}", response_model=VehicleResponse)
def read(vehicle_id: int, db: Session = Depends(get_db)):
    vehicle = vehicle_service.read(db, vehicle_id)

    if not vehicle:
        raise HTTPException(status_code=404)

    return vehicle


@router.post("/", response_model=VehicleResponse)
def create(vehicle_create: VehicleCreate, db: Session = Depends(get_db)):
    if vehicle_service.read(db, vehicle_create.id):
        raise HTTPException(
            status_code=400)

    return vehicle_service.create(db, vehicle_create)


@router.put("/", response_model=VehicleResponse)
def update(vehicle_update: VehicleUpdate, db: Session = Depends(get_db)):
    vehicle = vehicle_service.read(db, vehicle_update.id)

    if not vehicle:
        raise HTTPException(status_code=404)

    return vehicle_service.update(db, vehicle, vehicle_update)


@router.delete("/{vehicle_id}", response_model=VehicleResponse)
def delete(vehicle_id: int, db: Session = Depends(get_db)):
    vehicle = vehicle_service.read(db, vehicle_id)

    if not vehicle:
        raise HTTPException(status_code=404)

    return vehicle_service.delete(db, vehicle_id)
